package me.phantom.event.entity;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.entity.Entity;

public class AttackEntityEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<AttackEntityEvent> pool = new PerThreadObjectPool(() -> new AttackEntityEvent());
   private Entity kg;
   public boolean zq;

   public static AttackEntityEvent he(Entity var0) {
      AttackEntityEvent var1 = (AttackEntityEvent)pool.get();
      var1.setCancelled(false);
      var1.xs(var0);
      var1.ywz(false);
      return var1;
   }

   public void xs(Entity var1) {
      this.kg = var1;
   }

   public Entity tg() {
      return this.kg;
   }

   public boolean re() {
      return this.zq;
   }

   public void ywz(boolean var1) {
      this.zq = var1;
   }
}
