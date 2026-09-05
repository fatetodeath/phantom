package me.phantom.core.module;

import me.phantom.util.GlyphTextUtil;

public class ClientPlayStyle {
   public static final ClientPlayStyle bq = new ClientPlayStyle(GlyphTextUtil.encode("FULL_RAGE"), 0);
   public static final ClientPlayStyle ats = new ClientPlayStyle(GlyphTextUtil.encode("SEMI_RAGE"), 1);
   public static final ClientPlayStyle zfw = new ClientPlayStyle(GlyphTextUtil.encode("SEMI_LEGIT"), 2);
   public static final ClientPlayStyle or = new ClientPlayStyle(GlyphTextUtil.encode("FULL_LEGIT"), 3);
   private final String name;
   private final int yay;

   public ClientPlayStyle(String var1, int var2) {
      this.name = var1;
      this.yay = var2;
   }

   public String vr() {
      return this.name;
   }

   public int getLevel() {
      return this.yay;
   }
}
