package me.phantom.util;

import me.phantom.core.PhantomClient;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.LanguageFile;

import java.util.ArrayList;
import java.util.Map.Entry;

public class DumpMissingTranslationsCommand {
   public static void ck(String[] var0) {
      PhantomClient.isDebugBuild = true;
      LanguageFile var1 = new LanguageFile("de_DE");
      LanguageFile var2 = new LanguageFile("en_US");
      ArrayList var3 = new ArrayList();

      for (Entry var5 : var2.zim().entrySet()) {
         if (!var1.zim().containsKey(var5.getKey())) {
            var3.add(GlyphTextUtil.decode((String)var5.getKey()));
         }
      }

      var3.stream().sorted().map(var0x -> var0x + "=").forEach(System.out::println);
   }
}
