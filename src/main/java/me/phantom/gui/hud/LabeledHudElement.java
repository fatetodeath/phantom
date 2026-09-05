package me.phantom.gui.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import java.nio.FloatBuffer;
import org.lwjgl.nanovg.NanoVG;

public abstract class LabeledHudElement extends HudElementBase {
   public LabeledHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public float icg(GuiRenderContext var1) {
      long var2 = var1.nvg;
      NanoVG.nvgFontFace(var2, InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(var2, 12.0F);
      GlyphTextUtil.setTextAlign(var2, 9);
      return GlyphTextUtil.measureText(var2, 0.0F, 0.0F, this.oiuz(var1.tickDelta), (FloatBuffer)null) + this.yxe(var1) + 10.0F + 3.0F;
   }

   @Override
   public float yxe(GuiRenderContext var1) {
      return 22.0F;
   }

   @Override
   public void jjc(GuiRenderContext var1) {
      this.net(var1);
      long var2 = var1.nvg;
      float var4 = this.yxe(var1);
      NanoVG.nvgBeginPath(var2);
      NanoVG.nvgRect(var2, var4, 0.0F, 1.0F, var4);
      NanoVG.nvgFillColor(var2, GuiRenderContext.rgba(120, 120, 120, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var2);
      this.fc(var1, 6.25F + var4 / 4.0F, var4 / 2.0F);
      this.tov(var1, 7.5F + var4, var4 / 2.0F + 1.0F, this.oiuz(var1.tickDelta));
   }

   @Override
   public void fc(GuiRenderContext var1, float var2, float var3) {
      long var4 = var1.nvg;
      int var6 = this.uod();
      NanoVG.nvgFontFace(var4, GlyphTextUtil.encode("icons"));
      NanoVG.nvgFontSize(var4, 12.0F);
      GlyphTextUtil.setTextAlign(var4, 18);
      NanoVG.nvgFillColor(var4, GuiRenderContext.rgba(100, 100, 100, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFontBlur(var4, 2.0F);
      GlyphTextUtil.drawTextBuffer(var4, var2, var3, GuiRenderContext.iconBuffer(var6));
      NanoVG.nvgFontBlur(var4, 0.0F);
      NanoVG.nvgFillColor(var4, GuiRenderContext.rgba(200, 200, 200, 220, GuiRenderContext.colorScratch));
      GlyphTextUtil.drawTextBuffer(var4, var2, var3, GuiRenderContext.iconBuffer(var6));
   }

   public abstract String oiuz(float var1);

   public abstract int uod();
}
