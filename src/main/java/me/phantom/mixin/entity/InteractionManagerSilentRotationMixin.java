package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.misc.SignalEvent;
import me.phantom.util.mc.BlockBreakingCooldownAccessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MultiPlayerGameMode.class})
public class InteractionManagerSilentRotationMixin implements BlockBreakingCooldownAccessor {
   private float silentYaw;
   private float silentPitch;
   @Shadow
   private int destroyDelay;

   @Inject(
      method = {"useItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"},
      at = {@At("HEAD")}
   )
   public void preSendMovementPackets(CallbackInfoReturnable<InteractionResult> info) {
      if (PhantomClient.initialized) {
         Player playerEntity = Minecraft.getInstance().player;
         this.silentYaw = playerEntity.getYRot();
         this.silentPitch = playerEntity.getXRot();
         PhantomClient.eventManager.dispatch(SignalEvent.zr());
      }
   }

   @Inject(
      method = {"useItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"},
      at = {@At("RETURN")}
   )
   public void postSendMovementPackets(CallbackInfoReturnable<InteractionResult> info) {
      if (PhantomClient.initialized) {
         Player playerEntity = Minecraft.getInstance().player;
         playerEntity.setYRot(this.silentYaw);
         playerEntity.setXRot(this.silentPitch);
      }
   }

   @Override
   public void setBlockBreakingCooldown(int blockBreakingCooldown) {
      this.destroyDelay = blockBreakingCooldown;
   }
}
