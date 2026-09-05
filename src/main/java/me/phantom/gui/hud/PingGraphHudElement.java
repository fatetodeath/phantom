package me.phantom.gui.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.module.network.PingMeasurementModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.net.PingSample;

import com.google.gson.JsonObject;
import java.nio.FloatBuffer;
import java.util.List;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public class PingGraphHudElement extends LabeledHudElement {
   public PingGraphHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public void jjc(GuiRenderContext var1) {
      this.net(var1);
      long var2 = var1.nvg;
      float var4 = this.icg(var1);
      float var5 = this.yxe(var1);
      NanoVG.nvgBeginPath(var2);
      NanoVG.nvgRect(var2, var5, 0.0F, 1.0F, var5);
      NanoVG.nvgFillColor(var2, GuiRenderContext.rgba(120, 120, 120, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var2);
      this.fc(var1, 6.25F + var5 / 4.0F, var5 / 2.0F);
      this.vkw(var1, var5 + (var4 - var5) / 2.0F, var5 / 2.0F + 1.0F, this.oiuz(var1.tickDelta), 18);
   }

   @Override
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

      try {
         List var7 = PingMeasurementModule.xhh();
         if (!var7.isEmpty()) {
            long var8 = Long.MAX_VALUE;
            long var10 = Long.MIN_VALUE;
            int var12 = Integer.MAX_VALUE;
            int var13 = Integer.MIN_VALUE;

            for (int var14 = 0; var14 < var7.size(); var14++) {
               PingSample var15 = (PingSample)var7.get(var14);
               var8 = Math.min(var8, var15.getTime());
               var10 = Math.max(var10, var15.getTime());
               var12 = Math.min(var12, var15.getPing());
               var13 = Math.max(var13, var15.getPing());
            }

            long var22 = var10 - var8;
            int var16 = var13 - var12;
            NanoVG.nvgBeginPath(var4);

            for (int var17 = 0; var17 < var7.size(); var17++) {
               PingSample var18 = (PingSample)var7.get(var17);
               float var19 = (float)(var18.getTime() - var8) / (1.0F * (float)var22) * (var2 - 6.0F - var3) + 3.0F + var3;
               float var20 = ((var18.getPing() - var12) / (1.0F * var16) * 0.6F + 0.2F) * var3;
               if (var17 == 0) {
                  NanoVG.nvgMoveTo(var4, var19, var20);
               } else {
                  NanoVG.nvgLineTo(var4, var19, var20);
               }
            }

            NanoVG.nvgFillColor(var4, var1.shadeColor(var1.accentColor, 0.5F, 50, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var4);
            NanoVG.nvgStrokeColor(var4, var1.shadeColor(var1.accentColor, 0.7F, 80, GuiRenderContext.colorScratch));
            NanoVG.nvgStroke(var4);
         }
      } catch (Throwable var21) {
      }
   }

   @Override
   public float icg(GuiRenderContext var1) {
      long var2 = var1.nvg;
      NanoVG.nvgFontFace(var2, InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(var2, 12.0F);
      GlyphTextUtil.setTextAlign(var2, 9);
      return GlyphTextUtil.measureText(var2, 0.0F, 0.0F, GlyphTextUtil.encode("100 ms"), (FloatBuffer)null) + this.yxe(var1) + 10.0F + 3.0F + (PingMeasurementModule.bsd.isEnabled() && !PingMeasurementModule.xhh().isEmpty() ? 25 : 0);
   }

   @Override
   public String oiuz(float var1) {
      return PingMeasurementModule.wxa() + GlyphTextUtil.encode(" ms");
   }

   @Override
   public int uod() {
      return 59194;
   }
}
