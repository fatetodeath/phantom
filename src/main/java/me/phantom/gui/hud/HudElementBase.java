package me.phantom.gui.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public abstract class HudElementBase {
   public final String lbb;

   public HudElementBase(JsonObject var1) {
      this.lbb = var1.get(GlyphTextUtil.encode("type")).getAsString();
   }

   public JsonObject qr() {
      JsonObject var1 = new JsonObject();
      var1.addProperty(GlyphTextUtil.encode("type"), this.lbb);
      return var1;
   }

   public void net(GuiRenderContext var1) {
      float var2 = this.icg(var1);
      float var3 = this.yxe(var1);
      long var4 = var1.nvg;
      NanoVG.nvgFontFace(var4, InterfaceModule.fontBold);
      NanoVG.nvgFontSize(var4, var3 - 8.0F);
      GlyphTextUtil.setTextAlign(var4, 9);
      NVGPaint var6 = NanoVG.nvgBoxGradient(var4, 0.0F, 0.0F, var2, var3, 12.0F, 7.5F, GuiRenderContext.rgba(0, 0, 0, 120, GuiRenderContext.colorScratch), GuiRenderContext.rgba(0, 0, 0, 0, GuiRenderContext.colorScratch2), GuiRenderContext.paintScratch);
      NanoVG.nvgBeginPath(var4);
      NanoVG.nvgRect(var4, -7.5F, -7.5F, var2 + 15.0F, var3 + 15.0F);
      NanoVG.nvgRoundedRect(var4, 0.0F, 0.0F, var2, var3, 6.0F);
      NanoVG.nvgPathWinding(var4, 2);
      NanoVG.nvgFillPaint(var4, var6);
      NanoVG.nvgFill(var4);
      NanoVG.nvgBeginPath(var4);
      NanoVG.nvgRoundedRect(var4, 0.0F, 0.0F, var2, var3, 6.0F);
      NanoVG.nvgFillColor(var4, GuiRenderContext.rgba(20, 20, 20, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var4);
   }

   public abstract void fc(GuiRenderContext var1, float var2, float var3);

   public void tov(GuiRenderContext var1, float var2, float var3, String var4) {
      this.vkw(var1, var2, var3, var4, 17);
   }

   public void vkw(GuiRenderContext var1, float var2, float var3, String var4, int var5) {
      long var6 = var1.nvg;
      NanoVG.nvgFontFace(var6, InterfaceModule.fontRegular);
      GlyphTextUtil.setTextAlign(var6, var5);
      NanoVG.nvgFontSize(var6, 12.0F);
      NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(100, 100, 100, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFontBlur(var6, 2.0F);
      GlyphTextUtil.drawText(var6, var2, var3, var4);
      NanoVG.nvgFontBlur(var6, 0.0F);
      NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(200, 200, 200, 220, GuiRenderContext.colorScratch));
      GlyphTextUtil.drawText(var6, var2, var3, var4);
   }

   public abstract void jjc(GuiRenderContext var1);

   public abstract float icg(GuiRenderContext var1);

   public abstract float yxe(GuiRenderContext var1);
}
