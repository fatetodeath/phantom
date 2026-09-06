package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import org.lwjgl.nanovg.NanoVG;
public class AutoRespawnModule extends BaseModule {
   public final FloatRangeSliderSetting xaz = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("delay"), 1.0F, 1.5F, 3.0F, 5.0F);
   public long wv;

   public AutoRespawnModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.or, GlyphTextUtil.encode("AutoRespawn"));
      this.markClipboardHint();
   }

   @Override
   public boolean ma() {
      return this.wv > 0L;
   }

   @Override
   public void onEnable() {
      this.wv = 0L;
   }

   public void wj(WorldRenderOverlayEvent var1) {
      if (Minecraft.getInstance().screen instanceof DeathScreen) {
         if (this.wv != -1L) {
            if (this.wv == 0L) {
               this.wv = System.currentTimeMillis() + (int)(this.xaz.fbz() * 1000.0F);
            } else if (System.currentTimeMillis() >= this.wv) {
               this.wv = -1L;
               Minecraft.getInstance().player.respawn();
               Minecraft.getInstance().setScreen(null);
            } else {
               GuiRenderContext var2 = var1.getContext();
               int var3 = var2.framebufferWidth / 2;
               int var4 = var2.framebufferHeight / 3;
               String var5 = PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.autorespawn.text"), RotationMathUtil.formatTwoDecimals((float)(this.wv - System.currentTimeMillis()) / 1000.0F));
               NanoVG.nvgFontSize(var2.nvg, 40.0F);
               NanoVG.nvgFontFace(var2.nvg, InterfaceModule.fontRegular);
               GlyphTextUtil.setTextAlign(var2.nvg, 18);
               NanoVG.nvgFillColor(var2.nvg, GuiRenderContext.rgba(20, 20, 20, 200, GuiRenderContext.colorScratch));

               for (int var6 = -1; var6 <= 1; var6++) {
                  for (int var7 = -1; var7 <= 1; var7++) {
                     GlyphTextUtil.drawText(var2.nvg, var3 + var6, var4 + var7, var5);
                  }
               }

               NanoVG.nvgFillColor(var2.nvg, GuiRenderContext.rgba(240, 240, 240, 200, GuiRenderContext.colorScratch));
               GlyphTextUtil.drawText(var2.nvg, var3, var4, var5);
            }
         }
      } else {
         this.wv = 0L;
      }
   }
}
