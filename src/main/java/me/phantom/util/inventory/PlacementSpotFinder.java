package me.phantom.util.inventory;

import me.phantom.module.build.AutoPlaceModule;
import me.phantom.render.Render3DUtil;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.WorldEntityUtil;
import me.phantom.util.math.RotationMathUtil;

import java.awt.Color;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class PlacementSpotFinder {
   public static final int CHECKS_PER_EDGE = 10;
   public final AutoPlaceModule gv;
   public final ClientPlayerEntity player;
   private final Vec3d eyePos;
   public final Vec3d lookHitPos;
   public Vec3d bestSpot;
   private double bestDistance;
   private MatrixStack matrixStack;
   public Box searchBox;

   public PlacementSpotFinder(AutoPlaceModule var1, ClientPlayerEntity var2, Entity var3, MatrixStack var4) {
      this.gv = var1;
      this.player = var2;
      this.eyePos = var2.getEyePos();
      Vec3d var5 = var3.getPos();
      float var6 = var3.getWidth();
      this.searchBox = new Box(var5.x - var6 / 2.0F, var5.y - 0.5, var5.z - var6 / 2.0F, var5.x + var6 / 2.0F, var5.y, var5.z + var6 / 2.0F);
      Vec3d var7 = this.eyePos.add(RotationMathUtil.getLookVector(this.player.getYaw(), this.player.getPitch()).multiply(((Float)var1.em.getValue()).floatValue()));
      BlockHitResult var8 = this.player.getWorld().raycast(new RaycastContext(this.eyePos, var7, ShapeType.COLLIDER, FluidHandling.ANY, this.player));
      if (var8 != null && var8.getType() == Type.BLOCK) {
         var7 = var8.getPos();
      }

      this.lookHitPos = var7;
      this.matrixStack = var4;
   }

   public void checkSpot(Direction var1, Vec3d var2) {
      ClientWorld var3 = MinecraftClient.getInstance().world;
      BlockPos var4 = new BlockPos((int)Math.floor(var2.x), (int)Math.floor(var2.y), (int)Math.floor(var2.z));
      BlockState var5 = var3.getBlockState(var4);
      if (var5.isSolidBlock(var3, var4)) {
         double var6 = 0.02500000074505806;
         Box var8 = new Box(
            var2.x - 0.02500000074505806,
            var2.y - 0.02500000074505806,
            var2.z - 0.02500000074505806,
            var2.x + 0.02500000074505806,
            var2.y + 0.02500000074505806,
            var2.z + 0.02500000074505806
         );
         if (!(var2.squaredDistanceTo(this.player.getPos()) <= 1.0)) {
            for (Entity var10 : WorldEntityUtil.getEntities()) {
               if (var10.getBoundingBox().expand(0.0, 0.5, 0.0).intersects(var8)) {
                  return;
               }
            }

            Vec3i var15 = var1.getVector();
            BlockPos var16 = BlockPos.ofFloored(var8.getCenter().add(var15.getX() * 0.1F, var15.getY() * 0.1F, var15.getZ() * 0.1F));
            BlockState var11 = var3.getBlockState(var16);
            if (var11.getBlock() == Blocks.AIR) {
               double var12 = this.lookHitPos.squaredDistanceTo(var2);
               if (var12 < 16.0 && (this.bestSpot == null || this.bestDistance > var12)) {
                  HitResult var14 = GameInteractionUtil.qmg(
                     this.player, this.eyePos, this.eyePos.add(var2.subtract(this.eyePos).normalize().multiply(((Float)this.gv.em.getValue()).floatValue()))
                  );
                  if (var14 instanceof BlockHitResult && var14.getPos().squaredDistanceTo(var2) <= 0.010000000000000002) {
                     this.bestSpot = var2;
                     this.bestDistance = var12;
                  }
               }

               if (this.matrixStack != null) {
                  Render3DUtil.ehw(this.matrixStack, VoxelShapes.cuboid(var8), new Color(0, 255, 0), 0.7F);
               }
            }
         }
      }
   }

   public Vec3d findBestSpot() {
      for (VoxelShape var2 : this.player.clientWorld.getBlockCollisions(this.player, this.searchBox)) {
         if (var2.getBoundingBoxes().size() == 1) {
            Box var3 = (Box)var2.getBoundingBoxes().get(0);
            if (var3.getLengthX() == 1.0 && var3.getLengthY() == 1.0 && var3.getLengthZ() == 1.0) {
               float var4 = 0.01F;

               for (int var5 = 0; var5 <= 10; var5++) {
                  float var6 = 0.2F;
                  float var7 = var5 / 10.0F * (1.0F - var6 * 2.0F) + var6;

                  for (int var8 = 0; var8 < 2; var8++) {
                     this.checkSpot(Direction.UP, new Vec3d(var3.minX + var7, var3.maxY - var4, var3.minZ + var6));
                     this.checkSpot(Direction.UP, new Vec3d(var3.minX + var7, var3.maxY - var4, var3.maxZ - var6));
                     this.checkSpot(Direction.UP, new Vec3d(var3.minX + var6, var3.maxY - var4, var3.minZ + var7));
                     this.checkSpot(Direction.UP, new Vec3d(var3.maxX - var6, var3.maxY - var4, var3.minZ + var7));
                     this.checkSpot(Direction.EAST, new Vec3d(var3.minX - var4, var3.maxY + var7, var3.minZ + var6));
                     this.checkSpot(Direction.EAST, new Vec3d(var3.minX - var4, var3.maxY + var7, var3.maxZ - var6));
                     this.checkSpot(Direction.EAST, new Vec3d(var3.minX - var4, var3.maxY + var6, var3.minZ + var7));
                     this.checkSpot(Direction.EAST, new Vec3d(var3.minX - var4, var3.maxY - var6 + 1.0, var3.minZ + var7));
                     this.checkSpot(Direction.WEST, new Vec3d(var3.maxX + var4, var3.maxY + var7, var3.minZ + var6));
                     this.checkSpot(Direction.WEST, new Vec3d(var3.maxX + var4, var3.maxY + var7, var3.maxZ - var6));
                     this.checkSpot(Direction.WEST, new Vec3d(var3.maxX + var4, var3.maxY + var6, var3.minZ + var7));
                     this.checkSpot(Direction.WEST, new Vec3d(var3.maxX + var4, var3.maxY - var6 + 1.0, var3.minZ + var7));
                     this.checkSpot(Direction.SOUTH, new Vec3d(var3.minX + var6, var3.maxY + var7, var3.minZ - var4));
                     this.checkSpot(Direction.SOUTH, new Vec3d(var3.maxX - var6, var3.maxY + var7, var3.minZ - var4));
                     this.checkSpot(Direction.SOUTH, new Vec3d(var3.minX + var7, var3.maxY + var6, var3.minZ - var4));
                     this.checkSpot(Direction.SOUTH, new Vec3d(var3.minX + var7, var3.maxY - var6 + 1.0, var3.minZ - var4));
                     this.checkSpot(Direction.NORTH, new Vec3d(var3.minX + var6, var3.maxY + var7, var3.maxZ + var4));
                     this.checkSpot(Direction.NORTH, new Vec3d(var3.maxX - var6, var3.maxY + var7, var3.maxZ + var4));
                     this.checkSpot(Direction.NORTH, new Vec3d(var3.minX + var7, var3.maxY + var6, var3.maxZ + var4));
                     this.checkSpot(Direction.NORTH, new Vec3d(var3.minX + var7, var3.maxY - var6 + 1.0, var3.maxZ + var4));
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
