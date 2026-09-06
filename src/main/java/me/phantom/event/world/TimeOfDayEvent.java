package me.phantom.event.world;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

public class TimeOfDayEvent extends EventBase {
   private static PerThreadObjectPool<TimeOfDayEvent> pool = new PerThreadObjectPool(() -> new TimeOfDayEvent());
   private long time;

   public static TimeOfDayEvent mj(long var0) {
      TimeOfDayEvent var2 = (TimeOfDayEvent)pool.get();
      var2.time = var0;
      return var2;
   }

   public long ou() {
      return this.time;
   }

   public void vqq(long var1) {
      this.time = var1;
   }
}
