package me.phantom.mixin.entity;

import me.phantom.util.mc.FishingBobberDuck;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({FishingBobberEntity.class})
public abstract class FishingBobberAccessorMixin implements FishingBobberDuck {
   @Shadow
   public boolean caughtFish;

   @Shadow
   @Override
   public abstract PlayerEntity getPlayerOwner();

   @Override
   public boolean hasCaughtFish() {
      return this.caughtFish;
   }
}
