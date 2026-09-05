package me.phantom.util.inventory;

import me.phantom.module.base.InventoryModuleBase;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potions;

public class ItemScoreCategory {
   public static final Map<String, ItemScoreCategory> plp = new LinkedHashMap<>();
   public static final ItemScoreCategory ryh = new ItemScoreCategory(GlyphTextUtil.encode("NONE"), new ItemStack(Items.BARRIER), var0 -> 0.0F);
   public static final ItemScoreCategory um = new ItemScoreCategory(GlyphTextUtil.encode("IGNORE"), new ItemStack(Items.BEDROCK), var0 -> 0.0F);
   public static final ItemScoreCategory ko = new ItemScoreCategory(GlyphTextUtil.encode("SWORD"), new ItemStack(Items.DIAMOND_SWORD), var0 -> GameInteractionUtil.getWeaponScore(var0, null));
   public static final ItemScoreCategory yaq = new ItemScoreCategory(GlyphTextUtil.encode("MACE"), new ItemStack(Items.MACE), var0 -> GameInteractionUtil.getWeaponScore(var0, null));
   public static final ItemScoreCategory ta = new ItemScoreCategory(
      GlyphTextUtil.encode("PICKAXE"), new ItemStack(Items.DIAMOND_PICKAXE), var0 -> var0.getMiningSpeedMultiplier(Blocks.STONE.getDefaultState())
   );
   public static final ItemScoreCategory db = new ItemScoreCategory(
      GlyphTextUtil.encode("AXE"), new ItemStack(Items.DIAMOND_AXE), var0 -> var0.getMiningSpeedMultiplier(Blocks.OAK_PLANKS.getDefaultState())
   );
   public static final ItemScoreCategory iz = new ItemScoreCategory(GlyphTextUtil.encode("TRIDENT"), new ItemStack(Items.TRIDENT), var0 -> GameInteractionUtil.getWeaponScore(var0, null));
   public static final ItemScoreCategory bqi = new ItemScoreCategory(GlyphTextUtil.encode("EXP_BOTTLE"), new ItemStack(Items.EXPERIENCE_BOTTLE), var0 -> (float)(var0.getCount() * 8));
   public static final ItemScoreCategory vxx = new ItemScoreCategory(GlyphTextUtil.encode("HELMET"), new ItemStack(Items.DIAMOND_HELMET), var0 -> GameInteractionUtil.getArmorScore(var0, EquipmentSlot.HEAD));
   public static final ItemScoreCategory qhx = new ItemScoreCategory(GlyphTextUtil.encode("CHESTPLATE"), new ItemStack(Items.DIAMOND_CHESTPLATE), var0 -> GameInteractionUtil.getArmorScore(var0, EquipmentSlot.CHEST));
   public static final ItemScoreCategory cbu = new ItemScoreCategory(GlyphTextUtil.encode("LEGGINGS"), new ItemStack(Items.DIAMOND_LEGGINGS), var0 -> GameInteractionUtil.getArmorScore(var0, EquipmentSlot.LEGS));
   public static final ItemScoreCategory aqy = new ItemScoreCategory(GlyphTextUtil.encode("BOOTS"), new ItemStack(Items.DIAMOND_BOOTS), var0 -> GameInteractionUtil.getArmorScore(var0, EquipmentSlot.FEET));
   public static final ItemScoreCategory fpz = new ItemScoreCategory(
      GlyphTextUtil.encode("BAD_POTION"), GameInteractionUtil.setPotionContents(new ItemStack(Items.POTION), Potions.POISON), var0 -> InventoryModuleBase.wah((PotionItem)var0.getItem(), var0)
   );
   public static final ItemScoreCategory lqi = new ItemScoreCategory(
      GlyphTextUtil.encode("GOOD_POTION"), GameInteractionUtil.setPotionContents(new ItemStack(Items.POTION), Potions.HEALING), var0 -> InventoryModuleBase.raa((PotionItem)var0.getItem(), var0)
   );
   public static final ItemScoreCategory wxo = new ItemScoreCategory(GlyphTextUtil.encode("BOW"), new ItemStack(Items.BOW), var0 -> InventoryModuleBase.zql(var0));
   public static final ItemScoreCategory jgo = new ItemScoreCategory(GlyphTextUtil.encode("ARROW"), new ItemStack(Items.ARROW), var0 -> var0.getCount() * 10.0F);
   public static final ItemScoreCategory ytf = new ItemScoreCategory(GlyphTextUtil.encode("FISHING_ROD"), new ItemStack(Items.FISHING_ROD), var0 -> (float)var0.getMaxDamage() - var0.getDamage());
   public static final ItemScoreCategory fom = new ItemScoreCategory(GlyphTextUtil.encode("BUILDING"), new ItemStack(Items.OAK_PLANKS), var0 -> (float)(var0.getCount() * 8));
   public static final ItemScoreCategory zle = new ItemScoreCategory(GlyphTextUtil.encode("ENDER_PEARL"), new ItemStack(Items.ENDER_PEARL), var0 -> 0.0F);
   public static final ItemScoreCategory ggc = new ItemScoreCategory(GlyphTextUtil.encode("GOLDEN_APPLE"), new ItemStack(Items.GOLDEN_APPLE), var0 -> InventoryModuleBase.gcr.indexOf(var0.getItem()) * 10.0F + 10.0F);
   public static final ItemScoreCategory jva = new ItemScoreCategory(
      GlyphTextUtil.encode("FOOD"),
      new ItemStack(Items.COOKED_BEEF),
      var0 -> Optional.ofNullable((FoodComponent)var0.getItem().getComponents().get(DataComponentTypes.FOOD)).<Float>map(FoodComponent::saturation).get()
   );
   public static final ItemScoreCategory ihl = new ItemScoreCategory(GlyphTextUtil.encode("SHIELD"), new ItemStack(Items.SHIELD), var0 -> var0.getItem().equals(Items.SHIELD) ? 1.0F : 0.0F);
   public static final ItemScoreCategory dqx = new ItemScoreCategory(
      GlyphTextUtil.encode("WATER_BUCKET"), new ItemStack(Items.WATER_BUCKET), var0 -> var0.getItem().equals(Items.WATER_BUCKET) ? 1.0F : 0.0F
   );
   public static final ItemScoreCategory ogi = new ItemScoreCategory(
      GlyphTextUtil.encode("LAVA_BUCKET"), new ItemStack(Items.LAVA_BUCKET), var0 -> var0.getItem().equals(Items.WATER_BUCKET) ? 1.0F : 0.0F
   );
   public static final ItemScoreCategory zab = new ItemScoreCategory(
      GlyphTextUtil.encode("FLINT_AND_STEEL"), new ItemStack(Items.FLINT_AND_STEEL), var0 -> var0.getItem().equals(Items.FLINT_AND_STEEL) ? 1.0F : 0.0F
   );
   public static final ItemScoreCategory zny = new ItemScoreCategory(GlyphTextUtil.encode("FIRE_CHARGE"), new ItemStack(Items.FIRE_CHARGE), var0 -> var0.getItem().equals(Items.FIRE_CHARGE) ? 1.0F : 0.0F);
   public static final ItemScoreCategory bk = new ItemScoreCategory(GlyphTextUtil.encode("COBWEB"), new ItemStack(Items.COBWEB), var0 -> var0.getItem().equals(Items.COBWEB) ? 1.0F : 0.0F);
   public static final ItemScoreCategory zye = new ItemScoreCategory(GlyphTextUtil.encode("GOLD"), new ItemStack(Items.GOLD_INGOT), var0 -> var0.getItem().equals(Items.GOLD_INGOT) ? 1.0F : 0.0F);
   public static final ItemScoreCategory vii = new ItemScoreCategory(GlyphTextUtil.encode("IRON"), new ItemStack(Items.IRON_INGOT), var0 -> var0.getItem().equals(Items.IRON_INGOT) ? 1.0F : 0.0F);
   public static final ItemScoreCategory vbh = new ItemScoreCategory(GlyphTextUtil.encode("EMERALD"), new ItemStack(Items.EMERALD), var0 -> var0.getItem().equals(Items.EMERALD) ? 1.0F : 0.0F);
   public static final ItemScoreCategory gx = new ItemScoreCategory(GlyphTextUtil.encode("DIAMOND"), new ItemStack(Items.DIAMOND), var0 -> var0.getItem().equals(Items.DIAMOND) ? 1.0F : 0.0F);
   public static final ItemScoreCategory cqg = new ItemScoreCategory(GlyphTextUtil.encode("COPPER"), new ItemStack(Items.COPPER_INGOT), var0 -> var0.getItem().equals(Items.COPPER_INGOT) ? 1.0F : 0.0F);
   public static final ItemScoreCategory xzo = new ItemScoreCategory(GlyphTextUtil.encode("SOUP"), new ItemStack(Items.MUSHROOM_STEW), var0 -> 1.0F);
   public static final ItemScoreCategory yuy = new ItemScoreCategory(GlyphTextUtil.encode("SHEARS"), new ItemStack(Items.SHEARS), var0 -> 1.0F);
   public static final ItemScoreCategory ui = new ItemScoreCategory(GlyphTextUtil.encode("EGG"), new ItemStack(Items.EGG), var0 -> var0.getCount() * 10.0F);
   public static final ItemScoreCategory pmb = new ItemScoreCategory(GlyphTextUtil.encode("SNOWBALL"), new ItemStack(Items.SNOWBALL), var0 -> var0.getCount() * 10.0F);
   public static final ItemScoreCategory exo = new ItemScoreCategory(GlyphTextUtil.encode("TOTEM"), new ItemStack(Items.TOTEM_OF_UNDYING), var0 -> 1.0F);
   private static int vzy;
   public final String name;
   public final ItemStack stack;
   public final Function<ItemStack, Float> jgy;
   public final int yay;

   public ItemScoreCategory(String var1, ItemStack var2, Function<ItemStack, Float> var3) {
      this.yay = vzy++;
      this.name = var1;
      this.stack = var2;
      this.jgy = var3;
      plp.put(var1, this);
   }

   public float nfb(ItemStack var1) {
      return this.jgy.apply(var1);
   }

   public boolean srz() {
      return this == db || this == ko || this == iz || this == yaq;
   }

   public static ItemScoreCategory[] qfo() {
      return plp.values().toArray(ItemScoreCategory[]::new);
   }

   public String vr() {
      return this.name;
   }

   public int getOrderIndex() {
      return this.yay;
   }

   public static ItemScoreCategory ol(String var0) {
      return Optional.ofNullable(plp.get(var0)).orElseThrow();
   }
}
