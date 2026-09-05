package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.build.AutoToolModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.IntRangeSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;

public class ShieldBreakerLegacyModule extends BaseModule {
   public FloatRangeSliderSetting xaz = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("delay"), 0.0F, 0.05F, 0.1F, 1.0F);
   private FloatRangeSliderSetting az = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("retention"), 0.0F, 0.3F, 0.5F, 1.0F);
   public IntRangeSliderSetting ux = new IntRangeSliderSetting(this, GlyphTextUtil.encode("CPS"), 1, 13, 16, 20);
   public IntegerSliderSetting ezp = new IntegerSliderSetting(this, GlyphTextUtil.encode("charge"), 1, 13, 100);
   private ToggleSetting exh = new ToggleSetting(this, GlyphTextUtil.encode("unlegit"), false);
   public int smg = -1;
   public int nwz = -1;
   private int uoh = 0;
   public long bw;

   public ShieldBreakerLegacyModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("ShieldBreakerLegacy"));
      this.markBeta();
   }

   @Override
   public void onEnable() {
      this.nwz = -1;
      this.smg = -1;
   }

   public boolean vie() {
      Entity var1 = GameInteractionUtil.getCrosshairEntity();
      return var1 != null && var1 instanceof PlayerEntity var2 ? var2.isBlocking() : false;
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      Entity var3 = GameInteractionUtil.getCrosshairEntity();
      boolean var4 = this.vie();
      if (!var4 && this.uoh <= 0) {
         if (this.smg != -1) {
            var2.getInventory().setSelectedSlot(this.smg);
            this.smg = -1;
         }
      } else {
         if (this.nwz == -1) {
            this.nwz = this.xaz.cah();
         } else {
            if (this.nwz > 0) {
               this.nwz--;
               return;
            }

            if (this.smg == -1) {
               this.smg = var2.getInventory().getSelectedSlot();
               var2.getInventory().setSelectedSlot(AutoToolModule.nn(var1x -> var1x.getItem() instanceof AxeItem ? GameInteractionUtil.getWeaponScore(var1x, var3) : 0.0F));
            }

            if ((Boolean)this.exh.getValue()) {
               PhantomClient.mc().setAttackCooldown(0);
            }

            if (var2.getAttackCooldownProgress(0.5F) > ((Integer)this.ezp.getValue()).intValue() && (this.bw == 0L || this.bw > System.currentTimeMillis())) {
               GameInteractionUtil.uoo();
               this.bw = System.currentTimeMillis() + (int)(1000.0F / this.ux.koj());
            }
         }

         if (!var4) {
            if (this.uoh > 0) {
               this.uoh--;
            }
         } else {
            this.uoh = this.az.cah();
         }
      }
   }
}
