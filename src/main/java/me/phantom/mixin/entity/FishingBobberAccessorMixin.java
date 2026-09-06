package me.phantom.mixin.entity;

import me.phantom.util.mc.FishingBobberDuck;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({FishingHook.class})
public abstract class FishingBobberAccessorMixin implements FishingBobberDuck {
   @Shadow
   public boolean biting;

   @Shadow
   @Override
   public abstract Player getPlayerOwner();

   @Override
   public boolean hasCaughtFish() {
      return this.biting;
   }
}
