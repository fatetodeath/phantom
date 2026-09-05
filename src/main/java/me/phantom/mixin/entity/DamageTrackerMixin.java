package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.EntityDamageEvent;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({DamageTracker.class})
public abstract class DamageTrackerMixin {
   @Shadow
   @Final
   public LivingEntity entity;

   @Inject(
      method = {"onDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"},
      at = {@At("HEAD")}
   )
   public void onDamage(CallbackInfo info, @Local(ordinal = 0) DamageSource damageSource, @Local(ordinal = 0) float damage) {
      if (PhantomClient.initialized) {
         PhantomClient.eventManager.dispatch(EntityDamageEvent.ef(this.entity, damageSource, damage));
      }
   }
}
