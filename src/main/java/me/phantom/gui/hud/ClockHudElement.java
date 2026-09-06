package me.phantom.gui.hud;

import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import java.util.Calendar;

public class ClockHudElement extends LabeledHudElement {
   public ClockHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public String oiuz(float var1) {
      Calendar var2 = Calendar.getInstance();
      return String.format(GlyphTextUtil.plain("%02d:%02d:%02d"), var2.get(11), var2.get(12), var2.get(13));
   }

   @Override
   public int uod() {
      return 128340;
   }
}
