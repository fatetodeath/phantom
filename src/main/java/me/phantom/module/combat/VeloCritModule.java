package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.entity.PlayerVelocityEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.mc.MinecraftClientBridge;
import me.phantom.util.WorldEntityUtil;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
public class VeloCritModule extends BaseModule {
   public ToggleSetting xqe = new ToggleSetting(this, GlyphTextUtil.encode("silent"), true);
   public FloatSliderSetting ro = new FloatSliderSetting(this, GlyphTextUtil.encode("attack_range"), 0.5F, 3.0F, 5.0F);
   private FloatSliderSetting shf = new FloatSliderSetting(this, GlyphTextUtil.encode("aim_range"), 0.0F, 5.0F, 8.0F);
   public ToggleSetting pcu = new ToggleSetting(this, GlyphTextUtil.encode("ignore_actuate"), true);
   private FloatSliderSetting mc = new FloatSliderSetting(this, GlyphTextUtil.encode("actuate_range"), 0.0F, 5.0F, 8.0F);
   public FloatRangeSliderSetting ag = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("aim_speed"), 0.0F, 0.05F, 0.1F, 0.3F);
   public FloatSliderSetting yxs = new FloatSliderSetting(this, GlyphTextUtil.encode("fov"), 30.0F, 60.0F, 180.0F);
   public AimHelperModule ia;
   public int pzm = 0;
   public boolean io;
   public int ur;
   public int hitDelayTicks;

   public VeloCritModule() {
      super(ModuleCategory.vne, ClientPlayStyle.ats, GlyphTextUtil.encode("VeloCrit"));
      this.mc.setVisibleWhen(this.pcu);
      this.markBeta();
   }

   public void sau(KeyStateEvent var1) {
      if (var1.by().getName().equals("key.back") && this.pzm > 0) {
         var1.ywz(true);
      } else if (var1.by().getName().equals("key.sprint") && this.pzm > 0) {
         var1.ywz(false);
      }
   }

   public void bhp(PlayerVelocityEvent var1) {
      Minecraft var2 = Minecraft.getInstance();
      LocalPlayer var3 = var2.player;
      if (var2.screen != null && var1.tg().equals(var3) && var1.waw().y > 0.0 && this.fi()) {
         this.pzm = 4;
      }
   }

   public boolean fi() {
      Minecraft var1 = Minecraft.getInstance();
      LocalPlayer var2 = var1.player;
      if ((Boolean)this.pcu.getValue()) {
         return true;
      } else {
         double var3 = RotationMathUtil.square((Float)this.mc.getValue() + 1.0F);
         Vec3 var5 = var2.getEyePosition();

         for (Entity var7 : WorldEntityUtil.getEntities()) {
            if (var7 instanceof LivingEntity var8 && !(var7 instanceof LocalPlayer)) {
               if (var8.deathTime > 0) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("dead"));
                  }
               } else if (!PhantomClient.targetSelector.csu(var8)) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("filter"));
                  }
               } else {
                  Vec3 var9 = GameInteractionUtil.getLerpedPos(var8, PhantomClient.uiManager.getContext().tickDelta);
                  if (!(var5.distanceToSqr(var9) > var3) && !var8.isInvisible()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   @Override
   public boolean ma() {
      return this.pzm > 0;
   }

   public void tjy(TickEvent var1) {
      Minecraft var2 = Minecraft.getInstance();
      LocalPlayer var3 = var2.player;
      if (this.pzm > 0 && var2.screen == null) {
         if (var3.onGround()) {
            this.hitDelayTicks = 0;
            if (this.pzm > 0) {
               this.pzm--;
               this.io = false;
            }
         } else {
            Vec3 var4 = var3.getEyePosition();
            float var5 = var3.getYRot();
            Entity var6 = (Entity)null;
            Vec2 var7 = (Vec2)null;
            float var8 = Float.MAX_VALUE;
            double var9 = RotationMathUtil.square((Float)this.shf.getValue() + 1.0F);
            double var11 = RotationMathUtil.square(((Float)this.ro.getValue()).floatValue());
            boolean var13 = false;

            for (Entity var15 : WorldEntityUtil.getEntities()) {
               if (var15 instanceof LivingEntity var16 && !(var15 instanceof LocalPlayer)) {
                  if (var16.deathTime > 0) {
                     if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("dead"));
                     }
                  } else if (!PhantomClient.targetSelector.csu(var16)) {
                     if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("filter"));
                     }
                  } else {
                     Vec3 var17 = GameInteractionUtil.getLerpedPos(var16, PhantomClient.uiManager.getContext().tickDelta);
                     if (!(var4.distanceToSqr(var17) > var9) && !var16.isInvisible()) {
                        Vec3 var18 = var15.position()
                           .add(Math.random() * 0.2 - 0.1, var15.getBbHeight() / 4.0F * 3.0F + Math.random() * 0.2 - 0.2, Math.random() * 0.2 - 0.1);
                        Vec2 var19 = RotationMathUtil.getRotationTo(var4, var18);
                        if (!Float.isNaN(var19.x) && !Float.isNaN(var19.y)) {
                           if (var3.hasLineOfSight(var16)) {
                              float var20 = RotationMathUtil.angleDifference(var19.x, var5);
                              if (!(Math.abs(var20) > (Float)this.yxs.getValue()) && (var7 == null || var8 > var20)) {
                                 var8 = var20;
                                 var6 = var15;
                                 var7 = var19;
                                 Vec3 var21 = var4.add(RotationMathUtil.getLookVector(var3.getYRot() + var20, var19.y).scale(3.0));
                                 AABB var22 = var15.getBoundingBox().inflate(var15.getPickRadius());
                                 Optional var23 = var22.clip(var4, var21);
                                 var13 = var22.contains(var4) || var23.isPresent() && var4.distanceToSqr((Vec3)var23.get()) <= var11;
                              }
                           } else if (PhantomClient.traceEnabled) {
                              PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("trace"));
                           }
                        }
                     } else if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("range"));
                     }
                  }
               }
            }

            if (var6 == null) {
               this.ur = 0;
               this.hitDelayTicks = 0;
            } else {
               if (this.hitDelayTicks == 0) {
                  this.hitDelayTicks = 1 + this.ag.cah();
               }

               float var24 = this.hitDelayTicks <= 1 ? 1.0F : Math.min(1.0F, this.ur / (1.0F * this.hitDelayTicks));
               this.ia.vc(20, RotationMathUtil.angleDifference(var7.x, var3.getYRot()) * var24, (var7.y - var3.getXRot()) * var24, (Boolean)this.xqe.getValue());
               if (this.ur < this.hitDelayTicks) {
                  this.ur++;
               }
            }

            if (this.pzm > 0
               && var6 != null
               && !var3.isUnderWater()
               && !var3.isInWater()
               && !var3.onClimbable()
               && !var3.onGround()
               && !var3.onClimbable()
               && !var3.isInWater()
               && !var3.hasEffect(MobEffects.BLINDNESS)
               && var3.getAttackStrengthScale(0.5F) > 0.9F
               && !var3.isPassenger()
               && var13
               && this.ur >= this.hitDelayTicks
               && PhantomClient.fallDistance > 0.0) {
               if (!this.io) {
                  this.io = true;
               } else {
                  this.io = false;
                  ((MinecraftClientBridge) Minecraft.getInstance()).getInteractionManager().attack(var3, var6);
                  var3.swing(InteractionHand.MAIN_HAND);
                  this.pzm = 0;
                  this.ur = 0;
                  this.hitDelayTicks = 0;
               }
            }
         }
      } else {
         this.pzm = 0;
         this.ur = 0;
         this.hitDelayTicks = 0;
      }
   }
}
