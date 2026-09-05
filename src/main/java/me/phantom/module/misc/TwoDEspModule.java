package me.phantom.module.misc;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.visuals.EspModule;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.FloatPoint2D;
import me.phantom.util.math.RotationMathUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.nanovg.NanoVG;

public class TwoDEspModule extends AbstractEspFeature {
   public final ColorSetting edl = new ColorSetting(this.vsr, GlyphTextUtil.encode("color_2d"), 0.35F, 0.0F, 0.9F);
   public final IntegerSliderSetting wpl = new IntegerSliderSetting(this.vsr, GlyphTextUtil.encode("2d_scaling"), 20, 100, 200);
   public final IntegerSliderSetting izg = new IntegerSliderSetting(this.vsr, GlyphTextUtil.encode("2d_corner"), 5, 30, 100);
   public final ToggleSetting ikz = new ToggleSetting(this.vsr, GlyphTextUtil.encode("2d_healthbar"), true);
   public final ToggleSetting ql = new ToggleSetting(this.vsr, GlyphTextUtil.encode("2d_healthbar_dotted"), true);
   public final ColorSetting swn = new ColorSetting(this.vsr, GlyphTextUtil.encode("aimassist_color_2d"), 0.2F, 0.9F, 0.9F);
   public final FloatPoint2D projection = new FloatPoint2D();

   public TwoDEspModule(EspModule var1) {
      super(GlyphTextUtil.encode("2D"), var1);
      this.ql.setVisibilitySupplier(() -> this.isEnabled() && (Boolean)this.ikz.getValue());
   }

   @Override
   public void lfc(GuiRenderContext var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      ClientPlayerEntity var3 = var2.player;
      float var4 = var1.tickDelta;
      Vec3d var5 = GameInteractionUtil.getLerpedEyePos(var3, var4);
      this.vsr.bot(var1, var4x -> {
         LivingEntity var5x = (LivingEntity)var4x;
         Vec3d var6 = GameInteractionUtil.getLerpedPos(var5x, var4);
         Vec3d var7 = var6.subtract(var5);
         float[] var8 = RotationMathUtil.getScreenBoundingBox(var7, var4x);
         if (var8 != null) {
            float var9 = var8[0];
            float var10 = var8[1];
            float var11 = var8[2];
            float var12 = var8[3];
            float var13 = var11 - var9;
            float var14 = var12 - var10;
            float var15 = ((Integer)this.wpl.getValue()).intValue() / 100.0F;
            var9 -= var13 * (var15 - 1.0F) / 2.0F;
            var10 -= var14 * (var15 - 1.0F) / 2.0F;
            var13 *= var15;
            var14 *= var15;
            int var16 = (int)(Math.min(var13, var14) * (((Integer)this.izg.getValue()).intValue() / 200.0F));
            NanoVG.nvgBeginPath(var1.nvg);
            NanoVG.nvgMoveTo(var1.nvg, var9, var10 + var16);
            NanoVG.nvgLineTo(var1.nvg, var9, var10);
            NanoVG.nvgLineTo(var1.nvg, var9 + var16, var10);
            NanoVG.nvgMoveTo(var1.nvg, var9, var10 + var14 - var16);
            NanoVG.nvgLineTo(var1.nvg, var9, var10 + var14);
            NanoVG.nvgLineTo(var1.nvg, var9 + var16, var10 + var14);
            NanoVG.nvgMoveTo(var1.nvg, var9 + var13 - var16, var10 + var14);
            NanoVG.nvgLineTo(var1.nvg, var9 + var13, var10 + var14);
            NanoVG.nvgLineTo(var1.nvg, var9 + var13, var10 + var14 - var16);
            NanoVG.nvgMoveTo(var1.nvg, var9 + var13 - var16, var10);
            NanoVG.nvgLineTo(var1.nvg, var9 + var13, var10);
            NanoVG.nvgLineTo(var1.nvg, var9 + var13, var10 + var16);
            NanoVG.nvgStrokeWidth(var1.nvg, 4.0F);
            NanoVG.nvgStrokeColor(var1.nvg, GuiRenderContext.rgba(0, 0, 0, 60, GuiRenderContext.colorScratch));
            NanoVG.nvgStroke(var1.nvg);
            NanoVG.nvgStrokeWidth(var1.nvg, 3.0F);
            NanoVG.nvgStrokeColor(var1.nvg, this.ypg(var4x).xam(((Integer)this.ep.getValue()).intValue() / 100.0F, GuiRenderContext.colorScratch));
            NanoVG.nvgStroke(var1.nvg);
            if ((Boolean)this.ikz.getValue() && var4x instanceof LivingEntity var17) {
               float var18 = 6.0F + (float)Math.sqrt(var16) * 0.4F;
               float var19 = 1.0F;
               float var20 = 1.0F;
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRoundedRect(var1.nvg, var9 - var18, var10, var18 - var19, var14, 1.5F);
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(0, 0, 0, 220, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var1.nvg);
               float var21 = Math.max(0.0F, Math.min(1.0F, var17.getHealth() / var17.getMaxHealth()));
               float var22 = var14 * var21 - 2.0F * var20;
               if (var22 > 0.0F) {
                  NanoVG.nvgBeginPath(var1.nvg);
                  NanoVG.nvgRoundedRect(var1.nvg, var9 - var18 + var20, var10 + var20, var18 - var19 - 2.0F * var20, var22, 1.5F);
                  NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(0, 220, 0, 220, GuiRenderContext.colorScratch));
                  NanoVG.nvgFill(var1.nvg);
               }

               if ((Boolean)this.ql.getValue()) {
                  for (int var23 = 0; var23 < Math.ceil(var17.getMaxHealth() / 2.0F); var23++) {
                     float var24 = var14 * Math.max(0.0F, Math.min(1.0F, var23 / var17.getMaxHealth() * 2.0F)) - 2.0F * var20;
                     NanoVG.nvgBeginPath(var1.nvg);
                     NanoVG.nvgRoundedRect(var1.nvg, var9 - var18 + var20, var10 + var20 + var24, var18 - var19 - 2.0F * var20, 2.0F, 1.5F);
                     NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(30, 30, 30, 220, GuiRenderContext.colorScratch));
                     NanoVG.nvgFill(var1.nvg);
                  }
               }
            }
         }
      });
   }
}
