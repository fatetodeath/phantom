package me.phantom.event.world;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.level.block.state.BlockState;

public class ShouldDrawBlockSideEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<ShouldDrawBlockSideEvent> pool = new PerThreadObjectPool(() -> new ShouldDrawBlockSideEvent());
   private BlockState vo;
   private boolean jgp;

   public static ShouldDrawBlockSideEvent td(BlockState var0) {
      ShouldDrawBlockSideEvent var1 = (ShouldDrawBlockSideEvent)pool.get();
      var1.setCancelled(false);
      var1.vo = var0;
      return var1;
   }

   public BlockState tf() {
      return this.vo;
   }

   public boolean dai() {
      return this.jgp;
   }

   public void rh(boolean var1) {
      this.jgp = var1;
   }

}
