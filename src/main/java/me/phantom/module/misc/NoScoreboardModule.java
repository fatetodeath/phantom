package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.render.ScoreboardSidebarRenderEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

public class NoScoreboardModule extends BaseModule {
   public NoScoreboardModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.or, GlyphTextUtil.encode("NoScoreboard"));
      this.selectAllSettings();
   }

   public void yafn(ScoreboardSidebarRenderEvent var1) {
      var1.setCancelled(true);
   }
}
