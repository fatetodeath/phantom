package me.phantom.event.entity;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.PerThreadObjectPool;

public class StatusEffectEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<StatusEffectEvent> pool = new PerThreadObjectPool(() -> new StatusEffectEvent());

   public static StatusEffectEvent ofv() {
      StatusEffectEvent var0 = (StatusEffectEvent)pool.get();
      var0.setCancelled(false);
      return var0;
   }
}
