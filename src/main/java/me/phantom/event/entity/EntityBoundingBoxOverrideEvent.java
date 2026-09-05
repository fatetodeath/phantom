package me.phantom.event.entity;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class EntityBoundingBoxOverrideEvent extends EventBase {
   private static PerThreadObjectPool<EntityBoundingBoxOverrideEvent> pool = new PerThreadObjectPool(() -> new EntityBoundingBoxOverrideEvent());
   private Entity kg;
   private Box og;

   public static EntityBoundingBoxOverrideEvent gbt(Entity var0, Box var1) {
      EntityBoundingBoxOverrideEvent var2 = (EntityBoundingBoxOverrideEvent)pool.get();
      var2.kg = var0;
      var2.og = var1;
      return var2;
   }

   public void uir(Box var1) {
      this.og = var1;
   }

   public Entity tg() {
      return this.kg;
   }

   public Box sq() {
      return this.og;
   }

}
