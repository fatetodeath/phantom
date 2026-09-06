package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.EntityTargetFilterModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.IntRangeSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.WorldEntityUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
public class OutOfFOVArrowModule extends EntityTargetFilterModule {
   public final FloatSliderSetting sjj = new FloatSliderSetting(this, GlyphTextUtil.encode("arrowsize"), 40.0F, 80.0F, 120.0F);
   public final IntRangeSliderSetting hdl = new IntRangeSliderSetting(this, GlyphTextUtil.encode("range"), 0, 0, 120, 256);
   public final IntegerSliderSetting yoa = new IntegerSliderSetting(this, GlyphTextUtil.encode("near_range"), 0, 0, 64).kyc(var0 -> var0 == 0 ? GlyphTextUtil.encode("Auto") : GlyphTextUtil.encode("") + var0);
   public final IntRangeSliderSetting ojv = new IntRangeSliderSetting(this, GlyphTextUtil.encode("radius"), 10, 30, 80, 200);
   public final ToggleSetting izh = new ToggleSetting(this, GlyphTextUtil.encode("3d"), true);
   public final ToggleSetting uct = new ToggleSetting(this, GlyphTextUtil.encode("checkfrustum"), true);
   public final ColorSetting acj = new ColorSetting(this, GlyphTextUtil.encode("farcolor"), 0.33F, 1.0F, 1.0F);
   public final ColorSetting nrm = new ColorSetting(this, GlyphTextUtil.encode("nearcolor"), 0.0F, 1.0F, 1.0F);
   public final ColorSetting gav = new ColorSetting(this, GlyphTextUtil.encode("friendcolor"), 0.66F, 1.0F, 1.0F);
   public final ToggleSetting ctd = new ToggleSetting(this, GlyphTextUtil.encode("projectiles"), true);
   public final ColorSetting ljg = new ColorSetting(this, GlyphTextUtil.encode("projectiles_color"), 0.33F, 1.0F, 1.0F);
   public final ToggleSetting apl = new ToggleSetting(this, GlyphTextUtil.encode("circles"), true);
   public final IntegerSliderSetting qmq = new IntegerSliderSetting(this, GlyphTextUtil.encode("block_per_circle"), 5, 20, 100);
   public final ToggleSetting oalh = new ToggleSetting(this, GlyphTextUtil.encode("circle_nametag"), false);

