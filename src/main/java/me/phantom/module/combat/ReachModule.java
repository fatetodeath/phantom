package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.core.PhantomClient;
import me.phantom.event.misc.MutableDoubleEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

@ModuleMetadata(
   x = 5.5,
   y = -60.0,
   z = -18.5
)
public class ReachModule extends BaseModule {
   public final ToggleSetting xpw = new ToggleSetting(this, GlyphTextUtil.encode("disableliquid"), true);
   public final FloatRangeSliderSetting wi = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("strafeextend"), 0.0F, 0.5F, 1.0F, 2.0F);
   public final FloatRangeSliderSetting cr = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("reach"), 3.0F, 3.2F, 3.6F, 6.0F);
   public final IntegerSliderSetting vk = new IntegerSliderSetting(this, GlyphTextUtil.encode("percentage"), 1, 100, 100);
   public final ToggleSetting xra = new ToggleSetting(this, GlyphTextUtil.encode("hurt_time_check"), true);

   public ReachModule() {
      super(ModuleCategory.vne, ClientPlayStyle.bq, GlyphTextUtil.encode("Reach"));
      this.markClipboardHint();
   }

   @Override
   public String getValueSuffix() {
      return RotationMathUtil.formatTwoDecimals(this.cr.leo()) + GlyphTextUtil.encode(" - ") + RotationMathUtil.formatTwoDecimals(this.cr.wgp());
   }

   public void tjy(TickEvent var1) {
   }

   public void jt(MutableDoubleEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      boolean var3 = var2.xxa != 0.0F;
      if (!(((Integer)this.vk.getValue()).intValue() < 100.0 * Math.random())) {
         if (!(Boolean)this.xpw.getValue() || !var2.isInWater()) {
            float var4 = this.cr.fbz() + (var3 ? this.wi.fbz() : 0.0F);
            if ((Boolean)this.xra.getValue()) {
               float var5 = PhantomClient.uiManager.getContext().tickDelta;
               if (!(GameInteractionUtil.raycastFromRotation(var2, var2.getEyePosition(), var2.getViewYRot(var5), var2.getViewXRot(var5), var4) instanceof EntityHitResult var7)) {
                  return;
               }

               if (!(var7.getEntity() instanceof LivingEntity var9)) {
                  return;
               }

               if (var9.hurtTime > 0) {
                  return;
               }
            }

            var1.ph(var4);
         }
      }
   }
}
