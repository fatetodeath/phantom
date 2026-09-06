package me.phantom.event.entity;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.phys.Vec3;

public class PositionEvent extends EventBase {
   public static PerThreadObjectPool<PositionEvent> pool = new PerThreadObjectPool(() -> new PositionEvent());
   public Vec3 pos;

   public static PositionEvent jv(Vec3 var0) {
      PositionEvent var1 = (PositionEvent)pool.get();
      var1.pos = var0;
      return var1;
   }

   public Vec3 cl() {
      return this.pos;
   }

   public void edk(Vec3 var1) {
      this.pos = var1;
   }
}
