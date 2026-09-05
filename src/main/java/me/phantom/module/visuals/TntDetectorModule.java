package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.render.hud.CountdownLabelRenderer;
import me.phantom.render.LineStripBuilder;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.FloatPoint2D;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;
public class TntDetectorModule extends BaseModule {
   private static final float ier = 22.0F;
   public final ColorSetting dgd = new ColorSetting(this, GlyphTextUtil.encode("lowcolor"), 0.33F, 1.0F, 1.0F);
   public final ColorSetting mv = new ColorSetting(this, GlyphTextUtil.encode("highcolor"), 0.0F, 1.0F, 1.0F);
   public final ColorSetting imc = new ColorSetting(this, GlyphTextUtil.encode("color"), 0.0F, 1.0F, 1.0F);
   private final FloatPoint2D bin = new FloatPoint2D();
   public final List<CountdownLabelRenderer> zgr = new LinkedList<>();

   public TntDetectorModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("TNTDetector"));
      this.selectAllSettings();
      this.markClipboardHint();
   }

   public void wj(WorldRenderOverlayEvent var1) {
      GuiRenderContext var2 = var1.getContext();
      NanoVG.nvgFontFace(var2.nvg, InterfaceModule.fontRegular);
      GlyphTextUtil.setTextAlign(var2.nvg, 9);
      NanoVG.nvgFontSize(var2.nvg, 22.0F);
      this.zgr.forEach(var1x -> var1x.jjc(var2));
      this.zgr.clear();
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      MatrixStack var2 = var1.getMatrices();
      ClientPlayerEntity var3 = MinecraftClient.getInstance().player;
      double var4 = 12.0 - 4.0 * Math.sqrt(6.0F + var3.getHealth() + var3.getAbsorptionAmount()) / Math.sqrt(7.0);
      float var6 = var1.tickDelta();
      Vec3d var7 = GameInteractionUtil.getLerpedEyePos(var3, var6);
      Color var8 = this.mv.nqq();
      Color var9 = this.dgd.nqq();

      try {
         GL11.glDisable(2929);
         this.zgr.clear();

         for (Entity var11 : WorldEntityUtil.getEntities()) {
            if (var11 instanceof TntEntity var12) {
               Vec3d var13 = GameInteractionUtil.getLerpedPos(var12, var6).add(0.0, 0.5, 0.0);
               double var14 = 8.3F;
               if (Render3DUtil.igxp(var13.x - var7.x, var13.y - var7.y, var13.z - var7.z, true, this.bin)) {
                  this.zgr.add(new CountdownLabelRenderer(this, this.bin.axn, this.bin.oaz, var12.getFuse()));
               }

               LineStripBuilder var16 = LineStripBuilder.create(var2);

               try {
                  for (int var17 = 0; var17 <= 90; var17++) {
                     double var18 = Math.toRadians(var17 * 4);
                     var16.vertex(
                        var13.x + Math.cos(var18) * var4,
                        var13.y + 0.1,
                        var13.z + Math.sin(var18) * var4,
                        var8.getRed() / 255.0F,
                        var8.getGreen() / 255.0F,
                        var8.getBlue() / 255.0F,
                        1.0F
                     );
                  }
               } catch (Throwable var29) {
                  if (var16 != null) {
                     try {
                        var16.close();
                     } catch (Throwable var27) {
                        var29.addSuppressed(var27);
                     }
                  }

                  throw var29;
               }

               if (var16 != null) {
                  var16.close();
               }

               var16 = LineStripBuilder.create(var2);

               try {
                  for (int var32 = 0; var32 <= 90; var32++) {
                     double var33 = Math.toRadians(var32 * 4);
                     var16.vertex(
                        var13.x + Math.cos(var33) * var14,
                        var13.y + 0.1,
                        var13.z + Math.sin(var33) * var14,
                        var9.getRed() / 255.0F,
                        var9.getGreen() / 255.0F,
                        var9.getBlue() / 255.0F,
                        1.0F
                     );
                  }
               } catch (Throwable var28) {
                  if (var16 != null) {
                     try {
                        var16.close();
                     } catch (Throwable var26) {
                        var28.addSuppressed(var26);
                     }
                  }

                  throw var28;
               }

               if (var16 != null) {
                  var16.close();
               }
            }
         }
      } finally {
         GL11.glEnable(2929);
      }
   }
}
