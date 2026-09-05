package me.phantom.gui.hud;

import me.phantom.module.internal.HudModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;

public class RightCpsHudElement extends LabeledHudElement {
   public RightCpsHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public String oiuz(float var1) {
      return GlyphTextUtil.encode("R:") + HudModule.cbp.size();
   }

   @Override
   public int uod() {
      return 128200;
   }
}
