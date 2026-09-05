package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.CrosshairTargetUpdateEvent;
import me.phantom.event.render.FovMultiplierEvent;
import me.phantom.event.packet.SimplePacketEvent;
import me.phantom.util.mc.PoolAccessor;

import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Pool;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GameRenderer.class})
public abstract class GameRendererCameraMixin implements PoolAccessor {
   @Shadow
   @Final
   private BufferBuilderStorage buffers;
   @Shadow
   private float fovMultiplier;
   @Shadow
   private Pool pool;

   @Inject(
      method = {"updateFovMultiplier()V"},
      at = {@At("RETURN")}
   )
   public void render(CallbackInfo info) {
      if (PhantomClient.initialized) {
           this.fovMultiplier = PhantomClient.eventManager.dispatch(FovMultiplierEvent.vp(this.fovMultiplier)).getMultiplier();
      }
   }

   @Inject(
      method = {"updateCrosshairTarget(F)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void updateCrosshairTarget(float tickDelta, CallbackInfo info) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(CrosshairTargetUpdateEvent.nxk(tickDelta)).isCancelled()) {
         info.cancel();
      }
   }

   @Inject(
      method = {"tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void tiltViewWhenHurt(MatrixStack matrix, float tickDelta, CallbackInfo info) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(SimplePacketEvent.xet(tickDelta)).isCancelled()) {
         info.cancel();
      }
   }

   @Override
   public Pool getPool() {
      return this.pool;
   }
}
