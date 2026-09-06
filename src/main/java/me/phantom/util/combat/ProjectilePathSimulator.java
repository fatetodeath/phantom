package me.phantom.util.combat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.ClipContext.Block;

public class ProjectilePathSimulator {
   private final Level eba;
   public final ProjectileType tmj;
   public final List<VoxelShape> lcc = new ArrayList<>();
   private Entity dxm;
   private Vec3 ref;
   private Vec3 va;
   private Vec3 jvj;
   private Vec3 xia;
   public boolean npo;

   public ProjectilePathSimulator(Level var1, Entity var2, Vec3 var3, Vec3 var4, ProjectileType var5) {
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

   public boolean jza(Vec3 var1) {
      try {
         ClipContext var2 = this.dxm == null
            ? new ClipContext(this.ref, this.ref.add(var1), Block.COLLIDER, Fluid.NONE, (CollisionContext)null)
            : new ClipContext(this.ref, this.ref.add(var1), Block.COLLIDER, Fluid.NONE, this.dxm);
         BlockHitResult var3 = this.eba.clip(var2);
         if (var3.getType() != Type.MISS) {
            this.ref = var3.getLocation();
            return true;
         } else {
            return false;
         }
      } catch (Throwable var4) {
         return true;
      }
   }

   public AABB sq() {
      return new AABB(
         this.ref.x - this.tmj.hed / 2.0F,
         this.ref.y,
         this.ref.z - this.tmj.hed / 2.0F,
         this.ref.x + this.tmj.hed / 2.0F,
         this.ref.y + this.tmj.hwfy,
         this.ref.z + this.tmj.hed / 2.0F
      );
   }

   public Vec3 udz() {
      return this.xia;
   }

   public Vec3 imp() {
      return this.va;
   }

   public Vec3 swx() {
      return this.ref;
   }

   public List<VoxelShape> ssk() {
      return this.lcc;
   }

   public boolean pln() {
      return this.npo;
   }

   public BlockPos akm() {
      return BlockPos.containing(this.ref.x + this.xia.x, this.ref.y + this.xia.y, this.ref.z + this.xia.z);
   }

}
