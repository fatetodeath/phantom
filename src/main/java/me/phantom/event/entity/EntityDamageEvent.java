package me.phantom.event.entity;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;

public class EntityDamageEvent extends EventBase {
   private static PerThreadObjectPool<EntityDamageEvent> pool = new PerThreadObjectPool(() -> new EntityDamageEvent());
   private Entity kg;
   private DamageSource vn;
   public float ptc;

   public static EntityDamageEvent ef(Entity var0, DamageSource var1, float var2) {
      EntityDamageEvent var3 = (EntityDamageEvent)pool.get();
      var3.kg = var0;
      var3.vn = var1;
      var3.ptc = var2;
      return var3;
   }

   public Entity tg() {
      return this.kg;
   }

   public DamageSource aq() {
      return this.vn;
   }

   public float wm() {
      return this.ptc;
   }
}
