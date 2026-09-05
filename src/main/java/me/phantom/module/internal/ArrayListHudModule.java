package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.hud.HudAnchor;
import me.phantom.gui.hud.HudElementBase;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
public class ArrayListHudModule extends BaseModule {
   public ToggleSetting hmh = new ToggleSetting(this, GlyphTextUtil.encode("rightside"), false);
   public ModeSetting jzb = new ModeSetting(this, GlyphTextUtil.encode("fontMode"), GlyphTextUtil.encode("Normal"), GlyphTextUtil.encode("Lowercase"), GlyphTextUtil.encode("Uppercase"), GlyphTextUtil.encode("Retarded"));
   public ColorSetting nkj = new ColorSetting(this, GlyphTextUtil.encode("fontColor"), Color.WHITE);
   public IntegerSliderSetting bzc = new IntegerSliderSetting(this, GlyphTextUtil.encode("fontSize"), 12, 18, 32);
   public ToggleSetting sqo = new ToggleSetting(this, GlyphTextUtil.encode("fontBold"), false);
   public ColorSetting nar = new ColorSetting(this, GlyphTextUtil.encode("backgroundColor"), Color.BLACK);
   public IntegerSliderSetting xnj = new IntegerSliderSetting(this, GlyphTextUtil.encode("backgroundOpacity"), 0, 40, 100);
   public IntegerSliderSetting xss = new IntegerSliderSetting(this, GlyphTextUtil.encode("glowBackgroundOpacity"), 0, 15, 100);
   public ToggleSetting ku = new ToggleSetting(this, GlyphTextUtil.encode("acting_glow"), true);
   public ToggleSetting ijf = new ToggleSetting(this, GlyphTextUtil.encode("keybinds"), true);
   public ModeSetting mau = new ModeSetting(this, GlyphTextUtil.encode("module_info"), GlyphTextUtil.encode("Off"), GlyphTextUtil.encode("Name [*]"), GlyphTextUtil.encode("Name <*>"), GlyphTextUtil.encode("Name *"), GlyphTextUtil.encode("Name - *"));
   public ToggleSetting fyq = new ToggleSetting(this, GlyphTextUtil.encode("line"), true);
   public ToggleSetting let = new ToggleSetting(this, GlyphTextUtil.encode("lineMagic"), true);
   public ColorSetting qmv = new ColorSetting(this, GlyphTextUtil.encode("lineColor"), Color.MAGENTA);
   public IntegerSliderSetting gsr = new IntegerSliderSetting(this, GlyphTextUtil.encode("lineOpacity"), 0, 80, 100);
   public ToggleSetting kdku = new ToggleSetting(this, GlyphTextUtil.encode("dotted"), false);
   public final ModeSetting tbw = new ModeSetting(this, GlyphTextUtil.encode("font"), InterfaceModule.gfpy);
   public InterfaceModule utt;
   public HudModule lkng;

