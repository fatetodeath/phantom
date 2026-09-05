package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.misc.SignalEvent;
import me.phantom.event.entity.StatusEffectEvent;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPlayerEntity.class})
public abstract class ClientPlayerSilentRotationMixin extends AbstractClientPlayerEntity {
   @Shadow
   public float nauseaIntensity;
   @Shadow
   public float lastNauseaIntensity;
   private float silentYaw;
   private float silentPitch;

   public ClientPlayerSilentRotationMixin(ClientWorld world, GameProfile profile) {
      super(world, profile);
   }

   @Inject(
      method = {"tickNausea(Z)V"},
      at = {@At("RETURN")}
   )
   public void tickNausea(CallbackInfo info) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(StatusEffectEvent.ofv()).isCancelled()) {
         this.nauseaIntensity = 0.0F;
         this.lastNauseaIntensity = 0.0F;
      }
   }

   @Inject(
      method = {"sendMovementPackets()V"},
      at = {@At("HEAD")}
   )
   public void preSendMovementPackets(CallbackInfo info) {
      if (PhantomClient.initialized) {
         this.silentYaw = this.getYaw();
         this.silentPitch = this.getPitch();
         PhantomClient.eventManager.dispatch(SignalEvent.zr());
      }
   }

   @Inject(
      method = {"sendMovementPackets()V"},
      at = {@At("RETURN")}
   )
   public void postSendMovementPackets(CallbackInfo info) {
      if (PhantomClient.initialized) {
         this.setYaw(this.silentYaw);
         this.setPitch(this.silentPitch);
      }
   }
}
