package me.phantom.core.module;

import me.phantom.util.GlyphTextUtil;

public class ModuleCategory {
   public static final ModuleCategory wld = new ModuleCategory(GlyphTextUtil.encode("MOVEMENT"), 128640);
   public static final ModuleCategory jq = new ModuleCategory(GlyphTextUtil.encode("NETWORK"), 59194);
   public static final ModuleCategory iua = new ModuleCategory(GlyphTextUtil.encode("INVENTORY"), 128230);
   public static final ModuleCategory vne = new ModuleCategory(GlyphTextUtil.encode("COMBAT"), 127919);
   public static final ModuleCategory pn = new ModuleCategory(GlyphTextUtil.encode("BUILD"), 59222);
   public static final ModuleCategory hpi = new ModuleCategory(GlyphTextUtil.encode("MISC"), 9874);
   public static final ModuleCategory yxw = new ModuleCategory(GlyphTextUtil.encode("VISUALS"), 59290);
   public static final ModuleCategory yy = new ModuleCategory(GlyphTextUtil.encode("INTERNAL"), 9881);
   public static final ModuleCategory cuh = new ModuleCategory(GlyphTextUtil.encode("TARGETS"), 127919);
   public static final ModuleCategory[] hx = new ModuleCategory[]{wld, jq, iua, vne, pn, hpi, yxw, yy, cuh};
   private final String name;
   public final int gve;

   public ModuleCategory(String var1, int var2) {
      this.name = var1;
      this.gve = var2;
   }

   public int zs() {
      return this.gve;
   }

   public String getName() {
      return this.name.charAt(0) + GlyphTextUtil.lowercase(this.name.substring(1));
   }

   public static ModuleCategory[] nku() {
      return hx;
   }

}
