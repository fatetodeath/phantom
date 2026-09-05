package me.phantom.module.base;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.inventory.InventoryActionModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.InventoryWrapper;
import me.phantom.util.inventory.ItemScoreCategory;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.screen.slot.SlotActionType;

public class InventoryModuleBase extends BaseModule {
   public static final int[] plt = new int[]{
      36, 45, 37, 38, 39, 40, 41, 42, 43, 44, 45, 27, 28, 29, 30, 31, 32, 33, 34, 35, 18, 19, 20, 21, 22, 23, 24, 25, 26, 9, 10, 11, 12, 13, 14, 15, 16, 17
   };
   public static int[] fzs = new int[]{0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8};
   public static List<Item> gcr = Arrays.asList(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
   public static List<Item> bns = Arrays.asList(Items.COBWEB);
   public InventoryActionModule actionModule;
   public final FloatRangeSliderSetting xaz = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("delay"), 0.05F, 0.05F, 0.1F, 0.5F);
   public final ToggleSetting las = new ToggleSetting(this, GlyphTextUtil.encode("indicators"), true);

   public InventoryModuleBase(ModuleCategory var1, ClientPlayStyle var2, String var3) {
      super(var1, var2, var3);
   }

   public boolean ngv(InventoryWrapper var1) {
      for (int var2 = 0; var2 < var1.getSize(); var2++) {
         ItemStack var3 = var1.getStack(var2);
         if (var3 == null || var3.getItem() == Items.AIR) {
            return false;
         }
      }

      return true;
   }

   public void yu(InventoryWrapper var1, int var2, Color var3, ItemStack var4) {
      this.actionModule.uxl(var1, var2, var3, var4);
   }

   public void en(InventoryWrapper var1, int var2) {
      this.actionModule.queueAction(this, () -> var1.click(var2, 0, SlotActionType.QUICK_MOVE));
   }

   public void throwSlot(InventoryWrapper var1, int var2, boolean var3) {
      this.actionModule.queueAction(this, () -> var1.click(var2, var3 ? 1 : 0, SlotActionType.THROW));
   }

   public void pickupSlot(InventoryWrapper var1, int var2, boolean var3) {
      this.actionModule.queueAction(this, () -> var1.click(var2, var3 ? 1 : 0, SlotActionType.PICKUP));
   }

   public void mxv(InventoryWrapper var1, int var2, int var3) {
      if (var3 >= 36 && var3 < 45) {
         this.actionModule.queueAction(this, () -> var1.click(var2, var3 - 36, SlotActionType.SWAP));
      } else {
         this.actionModule.queueAction(this, () -> {
            var1.click(var2, 0, SlotActionType.PICKUP);
            var1.click(var3, 0, SlotActionType.PICKUP);
         });
      }
   }

   public void closeScreen() {
      if (MinecraftClient.getInstance().currentScreen instanceof HandledScreen var1) {
         var1.close();
      }
   }

