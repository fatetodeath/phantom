package me.phantom.event.misc;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

public class SignalEvent extends EventBase {
   private static PerThreadObjectPool<SignalEvent> pool = new PerThreadObjectPool(() -> new SignalEvent());

   public static SignalEvent zr() {
      return (SignalEvent)pool.get();
   }
}
