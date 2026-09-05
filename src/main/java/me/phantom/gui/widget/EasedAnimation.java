package me.phantom.gui.widget;

import me.phantom.core.PhantomClient;

public class EasedAnimation {
   public final boolean jsl;
   public long startTime;
   public int tco;

   public EasedAnimation(boolean var1) {
      this.jsl = var1;
   }

   public void qou(int var1) {
      if (PhantomClient.uiManager == null || (Boolean)PhantomClient.uiManager.interfaceModule.ppr.getValue()) {
         float var2 = PhantomClient.uiManager == null ? 1.0F : (Float)PhantomClient.uiManager.interfaceModule.wmp.getValue();
         if (!(var2 <= 0.0F)) {
            this.startTime = System.currentTimeMillis();
            this.tco = (int)(var1 * (1.0F / var2));
         }
      }
   }

   public float hee() {
      long var1 = System.currentTimeMillis() - this.startTime;
      if (var1 > this.tco) {
         return this.jsl ? 0.0F : 1.0F;
      } else {
         float var3 = (float)var1 / (this.tco * 1.0F);
         return this.jsl ? this.hku(var3) : 1.0F - this.hku(1.0F - var3);
      }
   }

   private float hku(float var1) {
      return (float)Math.pow(var1, 2.7);
   }

   public boolean sdx() {
      return System.currentTimeMillis() - this.startTime <= this.tco;
   }
}
