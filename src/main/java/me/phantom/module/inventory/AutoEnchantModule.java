package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ContainerKind;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.inventory.ContainerSwitchEvent;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.util.inventory.ContainerPassMode;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.InventoryWrapper;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.inventory.EnchantmentMenu;
public class AutoEnchantModule extends InventoryModuleBase {
   public InventorySortModule iyb;

   public AutoEnchantModule() {
      super(ModuleCategory.iua, ClientPlayStyle.ats, GlyphTextUtil.encode("AutoEnchant"));
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
   }

   public void ee(ContainerSwitchEvent var1) {
      InventoryWrapper var2 = var1.eh();
      InventoryWrapper var3 = var1.yt();
      if (var3 != null && var3.getContainerKind() == ContainerKind.zk) {
         LocalPlayer var4 = Minecraft.getInstance().player;
         EnchantmentScreen var5 = (EnchantmentScreen) Minecraft.getInstance().screen;
         if (var5 != null) {
            EnchantmentMenu var6 = (EnchantmentMenu)var5.getMenu();
            int var7 = var4.experienceLevel;
            if (var7 < 1) {
               this.closeScreen();
            } else {
               ItemStack var8 = var3.getStack(1);
               if (var8 == null || var8.getItem() != Items.LAPIS_LAZULI) {
                  for (int var9 = 0; var9 < var2.getSize(); var9++) {
                     int var10;
                     if (var9 < 9) {
                        var10 = 27 + var9;
                     } else {
                        var10 = var9 - 9;
                     }

                     ItemStack var11 = var2.getStack(var9);
                     if (var11 != null && var11.getItem() == Items.LAPIS_LAZULI) {
                        if (var1.xy() == ContainerPassMode.ss) {
                           this.en(var2, var10);
                           return;
                        }

                        if (var1.xy() == ContainerPassMode.fp) {
                           this.yu(var2, var9, Color.GREEN, var11);
                        }
                     }
                  }

                  if (var1.xy() == ContainerPassMode.ss) {
                     this.closeScreen();
                     return;
                  }
               }

               ItemStack var13 = var3.getStack(0);
               if (var1.xy() == ContainerPassMode.ss && var13 != null && var13.isEnchanted()) {
                  this.en(var3, 0);
               } else {
                  if (var13 == null || var13.getItem() == Items.AIR || var1.xy() == ContainerPassMode.fp) {
                     for (int var14 = 0; var14 < var2.getSize(); var14++) {
                        int var16;
                        if (var14 < 9) {
                           var16 = 27 + var14;
                        } else {
                           var16 = var14 - 9;
                        }

                        ItemStack var12 = var2.getStack(var14);
                        if (var12 != null && !var12.isEnchanted() && var12.isEnchantable()) {
                           if (var1.xy() == ContainerPassMode.ss) {
                              this.en(var2, var16);
                              return;
                           }

                           if (var1.xy() == ContainerPassMode.fp) {
                              this.yu(var2, var14, Color.GREEN, var12);
                           }
                        }
                     }

                     if (var1.xy() == ContainerPassMode.ss) {
                        this.closeScreen();
                        return;
                     }
                  }

                  if (var1.xy() == ContainerPassMode.ss) {
                     if (var6.costs[0] == 0) {
                        return;
                     }

                     for (int var15 = 2; var15 >= 0; var15--) {
                        int var17 = var6.costs[var15];
                        if (var17 != 0 && var7 >= var17) {
                           Minecraft.getInstance().gameMode.handleInventoryButtonClick(var6.containerId, var15);
                           return;
                        }
                     }

                     this.closeScreen();
                  }
               }
            }
         }
      }
   }
}
