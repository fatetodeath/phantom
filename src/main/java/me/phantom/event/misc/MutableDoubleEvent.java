package me.phantom.event.misc;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

public class MutableDoubleEvent extends EventBase {
   private static PerThreadObjectPool<MutableDoubleEvent> pool = new PerThreadObjectPool(() -> new MutableDoubleEvent());
   private double wh;

   public static MutableDoubleEvent iwi(double var0) {
      MutableDoubleEvent var2 = (MutableDoubleEvent)pool.get();
      var2.wh = var0;
      return var2;
   }

   public double fgb() {
      return this.wh;
   }

   public void ph(double var1) {
      this.wh = var1;
   }
}
