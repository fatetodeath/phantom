package me.phantom.util.entity;

import me.phantom.util.GameInteractionUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class EntityGhostState {
   public final Entity kg;
   public Vec3d ti;
   public Vec3d ref;
   public Vec3d ssi;
   public float kr;
   public float hln;

   public EntityGhostState(Entity var1) {
      this.kg = var1;
      this.ti = var1.getPos();
      this.ref = var1.getPos();
      this.ssi = new Vec3d(var1.lastX, var1.lastY, var1.lastZ);
   }

   public void zd(double var1, double var3, double var5) {
      this.vi(this.ti.x + var1, this.ref.y + var3, this.ref.z + var5);
   }

   public void vi(double var1, double var3, double var5) {
      this.ti = new Vec3d(var1, var3, var5);
   }

   public void npr(float var1) {
      this.hln = var1;
   }

   public Box epx(float var1) {
      return GameInteractionUtil.createBox(this.ssi.lerp(this.ref, var1), this.kg.getWidth(), this.kg.getHeight());
   }
}
