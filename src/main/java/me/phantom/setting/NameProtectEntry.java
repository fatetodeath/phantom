package me.phantom.setting;

import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;

public class NameProtectEntry {
   public final String rxs;
   public String iwg;

   public NameProtectEntry(NameListSetting var1, String var2) {
      this(var1, var2, NameListSetting.dpg());
   }

   public NameProtectEntry(NameListSetting var1, String var2, String var3) {
      this.rxs = var2;
      this.iwg = var3;
   }

   public NameProtectEntry(NameListSetting var1, JsonObject var2) {
      this.rxs = var2.get(GlyphTextUtil.encode("name")).getAsString();
      this.iwg = var2.get(GlyphTextUtil.encode("nameprotect")).getAsString();
   }

   public JsonObject qr() {
      JsonObject var1 = new JsonObject();
      var1.addProperty(GlyphTextUtil.encode("name"), this.rxs);
      var1.addProperty(GlyphTextUtil.encode("nameprotect"), this.iwg);
      return var1;
   }
}
