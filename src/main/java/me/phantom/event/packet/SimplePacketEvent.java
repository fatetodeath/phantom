package me.phantom.event.packet;

import me.phantom.util.PerThreadObjectPool;

public class SimplePacketEvent extends AbstractPacketEvent {
   private static PerThreadObjectPool<SimplePacketEvent> pool = new PerThreadObjectPool(() -> new SimplePacketEvent());

   public static SimplePacketEvent xet(float var0) {
      SimplePacketEvent var1 = (SimplePacketEvent)pool.get();
      var1.setCancelled(false);
      return var1;
   }
}
