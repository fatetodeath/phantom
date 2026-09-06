package me.phantom.gui.screen;

import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import org.lwjgl.nanovg.NanoVG;

public class ModuleSearchDialog extends AbstractDialog {
   public int too;
   public String ydp = GlyphTextUtil.encode("");
   public boolean fot;

   public ModuleSearchDialog(boolean var1) {
      super(GlyphTextUtil.encode("Configs"), null);
      this.fot = var1;
      this.rtz(0);
   }

   @Override
   public void fiv() {
      if (this.fot) {
         PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("UI")).setEnabled(false, true);
         Minecraft.getInstance().setScreen(null);
      } else {
         super.fiv();
      }
   }

   public void rtz(int var1) {
      this.too = var1;
   }

   @Override
   public void yww() {
      this.rtz(this.too);
   }

   @Override
   public int vgh(int var1, int var2) {
      int var3 = (int)(var2 * 0.9F);
      return var3 / 9 * 16;
   }

   @Override
   public int zch(int var1, int var2) {
      return (int)(var2 * 0.8F);
   }

   @Override
   public void na(char var1, int var2) {
      if (this.ydp.length() < 24
         && (Character.isDigit(var1) || var1 >= 'a' && var1 <= 'z' || var1 >= 'A' && var1 <= 'Z' || GlyphTextUtil.encode("_()[].,/äöüÄÖÜ").contains(GlyphTextUtil.encode("") + GlyphTextUtil.encodeChar(var1)))) {
         this.ydp = this.ydp + GlyphTextUtil.encodeChar(var1);
      }
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      short var6 = 160;
      byte var7 = 0;
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 0.0F, var6 + 10, var3, 5.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.panelColor, 0.85F, 230, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var6 + 12, 5.0F, var2 - var6 - 16, 20.0F, 3.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, 0.3F, 230, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      if (!this.ydp.isEmpty()) {
         GlyphTextUtil.setTextAlign(var1.nvg, 9);
         NanoVG.nvgFontBlur(var1.nvg, 2.0F);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(0, 0, 0, 128, GuiRenderContext.colorScratch));
         GlyphTextUtil.drawText(var1.nvg, var6 + 16, 7.0F, this.ydp);
         NanoVG.nvgFontBlur(var1.nvg, 0.0F);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(220, 220, 220, 160, GuiRenderContext.colorScratch));
         GlyphTextUtil.drawText(var1.nvg, var6 + 16, 7.0F, this.ydp);
      }

      int var8 = -1;
      int var9 = 0;

      for (ModuleCategory var13 : ModuleCategory.nku()) {
         var1.translate(5, var7);
         boolean var14 = var1.isHovered(var4, var5, 0, 0, var6, 28);
         if (var1.drawButton(var4, var5, GlyphTextUtil.encode(" ") + var13.getName(), var6, 28, false, this.too == var9 ? 1.0F : 0.0F, var14 ? 1.0F : 0.0F) && var1.doj[0]) {
            var8 = var9;
         }

         var1.drawIconGlyph(var1, GuiRenderContext.iconBuffer(var13.zs()), 8, 15);
         var1.translate(-5, -var7);
         var7 += 32;
         var9++;
      }

      if (var8 != -1) {
         this.rtz(var8);
      }

      var1.translate(var6 + 10, 32);
      var1.translate(-var6 - 10, -32);
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
   }

   public boolean lts(String var1, String var2) {
      return var2.isEmpty() || GlyphTextUtil.lowercase(var1).contains(GlyphTextUtil.lowercase(var2)) || GlyphTextUtil.lowercase(var2).contains(GlyphTextUtil.lowercase(var1));
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      switch (var2) {
         case 259:
            if (this.ydp.length() > 0 && var4 >= 1) {
               this.ydp = this.ydp.substring(0, this.ydp.length() - 1);
            }
         default:
            return super.handleKey(var1, var2, var3, var4);
      }
   }
}
