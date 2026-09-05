package me.phantom.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils {
   public static int jwk(JsonObject var0, String var1, int var2) {
      try {
         return var0.get(var1).getAsInt();
      } catch (Throwable var4) {
         return var2;
      }
   }

   public static long wit(JsonObject var0, String var1, long var2) {
      try {
         return var0.get(var1).getAsLong();
      } catch (Throwable var5) {
         return var2;
      }
   }

   public static boolean itd(JsonObject var0, String var1, boolean var2) {
      try {
         return uzc(var0.get(var1));
      } catch (Throwable var4) {
         return var2;
      }
   }

   public static Boolean uzc(JsonElement var0) {
      return var0.isJsonPrimitive() && (var0.getAsBoolean() || var0.getAsString().equals(GlyphTextUtil.encode("true")));
   }
}
