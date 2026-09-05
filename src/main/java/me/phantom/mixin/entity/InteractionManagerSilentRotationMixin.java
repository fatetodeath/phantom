package me.phantom.mixin.entity;

import me.phantom.core.PhantomClient;
import me.phantom.event.misc.SignalEvent;
import me.phantom.util.mc.BlockBreakingCooldownAccessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientPlayerInteractionManager.class})
public class InteractionManagerSilentRotationMixin implements BlockBreakingCooldownAccessor {
   private float silentYaw;
   private float silentPitch;
   @Shadow
   private int blockBreakingCooldown;

   @Inject(
      method = {"interactItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")}
   )
   public void preSendMovementPackets(CallbackInfoReturnable<ActionResult> info) {
      if (PhantomClient.initialized) {
         PlayerEntity playerEntity = MinecraftClient.getInstance().player;
         this.silentYaw = playerEntity.getYaw();
         this.silentPitch = playerEntity.getPitch();
         PhantomClient.eventManager.dispatch(SignalEvent.zr());
      }
   }

   @Inject(
      method = {"interactItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("RETURN")}
   )
   public void postSendMovementPackets(CallbackInfoReturnable<ActionResult> info) {
      if (PhantomClient.initialized) {
         PlayerEntity playerEntity = MinecraftClient.getInstance().player;
         playerEntity.setYaw(this.silentYaw);
         playerEntity.setPitch(this.silentPitch);
      }
   }

   @Override
   public void setBlockBreakingCooldown(int blockBreakingCooldown) {
      this.blockBreakingCooldown = blockBreakingCooldown;
   }
}
