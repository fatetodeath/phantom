package me.phantom.module.targets;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.TeamsModule;
import me.phantom.setting.StringSetSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.OriginalProfileProvider;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PrioListModule extends BaseModule {
   public StringSetSetting hso = new StringSetSetting(this, "prio_list");

   public PrioListModule() {
      super(ModuleCategory.cuh, ClientPlayStyle.or, GlyphTextUtil.encode("Prio list"));
   }

   @Override
   public void setExpanded(boolean var1) {
      if (var1) {
         this.hso.poo();
      }
   }

   public boolean pji(Entity var1) {
      if (!(var1 instanceof Player var2)) {
         return false;
      } else {
         String var3 = ((OriginalProfileProvider)var1).getOrginalProfile().name();
         return this.hso.ovk(GlyphTextUtil.decode(var3)) || TeamsModule.sameTeam(var2);
      }
   }
}
