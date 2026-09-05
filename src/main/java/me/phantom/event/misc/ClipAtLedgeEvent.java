package me.phantom.event.misc;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

public class ClipAtLedgeEvent extends EventBase {
   public static PerThreadObjectPool<ClipAtLedgeEvent> pool = new PerThreadObjectPool(() -> new ClipAtLedgeEvent());
   public boolean aj;

   public static ClipAtLedgeEvent qon() {
      ClipAtLedgeEvent var0 = (ClipAtLedgeEvent)pool.get();
      var0.aj = false;
      return var0;
   }

   public boolean iyh() {
      return this.aj;
   }

   public void eq(boolean var1) {
      this.aj = var1;
   }
}
