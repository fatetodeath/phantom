package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.entity.AttackEntityEvent;
import me.phantom.event.render.FovMultiplierEvent;
import me.phantom.event.packet.SimplePacketEvent;
import me.phantom.event.render.BlockParticleEvent;
import me.phantom.event.render.WeatherGradientEvent;
import me.phantom.event.entity.StatusEffectEvent;
import me.phantom.event.world.TimeOfDayEvent;
import me.phantom.event.misc.ValueOverrideEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.block.Blocks;
public class AntiVisionModule extends BaseModule {
   public final ToggleSetting fjq = new ToggleSetting(this, GlyphTextUtil.encode("weather"), true);
   public final ToggleSetting xhl = new ToggleSetting(this, GlyphTextUtil.encode("fullbright"), true);
   public final ToggleSetting nez = new ToggleSetting(this, GlyphTextUtil.encode("no_potion"), true);
   public final ToggleSetting coj = new ToggleSetting(this, GlyphTextUtil.encode("time"), true);
   public final IntegerSliderSetting tva = new IntegerSliderSetting(this, GlyphTextUtil.encode("daytime"), 0, 6000, 24000);
   public final ToggleSetting doi = new ToggleSetting(this, GlyphTextUtil.encode("fov"), true);
   public final ToggleSetting ust = new ToggleSetting(this, GlyphTextUtil.encode("hurtcam"), true);
   public final ToggleSetting ryz = new ToggleSetting(this, GlyphTextUtil.encode("barriers"), false);
   public final ToggleSetting abw = new ToggleSetting(this, GlyphTextUtil.encode("invisible"), false);

   public AntiVisionModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("AntiVision"));
      this.tva.setVisibleWhen(this.coj);
      this.selectAllSettings();
      this.markClipboardHint();
   }

   public void imex(AttackEntityEvent var1) {
      if ((Boolean)this.abw.getValue()) {
         var1.setCancelled(true);
         var1.ywz(false);
      }
   }

   public void isp(BlockParticleEvent var1) {
      if ((Boolean)this.ryz.getValue()) {
         var1.kh(Blocks.BARRIER);
      }
   }

   public void vou(SimplePacketEvent var1) {
      if ((Boolean)this.ust.getValue()) {
         var1.setCancelled(true);
      }
   }

   public void qtc(FovMultiplierEvent var1) {
      if ((Boolean)this.doi.getValue()) {
         var1.gd(1.0F);
      }
   }

   public void wia(WeatherGradientEvent var1) {
      if ((Boolean)this.fjq.getValue()) {
         var1.setCancelled(true);
      }
   }

   public void wax(TimeOfDayEvent var1) {
      if ((Boolean)this.coj.getValue()) {
         var1.vqq(((Integer)this.tva.getValue()).intValue());
      }
   }

   public void xzg(ValueOverrideEvent var1) {
      if ((Boolean)this.xhl.getValue()) {
         var1.setValue(2000.0);
      }
   }

   public void joa(StatusEffectEvent var1) {
      if ((Boolean)this.nez.getValue()) {
         var1.setCancelled(true);
      }
   }
}
