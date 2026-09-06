package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Objects;

public class FloatSliderSetting extends AbstractSetting<Float> {
   public float pya;
   private float cmr;
   public int ckc;
   private long oel = System.currentTimeMillis();

   public FloatSliderSetting(BaseModule var1, String var2, float var3, float var4, float var5) {
      super(var1, var2, var4);
      this.pya = var3;
      this.cmr = var5;
   }

   public void aiu(Float var1) {
      super.setValue(Math.min(this.cmr, Math.max(this.pya, var1)));
   }

   public float wgp() {
      return this.cmr;
   }

   public float leo() {
      return this.pya;
   }

   @Override
   public JsonElement toJson() {
      return new JsonPrimitive((Float)this.getValue());
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.aiu(var1.getAsFloat());
   }

   @Override
   public int getHeight() {
      return 42;
   }

   @Override
   public String getDisplayName() {
      return this.cachedLabel.replace(GlyphTextUtil.encode("$1"), RotationMathUtil.formatTwoDecimals((Float)this.getValue()));
   }

   @Override
   public String getDescription() {
      return this.cachedDescription.replace(GlyphTextUtil.encode("$1"), RotationMathUtil.formatTwoDecimals((Float)this.getValue()));
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.lgo(var3, var4, var2, 45, ((Float)this.getValue() - this.pya) / (this.cmr - this.pya), this.getDisplayName(), Objects.equals(var1.xwl, this))) {
         if (System.currentTimeMillis() - this.oel >= 160L) {
            if (var1.gml[262]) {
               this.aiu((Float)this.getValue() + 0.1F);
            }

            if (var1.gml[263]) {
               this.aiu((Float)this.getValue() - 0.1F);
            }

            this.oel = System.currentTimeMillis();
         }

         if (!var1.gml[262] && !var1.gml[263]) {
            this.oel = 0L;
         }

         var1.setTooltip(this.getDescription());
         if (Objects.equals(var1.xwl, this)) {
            if (var1.xfy[0]) {
               int var7 = var3 - this.ckc;
               float var8 = Math.abs(var4 - var1.mouseY);
               byte var9 = 45;
               int var10 = var9 + var1.framebufferHeight / 3;
               if (var8 <= var9) {
                  var8 = 1.0F;
               } else if (var8 <= var10) {
                  var8 = (1.0F - (var8 - var9) / (var10 - var9)) * 0.95F + 0.05F;
               } else {
                  var8 = 0.05F;
               }

               this.ckc = var3;
               this.aiu((Float)this.getValue() + (this.cmr - this.pya) * (var7 / (1.0F * var2)) * var8);
            } else {
               var1.xwl = null;
            }
         } else if (var1.doj[0]) {
            var1.doj[0] = false;
            var1.xwl = this;
            this.ckc = var3;
         }
      }
   }

   public int gol() {
      return Math.round((Float)this.getValue() / 20.0F);
   }
}
