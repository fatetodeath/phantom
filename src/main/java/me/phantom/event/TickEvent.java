package me.phantom.event;

import me.phantom.util.PerThreadObjectPool;

public class TickEvent extends EventBase {
   public static PerThreadObjectPool<TickEvent> pool = new PerThreadObjectPool(() -> new TickEvent());

   public static TickEvent get() {
      return (TickEvent)pool.get();
   }
}
