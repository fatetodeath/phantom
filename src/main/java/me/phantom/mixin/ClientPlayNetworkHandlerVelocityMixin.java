package me.phantom.mixin;

import me.phantom.core.PhantomClient;
import me.phantom.event.packet.PacketType;
import me.phantom.event.entity.PlayerVelocityEvent;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ClientPlayNetworkHandler.class})
public class ClientPlayNetworkHandlerVelocityMixin {
   @Redirect(
      method = {"onEntityVelocityUpdate(Lnet/minecraft/network/packet/s2c/play/EntityVelocityUpdateS2CPacket;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/Entity;setVelocityClient(DDD)V"
      )
   )
   public void onEntityVelocityUpdate_setVelocityClient(Entity entity, double x, double y, double z) {
      if (entity instanceof ClientPlayerEntity && PhantomClient.initialized) {
         PlayerVelocityEvent event = PhantomClient.eventManager.dispatch(PlayerVelocityEvent.jfp(entity, new Vec3d(x, y, z), PacketType.sf));
         entity.setVelocityClient(event.waw().getX(), event.waw().getY(), event.waw().getZ());
      } else {
         entity.setVelocity(x, y, z);
      }
   }
}
