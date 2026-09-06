package me.phantom.gui.widget;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.core.UiManager;
import me.phantom.gui.hud.LabeledHudElement;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import java.nio.FloatBuffer;
import java.util.List;
import org.lwjgl.nanovg.NanoVG;

public class KeybindsListPanel extends LabeledHudElement {
   public KeybindsListPanel(JsonObject var1) {
      super(var1);
   }

   @Override
   public float icg(GuiRenderContext var1) {
      long var2 = var1.nvg;
      NanoVG.nvgFontFace(var2, InterfaceModule.fontBold);
      NanoVG.nvgFontSize(var2, 9.5F);
      GlyphTextUtil.setTextAlign(var2, 9);
      List<BaseModule> var4 = PhantomClient.moduleManager.getKeyboundModules();
      int var5 = 22;

      for (BaseModule var7 : var4) {
         String var8 = (UiManager.getKeyName(var7.getKeyCode()) + GlyphTextUtil.encode("")).toUpperCase();
         var5 = Math.max(var5, (int)(GlyphTextUtil.measureText(var1.nvg, 0.0F, 0.0F, var8, (FloatBuffer)null) + 7.5F));
      }

      NanoVG.nvgFontSize(var2, 14.0F);
      float var9 = GlyphTextUtil.measureText(var2, 0.0F, 0.0F, GlyphTextUtil.encode("Keybind"), (FloatBuffer)null) + 10.0F + 22.0F;

      for (BaseModule var11 : var4) {
         var9 = Math.max(var9, GlyphTextUtil.measureText(var2, 0.0F, 0.0F, var11.getName(), (FloatBuffer)null));
      }

      return var5 + 2 + var9 + 5.0F;
   }

   @Override
   public float yxe(GuiRenderContext var1) {
      List<BaseModule> var2 = PhantomClient.moduleManager.getKeyboundModules();
      return 22.0F * (1 + var2.size());
   }

   @Override
   public void jjc(GuiRenderContext var1) {
      this.net(var1);
      float var2 = this.icg(var1);
      float var3 = 22.0F;
      long var4 = var1.nvg;
      NanoVG.nvgFontFace(var4, InterfaceModule.fontBold);
      NanoVG.nvgFontSize(var4, 9.5F);
      GlyphTextUtil.setTextAlign(var4, 9);
      List<BaseModule> var6 = PhantomClient.moduleManager.getKeyboundModules();
      int var7 = 22;

      for (BaseModule var9 : var6) {
         String var10 = (UiManager.getKeyName(var9.getKeyCode()) + GlyphTextUtil.encode("")).toUpperCase();
         var7 = Math.max(var7, (int)(GlyphTextUtil.measureText(var1.nvg, 0.0F, 0.0F, var10, (FloatBuffer)null) + 7.5F));
      }

      this.vkw(var1, var2 / 2.0F + var7 / 2 - 2.0F, var3 / 2.0F + 1.0F, this.oiuz(var1.tickDelta), 18);
      this.fc(var1, var7 / 2 + 2.5F, var3 / 2.0F);
      int var16 = 0;

      for (BaseModule var18 : var6) {
         float var11 = ++var16 * 22.0F;
         byte var12 = 17;
         String var13 = (UiManager.getKeyName(var18.getKeyCode()) + GlyphTextUtil.encode("")).toUpperCase();
         NanoVG.nvgSave(var1.nvg);
         NanoVG.nvgTranslate(var1.nvg, 2.5F, 2.5F + var11);
         NanoVG.nvgBeginPath(var4);
         NanoVG.nvgRoundedRect(var4, -1.0F, -1.0F, var7 + 2, var12 + 2, 4.0F);
         NanoVG.nvgFillColor(var4, GuiRenderContext.rgba(30, 30, 30, 220, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var4);
         var1.drawGlitchOverlay(var7, var12, 4.0F, var18.hashCode(), 0.5F);
         NanoVG.nvgRestore(var1.nvg);
         this.vkw(var1, var2 / 2.0F + var7 / 2, var3 / 2.0F + 1.0F + var11, var18.getName(), 18);
         if (!var13.isEmpty()) {
            float var14 = 2.5F + var7 / 2;
            float var15 = var3 / 2.0F + 1.0F + var11;
            NanoVG.nvgFontFace(var4, InterfaceModule.fontRegular);
            GlyphTextUtil.setTextAlign(var4, 18);
            NanoVG.nvgFontSize(var4, 9.5F);
            NanoVG.nvgFillPaint(var4, GuiRenderContext.paintScratch);
            NanoVG.nvgFill(var4);
            NanoVG.nvgFillColor(var4, GuiRenderContext.rgba(100, 100, 100, 220, GuiRenderContext.colorScratch));
            NanoVG.nvgFontBlur(var4, 2.0F);
            GlyphTextUtil.drawText(var4, var14, var15, var13);
            NanoVG.nvgFontBlur(var4, 0.0F);
            NanoVG.nvgFillColor(var4, GuiRenderContext.rgba(200, 200, 200, 220, GuiRenderContext.colorScratch));
            GlyphTextUtil.drawText(var4, var14, var15, var13);
         }
      }
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

   @Override
   public String oiuz(float var1) {
      return GlyphTextUtil.encode("Keybinds");
   }

   @Override
   public int uod() {
      return 9000;
   }
}
