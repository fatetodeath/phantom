package me.phantom.mixin.network;

import me.phantom.core.PhantomClient;
import me.phantom.event.packet.PacketEvent;
import me.phantom.event.packet.PacketSendEvent;

import io.netty.channel.ChannelHandlerContext;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientConnection.class})
public class ClientConnectionPacketMixin {
   @Inject(
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V",
         ordinal = 0
      )},
      method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V"},
      cancellable = true
   )
   private void channelRead0(ChannelHandlerContext context, Packet<ClientPlayNetworkHandler> packet, CallbackInfo info) {
      if (this.checkClient() && PhantomClient.initialized && !PhantomClient.sendingPacket && PhantomClient.eventManager.dispatch(PacketEvent.hd(packet)).isCancelled()) {
         info.cancel();
      }
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"send(Lnet/minecraft/network/packet/Packet;)V"},
      cancellable = true
   )
   private void send(Packet<ServerPlayNetworkHandler> packet, CallbackInfo info) {
      if (this.checkClient() && PhantomClient.initialized && !PhantomClient.sendingPacket && PhantomClient.eventManager.dispatch(PacketSendEvent.sk(packet)).isCancelled()) {
         info.cancel();
      }
   }

   private boolean checkClient() {
      ClientPlayNetworkHandler handler = MinecraftClient.getInstance().getNetworkHandler();
      if (handler == null) {
         return false;
      } else {
         ClientConnection _this = (ClientConnection)(Object)this;
         return Objects.equals(_this, handler.getConnection());
      }
   }
}
