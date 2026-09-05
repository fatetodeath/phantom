package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.StatusEffectEvent;
import me.phantom.util.mc.JumpCooldownAccessor;

import java.util.Map;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({LivingEntity.class})
public abstract class LivingEntityStatusEffectMixin implements JumpCooldownAccessor {
   @Shadow
   @Final
   private Map<RegistryEntry<StatusEffect>, StatusEffectInstance> activeStatusEffects;

   @Overwrite(
      aliases = {"hasStatusEffect"}
   )
   public boolean hasStatusEffect(RegistryEntry<StatusEffect> effect) {
      return (Object)this instanceof ClientPlayerEntity
            && (
               effect.value() == StatusEffects.BLINDNESS
                  || effect.value() == StatusEffects.NAUSEA
                  || effect.value() == StatusEffects.DARKNESS
                  || effect.value() == StatusEffects.SPEED
                  || effect.value() == StatusEffects.SLOWNESS
            )
            && PhantomClient.initialized
            && PhantomClient.eventManager.dispatch(StatusEffectEvent.ofv()).isCancelled()
         ? false
         : this.activeStatusEffects.containsKey(effect);
   }

   @Accessor
   @Override
   public abstract void setJumpingCooldown(int var1);
}
