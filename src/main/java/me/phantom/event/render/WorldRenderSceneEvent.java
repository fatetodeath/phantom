package me.phantom.event.render;

import me.phantom.event.EventBase;
import me.phantom.util.PerThreadObjectPool;

import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;

public class WorldRenderSceneEvent extends EventBase {
   private static PerThreadObjectPool<WorldRenderSceneEvent> pool = new PerThreadObjectPool(() -> new WorldRenderSceneEvent());
   private PoseStack matrixStack;
   private BufferSource vertexConsumers;
   private Camera camera;
   public DeltaTracker tickCounter;
   public List<Entity> entities;

   public static WorldRenderSceneEvent get(PoseStack var0, BufferSource var1, Camera var2, DeltaTracker var3, List<Entity> var4) {
      WorldRenderSceneEvent var5 = (WorldRenderSceneEvent)pool.get();
      var5.matrixStack = var0;
      var5.vertexConsumers = var1;
      var5.camera = var2;
      var5.tickCounter = var3;
      var5.entities = var4;
      return var5;
   }

   public PoseStack getMatrices() {
      return this.matrixStack;
   }

   public BufferSource getVertexConsumers() {
      return this.vertexConsumers;
   }

   public Camera getCamera() {
      return this.camera;
   }

   public DeltaTracker getTickCounter() {
      return this.tickCounter;
   }

   public List<Entity> getEntities() {
      return this.entities;
   }
}
