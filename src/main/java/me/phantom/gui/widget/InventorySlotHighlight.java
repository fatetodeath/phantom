package me.phantom.gui.widget;

import me.phantom.module.inventory.InventoryActionModule;

import java.awt.Color;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class InventorySlotHighlight {
   public final Container inventory;
   public final int slotIndex;
   public final Color color;
   public final ItemStack iconStack;

   public InventorySlotHighlight(InventoryActionModule var1, Container var2, int var3, Color var4, ItemStack var5) {
      this.inventory = var2;
      this.slotIndex = var3;
      this.color = var4 == null ? null : new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), Math.min(var4.getAlpha(), 160));
      this.iconStack = var5;
   }
}
