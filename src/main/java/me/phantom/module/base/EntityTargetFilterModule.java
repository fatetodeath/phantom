package me.phantom.module.base;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.internal.FriendModule;
import me.phantom.module.misc.AntiBotModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class EntityTargetFilterModule extends BaseModule {
   public final ToggleSetting zfe = new ToggleSetting(this, GlyphTextUtil.encode("attackmobs"), false);
   public final ToggleSetting dcv = new ToggleSetting(this, GlyphTextUtil.encode("attackfriends"), true);
   public final ToggleSetting yca = new ToggleSetting(this, GlyphTextUtil.encode("attackplayers"), true);
   public final ToggleSetting xf = new ToggleSetting(this, GlyphTextUtil.encode("antibot"), false);
   public AntiBotModule pg;
   public FriendModule bd;

   public EntityTargetFilterModule(ModuleCategory var1, ClientPlayStyle var2, String var3) {
      super(var1, var2, var3);
   }

   public boolean csu(Entity var1) {
      if ((Boolean)this.xf.getValue() && this.pg.uei(var1)) {
         return false;
      } else if (var1 instanceof Player) {
         return !this.dcv.getValue() && this.bd.tdv(var1) ? false : (Boolean)this.yca.getValue();
      } else {
         return var1 instanceof Mob ? (Boolean)this.zfe.getValue() : false;
      }
   }
}
