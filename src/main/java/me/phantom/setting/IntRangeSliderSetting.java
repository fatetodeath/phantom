package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.IntPoint;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;

public class IntRangeSliderSetting extends AbstractSetting<IntPoint> {
   public int paq;
   public int swl;
   private float fpw;
   private int ckc;
   public boolean nhv;
   public long oel = System.currentTimeMillis();

   public IntRangeSliderSetting(BaseModule var1, String var2, int var3, int var4, int var5, int var6) {
      super(var1, var2, new IntPoint(var4, var5));
      this.paq = var3;
      this.swl = var6;
   }

   public void qdk(IntPoint var1) {
      int var2 = Math.min(this.swl, Math.max(this.paq, var1.x));
      int var3 = Math.min(this.swl, Math.max(this.paq, var1.y));
      if (var2 > var3) {
         int var4 = var3;
         var3 = var2;
         var2 = var4;
      }

      super.setValue(new IntPoint(var2, var3));
   }

   public int koj() {
      return (int)(this.pop() + (this.zcq() - this.pop()) * Math.random());
   }

   public int pop() {
      return ((IntPoint)this.getValue()).x;
   }

   public int zcq() {
      return ((IntPoint)this.getValue()).y;
   }

   public int ciz() {
      return this.swl;
   }

   @Override
   public JsonElement toJson() {
      JsonObject var1 = new JsonObject();
      var1.addProperty(GlyphTextUtil.encode("min"), this.pop());
      var1.addProperty(GlyphTextUtil.encode("max"), this.zcq());
      return var1;
   }

   @Override
   public void fromJson(JsonElement var1) {
      JsonObject var2 = var1.getAsJsonObject();
      this.qdk(new IntPoint(var2.get(GlyphTextUtil.encode("min")).getAsInt(), var2.get(GlyphTextUtil.encode("max")).getAsInt()));
   }

   @Override
   public int getHeight() {
      return 42;
   }

   @Override
   public String getDisplayName() {
      return this.cachedLabel.replace(GlyphTextUtil.encode("$1"), String.valueOf(this.pop())).replace(GlyphTextUtil.encode("$2"), String.valueOf(this.zcq()));
   }

   @Override
   public String getDescription() {
      return this.cachedDescription.replace(GlyphTextUtil.encode("$1"), String.valueOf(this.pop())).replace(GlyphTextUtil.encode("$2"), String.valueOf(this.zcq()));
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      float var7 = this.qp();
      float var8 = (this.pop() - this.paq) / var7;
      float var9 = (this.zcq() - this.paq) / var7;
      float var10 = (var8 + var9) / 2.0F;
      boolean var11 = Objects.equals(this, var1.xwl);
      if (var1.thg(var3, var4, var2, 45, var8, var9, this.getDisplayName(), var11)) {
         if (System.currentTimeMillis() - this.oel >= 200L) {
            boolean var12 = var3 >= var1.mouseX + var10 * var2;
            if (var1.gml[262]) {
               int var13 = (var12 ? this.zcq() : this.pop()) + 1;
               this.qdk(new IntPoint(var12 ? this.pop() : var13, var12 ? var13 : this.zcq()));
            }

            if (var1.gml[263]) {
               int var15 = (var12 ? this.zcq() : this.pop()) - 1;
               this.qdk(new IntPoint(var12 ? this.pop() : var15, var12 ? var15 : this.zcq()));
            }

            this.oel = System.currentTimeMillis();
         }

         if (!var1.gml[262] && !var1.gml[263]) {
            this.oel = 0L;
         }

         var1.setTooltip(this.getDescription());
         if (var11) {
            if (var1.xfy[0]) {
               int var14 = Math.round(this.fpw + (this.swl - this.paq) * ((var3 - this.ckc) / (1.0F * var2)));
               this.qdk(new IntPoint(this.nhv ? this.pop() : var14, this.nhv ? var14 : this.zcq()));
            } else {
               var1.xwl = null;
            }
         } else if (var1.doj[0]) {
            this.nhv = var3 >= var1.mouseX + var10 * var2;
            var1.xwl = this;
            var1.doj[0] = false;
            this.ckc = var3;
            this.fpw = this.nhv ? this.zcq() : this.pop();
         }
      }
   }

   public int nsp() {
      return this.paq;
   }

   public int qp() {
      return this.swl - this.paq;
   }
}
