package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
public class AntiAfkModule extends BaseModule {
   public final ModeSetting modeSetting = new ModeSetting(this, GlyphTextUtil.encode("mode"), GlyphTextUtil.encode("circles"), GlyphTextUtil.encode("jump"), GlyphTextUtil.encode("wtap"));
   public final FloatRangeSliderSetting dck = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("interval"), 0.5F, 1.5F, 3.0F, 15.0F);
   public final IntegerSliderSetting svy = new IntegerSliderSetting(this, GlyphTextUtil.encode("circleangle"), 1, 80, 180);
   public int uzw;
   public boolean ykz;

   public AntiAfkModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.zfw, GlyphTextUtil.encode("AntiAFK"));
      this.svy.setVisibilitySupplier(() -> ((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("circles")));
      this.dck.setVisibilitySupplier(() -> !((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("circles")));
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
      this.uzw = 0;
      this.ykz = false;
   }

   @Override
   public String getValueSuffix() {
      return (String)this.modeSetting.getValue();
   }

   public void sau(KeyStateEvent var1) {
      if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("circles"))) {
         if (var1.by().getName().equals(GlyphTextUtil.plain("key.forward"))) {
            var1.ywz(true);
         }
      } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("jump"))) {
         if (this.ykz && var1.by().getName().equals(GlyphTextUtil.plain("key.jump"))) {
            var1.ywz(true);
            this.ykz = false;
         }
      } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("wtap")) && this.ykz && var1.by().getName().equals(GlyphTextUtil.plain("key.forward"))) {
         var1.ywz(true);
         this.ykz = false;
      }
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("circles"))) {
         GameInteractionUtil.tns(Minecraft.getInstance().player, ((Integer)this.svy.getValue()).intValue() / 20.0F * var1.wt(), 0.0F);
      }
   }

   public void tjy(TickEvent var1) {
      this.ykz = false;
      if (!((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("jump")) && !((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("wtap"))) {
         this.uzw = 0;
      } else if (this.uzw-- <= 0) {
         this.ykz = true;
         this.uzw = this.dck.cah();
      }
   }
}
