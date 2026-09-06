package me.phantom.module.network;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ModeSetting;
import me.phantom.util.GlyphTextUtil;

public class ClientSpooferModule extends BaseModule {
   public static ClientSpooferModule aw;
   public ModeSetting modeSetting = new ModeSetting(this, GlyphTextUtil.encode("mode"), GlyphTextUtil.encode("LabyMod"), GlyphTextUtil.encode("Lunar"), GlyphTextUtil.encode("Feather"), GlyphTextUtil.encode("Badlion"));

   public ClientSpooferModule() {
      super(ModuleCategory.jq, ClientPlayStyle.ats, GlyphTextUtil.encode("ClientSpoofer"));
      aw = this;
   }

   @Override
   public void afterToggle() {
      if (this.isEnabled()) {
         PhantomClient.uiManager.notifications().klr(GlyphTextUtil.encode("module.clientspoofer.toggle"));
      }
   }

   public static String vme(String var0) {
      if (aw != null && aw.isEnabled()) {
         if (((String)aw.modeSetting.getValue()).equals(GlyphTextUtil.encode("LabyMod"))) {
            return GlyphTextUtil.decode(GlyphTextUtil.encode("labymod"));
         } else if (((String)aw.modeSetting.getValue()).equals(GlyphTextUtil.encode("Lunar"))) {
            return GlyphTextUtil.decode(GlyphTextUtil.encode("lunarclient:v2.15.6-2422"));
         } else if (((String)aw.modeSetting.getValue()).equals(GlyphTextUtil.encode("Feather"))) {
            return GlyphTextUtil.decode(GlyphTextUtil.encode("Feather Fabric"));
         } else {
            return ((String)aw.modeSetting.getValue()).equals(GlyphTextUtil.encode("Badlion")) ? GlyphTextUtil.decode(GlyphTextUtil.encode("badlion")) : var0;
         }
      } else {
         return var0;
      }
   }
}
