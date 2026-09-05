package me.phantom.event.entity;

import me.phantom.event.EventBase;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.util.math.Vec3d;

public class PositionEvent extends EventBase {
   public static PerThreadObjectPool<PositionEvent> pool = new PerThreadObjectPool(() -> new PositionEvent());
   public Vec3d pos;

   public static PositionEvent jv(Vec3d var0) {
      PositionEvent var1 = (PositionEvent)pool.get();
      var1.pos = var0;
      return var1;
   }

   public Vec3d cl() {
      return this.pos;
   }

   public void edk(Vec3d var1) {
      this.pos = var1;
   }
}
