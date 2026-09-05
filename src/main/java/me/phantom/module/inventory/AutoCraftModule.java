package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ContainerKind;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.inventory.ContainerSwitchEvent;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.util.inventory.ContainerPassMode;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.InventoryWrapper;
import me.phantom.util.inventory.ShapedCraftingRecipe;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.CraftingScreenHandler;

public class AutoCraftModule extends InventoryModuleBase {
   public static final String nxb = GlyphTextUtil.encode("1_1111111");
   private static final String qrw = GlyphTextUtil.encode("1111_11_1");
   public static final String okq = GlyphTextUtil.encode("1111_1___");
   private static final String vbl = GlyphTextUtil.encode("1_11_1___");
   public static final String ig = GlyphTextUtil.encode("_1__1__2_");
   public static final String bnt = GlyphTextUtil.encode("111_2__2_");
   public static final String loj = GlyphTextUtil.encode("11_12__2_");
   private ShapedCraftingRecipe[] tbk;

   public AutoCraftModule() {
      super(ModuleCategory.iua, ClientPlayStyle.ats, GlyphTextUtil.encode("AutoCraft"));
      this.markClipboardHint();
   }

   @Override
   public void registerModules() {
      this.tbk = new ShapedCraftingRecipe[]{
         new ShapedCraftingRecipe(this, Items.DIAMOND_SWORD, ig, Items.DIAMOND, Items.STICK),
         new ShapedCraftingRecipe(this, Items.DIAMOND_CHESTPLATE, nxb, Items.DIAMOND),
         new ShapedCraftingRecipe(this, Items.DIAMOND_LEGGINGS, qrw, Items.DIAMOND),
         new ShapedCraftingRecipe(this, Items.DIAMOND_HELMET, okq, Items.DIAMOND),
         new ShapedCraftingRecipe(this, Items.DIAMOND_BOOTS, vbl, Items.DIAMOND),
         new ShapedCraftingRecipe(this, Items.DIAMOND_PICKAXE, bnt, Items.DIAMOND, Items.STICK),
         new ShapedCraftingRecipe(this, Items.DIAMOND_AXE, loj, Items.DIAMOND, Items.STICK),
         new ShapedCraftingRecipe(this, Items.IRON_SWORD, ig, Items.IRON_INGOT, Items.STICK).skipIfOwned(Items.DIAMOND_SWORD),
         new ShapedCraftingRecipe(this, Items.IRON_CHESTPLATE, nxb, Items.IRON_INGOT).skipIfOwned(Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE),
         new ShapedCraftingRecipe(this, Items.IRON_LEGGINGS, qrw, Items.IRON_INGOT).skipIfOwned(Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS),
         new ShapedCraftingRecipe(this, Items.IRON_HELMET, okq, Items.IRON_INGOT).skipIfOwned(Items.IRON_HELMET, Items.DIAMOND_HELMET),
         new ShapedCraftingRecipe(this, Items.IRON_BOOTS, vbl, Items.IRON_INGOT).skipIfOwned(Items.IRON_BOOTS, Items.DIAMOND_BOOTS),
         new ShapedCraftingRecipe(this, Items.IRON_PICKAXE, bnt, Items.IRON_INGOT, Items.STICK).skipIfOwned(Items.DIAMOND_PICKAXE),
         new ShapedCraftingRecipe(this, Items.IRON_AXE, loj, Items.IRON_INGOT, Items.STICK).skipIfOwned(Items.DIAMOND_AXE),
         new ShapedCraftingRecipe(this, Items.GOLDEN_SWORD, ig, Items.GOLD_INGOT, Items.STICK).skipIfOwned(Items.IRON_SWORD, Items.DIAMOND_SWORD),
         new ShapedCraftingRecipe(this, Items.GOLDEN_CHESTPLATE, nxb, Items.GOLD_INGOT).skipIfOwned(Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE),
         new ShapedCraftingRecipe(this, Items.GOLDEN_LEGGINGS, qrw, Items.GOLD_INGOT).skipIfOwned(Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS),
         new ShapedCraftingRecipe(this, Items.GOLDEN_HELMET, okq, Items.GOLD_INGOT).skipIfOwned(Items.IRON_HELMET, Items.DIAMOND_HELMET),
         new ShapedCraftingRecipe(this, Items.GOLDEN_BOOTS, vbl, Items.GOLD_INGOT).skipIfOwned(Items.IRON_BOOTS, Items.DIAMOND_BOOTS),
         new ShapedCraftingRecipe(this, Items.GOLDEN_PICKAXE, bnt, Items.GOLD_INGOT, Items.STICK).skipIfOwned(Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE),
         new ShapedCraftingRecipe(this, Items.GOLDEN_AXE, loj, Items.GOLD_INGOT, Items.STICK).skipIfOwned(Items.IRON_AXE, Items.DIAMOND_AXE),
         new ShapedCraftingRecipe(this, Items.STONE_SWORD, ig, Items.COBBLESTONE, Items.STICK).skipIfOwned(Items.GOLDEN_SWORD, Items.IRON_SWORD, Items.DIAMOND_SWORD),
         new ShapedCraftingRecipe(this, Items.LEATHER_CHESTPLATE, nxb, Items.LEATHER).skipIfOwned(Items.GOLDEN_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE),
         new ShapedCraftingRecipe(this, Items.LEATHER_LEGGINGS, qrw, Items.LEATHER).skipIfOwned(Items.GOLDEN_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS),
         new ShapedCraftingRecipe(this, Items.LEATHER_HELMET, okq, Items.LEATHER).skipIfOwned(Items.GOLDEN_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET),
         new ShapedCraftingRecipe(this, Items.LEATHER_BOOTS, vbl, Items.LEATHER).skipIfOwned(Items.GOLDEN_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS),
         new ShapedCraftingRecipe(this, Items.STONE_PICKAXE, bnt, Items.COBBLESTONE, Items.STICK).skipIfOwned(Items.GOLDEN_PICKAXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE),
         new ShapedCraftingRecipe(this, Items.STONE_AXE, loj, Items.COBBLESTONE, Items.STICK).skipIfOwned(Items.GOLDEN_AXE, Items.IRON_AXE, Items.DIAMOND_AXE)
      };
   }

