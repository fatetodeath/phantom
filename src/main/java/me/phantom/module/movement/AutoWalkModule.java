package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

public class AutoWalkModule extends BaseModule {
   public AutoWalkModule() {
      super(ModuleCategory.wld, ClientPlayStyle.or, GlyphTextUtil.encode("AutoWalk"));
      this.markClipboardHint();
   }

   public void sau(KeyStateEvent var1) {
      if (var1.by().getName().equals(GlyphTextUtil.plain("key.forward")) && PhantomClient.isScreenClosed()) {
         var1.ywz(true);
      }
   }
}
