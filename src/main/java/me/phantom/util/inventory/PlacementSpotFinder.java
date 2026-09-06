package me.phantom.util.inventory;

import me.phantom.module.build.AutoPlaceModule;
import me.phantom.render.Render3DUtil;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.WorldEntityUtil;
import me.phantom.util.math.RotationMathUtil;

import java.awt.Color;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.ClipContext.Block;

public class PlacementSpotFinder {
   public static final int CHECKS_PER_EDGE = 10;
   public final AutoPlaceModule gv;
   public final LocalPlayer player;
   private final Vec3 eyePos;
   public final Vec3 lookHitPos;
   public Vec3 bestSpot;
   private double bestDistance;
   private PoseStack matrixStack;
   public AABB searchBox;

   public PlacementSpotFinder(AutoPlaceModule var1, LocalPlayer var2, Entity var3, PoseStack var4) {
      this.gv = var1;
      this.player = var2;
      this.eyePos = var2.getEyePosition();
      Vec3 var5 = var3.position();
      float var6 = var3.getBbWidth();
      this.searchBox = new AABB(var5.x - var6 / 2.0F, var5.y - 0.5, var5.z - var6 / 2.0F, var5.x + var6 / 2.0F, var5.y, var5.z + var6 / 2.0F);
      Vec3 var7 = this.eyePos.add(RotationMathUtil.getLookVector(this.player.getYRot(), this.player.getXRot()).scale(((Float)var1.em.getValue()).floatValue()));
      BlockHitResult var8 = this.player.level().clip(new ClipContext(this.eyePos, var7, Block.COLLIDER, Fluid.ANY, this.player));
      if (var8 != null && var8.getType() == Type.BLOCK) {
         var7 = var8.getLocation();
      }

      this.lookHitPos = var7;
      this.matrixStack = var4;
   }

   public void checkSpot(Direction var1, Vec3 var2) {
      ClientLevel var3 = Minecraft.getInstance().level;
      BlockPos var4 = new BlockPos((int)Math.floor(var2.x), (int)Math.floor(var2.y), (int)Math.floor(var2.z));
      BlockState var5 = var3.getBlockState(var4);
      if (var5.isRedstoneConductor(var3, var4)) {
         double var6 = 0.02500000074505806;
         AABB var8 = new AABB(
            var2.x - 0.02500000074505806,
            var2.y - 0.02500000074505806,
            var2.z - 0.02500000074505806,
            var2.x + 0.02500000074505806,
            var2.y + 0.02500000074505806,
            var2.z + 0.02500000074505806
         );
         if (!(var2.distanceToSqr(this.player.position()) <= 1.0)) {
            for (Entity var10 : WorldEntityUtil.getEntities()) {
               if (var10.getBoundingBox().inflate(0.0, 0.5, 0.0).intersects(var8)) {
                  return;
               }
            }

            Vec3i var15 = var1.getUnitVec3i();
            BlockPos var16 = BlockPos.containing(var8.getCenter().add(var15.getX() * 0.1F, var15.getY() * 0.1F, var15.getZ() * 0.1F));
            BlockState var11 = var3.getBlockState(var16);
            if (var11.getBlock() == Blocks.AIR) {
               double var12 = this.lookHitPos.distanceToSqr(var2);
               if (var12 < 16.0 && (this.bestSpot == null || this.bestDistance > var12)) {
                  HitResult var14 = GameInteractionUtil.qmg(
                     this.player, this.eyePos, this.eyePos.add(var2.subtract(this.eyePos).normalize().scale(((Float)this.gv.em.getValue()).floatValue()))
                  );
                  if (var14 instanceof BlockHitResult && var14.getLocation().distanceToSqr(var2) <= 0.010000000000000002) {
                     this.bestSpot = var2;
                     this.bestDistance = var12;
                  }
               }

               if (this.matrixStack != null) {
                  Render3DUtil.ehw(this.matrixStack, Shapes.create(var8), new Color(0, 255, 0), 0.7F);
               }
            }
         }
      }
   }

   public Vec3 findBestSpot() {
      for (VoxelShape var2 : this.player.level().getBlockCollisions(this.player, this.searchBox)) {
         if (var2.toAabbs().size() == 1) {
            AABB var3 = (AABB)var2.toAabbs().get(0);
            if (var3.getXsize() == 1.0 && var3.getYsize() == 1.0 && var3.getZsize() == 1.0) {
               float var4 = 0.01F;

               for (int var5 = 0; var5 <= 10; var5++) {
                  float var6 = 0.2F;
                  float var7 = var5 / 10.0F * (1.0F - var6 * 2.0F) + var6;

                  for (int var8 = 0; var8 < 2; var8++) {
                     this.checkSpot(Direction.UP, new Vec3(var3.minX + var7, var3.maxY - var4, var3.minZ + var6));
                     this.checkSpot(Direction.UP, new Vec3(var3.minX + var7, var3.maxY - var4, var3.maxZ - var6));
                     this.checkSpot(Direction.UP, new Vec3(var3.minX + var6, var3.maxY - var4, var3.minZ + var7));
                     this.checkSpot(Direction.UP, new Vec3(var3.maxX - var6, var3.maxY - var4, var3.minZ + var7));
                     this.checkSpot(Direction.EAST, new Vec3(var3.minX - var4, var3.maxY + var7, var3.minZ + var6));
                     this.checkSpot(Direction.EAST, new Vec3(var3.minX - var4, var3.maxY + var7, var3.maxZ - var6));
                     this.checkSpot(Direction.EAST, new Vec3(var3.minX - var4, var3.maxY + var6, var3.minZ + var7));
                     this.checkSpot(Direction.EAST, new Vec3(var3.minX - var4, var3.maxY - var6 + 1.0, var3.minZ + var7));
                     this.checkSpot(Direction.WEST, new Vec3(var3.maxX + var4, var3.maxY + var7, var3.minZ + var6));
                     this.checkSpot(Direction.WEST, new Vec3(var3.maxX + var4, var3.maxY + var7, var3.maxZ - var6));
                     this.checkSpot(Direction.WEST, new Vec3(var3.maxX + var4, var3.maxY + var6, var3.minZ + var7));
                     this.checkSpot(Direction.WEST, new Vec3(var3.maxX + var4, var3.maxY - var6 + 1.0, var3.minZ + var7));
                     this.checkSpot(Direction.SOUTH, new Vec3(var3.minX + var6, var3.maxY + var7, var3.minZ - var4));
                     this.checkSpot(Direction.SOUTH, new Vec3(var3.maxX - var6, var3.maxY + var7, var3.minZ - var4));
                     this.checkSpot(Direction.SOUTH, new Vec3(var3.minX + var7, var3.maxY + var6, var3.minZ - var4));
                     this.checkSpot(Direction.SOUTH, new Vec3(var3.minX + var7, var3.maxY - var6 + 1.0, var3.minZ - var4));
                     this.checkSpot(Direction.NORTH, new Vec3(var3.minX + var6, var3.maxY + var7, var3.maxZ + var4));
                     this.checkSpot(Direction.NORTH, new Vec3(var3.maxX - var6, var3.maxY + var7, var3.maxZ + var4));
                     this.checkSpot(Direction.NORTH, new Vec3(var3.minX + var7, var3.maxY + var6, var3.maxZ + var4));
                     this.checkSpot(Direction.NORTH, new Vec3(var3.minX + var7, var3.maxY - var6 + 1.0, var3.maxZ + var4));
                     var6 = 0.05F;
                     var7 = var5 / 10.0F * (1.0F - var6 * 2.0F) + var6;
                  }
               }
            }
         }
      }

      return this.bestSpot;
   }
}
