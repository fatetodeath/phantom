package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.build.AutoToolModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.IntRangeSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public class ShieldBreakerModule extends BaseModule {
   public final FloatRangeSliderSetting xaz = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("delay_switch"), 0.0F, 0.1F, 0.2F, 1.0F);
   public final IntRangeSliderSetting ux = new IntRangeSliderSetting(this, GlyphTextUtil.encode("cps"), 1, 12, 16, 20);
   public final IntegerSliderSetting gbc = new IntegerSliderSetting(this, GlyphTextUtil.encode("min_charge_percent"), 1, 13, 100);
   public final ToggleSetting ili = new ToggleSetting(this, GlyphTextUtil.encode("spam_click"), true);
   public final ToggleSetting zqb = new ToggleSetting(this, GlyphTextUtil.encode("auto_switch"), true);
   public final ToggleSetting owx = new ToggleSetting(this, GlyphTextUtil.encode("switch_back"), true);
   public final ToggleSetting exh = new ToggleSetting(this, GlyphTextUtil.encode("unlegit_mode"), false);
   public final FloatSliderSetting tah = new FloatSliderSetting(this, GlyphTextUtil.encode("range"), 2.0F, 3.0F, 5.0F);
   private int ndv = -1;
   public long ovb = 0L;
   private int smg = -1;
   public boolean mhk = false;

   public ShieldBreakerModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("ShieldBreaker"));
      this.owx.setVisibleWhen(this.zqb);
   }

   @Override
   public void onEnable() {
      this.ndv = -1;
      this.smg = -1;
      this.mhk = false;
   }

   @Override
   public void onDisable() {
      if (this.smg != -1) {
         Minecraft.getInstance().player.getInventory().setSelectedSlot(this.smg);
         this.smg = -1;
      }

      this.mhk = false;
   }

   public void tjy(TickEvent var1) {
      Minecraft var2 = Minecraft.getInstance();
      LocalPlayer var3 = var2.player;
      if (PhantomClient.isScreenClosed() && var3 != null) {
         HitResult var4 = GameInteractionUtil.qmg(var3, var3.getEyePosition(), var3.getEyePosition().add(GameInteractionUtil.getRotationVector(var3, 1.0F).scale(((Float)this.tah.getValue()).floatValue())));
         if (var4.getType() == Type.ENTITY && var4 instanceof EntityHitResult var5 && var5.getEntity() instanceof Player var6 && var6.isBlocking()) {
            if ((Boolean)this.zqb.getValue()) {
               if (!this.mhk) {
                  this.ndv = this.xaz.cah();
                  this.smg = var3.getInventory().getSelectedSlot();
                  int var10 = AutoToolModule.nn(var1x -> var1x.getItem() instanceof AxeItem ? GameInteractionUtil.getWeaponScore(var1x, var6) : 0.0F);
                  if (var10 != -1) {
                     var3.getInventory().setSelectedSlot(var10);
                  }

                  this.mhk = true;
                  return;
               }

               if (this.ndv > 0) {
                  this.ndv--;
                  return;
               }
            }

            float var9 = var3.getAttackStrengthScale(0.5F);
            float var8 = ((Integer)this.gbc.getValue()).intValue() / 100.0F;
            if (((Boolean)this.ili.getValue() || var9 >= var8) && System.currentTimeMillis() >= this.ovb) {
               if ((Boolean)this.exh.getValue()) {
                  PhantomClient.mc().setAttackCooldown(0);
               }

               GameInteractionUtil.uoo();
               this.ovb = System.currentTimeMillis() + (long)(1000.0 / this.ux.koj());
            }
         } else if (this.mhk && (Boolean)this.owx.getValue() && this.smg != -1) {
            var3.getInventory().setSelectedSlot(this.smg);
            this.smg = -1;
            this.mhk = false;
         }
      }
   }
}
