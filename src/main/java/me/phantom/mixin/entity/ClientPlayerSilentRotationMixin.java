package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.misc.SignalEvent;
import me.phantom.event.entity.StatusEffectEvent;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LocalPlayer.class})
public abstract class ClientPlayerSilentRotationMixin extends AbstractClientPlayer {
   @Shadow
   public float portalEffectIntensity;
   @Shadow
   public float oPortalEffectIntensity;
   private float silentYaw;
   private float silentPitch;

   public ClientPlayerSilentRotationMixin(ClientLevel world, GameProfile profile) {
      super(world, profile);
   }

   @Inject(
      method = {"handlePortalTransitionEffect(Z)V"},
      at = {@At("RETURN")}
   )
   public void tickNausea(CallbackInfo info) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(StatusEffectEvent.ofv()).isCancelled()) {
         this.portalEffectIntensity = 0.0F;
         this.oPortalEffectIntensity = 0.0F;
      }
   }

   @Inject(
      method = {"sendPosition()V"},
      at = {@At("HEAD")}
   )
   public void preSendMovementPackets(CallbackInfo info) {
      if (PhantomClient.initialized) {
         this.silentYaw = this.getYRot();
         this.silentPitch = this.getXRot();
         PhantomClient.eventManager.dispatch(SignalEvent.zr());
      }
   }

   @Inject(
      method = {"sendPosition()V"},
      at = {@At("RETURN")}
   )
   public void postSendMovementPackets(CallbackInfo info) {
      if (PhantomClient.initialized) {
         this.setYRot(this.silentYaw);
         this.setXRot(this.silentPitch);
      }
   }
}
