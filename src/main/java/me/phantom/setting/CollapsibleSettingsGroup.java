package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.EasedAnimation;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.JsonUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.List;
import org.lwjgl.nanovg.NanoVG;

public class CollapsibleSettingsGroup extends AbstractSetting<Boolean> {
   public final EasedAnimation env = new EasedAnimation(false);
   private final EasedAnimation ix = new EasedAnimation(false);
   public final EasedAnimation epa = new EasedAnimation(false);
   private final EasedAnimation aja = new EasedAnimation(false);
   private final List<AbstractSetting<?>> iah;
   public boolean rqn;

   public CollapsibleSettingsGroup(BaseModule var1, String var2, List<AbstractSetting<?>> var3, boolean var4) {
      super(var1, var2, var4);
      this.iah = var3;

      for (AbstractSetting<?> var6 : var3) {
         var6.setGroup(this);
      }
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      float var7 = this.getValue() ? this.env.hee() : 1.0F - this.ix.hee();
      boolean var8 = var1.isHovered(var3, var4, 0, 0, var2, 24);
      if (var1.drawButton(var3, var4, this.getDisplayName(), var2, 24, true, var7, var8 ? 1.0F : 0.0F)) {
         var1.setTooltip(this.getDescription());
         if (var1.doj[0]) {
            this.bdl(!(Boolean)this.getValue());
            var1.doj[0] = false;
         }

         if (var1.doj[1]) {
            for (AbstractSetting<?> var10 : this.module.getSettings()) {
               if (var10 instanceof CollapsibleSettingsGroup var11 && !this.equals(var10) && var11.rqn) {
                  var11.rqn = false;
                  var11.aja.qou(150);
               }
            }

            this.rqn = !this.rqn;
            if (this.rqn) {
               this.epa.qou(150);
            } else {
               this.aja.qou(150);
            }

            var1.doj[1] = false;
         }
      }

      float var23 = this.rqn ? this.epa.hee() : 1.0F - this.aja.hee();
      long var24 = var1.nvg;
      byte var12 = 12;
      byte var13 = 10;
      int var14 = Math.round(5.0F - var23 * 5.0F);
      NanoVG.nvgBeginPath(var24);
      NanoVG.nvgMoveTo(var24, var12 - 5, var13 + var14);
      NanoVG.nvgLineTo(var24, var12, var13 + 5 - var14);
      NanoVG.nvgLineTo(var24, var12 + 5, var13 + var14);
      int var15 = (int)(var23 * 120.0F + 120.0F);
      NanoVG.nvgStrokeColor(var24, GuiRenderContext.rgba(var15, var15, var15, var15, GuiRenderContext.colorScratch));
      NanoVG.nvgStroke(var24);
      boolean var16 = this.epa.sdx() || this.aja.sdx();
      int var17 = this.getHeight();
      if (var16) {
         NanoVG.nvgScissor(var1.nvg, -1000.0F, 0.0F, 2000.0F, var17 - 3);
      }

      int var18 = 0;
      if (this.rqn || var16) {
         byte var19 = 8;
         var18 += 32;
         var1.translate(var19, var18);

         for (AbstractSetting<?> var21 : this.iah) {
            if (var21.visibilitySupplier == null || var21.visibilitySupplier.get()) {
               int var22 = var21.getHeight();
               if (var1.xwl != null && !var1.xwl.equals(var21)) {
                  var21.renderSetting(var1, var2 - var19 * 2, -1, -1, var1.mzx, var1.mzx);
               } else {
                  var21.renderSetting(var1, var2 - var19 * 2, var3, var4, var5, var6);
               }

               var18 += var22;
               var1.translate(0, var22);
            }
         }

         var18 += 4;
         var1.translate(-var19, 4);
         var1.translate(0, -var18);
         if (var16) {
            NanoVG.nvgResetScissor(var1.nvg);
         }
      }
   }

   public void bdl(Boolean var1) {
      if (var1 != this.getValue()) {
         if (var1) {
            this.env.qou(300);
         } else {
            this.ix.qou(300);
         }
      }

      super.setValue(var1);
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.bdl(JsonUtils.uzc(var1));
   }

   @Override
   public JsonElement toJson() {
      return new JsonPrimitive(String.valueOf(this.getValue()));
   }

   @Override
   public int getHeight() {
      int var1 = 30;
      if (this.rqn || this.aja.sdx() || this.epa.sdx()) {
         for (AbstractSetting<?> var3 : this.iah) {
            if (var3.cachedLabel.contains(GlyphTextUtil.encode("Hurt")) && var3.visibilitySupplier == null) {
            }

            if (var3.visibilitySupplier == null || var3.visibilitySupplier.get()) {
               var1 += var3.getHeight();
            }
         }

         var1 += 4;
         if (this.epa.sdx() || this.aja.sdx()) {
            var1 = 30 + (int)((var1 - 30) * (this.rqn ? this.epa.hee() : 1.0F - this.aja.hee()));
         }
      }

      return var1;
   }
}
