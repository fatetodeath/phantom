package me.phantom.util;

import me.phantom.event.EventBase;

import com.google.common.base.Supplier;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class PerThreadObjectPool<T> {
   private final Long2ObjectMap<T> poolMap = new Long2ObjectOpenHashMap();
   private final Supplier<T> factory;

   public PerThreadObjectPool(Supplier<T> var1) {
      this.factory = var1;
   }

   public synchronized T get() {
      return this.poolMap.computeIfAbsent(Thread.currentThread().getId(), var1 -> this.factory.get());
   }
}
