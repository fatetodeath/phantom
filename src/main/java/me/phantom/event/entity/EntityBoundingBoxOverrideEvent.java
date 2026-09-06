package me.phantom.event.entity;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public class EntityBoundingBoxOverrideEvent extends EventBase {
   private static PerThreadObjectPool<EntityBoundingBoxOverrideEvent> pool = new PerThreadObjectPool(() -> new EntityBoundingBoxOverrideEvent());
   private Entity kg;
   private AABB og;

   public static EntityBoundingBoxOverrideEvent gbt(Entity var0, AABB var1) {
      EntityBoundingBoxOverrideEvent var2 = (EntityBoundingBoxOverrideEvent)pool.get();
      var2.kg = var0;
      var2.og = var1;
      return var2;
   }

   public void uir(AABB var1) {
      this.og = var1;
   }

   public Entity tg() {
      return this.kg;
   }

   public AABB sq() {
      return this.og;
   }

}
