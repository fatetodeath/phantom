package me.phantom.event.render;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class WorldRenderMatricesEvent extends RenderEventBase {
   private static PerThreadObjectPool<WorldRenderMatricesEvent> pool = new PerThreadObjectPool(() -> new WorldRenderMatricesEvent());
   private Vec3d hn;
   public MatrixStack matrixStack;
   public float tickDelta;
   public float worldTickDelta;

   public static WorldRenderMatricesEvent oki(MatrixStack var0, float var1, float var2, Vec3d var3) {
      WorldRenderMatricesEvent var4 = (WorldRenderMatricesEvent)pool.get();
      var4.matrixStack = var0;
      var4.tickDelta = var1;
      var4.worldTickDelta = var2;
      var4.hn = var3;
      return var4;
   }

   public MatrixStack getMatrices() {
      return this.matrixStack;
   }

   public float tickDelta() {
      return this.tickDelta;
   }

   public float wt() {
      return this.worldTickDelta;
   }

   public Vec3d yq() {
      return this.hn;
   }
}
