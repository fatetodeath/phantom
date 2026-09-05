package me.phantom.event.entity;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.PerThreadObjectPool;

public class EntityPushEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<EntityPushEvent> pool = new PerThreadObjectPool(() -> new EntityPushEvent());
   private CollisionType ll;

   public static EntityPushEvent sos(CollisionType var0) {
      EntityPushEvent var1 = (EntityPushEvent)pool.get();
      var1.ll = var0;
      var1.setCancelled(false);
      return var1;
   }

   public CollisionType xna() {
      return this.ll;
   }
}
