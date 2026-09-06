package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.FovMultiplierEvent;
import me.phantom.event.packet.SimplePacketEvent;
import me.phantom.util.mc.PoolAccessor;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Camera.class})
public abstract class GameRendererCameraMixin {
   @Shadow
   private float fovModifier;

   @Inject(
      method = {"tickFov()V"},
      at = {@At("RETURN")}
   )
   public void onTickFov(CallbackInfo info) {
      if (PhantomClient.initialized) {
           this.fovModifier = PhantomClient.eventManager.dispatch(FovMultiplierEvent.vp(this.fovModifier)).getMultiplier();
      }
   }
}
