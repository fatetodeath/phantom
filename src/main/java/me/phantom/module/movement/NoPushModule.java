package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.entity.CollisionType;
import me.phantom.event.entity.EntityPushEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

@ModuleMetadata(
   x = 21.5,
   y = -60.0,
   z = -4.5
)
public class NoPushModule extends BaseModule {
   public final ToggleSetting xpw = new ToggleSetting(this, GlyphTextUtil.encode("liquids"), true);
   public final ToggleSetting qgk = new ToggleSetting(this, GlyphTextUtil.encode("entities"), true);

   public NoPushModule() {
      super(ModuleCategory.wld, ClientPlayStyle.bq, GlyphTextUtil.encode("NoPush"));
   }

   public void fwc(EntityPushEvent var1) {
      if (var1.xna() == CollisionType.itl && (Boolean)this.qgk.getValue()) {
         var1.setCancelled(true);
      } else if (var1.xna() == CollisionType.nu && (Boolean)this.xpw.getValue()) {
         var1.setCancelled(true);
      }
   }
}
