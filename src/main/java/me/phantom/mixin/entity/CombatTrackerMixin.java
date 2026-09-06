package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.EntityDamageEvent;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.CombatTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CombatTracker.class})
public abstract class CombatTrackerMixin {
   @Shadow
   @Final
   public LivingEntity mob;

   @Inject(
      method = {"recordDamage(Lnet/minecraft/world/damagesource/DamageSource;F)V"},
      at = {@At("HEAD")}
   )
   public void onDamage(CallbackInfo info, @Local(ordinal = 0) DamageSource damageSource, @Local(ordinal = 0) float damage) {
      if (PhantomClient.initialized) {
         PhantomClient.eventManager.dispatch(EntityDamageEvent.ef(this.mob, damageSource, damage));
      }
   }
}
