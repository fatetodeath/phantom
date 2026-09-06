package me.phantom.gui.hud;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.HudModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

public class LogoHudElement extends LabeledHudElement {
   public final String versionLabel = GlyphTextUtil.encode("Phantom ") + PhantomClient.version;

   public LogoHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public float yxe(GuiRenderContext var1) {
      return super.yxe(var1);
   }

   @Override
   public int uod() {
      return -1;
   }

   @Override
   public void fc(GuiRenderContext var1, float var2, float var3) {
      var2 -= 11.0F;
      var3 -= 11.0F;
      float var4 = 12.0F;
      long var5 = var1.nvg;
      NVGColor var7 = var1.accentColor;
      GuiRenderContext.colorScratch2.r(var7.r() / 2.0F);
      GuiRenderContext.colorScratch2.g(var7.g() / 2.0F);
      GuiRenderContext.colorScratch2.b(var7.b() / 2.0F);
      GuiRenderContext.colorScratch2.a(0.5F);

      for (int var8 = -1; var8 <= 1; var8++) {
         for (int var9 = -1; var9 <= 1; var9++) {
            NanoVG.nvgBeginPath(var5);
            NanoVG.nvgRect(var5, var2 + 5.0F + var8, var3 + 5.0F + var9, var4, var4);
            NanoVG.nvgImagePattern(var5, var2 + 5.0F + var8, var3 + 5.0F + var9, var4, var4, 0.0F, HudModule.tjj, 1.0F, GuiRenderContext.paintScratch);
            GuiRenderContext.paintScratch.innerColor(GuiRenderContext.colorScratch2);
            NanoVG.nvgFillPaint(var5, GuiRenderContext.paintScratch);
            NanoVG.nvgFill(var5);
         }
      }

      GuiRenderContext.colorScratch2.r(var7.r());
      GuiRenderContext.colorScratch2.g(var7.g());
      GuiRenderContext.colorScratch2.b(var7.b());
      GuiRenderContext.colorScratch2.a(1.0F);
      NanoVG.nvgBeginPath(var5);
      NanoVG.nvgRect(var5, var2 + 5.0F, var3 + 5.0F, var4, var4);
      NanoVG.nvgImagePattern(var5, var2 + 5.0F, var3 + 5.0F, var4, var4, 0.0F, HudModule.tjj, 1.0F, GuiRenderContext.paintScratch);
      GuiRenderContext.paintScratch.innerColor(GuiRenderContext.colorScratch2);
      NanoVG.nvgFillPaint(var5, GuiRenderContext.paintScratch);
      NanoVG.nvgFill(var5);
   }

   @Override
   public float icg(GuiRenderContext var1) {
      return super.icg(var1);
   }

   @Override
   public String oiuz(float var1) {
      return this.versionLabel;
   }
}
