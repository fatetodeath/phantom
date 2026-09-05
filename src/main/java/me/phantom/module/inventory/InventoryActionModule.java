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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
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
      if (MinecraftClient.getInstance().currentScreen instanceof CraftingScreen var4) {
         final CraftingScreenHandler var10 = (CraftingScreenHandler)var4.getScreenHandler();
         var2 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2x, SlotActionType var3x) {
               MinecraftClient var4x = MinecraftClient.getInstance();
               var4x.interactionManager.clickSlot(var10.syncId, var1, var2x, var3x, var4x.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var10.getStacks().get(var1);
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
         final PlayerInventory var6 = MinecraftClient.getInstance().player.getInventory();
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, SlotActionType var3) {
               MinecraftClient var4 = MinecraftClient.getInstance();
               var4.interactionManager.clickSlot(var10.syncId, var1 + 10, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return var10.getSlot(var1 + 10).getStack();
            }

            @Override
            public Slot getSlotAt(int var1) {
               return new Slot(var6, var1, 0, 0);
            }

            @Override
            public int getSize() {
               return var6.getMainStacks().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      } else if (MinecraftClient.getInstance().currentScreen instanceof EnchantmentScreen) {
         EnchantmentScreen var11 = (EnchantmentScreen)MinecraftClient.getInstance().currentScreen;
         final EnchantmentScreenHandler var15 = (EnchantmentScreenHandler)var11.getScreenHandler();
         var2 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, SlotActionType var3) {
               MinecraftClient var4 = MinecraftClient.getInstance();
               var4.interactionManager.clickSlot(var15.syncId, var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var15.getStacks().get(var1);
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
         final PlayerInventory var7 = MinecraftClient.getInstance().player.getInventory();
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, SlotActionType var3) {
               MinecraftClient var4 = MinecraftClient.getInstance();
               var4.interactionManager.clickSlot(var15.syncId, 2 + var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var7.getMainStacks().get(var1);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return new Slot(var7, var1, 0, 0);
            }

            @Override
            public int getSize() {
               return var7.getMainStacks().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      } else if (MinecraftClient.getInstance().currentScreen instanceof CraftingScreen) {
         CraftingScreen var12 = (CraftingScreen)MinecraftClient.getInstance().currentScreen;
         final CraftingScreenHandler var16 = (CraftingScreenHandler)var12.getScreenHandler();
         var2 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, SlotActionType var3) {
               MinecraftClient var4 = MinecraftClient.getInstance();
               var4.interactionManager.clickSlot(var16.syncId, var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var16.getStacks().get(var1);
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
         final PlayerInventory var19 = MinecraftClient.getInstance().player.getInventory();
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, SlotActionType var3) {
               MinecraftClient var4 = MinecraftClient.getInstance();
               var4.interactionManager.clickSlot(var16.syncId, 10 + var1, var2, var3, var4.player);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return new Slot(var19, var1, 0, 0);
            }

            @Override
            public ItemStack getStack(int var1) {
               return (ItemStack)var19.getMainStacks().get(var1);
            }

            @Override
            public int getSize() {
               return var19.getMainStacks().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      } else if (MinecraftClient.getInstance().currentScreen instanceof GenericContainerScreen) {
         final GenericContainerScreen var13 = (GenericContainerScreen)MinecraftClient.getInstance().currentScreen;
         final GenericContainerScreenHandler var17 = (GenericContainerScreenHandler)var13.getScreenHandler();
         final ContainerKind var20 = var13.getTitle() != null
            && var13.getTitle().getContent() != null
            && var13.getTitle().getContent() instanceof TranslatableTextContent var8
            && var8.getKey().equals(GlyphTextUtil.plain("container.enderchest"))
            ? ContainerKind.uyr
            : ContainerKind.phn;

         var2 = new InventoryWrapper() {
            @Override
            public Text getTitle() {
               return var13.getTitle();
            }

            @Override
            public void click(int var1, int var2, SlotActionType var3) {
               MinecraftClient var4 = MinecraftClient.getInstance();
               var4.interactionManager.clickSlot(var17.syncId, var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return var17.getInventory().getStack(var1);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return var17.getSlot(var1);
            }

            @Override
            public int getSize() {
               return var17.getInventory().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return var20;
            }
         };
         final PlayerInventory var21 = MinecraftClient.getInstance().player.getInventory();
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, SlotActionType var3) {
               MinecraftClient var4 = MinecraftClient.getInstance();
               var4.interactionManager.clickSlot(var17.syncId, var17.slots.size() + var1, var2, var3, var4.player);
            }

            @Override
            public Slot getSlotAt(int var1) {
               return new Slot(var21, var1, 0, 0);
            }

            @Override
            public ItemStack getStack(int var1) {
               return var21.getStack(var1);
            }

            @Override
            public int getSize() {
               return var21.getMainStacks().size();
            }

            @Override
            public ContainerKind getContainerKind() {
               return ContainerKind.rgk;
            }
         };
      } else {
         if (!(MinecraftClient.getInstance().currentScreen instanceof InventoryScreen)) {
            this.jkq = 0L;
            this.ule.clear();
            return;
         }

         InventoryScreen var14 = (InventoryScreen)MinecraftClient.getInstance().currentScreen;
         final ScreenHandler var18 = var14.getScreenHandler();
         var2 = null;
         var3 = new InventoryWrapper() {
            @Override
            public void click(int var1, int var2, SlotActionType var3) {
               MinecraftClient var4 = MinecraftClient.getInstance();
               var4.interactionManager.clickSlot(var18.syncId, var1, var2, var3, var4.player);
            }

            @Override
            public ItemStack getStack(int var1) {
               return var18.getSlot(var1).getStack();
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
         this.fm.removeIf(var1x -> Objects.equals(var5.inventory, var1x.inventory) && var1x.slotIndex == var5.getIndex());
         this.fm.add(new InventorySlotHighlight(this, var5.inventory, var5.getIndex(), var3, var4));
      }
   }

   public void byc(DrawContext var1, Slot var2) {
      InventorySlotHighlight var3 = this.fm.stream().filter(var1x -> Objects.equals(var2.inventory, var1x.inventory) && var1x.slotIndex == var2.getIndex()).findFirst().orElse(null);
      if (var3 != null) {
         if (var3.iconStack != null) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.3F);
            var1.drawItem(var3.iconStack, var2.x, var2.y, 0);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         }

         if (var3.color != null) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.75F);
            var1.fill(var2.x, var2.y, var2.x + 16, var2.y + 16, var3.color.getRGB());
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }
   }
}
