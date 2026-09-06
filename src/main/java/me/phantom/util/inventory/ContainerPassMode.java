package me.phantom.util.inventory;

import me.phantom.util.GlyphTextUtil;

public class ContainerPassMode {
   public static final ContainerPassMode jvu = new ContainerPassMode(GlyphTextUtil.encode("DRY"));
   public static final ContainerPassMode fp = new ContainerPassMode(GlyphTextUtil.encode("DRY"));
   public static final ContainerPassMode ss = new ContainerPassMode(GlyphTextUtil.encode("DRY"));
   public final String name;

   public ContainerPassMode(String var1) {
      this.name = var1;
   }

   public String vr() {
      return this.name;
   }
}
