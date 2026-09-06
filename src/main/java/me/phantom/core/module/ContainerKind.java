package me.phantom.core.module;

import me.phantom.util.GlyphTextUtil;

public class ContainerKind {
   public static final ContainerKind rgk = new ContainerKind(GlyphTextUtil.encode("INVENTORY"));
   public static final ContainerKind phn = new ContainerKind(GlyphTextUtil.encode("CHEST"));
   public static final ContainerKind zk = new ContainerKind(GlyphTextUtil.encode("ENCHANTMENT"));
   public static final ContainerKind hss = new ContainerKind(GlyphTextUtil.encode("CRAFTING_TABLE"));
   public static final ContainerKind uyr = new ContainerKind(GlyphTextUtil.encode("ENDER_CHEST"));
   public final String name;

   public ContainerKind(String var1) {
      this.name = var1;
   }

   public String vr() {
      return this.name;
   }
}
