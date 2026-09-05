package me.phantom.gui.hud;

import me.phantom.module.internal.HudModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;

public class LeftCpsHudIndicator extends LabeledHudElement {
   public LeftCpsHudIndicator(JsonObject var1) {
      super(var1);
   }

   @Override
   public String oiuz(float var1) {
      return GlyphTextUtil.encode("L:") + HudModule.yxu.size();
   }

   @Override
   public int uod() {
      return 128200;
   }
}
