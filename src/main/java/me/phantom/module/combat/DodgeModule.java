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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
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
         if (var2 instanceof LivingEntity var3 && !(var2 instanceof ClientPlayerEntity) && PhantomClient.targetSelector.csu(var2)) {
            MinecraftClient var4 = MinecraftClient.getInstance();
            ClientPlayerEntity var5 = var4.player;
            Vec3d var6 = var5.getPos();
            Vec3d var7 = var2.getEyePos();
            Vec2f var8 = RotationMathUtil.getRotationTo(var6, var7);
            if (Math.abs(RotationMathUtil.angleDifference(var5.getYaw(), var8.x)) > (Float)this.yxs.getValue()) {
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, "no_target", "out_of_fov");
               }
            } else {
               Vec3d var9 = var2.getEyePos().add(RotationMathUtil.getLookVector(var2.getYaw(), var2.getPitch()).multiply(((Float)this.tah.getValue()).floatValue()));
               Box var10 = var5.getBoundingBox().expand((Float)this.zyv.getValue() / 2.0F);
               Optional var11 = var10.raycast(var7, var9);
               boolean var12 = var10.contains(var7)
                  || var11.isPresent() && var7.squaredDistanceTo((Vec3d)var11.get()) <= RotationMathUtil.square(((Float)this.tah.getValue()).floatValue());
               if (var12) {
                  this.ebk = var2;
                  this.iw = (int)Math.signum(RotationMathUtil.angleDifference(var2.getYaw(), var2.lastYaw));
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
      MinecraftClient var2 = MinecraftClient.getInstance();
      ClientPlayerEntity var3 = var2.player;
      if ((!(Boolean)this.ssn.getValue() || var3.hurtTime > 0) && this.wa()) {
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, "started", this.ticksRemaining);
         }

         this.ticksRemaining = Math.max(this.ticksRemaining, this.aup.cah());
      }

      if (this.ticksRemaining > 0) {
         if (WorldEntityUtil.getEntities().contains(this.ebk) && !(this.ebk.squaredDistanceTo(var3.getPos()) >= RotationMathUtil.square((Float)this.tah.getValue() + 2.0F))) {
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
