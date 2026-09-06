package me.phantom.event.input;

import me.phantom.event.EventBase;

import me.phantom.util.mc.KeyBindingTimesPressedAccessor;
import me.phantom.util.PerThreadObjectPool;

public class KeyStateEvent extends EventBase {
   private static PerThreadObjectPool<KeyStateEvent> pool = new PerThreadObjectPool(() -> new KeyStateEvent());
   public KeyBindingTimesPressedAccessor gw;
   private boolean zq;

   public static KeyStateEvent kt(KeyBindingTimesPressedAccessor var0, boolean var1) {
      KeyStateEvent var2 = (KeyStateEvent)pool.get();
      var2.gw = var0;
      var2.zq = var1;
      return var2;
   }

   public KeyBindingTimesPressedAccessor by() {
      return this.gw;
   }

   public boolean re() {
      return this.zq;
   }

   public void ywz(boolean var1) {
      this.zq = var1;
   }
}
