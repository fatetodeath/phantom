package me.phantom.event.render;

import me.phantom.event.packet.AbstractPacketEvent;
import me.phantom.util.PerThreadObjectPool;

public class ScoreboardSidebarRenderEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<ScoreboardSidebarRenderEvent> pool = new PerThreadObjectPool(() -> new ScoreboardSidebarRenderEvent());

   public static ScoreboardSidebarRenderEvent nq() {
      ScoreboardSidebarRenderEvent var0 = (ScoreboardSidebarRenderEvent)pool.get();
      var0.setCancelled(false);
      return var0;
   }
}
