package me.phantom.gui.hud;

import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class CoordinatesHudElement extends LabeledHudElement {
   public CoordinatesHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public String oiuz(float var1) {
      Vec3d var2 = Vec3d.ZERO;

      try {
         ClientPlayerEntity var3 = MinecraftClient.getInstance().player;
         var2 = var3.getLerpedPos(var1);
      } catch (Throwable var4) {
      }

      return GlyphTextUtil.plain("X: %.1f Y: %.1f Z: %.1f").formatted(var2.x, var2.y, var2.z).replace(GlyphTextUtil.plain(","), GlyphTextUtil.plain("."));
   }

   @Override
   public int uod() {
      return 127758;
   }
}
