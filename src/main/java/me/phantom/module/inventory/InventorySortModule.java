package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.inventory.ContainerSwitchEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.InventorySortSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.inventory.ContainerPassMode;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.InventoryWrapper;
import me.phantom.util.inventory.ItemScoreCategory;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
public class InventorySortModule extends InventoryModuleBase {
   public final InventorySortSetting lpm = new InventorySortSetting(this, GlyphTextUtil.encode("configuration"), new ItemScoreCategory[46]);
   public final FloatSliderSetting ecl = new FloatSliderSetting(this, GlyphTextUtil.encode("start_delay"), 0.0F, 0.2F, 5.0F);
   public final ToggleSetting xr = new ToggleSetting(this, GlyphTextUtil.encode("auto_refill"), false);
   public final ToggleSetting su = new ToggleSetting(this, GlyphTextUtil.encode("click_interruption"), true);
   public final FloatSliderSetting dkp = new FloatSliderSetting(this, GlyphTextUtil.encode("click_interruption_delay"), 0.2F, 0.5F, 1.0F);
   public int hqy = -1;
   public int ri = -1;
   public int qqi = -1;
   public long jkq;

   public InventorySortModule() {
      super(ModuleCategory.iua, ClientPlayStyle.ats, GlyphTextUtil.encode("InventorySort"));
      this.dkp.setVisibleWhen(this.su);
      this.markClipboardHint();
      this.kxp();
   }

   public void kxp() {
      ItemScoreCategory[] var1 = (ItemScoreCategory[])this.lpm.getDefaultValue();
      Arrays.fill(var1, ItemScoreCategory.ryh);
      var1[9] = ItemScoreCategory.vxx;
      var1[10] = ItemScoreCategory.qhx;
      var1[11] = ItemScoreCategory.cbu;
      var1[12] = ItemScoreCategory.aqy;
      var1[15] = ItemScoreCategory.wxo;
      var1[16] = ItemScoreCategory.ytf;
      var1[36] = ItemScoreCategory.ko;
      var1[37] = ItemScoreCategory.wxo;
      var1[38] = ItemScoreCategory.ggc;
      var1[39] = ItemScoreCategory.jva;
      var1[40] = ItemScoreCategory.ta;
      var1[41] = ItemScoreCategory.lqi;
      var1[42] = ItemScoreCategory.lqi;
      var1[43] = ItemScoreCategory.zle;
      var1[44] = ItemScoreCategory.ytf;
      this.lpm.setValue((ItemScoreCategory[])var1.clone());
   }

   @Override
   public void onEnable() {
   }

   public void tjy(TickEvent var1) {
      if (Minecraft.getInstance().screen == null) {
         this.ri = -1;
         this.lp();
      } else if (this.ri > 0) {
         this.ri--;
      }
   }

