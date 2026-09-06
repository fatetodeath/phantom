package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.WeatherGradientEvent;

import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({WeatherEffectRenderer.class})
public class WeatherEffectRendererNoRainMixin {
   @Redirect(
      method = {"render", "tickRainParticles"},
      at = @At(
         value = "INVOKE",
          target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"
      ),
      require = 0
   )
   private static float getRainGradient(ClientLevel world, float delta) {
      return PhantomClient.initialized && PhantomClient.eventManager.dispatch(WeatherGradientEvent.cz()).isCancelled() ? 0.0F : world.getRainLevel(delta);
   }

   @Redirect(
      method = {"render", "tickRainParticles"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/Level;getRainLevel(F)F"
      ),
      require = 0
   )
   private static float getRainGradient2(Level world, float delta) {
      return PhantomClient.initialized && PhantomClient.eventManager.dispatch(WeatherGradientEvent.cz()).isCancelled() ? 0.0F : world.getRainLevel(delta);
   }
}
