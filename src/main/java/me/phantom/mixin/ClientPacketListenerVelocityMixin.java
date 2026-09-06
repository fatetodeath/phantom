package me.phantom.mixin;

import me.phantom.core.PhantomClient;
import me.phantom.event.packet.PacketType;
import me.phantom.event.entity.PlayerVelocityEvent;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ClientPacketListener.class})
public class ClientPacketListenerVelocityMixin {
   @Redirect(
      method = {"handleSetEntityMotion(Lnet/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket;)V"},
      at = @At(
         value = "INVOKE",
          target = "Lnet/minecraft/world/entity/Entity;lerpMotion(Lnet/minecraft/world/phys/Vec3;)V"
      )
   )
   public void onEntityVelocityUpdate_setVelocityClient(Entity entity, Vec3 velocity) {
      if (entity instanceof LocalPlayer && PhantomClient.initialized) {
         PlayerVelocityEvent event = PhantomClient.eventManager.dispatch(PlayerVelocityEvent.jfp(entity, velocity, PacketType.sf));
         entity.lerpMotion(event.waw());
      } else {
         entity.setDeltaMovement(velocity);
      }
   }
}