   public ArrayListHudModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("List"));
      this.qmv.setVisibleWhen(this.fyq);
      this.gsr.setVisibleWhen(this.fyq);
      this.kdku.setVisibleWhen(this.fyq);
      this.let.setVisibleWhen(this.fyq);
      this.selectAllSettings();
   }

   public String apk(String var1) {
      if (!((String)this.jzb.getValue()).equals(GlyphTextUtil.encode("Normal"))) {
         if (((String)this.jzb.getValue()).equals(GlyphTextUtil.encode("Lowercase"))) {
            return GlyphTextUtil.lowercase(var1);
         }

         if (((String)this.jzb.getValue()).equals(GlyphTextUtil.encode("Uppercase"))) {
            return GlyphTextUtil.uppercase(var1);
         }

         if (((String)this.jzb.getValue()).equals(GlyphTextUtil.encode("Retarded"))) {
            char[] var2 = var1.toCharArray();
            Random var3 = new Random(var1.hashCode());

            for (int var4 = 0; var4 < var2.length; var4++) {
               var2[var4] = var3.nextBoolean() ? GlyphTextUtil.uppercase(String.valueOf(var2[var4])).charAt(0) : GlyphTextUtil.lowercase(String.valueOf(var2[var4])).charAt(0);
            }

            return new String(var2);
         }
      }

      return var1;
   }

   public void fkf(GuiRenderContext var1) {
      if (this.isEnabled() && PhantomClient.isInWorld()) {
         long var2 = var1.nvg;
         NanoVG.nvgFontSize(var2, ((Integer)this.bzc.getValue()).intValue());
         GlyphTextUtil.setTextAlign(var2, 9);
         NanoVG.nvgFontFace(var2, this.sqo.getValue() ? var1.loadFont((String)this.tbw.getValue(), "bold") : var1.loadFont((String)this.tbw.getValue(), "regular"));
         BaseModule[] var4 = PhantomClient.moduleManager.getModules();
         ArrayList<BaseModule> var5 = new ArrayList<>(var4.length);

         for (int var6 = 0; var6 < var4.length; var6++) {
            BaseModule var7 = var4[var6];
            if (var7.lvr() && !var7.isHiddenFromList() && (var7.isEnabled() || var7.disableAnimation.sdx()) && !this.equals(var7) && var7.getCategory() != ModuleCategory.yy) {
               var5.add(var7);
            }
         }

         var5.sort(
            Comparator.comparingDouble(
               var3 -> -GlyphTextUtil.measureText(var2, 0.0F, 0.0F, this.apk(var3.buildDisplayName((Boolean)this.ijf.getValue(), !((String)this.mau.getValue()).equals(GlyphTextUtil.encode("Off")))), (FloatBuffer)null)
            )
         );
         float var28 = this.hmh.getValue() ? var1.framebufferWidth - 5 : 5.0F;
         float var29 = 5.0F;
         float var8 = (Integer)this.bzc.getValue() + 7;
         float var9 = (Integer)this.bzc.getValue() / 3;
         float var10 = ((Integer)this.gsr.getValue()).intValue() / 100.0F;
         int var11 = (int)(((Integer)this.xnj.getValue()).intValue() / 100.0F * 255.0F);
         float var12 = ((Integer)this.xss.getValue()).intValue() / 100.0F;
         if (this.lkng.isEnabled()) {
            float var13 = 1.4F * Math.round(((Integer)this.lkng.rfv.getValue()).intValue() / 100.0F * 5.0F) / 5.0F;
            float var14 = 0.0F;
            HudAnchor var15 = this.hmh.getValue() ? HudAnchor.cns : HudAnchor.obq;
            float var16 = var15.xtp(var1, var13);

            for (HudElementBase var18 : var15.sm) {
               var14 = Math.max(var16 + var18.yxe(var1) * var13, var14);
            }

            var29 = var14 + 5.0F;
         }

         boolean var30 = (Boolean)this.kdku.getValue() && (Boolean)this.fyq.getValue();
         int var31 = 0;
         float var32 = var29;

         for (BaseModule var34 : var5) {
            float var35 = var34.isEnabled() ? var34.enableAnimation.hee() : 1.0F - var34.disableAnimation.hee();
            if (var34.isEnabled()) {
               var35 = 1.0F - (float)Math.pow(var35, 2.7);
            } else {
               var35 = (float)Math.pow(1.0F - var35, 2.7);
            }

            NanoVG.nvgGlobalAlpha(var1.nvg, 1.0F - var35);
            String var19 = this.apk(var34.buildDisplayName((Boolean)this.ijf.getValue(), !((String)this.mau.getValue()).equals(GlyphTextUtil.encode("Off"))));
            float var20 = var28;
            float var21 = GlyphTextUtil.measureText(var2, 0.0F, 0.0F, var19, (FloatBuffer)null) + var9 + 13.0F;
            if ((Boolean)this.hmh.getValue()) {
               var20 = var28 - (var21 - 2.0F);
            }

            float var22 = var35 * var21;
            if (!(Boolean)this.hmh.getValue()) {
               var22 = -var22;
            }

            NanoVG.nvgTranslate(var1.nvg, var22, 0.0F);
            NanoVG.nvgBeginPath(var2);
            float var23 = var21 - (var30 ? 6 : 2);
            byte var24 = 3;
            if ((Boolean)this.hmh.getValue()) {
               NanoVG.nvgMoveTo(var2, var20 + (var31 == 0 ? var24 : 0), var29);
               NanoVG.nvgLineTo(var2, var20 + var23, var29);
               NanoVG.nvgLineTo(var2, var20 + var23, var29 + var8);
               NanoVG.nvgLineTo(var2, var20 + var24, var29 + var8);
               NanoVG.nvgLineTo(var2, var20, var29 + var8 - var24);
               NanoVG.nvgLineTo(var2, var20, var29 + (var31 == 0 ? var24 : 0));
            } else {
               NanoVG.nvgMoveTo(var2, var20 + var24, var29);
               if (var31 == 0) {
                  NanoVG.nvgLineTo(var2, var20 + var23 + var24, var29);
                  NanoVG.nvgLineTo(var2, var20 + var23 + var24 + var24, var29 + var24);
               } else {
                  NanoVG.nvgLineTo(var2, var20 + var23 + var24 + var24, var29);
               }

               NanoVG.nvgLineTo(var2, var20 + var23 + var24 + var24, var29 + var8 - var24);
               NanoVG.nvgLineTo(var2, var20 + var23 + var24 + var24 - var24, var29 + var8);
               NanoVG.nvgLineTo(var2, var20 + var24, var29 + var8);
            }

            NanoVG.nvgFillColor(var2, this.nar.xam(var11 / 255.0F, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var2);
            NanoVG.nvgTranslate(var2, var20, var29);
            if (var30) {
               float var25 = this.hmh.getValue() ? var21 - var9 : 0.0F;
               NanoVG.nvgTranslate(var2, var25, 1.0F);
               this.ohf(var1, (int)var9, (int)var8 - 2, 2.0F, 0, var10, this.qmv.xam(1.0F, GuiRenderContext.colorScratch2));
               NanoVG.nvgTranslate(var2, -var25, -1.0F);
            }

            if (!(Boolean)this.ku.getValue() || var34.ma()) {
               this.ohf(var1, (int)var21, (int)var8, 3.0F, 0, var12, this.qmv.xam(var12, GuiRenderContext.colorScratch2));
            }

            NanoVG.nvgTranslate(var2, -var20, -var29);
            int var37 = this.hmh.getValue() ? 0 : 5;
            NanoVG.nvgFillColor(var2, GuiRenderContext.rgba(0, 0, 0, var11, GuiRenderContext.colorScratch));

            for (int var26 = -1; var26 <= 1; var26++) {
               for (int var27 = -1; var27 <= 1; var27++) {
                  GlyphTextUtil.drawText(var2, var20 + var26 + var9 + var37, var29 + var27 + 5.0F, var19);
               }
            }

            NanoVG.nvgFillColor(var2, this.nkj.xam(1.0F, GuiRenderContext.colorScratch));
            GlyphTextUtil.drawText(var2, var20 + var9 + var37, var29 + 5.0F, var19);
            NanoVG.nvgTranslate(var1.nvg, -var22, 0.0F);
            var29 += var8 * (1.0F - var35);
            var31++;
         }

         NanoVG.nvgGlobalAlpha(var1.nvg, 1.0F);
         if (!(Boolean)this.kdku.getValue() && (Boolean)this.fyq.getValue()) {
            NanoVG.nvgTranslate(var2, var28, var32);
            this.ohf(var1, (int)var9, (int)(var29 - var32), 0.0F, 0, var10, this.qmv.xam(1.0F, GuiRenderContext.colorScratch2));
            NanoVG.nvgTranslate(var2, -var28, -var32);
         }
      }
   }

   private void ohf(GuiRenderContext var1, int var2, int var3, float var4, int var5, float var6, NVGColor var7) {
      if ((Boolean)this.let.getValue()) {
         var1.drawTintedGlitchOverlay(var2, var3, var4, var5, var6, var7);
      } else {
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 0.0F, var2, var3, var4);
         NanoVG.nvgFillColor(var1.nvg, var7);
         NanoVG.nvgFill(var1.nvg);
      }
   }
}
