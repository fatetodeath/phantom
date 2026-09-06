package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.DeltaTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GameRenderer.class})
public abstract class GameRendererOverlayMixin {
    @Inject(
       method = {"render(Lnet/minecraft/client/DeltaTracker;Z)V"},
       at = {@At("TAIL")}
    )
    private void phantom$renderOverlay(DeltaTracker deltaTracker, boolean tickCounter, CallbackInfo ci) {
       if (PhantomClient.initialized) {
          net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
          if (mc.level != null || mc.screen != null) {
             me.phantom.render.GlStateSnapshot state = me.phantom.render.GlStateSnapshot.capture();
              try {
                  PhantomClient.uiManager.upfx2();
              } catch (Throwable t) {
                 t.printStackTrace();
              } finally {
                state.restore();
              }
          }
       }
    }
}
