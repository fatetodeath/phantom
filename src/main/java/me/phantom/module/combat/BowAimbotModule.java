package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.KeybindSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.FloatPoint2D;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.combat.ProjectilePathSimulator;
import me.phantom.util.combat.ProjectileType;
import me.phantom.util.WorldEntityUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.nanovg.NanoVG;

@ModuleMetadata(
   x = 5.5,
   y = -60.0,
   z = -34.5
)
public class BowAimbotModule extends BaseModule {
   public final ToggleSetting yzy = new ToggleSetting(this, GlyphTextUtil.encode("autoaim"), true);
   public final IntegerSliderSetting kyp = new IntegerSliderSetting(this, GlyphTextUtil.encode("range"), 0, 60, 120);
   public final FloatSliderSetting yxs = new FloatSliderSetting(this, GlyphTextUtil.encode("fov"), 0.0F, 80.0F, 180.0F);
   public final FloatSliderSetting odz = new FloatSliderSetting(this, GlyphTextUtil.encode("prediction"), 0.0F, 0.5F, 2.5F);
   public final ToggleSetting mhi = new ToggleSetting(this, GlyphTextUtil.encode("drawline"), true);
   public final KeybindSetting roo = new KeybindSetting(this, GlyphTextUtil.encode("activate_key"), 100000);
   public final ToggleSetting cx = new ToggleSetting(this, GlyphTextUtil.encode("autotrigger"), false);
   public AimHelperModule ia;
   public FloatPoint2D to = new FloatPoint2D();

