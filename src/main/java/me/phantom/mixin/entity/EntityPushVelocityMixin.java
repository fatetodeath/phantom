package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.AttackEntityEvent;
import me.phantom.event.entity.CollisionType;
import me.phantom.event.entity.EntityPushEvent;
import me.phantom.event.packet.PacketType;
import me.phantom.event.entity.PlayerVelocityEvent;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public class EntityPushVelocityMixin {
   @Inject(
      at = {@At("HEAD")},
      method = {"push"},
      cancellable = true
   )
   public void pushAwayFrom(Entity entity, CallbackInfo callback) {
      if ((Entity)(Object)this instanceof LocalPlayer && PhantomClient.initialized && PhantomClient.eventManager.dispatch(EntityPushEvent.sos(CollisionType.itl)).isCancelled()) {
         callback.cancel();
      }
   }

   @ModifyVariable(
      at = @At("HEAD"),
      method = {"addDeltaMovement"},
      index = 1,
      argsOnly = true
   )
   public Vec3 addVelocityInternal(Vec3 velocity) {
      return (Entity)(Object)this instanceof LocalPlayer && PhantomClient.initialized ? PhantomClient.eventManager.dispatch(PlayerVelocityEvent.jfp((Entity)(Object)this, velocity, PacketType.cq)).waw() : velocity;
   }

    @Inject(
      method = {"updateFluidInteraction"},
      at = {@At("HEAD")},
      cancellable = true
    )
    public void updateMovementInFluid(CallbackInfoReturnable<Boolean> callback) {
       if ((Entity)(Object)this instanceof LocalPlayer && PhantomClient.initialized && !PhantomClient.sendingPacket && PhantomClient.eventManager.dispatch(EntityPushEvent.sos(CollisionType.nu)).isCancelled()) {
          callback.setReturnValue(false);
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
