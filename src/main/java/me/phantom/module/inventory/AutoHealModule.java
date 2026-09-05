package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ThrowablePotionItem;
public class AutoHealModule extends BaseModule {
   public static int wx = 15;
   public final FloatRangeSliderSetting uao = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("executedelay"), 0.0F, 0.05F, 0.1F, 1.0F);
   public final FloatSliderSetting uy = new FloatSliderSetting(this, GlyphTextUtil.encode("minhealth"), 0.0F, 5.0F, 9.0F);
   public final ToggleSetting xqe = new ToggleSetting(this, GlyphTextUtil.encode("silent"), false);
   public final FloatRangeSliderSetting ag = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("aim_speed"), 0.0F, 0.15F, 0.3F, 0.7F);
   public AimHelperModule ia;
   public int xwb;
   private int rdp;
   public int ur;
   public int hitDelayTicks;
   public float ym;

   public AutoHealModule() {
      super(ModuleCategory.iua, ClientPlayStyle.zfw, GlyphTextUtil.encode("AutoHeal"));
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
      this.xwb = 0;
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      if (PhantomClient.isScreenClosed()) {
         switch (this.xwb) {
            case 0:
               if (!var2.isOnGround() || var2.getHealth() / 2.0F > (Float)this.uy.getValue()) {
                  return;
               }

               int var15 = var2.getInventory().getSelectedSlot();

               for (int var7 : InventoryModuleBase.fzs) {
                  int var8 = var15 + var7;
                  if (var8 < 9 && var8 >= 0) {
                     ItemStack var9 = var2.getInventory().getStack(var8);
                     if (var9 != null) {
                        Item var10 = var9.getItem();
                        if (var10 != null && var10 instanceof ThrowablePotionItem) {
                           boolean var11 = false;

                           for (StatusEffectInstance var13 : GameInteractionUtil.tnw(var9)) {
                              if (var13.getEffectType() == StatusEffects.INSTANT_HEALTH || var13.getEffectType() == StatusEffects.REGENERATION) {
                                 var11 = true;
                                 break;
                              }
                           }

                           if (var11) {
                              this.rdp = var2.getInventory().getSelectedSlot();
                              this.ur = 0;
                              this.hitDelayTicks = this.ag.cah();
                              this.ym = (float)(Math.random() * 15.0 - 7.5);
                              this.xwb = this.hitDelayTicks == 0 ? 2 : 1;
                              var2.getInventory().setSelectedSlot(var8);
                              return;
                           }
                        }
                     }
                  }
               }
               break;
            case 1:
               float var14 = Math.min(1.0F, ++this.ur / (1.0F * this.hitDelayTicks));
               this.ia.vc(wx, this.ym * var14, (90.0F - var2.getPitch()) * var14, (Boolean)this.xqe.getValue());
               if (this.ur >= this.hitDelayTicks) {
                  this.xwb = 2;
               }
               break;
            case 2:
               this.ia.vc(wx, this.ym, 90.0F - var2.getPitch(), (Boolean)this.xqe.getValue());
               GameInteractionUtil.zgx();
               this.xwb = 3;
               break;
            case 3:
               float var3 = Math.max(0.0F, --this.ur / (1.0F * this.hitDelayTicks));
               if ((Boolean)this.xqe.getValue()) {
                  this.ia.vc(wx, this.ym * var3, (90.0F - var2.getPitch()) * var3, (Boolean)this.xqe.getValue());
               }

               if (this.ur <= 0) {
                  this.xwb = 4;
               }
               break;
            case 4:
               this.xwb = 0;
               var2.getInventory().setSelectedSlot(this.rdp);
         }
      }
   }
}
