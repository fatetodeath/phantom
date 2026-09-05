package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.EntityBoundingBoxOverrideEvent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ProjectileUtil.class})
public class ProjectileUtilHitboxMixin {
   @Redirect(
      method = {"raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"},
      at = @At(
         value = "INVOKE",
         target = "net/minecraft/entity/Entity.getBoundingBox()Lnet/minecraft/util/math/Box;"
      )
   )
   private static Box getBoundingBox(Entity entity) {
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(EntityBoundingBoxOverrideEvent.gbt(entity, entity.getBoundingBox())).sq() : entity.getBoundingBox();
   }
}
