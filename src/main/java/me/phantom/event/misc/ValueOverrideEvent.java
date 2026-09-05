package me.phantom.event.misc;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

public class ValueOverrideEvent extends EventBase {
   public static PerThreadObjectPool<ValueOverrideEvent> pool = new PerThreadObjectPool(() -> new ValueOverrideEvent());
   public Object value;

   public static ValueOverrideEvent fge(Object var0) {
      ValueOverrideEvent var1 = (ValueOverrideEvent)pool.get();
      var1.value = var0;
      return var1;
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object var1) {
      this.value = var1;
   }

}
