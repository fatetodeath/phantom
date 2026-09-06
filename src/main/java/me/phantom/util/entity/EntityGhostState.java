package me.phantom.util.entity;

import me.phantom.util.GameInteractionUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityGhostState {
   public final Entity kg;
   public Vec3 ti;
   public Vec3 ref;
   public Vec3 ssi;
   public float kr;
   public float hln;

   public EntityGhostState(Entity var1) {
      this.kg = var1;
      this.ti = var1.position();
      this.ref = var1.position();
      this.ssi = new Vec3(var1.xo, var1.yo, var1.zo);
   }

   public void zd(double var1, double var3, double var5) {
      this.vi(this.ti.x + var1, this.ref.y + var3, this.ref.z + var5);
   }

   public void vi(double var1, double var3, double var5) {
      this.ti = new Vec3(var1, var3, var5);
   }

   public void npr(float var1) {
      this.hln = var1;
   }

   public AABB epx(float var1) {
      return GameInteractionUtil.createBox(this.ssi.lerp(this.ref, var1), this.kg.getBbWidth(), this.kg.getBbHeight());
   }
}
