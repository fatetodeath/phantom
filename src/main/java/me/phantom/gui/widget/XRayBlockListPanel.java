package me.phantom.gui.widget;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.AbstractDialog;
import me.phantom.setting.ChoiceListSetting;
import me.phantom.util.GlyphTextUtil;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.lwjgl.nanovg.NanoVG;

public class XRayBlockListPanel extends AbstractDialog {
   private final ChoiceListSetting prq;
   public float scrollOffset;

   public XRayBlockListPanel(ChoiceListSetting var1) {
      super(GlyphTextUtil.encode("XRay Blocks"), null);
      this.prq = var1;
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
      this.scrollOffset -= var2 * 30.0F;
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
      int var8 = ((List)this.prq.getDefaultValue()).size() * (var6 + 5) - 5;
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

      for (int var11 = 0; var11 < ((List)this.prq.getDefaultValue()).size(); var11++) {
         String var12 = (String)((List)this.prq.getDefaultValue()).get(var11);
         int var13 = var11 * (var6 + 5);
         if (var13 > this.scrollOffset - var3 && var13 <= this.scrollOffset + var3) {
            NanoVG.nvgTranslate(var1.nvg, 0.0F, var13);
            this.zsz(var1, var12, var4, var5 - var13, var7, var6);
            NanoVG.nvgTranslate(var1.nvg, 0.0F, -var13);
         }
      }

      NanoVG.nvgTranslate(var1.nvg, 0.0F, this.scrollOffset);
      NanoVG.nvgResetScissor(var1.nvg);
   }

   private void zsz(GuiRenderContext var1, String var2, int var3, int var4, int var5, int var6) {
      String var7 = I18n.translate(GlyphTextUtil.plain("block.") + GlyphTextUtil.decode(var2).replace(GlyphTextUtil.plain(":"), GlyphTextUtil.plain(".")), new Object[0]);
      boolean var8 = var1.isHovered(var3, var4, 0, 0, var5, var6);
      boolean var9 = ((List)this.prq.getValue()).contains(var2);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 1.0F, var5, var6 - 2, 4.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, var9 ? 0.6F : 0.2F, var8 ? 220 : 120, GuiRenderContext.colorScratch2));
      NanoVG.nvgFill(var1.nvg);
      var1.drawButton(var3, var4, GlyphTextUtil.encode(""), var6, var6, true, ((List)this.prq.getValue()).contains(var2) ? 1.0F : 0.0F, var8 ? 1.0F : 0.0F);
      var1.drawTextAuto(48.0F, 10.0F, var7, 22.0F, false, 9, 0.0F, 1.0F);

      try {
         PhantomClient.uiManager
            .sdd()
            .tzk(
               new ItemStack((ItemConvertible)Registries.BLOCK.get(Identifier.ofVanilla(GlyphTextUtil.decode(var2.substring(GlyphTextUtil.encode("minecraft:").length()))))),
               4.0F,
               4.0F,
               32.0F,
               32.0F,
               1.0F
            );
      } catch (Throwable var11) {
      }

      var1.drawIconText(var5 - 20, 20, var9 ? 10003 : 10005, 22.0F, false, 18, 0.0F, 1.0F);
      if (var8 && var1.doj[0]) {
         if (((List)this.prq.getValue()).contains(var2)) {
            ((List)this.prq.getValue()).remove(var2);
         } else {
            ((List)this.prq.getValue()).add(var2);
         }

         if (PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("XRay")).isEnabled() && MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().worldRenderer.reload();
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
