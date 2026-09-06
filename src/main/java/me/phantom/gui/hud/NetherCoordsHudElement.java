package me.phantom.gui.hud;

import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

public class NetherCoordsHudElement extends LabeledHudElement {
   public NetherCoordsHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public String oiuz(float var1) {
      Vec3 var2 = Vec3.ZERO;

      try {
         LocalPlayer var3 = Minecraft.getInstance().player;
         var2 = var3.getPosition(var1);
      } catch (Throwable var4) {
      }

      return GlyphTextUtil.plain("X: %.1f Y: %.1f Z: %.1f").formatted(var2.x / 8.0, var2.y, var2.z / 8.0).replace(GlyphTextUtil.plain(","), GlyphTextUtil.plain("."));
   }

   @Override
   public int uod() {
      return 128229;
   }
}
