package me.phantom.event.render;

import me.phantom.event.EventBase;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.util.PerThreadObjectPool;

public class UiRenderEvent extends EventBase {
   public static PerThreadObjectPool<UiRenderEvent> pool = new PerThreadObjectPool(() -> new UiRenderEvent());
   public GuiRenderContext context;

   public static UiRenderEvent get(GuiRenderContext var0) {
      UiRenderEvent var1 = (UiRenderEvent)pool.get();
      var1.context = var0;
      return var1;
   }

   public GuiRenderContext getContext() {
      return this.context;
   }
}