   public BowAimbotModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("BowAimbot"));
      this.roo.setVisibilitySupplier(() -> !(Boolean)this.cx.getValue());
   }

   @Override
   public String getValueSuffix() {
      return RotationMathUtil.formatTwoDecimals(this.odz.leo()) + GlyphTextUtil.encode("s - ") + RotationMathUtil.formatTwoDecimals(this.odz.wgp()) + GlyphTextUtil.encode("s");
   }

   public Vec2 vbu(Vec3 var1, float var2) {
      double var3 = var1.length();
      if (var3 > RotationMathUtil.square(((Integer)this.kyp.getValue()).intValue())) {
         return null;
      } else {
         double var5 = Math.sqrt(Math.pow(var1.x, 2.0) + Math.pow(var1.z, 2.0));
         if (var5 < 1.0E-7) {
            return null;
         } else {
            float var7 = (float)(Math.atan2(var1.z, var1.x) - (Math.PI / 2));
            double var8 = 0.006F;
            double var10 = var2 * var2 * var2 * var2 - var8 * (var8 * (var5 * var5) + 2.0 * var1.y * (var2 * var2));
            float var12 = (float)(-Math.atan((var2 * var2 - Math.sqrt(var10)) / (var8 * var5)));
            return !Float.isNaN(var7) && !Float.isNaN(var12) ? new Vec2((float)Math.toDegrees(var7), (float)Math.toDegrees(var12)) : null;
         }
      }
   }

   public void wj(WorldRenderOverlayEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      ItemStack var3 = var2.getMainHandItem();
      if (var3 == null || var3.getItem() instanceof BowItem) {
         if (var2.getUseItemRemainingTicks() > 0) {
            int var4 = 72000 - var2.getUseItemRemainingTicks();
            float var5 = BowItem.getPowerForTime(var4);
            if (!(var5 <= 0.1F)) {
               int var6 = (int)RotationMathUtil.square(((Integer)this.kyp.getValue()).intValue());
               GuiRenderContext var7 = var1.getContext();
               float var8 = var7.tickDelta;
               long var9 = var1.getContext().nvg;
               GlyphTextUtil.setTextAlign(var9, 18);
               NanoVG.nvgFontSize(var9, 18.0F);
               ProjectileType var11 = ProjectileType.qju;
               Vec3 var12 = var2.getEyePosition();
               float var13 = GameInteractionUtil.getLerpedYaw(var2, var8);
               float var14 = GameInteractionUtil.getLerpedPitch(var2, var8);
               float var15 = var11.ayy(var2);
               Vec3 var16 = RotationMathUtil.getLookVector(var13, var14).scale(var15);

               for (Entity var18 : WorldEntityUtil.getEntities()) {
                  if (PhantomClient.targetSelector.csu(var18) && var2.hasLineOfSight(var18)) {
                     Vec3 var19 = GameInteractionUtil.getLerpedPos(var18, var8).add(0.0, var18.getBbHeight() / 2.0F, 0.0);
                     Vec3 var20 = var19.subtract(var12);
                     if (!(var20.lengthSqr() > var6) && Render3DUtil.worldToScreen(var20, this.to)) {
                        float var21 = this.to.axn;
                        float var22 = this.to.oaz;
                        Vec2 var23 = this.vbu(var20, var5);
                        if (var23 != null && Render3DUtil.worldToScreen(RotationMathUtil.getLookVector(var23.x, var23.y).scale(100.0), this.to)) {
                           float var24 = this.to.oaz;
                           byte var25 = 3;
                           int var26 = (int)(var22 - var24);
                           boolean var27 = false;
                           int var28 = 0;
                           ProjectilePathSimulator var29 = new ProjectilePathSimulator(Minecraft.getInstance().level, var18, var12, var16, var11);

                           while (!var29.pln()) {
                              if (++var28 > 200) {
                                 break;
                              }

                              Vec3 var30 = var29.swx();
                              var29.tkm();
                              if (var18.getBoundingBox().intersects(var30, var30.add(var29.udz()))) {
                                 var27 = true;
                                 if (this.roo.rrq() || (Boolean)this.cx.getValue()) {
                                    Minecraft.getInstance().options.keyUse.setDown(false);
                                 }
                                 break;
                              }
                           }

                           NanoVG.nvgSave(var9);
                           NanoVG.nvgTranslate(var9, var21, var22);
                           NanoVG.nvgStrokeWidth(var9, 4.0F);
                           NanoVG.nvgBeginPath(var9);
                           NanoVG.nvgMoveTo(var9, 0.0F, -var25 - var26);
                           NanoVG.nvgLineTo(var9, 0.0F, -var25);
                           NanoVG.nvgStrokeColor(var9, GuiRenderContext.rgba(20, 20, 20, 255, GuiRenderContext.shadowColor));
                           NanoVG.nvgStroke(var9);
                           NanoVG.nvgBeginPath(var9);
                           NanoVG.nvgCircle(var9, 0.0F, 0.0F, var25);
                           NanoVG.nvgFillColor(var9, GuiRenderContext.rgba(50, 50, 50, 255, GuiRenderContext.shadowColor));
                           NanoVG.nvgFill(var9);
                           NanoVG.nvgBeginPath(var9);
                           NanoVG.nvgCircle(var9, 0.0F, 0.0F, var25 - 1);
                           NanoVG.nvgFillColor(var9, GuiRenderContext.rgba(var27 ? 50 : 255, var27 ? 255 : 50, 50, 255, GuiRenderContext.shadowColor));
                           NanoVG.nvgFill(var9);
                           NanoVG.nvgBeginPath(var9);
                           NanoVG.nvgCircle(var9, 0.0F, -var26, var25);
                           NanoVG.nvgFillColor(var9, GuiRenderContext.rgba(50, 50, 50, 255, GuiRenderContext.shadowColor));
                           NanoVG.nvgFill(var9);
                           NanoVG.nvgBeginPath(var9);
                           NanoVG.nvgCircle(var9, 0.0F, -var26, var25 - 1);
                           NanoVG.nvgFillColor(var9, GuiRenderContext.rgba(var27 ? 50 : 255, var27 ? 255 : 50, 50, 255, GuiRenderContext.shadowColor));
                           NanoVG.nvgFill(var9);
                           NanoVG.nvgRestore(var9);
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