   public void lp() {
      if (this.qqi != -1) {
         Minecraft.getInstance().player.getInventory().setSelectedSlot(this.qqi);
         this.qqi = -1;
      } else {
         Minecraft var1 = Minecraft.getInstance();
         LocalPlayer var2 = var1.player;
         ItemScoreCategory var3 = ((ItemScoreCategory[])this.lpm.getValue())[45];
         if (var3 != null && var3 != ItemScoreCategory.ryh) {
            Inventory var4 = var2.getInventory();
            ItemStack var5 = var2.getOffhandItem();
            ItemScoreCategory var6 = InventoryModuleBase.getItemCategory(var5);
            float var7 = var6 == var3 ? var6.nfb(var5) : -1.0F;
            int var8 = -1;
            float var9 = var7;

            for (int var10 = 0; var10 < 9; var10++) {
               ItemStack var11 = (ItemStack)var4.getNonEquipmentItems().get(var10);
               ItemScoreCategory var12 = InventoryModuleBase.getItemCategory(var11);
               if (var12 == var3) {
                  float var13 = var12.nfb(var11);
                  if (var13 > var9) {
                     var9 = var13;
                     var8 = var10;
                  }
               }
            }

            if (var8 != -1) {
               this.qqi = var2.getInventory().getSelectedSlot();
               var2.getInventory().setSelectedSlot(var8);
               var1.getConnection().send(new ServerboundSetCarriedItemPacket(var8));
               var1.getConnection().send(new ServerboundPlayerActionPacket(Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
            }
         }
      }
   }

   @Override
   public void yu(InventoryWrapper var1, int var2, Color var3, ItemStack var4) {
      if (this.jkq > System.currentTimeMillis()) {
         var3 = null;
      }

      super.yu(var1, var2, var3, var4);
   }

   public void ee(ContainerSwitchEvent var1) {
      if ((Boolean)this.su.getValue() && PhantomClient.uiManager.getContext().xfy[0]) {
         this.jkq = System.currentTimeMillis() + (int)(1000.0F * (Float)this.dkp.getValue());
      }

      if (this.jkq <= System.currentTimeMillis() || var1.xy() != ContainerPassMode.ss) {
         if (var1.yt() == null) {
            if (this.ri == -1) {
               this.ri = (int)((Float)this.ecl.getValue() * 20.0F);
            }

            if (this.ri <= 0 || var1.xy() != ContainerPassMode.ss) {
               ItemScoreCategory[] var2 = (ItemScoreCategory[])this.lpm.getValue();
               InventoryWrapper var3 = var1.eh();
               InventoryScreen var4 = (InventoryScreen) Minecraft.getInstance().screen;
               AbstractContainerMenu var5 = var4.getMenu();
               ItemStack var6 = var5.getCarried();
               if (var6 != null && !Objects.equals(Items.AIR, var6.getItem()) && var1.xy() == ContainerPassMode.ss) {
                  ItemScoreCategory var21 = getItemCategory(var6);

                  for (int var25 = 0; var25 < plt.length; var25++) {
                     int var29 = plt[var25];
                     ItemStack var33 = var3.getStack(var29);
                     if (var33.hashCode() == var6.hashCode()) {
                        var33 = new ItemStack(Items.AIR);
                     }

                     if (var2[var29] == var21
                           && getItemCategory(var33) == var21
                           && var33.getMaxStackSize() > 1
                           && ItemStack.isSameItemSameComponents(var33, var6)
                           && var33.getCount() < var33.getMaxStackSize()
                        || var2[var29] == var21 && getItemCategory(var33) == ItemScoreCategory.ryh) {
                        this.pickupSlot(var3, var29, false);
                        return;
                     }
                  }

                  this.pickupSlot(var3, -999, false);
               } else {
                  for (int var7 = 0; var7 < plt.length; var7++) {
                     int var8 = plt[var7];
                     ItemScoreCategory var9 = var2[var8];
                     if (var9 != ItemScoreCategory.um && var9 != null) {
                        ItemStack var10 = var3.getStack(var8);
                        ItemScoreCategory var11 = InventoryModuleBase.getItemCategory(var10);
                        float var12 = var11 == var9 ? var11.nfb(var10) : -1.0F;
                        int var13 = -1;
                        float var14 = var12;

                        for (int var15 = 0; var15 < plt.length; var15++) {
                           int var16 = plt[var15];
                           if (var7 != var15 && var2[var16] != var9 && var2[var16] != ItemScoreCategory.um) {
                              ItemStack var17 = var3.getStack(var16);
                              ItemScoreCategory var18 = InventoryModuleBase.getItemCategory(var17);
                              if (var18 == var9) {
                                 float var19 = var18.nfb(var17);
                                 if (var19 > var14) {
                                    var14 = var19;
                                    var13 = var16;
                                 }
                              }
                           }
                        }

                        if (var13 >= 0) {
                           if (ContainerPassMode.ss.equals(var1.xy())) {
                              this.mxv(var3, var13, var8);
                              return;
                           }

                           if (ContainerPassMode.ss.equals(var1.xy())) {
                              this.yu(var3, var8, var9 == ItemScoreCategory.um ? Color.gray : Color.yellow, var9.getStack());
                           }
                        } else if (var9 != null) {
                           if (var11 == var9) {
                              this.yu(var3, var8, Color.green, null);
                           } else {
                              this.yu(var3, var8, var9 == ItemScoreCategory.um ? Color.gray : Color.yellow, var9.getStack());
                           }
                        }
                     } else if (ContainerPassMode.fp.equals(var1.xy())) {
                        this.yu(var3, var8, Color.gray, null);
                     }
                  }

                  boolean var20 = (Boolean)this.xr.getValue();
                  if (var20) {
                     for (int var22 = 36; var22 < 44; var22++) {
                        ItemStack var26 = var3.getStack(var22);
                        if (var26 == null || var26.getItem() == Items.AIR) {
                           for (int var30 = 0; var30 < plt.length; var30++) {
                              int var34 = plt[var30];
                              if (var34 < 36 || var34 > 44) {
                                 ItemScoreCategory var37 = InventoryModuleBase.getItemCategory(var3.getStack(var34));
                                 if ((var37 == ItemScoreCategory.lqi || var37 == ItemScoreCategory.fpz || var37 == ItemScoreCategory.xzo) && var1.xy() == ContainerPassMode.ss) {
                                    this.mxv(var3, var34, var22);
                                    return;
                                 }
                              }
                           }
                        }
                     }
                  }

                  for (int var23 = 0; var23 < plt.length; var23++) {
                     int var27 = plt[var23];
                     ItemStack var31 = var3.getStack(var27);
                     if (var31 != null && var31.getItem() != Items.AIR && var31.getItem().getDefaultMaxStackSize() > 1 && getItemCategory(var31) != var2[var27]) {
                        ItemScoreCategory var35 = InventoryModuleBase.getItemCategory(var31);

                        for (int var38 = 0; var38 < plt.length; var38++) {
                           int var40 = plt[var38];
                           if (var40 != var27) {
                              ItemStack var41 = var3.getStack(var40);
                              if (getItemCategory(var41) == var35 && ItemStack.isSameItemSameComponents(var31, var41)) {
                                 if (ContainerPassMode.ss.equals(var1.xy())) {
                                    this.pickupSlot(var3, var27, false);
                                    this.pickupSlot(var3, var40, false);
                                    return;
                                 }

                                 if (ContainerPassMode.fp.equals(var1.xy())) {
                                    this.yu(var3, var27, Color.green, null);
                                 }
                              }
                           }
                        }
                     }
                  }

                  for (int var24 = 0; var24 < plt.length; var24++) {
                     int var28 = plt[var24];
                     ItemStack var32 = var3.getStack(var28);
                     if (var32 != null && var32.getItem() != Items.AIR) {
                        ItemScoreCategory var36 = var2[var28];
                        if (var36 != ItemScoreCategory.um) {
                           ItemScoreCategory var39 = InventoryModuleBase.getItemCategory(var32);
                           if ((!var20 || var39 != ItemScoreCategory.lqi && var39 != ItemScoreCategory.fpz && var39 != ItemScoreCategory.xzo) && var36 != var39) {
                              if (ContainerPassMode.ss.equals(var1.xy())) {
                                 this.throwSlot(var3, var28, var32.getCount() > 1);
                                 return;
                              }

                              if (ContainerPassMode.ss.equals(var1.xy())) {
                                 this.yu(var3, var28, Color.red, null);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

}
