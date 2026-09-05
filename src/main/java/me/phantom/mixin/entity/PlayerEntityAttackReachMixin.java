package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.misc.ClipAtLedgeEvent;
import me.phantom.event.misc.MutableDoubleEvent;
import me.phantom.module.misc.NameProtectModule;
import me.phantom.util.mc.OriginalProfileProvider;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerEntity.class})
public abstract class PlayerEntityAttackReachMixin implements OriginalProfileProvider {
   @Shadow
   private GameProfile gameProfile;

   @Inject(
      method = {"clipAtLedge()Z"},
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
         PhantomClient.onAttackHook((PlayerEntity)(Object)this, target);
      }
   }

   @Overwrite
   public GameProfile getGameProfile() {
      return (GameProfile)NameProtectModule.jue(this.gameProfile);
   }

   @Overwrite
   public Text getName() {
      return Text.literal(this.getGameProfile().getName());
   }

   @Overwrite
   public String getNameForScoreboard() {
      return this.getGameProfile().getName();
   }

   @Override
   public GameProfile getOrginalProfile() {
      return this.gameProfile;
   }

   @Overwrite
   public double getBlockInteractionRange() {
      double val = ((PlayerEntity)(Object)this).getAttributeValue(EntityAttributes.BLOCK_INTERACTION_RANGE);
      return PhantomClient.initialized && (Object)this instanceof ClientPlayerEntity ? PhantomClient.eventManager.dispatch(MutableDoubleEvent.iwi(val)).fgb() : val;
   }

   @Overwrite
   public double getEntityInteractionRange() {
      double val = ((PlayerEntity)(Object)this).getAttributeValue(EntityAttributes.ENTITY_INTERACTION_RANGE);
      return PhantomClient.initialized && (Object)this instanceof ClientPlayerEntity ? PhantomClient.eventManager.dispatch(MutableDoubleEvent.iwi(val)).fgb() : val;
   }
}
