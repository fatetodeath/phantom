package me.phantom.render;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.FloatPoint2D;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.TextureFormat;
import java.awt.Color;
import me.phantom.mixin.render.GameRendererProjectionAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class Render3DUtil {
   public static float[][] ktp = new float[6][4];
   private static float[] agg = new float[16];
   public static float[] akk = new float[16];
   public static float[] mpv = new float[16];
   public static boolean ikib = true;

   public static boolean worldToScreen(Vec3 var0, boolean var1, FloatPoint2D var2) {
      return nrq((float)var0.x, (float)var0.y, (float)var0.z, var1, var2);
   }

   public static boolean worldToScreen(Vec3 var0, FloatPoint2D var1) {
      return nrq((float)var0.x, (float)var0.y, (float)var0.z, true, var1);
   }

   public static void sgd(Pose var0) {
       GameRendererProjectionAccessor accessor = (GameRendererProjectionAccessor) Minecraft.getInstance().gameRenderer;
       Matrix4f proj = accessor.phantom$getHudProjection().getMatrix(new Matrix4f());
      proj.get(mpv);
      var0.pose().get(akk);
      agg[0] = akk[0] * mpv[0] + akk[1] * mpv[4] + akk[2] * mpv[8] + akk[3] * mpv[12];
      agg[1] = akk[0] * mpv[1] + akk[1] * mpv[5] + akk[2] * mpv[9] + akk[3] * mpv[13];
      agg[2] = akk[0] * mpv[2] + akk[1] * mpv[6] + akk[2] * mpv[10] + akk[3] * mpv[14];
      agg[3] = akk[0] * mpv[3] + akk[1] * mpv[7] + akk[2] * mpv[11] + akk[3] * mpv[15];
      agg[4] = akk[4] * mpv[0] + akk[5] * mpv[4] + akk[6] * mpv[8] + akk[7] * mpv[12];
      agg[5] = akk[4] * mpv[1] + akk[5] * mpv[5] + akk[6] * mpv[9] + akk[7] * mpv[13];
      agg[6] = akk[4] * mpv[2] + akk[5] * mpv[6] + akk[6] * mpv[10] + akk[7] * mpv[14];
      agg[7] = akk[4] * mpv[3] + akk[5] * mpv[7] + akk[6] * mpv[11] + akk[7] * mpv[15];
      agg[8] = akk[8] * mpv[0] + akk[9] * mpv[4] + akk[10] * mpv[8] + akk[11] * mpv[12];
      agg[9] = akk[8] * mpv[1] + akk[9] * mpv[5] + akk[10] * mpv[9] + akk[11] * mpv[13];
      agg[10] = akk[8] * mpv[2] + akk[9] * mpv[6] + akk[10] * mpv[10] + akk[11] * mpv[14];
      agg[11] = akk[8] * mpv[3] + akk[9] * mpv[7] + akk[10] * mpv[11] + akk[11] * mpv[15];
      agg[12] = akk[12] * mpv[0] + akk[13] * mpv[4] + akk[14] * mpv[8] + akk[15] * mpv[12];
      agg[13] = akk[12] * mpv[1] + akk[13] * mpv[5] + akk[14] * mpv[9] + akk[15] * mpv[13];
      agg[14] = akk[12] * mpv[2] + akk[13] * mpv[6] + akk[14] * mpv[10] + akk[15] * mpv[14];
      agg[15] = akk[12] * mpv[3] + akk[13] * mpv[7] + akk[14] * mpv[11] + akk[15] * mpv[15];
      ktp[0][0] = agg[3] - agg[0];
      ktp[0][1] = agg[7] - agg[4];
      ktp[0][2] = agg[11] - agg[8];
      ktp[0][3] = agg[15] - agg[12];
      wil(ktp[0]);
      ktp[1][0] = agg[3] + agg[0];
      ktp[1][1] = agg[7] + agg[4];
      ktp[1][2] = agg[11] + agg[8];
      ktp[1][3] = agg[15] + agg[12];
      wil(ktp[1]);
      ktp[2][0] = agg[3] + agg[1];
      ktp[2][1] = agg[7] + agg[5];
      ktp[2][2] = agg[11] + agg[9];
      ktp[2][3] = agg[15] + agg[13];
      wil(ktp[2]);
      ktp[3][0] = agg[3] - agg[1];
      ktp[3][1] = agg[7] - agg[5];
      ktp[3][2] = agg[11] - agg[9];
      ktp[3][3] = agg[15] - agg[13];
      wil(ktp[3]);
      ktp[4][0] = agg[3] - agg[2];
      ktp[4][1] = agg[7] - agg[6];
      ktp[4][2] = agg[11] - agg[10];
      ktp[4][3] = agg[15] - agg[14];
      wil(ktp[4]);
      ktp[5][0] = agg[3] + agg[2];
      ktp[5][1] = agg[7] + agg[6];
      ktp[5][2] = agg[11] + agg[10];
      ktp[5][3] = agg[15] + agg[14];
      wil(ktp[5]);
   }

   private static void wil(float[] var0) {
      float var1 = Mth.sqrt(var0[0] * var0[0] + var0[1] * var0[1] + var0[2] * var0[2]);
      var0[0] /= var1;
      var0[1] /= var1;
      var0[2] /= var1;
      var0[3] /= var1;
   }

   public static float zns(float[] var0, float var1, float var2, float var3) {
      return var0[0] * var1 + var0[1] * var2 + var0[2] * var3 + var0[3];
   }

   public static boolean bn(float var0, float var1, float var2) {
      for (int var3 = 0; var3 < 6; var3++) {
         float[] var4 = ktp[var3];
         if (zns(var4, var0, var1, var2) <= 0.0) {
            return false;
         }
      }

      return true;
   }

   public static boolean isPointInFrustum(Vec3 var0) {
      return bn((float)var0.x, (float)var0.y, (float)var0.z);
   }

   private static void gzv(float[] var0, float[] var1, float[] var2) {
      for (int var3 = 0; var3 < 4; var3++) {
         var2[var3] = var1[0] * var0[0 + var3] + var1[1] * var0[4 + var3] + var1[2] * var0[8 + var3] + var1[3] * var0[12 + var3];
      }
   }

   public static boolean igxp(double var0, double var2, double var4, boolean var6, FloatPoint2D var7) {
      return nrq((float)var0, (float)var2, (float)var4, var6, var7);
   }

   public static boolean gub(double var0, double var2, double var4, FloatPoint2D var6) {
      return nrq((float)var0, (float)var2, (float)var4, false, var6);
   }

   public static boolean uoa(float var0, float var1, float var2, FloatPoint2D var3) {
      return nrq(var0, var1, var2, false, var3);
   }

   public static boolean nrq(float var0, float var1, float var2, boolean var3, FloatPoint2D var4) {
      if (var3 && !bn(var0, var1, var2)) {
         return false;
      } else {
         float[] var5 = new float[]{var0, var1, var2, 1.0F};
         float[] var6 = new float[]{0.0F, 0.0F, 0.0F, 0.0F};
         gzv(akk, var5, var6);
         gzv(mpv, var6, var5);
         if (var5[3] == 0.0F) {
            return false;
         } else {
            var5[3] = 1.0F / var5[3] * 0.5F;
            var5[0] = var5[0] * var5[3] + 0.5F;
            var5[1] = var5[1] * var5[3] + 0.5F;
            var5[2] = var5[2] * var5[3] + 0.5F;
            GuiRenderContext var7 = PhantomClient.uiManager.getContext();
            var4.axn = (int)(var5[0] * var7.framebufferWidth);
            var4.oaz = (int)((1.0 - var5[1]) * var7.framebufferHeight);
            return true;
         }
      }
   }

   public static void jmb(String var0) {
      if (PhantomClient.isDebugBuild) {
         int var1 = GL11.glGetError();
         if (var1 != 0) {
            System.err.println(var1);
         }
      }
   }

   public static GlTexture qpv(int var0, int var1, int var2) {
      return new GlTexture(0, "", TextureFormat.RGBA8, var0, var1, 0, var2, 0) {};
   }

   public static void ehw(PoseStack var0, VoxelShape var1, Color var2, float var3) {
      float var4 = var2.getRed() / 255.0F;
      float var5 = var2.getGreen() / 255.0F;
      float var6 = var2.getBlue() / 255.0F;
      cpb(var0, var1, var4, var5, var6, var3);
      szb(var0, var1, var4, var5, var6, var3);
   }

   public static void szb(PoseStack var0, VoxelShape var1, float var2, float var3, float var4, float var5) {
      BufferSource var6 = Minecraft.getInstance().renderBuffers().bufferSource();
       VertexConsumer var7 = var6.getBuffer(RenderTypes.LINES);
      Pose var8 = var0.last();
      var1.forAllEdges((var6x, var8x, var10, var12, var14, var16) -> {
         Vector3f var18 = new Vector3f((float)(var12 - var6x), (float)(var14 - var8x), (float)(var16 - var10)).normalize();
         var7.addVertex(var8, (float)var6x, (float)var8x, (float)var10).setColor(var2, var3, var4, var5).setLineWidth(1.0F).setNormal(var8, var18);
         var7.addVertex(var8, (float)var12, (float)var14, (float)var16).setColor(var2, var3, var4, var5).setLineWidth(1.0F).setNormal(var8, var18);
      });
      if (ikib) {
         var6.endLastBatch();
      }
   }

   public static void cpb(PoseStack var0, VoxelShape var1, float var2, float var3, float var4, float var5) {
      BufferSource var6 = Minecraft.getInstance().renderBuffers().bufferSource();
       VertexConsumer var7 = var6.getBuffer(RenderTypes.debugFilledBox());
      Matrix4f var8 = var0.last().pose();

      for (AABB var10 : var1.toAabbs()) {
         float var11 = (float)var10.minX;
         float var12 = (float)var10.minY;
         float var13 = (float)var10.minZ;
         float var14 = (float)var10.maxX;
         float var15 = (float)var10.maxY;
         float var16 = (float)var10.maxZ;
         var7.addVertex(var8, var11, var12, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var12, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var12, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var12, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var15, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var15, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var15, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var12, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var15, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var12, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var12, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var12, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var15, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var15, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var15, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var12, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var15, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var12, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var12, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var12, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var12, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var12, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var12, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var15, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var15, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var11, var15, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var15, var13).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var15, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var15, var16).setColor(var2, var3, var4, var5);
         var7.addVertex(var8, var14, var15, var16).setColor(var2, var3, var4, var5);
      }

      if (ikib) {
         var6.endLastBatch();
      }
   }
}
