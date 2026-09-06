package me.phantom.gui.screen;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.module.misc.NameProtectModule;
import me.phantom.setting.StringSetSetting;
import me.phantom.util.GlyphTextUtil;

import java.util.Set;
import org.lwjgl.nanovg.NanoVG;

public class StringSetEditorDialog extends AbstractDialog {
   public final StringSetSetting mun;
   public float scrollOffset;
   private StringBuilder orx = new StringBuilder();

   public StringSetEditorDialog(String var1, StringSetSetting var2) {
      super(var1, null);
      this.mun = var2;
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
      long var6 = var1.nvg;
      float var8 = 5.0F;
      float var9 = 30.0F;
      float var10 = (((Set)this.mun.getValue()).size() + 1) * 35.0F;
      this.scrollOffset = Math.max(0.0F, Math.min(var10 - var3 + 5.0F, this.scrollOffset));
      float var11 = 1.0F * var3 / var10 * var3;
      if (var3 < var10) {
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, var2 - 5.0F - 2.0F, this.scrollOffset / (var10 - var3 + 5.0F) * (var3 - var11), 5.0F, var11, 2.0F);
         NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, 0.65F, 230, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var1.nvg);
      }

      NanoVG.nvgScissor(var1.nvg, 0.0F, 0.0F, var2 - 10, var3);
      NanoVG.nvgTranslate(var1.nvg, 0.0F, -this.scrollOffset);
      var5 = (int)(var5 - this.scrollOffset);
      float var12 = 5.0F;
      NanoVG.nvgFontSize(var6, 20.0F);
      var1.translate(5, (int)var12);
      NanoVG.nvgBeginPath(var6);
      NanoVG.nvgRect(var6, 0.0F, 0.0F, var2 - 15.0F, 30.0F);
      NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(20, 20, 20, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var6);
      boolean var13 = true;
      int var14 = var13 ? 255 : 220;
      float var15 = var2 - 40.0F;
      NanoVG.nvgFontFace(var6, InterfaceModule.fontRegular);
      NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(var14, var14, var14, var14, GuiRenderContext.colorScratch));
      GlyphTextUtil.setTextAlign(var6, 18);
      String var16 = this.orx.length() <= 0
         ? GlyphTextUtil.encode("Add new")
         : this.orx.toString() + (var13 && System.currentTimeMillis() % 1000L > 500L ? GlyphTextUtil.encode("|") : GlyphTextUtil.encode(""));
      GlyphTextUtil.drawText(var6, 5.0F + var15 / 2.0F, 15.0F, var16);
      var13 = var1.isHoveredF(var4, var5, var2 - 40.0F, 0.0F, 30.0F, 30.0F);
      var14 = var13 ? 255 : 220;
      NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(var14, var14, var14, var14, GuiRenderContext.colorScratch));
      NanoVG.nvgFontFace(var6, GlyphTextUtil.encode("icons"));
      GlyphTextUtil.drawTextBuffer(var6, var2 - 30.0F, 15.0F, GuiRenderContext.iconBuffer(8862));
      if (var13 && var1.doj[0]) {
         if (this.orx.length() > 0) {
            this.mun.swh(this.orx.toString());
            this.orx.setLength(0);
         }

         var1.doj[0] = false;
      }

      var1.translate(-5, (int)(-var12));
      var12 += 35.0F;
      String var17 = null;

      for (String var19 : this.mun.getValue()) {
         if (var12 > this.scrollOffset - var3 && var12 <= this.scrollOffset + var3) {
            var1.translate(5, (int)var12);
            NanoVG.nvgBeginPath(var6);
            NanoVG.nvgRect(var6, 0.0F, 0.0F, var2 - 15.0F, 30.0F);
            NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(20, 20, 20, 220, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var6);
            var13 = var1.isHoveredF(var4, var5, 0.0F, 0.0F, var2 - 40.0F, 30.0F);
            var14 = var13 ? 255 : 220;
            NanoVG.nvgFontFace(var6, InterfaceModule.fontRegular);
            GlyphTextUtil.setTextAlign(var6, 18);
            NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(var14, var14, var14, var14, GuiRenderContext.colorScratch));
            GlyphTextUtil.drawText(var6, 5.0F + var15 / 2.0F, 15.0F, var19);
            if (var13 && var1.doj[0]) {
               this.orx.setLength(0);
               var1.doj[0] = false;
            }

            var13 = var1.isHoveredF(var4, var5, var2 - 40.0F, 0.0F, 30.0F, 30.0F);
            var14 = var13 ? 255 : 220;
            NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(var14, var14, var14, var14, GuiRenderContext.colorScratch));
            if (var13 && var1.doj[0]) {
               var17 = var19.toLowerCase();
               var1.doj[0] = false;
            }

            NanoVG.nvgFontFace(var6, GlyphTextUtil.encode("icons"));
            GlyphTextUtil.drawTextBuffer(var6, var2 - 30.0F, 15.0F, GuiRenderContext.iconBuffer(59177));
            var1.translate(-5, (int)(-var12));
         }

         var12 += 35.0F;
      }

      if (var17 != null) {
         ((Set)this.mun.getValue()).remove(var17);
         if (NameProtectModule.zj.isEnabled()) {
            NameProtectModule.zj.hhk();
         }

         PhantomClient.configManager.fl();
      }

      if (var1.doj[0]) {
         var1.doj[0] = false;
      }

      NanoVG.nvgTranslate(var1.nvg, 0.0F, this.scrollOffset);
      NanoVG.nvgResetScissor(var1.nvg);
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
      this.scrollOffset -= var2 * 15.0F;
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      switch (var2) {
         case 257:
            if (this.orx.length() > 0) {
               this.mun.swh(this.orx.toString());
               PhantomClient.configManager.fl();
               this.orx.setLength(0);
            }
            break;
         case 259:
            if (var4 >= 1 && this.orx.length() > 0) {
               this.orx.setLength(this.orx.length() - 1);
            }
      }

      return super.handleKey(var1, var2, var3, var4);
   }

   @Override
   public void na(char var1, int var2) {
      if (this.orx.length() <= 24) {
         this.orx.append(var1);
      }
   }
}
