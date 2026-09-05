package me.phantom.event.render;

import me.phantom.event.EventBase;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.util.PerThreadObjectPool;

public class WorldRenderOverlayEvent extends EventBase {
   private static PerThreadObjectPool<WorldRenderOverlayEvent> pool = new PerThreadObjectPool(() -> new WorldRenderOverlayEvent());
   private GuiRenderContext context;

   public static WorldRenderOverlayEvent get(GuiRenderContext var0) {
      WorldRenderOverlayEvent var1 = (WorldRenderOverlayEvent)pool.get();
      var1.context = var0;
      return var1;
   }

   public GuiRenderContext getContext() {
      return this.context;
   }
}
