package me.phantom.util;

import me.phantom.core.PhantomClient;
import me.phantom.util.math.IntPoint;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.mc.KeyBindingTimesPressedAccessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.chunk.LevelChunk;

public class GameInteractionUtil {
   public static void tns(LocalPlayer var0, float var1, float var2) {
      double var3 = Math.pow((Double) Minecraft.getInstance().options.sensitivity().get() * 0.6F + 0.2F, 3.0) * 8.0;
      LocalPlayer var5 = Minecraft.getInstance().player;
      double var6 = (float)((int)(var1 / 0.15 / var3) * var3) * 0.15F;
      double var8 = (float)((int)(var2 / 0.15 / var3) * var3) * 0.15F;
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(GlyphTextUtil.encode("MCUtils"), GlyphTextUtil.encode("moveAngle"), var5.getYRot(), var6, var5.getXRot(), var8);
      }

      var5.setXRot((float) Mth.clamp(var5.getXRot() + var8, -90.0, 90.0));
      var5.setYRot((float)(var5.getYRot() + var6));
   }

   public static Vec3 getLerpedPos(Entity var0, float var1) {
      return var0.getPosition(var1);
   }

   public static float getLerpedYaw(Entity var0, float var1) {
      return var0.getViewYRot(var1);
   }

   public static float getLerpedPitch(Entity var0, float var1) {
      return var0.getViewXRot(var1);
   }

   public static Vec3 getLerpedEyePos(Entity var0, float var1) {
      return getLerpedPos(var0, var1).add(0.0, var0.getEyeHeight(var0.getPose()), 0.0);
   }

   public static Vec3 getRotationVector(Entity var0, float var1) {
      double var2 = Math.toRadians(getLerpedYaw(var0, var1));
      double var4 = Math.toRadians(getLerpedPitch(var0, var1));
      double var6 = Math.cos(var4);
      return new Vec3(-Math.sin(var2) * var6, -Math.sin(var4), Math.cos(var2) * var6);
   }

   public static boolean fsv(ClientLevel var0, Vec3 var1) {
      return !var0.getFluidState(new BlockPos((int)Math.floor(var1.x), (int)Math.floor(var1.y), (int)Math.floor(var1.z))).isEmpty();
   }

   public static float getArmorScore(ItemStack var0, EquipmentSlot var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      if (var2 == null) {
         return 0.0F;
      } else {
         HashMap<Holder<Attribute>, AttributeModifier> var3 = new HashMap<>();
         double var4 = 0.0;
         double var6 = 0.0;
         int var8 = 0;

         try {
            var0.forEachModifier(var1, var3::put);
         } catch (Throwable var14) {
         }

         try {
            for (Entry<Holder<Attribute>, AttributeModifier> var10 : var3.entrySet()) {
               if (((Holder)var10.getKey()).getRegisteredName().equals("minecraft:armor")) {
                  var4 = ((AttributeModifier)var10.getValue()).amount();
               } else if (((Holder)var10.getKey()).getRegisteredName().equals("minecraft:armor_toughness")) {
                  var6 = ((AttributeModifier)var10.getValue()).amount();
               }
            }
         } catch (Throwable var16) {
         }

         try {
            ItemEnchantments var17 = EnchantmentHelper.getEnchantmentsForCrafting(var0);

            for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var11 : var17.entrySet()) {
               String var12 = ((Holder)var11.getKey()).getRegisteredName();
               if (var12.equals("minecraft:protection")) {
                  var8 = var11.getIntValue();
               }
            }
         } catch (Throwable var15) {
         }

         try {
            return (float)(Math.min(var4, 10.0) * 10.0 * 10.0 + Math.min(var6, 10.0) * 10.0 + Math.min(var8, 10));
         } catch (Throwable var13) {
            return 0.0F;
         }
      }
   }

   public static float getWeaponScore(ItemStack var0, Entity var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      if (var2 == null) {
         return 0.0F;
      } else {
         if (var1 == null) {
            ;
         }

         HashMap var3 = new HashMap();

         try {
            var0.forEachModifier(EquipmentSlot.MAINHAND, var3::put);
         } catch (Throwable var11) {
         }

         AttributeModifier var4 = (AttributeModifier)var3.get(Attributes.ATTACK_DAMAGE);
         float var5 = var4 == null ? 1.0F : (float)var4.amount();
         int var6 = 0;

         try {
            ItemEnchantments var7 = EnchantmentHelper.getEnchantmentsForCrafting(var0);

            for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var9 : var7.entrySet()) {
               String var10 = ((Holder)var9.getKey()).getRegisteredName();
               if (var10.equals("minecraft:sharpness")) {
                  var6 = var9.getIntValue();
               }
            }
         } catch (Throwable var12) {
         }

         return var5 + (var6 > 0 ? 0.5F * var6 + 0.5F : 0.0F);
      }
   }

   public static void zgx() {
      incrementTimesPressed(Minecraft.getInstance().options.keyUse);
   }

   public static void uoo() {
      incrementTimesPressed(Minecraft.getInstance().options.keyAttack);
   }

   public static void xzn() {
      incrementTimesPressed(Minecraft.getInstance().options.keyDrop);
   }

   public static void incrementTimesPressed(KeyMapping var0) {
      KeyBindingTimesPressedAccessor var1 = (KeyBindingTimesPressedAccessor)var0;
      var1.setTimesPressed(var1.getTimesPressed() + 1);
   }

   public static Entity getCrosshairEntity() {
      return PhantomClient.mc().getCrosshairTarget() instanceof EntityHitResult ? ((EntityHitResult)PhantomClient.mc().getCrosshairTarget()).getEntity() : null;
   }

   public static AABB createBox(Vec3 var0, float var1, float var2) {
      var1 /= 2.0F;
      return new AABB(var0.x - var1, var0.y, var0.z - var1, var0.x + var1, var0.y + var2, var0.z + var1);
   }

   public static void sendPacketUnsafe(Packet<ServerGamePacketListenerImpl> var0) {
      try {
         PhantomClient.sendingPacket = true;
         Minecraft.getInstance().getConnection().send(var0);
      } finally {
         PhantomClient.sendingPacket = false;
      }
   }

   public static void handlePacketUnsafe(Packet<ClientPacketListener> var0) {
      try {
         PhantomClient.sendingPacket = true;
         var0.handle(Minecraft.getInstance().getConnection());
      } finally {
         PhantomClient.sendingPacket = false;
      }
   }

   public static EntityHitResult raycastEntity(Entity var0, Vec3 var1, Vec3 var2, AABB var3, Predicate<Entity> var4, double var5, Function<Entity, AABB> var7) {
      Level var8 = var0.level();
      double var9 = var5;
      Entity var11 = null;
      Vec3 var12 = null;

      for (Entity var14 : var8.getEntities(var0, var3, var4)) {
         AABB var15 = ((AABB)var7.apply(var14)).inflate(var14.getPickRadius());
         Optional<Vec3> var16 = var15.clip(var1, var2);
         if (var15.contains(var1)) {
            if (!(var9 < 0.0)) {
               var11 = var14;
               var12 = var16.orElse(var1);
               var9 = 0.0;
            }
         } else if (var16.isPresent()) {
            Vec3 var17 = (Vec3)var16.get();
            double var18 = var1.distanceToSqr(var17);
            if (!(var18 >= var9) || var9 == 0.0) {
               if (var14.getRootVehicle() == var0.getRootVehicle()) {
                  if (var9 == 0.0) {
                     var11 = var14;
                     var12 = var17;
                  }
               } else {
                  var11 = var14;
                  var12 = var17;
                  var9 = var18;
               }
            }
         }
      }

      return var11 == null ? null : new EntityHitResult(var11, var12);
   }

   public static HitResult qmg(LocalPlayer var0, Vec3 var1, Vec3 var2) {
      return raycastCombined(var0, var1, var2, Entity::getBoundingBox);
   }

   public static HitResult raycastCombined(LocalPlayer var0, Vec3 var1, Vec3 var2, Function<Entity, AABB> var3) {
      Minecraft var4 = Minecraft.getInstance();
      double var5 = var1.distanceTo(var2);
      HitResult var7 = var4.level.clip(new ClipContext(var1, var2, Block.OUTLINE, Fluid.NONE, var0));
      double var8 = var7 != null ? var7.getLocation().distanceToSqr(var1) : var5 * var5;
      Vec3 var10 = var2.subtract(var1).normalize();
      Vec3 var11 = var1.add(var10.x * var5, var10.y * var5, var10.z * var5);
      AABB var12 = var0.getBoundingBox().expandTowards(var10.scale(var5)).inflate(1.0, 1.0, 1.0);
      EntityHitResult var13 = raycastEntity(var0, var1, var11, var12, var0x -> !var0x.isSpectator() && var0x.isPickable(), var8, var3);
      if (var13 != null) {
         Vec3 var14 = var13.getLocation();
         double var15 = var1.distanceToSqr(var14);
         if (var15 > var5 * var5) {
            var7 = BlockHitResult.miss(var14, Direction.getApproximateNearest(var10.x, var10.y, var10.z), BlockPos.containing(var14));
         } else if (var15 < var8 || var7 == null) {
            var7 = var13;
         }
      }

      return (HitResult)var7;
   }

   public static HitResult raycastFromRotation(LocalPlayer var0, Vec3 var1, float var2, float var3, double var4) {
      return qmg(var0, var1, var1.add(RotationMathUtil.getLookVector(var2, var3).scale(var4)));
   }

   public static EntityHitResult raycastEntities(Level var0, Entity var1, Vec3 var2, Vec3 var3, AABB var4, Function<Entity, AABB> var5) {
      float var6 = 0.3F;
      double var7 = Double.MAX_VALUE;
      Entity var9 = null;

      for (Entity var11 : var0.getEntities(var1, var4, var1x -> var5.apply(var1x) != null)) {
         AABB var12 = ((AABB)var5.apply(var11)).inflate(0.3F);
         Optional var13 = var12.clip(var2, var3);
         if (var13.isPresent()) {
            double var14 = var2.distanceToSqr((Vec3)var13.get());
            if (var14 < var7) {
               var9 = var11;
               var7 = var14;
            }
         }
      }

      return var9 == null ? null : new EntityHitResult(var9);
   }

   public static ItemStack setPotionContents(ItemStack var0, Holder<Potion> var1) {
      var0.set(DataComponents.POTION_CONTENTS, new PotionContents(var1));
      return var0;
   }

   public static float xqg(ResourceKey<Enchantment> var0, ItemStack var1) {
      for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var3 : var1.getEnchantments().entrySet()) {
         if (Objects.equals(((Holder)var3.getKey()).unwrapKey().orElse(null), var0)) {
            return var3.getIntValue();
         }
      }

      return 0.0F;
   }

   public static List<MobEffectInstance> tnw(ItemStack var0) {
      LinkedList var1 = new LinkedList();

      for (MobEffectInstance var3 : ((PotionContents)var0.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY))
         .getAllEffects()) {
         var1.add(var3);
      }

      return var1;
   }

   public static List<ItemStack> getArmorAndOffHandStacks(LocalPlayer var0) {
      return Arrays.asList(
         var0.getItemBySlot(EquipmentSlot.HEAD),
         var0.getItemBySlot(EquipmentSlot.CHEST),
         var0.getItemBySlot(EquipmentSlot.LEGS),
         var0.getItemBySlot(EquipmentSlot.FEET)
      );
   }

   public static List<ItemStack> lqy(Player var0) {
      return Arrays.asList(var0.getMainHandItem(), var0.getOffhandItem());
   }

   public static boolean dwx(Item var0) {
      return var0 == Items.DIAMOND_SWORD
         || var0 == Items.GOLDEN_SWORD
         || var0 == Items.IRON_SWORD
         || var0 == Items.NETHERITE_SWORD
         || var0 == Items.STONE_SWORD
         || var0 == Items.WOODEN_SWORD;
   }

   public static boolean nm(Item var0) {
      return var0 == Items.DIAMOND_PICKAXE
         || var0 == Items.GOLDEN_PICKAXE
         || var0 == Items.IRON_PICKAXE
         || var0 == Items.NETHERITE_PICKAXE
         || var0 == Items.STONE_PICKAXE
         || var0 == Items.WOODEN_PICKAXE;
   }

   public static boolean vdw(Item var0) {
      return var0 == Items.CHAINMAIL_BOOTS
         || var0 == Items.CHAINMAIL_LEGGINGS
         || var0 == Items.CHAINMAIL_CHESTPLATE
         || var0 == Items.CHAINMAIL_HELMET
         || var0 == Items.LEATHER_BOOTS
         || var0 == Items.LEATHER_LEGGINGS
         || var0 == Items.LEATHER_CHESTPLATE
         || var0 == Items.LEATHER_HELMET
         || var0 == Items.IRON_BOOTS
         || var0 == Items.IRON_LEGGINGS
         || var0 == Items.IRON_CHESTPLATE
         || var0 == Items.IRON_HELMET
         || var0 == Items.GOLDEN_BOOTS
         || var0 == Items.GOLDEN_LEGGINGS
         || var0 == Items.GOLDEN_CHESTPLATE
         || var0 == Items.GOLDEN_HELMET
         || var0 == Items.DIAMOND_BOOTS
         || var0 == Items.DIAMOND_LEGGINGS
         || var0 == Items.DIAMOND_CHESTPLATE
         || var0 == Items.DIAMOND_HELMET
         || var0 == Items.NETHERITE_BOOTS
         || var0 == Items.NETHERITE_LEGGINGS
         || var0 == Items.NETHERITE_CHESTPLATE
         || var0 == Items.NETHERITE_HELMET
         || var0 == Items.TURTLE_HELMET;
   }

   public static List<LevelChunk> getLoadedChunks() {
      Minecraft var0 = Minecraft.getInstance();
      int var1 = Math.max(2, var0.options.getEffectiveRenderDistance()) + 3;
      int var2 = var1 * 2 + 1;
      ChunkPos var3 = var0.player.chunkPosition();
      IntPoint var4 = new IntPoint(var3.x() - var1, var3.z() - var1);
      IntPoint var5 = new IntPoint(var3.x() + var1, var3.z() + var1);
      return Stream.<IntPoint>iterate(var4, var2x -> {
            int var3x = var2x.x + 1;
            int var4x = var2x.y;
            if (var3x > var5.x) {
               var3x = var4.x;
               var4x++;
            }

            return new IntPoint(var3x, var4x);
         })
         .limit(var2 * var2)
         .filter(var1x -> var0.level.hasChunk(var1x.x, var1x.y))
         .map(var1x -> var0.level.getChunk(var1x.x, var1x.y))
         .filter(Objects::nonNull)
         .toList();
   }

   public static Set<Holder<Enchantment>> getEnchantments(ItemStack var0) {
      try {
         return EnchantmentHelper.getEnchantmentsForCrafting(var0).keySet();
      } catch (Throwable var2) {
         return Collections.emptySet();
      }
   }
}
