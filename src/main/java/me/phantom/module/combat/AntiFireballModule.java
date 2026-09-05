package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.mc.MinecraftClientBridge;
import me.phantom.util.WorldEntityUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class AntiFireballModule extends BaseModule {
   public static final int wx = 11;
   public AimHelperModule ia;
   public boolean kk = false;
   public FloatSliderSetting yxs = new FloatSliderSetting(this, GlyphTextUtil.encode("fov"), 20.0F, 90.0F, 180.0F);
   private FloatSliderSetting em = new FloatSliderSetting(this, GlyphTextUtil.encode("reach"), 2.5F, 4.0F, 5.0F);
   public FloatSliderSetting qrd = new FloatSliderSetting(this, GlyphTextUtil.encode("aim_reach"), 0.0F, 1.0F, 15.0F);

   public AntiFireballModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("AntiFireball"));
      this.markClipboardHint();
   }

   @Override
   public String getValueSuffix() {
      return RotationMathUtil.formatTwoDecimals((Float)this.em.getValue());
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      float var3 = var2.getYaw();
      Vec3d var4 = var2.getEyePos();
      float var5 = Float.MAX_VALUE;
      Vec2f var6 = null;
      Entity var7 = null;
      boolean var8 = false;

      for (Entity var10 : WorldEntityUtil.getEntities()) {
         if (var10 instanceof FireballEntity || var10 instanceof SmallFireballEntity) {
            Vec3d var11 = var10.getPos().add(0.0, var10.getHeight() / 2.0F, 0.0);
            double var12 = var11.squaredDistanceTo(var4);
            if (!(var12 > RotationMathUtil.square((Float)this.em.getValue() + (Float)this.qrd.getValue()))) {
               Vec2f var14 = RotationMathUtil.getRotationTo(var4, var11);
               if (!Float.isNaN(var14.x) && !Float.isNaN(var14.y)) {
                  float var15 = RotationMathUtil.angleDifference(var14.x, var3);
                  if (!(var15 > (Float)this.yxs.getValue()) && (var6 == null || var5 > var15)) {
                     var5 = var15;
                     var6 = var14;
                     var7 = var10;
                     var8 = var12 <= RotationMathUtil.square(((Float)this.em.getValue()).floatValue());
                  }
               }
            }
         }
      }

      if (var6 != null) {
         this.ia.vc(11, var6.x - var2.getYaw(), var6.y - var2.getPitch(), true);
         if (var8) {
            if (this.kk) {
               ((MinecraftClientBridge)MinecraftClient.getInstance()).getInteractionManager().attackEntity(var2, var7);
               var2.swingHand(Hand.MAIN_HAND);
               this.kk = false;
            } else {
               this.kk = true;
            }
         }
      }
   }
}
