package me.phantom.gui.hud;

import me.phantom.module.misc.NameProtectModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;

public class UsernameHudElement extends LabeledHudElement {
   public UsernameHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public String oiuz(float var1) {
      String var2 = Minecraft.getInstance().getGameProfile().name();
      return GlyphTextUtil.encode("Username: ") + NameProtectModule.jue(var2);
   }

   @Override
   public int uod() {
      return 128100;
   }
}
