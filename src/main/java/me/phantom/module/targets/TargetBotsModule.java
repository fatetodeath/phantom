package me.phantom.module.targets;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

public class TargetBotsModule extends BaseModule {
   public TargetBotsModule() {
      super(ModuleCategory.cuh, ClientPlayStyle.or, GlyphTextUtil.encode("Target bots"));
   }

}
