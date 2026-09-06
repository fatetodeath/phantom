package me.phantom.event.render;

import me.phantom.event.EventBase;
import me.phantom.util.PerThreadObjectPool;

public class RenderAlphaEvent extends EventBase {
   public static PerThreadObjectPool<RenderAlphaEvent> pool = new PerThreadObjectPool(() -> new RenderAlphaEvent());
   private float eg;
   public boolean xm;

   public static RenderAlphaEvent ao(float var0, boolean var1) {
      RenderAlphaEvent var2 = (RenderAlphaEvent)pool.get();
      var2.eg = var0;
      var2.xm = false;
      return var2;
   }

   public float oqw() {
      return this.eg;
   }

   public void tj(float var1) {
      this.eg = var1;
   }

   public boolean swj() {
      return this.xm;
   }

   public void fo(boolean var1) {
      this.xm = var1;
   }
}
