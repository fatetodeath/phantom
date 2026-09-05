package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Objects;
import java.util.function.Function;

public class IntegerSliderSetting extends AbstractSetting<Integer> {
   private int paq;
   private int swl;
   private float lxg;
   public int ckc;
   private long oel = System.currentTimeMillis();
   public Function<Integer, String> rae;

   public IntegerSliderSetting(BaseModule var1, String var2, int var3, int var4, int var5) {
      super(var1, var2, var4);
      this.paq = var3;
      this.swl = var5;
   }

   public void esd(Integer var1) {
      super.setValue(Math.min(this.swl, Math.max(this.paq, var1)));
   }

   public int ciz() {
      return this.swl;
   }

   public int nsp() {
      return this.paq;
   }

   @Override
   public int getHeight() {
      return 42;
   }

   @Override
   public JsonElement toJson() {
      return new JsonPrimitive((Number)this.getValue());
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.esd(var1.getAsInt());
   }

   @Override
   public String getDisplayName() {
      return super.getDisplayName().replace(GlyphTextUtil.encode("$1"), (CharSequence)(this.rae == null ? ((Integer)this.getValue()).toString() : this.rae.apply((Integer)this.getValue())));
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.lgo(var3, var4, var2, 45, ((Integer)this.getValue() - this.paq) / (1.0F * (this.swl - this.paq)), this.getDisplayName(), Objects.equals(var1.xwl, this))) {
         if (System.currentTimeMillis() - this.oel >= 160L) {
            if (var1.gml[262]) {
               this.esd((Integer)this.getValue() + 1);
            }

            if (var1.gml[263]) {
               this.esd((Integer)this.getValue() - 1);
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

               this.lxg = this.lxg + (this.swl - this.paq) * (var7 / (1.0F * var2)) * var8;
               this.ckc = var3;
               this.esd(Math.round(this.lxg));
            } else {
               var1.xwl = null;
            }
         } else if (var1.doj[0]) {
            var1.doj[0] = false;
            var1.xwl = this;
            this.ckc = var3;
            this.lxg = ((Integer)this.getValue()).intValue();
         }
      }
   }

   public IntegerSliderSetting kyc(Function<Integer, String> var1) {
      this.rae = var1;
      return this;
   }
}
