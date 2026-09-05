package me.phantom.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({PotionEntity.class})
public abstract class PotionEntityFlyingItemMixin extends ThrownItemEntity implements FlyingItemEntity {
   public PotionEntityFlyingItemMixin(EntityType<? extends PotionEntity> entityType, World world) {
      super(entityType, world);
   }
}
