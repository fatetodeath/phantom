package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ContainerKind;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.inventory.ContainerSwitchEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.inventory.ContainerPassMode;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.InventoryWrapper;
import me.phantom.util.inventory.ItemScoreCategory;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableTextContent;
public class ChestStealerModule extends InventoryModuleBase {
   public InventorySortModule iyb;
   public ToggleSetting qiu = new ToggleSetting(this, GlyphTextUtil.encode("autoclose"), false);
   private ToggleSetting esr = new ToggleSetting(this, GlyphTextUtil.encode("unnamed"), false);
   private ToggleSetting ib = new ToggleSetting(this, GlyphTextUtil.encode("steal_all"), false);
   public final FloatSliderSetting ecl = new FloatSliderSetting(this, GlyphTextUtil.encode("start_delay"), 0.0F, 0.2F, 5.0F);
   public long jeu = -1L;

   public ChestStealerModule() {
      super(ModuleCategory.iua, ClientPlayStyle.ats, GlyphTextUtil.encode("ChestStealer"));
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
      this.jeu = -1L;
   }

   public void tjy(TickEvent var1) {
      if (MinecraftClient.getInstance().currentScreen == null) {
         this.jeu = -1L;
      }
   }

   public void ee(ContainerSwitchEvent var1) {
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this, GlyphTextUtil.encode("invaction"));
      }

      if (var1.yt() != null && var1.yt().getContainerKind() == ContainerKind.phn) {
         if (var1.xy() == ContainerPassMode.ss) {
            if (this.jeu == -1L) {
               this.jeu = System.currentTimeMillis() + (int)((Float)this.ecl.getValue() * 1000.0F);
            }

            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("initial_ticks:") + this.jeu);
            }

            if (this.jeu > System.currentTimeMillis()) {
               return;
            }
         }

         InventoryWrapper var2 = var1.yt();
         InventoryWrapper var3 = var1.eh();
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, GlyphTextUtil.encode("unnamed:") + this.esr.getValue());
         }

         if (!(Boolean)this.esr.getValue()
            || var2.getTitle() != null
               && var2.getTitle().getContent() != null
               && var2.getTitle().getContent() instanceof TranslatableTextContent var4
               && (
                  var4.getKey().equals(GlyphTextUtil.plain("container.chest"))
                     || var4.getKey().equals(GlyphTextUtil.plain("container.barrel"))
                     || var4.getKey().equals(GlyphTextUtil.plain("block.minecraft.chest"))
               )) {
            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("isfull:") + this.ngv(var3));
            }

            if (!this.ngv(var3)) {
               if ((Boolean)this.ib.getValue()) {
                  for (int var17 = 0; var17 < var2.getSize(); var17++) {
                     ItemStack var19 = var2.getStack(var17);
                     if (var19 != null && !var19.getItem().equals(Items.AIR)) {
                        if (var1.xy() == ContainerPassMode.ss) {
                           this.en(var2, var17);
                           return;
                        }

                        if (var1.xy() == ContainerPassMode.fp) {
                           this.yu(var2, var17, Color.GREEN, null);
                        }
                     }
                  }
               } else {
                  for (int var18 = 0; var18 < var3.getSize(); var18++) {
                     int var20 = var18;
                     if (var18 <= 8) {
                        var20 = 36 + var18;
                     }

                     ItemStack var6 = var3.getStack(var18);
                     ItemScoreCategory var7 = ((ItemScoreCategory[])this.iyb.lpm.getValue())[var20];
                     if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, var18 + GlyphTextUtil.encode("=") + var7);
                     }

                     if (var7 != ItemScoreCategory.ryh) {
                        if (PhantomClient.traceEnabled) {
                           PhantomClient.trace(this, var18 + GlyphTextUtil.encode("=") + var7 + GlyphTextUtil.encode("=") + var6);
                        }

                        ItemScoreCategory var8 = InventoryModuleBase.getItemCategory(var6);
                        float var9 = var8 == var7 ? var8.nfb(var6) : -1.0F;
                        int var10 = -1;
                        float var11 = var9;
                        ItemStack var12 = null;
                        if (var8 != var7 || var6.getMaxCount() <= 1 || var6.getCount() < var6.getMaxCount()) {
                           for (int var13 = 0; var13 < var2.getSize(); var13++) {
                              ItemStack var14 = var2.getStack(var13);
                              ItemScoreCategory var15 = InventoryModuleBase.getItemCategory(var14);
                              if (var15 == var7) {
                                 float var16 = var15.nfb(var14);
                                 if (var16 > var11) {
                                    var12 = var14;
                                    var11 = var16;
                                    var10 = var13;
                                 }
                              }
                           }

                           if (PhantomClient.traceEnabled) {
                              PhantomClient.trace(this, var18 + GlyphTextUtil.encode("=") + var7 + GlyphTextUtil.encode("=") + var6 + GlyphTextUtil.encode("=") + var10 + GlyphTextUtil.encode("=") + var11);
                           }

                           if (var10 >= 0) {
                              if (var1.xy() == ContainerPassMode.ss) {
                                 if (var18 >= 0 && var18 < 9 && var12.getItem() != var6.getItem()) {
                                    this.mxv(var2, var10, var18 + 36);
                                 } else {
                                    this.en(var2, var10);
                                 }

                                 return;
                              }

                              if (var1.xy() == ContainerPassMode.fp) {
                                 this.yu(var2, var10, Color.GREEN, null);
                              }
                           } else if (var6.getMaxCount() > 1 && var6.getCount() < var6.getMaxCount()) {
                              for (int var21 = 0; var21 < var2.getSize(); var21++) {
                                 ItemStack var22 = var2.getStack(var21);
                                 ItemScoreCategory var23 = InventoryModuleBase.getItemCategory(var22);
                                 if (var23 == var7 && ItemStack.areItemsAndComponentsEqual(var22, var6)) {
                                    if (var1.xy() == ContainerPassMode.ss) {
                                       this.en(var2, var21);
                                       return;
                                    }

                                    if (var1.xy() == ContainerPassMode.fp) {
                                       this.yu(var2, var21, Color.GREEN, null);
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (var1.xy() == ContainerPassMode.ss && (Boolean)this.qiu.getValue()) {
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, GlyphTextUtil.encode("end"));
               }

               this.closeScreen();
               this.jeu = -1L;
            }
         }
      } else {
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, GlyphTextUtil.encode("stop_not_chest:") + var1.yt().getContainerKind());
         }

         this.jeu = -1L;
      }
   }
}