   public OutOfFOVArrowModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("OutOfFOVArrow"));
      this.selectAllSettings();
      this.markClipboardHint();
      this.qmq.setVisibleWhen(this.apl);
   }

   private void qqq(GuiRenderContext var1, Entity var2, boolean var3, int var4, int var5, float var6, float var7, String var8, float var9) {
      long var10 = var1.nvg;
      NanoVG.nvgFontSize(var10, 24.0F);
      NanoVG.nvgFontFace(var10, GlyphTextUtil.encode("icons"));
      NanoVG.nvgFillColor(var10, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
      GlyphTextUtil.setTextAlign(var10, 18);
      float var12 = 0.0F;
      float var13 = 0.0F;
      if (!(var9 > this.hdl.zcq())) {
         if (var9 > this.hdl.pop()) {
            var13 = var12 = (var9 - this.hdl.pop()) / (1.0F * this.hdl.qp());
            if ((Integer)this.yoa.getValue() != 0) {
               if (var9 < ((Integer)this.yoa.getValue()).intValue()) {
                  var13 = var9 / (((Integer)this.yoa.getValue()).intValue() * 1.0F);
               } else {
                  var13 = 1.0F;
               }
            }

            var13 = Math.max(0.0F, Math.min(var13, 1.0F));
            float var14 = this.ojv.pop() + (this.ojv.zcq() - this.ojv.pop()) * var12;
            var6 = (float)Math.toRadians(var6);
            NanoVG.nvgSave(var10);
            NanoVG.nvgTranslate(
               var10,
               var4 + var14 * (float)Math.cos(var6),
               var5 + var14 * (float)Math.sin(var6) * (this.izh.getValue() ? (float)Math.sin(Math.toRadians(var7)) : 1.0F)
            );
            if (var7 < 0.0F && (Boolean)this.izh.getValue()) {
               NanoVG.nvgRotate(var10, -var6);
            } else {
               NanoVG.nvgRotate(var10, var6);
            }

            NanoVG.nvgFontSize(var10, (Float)this.sjj.getValue() / 3.0F);
            NanoVG.nvgFontFace(var10, GlyphTextUtil.encode("icons"));
            GlyphTextUtil.setTextAlign(var10, 18);
            if (TrajectoriesModule.bf(var2) != null) {
               int var15 = System.currentTimeMillis() % 500L <= 250L ? 2 : 1;
               NanoVG.nvgFillColor(var10, GuiRenderContext.rgba(30 * var15, 30 * var15, 30 * var15, 140, GuiRenderContext.colorScratch));

               for (int var16 = -var15; var16 <= var15; var16++) {
                  for (int var17 = -var15; var17 <= var15; var17++) {
                     GlyphTextUtil.drawTextBuffer(var10, var16, var17, GuiRenderContext.iconBuffer(128640));
                  }
               }

               NanoVG.nvgFillColor(var10, this.ljg.xam(1.0F, GuiRenderContext.shadowColor));
               GlyphTextUtil.drawTextBuffer(var10, 0.0F, 0.0F, GuiRenderContext.iconBuffer(128640));
               NanoVG.nvgRestore(var10);
            } else {
               NVGColor var22;
               if (var3) {
                  var22 = this.gav.xam(1.0F, GuiRenderContext.shadowColor);
               } else {
                  NVGColor var23 = this.nrm.xam(1.0F, GuiRenderContext.colorScratch);
                  NVGColor var25 = this.acj.xam(1.0F, GuiRenderContext.colorScratch2);
                  var22 = GuiRenderContext.rgba(
                     (int)(255.0F * (var23.r() + (var25.r() - var23.r()) * var13)),
                     (int)(255.0F * (var23.g() + (var25.g() - var23.g()) * var13)),
                     (int)(255.0F * (var23.b() + (var25.b() - var23.b()) * var13)),
                     255,
                     GuiRenderContext.shadowColor
                  );
               }

               NanoVG.nvgFillColor(var10, GuiRenderContext.rgba(0, 0, 0, 140, GuiRenderContext.colorScratch));

               for (int var24 = -1; var24 <= 1; var24++) {
                  for (int var26 = -1; var26 <= 1; var26++) {
                     GlyphTextUtil.drawTextBuffer(var10, var24, var26, GuiRenderContext.iconBuffer(59230));
                  }
               }

               NanoVG.nvgFillColor(var10, var22);
               GlyphTextUtil.drawTextBuffer(var10, 0.0F, 0.0F, GuiRenderContext.iconBuffer(59230));
               NanoVG.nvgRestore(var10);
            }
         }
      }
   }

   @Override
   public boolean csu(Entity var1) {
      return super.csu(var1);
   }

   public void wj(WorldRenderOverlayEvent var1) {
      GuiRenderContext var2 = var1.getContext();
      float var3 = var2.tickDelta;
      LocalPlayer var4 = Minecraft.getInstance().player;
      Vec3 var5 = GameInteractionUtil.getLerpedPos(var4, var3);
      float var6 = GameInteractionUtil.getLerpedYaw(var4, var3);
      float var7 = var4.getViewXRot(var3);
      long var8 = var2.nvg;
      if ((Boolean)this.apl.getValue()) {
         int var10 = (Integer)this.qmq.getValue();
         int var11 = 0;

         for (int var12 = var10; var12 < this.hdl.zcq(); var12 += var10) {
            float var13 = this.ojv.pop() + (var12 - this.hdl.pop()) / (1.0F * this.hdl.qp()) * this.ojv.qp();
            NanoVG.nvgBeginPath(var8);

            for (int var14 = 0; var14 < 361; var14++) {
               double var15 = Math.toRadians(var14 + 90);
               float var17 = var1.getContext().framebufferWidth / 2 + var13 * (float)Math.cos(var15);
               float var18 = var1.getContext().framebufferHeight / 2 + var13 * (float)Math.sin(var15) * (this.izh.getValue() ? (float)Math.sin(Math.toRadians(var7)) : 1.0F);
               if (var14 == 0) {
                  NanoVG.nvgMoveTo(var8, var17, var18);
               } else {
                  NanoVG.nvgLineTo(var8, var17, var18);
               }
            }

            if (var11++ % 2 == 0) {
               NanoVG.nvgStrokeWidth(var8, 3.0F);
               NanoVG.nvgStrokeColor(var8, GuiRenderContext.rgba(60, 60, 60, 80, GuiRenderContext.colorScratch));
               NanoVG.nvgStroke(var8);
               NanoVG.nvgStrokeWidth(var8, 2.0F);
               NanoVG.nvgStrokeColor(var8, GuiRenderContext.rgba(120, 120, 120, 220, GuiRenderContext.colorScratch));
               NanoVG.nvgStroke(var8);
            } else {
               NanoVG.nvgStrokeWidth(var8, 3.0F);
               NanoVG.nvgStrokeColor(var8, GuiRenderContext.rgba(60, 60, 60, 80, GuiRenderContext.colorScratch));
               NanoVG.nvgStroke(var8);
               NanoVG.nvgStrokeWidth(var8, 2.0F);
               NanoVG.nvgStrokeColor(var8, GuiRenderContext.rgba(220, 220, 220, 220, GuiRenderContext.colorScratch));
               NanoVG.nvgStroke(var8);
            }
         }
      }

      for (Entity var26 : WorldEntityUtil.getEntities()) {
         if (!(var26 instanceof LocalPlayer)) {
            boolean var29 = this.bd.tdv(var26);
            if ((var29 || this.csu(var26)) && (!var29 || (Boolean)this.dcv.getValue())) {
               Vec3 var32 = GameInteractionUtil.getLerpedPos(var26, var3);
               Vec3 var35 = var32.subtract(var5);
               double var38 = var35.lengthSqr();
               if (!(var38 <= 4.0) && (!(Boolean)this.uct.getValue() || !Render3DUtil.isPointInFrustum(var35))) {
                  String var41 = var26 instanceof Player ? ((Player)var26).getName().getString() : GlyphTextUtil.encode("");
                  float var42 = RotationMathUtil.getRotationTo(var5, var32).x;
                  this.qqq(var2, var26, var29, var2.framebufferWidth / 2, var2.framebufferHeight / 2, RotationMathUtil.angleDifference(var42, var6) - 90.0F, var7, var41, (float)Math.sqrt(var38));
               }
            }
         }
      }

      if ((Boolean)this.ctd.getValue()) {
         for (Entity var27 : WorldEntityUtil.getEntities()) {
            if (TrajectoriesModule.bf(var27) != null) {
               Vec3 var30 = GameInteractionUtil.getLerpedPos(var27, var3);
               Vec3 var33 = var30.subtract(var5);
               double var36 = var33.lengthSqr();
               if (!(var36 <= 4.0)) {
                  float var16 = RotationMathUtil.getRotationTo(var5, var30).x;
                  this.qqq(var2, var27, false, var2.framebufferWidth / 2, var2.framebufferHeight / 2, RotationMathUtil.angleDifference(var16, var6) - 90.0F, var7, GlyphTextUtil.encode(""), (float)Math.sqrt(var36));
               }
            }
         }
      }

      if ((Boolean)this.oalh.getValue()) {
         NanoVG.nvgFontSize(var8, 12.0F);
         NanoVG.nvgFontFace(var8, InterfaceModule.fontRegular);
         NanoVG.nvgFillColor(var8, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
         GlyphTextUtil.setTextAlign(var8, 18);
         int var25 = (Integer)this.qmq.getValue();

         for (int var28 = var25; var28 < this.hdl.zcq(); var28 += var25) {
            float var31 = (var28 - this.hdl.pop()) / (1.0F * this.hdl.qp());
            NVGColor var34 = this.nrm.xam(1.0F, GuiRenderContext.colorScratch);
            NVGColor var37 = this.acj.xam(1.0F, GuiRenderContext.colorScratch2);
            float var39 = this.ojv.pop() + (var28 - this.hdl.pop()) / (1.0F * this.hdl.qp()) * this.ojv.qp();
            double var40 = Math.toRadians(-45.0);
            float var43 = var1.getContext().framebufferWidth / 2 + var39 * (float)Math.cos(var40);
            float var19 = var1.getContext().framebufferHeight / 2 + var39 * (float)Math.sin(var40) * (this.izh.getValue() ? (float)Math.sin(Math.toRadians(var7)) : 1.0F);
            NanoVG.nvgSave(var8);
            NanoVG.nvgTranslate(var8, var43, var19);
            NanoVG.nvgRotate(var8, (float)(-var40));
            NanoVG.nvgFillColor(var8, GuiRenderContext.rgba(0, 0, 0, 120, GuiRenderContext.shadowColor));
            String var20 = (int)var39 + GlyphTextUtil.encode("m");

            for (int var21 = -1; var21 <= 1; var21++) {
               for (int var22 = -1; var22 <= 1; var22++) {
                  GlyphTextUtil.drawText(var8, var21, var22, var20);
               }
            }

            NanoVG.nvgFillColor(
               var8,
               GuiRenderContext.rgba(
                  (int)(255.0F * (var34.r() + (var37.r() - var34.r()) * var31)),
                  (int)(255.0F * (var34.g() + (var37.g() - var34.g()) * var31)),
                  (int)(255.0F * (var34.b() + (var37.b() - var34.b()) * var31)),
                  512,
                  GuiRenderContext.colorScratch
               )
            );
            GlyphTextUtil.drawText(var8, 0.0F, 0.0F, var20);
            NanoVG.nvgRestore(var8);
         }
      }
   }
}
