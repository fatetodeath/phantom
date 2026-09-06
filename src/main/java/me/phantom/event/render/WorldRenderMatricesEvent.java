package me.phantom.event.render;

import me.phantom.util.PerThreadObjectPool;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;

public class WorldRenderMatricesEvent extends RenderEventBase {
   private static PerThreadObjectPool<WorldRenderMatricesEvent> pool = new PerThreadObjectPool(() -> new WorldRenderMatricesEvent());
   private Vec3 hn;
   public PoseStack matrixStack;
   public float tickDelta;
   public float worldTickDelta;

   public static WorldRenderMatricesEvent oki(PoseStack var0, float var1, float var2, Vec3 var3) {
      WorldRenderMatricesEvent var4 = (WorldRenderMatricesEvent)pool.get();
      var4.matrixStack = var0;
      var4.tickDelta = var1;
      var4.worldTickDelta = var2;
      var4.hn = var3;
      return var4;
   }

   public PoseStack getMatrices() {
      return this.matrixStack;
   }

   public float tickDelta() {
      return this.tickDelta;
   }

   public float wt() {
      return this.worldTickDelta;
   }

   public Vec3 yq() {
      return this.hn;
   }
}
