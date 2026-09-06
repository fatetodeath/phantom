package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.entity.PlayerVelocityEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
public class VelocityModule extends BaseModule {
   public final IntegerSliderSetting pha = new IntegerSliderSetting(this, GlyphTextUtil.encode("jumppercentage"), 0, 75, 100);
   public final FloatRangeSliderSetting lox = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("jumpdelay"), 0.0F, 0.1F, 0.2F, 1.0F);
   public final ToggleSetting kqw = new ToggleSetting(this, GlyphTextUtil.encode("jumpfacing"), false);
   public final ToggleSetting cpo = new ToggleSetting(this, GlyphTextUtil.encode("multiplyh"), true);
   public final FloatRangeSliderSetting mii = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("velmulxz"), 0.0F, 50.0F, 75.0F, 100.0F);
   public final ToggleSetting puh = new ToggleSetting(this, GlyphTextUtil.encode("multiplyy"), true);
   public final FloatRangeSliderSetting vlz = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("velmuly"), 0.0F, 50.0F, 75.0F, 100.0F);
   public boolean wud;
   private long vft;

   public VelocityModule() {
      super(ModuleCategory.wld, ClientPlayStyle.ats, GlyphTextUtil.encode("Velocity"));
      this.mii.setVisibleWhen(this.cpo);
      this.vlz.setVisibleWhen(this.puh);
      this.lox.setVisibilitySupplier(() -> (Integer)this.pha.getValue() > 0);
      this.kqw.setVisibilitySupplier(() -> (Integer)this.pha.getValue() > 0);
   }

   @Override
   public void onEnable() {
      this.wud = false;
   }

   public void sau(KeyStateEvent var1) {
      if (this.wud && System.currentTimeMillis() > this.vft && var1.by().getName().equals(GlyphTextUtil.plain("key.jump"))) {
         var1.ywz(true);
         this.wud = false;
      }
   }

   public void bhp(PlayerVelocityEvent var1) {
      if (var1.tg() instanceof LocalPlayer) {
         if ((Boolean)this.cpo.getValue() || (Boolean)this.puh.getValue()) {
            float var2 = this.cpo.getValue() ? this.mii.fbz() / 100.0F : 1.0F;
            float var3 = this.puh.getValue() ? this.vlz.fbz() / 100.0F : 1.0F;
            if (var2 < 1.0F || var3 < 1.0F) {
               var1.rpb(var1.waw().multiply(var2, var3, var2));
            }
         }

         if (((Integer)this.pha.getValue()).intValue() >= 100.0 * Math.random() && (!(Boolean)this.kqw.getValue() || GameInteractionUtil.getCrosshairEntity() instanceof LivingEntity)) {
            this.wud = true;
            this.vft = (long)((float)System.currentTimeMillis() + this.lox.fbz() * 1000.0F);
         }
      }
   }
}
