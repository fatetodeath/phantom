package me.phantom.render.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.visuals.TntDetectorModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;

import java.nio.FloatBuffer;
import org.lwjgl.nanovg.NanoVG;

public final class CountdownLabelRenderer {
   final TntDetectorModule yym;
   final float axn;
   final float oaz;
   final int ticksRemaining;

   public CountdownLabelRenderer(TntDetectorModule var1, float var2, float var3, int var4) {
      this.yym = var1;
      this.axn = var2;
      this.oaz = var3;
      this.ticksRemaining = var4;
   }

   public void jjc(GuiRenderContext var1) {
      float var2 = var1.tickDelta;
      String var3 = RotationMathUtil.formatTwoDecimals((this.ticksRemaining - var2) / 20.0F) + GlyphTextUtil.encode("s");
      float var4 = GlyphTextUtil.measureText(var1.nvg, 0.0F, 0.0F, var3, (FloatBuffer)null);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, this.axn - var4 / 2.0F - 2.0F, this.oaz - 2.0F, var4 + 4.0F, 26.0F, 3.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(10, 10, 10, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      float var5 = Math.min(1.0F, Math.max(0.0F, this.ticksRemaining / 80.0F));
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, this.axn - var4 / 2.0F - 2.0F, this.oaz - 2.0F + 26.0F * var5, var4 + 4.0F, 26.0F * (1.0F - var5), 3.0F);
      NanoVG.nvgFillColor(var1.nvg, this.yym.imc.xam(1.0F, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
      GlyphTextUtil.drawText(var1.nvg, this.axn - var4 / 2.0F, this.oaz + 2.0F, var3);
   }
}
