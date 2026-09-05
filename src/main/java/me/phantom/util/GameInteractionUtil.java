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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.potion.Potion;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import net.minecraft.world.chunk.WorldChunk;

public class GameInteractionUtil {
   public static void tns(ClientPlayerEntity var0, float var1, float var2) {
      double var3 = Math.pow((Double)MinecraftClient.getInstance().options.getMouseSensitivity().getValue() * 0.6F + 0.2F, 3.0) * 8.0;
      ClientPlayerEntity var5 = MinecraftClient.getInstance().player;
      double var6 = (float)((int)(var1 / 0.15 / var3) * var3) * 0.15F;
      double var8 = (float)((int)(var2 / 0.15 / var3) * var3) * 0.15F;
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(GlyphTextUtil.encode("MCUtils"), GlyphTextUtil.encode("moveAngle"), var5.getYaw(), var6, var5.getPitch(), var8);
      }

      var5.setPitch((float)MathHelper.clamp(var5.getPitch() + var8, -90.0, 90.0));
      var5.setYaw((float)(var5.getYaw() + var6));
   }

   public static Vec3d getLerpedPos(Entity var0, float var1) {
      return var0.getLerpedPos(var1);
   }

   public static float getLerpedYaw(Entity var0, float var1) {
      return var0.getYaw(var1);
   }

   public static float getLerpedPitch(Entity var0, float var1) {
      return var0.getPitch(var1);
   }

   public static Vec3d getLerpedEyePos(Entity var0, float var1) {
      return getLerpedPos(var0, var1).add(0.0, var0.getEyeHeight(var0.getPose()), 0.0);
   }

   public static Vec3d getRotationVector(Entity var0, float var1) {
      double var2 = Math.toRadians(getLerpedYaw(var0, var1));
      double var4 = Math.toRadians(getLerpedPitch(var0, var1));
      double var6 = Math.cos(var4);
      return new Vec3d(-Math.sin(var2) * var6, -Math.sin(var4), Math.cos(var2) * var6);
   }

   public static boolean fsv(ClientWorld var0, Vec3d var1) {
      return !var0.getFluidState(new BlockPos((int)Math.floor(var1.x), (int)Math.floor(var1.y), (int)Math.floor(var1.z))).isEmpty();
   }

   public static float getArmorScore(ItemStack var0, EquipmentSlot var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      if (var2 == null) {
         return 0.0F;
      } else {
         HashMap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> var3 = new HashMap<>();
         double var4 = 0.0;
         double var6 = 0.0;
         int var8 = 0;

         try {
            var0.applyAttributeModifiers(var1, var3::put);
         } catch (Throwable var14) {
         }

         try {
            for (Entry<RegistryEntry<EntityAttribute>, EntityAttributeModifier> var10 : var3.entrySet()) {
               if (((RegistryEntry)var10.getKey()).getIdAsString().equals("minecraft:armor")) {
                  var4 = ((EntityAttributeModifier)var10.getValue()).value();
               } else if (((RegistryEntry)var10.getKey()).getIdAsString().equals("minecraft:armor_toughness")) {
                  var6 = ((EntityAttributeModifier)var10.getValue()).value();
               }
            }
         } catch (Throwable var16) {
         }

         try {
            ItemEnchantmentsComponent var17 = EnchantmentHelper.getEnchantments(var0);

            for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var11 : var17.getEnchantmentEntries()) {
               String var12 = ((RegistryEntry)var11.getKey()).getIdAsString();
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
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      if (var2 == null) {
         return 0.0F;
      } else {
         if (var1 == null) {
            ;
         }

         HashMap var3 = new HashMap();

         try {
            var0.applyAttributeModifiers(EquipmentSlot.MAINHAND, var3::put);
         } catch (Throwable var11) {
         }

         EntityAttributeModifier var4 = (EntityAttributeModifier)var3.get(EntityAttributes.ATTACK_DAMAGE);
         float var5 = var4 == null ? 1.0F : (float)var4.value();
         int var6 = 0;

         try {
            ItemEnchantmentsComponent var7 = EnchantmentHelper.getEnchantments(var0);

            for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var9 : var7.getEnchantmentEntries()) {
               String var10 = ((RegistryEntry)var9.getKey()).getIdAsString();
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
      incrementTimesPressed(MinecraftClient.getInstance().options.useKey);
   }

   public static void uoo() {
      incrementTimesPressed(MinecraftClient.getInstance().options.attackKey);
   }

   public static void xzn() {
      incrementTimesPressed(MinecraftClient.getInstance().options.dropKey);
   }

   public static void incrementTimesPressed(KeyBinding var0) {
      KeyBindingTimesPressedAccessor var1 = (KeyBindingTimesPressedAccessor)var0;
      var1.setTimesPressed(var1.getTimesPressed() + 1);
   }

   public static Entity getCrosshairEntity() {
      return PhantomClient.mc().getCrosshairTarget() instanceof EntityHitResult ? ((EntityHitResult)PhantomClient.mc().getCrosshairTarget()).getEntity() : null;
   }

   public static Box createBox(Vec3d var0, float var1, float var2) {
      var1 /= 2.0F;
      return new Box(var0.x - var1, var0.y, var0.z - var1, var0.x + var1, var0.y + var2, var0.z + var1);
   }

   public static void sendPacketUnsafe(Packet<ServerPlayNetworkHandler> var0) {
      try {
         PhantomClient.sendingPacket = true;
         MinecraftClient.getInstance().getNetworkHandler().sendPacket(var0);
      } finally {
         PhantomClient.sendingPacket = false;
      }
   }

   public static void handlePacketUnsafe(Packet<ClientPlayNetworkHandler> var0) {
      try {
         PhantomClient.sendingPacket = true;
         var0.apply(MinecraftClient.getInstance().getNetworkHandler());
      } finally {
         PhantomClient.sendingPacket = false;
      }
   }

   public static EntityHitResult raycastEntity(Entity var0, Vec3d var1, Vec3d var2, Box var3, Predicate<Entity> var4, double var5, Function<Entity, Box> var7) {
      World var8 = var0.getWorld();
      double var9 = var5;
      Entity var11 = null;
      Vec3d var12 = null;

      for (Entity var14 : var8.getOtherEntities(var0, var3, var4)) {
         Box var15 = ((Box)var7.apply(var14)).expand(var14.getTargetingMargin());
         Optional<Vec3d> var16 = var15.raycast(var1, var2);
         if (var15.contains(var1)) {
            if (!(var9 < 0.0)) {
               var11 = var14;
               var12 = var16.orElse(var1);
               var9 = 0.0;
            }
         } else if (var16.isPresent()) {
            Vec3d var17 = (Vec3d)var16.get();
            double var18 = var1.squaredDistanceTo(var17);
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

   public static HitResult qmg(ClientPlayerEntity var0, Vec3d var1, Vec3d var2) {
      return raycastCombined(var0, var1, var2, Entity::getBoundingBox);
   }

   public static HitResult raycastCombined(ClientPlayerEntity var0, Vec3d var1, Vec3d var2, Function<Entity, Box> var3) {
      MinecraftClient var4 = MinecraftClient.getInstance();
      double var5 = var1.distanceTo(var2);
      HitResult var7 = var4.world.raycast(new RaycastContext(var1, var2, ShapeType.OUTLINE, FluidHandling.NONE, var0));
      double var8 = var7 != null ? var7.getPos().squaredDistanceTo(var1) : var5 * var5;
      Vec3d var10 = var2.subtract(var1).normalize();
      Vec3d var11 = var1.add(var10.x * var5, var10.y * var5, var10.z * var5);
      Box var12 = var0.getBoundingBox().stretch(var10.multiply(var5)).expand(1.0, 1.0, 1.0);
      EntityHitResult var13 = raycastEntity(var0, var1, var11, var12, var0x -> !var0x.isSpectator() && var0x.canHit(), var8, var3);
      if (var13 != null) {
         Vec3d var14 = var13.getPos();
         double var15 = var1.squaredDistanceTo(var14);
         if (var15 > var5 * var5) {
            var7 = BlockHitResult.createMissed(var14, Direction.getFacing(var10.x, var10.y, var10.z), BlockPos.ofFloored(var14));
         } else if (var15 < var8 || var7 == null) {
            var7 = var13;
         }
      }

      return (HitResult)var7;
   }

   public static HitResult raycastFromRotation(ClientPlayerEntity var0, Vec3d var1, float var2, float var3, double var4) {
      return qmg(var0, var1, var1.add(RotationMathUtil.getLookVector(var2, var3).multiply(var4)));
   }

   public static EntityHitResult raycastEntities(World var0, Entity var1, Vec3d var2, Vec3d var3, Box var4, Function<Entity, Box> var5) {
      float var6 = 0.3F;
      double var7 = Double.MAX_VALUE;
      Entity var9 = null;

      for (Entity var11 : var0.getOtherEntities(var1, var4, var1x -> var5.apply(var1x) != null)) {
         Box var12 = ((Box)var5.apply(var11)).expand(0.3F);
         Optional var13 = var12.raycast(var2, var3);
         if (var13.isPresent()) {
            double var14 = var2.squaredDistanceTo((Vec3d)var13.get());
            if (var14 < var7) {
               var9 = var11;
               var7 = var14;
            }
         }
      }

      return var9 == null ? null : new EntityHitResult(var9);
   }

   public static ItemStack setPotionContents(ItemStack var0, RegistryEntry<Potion> var1) {
      var0.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(var1));
      return var0;
   }

   public static float xqg(RegistryKey<Enchantment> var0, ItemStack var1) {
      for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var3 : var1.getEnchantments().getEnchantmentEntries()) {
         if (Objects.equals(((RegistryEntry)var3.getKey()).getKey().orElse(null), var0)) {
            return var3.getIntValue();
         }
      }

      return 0.0F;
   }

   public static List<StatusEffectInstance> tnw(ItemStack var0) {
      LinkedList var1 = new LinkedList();

      for (StatusEffectInstance var3 : ((PotionContentsComponent)var0.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT))
         .getEffects()) {
         var1.add(var3);
      }

      return var1;
   }

   public static List<ItemStack> getArmorAndOffHandStacks(ClientPlayerEntity var0) {
      return Arrays.asList(
         var0.getEquippedStack(EquipmentSlot.HEAD),
         var0.getEquippedStack(EquipmentSlot.CHEST),
         var0.getEquippedStack(EquipmentSlot.LEGS),
         var0.getEquippedStack(EquipmentSlot.FEET)
      );
   }

   public static List<ItemStack> lqy(PlayerEntity var0) {
      return Arrays.asList(var0.getMainHandStack(), var0.getOffHandStack());
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

   public static List<WorldChunk> getLoadedChunks() {
      MinecraftClient var0 = MinecraftClient.getInstance();
      int var1 = Math.max(2, var0.options.getClampedViewDistance()) + 3;
      int var2 = var1 * 2 + 1;
      ChunkPos var3 = var0.player.getChunkPos();
      IntPoint var4 = new IntPoint(var3.x - var1, var3.z - var1);
      IntPoint var5 = new IntPoint(var3.x + var1, var3.z + var1);
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
         .filter(var1x -> var0.world.isChunkLoaded(var1x.x, var1x.y))
         .map(var1x -> var0.world.getChunk(var1x.x, var1x.y))
         .filter(Objects::nonNull)
         .toList();
   }

   public static Set<RegistryEntry<Enchantment>> getEnchantments(ItemStack var0) {
      try {
         return EnchantmentHelper.getEnchantments(var0).getEnchantments();
      } catch (Throwable var2) {
         return Collections.emptySet();
      }
   }
}
