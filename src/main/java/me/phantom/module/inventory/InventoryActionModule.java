package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ContainerKind;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.inventory.ContainerSwitchEvent;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.gui.widget.InventorySlotHighlight;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.util.inventory.ContainerPassMode;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.InventoryWrapper;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
public class InventoryActionModule extends BaseModule {
   public static InventoryActionModule acr;
   public long jkq;
   public final List<Runnable> ule = new LinkedList<>();
   public final List<InventorySlotHighlight> fm = new LinkedList<>();
   public ContainerPassMode yr;
   public long gxi;

   public InventoryActionModule() {
      super(ModuleCategory.iua, ClientPlayStyle.or, GlyphTextUtil.encode("InventoryAction"));
      acr = this;
      this.setEnabled(true, true);
   }

   @Override
   public boolean lvr() {
      return false;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.setEnabled(true, true);
   }

   public void wj(WorldRenderOverlayEvent var1) {
      InventoryWrapper var2;
      InventoryWrapper var3;
      if (Minecraft.getInstance().screen instanceof CraftingScreen var4) {
         final CraftingMenu var10 = (CraftingMenu)var4.getMenu();
         var2 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2x, ContainerInput var3x) {
               Minecraft var4x = Minecraft.getInstance();
               var4x.gameMode.handleContainerInput(var10.containerId, var1, var2x, var3x, var4x.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var10.getItems().get(var1);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return var10.getSlot(var1);
            }

            @Override
            public int getSize() {
               return 10;
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.hss;
            }
         };
         final Inventory var6 = Minecraft.getInstance().player.getInventory();
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, ContainerInput var3) {
               Minecraft var4 = Minecraft.getInstance();
               var4.gameMode.handleContainerInput(var10.containerId, var1 + 10, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return var10.getSlot(var1 + 10).getItem();
            }

            @Override
            public Slot getSlotAt(int var1) {
               return new Slot(var6, var1, 0, 0);
            }

            @Override
            public int getSize() {
               return var6.getNonEquipmentItems().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      } else if (Minecraft.getInstance().screen instanceof EnchantmentScreen) {
         EnchantmentScreen var11 = (EnchantmentScreen) Minecraft.getInstance().screen;
         final EnchantmentMenu var15 = (EnchantmentMenu)var11.getMenu();
         var2 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, ContainerInput var3) {
               Minecraft var4 = Minecraft.getInstance();
               var4.gameMode.handleContainerInput(var15.containerId, var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var15.getItems().get(var1);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return var15.getSlot(var1);
            }

            @Override
            public int getSize() {
               return 2;
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.zk;
            }
         };
         final Inventory var7 = Minecraft.getInstance().player.getInventory();
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, ContainerInput var3) {
               Minecraft var4 = Minecraft.getInstance();
               var4.gameMode.handleContainerInput(var15.containerId, 2 + var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var7.getNonEquipmentItems().get(var1);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return new Slot(var7, var1, 0, 0);
            }

            @Override
            public int getSize() {
               return var7.getNonEquipmentItems().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      } else if (Minecraft.getInstance().screen instanceof CraftingScreen) {
         CraftingScreen var12 = (CraftingScreen) Minecraft.getInstance().screen;
         final CraftingMenu var16 = (CraftingMenu)var12.getMenu();
         var2 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, ContainerInput var3) {
               Minecraft var4 = Minecraft.getInstance();
               var4.gameMode.handleContainerInput(var16.containerId, var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var16.getItems().get(var1);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return var16.getSlot(var1);
            }

            @Override
            public int getSize() {
               return 10;
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.hss;
            }
         };
         final Inventory var19 = Minecraft.getInstance().player.getInventory();
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, ContainerInput var3) {
               Minecraft var4 = Minecraft.getInstance();
               var4.gameMode.handleContainerInput(var16.containerId, 10 + var1, var2, var3, var4.player);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return new Slot(var19, var1, 0, 0);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var19.getNonEquipmentItems().get(var1);
            }

