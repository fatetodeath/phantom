package me.phantom.render.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.module.visuals.TrajectoriesModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;

import java.nio.FloatBuffer;
import org.lwjgl.nanovg.NanoVG;

public class CountdownBadgeRenderer {
   public final int acae;
   private final float axn;
   public final float oaz;

   public CountdownBadgeRenderer(TrajectoriesModule var1, int var2, float var3, float var4) {
      this.acae = var2;
      this.axn = var3;
      this.oaz = var4;
   }

   public void jjc(GuiRenderContext var1) {
      float var2 = var1.tickDelta;
      float var3 = 22.0F;
      String var4 = RotationMathUtil.formatTwoDecimals((this.acae - var2) / 20.0F) + GlyphTextUtil.encode("s");
      long var5 = var1.nvg;
      NanoVG.nvgFontFace(var5, InterfaceModule.fontRegular);
      GlyphTextUtil.setTextAlign(var5, 9);
      NanoVG.nvgFontSize(var5, 22.0F);
      float var7 = GlyphTextUtil.measureText(var5, 0.0F, 0.0F, var4, (FloatBuffer)null);
      float var8 = var7 + 4.0F;
      float var9 = 26.0F;
      float var10 = this.axn - var7 / 2.0F - 2.0F;
      float var11 = this.oaz - 2.0F;
      NanoVG.nvgBeginPath(var5);
      NanoVG.nvgRoundedRect(var5, var10, var11, var8, var9, 3.0F);
      NanoVG.nvgFillColor(var5, GuiRenderContext.rgba(10, 10, 10, 150, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var5);
      NanoVG.nvgFillColor(var5, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
      GlyphTextUtil.drawText(var5, var10 + 2.0F, var11 + 4.0F, var4);
   }
}
