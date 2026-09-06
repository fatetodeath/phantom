package me.phantom.util;

public class ScheduledTask {
   public final Runnable runnable;
   public int ticksRemaining;

   public ScheduledTask(Runnable var1, int var2) {
      this.runnable = var1;
      this.ticksRemaining = var2;
   }
}
