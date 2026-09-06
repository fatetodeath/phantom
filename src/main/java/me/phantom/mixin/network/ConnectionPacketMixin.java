package me.phantom.mixin.network;

import me.phantom.core.PhantomClient;
import me.phantom.event.packet.PacketEvent;
import me.phantom.event.packet.PacketSendEvent;

import io.netty.channel.ChannelHandlerContext;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Connection.class})
public class ConnectionPacketMixin {
   @Inject(
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/Connection;genericsFtw(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V",
         ordinal = 0
      )},
      method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V"},
      cancellable = true
   )
   private void channelRead0(ChannelHandlerContext context, Packet<ClientPacketListener> packet, CallbackInfo info) {
      if (this.checkClient() && PhantomClient.initialized && !PhantomClient.sendingPacket && PhantomClient.eventManager.dispatch(PacketEvent.hd(packet)).isCancelled()) {
         info.cancel();
      }
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"send(Lnet/minecraft/network/protocol/Packet;)V"},
      cancellable = true
   )
   private void send(Packet<ServerGamePacketListenerImpl> packet, CallbackInfo info) {
      if (this.checkClient() && PhantomClient.initialized && !PhantomClient.sendingPacket && PhantomClient.eventManager.dispatch(PacketSendEvent.sk(packet)).isCancelled()) {
         info.cancel();
      }
   }

   private boolean checkClient() {
      ClientPacketListener handler = Minecraft.getInstance().getConnection();
      if (handler == null) {
         return false;
      } else {
         Connection _this = (Connection)(Object)this;
         return Objects.equals(_this, handler.getConnection());
      }
   }
}
