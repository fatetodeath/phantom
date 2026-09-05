package me.phantom.event.render;

import me.phantom.event.packet.AbstractPacketEvent;
import me.phantom.util.PerThreadObjectPool;

public class FramebufferBindEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<FramebufferBindEvent> pool = new PerThreadObjectPool(() -> new FramebufferBindEvent());
   private int gsd;

   public static FramebufferBindEvent ixo(int var0) {
      FramebufferBindEvent var1 = (FramebufferBindEvent)pool.get();
      var1.setCancelled(false);
      var1.gsd = var0;
      return var1;
   }

   public int nbt() {
      return this.gsd;
   }
}
