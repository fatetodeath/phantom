package me.phantom.event.render;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

public class FovMultiplierEvent extends EventBase {
   private static PerThreadObjectPool<FovMultiplierEvent> pool = new PerThreadObjectPool(() -> new FovMultiplierEvent());
   private float nfi;

   public static FovMultiplierEvent vp(float var0) {
      FovMultiplierEvent var1 = (FovMultiplierEvent)pool.get();
      var1.nfi = var0;
      return var1;
   }

   public float getMultiplier() {
      return this.nfi;
   }

   public void gd(float var1) {
      this.nfi = var1;
   }
}
