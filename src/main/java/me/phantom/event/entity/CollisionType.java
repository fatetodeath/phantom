package me.phantom.event.entity;

import me.phantom.util.GlyphTextUtil;

public class CollisionType {
   public static final CollisionType nu = new CollisionType(GlyphTextUtil.encode("LIQUID"));
   public static final CollisionType itl = new CollisionType(GlyphTextUtil.encode("ENTITY"));
   public final String name;

   public CollisionType(String var1) {
      this.name = var1;
   }

}
