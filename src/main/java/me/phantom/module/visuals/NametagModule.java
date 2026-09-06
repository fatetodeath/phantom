package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.PreEntityRenderEvent;
import me.phantom.event.render.UiRenderEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.FriendModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.module.misc.AntiBotModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.FloatPoint2D;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.CameraType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;
import org.lwjgl.nanovg.NanoVG;
public class NametagModule extends BaseModule {
   public final ToggleSetting bzf = new ToggleSetting(this, GlyphTextUtil.encode("drawmcnametag"), false);
   public final ToggleSetting pkt = new ToggleSetting(this, GlyphTextUtil.encode("drawhealthbar"), true);
   public final FloatSliderSetting zsr = new FloatSliderSetting(this, GlyphTextUtil.encode("size"), 4.0F, 7.0F, 17.0F);
   public final ToggleSetting zfe = new ToggleSetting(this, GlyphTextUtil.encode("mobs"), false);
   public final ToggleSetting abw = new ToggleSetting(this, GlyphTextUtil.encode("invisible"), false);
   public final ToggleSetting ptg = new ToggleSetting(this, GlyphTextUtil.encode("items"), true);
   public final ToggleSetting nne = new ToggleSetting(this, GlyphTextUtil.encode("items_lookAt"), true);
   public final IntegerSliderSetting zeq = new IntegerSliderSetting(this, GlyphTextUtil.encode("items_lookAt_size"), 20, 30, 60);
   public final ToggleSetting oyv = new ToggleSetting(this, GlyphTextUtil.encode("items_quad"), false);
   public final IntegerSliderSetting wmm = new IntegerSliderSetting(this, GlyphTextUtil.encode("items_opacity"), 0, 50, 100);
   public final FloatSliderSetting vvw = new FloatSliderSetting(this, GlyphTextUtil.encode("y_offset"), 0.0F, 0.2F, 0.5F);
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 0, 70, 100);
   public final ColorSetting yeg = new ColorSetting(this, GlyphTextUtil.encode("col_friends"), Color.blue);
   public final ColorSetting juy = new ColorSetting(this, GlyphTextUtil.encode("col_near"), Color.red);
   public final ColorSetting ite = new ColorSetting(this, GlyphTextUtil.encode("col_far"), Color.green);
   public final FloatSliderSetting ulx = new FloatSliderSetting(this, GlyphTextUtil.encode("near_range"), 16.0F, 64.0F, 128.0F);
   public final ToggleSetting wco = new ToggleSetting(this, GlyphTextUtil.encode("self_render"), false);
   public FriendModule bd;
   public AntiBotModule pg;
   public final FloatPoint2D ssa = new FloatPoint2D();

   public NametagModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("Nametag"));
      this.selectAllSettings();
      this.markClipboardHint();
      this.nne.setVisibleWhen(this.ptg);
      this.oyv.setVisibleWhen(this.ptg);
      this.zeq.setVisibleWhen(this.ptg);
      this.wmm.setVisibleWhen(this.ptg);
   }

   public void lud(PreEntityRenderEvent var1) {
      if (!(Boolean)this.bzf.getValue()) {
         var1.setCancelled(true);
      }
   }

   public void ztv(GuiRenderContext var1, LivingEntity var2, float var3, float var4, float var5, double var6) {
      float var8 = var5 * 0.1F;
      NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontBold);
      NanoVG.nvgFontSize(var1.nvg, var5);
      Component var9 = var2.getDisplayName();
      if (!var1.stripColorCodes(var9.getString()).replace(GlyphTextUtil.encode("\t"), GlyphTextUtil.encode("")).replace(GlyphTextUtil.encode(" "), GlyphTextUtil.encode("")).isEmpty()) {
         if ((Boolean)this.ptg.getValue()) {
            float var10 = (Float)this.zsr.getValue() * 8.0F;
            boolean var11 = true;
            if ((Boolean)this.nne.getValue()) {
               LocalPlayer var12 = Minecraft.getInstance().player;
               Vec3 var13 = GameInteractionUtil.getLerpedEyePos(var12, var1.tickDelta);
               Vec3 var14 = GameInteractionUtil.getLerpedPos(var2, var1.tickDelta);
               Vec3 var15 = var14.subtract(var13);
               float[] var16 = RotationMathUtil.getScreenBoundingBox(var15, var2);
               if (var16 == null) {
                  var11 = false;
               } else {
                  float var17 = var16[0];
                  float var18 = var16[1];
                  float var19 = var16[2];
                  float var20 = var16[3];
                  Vector2f var21 = new Vector2f(var1.framebufferWidth / 2, var1.framebufferHeight / 2);
                  var11 = var21.x >= var17 && var21.y >= var18 && var21.x <= var19 && var21.y <= var20;
                  if (!var11) {
                     float var22 = Math.max(0.0F, Math.max(var17 - var21.x, var21.x - var19));
                     float var23 = Math.max(0.0F, Math.max(var18 - var21.y, var21.y - var20));
                     float var24 = var22 * var22 + var23 * var23;
                     var11 = var24 <= RotationMathUtil.square((Integer)this.zeq.getValue() * 10);
                  }
               }
            }

            if (var11) {
               EquipmentSlot[] var27 = new EquipmentSlot[]{
                  EquipmentSlot.MAINHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.OFFHAND, EquipmentSlot.LEGS, EquipmentSlot.FEET
               };
               Boolean var28 = (Boolean)this.oyv.getValue();
               int var29 = var28 ? (int)(var10 / (var27.length / 2)) : (int)(var10 / var27.length);

               for (int var30 = 0; var30 < var27.length; var30++) {
                  ItemStack var31 = var2.getItemBySlot(var27[var30]);
                  float var32;
                  float var33;
                  float var34;
                  if (var28) {
                     var34 = var29 - var8 * 0.5F;
                     var32 = -var10 / 2.0F + var29 * (var30 % 3) + var8 * 0.5F;
                     var33 = var4 - var5 - var8 * 2.0F - var29 * (var30 / 3) - var34 * 1.5F;
                  } else {
                     var34 = var29 - var8 * 0.5F;
                     var32 = -var10 / 2.0F + var29 * var30 + var8 * 0.5F;
                     var33 = var4 - var5 - var8 * 2.0F - var34 * 1.5F;
                  }

                  if ((Integer)this.wmm.getValue() > 0) {
                     NanoVG.nvgBeginPath(var1.nvg);
                     NanoVG.nvgRoundedRect(var1.nvg, var32, var33, var34, var34, var8);
                     NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(10, 10, 10, (int)(((Integer)this.wmm.getValue()).intValue() / 100.0F * 255.0F), GuiRenderContext.colorScratch));
                     NanoVG.nvgFill(var1.nvg);
                  }

                  this.vxd(var1, var31, 0, var32, var33, var34 * 0.8F, var8);
               }
            }
         }

         float var25 = var1.measureTextComponent(var9, var5);
         float var26 = ((Integer)this.ep.getValue()).intValue() / 100.0F;
         if (var26 > 0.0F) {
            if (this.bd.tdv(var2)) {
               this.yeg.xam(var26, GuiRenderContext.shadowColor);
            } else {
               var1.lerpColor(
                  this.juy.xam(1.0F, GuiRenderContext.colorScratch),
                  this.ite.xam(1.0F, GuiRenderContext.colorScratch2),
                  GuiRenderContext.shadowColor,
                  Math.min(1.0F, Math.max(0.0F, (float)(var6 / ((Float)this.ulx.getValue()).floatValue()))),
                  var26
               );
            }
         } else {
            GuiRenderContext.rgba(0, 0, 0, 0, GuiRenderContext.shadowColor);
         }

         if ((Boolean)this.pkt.getValue()) {
            this.ezn(var1, var3 - var25 / 2.0F, var4, var25, var5, var8, this.qwt(var2) / var2.getMaxHealth());
         }

         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, GlyphTextUtil.encode("renderName"), var9.toString(), Arrays.toString(var9.toString().getBytes(StandardCharsets.UTF_8)));
         }

         if (var25 > 0.0F) {
            this.xkr(var1, var9, var3 - var8 - var25 / 2.0F, var4 - var5 - var8 * 2.0F - var5 * 0.05F, var25, var5, var8);
         }
      }
   }

   public float qwt(LivingEntity var1) {
      return var1.getHealth();
   }

   public void xkr(GuiRenderContext var1, Component var2, float var3, float var4, float var5, float var6, float var7) {
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var3, var4, var5 + var7 * 2.0F, var6 + var7 * 2.0F, var6 * 0.2F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.shadowColor);
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgStrokeWidth(var1.nvg, var6 * 0.1F);
      NanoVG.nvgStrokeColor(var1.nvg, GuiRenderContext.rgba(0, 0, 0, (Integer)this.ep.getValue(), GuiRenderContext.shadowColor));
      NanoVG.nvgStroke(var1.nvg);
      var1.drawTextComponent(var3 + var7, var4 + var6 / 2.0F + var7 * 1.5F, var2, var6, false, 17, 1.0F, 1.0F);
   }

   public void vxd(GuiRenderContext var1, ItemStack var2, int var3, float var4, float var5, float var6, float var7) {
      if (var2 != null) {
         PhantomClient.uiManager.sdd().tzk(var2, var4 + var7 / 2.0F, var5 + var7 / 2.0F, var6 + var7, var6 + var7, 1.0F);
      }
   }

   public void ezn(GuiRenderContext var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var5 * 0.1F;
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var2, var3, var4, var8, var5 * 0.05F);
      NanoVG.nvgFillPaint(
         var1.nvg,
         NanoVG.nvgLinearGradient(var1.nvg, 0.0F, var3, 0.0F, var3 + var8, GuiRenderContext.rgba(255, 0, 0, 255, GuiRenderContext.colorScratch), GuiRenderContext.rgba(130, 0, 0, 255, GuiRenderContext.colorScratch2), GuiRenderContext.paintScratch)
      );
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgLinearGradient(var1.nvg, 0.0F, var3, 0.0F, var3 + var8, GuiRenderContext.rgba(0, 255, 0, 255, GuiRenderContext.colorScratch), GuiRenderContext.rgba(0, 130, 0, 255, GuiRenderContext.colorScratch2), GuiRenderContext.paintScratch);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var2, var3, var4 * var7, var8, var5 * 0.05F);
      NanoVG.nvgFillPaint(var1.nvg, GuiRenderContext.paintScratch);
      NanoVG.nvgFill(var1.nvg);
   }

   public void up(UiRenderEvent var1) {
      GuiRenderContext var2 = var1.getContext();
      float var3 = var2.tickDelta;
      LocalPlayer var4 = Minecraft.getInstance().player;
      Vec3 var5 = GameInteractionUtil.getLerpedEyePos(var4, var3);
      float var6 = 1.0F;

      for (Entity var8 : WorldEntityUtil.getEntities()) {
         if ((
               var8 instanceof Player
                  ? !(var8 instanceof LocalPlayer)
                     || (Boolean)this.wco.getValue() && Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON
                  : var8 instanceof Mob && (Boolean)this.zfe.getValue()
            )
            && (!var8.isInvisible() || (Boolean)this.abw.getValue())
            && !this.pg.uei(var8)) {
            LivingEntity var9 = (LivingEntity)var8;
            float var10 = var8.getBbWidth();
            float var11 = var8.getBbHeight() + (var8 instanceof Bat ? 0.4F : 0.0F);
            boolean var12 = false;
            Vec3 var13 = GameInteractionUtil.getLerpedPos(var8, var3);
            Vec3 var14 = var13.subtract(var5);
            Float var15 = (Float)this.vvw.getValue();
            Vec3[] var16 = new Vec3[]{
               var14.add(0.0, var11 + var15, 0.0),
               var14.add(-var10 / 2.0F, var15.floatValue(), -var10 / 2.0F),
               var14.add(var10 / 2.0F, var15.floatValue(), -var10 / 2.0F),
               var14.add(-var10 / 2.0F, var15.floatValue(), var10 / 2.0F),
               var14.add(var10 / 2.0F, var15.floatValue(), var10 / 2.0F),
               var14.add(-var10 / 2.0F, var15 + var11, -var10 / 2.0F),
               var14.add(var10 / 2.0F, var15 + var11, -var10 / 2.0F),
               var14.add(-var10 / 2.0F, var15 + var11, var10 / 2.0F),
               var14.add(var10 / 2.0F, var15 + var11, var10 / 2.0F)
            };

            for (Vec3 var20 : var16) {
               if (Render3DUtil.isPointInFrustum(var20)) {
                  var12 = true;
                  break;
               }
            }

            if (var12) {
               float var27 = Float.MIN_VALUE;
               float var28 = Float.MIN_VALUE;
               float var29 = Float.MAX_VALUE;
               float var30 = Float.MAX_VALUE;
               float var21 = 0.0F;

               for (int var22 = 0; var22 < var16.length; var22++) {
                  Vec3 var23 = var16[var22];
                  if (Render3DUtil.worldToScreen(var23, true, this.ssa)) {
                     if (var22 == 0) {
                        var21 = this.ssa.axn;
                     }

                     var27 = Math.max(this.ssa.axn, var27);
                     var28 = Math.max(this.ssa.oaz, var28);
                     var29 = Math.min(this.ssa.axn, var29);
                     var30 = Math.min(this.ssa.oaz, var30);
                  }
               }

               float var31 = var27 - var29;
               float var32 = var28 - var30;
               if (var21 == 0.0F) {
                  var21 = var29 + var31 / 2.0F;
               }

               float var24 = var30 + var32 / 2.0F;
               double var25 = Math.max(8.0, var14.length());
               NanoVG.nvgSave(var2.nvg);
               NanoVG.nvgTranslate(var2.nvg, var21, var24 - var32 / 2.0F);
               this.ztv(var2, var9, 0.0F, 0.0F, (Float)this.zsr.getValue() * 8.0F * var6 * (1.0F / (float)Math.sqrt(var25)), var25);
               NanoVG.nvgRestore(var2.nvg);
            }
         }
      }
   }
}
