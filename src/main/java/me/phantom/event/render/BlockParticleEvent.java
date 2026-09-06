package me.phantom.event.render;

import me.phantom.event.EventBase;
import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.level.block.Block;

public class BlockParticleEvent extends EventBase {
   public static PerThreadObjectPool<BlockParticleEvent> pool = new PerThreadObjectPool(() -> new BlockParticleEvent());
   public Block yg;

   public static BlockParticleEvent xeu() {
      BlockParticleEvent var0 = (BlockParticleEvent)pool.get();
      var0.yg = null;
      return var0;
   }

   public Block kxf() {
      return this.yg;
   }

   public void kh(Block var1) {
      this.yg = var1;
   }
}
