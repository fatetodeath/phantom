package me.phantom.event.render;

import me.phantom.event.EventBase;
import me.phantom.util.PerThreadObjectPool;

import java.util.List;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class WorldRenderSceneEvent extends EventBase {
   private static PerThreadObjectPool<WorldRenderSceneEvent> pool = new PerThreadObjectPool(() -> new WorldRenderSceneEvent());
   private MatrixStack matrixStack;
   private Immediate vertexConsumers;
   private Camera camera;
   public RenderTickCounter tickCounter;
   public List<Entity> entities;

   public static WorldRenderSceneEvent get(MatrixStack var0, Immediate var1, Camera var2, RenderTickCounter var3, List<Entity> var4) {
      WorldRenderSceneEvent var5 = (WorldRenderSceneEvent)pool.get();
      var5.matrixStack = var0;
      var5.vertexConsumers = var1;
      var5.camera = var2;
      var5.tickCounter = var3;
      var5.entities = var4;
      return var5;
   }

   public MatrixStack getMatrices() {
      return this.matrixStack;
   }

   public Immediate getVertexConsumers() {
      return this.vertexConsumers;
   }

   public Camera getCamera() {
      return this.camera;
   }

   public RenderTickCounter getTickCounter() {
      return this.tickCounter;
   }

   public List<Entity> getEntities() {
      return this.entities;
   }
}
