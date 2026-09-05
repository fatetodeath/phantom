package me.phantom.core.service;

import me.phantom.core.PhantomClient;
import me.phantom.module.internal.DeveloperModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.LanguageFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TranslationManager {
   public final Map<String, LanguageFile> wg = new HashMap<>();

   public void refresh() throws Throwable {
      for (LanguageFile var2 : this.wg.values()) {
         var2.refresh();
      }
   }

   public LanguageFile oja(String var1) {
      return this.wg.computeIfAbsent(var1, LanguageFile::new);
   }

   public LanguageFile xzx() {
      return DeveloperModule.yyt != null && DeveloperModule.yyt.isEnabled() && DeveloperModule.yyt.gba.getValue() && DeveloperModule.yyt.mt != null
         ? DeveloperModule.yyt.mt
         : this.oja(PhantomClient.uiManager != null && PhantomClient.uiManager.interfaceModule != null ? (String)PhantomClient.uiManager.interfaceModule.ka.getValue() : GlyphTextUtil.encode("en_US"));
   }

   public String ry(String var1, Object... var2) {
      return this.xzx().ry(var1, var2);
   }

   public Set<String> cd() {
      return this.wg.keySet();
   }

}
