package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.minecraft.util.math.Vec2f;

public class FloatRangeSliderSetting extends AbstractSetting<Vec2f> {
   public float pya;
   private float cmr;
   public float fpw;
   public int ckc;
   public boolean nhv;
   public long oel = System.currentTimeMillis();

   public FloatRangeSliderSetting(BaseModule var1, String var2, float var3, float var4, float var5, float var6) {
      super(var1, var2, new Vec2f(var4, var5));
      this.pya = var3;
      this.cmr = var6;
   }

   public void abn(Vec2f var1) {
      float var2 = Math.min(this.cmr, Math.max(this.pya, var1.x));
      float var3 = Math.min(this.cmr, Math.max(this.pya, var1.y));
      if (var2 > var3) {
         float var4 = var3;
         var3 = var2;
         var2 = var4;
      }

      super.setValue(new Vec2f(var2, var3));
   }

   public float fbz() {
      return (float)(this.she() + (this.usw() - this.she()) * Math.random());
   }

   public float she() {
      return ((Vec2f)this.getValue()).x;
   }

   public float usw() {
      return ((Vec2f)this.getValue()).y;
   }

   public float wgp() {
      return this.cmr;
   }

   public float rug() {
      return this.cmr - this.pya;
   }

   @Override
   public String getDisplayName() {
      return this.cachedLabel.replace(GlyphTextUtil.encode("$1"), RotationMathUtil.formatTwoDecimals(this.she())).replace(GlyphTextUtil.encode("$2"), RotationMathUtil.formatTwoDecimals(this.usw()));
   }

   @Override
   public String getDescription() {
      return this.cachedDescription.replace(GlyphTextUtil.encode("$1"), RotationMathUtil.formatTwoDecimals(this.she())).replace(GlyphTextUtil.encode("$2"), RotationMathUtil.formatTwoDecimals(this.usw()));
   }

   @Override
   public JsonElement toJson() {
      JsonObject var1 = new JsonObject();
      var1.addProperty(GlyphTextUtil.encode("min"), (double)this.she());
      var1.addProperty(GlyphTextUtil.encode("max"), (double)this.usw());
      return var1;
   }

   @Override
   public void fromJson(JsonElement var1) {
      JsonObject var2 = (JsonObject)var1;
      this.abn(new Vec2f(var2.get(GlyphTextUtil.encode("min")).getAsFloat(), var2.get(GlyphTextUtil.encode("max")).getAsFloat()));
   }

   @Override
   public int getHeight() {
      return 42;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      float var7 = this.rug();
      float var8 = (this.she() - this.pya) / var7;
      float var9 = (this.usw() - this.pya) / var7;
      float var10 = (var8 + var9) / 2.0F;
      boolean var11 = Objects.equals(this, var1.xwl);
      if (var1.thg(var3, var4, var2, 45, var8, var9, this.getDisplayName(), var11)) {
         if (System.currentTimeMillis() - this.oel >= 160L) {
            boolean var12 = var3 >= var1.mouseX + var10 * var2;
            if (var1.gml[262]) {
               float var13 = (var12 ? this.usw() : this.she()) + 0.1F;
               this.abn(new Vec2f(var12 ? this.she() : var13, var12 ? var13 : this.usw()));
            }

            if (var1.gml[263]) {
               float var15 = (var12 ? this.usw() : this.she()) - 0.1F;
               this.abn(new Vec2f(var12 ? this.she() : var15, var12 ? var15 : this.usw()));
            }

            this.oel = System.currentTimeMillis();
         }

         if (!var1.gml[262] && !var1.gml[263]) {
            this.oel = 0L;
         }

         var1.setTooltip(this.getDescription());
         if (Objects.equals(this, var1.xwl)) {
            if (var1.xfy[0]) {
               float var14 = this.fpw + (this.cmr - this.pya) * ((var3 - this.ckc) / (1.0F * var2));
               this.abn(new Vec2f(this.nhv ? this.she() : var14, this.nhv ? var14 : this.usw()));
            } else {
               var1.xwl = null;
            }
         } else if (var1.doj[0]) {
            this.nhv = var3 >= var1.mouseX + var10 * var2;
            var1.xwl = this;
            var1.doj[0] = false;
            this.ckc = var3;
            this.fpw = this.nhv ? this.usw() : this.she();
         }
      }
   }

   public float leo() {
      return this.pya;
   }

   public int cah() {
      return Math.round(this.fbz() * 20.0F);
   }
}
