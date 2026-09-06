package me.phantom.gui.screen;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.setting.ItemCategorySetSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.ItemScoreCategory;

import java.util.Set;
import org.lwjgl.nanovg.NanoVG;

public class ItemCategoryPickerDialog extends AbstractDialog {
   private final ItemCategorySetSetting lwu;
   private float scrollOffset;

   public ItemCategoryPickerDialog(ItemCategorySetSetting var1) {
      super(GlyphTextUtil.encode("ItemESP"), null);
      this.lwu = var1;
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
      this.scrollOffset -= var2 * 30.0F;
   }

   @Override
   public void yww() {
   }

   @Override
   public int vgh(int var1, int var2) {
      return 500;
   }

   @Override
   public int zch(int var1, int var2) {
      return 700;
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      byte var6 = 40;
      int var7 = var2 - 10;
      int var8 = ((Set)this.lwu.getDefaultValue()).size() * (var6 + 5) - 5;
      this.scrollOffset = Math.max(0.0F, Math.min((float)(var8 - var3 + 5), this.scrollOffset));
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var2 - 8, 0.0F, 5.0F, var3, 2.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(30, 30, 30, 200, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      float var9 = 1.0F * var3 / var8 * var3;
      if (var3 < var8) {
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, var2 - 8, this.scrollOffset / (var8 - var3 + 10) * (var3 - var9), 5.0F, var9, 2.0F);
         NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, 0.65F, 230, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var1.nvg);
      }

      NanoVG.nvgScissor(var1.nvg, 0.0F, 0.0F, var2 - 10, var3);
      NanoVG.nvgTranslate(var1.nvg, 0.0F, -this.scrollOffset);
      var5 = (int)(var5 + this.scrollOffset);
      float var10 = var5 - var1.mouseY - this.scrollOffset;
      if (var10 >= var3 || var10 <= 0.0F) {
         var5 = -1000;
      }

      int var11 = 0;

      for (ItemScoreCategory var15 : ItemScoreCategory.qfo()) {
         int var16 = var11++ * (var6 + 5);
         if (var16 > this.scrollOffset - var3 && var16 <= this.scrollOffset + var3) {
            NanoVG.nvgTranslate(var1.nvg, 0.0F, var16);
            this.vbp(var1, var15, var4, var5 - var16, var7, var6);
            NanoVG.nvgTranslate(var1.nvg, 0.0F, -var16);
         }
      }

      NanoVG.nvgTranslate(var1.nvg, 0.0F, this.scrollOffset);
      NanoVG.nvgResetScissor(var1.nvg);
   }

   public void vbp(GuiRenderContext var1, ItemScoreCategory var2, int var3, int var4, int var5, int var6) {
      String var7 = PhantomClient.translationManager.ry(GlyphTextUtil.encode("category.name.") + var2.vr());
      boolean var8 = var1.isHovered(var3, var4, 0, 0, var5, var6);
      boolean var9 = ((Set)this.lwu.getValue()).contains(var2);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 1.0F, var5, var6 - 2, 4.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, var9 ? 0.6F : 0.2F, var8 ? 220 : 120, GuiRenderContext.colorScratch2));
      NanoVG.nvgFill(var1.nvg);
      var1.drawButton(var3, var4, GlyphTextUtil.encode(""), var6, var6, true, var9 ? 1.0F : 0.0F, var8 ? 1.0F : 0.0F);
      var1.drawTextAuto(48.0F, 10.0F, var7, 22.0F, false, 9, 0.0F, 1.0F);
      PhantomClient.uiManager.sdd().tzk(var2.getStack(), 4.0F, 4.0F, 32.0F, 32.0F, 1.0F);
      if (var8 && var1.doj[0]) {
         if (((Set)this.lwu.getValue()).contains(var2)) {
            ((Set)this.lwu.getValue()).remove(var2);
         } else {
            ((Set)this.lwu.getValue()).add(var2);
         }

         var1.doj[0] = false;
      }
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      return super.handleKey(var1, var2, var3, var4);
   }

   @Override
   public void na(char var1, int var2) {
   }
}
