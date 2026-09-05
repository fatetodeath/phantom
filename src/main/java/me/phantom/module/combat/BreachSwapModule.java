package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.build.AutoToolModule;
import me.phantom.setting.EnchantListSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.ItemScoreCategory;

import java.util.Arrays;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
public class BreachSwapModule extends BaseModule {
   private int smg;
   public int qwq;
   public boolean awr;
   private final EnchantListSetting sfl = new EnchantListSetting(this, GlyphTextUtil.encode("disallowed_enchantments"), Arrays.asList(new ItemStack(Items.MACE)));

   public BreachSwapModule() {
      super(ModuleCategory.vne, ClientPlayStyle.ats, GlyphTextUtil.encode("BreachSwap"));
      this.markBeta();
   }

   public void sau(KeyStateEvent var1) {
      if (var1.by().getName().equals("key.attack")) {
         if (var1.re()) {
            if (!this.awr) {
               this.awr = true;
               if (this.qwq <= 0) {
                  MinecraftClient var2 = MinecraftClient.getInstance();
                  ClientPlayerEntity var3 = var2.player;
                  if (InventoryModuleBase.getItemCategory(var3.getMainHandStack()) == ItemScoreCategory.ko) {
                     int var4 = var3.getInventory().getSelectedSlot();
                     int var5 = AutoToolModule.nn(var1x -> var1x.getItem() == Items.MACE && !this.sfl.oa(var1x) ? GameInteractionUtil.getEnchantments(var1x).size() : 0.0F);
                     if (var5 != var4) {
                        var3.getInventory().setSelectedSlot(var5);
                        this.smg = var4;
                        this.qwq = 3;
                     }
                  }
               }
            }
         } else {
            this.awr = false;
         }
      }
   }

   @Override
   public void onEnable() {
      this.qwq = 0;
      this.smg = -1;
   }

   public void tjy(TickEvent var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      ClientPlayerEntity var3 = var2.player;
      if (this.qwq > 0 && --this.qwq <= 0 && this.smg != -1) {
         var3.getInventory().setSelectedSlot(this.smg);
      }
   }

   @Override
   public boolean ma() {
      return this.qwq > 0;
   }
}
