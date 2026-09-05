package me.phantom.util;

import me.phantom.core.PhantomClient;

import java.util.HashMap;
import java.util.Map;

public class LanguageFile {
   private final Map<String, String> sp = new HashMap<>();
   public final String name;

   public LanguageFile(String var1) {
      this.name = var1;
      this.refresh();
   }

   public void refresh() {
      if (!this.name.equals("test")) {
         try {
            String var1 = ResourceLoader.repairPath(GlyphTextUtil.decode(this.name)).toLowerCase(java.util.Locale.ROOT);
            this.sp.clear();
            char var2 = GlyphTextUtil.encode("=").charAt(0);

            for (String var5 : new String(ResourceLoader.ogt("languages/" + var1 + ".lang"), GlyphTextUtil.plain("UTF-8"))
               .replace(GlyphTextUtil.encode("\r\n"), GlyphTextUtil.encode("\n"))
               .split(GlyphTextUtil.encode("\n"))) {
               if (!var5.isEmpty() && !var5.startsWith(GlyphTextUtil.encode("#"))) {
                  String var6 = var5.substring(0, var5.indexOf(var2));
                  String var7 = var5.substring(var6.length() + 1);
                  if (this.sp.put(var6, var7) != null) {
                     throw new RuntimeException(GlyphTextUtil.encode("Duplicate key: '") + var6 + GlyphTextUtil.encode("'"));
                  }
               }
            }
         } catch (Throwable var8) {
            PhantomClient.fatalError(GlyphTextUtil.encode("Language"), GlyphTextUtil.encode("Failed to load language ") + this.name, var8);
         }
      }
   }

   public Map<String, String> zim() {
      return this.sp;
   }

   public String ry(String var1, Object... var2) {
      var1 = GlyphTextUtil.lowercase(var1);
      String var3 = this.sp.get(var1);
      if (var3 == null) {
         StringBuilder var7 = new StringBuilder(var1 + GlyphTextUtil.encode("="));

         for (int var5 = 0; var5 < var2.length; var5++) {
            var7.append(GlyphTextUtil.encode("$")).append(var5 + 1).append(GlyphTextUtil.encode("  "));
         }

         this.sp.put(var1, var7.toString());
         if (!var1.startsWith(GlyphTextUtil.encode("module.esp."))) {
            if (PhantomClient.isDebugBuild) {
               System.err.println(GlyphTextUtil.decode(var1) + GlyphTextUtil.plain("="));
            } else {
               PhantomClient.clientLogger.sdj(GlyphTextUtil.encode("Language"), GlyphTextUtil.encode("Missing language key: ") + var1);
            }
         }

         return var7.toString();
      } else {
         for (int var4 = 0; var4 < var2.length; var4++) {
            var3 = var3.replace(GlyphTextUtil.encode("$") + GlyphTextUtil.encodeChars(String.valueOf(var4 + 1)), var2[var4].toString());
         }

         return var3;
      }
   }

   public String getName() {
      return this.name;
   }

}
