package me.phantom.mixin.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({AbstractThrownPotion.class})
public abstract class AbstractThrownPotionFlyingItemMixin extends ThrowableItemProjectile implements ItemSupplier {
   public AbstractThrownPotionFlyingItemMixin(EntityType<? extends AbstractThrownPotion> entityType, Level world) {
      super(entityType, world);
   }
}
