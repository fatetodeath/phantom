package me.phantom.event.entity;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.PerThreadObjectPool;

public class CrosshairTargetUpdateEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<CrosshairTargetUpdateEvent> pool = new PerThreadObjectPool(() -> new CrosshairTargetUpdateEvent());
   private float wq;

   public static CrosshairTargetUpdateEvent nxk(float var0) {
      CrosshairTargetUpdateEvent var1 = (CrosshairTargetUpdateEvent)pool.get();
      var1.setCancelled(false);
      var1.wq = var0;
      return var1;
   }

   public float oy() {
      return this.wq;
   }
}
