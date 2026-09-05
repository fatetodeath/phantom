package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.PreEntityRenderEvent;

import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public class EntityRendererNametagMixin {
   @Inject(
      method = {"renderLabelIfPresent"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void renderLabelIfPresent(CallbackInfo info) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(PreEntityRenderEvent.qt(PhantomClient.renderedEntity)).isCancelled()) {
         info.cancel();
      }
   }
}
