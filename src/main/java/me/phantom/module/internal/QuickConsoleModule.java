package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.UiRenderEvent;
import me.phantom.event.TickEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.core.UiManager;
import me.phantom.gui.screen.BlankPassthroughScreen;
import me.phantom.gui.widget.EasedAnimation;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.util.GlyphTextUtil;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.nanovg.NanoVG;
public class QuickConsoleModule extends BaseModule {
   public final IntegerSliderSetting hhm = new IntegerSliderSetting(this, GlyphTextUtil.encode("width"), 0, 35, 100);
   public final EasedAnimation enableAnimation = new EasedAnimation(false);
   public final EasedAnimation disableAnimation = new EasedAnimation(false);
   public final List<BaseModule> xoi = new LinkedList<>();
   public String searchQuery = GlyphTextUtil.encode("");
   public int jqu;
   public boolean mye;

   public QuickConsoleModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("QuickConsole"));
      this.selectAllSettings();
      this.setKeybind(85, false);
      this.markBeta();
   }

   @Override
   public boolean supportsHoldMode() {
      return false;
   }

   @Override
   public void setEnabled(boolean var1, boolean var2) {
      if (this.isEnabled() != var1) {
         super.setEnabled(var1, var2);
         this.mye = true;
         if (var1 && var2 && MinecraftClient.getInstance().currentScreen == null) {
            MinecraftClient.getInstance().setScreen(new BlankPassthroughScreen());
         }
      }
   }

   public void up(UiRenderEvent var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (!(var2.currentScreen instanceof BlankPassthroughScreen)) {
         this.setEnabled(false, false);
      } else {
         GuiRenderContext var3 = PhantomClient.uiManager.getContext();
         NanoVG.nvgFontFace(var3.nvg, InterfaceModule.fontBold);
         NanoVG.nvgFontSize(var3.nvg, 22.0F);
         String var4 = this.searchQuery.length() <= 0 ? PhantomClient.translationManager.ry("module.quickconsole.empty_label") : GlyphTextUtil.trim(this.searchQuery);
         float var5 = var3.framebufferWidth * (((Integer)this.hhm.getValue()).intValue() / 100.0F);
         float var6 = var3.framebufferWidth / 2 - var5 / 2.0F;
         byte var7 = 10;
         byte var8 = 40;
         int var9 = var8 / 4 * 3;
         float var10 = var5 / 4.0F * 3.0F;
         float var11 = var6 + var5 / 2.0F - var10 / 2.0F;
         if (this.searchQuery.length() > 0) {
            NanoVG.nvgBeginPath(var3.nvg);
            NanoVG.nvgRect(var3.nvg, var6 + 2.0F, var7, var5 - 4.0F, var3.framebufferHeight - 4 - var7);
            NanoVG.nvgFillColor(var3.nvg, GuiRenderContext.rgba(0, 0, 0, 140, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var3.nvg);
         }

         NanoVG.nvgBeginPath(var3.nvg);
         NanoVG.nvgRoundedRect(var3.nvg, var6 + 2.0F, var7 + 2, var5 - 4.0F, var8 - 4, 4.0F);
         NanoVG.nvgFillColor(var3.nvg, GuiRenderContext.rgba(0, 0, 0, 220, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var3.nvg);
         GlyphTextUtil.setTextAlign(var3.nvg, 18);
         NanoVG.nvgFillColor(var3.nvg, GuiRenderContext.rgba(20, 20, 20, 220, GuiRenderContext.colorScratch));
         NanoVG.nvgFontBlur(var3.nvg, 2.0F);
         GlyphTextUtil.drawText(var3.nvg, var6 + var5 / 2.0F, var7 + var8 / 2, var4);
         NanoVG.nvgFontBlur(var3.nvg, 0.0F);
         NanoVG.nvgFillColor(var3.nvg, GuiRenderContext.rgba(255, 255, 255, this.searchQuery.length() <= 0 ? 120 : 220, GuiRenderContext.colorScratch));
         GlyphTextUtil.drawText(var3.nvg, var6 + var5 / 2.0F, var7 + var8 / 2, var4);
         NanoVG.nvgFontFace(var3.nvg, InterfaceModule.fontRegular);
         NanoVG.nvgFontSize(var3.nvg, 16.0F);
         this.xoi.clear();
         if (this.searchQuery.length() > 0) {
            String var12 = GlyphTextUtil.lowercase(this.searchQuery);

            for (BaseModule var16 : PhantomClient.moduleManager.getModules()) {
               if (GlyphTextUtil.lowercase(var16.getDisplayName()).equals(var12)) {
                  this.xoi.clear();
                  this.xoi.add(var16);
                  break;
               }

               if (GlyphTextUtil.lowercase(var16.getDisplayName()).contains(var12)) {
                  this.xoi.add(var16);
               }
            }

            this.jqu = Math.min(this.jqu, this.xoi.size() - 1);

            for (int var18 = 0; var18 < this.xoi.size(); var18++) {
               int var19 = var7 + 2 + var8 + var18 * (var9 + 2);
               BaseModule var20 = this.xoi.get(var18);
               boolean var21 = this.jqu == var18;
               int var17 = var21 ? 50 : 0;
               NanoVG.nvgBeginPath(var3.nvg);
               NanoVG.nvgRoundedRect(var3.nvg, var11, var19, var10, var9, 4.0F);
               NanoVG.nvgFillColor(var3.nvg, var20.isEnabled() ? var3.shadeColor(var3.accentColor, var21 ? 0.7F : 0.3F, 220, GuiRenderContext.colorScratch2) : GuiRenderContext.rgba(var17, var17, var17, 220, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var3.nvg);
               NanoVG.nvgFillColor(var3.nvg, GuiRenderContext.rgba(255, 255, 255, 220, GuiRenderContext.colorScratch));
               GlyphTextUtil.drawText(var3.nvg, var11 + var10 / 2.0F, var19 + var9 / 2, var20.getName());
               if (var18 < 9) {
                  GlyphTextUtil.drawText(var3.nvg, var11 + var9 / 2, var19 + var9 / 2 + 1, String.valueOf(var18 + 1));
               }

               if (var20.getKeyCode() != 0) {
                  GlyphTextUtil.drawText(var3.nvg, var11 + var10 - var9 / 2, var19 + var9 / 2 + 1, UiManager.getKeyName(var20.getKeyCode()));
               }
            }
         }
      }
   }

   public void tjy(TickEvent var1) {
      this.mye = false;
   }

   public void dpf(int var1, int var2, int var3, int var4) {
      if (var1 == 256 && var3 == 1) {
         MinecraftClient.getInstance().setScreen(null);
         this.setEnabled(false, true);
      }

      if (var3 == 0) {
         if (var1 == 264) {
            if (this.jqu < this.xoi.size()) {
               this.jqu++;
            }
         } else if (var1 == 265) {
            if (this.jqu > 0) {
               this.jqu--;
            }
         } else if (var1 == 257) {
            if (this.jqu < this.xoi.size()) {
               BaseModule var5 = this.xoi.get(this.jqu);
               var5.setEnabled(!var5.isEnabled(), true);
            }

            return;
         }
      }

      if (this.searchQuery.length() > 0) {
         if ((var3 == 1 || var3 == 2) && var1 == 259) {
            this.jqu = 0;
            this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
         } else if (var1 == 256 && var3 == 1) {
            this.jqu = 0;
            this.searchQuery = GlyphTextUtil.encode("");
         }
      }
   }

   public void na(char var1, int var2) {
      if (!this.mye) {
         if (var1 >= '1' && var1 <= '9') {
            int var3 = var1 - '1';
            if (var3 >= 0 && var3 < this.xoi.size()) {
               BaseModule var4 = this.xoi.get(var3);
               var4.setEnabled(!var4.isEnabled(), true);
               return;
            }
         }

         if (this.searchQuery.length() <= 24 && (Character.isAlphabetic(var1) || var1 == '-' || var1 == ' ')) {
            this.jqu = 0;
            this.searchQuery = this.searchQuery + GlyphTextUtil.encodeChars(String.valueOf(GlyphTextUtil.lowercase(String.valueOf(var1)).charAt(0)));
         }
      }
   }
}
