package me.phantom.util;

import me.phantom.core.PhantomClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class ResourceLoader {
   private static final Map<Character, Character> REVERSE_TABLE = new HashMap<>();

   static {
      char[] table = GlyphTextUtil.ENCODE_TABLE;
      for (int i = 0; i < table.length; i++) {
         REVERSE_TABLE.put(table[i], (char) i);
      }
   }

   public static String repairPath(String var0) {
      StringBuilder var1 = new StringBuilder(var0.length());
      for (int i = 0; i < var0.length(); i++) {
         char c = var0.charAt(i);
         var1.append(REVERSE_TABLE.getOrDefault(c, c));
      }
      return var1.toString();
   }

   public static byte[] ogt(String var0) {
      var0 = repairPath(GlyphTextUtil.decode(var0));

      try {
         String var1 = System.getProperty(GlyphTextUtil.plain("ph.resources"));
         if (var1 != null) {
            File var8 = new File(var1, var0);
            if (!var0.endsWith(GlyphTextUtil.plain(".lang")) && !var0.endsWith(GlyphTextUtil.plain(".txt"))) {
               return IOUtils.toByteArray(new FileInputStream(var8));
            }

            return GlyphTextUtil.encodeChars(FileUtils.readFileToString(var8, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
         }
      } catch (IOException var5) {
      }

      try {
         if (PhantomClient.isDebugBuild) {
            String var7 = GlyphTextUtil.plain("../src/main/java/res/") + var0;
            File var2 = new File(var7);
            if (!var2.exists()) {
               var7 = GlyphTextUtil.plain("src/main/java/res/") + var0;
               var2 = new File(var7);
            }

            if (!var0.endsWith(GlyphTextUtil.plain(".lang")) && !var0.endsWith(GlyphTextUtil.plain(".txt"))) {
               return IOUtils.toByteArray(new FileInputStream(var7));
            }

            return GlyphTextUtil.encodeChars(FileUtils.readFileToString(var2, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
         }
      } catch (IOException var4) {
      }

      try {
         return !var0.endsWith(".lang") && !var0.endsWith(".txt")
            ? IOUtils.toByteArray(ResourceLoader.class.getClassLoader().getResourceAsStream(GlyphTextUtil.plain("res/") + var0))
            : IOUtils.toByteArray(ResourceLoader.class.getClassLoader().getResourceAsStream(GlyphTextUtil.plain("res/") + var0));
      } catch (IOException var3) {
         return null;
      }
   }
}