   @Override
   public void onEnable() {
   }

   public boolean isEquipped(Item var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      List var3 = GameInteractionUtil.getArmorAndOffHandStacks(var2);

      for (int var4 = 0; var4 < var3.size(); var4++) {
         ItemStack var5 = (ItemStack)var3.get(var4);
         if (var5 != null && Objects.equals(var1, var5.getItem())) {
            return true;
         }
      }

      return false;
   }

   public void ee(ContainerSwitchEvent var1) {
      InventoryWrapper var2 = var1.eh();
      InventoryWrapper var3 = var1.yt();
      if (var3 != null && var3.getContainerKind() == ContainerKind.hss) {
         CraftingScreen var4 = (CraftingScreen)MinecraftClient.getInstance().currentScreen;
         CraftingScreenHandler var5 = (CraftingScreenHandler)var4.getScreenHandler();
         ItemStack var6 = var5.getCursorStack();
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, GlyphTextUtil.encode("InventoryActionEvent"));
         }

         ItemStack var7 = var3.getStack(0);
         if (var7 != null && !Objects.equals(var7.getItem(), Items.AIR)) {
            for (int var8 = 0; var8 < this.tbk.length; var8++) {
               ShapedCraftingRecipe var9 = this.tbk[var8];
               if (var2.countItem(var9.result) <= 0 && !this.isEquipped(var9.result) && var7 != null && Objects.equals(var7.getItem(), var9.result)) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("pick"));
                  }

                  this.en(var3, 0);
                  return;
               }
            }

            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("not_pickable"));
            }
         }

         for (int var15 = 0; var15 < this.tbk.length; var15++) {
            ShapedCraftingRecipe var16 = this.tbk[var15];
            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("recipe[") + var15 + GlyphTextUtil.encode("]"));
            }

            if (var2.countItem(var16.result) <= 0 && var16.canCraft(var2, var6) && !this.isEquipped(var16.result)) {
               this.yu(var3, 0, Color.green, new ItemStack(var16.result));
               if (var1.xy() == ContainerPassMode.fp) {
                  for (int var10 = 0; var10 < 9; var10++) {
                     char var11 = GlyphTextUtil.decodeChar(var16.pattern.charAt(var10));
                     if (var11 != '_') {
                        int var12 = var11 - '1';
                        Item var13 = var16.ingredients[var12];
                        ItemStack var14 = var3.getStack(var10 + 1);
                        if (var14 == null || !Objects.equals(var14.getItem(), var13)) {
                           this.yu(var3, var10 + 1, Color.yellow, new ItemStack(var13));
                        }
                     }
                  }
               } else {
                  var16.moveIngredients(var6, var2, var3);
               }

               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, GlyphTextUtil.encode("recipe[") + var15 + GlyphTextUtil.encode("] matches"));
               }
               break;
            }

            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("throw recipe"), var2.countItem(var16.result), var16.canCraft(var2, var6), this.isEquipped(var16.result));
            }
         }
      } else {
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, GlyphTextUtil.encode("not crafting table"));
         }
      }
   }

}