   public static ItemScoreCategory getItemCategory(ItemStack var0) {
      if (var0 == null) {
         return ItemScoreCategory.ryh;
      } else {
         Item var1 = var0.getItem();
         if (var1 == null) {
            return ItemScoreCategory.ryh;
         } else if (var1 instanceof BlockItem var2
            && (var2.getBlock().getClass().equals(Block.class) || var2.getBlock() instanceof GrassBlock || var2.getBlock() instanceof PillarBlock)) {
            return ItemScoreCategory.fom;
         } else if (GameInteractionUtil.dwx(var1)) {
            return ItemScoreCategory.ko;
         } else if (GameInteractionUtil.nm(var1)) {
            return ItemScoreCategory.ta;
         } else if (var1 instanceof MaceItem) {
            return ItemScoreCategory.yaq;
         } else if (var1 instanceof AxeItem) {
            return ItemScoreCategory.db;
         } else if (var1 instanceof ExperienceBottleItem) {
            return ItemScoreCategory.bqi;
         } else if (GameInteractionUtil.vdw(var1)) {
            EquippableComponent var3 = (EquippableComponent)var1.getComponents().get(DataComponentTypes.EQUIPPABLE);
            if (var3 == null) {
               return ItemScoreCategory.ryh;
            } else {
               switch (var3.slot()) {
                  case HEAD:
                     return ItemScoreCategory.vxx;
                  case CHEST:
                     return ItemScoreCategory.qhx;
                  case LEGS:
                     return ItemScoreCategory.cbu;
                  case FEET:
                     return ItemScoreCategory.aqy;
                  default:
                     return ItemScoreCategory.ryh;
               }
            }
         } else if (var1 instanceof PotionItem || var1 instanceof SplashPotionItem) {
            return raa((PotionItem)var0.getItem(), var0) > 0.0F ? ItemScoreCategory.lqi : ItemScoreCategory.fpz;
         } else if (var1 instanceof EggItem) {
            return ItemScoreCategory.ui;
         } else if (var1 instanceof ArrowItem) {
            return ItemScoreCategory.jgo;
         } else if (var1 instanceof SnowballItem) {
            return ItemScoreCategory.pmb;
         } else if (var1 instanceof BowItem) {
            return ItemScoreCategory.wxo;
         } else if (var1 instanceof FishingRodItem) {
            return ItemScoreCategory.ytf;
         } else if (var1 instanceof EnderPearlItem) {
            return ItemScoreCategory.zle;
         } else if (var1.equals(Items.SHIELD)) {
            return ItemScoreCategory.ihl;
         } else if (var1.equals(Items.WATER_BUCKET)) {
            return ItemScoreCategory.dqx;
         } else if (var1.equals(Items.LAVA_BUCKET)) {
            return ItemScoreCategory.ogi;
         } else if (var1.equals(Items.FLINT_AND_STEEL)) {
            return ItemScoreCategory.zab;
         } else if (var1.equals(Items.FIRE_CHARGE)) {
            return ItemScoreCategory.zny;
         } else if (var1.equals(Items.COBWEB)) {
            return ItemScoreCategory.bk;
         } else if (var1.equals(Items.GOLD_INGOT)) {
            return ItemScoreCategory.zye;
         } else if (var1.equals(Items.IRON_INGOT)) {
            return ItemScoreCategory.vii;
         } else if (var1.equals(Items.EMERALD)) {
            return ItemScoreCategory.vbh;
         } else if (var1.equals(Items.DIAMOND)) {
            return ItemScoreCategory.gx;
         } else if (var1.equals(Items.COPPER_INGOT)) {
            return ItemScoreCategory.cqg;
         } else if (var1.equals(Items.SHEARS)) {
            return ItemScoreCategory.yuy;
         } else if (var1.equals(Items.MUSHROOM_STEW)) {
            return ItemScoreCategory.xzo;
         } else if (var1.equals(Items.TOTEM_OF_UNDYING)) {
            return ItemScoreCategory.exo;
         } else if (gcr.contains(var1)) {
            return ItemScoreCategory.ggc;
         } else {
            return var0.getItem().getComponents().contains(DataComponentTypes.FOOD) ? ItemScoreCategory.jva : ItemScoreCategory.ryh;
         }
      }
   }

   public static float raa(PotionItem var0, ItemStack var1) {
      float var2 = 0.0F;
      PotionContentsComponent var3 = (PotionContentsComponent)var1.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);

      for (StatusEffectInstance var5 : var3.getEffects()) {
         if (((StatusEffect)var5.getEffectType().value()).getCategory() == StatusEffectCategory.BENEFICIAL) {
            var2 += var5.getAmplifier() + 1;
         }
      }

      return var2;
   }

   public static float wah(PotionItem var0, ItemStack var1) {
      float var2 = 0.0F;
      PotionContentsComponent var3 = (PotionContentsComponent)var1.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);

      for (StatusEffectInstance var5 : var3.getEffects()) {
         if (((StatusEffect)var5.getEffectType().value()).getCategory() == StatusEffectCategory.HARMFUL) {
            var2 += var5.getAmplifier() + 1;
         }
      }

      return var2;
   }

   public static float zql(ItemStack var0) {
      return 1.0F + GameInteractionUtil.xqg(Enchantments.POWER, var0);
   }
}
