package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.AttackEntityEvent;
import me.phantom.event.entity.CollisionType;
import me.phantom.event.entity.EntityPushEvent;
import me.phantom.event.packet.PacketType;
import me.phantom.event.entity.PlayerVelocityEvent;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public class EntityPushVelocityMixin {
   @Inject(
      at = {@At("HEAD")},
      method = {"pushAwayFrom"},
      cancellable = true
   )
   public void pushAwayFrom(Entity entity, CallbackInfo callback) {
      if ((Entity)(Object)this instanceof ClientPlayerEntity && PhantomClient.initialized && PhantomClient.eventManager.dispatch(EntityPushEvent.sos(CollisionType.itl)).isCancelled()) {
         callback.cancel();
      }
   }

   @ModifyVariable(
      at = @At("HEAD"),
      method = {"addVelocityInternal"},
      index = 1,
      argsOnly = true
   )
   public Vec3d addVelocityInternal(Vec3d velocity) {
      return (Entity)(Object)this instanceof ClientPlayerEntity && PhantomClient.initialized ? PhantomClient.eventManager.dispatch(PlayerVelocityEvent.jfp((Entity)(Object)this, velocity, PacketType.cq)).waw() : velocity;
   }

   @Redirect(
      method = {"updateMovementInFluid(Lnet/minecraft/registry/tag/TagKey;D)Z"},
      at = @At(
         value = "INVOKE",
         target = "net/minecraft/entity/Entity.setVelocity(Lnet/minecraft/util/math/Vec3d;)V"
      )
   )
   public void updateMovementInFluid(Entity entity, Vec3d velocity) {
      if (!(entity instanceof ClientPlayerEntity) || !PhantomClient.initialized || PhantomClient.sendingPacket || !PhantomClient.eventManager.dispatch(EntityPushEvent.sos(CollisionType.nu)).isCancelled()) {
         entity.setVelocity(velocity);
      }
   }

   @Inject(
      at = {@At("HEAD")},
      method = {"isInvisible"},
      cancellable = true
   )
   private void isInvisible(CallbackInfoReturnable<Boolean> callback) {
      if (PhantomClient.initialized) {
         Entity entity = (Entity)(Object)this;
         AttackEntityEvent event = PhantomClient.eventManager.dispatch(AttackEntityEvent.he(entity));
         if (event.isCancelled()) {
            callback.setReturnValue(event.re());
         }
      }
   }
}
