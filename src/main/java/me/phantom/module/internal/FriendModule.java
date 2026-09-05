package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.misc.NameProtectModule;
import me.phantom.setting.NameListSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.entity.FreecamPlayerClone;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.OriginalProfileProvider;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
public class FriendModule extends BaseModule {
   public final NameListSetting pd = new NameListSetting(this, GlyphTextUtil.encode("friends"));
   public final ToggleSetting tlg = new ToggleSetting(this, GlyphTextUtil.encode("middleclick"), true);
   public NameProtectModule mlt;
   public boolean vv;

   public FriendModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("Friend"));
      this.setEnabled(true, true);
   }

   @Override
   public void onDisable() {
      this.setEnabled(true, true);
   }

   public void tjy(TickEvent var1) {
      if (!(Boolean)this.tlg.getValue() || !PhantomClient.uiManager.getContext().xfy[2]) {
         this.vv = false;
      } else if (!this.vv) {
         Entity var2 = GameInteractionUtil.getCrosshairEntity();
         this.vv = true;
         if (var2 instanceof PlayerEntity && !(var2 instanceof ClientPlayerEntity) && !(var2 instanceof FreecamPlayerClone)) {
            String var3 = ((OriginalProfileProvider)var2).getOrginalProfile().getName();
            if (this.mlt.isEnabled()) {
               var3 = this.mlt.ssv(var3);
            }

            if (this.pd.ovk(var3)) {
               this.pd.lcy(var3);
               PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("module.friend.remove"), var3);
            } else {
               this.pd.swh(var3);
               PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("module.friend.added"), var3);
            }

            this.pd.notifyConfigChanged();
            if (this.mlt.isEnabled()) {
               this.mlt.hhk();
            }
         }
      }
   }

   public boolean tdv(Entity var1) {
      if (!(var1 instanceof PlayerEntity var2)) {
         return false;
      } else {
         String var3 = ((OriginalProfileProvider)var1).getOrginalProfile().getName();
         return this.pd.ovk(GlyphTextUtil.decode(var3)) || TeamsModule.sameTeam(var2);
      }
   }
}
