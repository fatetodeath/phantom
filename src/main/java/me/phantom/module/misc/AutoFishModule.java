package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.FishingBobberDuck;
import me.phantom.util.WorldEntityUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
public class AutoFishModule extends BaseModule {
   public FloatRangeSliderSetting xaz = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("delay"), 0.0F, 0.05F, 0.1F, 1.0F);
   public boolean zee;
   public int sd;
   public int xi = -1;
   private boolean pxy;

   public AutoFishModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.zfw, GlyphTextUtil.encode("AutoFish"));
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
      this.zee = false;
      this.xi = -1;
   }

   @Override
   public boolean ma() {
      return this.pxy;
   }

   public void tjy(TickEvent var1) {
      this.pxy = false;
      if (this.sd > 0) {
         this.sd--;
         this.xi = -1;
      } else if (this.zee) {
         GameInteractionUtil.zgx();
         this.zee = false;
         this.xi = -1;
      } else {
         LocalPlayer var2 = Minecraft.getInstance().player;
         ItemStack var3 = var2.getMainHandItem();
         if (var3 != null && var3.getItem() != null && var3.getItem() instanceof FishingRodItem) {
            if (Minecraft.getInstance().options.keyUse.isDown()) {
               this.zee = false;
               this.xi = -1;
            } else if (this.xi != -1 && Minecraft.getInstance().level.getEntity(this.xi) == null) {
               GameInteractionUtil.zgx();
               this.sd = (int)(this.xaz.fbz() * 20.0F);
               this.xi = -1;
            } else {
               this.pxy = true;

               for (Entity var5 : WorldEntityUtil.getEntities()) {
                  if (var5 instanceof FishingHook) {
                     FishingBobberDuck var6 = (FishingBobberDuck)var5;
                     if (var6.getPlayerOwner() != null
                        && var6.getPlayerOwner() instanceof LocalPlayer
                        && !(Math.pow(var5.xo - var5.getX(), 2.0) + Math.pow(var5.zo - var5.getZ(), 2.0) >= 0.02)) {
                        this.xi = var5.getId();
                        if (var6.hasCaughtFish()) {
                           GameInteractionUtil.zgx();
                           this.zee = true;
                           this.sd = (int)(this.xaz.fbz() * 20.0F);
                           return;
                        }
                     }
                  }
               }
            }
         } else {
            this.zee = false;
            this.xi = -1;
         }
      }
   }
}
