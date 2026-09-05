package me.phantom.module.targets;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.TeamsModule;
import me.phantom.setting.StringSetSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.OriginalProfileProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class IgnoreListModule extends BaseModule {
   public StringSetSetting hso = new StringSetSetting(this, "ignore_list");

   public IgnoreListModule() {
      super(ModuleCategory.cuh, ClientPlayStyle.or, GlyphTextUtil.encode("Ignore list"));
   }

   @Override
   public void setExpanded(boolean var1) {
      if (var1) {
         this.hso.poo();
      }
   }

   public boolean pji(Entity var1) {
      if (!(var1 instanceof PlayerEntity var2)) {
         return false;
      } else {
         String var3 = ((OriginalProfileProvider)var1).getOrginalProfile().getName();
         return this.hso.ovk(GlyphTextUtil.decode(var3)) || TeamsModule.sameTeam(var2);
      }
   }

}
