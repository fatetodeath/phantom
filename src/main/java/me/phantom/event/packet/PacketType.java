package me.phantom.event.packet;

import me.phantom.util.GlyphTextUtil;

public class PacketType {
   public static final PacketType cq = new PacketType(GlyphTextUtil.encode("EXPLOSION_PACKET"));
   public static final PacketType niz = new PacketType(GlyphTextUtil.encode("POSITION_LOOK_PACKET"));
   public static final PacketType sf = new PacketType(GlyphTextUtil.encode("VELOCITY_PACKET"));
   public final String name;

   public PacketType(String var1) {
      this.name = var1;
   }
}
