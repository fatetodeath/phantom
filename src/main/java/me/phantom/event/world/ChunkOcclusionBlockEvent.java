package me.phantom.event.world;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.util.math.BlockPos;

public class ChunkOcclusionBlockEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<ChunkOcclusionBlockEvent> pool = new PerThreadObjectPool(() -> new ChunkOcclusionBlockEvent());
   public BlockPos eff;

   public static ChunkOcclusionBlockEvent py(BlockPos var0) {
      ChunkOcclusionBlockEvent var1 = (ChunkOcclusionBlockEvent)pool.get();
      var1.setCancelled(false);
      var1.eff = var0;
      return var1;
   }

   public BlockPos fgg() {
      return this.eff;
   }
}
