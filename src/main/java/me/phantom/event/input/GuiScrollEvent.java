package me.phantom.event.input;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.PerThreadObjectPool;

public class GuiScrollEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<GuiScrollEvent> pool = new PerThreadObjectPool(() -> new GuiScrollEvent());
   public double sr;

   public static GuiScrollEvent wf(double var0) {
      GuiScrollEvent var2 = (GuiScrollEvent)pool.get();
      var2.setCancelled(false);
      var2.sr = var0;
      return var2;
   }

   public double eiv() {
      return this.sr;
   }
}
