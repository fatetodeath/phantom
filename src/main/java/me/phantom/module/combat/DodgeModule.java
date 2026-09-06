package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.WorldEntityUtil;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
public class DodgeModule extends BaseModule {
   private ToggleSetting ssn = new ToggleSetting(this, GlyphTextUtil.encode("only_on_damage"), false);
   private FloatSliderSetting yxs = new FloatSliderSetting(this, GlyphTextUtil.encode("fov"), 30.0F, 60.0F, 180.0F);
   public FloatSliderSetting tah = new FloatSliderSetting(this, GlyphTextUtil.encode("range"), 0.5F, 3.0F, 5.0F);
   private FloatSliderSetting zyv = new FloatSliderSetting(this, GlyphTextUtil.encode("local_hitbox_margin"), 0.0F, 0.4F, 1.0F);
   private FloatRangeSliderSetting aup = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("duration"), 0.1F, 0.5F, 1.5F, 4.0F);
   private int ticksRemaining;
   private int iw;
   private Entity ebk;

   public DodgeModule() {
      super(ModuleCategory.vne, ClientPlayStyle.or, GlyphTextUtil.encode("Dodge"));
      this.markBeta();
   }

   public void sau(KeyStateEvent var1) {
      if (this.ticksRemaining > 0) {
         if (var1.by().getName().equals("key.left")) {
            var1.ywz(this.iw < 0);
         } else if (var1.by().getName().equals("key.right")) {
            var1.ywz(this.iw > 0);
         }
      }
   }

   @Override
   public void onEnable() {
      this.ticksRemaining = 0;
      super.onEnable();
   }

   public boolean wa() {
      this.ebk = null;
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this, "no_target_start");
      }

      for (Entity var2 : WorldEntityUtil.getEntities()) {
         if (var2 instanceof LivingEntity var3 && !(var2 instanceof LocalPlayer) && PhantomClient.targetSelector.csu(var2)) {
            Minecraft var4 = Minecraft.getInstance();
            LocalPlayer var5 = var4.player;
            Vec3 var6 = var5.position();
            Vec3 var7 = var2.getEyePosition();
            Vec2 var8 = RotationMathUtil.getRotationTo(var6, var7);
            if (Math.abs(RotationMathUtil.angleDifference(var5.getYRot(), var8.x)) > (Float)this.yxs.getValue()) {
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, "no_target", "out_of_fov");
               }
            } else {
               Vec3 var9 = var2.getEyePosition().add(RotationMathUtil.getLookVector(var2.getYRot(), var2.getXRot()).scale(((Float)this.tah.getValue()).floatValue()));
               AABB var10 = var5.getBoundingBox().inflate((Float)this.zyv.getValue() / 2.0F);
               Optional var11 = var10.clip(var7, var9);
               boolean var12 = var10.contains(var7)
                  || var11.isPresent() && var7.distanceToSqr((Vec3)var11.get()) <= RotationMathUtil.square(((Float)this.tah.getValue()).floatValue());
               if (var12) {
                  this.ebk = var2;
                  this.iw = (int)Math.signum(RotationMathUtil.angleDifference(var2.getYRot(), var2.yRotO));
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, "found_target", var2, this.iw);
                  }

                  return true;
               }

               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, "no_target", "not_aiming");
               }
            }
         }
      }

      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this, "no_target_end", "none");
      }

      return false;
   }

   public void tjy(TickEvent var1) {
      Minecraft var2 = Minecraft.getInstance();
      LocalPlayer var3 = var2.player;
      if ((!(Boolean)this.ssn.getValue() || var3.hurtTime > 0) && this.wa()) {
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, "started", this.ticksRemaining);
         }

         this.ticksRemaining = Math.max(this.ticksRemaining, this.aup.cah());
      }

      if (this.ticksRemaining > 0) {
         if (WorldEntityUtil.getEntities().contains(this.ebk) && !(this.ebk.distanceToSqr(var3.position()) >= RotationMathUtil.square((Float)this.tah.getValue() + 2.0F))) {
            this.ticksRemaining--;
         } else {
            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, "ended", "out_of_range");
            }

            this.ticksRemaining = 0;
            this.ebk = null;
         }
      }
   }
}
