package me.phantom.util.inventory;

import me.phantom.core.PhantomClient;
import me.phantom.module.inventory.AutoCraftModule;

import java.util.Objects;

import me.phantom.util.GlyphTextUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ShapedCraftingRecipe {
   public final AutoCraftModule module;
   public final Item result;
   public final String pattern;
   public final Item[] ingredients;
   public final int[] requiredCounts;
   public Item[] skipItems;

   public ShapedCraftingRecipe(AutoCraftModule var1, Item var2, String var3, Item... var4) {
      this.module = var1;
      this.skipItems = new Item[0];
      this.result = var2;
      this.pattern = var3;
      this.ingredients = var4;
      this.requiredCounts = new int[var4.length];

      for (int var5 = 0; var5 < 3; var5++) {
         for (int var6 = 0; var6 < 3; var6++) {
            char var7 = var3.charAt(var6 * 3 + var5);
            if (var7 != GlyphTextUtil.encode("_").charAt(0)) {
               int var8 = GlyphTextUtil.decodeChar(var7) - '1';
               this.requiredCounts[var8]++;
            }
         }
      }
   }

   public ShapedCraftingRecipe skipIfOwned(Item... var1) {
      this.skipItems = var1;
      return this;
   }

   public boolean canCraft(InventoryWrapper var1, ItemStack var2) {
      for (int var3 = 0; var3 < this.ingredients.length; var3++) {
         if (this.requiredCounts[var3] > var1.countItem(this.ingredients[var3]) + (var2 != null && Objects.equals(this.ingredients[var3], var2.getItem()) ? var2.getCount() : 0)) {
            return false;
         }
      }

      for (int var5 = 0; var5 < this.skipItems.length; var5++) {
         Item var4 = this.skipItems[var5];
         if (var1.countItem(var4) > 0 || var2 != null && Objects.equals(var4, var2.getItem()) || this.module.isEquipped(var4)) {
            return false;
         }
      }

      return true;
   }

   public void moveIngredients(ItemStack var1, InventoryWrapper var2, InventoryWrapper var3) {
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this.module, GlyphTextUtil.encode("moveItems()"), var1, var2, var3, this.pattern);
      }

      for (int var4 = 0; var4 < 9; var4++) {
         char var5 = GlyphTextUtil.decodeChar(this.pattern.charAt(var4));
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this.module, GlyphTextUtil.encode("moveItems[i]"), var5);
         }

         if (var5 != '_') {
            int var6 = var5 - '1';
            Item var7 = this.ingredients[var6];
            ItemStack var8 = var3.getStack(var4 + 1);
            if (var8 == null || !Objects.equals(var8.getItem(), var7)) {
               if (var1 != null && Objects.equals(var1.getItem(), var7)) {
                  this.module.pickupSlot(var3, var4 + 1, true);
                  return;
               }

               for (int var9 = 0; var9 < var2.getSize(); var9++) {
                  ItemStack var10 = var2.getStack(var9);
                  if (var8 != null && Objects.equals(var10.getItem(), var7)) {
                     if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this.module, GlyphTextUtil.encode("moveItems[i]"), GlyphTextUtil.encode("pickup"), var9);
                     }

                     this.module.pickupSlot(var2, var9, false);
                     return;
                  }
               }

               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this.module, GlyphTextUtil.encode("moveItems[i]"), GlyphTextUtil.encode("no target"));
               }
            }
         }
      }
   }
}
