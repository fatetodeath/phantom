package me.phantom.gui.hud;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.hud.NotificationAction;
import me.phantom.util.GlyphTextUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public class NotificationToast {
   public final HudIconGlyph icon;
   public String title;
   public String description;
   public long startTime;
   public long durationMs;
   public Consumer<NotificationToast> onClick;
   public List<NotificationAction> buttons;
   public final NotificationManager manager;

   NotificationToast(NotificationManager var1, HudIconGlyph var2, String var3, Object... var4) {
      this.manager = var1;
      this.buttons = new ArrayList<>(1);
      this.icon = var2;
      this.title = PhantomClient.translationManager.ry(var3 + GlyphTextUtil.encode(".title"), var4);
      this.description = PhantomClient.translationManager.ry(var3 + GlyphTextUtil.encode(".text"), var4);
      this.durationMs = var2 == HudIconGlyph.xhx ? 3600000L : 5000L;
      this.onClick = this.icon == HudIconGlyph.xhx ? var0 -> {} : var0 -> var0.durationMs = 0L;
   }

   public NotificationToast withOnClick(Consumer<NotificationToast> var1) {
      this.onClick = var1;
      return this;
   }

   public Runnable createDismissRunnable() {
      return () -> {
         synchronized (this.manager.key) {
            this.manager.key.remove(this);
         }
      };
   }

   public void render(GuiRenderContext var1, int var2, int var3, int var4) {
      if (this.startTime == 0L) {
         this.startTime = System.currentTimeMillis();
      }

      boolean var5 = var1.isHovered(var2, var3, 0, 0, 320, 70);
      long var6 = var1.nvg;
      float var8 = 0.0F;
      long var9 = System.currentTimeMillis() - this.startTime;
      float var11 = 420.0F;
      if ((float)var9 <= 420.0F) {
         var8 = (float)Math.pow(1.0F - (float)var9 / 420.0F, 2.7);
      } else if ((float)var9 > (float)this.durationMs - 420.0F) {
         var8 = 1.0F - (float)Math.pow((float)(this.durationMs - var9) / 420.0F, 2.7);
      }

      float var12 = (float)var9 / ((float)this.durationMs * 1.0F);
      NanoVG.nvgTranslate(var6, var8 * 330.0F, 0.0F);
      NanoVG.nvgGlobalAlpha(var6, 1.0F - var8);
      byte var13 = 26;

      try {
         if (var4 == 1) {
            NVGPaint var14 = NanoVG.nvgBoxGradient(
               var6, 0.0F, 0.0F, 320.0F, 70.0F, 8.0F, 10.0F, GuiRenderContext.rgba(0, 0, 0, 128, GuiRenderContext.colorScratch), GuiRenderContext.rgba(0, 0, 0, 0, GuiRenderContext.colorScratch2), GuiRenderContext.paintScratch
            );
            NanoVG.nvgBeginPath(var6);
            NanoVG.nvgRect(var6, -10.0F, -10.0F, 340.0F, 90.0F);
            NanoVG.nvgRoundedRect(var6, 0.0F, 0.0F, 320.0F, 70.0F, 4.0F);
            NanoVG.nvgPathWinding(var6, 2);
            NanoVG.nvgFillPaint(var6, var14);
            NanoVG.nvgFill(var6);
            NanoVG.nvgBeginPath(var6);
            NanoVG.nvgRoundedRect(var6, 0.0F, 0.0F, 320.0F, 70.0F, 8.0F);
            if (HudIconGlyph.ang.equals(this.icon)) {
               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(10, 90, 10, var5 ? 255 : 220, GuiRenderContext.colorScratch));
            } else if (HudIconGlyph.fof.equals(this.icon)) {
               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(80, 80, 10, var5 ? 255 : 220, GuiRenderContext.colorScratch));
            } else if (HudIconGlyph.sxq.equals(this.icon)) {
               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(110, 10, 10, var5 ? 255 : 220, GuiRenderContext.colorScratch));
            } else if (HudIconGlyph.xhx.equals(this.icon)) {
               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(110, 110, 110, var5 ? 255 : 220, GuiRenderContext.colorScratch));
            }

            NanoVG.nvgFill(var6);
            NanoVG.nvgFontSize(var6, 18.0F);
            GlyphTextUtil.setTextAlign(var6, 18);
            NanoVG.nvgBeginPath(var6);
            NanoVG.nvgRoundedRect(var6, 10.0F, 56.0F, 300 - this.buttons.size() * var13, 8.0F, 4.0F);
            NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(0, 0, 0, 128, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var6);
            NanoVG.nvgBeginPath(var6);
            NanoVG.nvgRoundedRect(var6, 10.0F, 56.0F, (300 - this.buttons.size() * var13) * (1.0F - var12), 8.0F, 4.0F);
            NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(60, 60, 60, 128, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var6);
            NVGColor var15 = var1.shadeColor(var1.textBaseColor, 0.7F, 220, GuiRenderContext.colorScratch);
            var1.drawTextAligned(170.0F, 20.0F, this.title, 20.0F, var1.loadFont((String)PhantomClient.uiManager.interfaceModule.zhn.getValue(), "bold"), 18, 0.0F, 1.0F, var15);
            var1.drawTextAligned(170.0F, 42.0F, this.description, 16.0F, var1.loadFont((String)PhantomClient.uiManager.interfaceModule.zhn.getValue(), "regular"), 18, 0.0F, 1.0F, var15);
            if (this.icon != HudIconGlyph.xhx) {
               var1.drawIconText(10, 30, this.icon.wet, 32.0F, false, 17, 0.0F, 1.0F);
            } else {
               float var16 = 30.0F;
               float var17 = 30.0F;
               float var18 = 17.0F;
               int var19 = (int)((float)(System.currentTimeMillis() % 1000L) / 1000.0F * 360.0F);
               NanoVG.nvgBeginPath(var6);
               NanoVG.nvgCircle(var6, var16, var17, var18);
               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(40, 40, 40, 255, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var6);
               NanoVG.nvgBeginPath(var6);
               NanoVG.nvgMoveTo(var6, var16, var17);

               for (int var20 = 0; var20 < 90; var20++) {
                  float var21 = var16 - (float)Math.sin(Math.toRadians(var19 + var20)) * var18;
                  float var22 = var17 + (float)Math.cos(Math.toRadians(var19 + var20)) * var18;
                  NanoVG.nvgLineTo(var6, var21, var22);
               }

               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(140, 140, 140, 255, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var6);
               NanoVG.nvgBeginPath(var6);
               NanoVG.nvgMoveTo(var6, var16, var17);

               for (int var35 = 0; var35 < 90; var35++) {
                  float var37 = var16 - (float)Math.sin(Math.toRadians(var19 + var35 + 180)) * var18;
                  float var38 = var17 + (float)Math.cos(Math.toRadians(var19 + var35 + 180)) * var18;
                  NanoVG.nvgLineTo(var6, var37, var38);
               }

               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(140, 140, 140, 255, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var6);
            }

            NanoVG.nvgFontSize(var6, var13 - 8);

            for (int var28 = 0; var28 < this.buttons.size(); var28++) {
               NotificationAction var30 = this.buttons.get(var28);
               int var32 = 70 - var13 - 1;
               int var34 = 320 - (var28 + 1) * (var13 + 3) + 1;
               boolean var36 = var1.isHovered(var2, var3, var34, var32, var13, var13);
               NanoVG.nvgBeginPath(var6);
               NanoVG.nvgRoundedRect(var6, var34, var32, var13, var13, 8.0F);
               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(30, 30, 30, var36 ? 240 : 180, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var6);
               NanoVG.nvgFillColor(var6, GuiRenderContext.rgba(170, 170, 170, 200, GuiRenderContext.colorScratch));
               GlyphTextUtil.setTextAlign(var6, 18);
               GlyphTextUtil.drawTextBuffer(var1.nvg, var34 + var13 / 2, 70 - var13 / 2 - 1, var30.iconImage);
               if (var36) {
                  var1.setTooltip(var30.label);
               }
            }
         } else if (var4 == 0) {
            for (int var26 = 0; var26 < this.buttons.size(); var26++) {
               NotificationAction var27 = this.buttons.get(var26);
               int var29 = 70 - var13 - 1;
               int var31 = 320 - (var26 + 1) * (var13 + 3) + 1;
               boolean var33 = var1.isHovered(var2, var3, var31, var29, var13, var13);
               if (var33 && var1.doj[0] && var4 == 0) {
                  if (var27.dismissCondition.get()) {
                     this.buttons.remove(var26--);
                     this.durationMs = 0L;
                  }

                  var1.doj[0] = false;
               }
            }

            if (var5 && var1.doj[0]) {
               this.onClick.accept(this);
               var1.doj[0] = false;
            }
         }
      } finally {
         NanoVG.nvgTranslate(var6, -var8 * 330.0F, 0.0F);
         NanoVG.nvgGlobalAlpha(var6, 1.0F);
      }
   }

   public NotificationToast withDurationMs(long var1) {
      this.durationMs = var1;
      return this;
   }

   public NotificationToast addButton(String var1, ByteBuffer var2, Supplier<Boolean> var3) {
      this.buttons.add(new NotificationAction(var1, var2, var3));
      return this;
   }
}
