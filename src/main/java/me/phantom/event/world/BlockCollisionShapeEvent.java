package me.phantom.event.world;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCollisionShapeEvent extends EventBase {
   private static PerThreadObjectPool<BlockCollisionShapeEvent> pool = new PerThreadObjectPool(() -> new BlockCollisionShapeEvent());
   private BlockPos eff;
   private Block vb;
   public VoxelShape exd;

   public static BlockCollisionShapeEvent fz(BlockPos var0, Block var1, VoxelShape var2) {
      BlockCollisionShapeEvent var3 = (BlockCollisionShapeEvent)pool.get();
      var3.eff = var0;
      var3.vb = var1;
      var3.exd = var2;
      return var3;
   }

   public BlockPos fgg() {
      return this.eff;
   }

   public Block kp() {
      return this.vb;
   }

   public VoxelShape nss() {
      return this.exd;
   }

   public void yvk(VoxelShape var1) {
      this.exd = var1;
   }
}
