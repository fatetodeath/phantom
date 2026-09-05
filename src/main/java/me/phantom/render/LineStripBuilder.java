package me.phantom.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class LineStripBuilder implements AutoCloseable {
   private final Matrix4f matrix;
   private final VertexConsumer vertexConsumer;

   public static LineStripBuilder create(MatrixStack var0) {
      return new LineStripBuilder(var0.peek().getPositionMatrix());
   }

   public LineStripBuilder(Matrix4f var1) {
      this.matrix = var1;
      this.vertexConsumer = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.getLineStrip());
   }

   public void vertex(double var1, double var3, double var5, float var7, float var8, float var9, float var10) {
      this.vertexConsumer.vertex(this.matrix, (float)var1, (float)var3, (float)var5).color(var7, var8, var9, var10).normal(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void close() {
      MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers().drawCurrentLayer();
   }
}
