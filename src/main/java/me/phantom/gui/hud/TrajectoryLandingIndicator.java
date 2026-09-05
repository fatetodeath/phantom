package me.phantom.gui.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.visuals.TrajectoriesModule;
import me.phantom.util.GlyphTextUtil;

import org.lwjgl.nanovg.NanoVG;

public class TrajectoryLandingIndicator {
   private final float xtt;

   public TrajectoryLandingIndicator(TrajectoriesModule var1, float var2) {
      this.xtt = var2;
   }

   public void jjc(GuiRenderContext var1) {
      long var2 = var1.nvg;
      NanoVG.nvgFontFace(var2, GlyphTextUtil.encode("icons"));
      NanoVG.nvgFillColor(var2, GuiRenderContext.rgba(255, 0, 0, 255, GuiRenderContext.colorScratch));
      GlyphTextUtil.setTextAlign(var2, 18);
      NanoVG.nvgFontSize(var2, 22.0F);
      NanoVG.nvgSave(var2);
      NanoVG.nvgTranslate(var2, var1.framebufferWidth / 2, var1.framebufferHeight / 2);
      NanoVG.nvgRotate(var2, (float)Math.toRadians(this.xtt));
      NanoVG.nvgTranslate(var2, 30.0F, 0.0F);
      GlyphTextUtil.drawTextBuffer(var2, 20.0F, 0.0F, GuiRenderContext.iconBuffer(59230));
      NanoVG.nvgRotate(var2, (float)Math.toRadians(-this.xtt));
      NanoVG.nvgBeginPath(var2);
      NanoVG.nvgRoundedRect(var2, -10.0F, -10.0F, 20.0F, 20.0F, 3.0F);
      NanoVG.nvgFillColor(var2, GuiRenderContext.rgba(220, 10, 10, 150, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var2);
      NanoVG.nvgRestore(var2);
   }
}
