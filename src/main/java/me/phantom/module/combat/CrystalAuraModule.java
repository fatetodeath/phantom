package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.mc.MinecraftClientBridge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
@ModuleMetadata(
   x = -20.5,
   y = -60.0,
   z = 5.5
)
public class CrystalAuraModule extends BaseModule {
   private static int wx = 15;
   public FloatSliderSetting tah = new FloatSliderSetting(this, GlyphTextUtil.encode("range"), 3.0F, 6.0F, 9.0F);
   public AimHelperModule ia;
   public int dhm = 0;
   private int xi = 0;

   public CrystalAuraModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("CrystalClicker"));
      this.markClipboardHint();
   }

   @Override
   public String getValueSuffix() {
      return RotationMathUtil.formatTwoDecimals((Float)this.tah.getValue());
   }

   public void tjy(TickEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      Vec3 var3 = var2.getEyePosition();
      Vec2 var4 = null;
      float var5 = 0.0F;
      Entity var6 = null;
      double var7 = Math.pow(((Float)this.tah.getValue()).floatValue(), 2.0);

      for (Entity var10 : ((ClientLevel)var2.level()).entitiesForRendering()) {
         if (var10 instanceof EndCrystal) {
            Vec3 var11 = var10.position().add(0.0, var10.getBbHeight() / 2.0F, 0.0);
            if (!(var11.distanceToSqr(var3) >= var7)) {
               Vec2 var12 = RotationMathUtil.getRotationTo(var3, var11);
               if (!Float.isNaN(var12.x)
                  && !Float.isNaN(var12.y)
                  && GameInteractionUtil.raycastFromRotation(var2, var3, var12.x, var12.y, 3.0) instanceof EntityHitResult var14
                  && var14.getEntity() == var10) {
                  float var15 = Math.abs(RotationMathUtil.angleDifference(var2.getYRot(), var12.x));
                  if (var4 == null || var5 > var15) {
                     var4 = var12;
                     var5 = var15;
                     var6 = var10;
                  }
               }
            }
         }
      }

      if (var4 != null && var6 != null) {
         this.ia.vc(wx, var4.x - var2.getYRot(), var4.y - var2.getXRot(), true);
         if (this.xi != var6.getId()) {
            this.dhm = 0;
         } else if (++this.dhm == 1) {
            ((MinecraftClientBridge) Minecraft.getInstance()).getInteractionManager().attack(var2, var6);
            var2.swing(InteractionHand.MAIN_HAND);
         }

         this.xi = var6.getId();
      } else {
         this.xi = 0;
         this.dhm = 0;
      }
   }
}
