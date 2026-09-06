package me.phantom.module.targets;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

public class TargetPlayersModule extends BaseModule {
   public TargetPlayersModule() {
      super(ModuleCategory.cuh, ClientPlayStyle.or, GlyphTextUtil.encode("Target players"));
      this.setEnabled(true, false);
   }
}
