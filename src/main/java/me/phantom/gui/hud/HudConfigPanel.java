package me.phantom.gui.hud;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.AbstractDialog;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.setting.HudElementsSetting;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import java.util.Map.Entry;
import java.util.function.Function;
import org.lwjgl.nanovg.NanoVG;

public class HudConfigPanel extends AbstractDialog {
   public final HudElementsSetting vvt;
   private HudElementBase qnm;

   public HudConfigPanel(HudElementsSetting var1) {
      super(GlyphTextUtil.encode("Hud Configuration"), null);
      this.vvt = var1;
   }

   @Override
   public void fiv() {
      super.fiv();
   }

   @Override
   public void yww() {
   }

   @Override
   public int vgh(int var1, int var2) {
      return (int)(var1 * 0.6F);
   }

   @Override
   public int zch(int var1, int var2) {
      return this.vgh(var1, var2) / 16 * 9;
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      GlyphTextUtil.setTextAlign(var1.nvg, 10);
      byte var6 = 33;
      int var7 = HudElementsSetting.sfw.size() * var6;
      int var8 = var3 / 2 - var7 / 2 - 14;
      short var9 = 200;
      byte var10 = 10;
      float var11 = 32.0F;
      this.nmm(var1, var4, var5, 0.0F, 0.0F, var2 / 3, 32.0F, HudAnchor.obq);
      this.nmm(var1, var4, var5, var2 / 3, 0.0F, var2 / 3, 32.0F, HudAnchor.vgm);
      this.nmm(var1, var4, var5, var2 / 3 * 2, 0.0F, var2 / 3, 32.0F, HudAnchor.cns);
      this.nmm(var1, var4, var5, 0.0F, var3 / 2 - var3 / 6, 32.0F, var3 / 3, HudAnchor.pwh);
      this.nmm(var1, var4, var5, var2 - 32.0F, var3 / 2 - var3 / 6, 32.0F, var3 / 3, HudAnchor.qrx);
      this.nmm(var1, var4, var5, 0.0F, var3 - 32.0F, var2 / 3, 32.0F, HudAnchor.rgc);
      this.nmm(var1, var4, var5, var2 / 3, var3 - 32.0F, var2 / 3, 32.0F, HudAnchor.uov);
      this.nmm(var1, var4, var5, var2 / 3 * 2, var3 - 32.0F, var2 / 3, 32.0F, HudAnchor.aio);
      NanoVG.nvgStrokeWidth(var1.nvg, 2.0F);
      NanoVG.nvgStrokeColor(var1.nvg, GuiRenderContext.rgba(255, 0, 0, 120, GuiRenderContext.colorScratch));
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgMoveTo(var1.nvg, var2 / 2 - 10, var3 / 2);
      NanoVG.nvgLineTo(var1.nvg, var2 / 2 + 10, var3 / 2);
      NanoVG.nvgStroke(var1.nvg);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgMoveTo(var1.nvg, var2 / 2, var3 / 2 - 10);
      NanoVG.nvgLineTo(var1.nvg, var2 / 2, var3 / 2 + 10);
      NanoVG.nvgStroke(var1.nvg);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var2 + 7 - 3, var8 + 1 - 3, var9 - 2 + 6, var7 + 6, 5.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(15, 15, 15, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);

      for (Entry var13 : HudElementsSetting.sfw.entrySet()) {
         String var14 = PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.hud.element.") + (String)var13.getKey());
         boolean var15 = var1.isHovered(var4, var5, var2 + 6, var8 + 1, var9 - 2, var6 - 2);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(15, 15, 15, 220, GuiRenderContext.colorScratch));
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, var2 + 6 + 1, var8 + 1, var9 - 2, var6 - 2 - 2, 5.0F);
         NanoVG.nvgFill(var1.nvg);
         if (var15) {
            NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(45, 45, 45, 180, GuiRenderContext.colorScratch));
            NanoVG.nvgBeginPath(var1.nvg);
            NanoVG.nvgRoundedRect(var1.nvg, var2 + 6 + 1, var8 + 1, var9 - 2, var6 - 2 - 2, 5.0F);
            NanoVG.nvgFill(var1.nvg);
            if (var1.doj[0]) {
               JsonObject var16 = new JsonObject();
               var16.addProperty(GlyphTextUtil.encode("type"), (String)var13.getKey());
               this.qnm = (HudElementBase)((Function)var13.getValue()).apply(var16);
               var1.doj[0] = false;
               this.vvt.notifyConfigChanged();
            }
         }

         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(215, 215, 215, 220, GuiRenderContext.colorScratch));
         NanoVG.nvgFontSize(var1.nvg, 16.0F);
         GlyphTextUtil.setTextAlign(var1.nvg, 9);
         NanoVG.nvgFontFace(var1.nvg, GlyphTextUtil.encode("icons"));
         GlyphTextUtil.drawTextBuffer(var1.nvg, var2 + 16, var8 + 9, GuiRenderContext.iconBuffer(8862));
         GlyphTextUtil.setTextAlign(var1.nvg, 10);
         NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontRegular);
         GlyphTextUtil.drawText(var1.nvg, var2 + 12 + var9 / 2, var8 + 9, var14);
         var8 += var6;
      }
   }

   public void nmm(GuiRenderContext var1, float var2, float var3, float var4, float var5, float var6, float var7, HudAnchor var8) {
      byte var9 = 7;
      boolean var10 = var8 == HudAnchor.pwh || var8 == HudAnchor.qrx;
      boolean var11 = this.qnm != null && var1.isHoveredF(var2, var3, var4, var5, var6, var7);
      int var12 = var11 ? 220 : 80;
      int var13 = var12;
      int var14 = var12;
      int var15 = var12;
      if (var11 && var8.sm.size() >= var9) {
         var13 = var12;
         var14 = var12 / 3;
         var15 = var12 / 3;
      }

      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRect(var1.nvg, var4, var5, var6, var7);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var13, var14, var15, 255, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRect(var1.nvg, var4 + 1.0F, var5 + 1.0F, var6 - 2.0F, var7 - 2.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var13 / 2, var14 / 2, var15 / 2, 255, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      float var16 = Math.min(var6, var7) - 10.0F;
      if (this.qnm != null && var11) {
         float var23 = var4 + 5.0F;
         float var24 = var5 + 5.0F;
         int var25 = -1;

         for (int var26 = 0; var26 < var8.sm.size(); var26++) {
            HudElementBase var27 = var8.sm.get(var26);
            boolean var28 = this.qnm == null && var1.isHoveredF(var2, var3, var23, var24, var16, var16);
            if (var10) {
               if (var3 - var1.mouseY >= var24 - var16 / 2.0F && var3 - var1.mouseY <= var24 + var16 / 2.0F) {
                  NanoVG.nvgBeginPath(var1.nvg);
                  NanoVG.nvgRect(var1.nvg, var4 + 1.0F, var24 + var16 / 4.0F - 2.0F, var6 - 2.0F, 1.0F);
                  NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(220, 20, 20, 255, GuiRenderContext.colorScratch));
                  NanoVG.nvgFill(var1.nvg);
                  var24 += var16 / 2.0F;
                  var25 = var26;
               }
            } else if (var2 - var1.mouseX >= var23 - var16 / 2.0F && var2 - var1.mouseX <= var23 + var16 / 2.0F) {
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRect(var1.nvg, var23 + var16 / 4.0F - 2.0F, var5 + 1.0F, 1.0F, var7 - 2.0F);
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(220, 20, 20, 255, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var1.nvg);
               var23 += var16 / 2.0F;
               var25 = var26;
            }

            this.wal(var1, var27, var23, var24, var16, var28);
            if (var10) {
               var24 += var16 + 5.0F;
            } else {
               var23 += var16 + 5.0F;
            }
         }

         if (var25 == -1) {
            if (var10) {
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRect(var1.nvg, var4 + 1.0F, var24 + var16 / 4.0F - 2.0F, var6 - 2.0F, 1.0F);
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(220, 20, 20, 255, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var1.nvg);
            } else {
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRect(var1.nvg, var23 + var16 / 4.0F - 2.0F, var5 + 1.0F, 1.0F, var7 - 2.0F);
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(220, 20, 20, 255, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var1.nvg);
            }
         }

         if (!var1.xfy[0]) {
            if (var8.sm.size() < var9) {
               if (var25 == -1) {
                  var8.sm.add(this.qnm);
               } else {
                  var8.sm.add(var25, this.qnm);
               }
            }

            this.qnm = null;
            this.vvt.notifyConfigChanged();
         }
      } else {
         float var17 = var4 + 5.0F;
         float var18 = var5 + 5.0F;
         HudElementBase var19 = null;

         for (HudElementBase var21 : var8.sm) {
            boolean var22 = this.qnm == null && var1.isHoveredF(var2, var3, var17, var18, var16, var16);
            this.wal(var1, var21, var17, var18, var16, var22);
            if (var22) {
               if (var1.doj[0]) {
                  var1.doj[0] = false;
                  var19 = var21;
               }

               var1.tooltipText = PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.hud.element.") + var21.lbb);
            }

            if (var10) {
               var18 += var16 + 5.0F;
            } else {
               var17 += var16 + 5.0F;
            }
         }

         if (var19 != null) {
            this.qnm = var19;
            var8.sm.remove(var19);
         }
      }
   }

   @Override
   public void eau(GuiRenderContext var1, int var2, int var3) {
      if (this.qnm != null) {
         if (var1.xfy[0]) {
            float var4 = 32.0F;
            float var5 = var1.screenWidth - var4 / 2.0F;
            float var6 = var1.screenHeight + 20;
            this.wal(var1, this.qnm, var5, var6, var4, true);
            NanoVG.nvgRestore(var1.nvg);
         } else {
            this.qnm = null;
         }
      }
   }

   private void wal(GuiRenderContext var1, HudElementBase var2, float var3, float var4, float var5, boolean var6) {
      int var7 = var6 ? 120 : 80;
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var3, var4, var5, var5, 6.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var7, var7, var7, 255, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgStrokeWidth(var1.nvg, 1.0F);
      NanoVG.nvgStrokeColor(var1.nvg, GuiRenderContext.rgba(var7, var7, var7, 255, GuiRenderContext.colorScratch));
      NanoVG.nvgStroke(var1.nvg);
      var2.fc(var1, var3 + var5 / 2.0F, var4 + var5 / 2.0F);
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      return super.handleKey(var1, var2, var3, var4);
   }

   @Override
   public void na(char var1, int var2) {
   }

}
