package me.phantom.util.inventory;

import me.phantom.core.module.ContainerKind;

import java.util.Objects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.network.chat.Component;

public interface InventoryWrapper {
   int getSize();

   ItemStack getStack(int var1);

   void click(int var1, int var2, ContainerInput var3);

   ContainerKind getContainerKind();

   Slot getSlotAt(int var1);

   default int countItem(Item var1) {
      int var2 = this.getSize();
      int var3 = 0;

      for (int var4 = 0; var4 < var2; var4++) {
         ItemStack var5 = this.getStack(var4);
         if (var5 != null && Objects.equals(var5.getItem(), var1)) {
            var3 += var5.getCount();
         }
      }

      return var3;
   }

   default Component getTitle() {
      return null;
   }
}