            @Override
            public int getSize() {
               return var19.getNonEquipmentItems().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      } else if (Minecraft.getInstance().screen instanceof ContainerScreen) {
         final ContainerScreen var13 = (ContainerScreen) Minecraft.getInstance().screen;
         final ChestMenu var17 = (ChestMenu)var13.getMenu();
         final ContainerKind var20 = var13.getTitle() != null
            && var13.getTitle().getContents() != null
            && var13.getTitle().getContents() instanceof TranslatableContents var8
            && var8.getKey().equals(GlyphTextUtil.plain("container.enderchest"))
            ? ContainerKind.uyr
            : ContainerKind.phn;

         var2 = new InventoryWrapper() {
            @Override
            public Component getTitle() {
               return var13.getTitle();
            }

            @Override
            public void click(int var1, int var2, ContainerInput var3) {
               Minecraft var4 = Minecraft.getInstance();
               var4.gameMode.handleContainerInput(var17.containerId, var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return var17.getContainer().getItem(var1);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return var17.getSlot(var1);
            }

            @Override
            public int getSize() {
               return var17.getContainer().getContainerSize();
            }

            @Override
            public ContainerKind getContainerKind() {
               return var20;
            }
         };
         final Inventory var21 = Minecraft.getInstance().player.getInventory();
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, ContainerInput var3) {
               Minecraft var4 = Minecraft.getInstance();
               var4.gameMode.handleContainerInput(var17.containerId, var17.slots.size() + var1, var2, var3, var4.player);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return new Slot(var21, var1, 0, 0);
            }

            @Override
            public ItemStack getStack(int var1) {
               return var21.getItem(var1);
            }

            @Override
            public int getSize() {
               return var21.getNonEquipmentItems().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      } else {
         if (!(Minecraft.getInstance().screen instanceof InventoryScreen)) {
            this.jkq = 0L;
            this.ule.clear();
            return;
         }

         InventoryScreen var14 = (InventoryScreen) Minecraft.getInstance().screen;
         final AbstractContainerMenu var18 = var14.getMenu();
         var2 = null;
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, ContainerInput var3) {
               Minecraft var4 = Minecraft.getInstance();
               var4.gameMode.handleContainerInput(var18.containerId, var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return var18.getSlot(var1).getItem();
            }

            @Override
            public Slot getSlotAt(int var1) {
               return var18.getSlot(var1);
            }

            @Override
            public int getSize() {
               return var18.slots.size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      }

      this.yr = ContainerPassMode.fp;
      this.fm.clear();
      PhantomClient.eventManager.dispatch(ContainerSwitchEvent.get(var2, var3, this.yr));
      if (!this.ule.isEmpty()) {
         if (System.currentTimeMillis() > this.jkq) {
            this.ule.remove(0).run();
            this.jkq = this.gxi;
         }
      } else {
         this.yr = ContainerPassMode.ss;
         PhantomClient.eventManager.dispatch(ContainerSwitchEvent.get(var2, var3, this.yr));
      }
   }

   public void queueAction(InventoryModuleBase var1, Runnable var2) {
      if (this.yr == ContainerPassMode.ss) {
         this.ule.add(var2);
         this.jkq = System.currentTimeMillis() + (int)(1000.0F * var1.xaz.fbz());
      }
   }

   public void uxl(InventoryWrapper var1, int var2, Color var3, ItemStack var4) {
      if (this.yr == ContainerPassMode.fp) {
         Slot var5 = var1.getSlotAt(var2);
         this.fm.removeIf(var1x -> Objects.equals(var5.container, var1x.inventory) && var1x.slotIndex == var5.getContainerSlot());
         this.fm.add(new InventorySlotHighlight(this, var5.container, var5.getContainerSlot(), var3, var4));
      }
   }

    public void byc(GuiGraphicsExtractor var1, Slot var2) {
       InventorySlotHighlight var3 = this.fm.stream().filter(var1x -> Objects.equals(var2.container, var1x.inventory) && var1x.slotIndex == var2.getContainerSlot()).findFirst().orElse(null);
       if (var3 != null) {
          if (var3.iconStack != null) {
             var1.item(var3.iconStack, var2.x, var2.y, 0);
          }

          if (var3.color != null) {
             var1.fill(var2.x, var2.y, var2.x + 16, var2.y + 16, var3.color.getRGB());
          }
       }
    }
}
