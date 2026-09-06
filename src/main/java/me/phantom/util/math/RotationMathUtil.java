package me.phantom.util.math;

import me.phantom.render.Render3DUtil;
import me.phantom.util.GlyphTextUtil;

import java.util.Locale;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class RotationMathUtil {
   public static final FloatPoint2D projection = new FloatPoint2D();

   public static double square(double var0) {
      return var0 * var0;
   }

   public static float[] getScreenBoundingBox(Vec3 var0, Entity var1) {
      double var2 = var1.getBbWidth();
      double var4 = var1.getBbHeight();
      boolean var6 = false;
      Vec3[] var7 = new Vec3[]{
         var0.add(-var2 / 2.0, 0.0, -var2 / 2.0),
         var0.add(var2 / 2.0, 0.0, -var2 / 2.0),
         var0.add(-var2 / 2.0, 0.0, var2 / 2.0),
         var0.add(var2 / 2.0, 0.0, var2 / 2.0),
         var0.add(-var2 / 2.0, var4, -var2 / 2.0),
         var0.add(var2 / 2.0, var4, -var2 / 2.0),
         var0.add(-var2 / 2.0, var4, var2 / 2.0),
         var0.add(var2 / 2.0, var4, var2 / 2.0)
      };

      for (Vec3 var11 : var7) {
         if (Render3DUtil.isPointInFrustum(var11)) {
            var6 = true;
            break;
         }
      }

      if (!var6) {
         return null;
      } else {
         float var16 = Float.MAX_VALUE;
         float var17 = Float.MAX_VALUE;
         float var18 = Float.MIN_VALUE;
         float var19 = Float.MIN_VALUE;

         for (Vec3 var15 : var7) {
            Render3DUtil.worldToScreen(var15, false, projection);
            var18 = Math.max(projection.axn, var18);
            var19 = Math.max(projection.oaz, var19);
            var16 = Math.min(projection.axn, var16);
            var17 = Math.min(projection.oaz, var17);
         }

         return new float[]{var16, var17, var18, var19};
      }
   }

   public static float wrapDegrees(float var0) {
      var0 %= 360.0F;
      if (var0 >= 180.0F) {
         var0 -= 360.0F;
      }

      if (var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static double distanceBetween(double var0, double var2, double var4, double var6, double var8, double var10) {
      return Math.sqrt(square(var0 - var6) + square(var2 - var8) + square(var4 - var10));
   }

   public static double squaredDistanceBetween(double var0, double var2, double var4, double var6, double var8, double var10) {
      return square(var0 - var6) + square(var2 - var8) + square(var4 - var10);
   }

   public static Vec2 getRotationTo(Vec3 var0, Vec3 var1) {
      Vec3 var2 = var1.subtract(var0);
      double var3 = Math.sqrt(var2.x * var2.x + var2.z * var2.z);
      double var5 = Math.sqrt(var3 * var3 + var2.y * var2.y);
      double var7 = Math.acos(var2.x / var3) * 180.0 / Math.PI;
      double var9 = Math.acos(var2.y / var5) * 180.0 / Math.PI - 90.0;
      if (var2.z < 0.0) {
         var7 += Math.abs(180.0 - var7) * 2.0;
      }

      return new Vec2((float)(var7 - 90.0), (float)var9);
   }

   public static float angleDifference(float var0, float var1) {
      return wrapDegrees(wrapDegrees(var0) - wrapDegrees(var1));
   }

   public static String formatTwoDecimals(float var0) {
      return String.format(Locale.US, GlyphTextUtil.plain("%.2f"), var0);
   }

   public static Vec3 getLookVector(float var0, float var1) {
      double var2 = Math.toRadians(var0);
      double var4 = Math.toRadians(var1);
      double var6 = Math.cos(var4);
      return new Vec3(-Math.sin(var2) * var6, -Math.sin(var4), Math.cos(var2) * var6);
   }

   public static float rotateTowards(float var0, float var1, float var2) {
      float var3 = angleDifference(var1, var0);
      if (var3 > var2) {
         var3 = var2;
      }

      if (var3 < -var2) {
         var3 = -var2;
      }

      float var4 = var0 + var3;
      if (var4 < 0.0F) {
         var4 += 360.0F;
      } else if (var4 > 360.0F) {
         var4 -= 360.0F;
      }

      return wrapDegrees(var4);
   }

   public static float lerp(float var0, float var1, float var2) {
      return var0 + (var1 - var0) * var2;
   }

}
