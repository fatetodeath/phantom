package me.phantom.core.module;

import me.phantom.core.PhantomClient;
import me.phantom.module.internal.PacketJamModule;
import me.phantom.util.GlyphTextUtil;

public interface PacketJamParticipant {
   boolean brz();

   boolean zmb();

   default void xmw() {
      PacketJamModule var1 = (PacketJamModule) PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("PacketJam"));
      var1.col();
      var1.feq();
   }
}
