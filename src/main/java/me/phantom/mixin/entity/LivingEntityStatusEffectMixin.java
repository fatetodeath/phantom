package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.StatusEffectEvent;
import me.phantom.util.mc.JumpCooldownAccessor;

import java.util.Map;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({LivingEntity.class})
public abstract class LivingEntityStatusEffectMixin implements JumpCooldownAccessor {
   @Shadow
   @Final
   private Map<Holder<MobEffect>, MobEffectInstance> activeEffects;

   @Overwrite(
      aliases = {"hasStatusEffect"}
   )
   public boolean hasEffect(Holder<MobEffect> effect) {
      return (Object)this instanceof LocalPlayer
            && (
               effect.value() == MobEffects.BLINDNESS
                  || effect.value() == MobEffects.NAUSEA
                  || effect.value() == MobEffects.DARKNESS
                  || effect.value() == MobEffects.SPEED
                  || effect.value() == MobEffects.SLOWNESS
            )
            && PhantomClient.initialized
            && PhantomClient.eventManager.dispatch(StatusEffectEvent.ofv()).isCancelled()
         ? false
         : this.activeEffects.containsKey(effect);
   }

    @Shadow
    private int noJumpDelay;

    @Override
    public void setJumpingCooldown(int var1) {
        this.noJumpDelay = var1;
    }
}
