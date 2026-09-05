package me.phantom.event.entity;

import me.phantom.event.EventBase;

import me.phantom.event.packet.PacketType;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class PlayerVelocityEvent extends EventBase {
   public static PerThreadObjectPool<PlayerVelocityEvent> pool = new PerThreadObjectPool(() -> new PlayerVelocityEvent());
   public Entity kg;
   public Vec3d nai;
   private PacketType jd;

   public static PlayerVelocityEvent jfp(Entity var0, Vec3d var1, PacketType var2) {
      PlayerVelocityEvent var3 = (PlayerVelocityEvent)pool.get();
      var3.kg = var0;
      var3.nai = var1;
      var3.jd = var2;
      return var3;
   }

   public Entity tg() {
      return this.kg;
   }

   public void rpb(Vec3d var1) {
      this.nai = var1;
   }

   public Vec3d waw() {
      return this.nai;
   }

   public PacketType tl() {
      return this.jd;
   }
}
