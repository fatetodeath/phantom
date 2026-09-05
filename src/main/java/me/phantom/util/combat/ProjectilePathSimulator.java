package me.phantom.util.combat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class ProjectilePathSimulator {
   private final World eba;
   public final ProjectileType tmj;
   public final List<VoxelShape> lcc = new ArrayList<>();
   private Entity dxm;
   private Vec3d ref;
   private Vec3d va;
   private Vec3d jvj;
   private Vec3d xia;
   public boolean npo;

   public ProjectilePathSimulator(World var1, Entity var2, Vec3d var3, Vec3d var4, ProjectileType var5) {
      this.eba = var1;
      this.dxm = var2;
      this.ref = var3;
      this.va = var3;
      this.jvj = var4;
      this.xia = var4;
      this.tmj = var5;
   }

   public void tkm() {
      if (!this.npo) {
         this.va = this.ref;
         if (!(this.ref.y < -64.0) && !this.jza(this.xia)) {
            this.ref = this.ref.add(this.xia);
            this.xia = this.tmj.kyx(this.xia, this.jvj);
         } else {
            this.npo = true;
         }
      }
   }

   public boolean jza(Vec3d var1) {
      try {
         RaycastContext var2 = this.dxm == null
            ? new RaycastContext(this.ref, this.ref.add(var1), ShapeType.COLLIDER, FluidHandling.NONE, (ShapeContext)null)
            : new RaycastContext(this.ref, this.ref.add(var1), ShapeType.COLLIDER, FluidHandling.NONE, this.dxm);
         BlockHitResult var3 = this.eba.raycast(var2);
         if (var3.getType() != Type.MISS) {
            this.ref = var3.getPos();
            return true;
         } else {
            return false;
         }
      } catch (Throwable var4) {
         return true;
      }
   }

   public Box sq() {
      return new Box(
         this.ref.x - this.tmj.hed / 2.0F,
         this.ref.y,
         this.ref.z - this.tmj.hed / 2.0F,
         this.ref.x + this.tmj.hed / 2.0F,
         this.ref.y + this.tmj.hwfy,
         this.ref.z + this.tmj.hed / 2.0F
      );
   }

   public Vec3d udz() {
      return this.xia;
   }

   public Vec3d imp() {
      return this.va;
   }

   public Vec3d swx() {
      return this.ref;
   }

   public List<VoxelShape> ssk() {
      return this.lcc;
   }

   public boolean pln() {
      return this.npo;
   }

   public BlockPos akm() {
      return BlockPos.ofFloored(this.ref.x + this.xia.x, this.ref.y + this.xia.y, this.ref.z + this.xia.z);
   }

}
