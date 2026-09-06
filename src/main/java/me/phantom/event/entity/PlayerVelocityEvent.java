package me.phantom.event.entity;

import me.phantom.event.EventBase;

import me.phantom.event.packet.PacketType;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PlayerVelocityEvent extends EventBase {
   public static PerThreadObjectPool<PlayerVelocityEvent> pool = new PerThreadObjectPool(() -> new PlayerVelocityEvent());
   public Entity kg;
   public Vec3 nai;
   private PacketType jd;

   public static PlayerVelocityEvent jfp(Entity var0, Vec3 var1, PacketType var2) {
      PlayerVelocityEvent var3 = (PlayerVelocityEvent)pool.get();
      var3.kg = var0;
      var3.nai = var1;
      var3.jd = var2;
      return var3;
   }

   public Entity tg() {
      return this.kg;
   }

   public void rpb(Vec3 var1) {
      this.nai = var1;
   }

   public Vec3 waw() {
      return this.nai;
   }

   public PacketType tl() {
      return this.jd;
   }
}
