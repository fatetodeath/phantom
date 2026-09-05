package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.inventory.ContainerSwitchEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.setting.EnchantListSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.InventoryWrapper;
import me.phantom.util.inventory.ItemScoreCategory;

import java.util.Arrays;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult.Type;
public class AutoArmorModule extends InventoryModuleBase {
   public static final ItemScoreCategory[] qgy = new ItemScoreCategory[]{ItemScoreCategory.vxx, ItemScoreCategory.qhx, ItemScoreCategory.cbu, ItemScoreCategory.aqy};
   public final ToggleSetting lbw = new ToggleSetting(this, GlyphTextUtil.encode("right_click"), true);
   public final ToggleSetting gzd = new ToggleSetting(this, GlyphTextUtil.encode("disable_invisible"), true);
   public final EnchantListSetting sfl = new EnchantListSetting(
      this,
      GlyphTextUtil.encode("disallowed_enchantments"),
      Arrays.asList(
         new ItemStack(Items.DIAMOND_HELMET),
         new ItemStack(Items.DIAMOND_CHESTPLATE),
         new ItemStack(Items.DIAMOND_LEGGINGS),
         new ItemStack(Items.DIAMOND_BOOTS)
      )
   );
   public AutoElytraModule fag;

   public AutoArmorModule() {
      super(ModuleCategory.iua, ClientPlayStyle.ats, GlyphTextUtil.encode("AutoArmor"));
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      if (!this.fag.isEnabled()) {
         if (!(Boolean)this.gzd.getValue() || !var2.isInvisible() && !var2.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            if ((Boolean)this.lbw.getValue() && PhantomClient.isScreenClosed()) {
               if (MinecraftClient.getInstance().crosshairTarget.getType() == Type.BLOCK
                  || MinecraftClient.getInstance().crosshairTarget.getType() == Type.ENTITY) {
                  return;
               }

               this.ctc();
            }
         }
      }
   }

   public boolean ctc() {
      ClientPlayerEntity var1 = MinecraftClient.getInstance().player;
      List var2 = GameInteractionUtil.getArmorAndOffHandStacks(var1);

      for (int var3 = 0; var3 < qgy.length; var3++) {
         ItemStack var4 = (ItemStack)var2.get(var3);
         ItemScoreCategory var5 = qgy[var3];
         int var6 = var1.getInventory().getSelectedSlot();
         int var7 = -1;
         float var8 = InventoryModuleBase.getItemCategory(var4) == var5 ? var5.nfb(var4) : -1.0F;

         for (int var12 : InventoryModuleBase.fzs) {
            int var13 = var6 + var12;
            if (var13 < 9 && var13 >= 0) {
               ItemStack var14 = (ItemStack)var1.getInventory().getMainStacks().get(var13);
               if (!this.sfl.oa(var14)) {
                  ItemScoreCategory var15 = InventoryModuleBase.getItemCategory(var14);
                  if (var15 == var5) {
                     float var16 = var15.nfb(var14);
                     if (var16 > var8) {
                        var8 = var16;
                        var7 = var13;
                     }
                  }
               }
            }
         }

         if (var7 != -1 && var8 > 0.0F) {
            var1.getInventory().setSelectedSlot(var7);
            GameInteractionUtil.zgx();
            PhantomClient.scheduleTask(() -> var1.getInventory().setSelectedSlot(var6), 1);
            return true;
         }
      }

      return false;
   }

   public void ee(ContainerSwitchEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      if (!(Boolean)this.gzd.getValue() || !var2.isInvisible() && !var2.hasStatusEffect(StatusEffects.INVISIBILITY)) {
         if (var1.yt() == null) {
            InventoryWrapper var3 = var1.eh();

            for (int var4 = 0; var4 < qgy.length; var4++) {
               ItemScoreCategory var5 = qgy[var4];
               ItemStack var6 = var3.getStack(5 + var4);
               float var7 = InventoryModuleBase.getItemCategory(var6) == var5 ? var5.nfb(var6) : -1.0F;
               int var8 = -1;
               float var9 = var7;

               for (int var10 = 0; var10 < plt.length; var10++) {
                  int var11 = plt[plt.length - var10 - 1];
                  if (var11 != 5 + var4) {
                     ItemStack var12 = var3.getStack(var11);
                     if (!this.sfl.oa(var12)) {
                        ItemScoreCategory var13 = InventoryModuleBase.getItemCategory(var12);
                        if (var13 == var5) {
                           float var14 = var13.nfb(var12);
                           if (var14 > var9) {
                              var9 = var14;
                              var8 = var11;
                           }
                        }
                     }
                  }
               }

               if (var8 >= 0) {
                  if (var6 != null && var6.getItem() != Items.AIR) {
                     this.throwSlot(var3, 5 + var4, false);
                  }

                  this.en(var3, var8);
                  return;
               }
            }
         }
      }
   }
}
