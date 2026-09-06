package me.phantom.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;

public class LineStripBuilder implements AutoCloseable {
   private final Matrix4f matrix;
   private final VertexConsumer vertexConsumer;

   public static LineStripBuilder create(PoseStack var0) {
      return new LineStripBuilder(var0.last().pose());
   }

   public LineStripBuilder(Matrix4f var1) {
      this.matrix = var1;
      this.vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderTypes.LINES);
   }

   public void vertex(double var1, double var3, double var5, float var7, float var8, float var9, float var10) {
       this.vertexConsumer.addVertex(this.matrix, (float)var1, (float)var3, (float)var5).setColor(var7, var8, var9, var10).setLineWidth(1.0F).setNormal(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void close() {
      Minecraft.getInstance().renderBuffers().bufferSource().endLastBatch();
   }
}
