package me.phantom.core.module;

import me.phantom.core.module.ModuleManager;
import me.phantom.module.internal.FriendModule;
import me.phantom.module.misc.AntiBotModule;
import me.phantom.module.targets.IgnoreListModule;
import me.phantom.module.targets.PrioListModule;
import me.phantom.module.targets.TargetBotsModule;
import me.phantom.module.targets.TargetFriendsModule;
import me.phantom.module.targets.TargetMobsModule;
import me.phantom.module.targets.TargetPlayersModule;

import me.phantom.util.GlyphTextUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class TargetSelector {
   public TargetBotsModule uto;
   public AntiBotModule me;
   public TargetFriendsModule rmo;
   public FriendModule uib;
   public TargetMobsModule tnq;
   public TargetPlayersModule axo;
   public IgnoreListModule efy;
   public PrioListModule cgq;

   public TargetSelector(ModuleManager var1) {
      this.me = (AntiBotModule)var1.getModuleByName(GlyphTextUtil.encode("AntiBot"));
      this.uto = (TargetBotsModule)var1.getModuleByName(GlyphTextUtil.encode("Target bots"));
      this.uib = (FriendModule)var1.getModuleByName(GlyphTextUtil.encode("Friend"));
      this.rmo = (TargetFriendsModule)var1.getModuleByName(GlyphTextUtil.encode("Target friends"));
      this.tnq = (TargetMobsModule)var1.getModuleByName(GlyphTextUtil.encode("Target mobs"));
      this.axo = (TargetPlayersModule)var1.getModuleByName(GlyphTextUtil.encode("Target players"));
      this.efy = (IgnoreListModule)var1.getModuleByName(GlyphTextUtil.encode("Ignore list"));
      this.cgq = (PrioListModule)var1.getModuleByName(GlyphTextUtil.encode("Prio list"));
   }

   public boolean tso(Entity var1) {
      return this.cgq.isEnabled() && this.cgq.pji(var1);
   }

   public boolean csu(Entity var1) {
      if (!this.uto.isEnabled() && this.me.uei(var1)) {
         return false;
      } else if (var1 instanceof PlayerEntity) {
         if (!this.rmo.isEnabled() && this.uib.tdv(var1)) {
            return false;
         } else {
            return this.efy.isEnabled() && this.efy.pji(var1) ? false : this.axo.isEnabled();
         }
      } else {
         return var1 instanceof MobEntity ? this.tnq.isEnabled() : false;
      }
   }
}
