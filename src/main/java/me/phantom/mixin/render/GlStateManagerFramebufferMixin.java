package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.FramebufferBindEvent;

import com.mojang.blaze3d.opengl.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GlStateManager.class})
public class GlStateManagerFramebufferMixin {
   @Inject(
      method = {"_glBindFramebuffer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void _glBindFramebuffer(int target, int buffer, CallbackInfo info) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(FramebufferBindEvent.ixo(buffer)).isCancelled()) {
         info.cancel();
      }
   }
}
