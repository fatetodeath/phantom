package me.phantom.gui.hud;

import me.phantom.gui.hud.LabeledHudElement;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;

public class HudServerInfoElement extends LabeledHudElement {
   public HudServerInfoElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public String oiuz(float var1) {
      String var2;
      try {
         var2 = MinecraftClient.getInstance().getNetworkHandler().getServerInfo().address;
      } catch (Throwable var4) {
         var2 = GlyphTextUtil.encode("Unknown");
      }

      return GlyphTextUtil.encode("Server: ") + var2;
   }

   @Override
   public int uod() {
      return 127758;
   }

}
