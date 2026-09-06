package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.misc.ClipAtLedgeEvent;
import me.phantom.event.misc.MutableDoubleEvent;
import me.phantom.module.misc.NameProtectModule;
import me.phantom.util.mc.OriginalProfileProvider;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Player.class})
public abstract class PlayerAttackReachMixin implements OriginalProfileProvider {
   @Shadow
   private GameProfile gameProfile;

   @Inject(
      method = {"isStayingOnGroundSurface()Z"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void clipAtLedge(CallbackInfoReturnable<Boolean> info) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(ClipAtLedgeEvent.qon()).iyh()) {
         info.setReturnValue(true);
      }
   }

   @Inject(
      method = {"attack"},
      at = {@At("HEAD")}
   )
   public void attack(Entity target, CallbackInfo info) {
      if (PhantomClient.initialized) {
         PhantomClient.onAttackHook((Player)(Object)this, target);
      }
   }

   @Overwrite
   public GameProfile getGameProfile() {
      return (GameProfile)NameProtectModule.jue(this.gameProfile);
   }

   @Overwrite
   public Component getName() {
      return Component.literal(this.getGameProfile().name());
   }

   @Overwrite
   public String getScoreboardName() {
      return this.getGameProfile().name();
   }

   @Override
   public GameProfile getOrginalProfile() {
      return this.gameProfile;
   }

   @Overwrite
   public double blockInteractionRange() {
      double val = ((Player)(Object)this).getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
      return PhantomClient.initialized && (Object)this instanceof LocalPlayer ? PhantomClient.eventManager.dispatch(MutableDoubleEvent.iwi(val)).fgb() : val;
   }

   @Overwrite
   public double entityInteractionRange() {
      double val = ((Player)(Object)this).getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
      return PhantomClient.initialized && (Object)this instanceof LocalPlayer ? PhantomClient.eventManager.dispatch(MutableDoubleEvent.iwi(val)).fgb() : val;
   }
}
