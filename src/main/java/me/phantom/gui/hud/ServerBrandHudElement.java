package me.phantom.gui.hud;

import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;

public class ServerBrandHudElement extends LabeledHudElement {
   public ServerBrandHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public String oiuz(float var1) {
      String var2;
      try {
         var2 = MinecraftClient.getInstance().getNetworkHandler().getBrand();
      } catch (Throwable var4) {
         var2 = GlyphTextUtil.encode("Unknown");
      }

      return GlyphTextUtil.encode("Brand: ") + var2;
   }

   @Override
   public int uod() {
      return 128240;
   }
}
