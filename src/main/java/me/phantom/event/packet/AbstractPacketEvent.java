package me.phantom.event.packet;

import me.phantom.event.EventBase;

public abstract class AbstractPacketEvent extends EventBase {
   private boolean cancelled;

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }

   public boolean stopsWhenCancelled() {
      return true;
   }
}
