package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.WeatherGradientEvent;

import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({WeatherRendering.class})
public class WeatherRenderingNoRainMixin {
   @Redirect(
      method = {"renderPrecipitation", "addParticlesAndSound"},
      at = @At(
         value = "INVOKE",
         target = "net/minecraft/client/world/ClientWorld.getRainGradient(F)F"
      ),
      require = 0
   )
   private static float getRainGradient(ClientWorld world, float delta) {
      return PhantomClient.initialized && PhantomClient.eventManager.dispatch(WeatherGradientEvent.cz()).isCancelled() ? 0.0F : world.getRainGradient(delta);
   }

   @Redirect(
      method = {"renderPrecipitation", "addParticlesAndSound"},
      at = @At(
         value = "INVOKE",
         target = "net/minecraft/world/World.getRainGradient(F)F"
      ),
      require = 0
   )
   private static float getRainGradient2(World world, float delta) {
      return PhantomClient.initialized && PhantomClient.eventManager.dispatch(WeatherGradientEvent.cz()).isCancelled() ? 0.0F : world.getRainGradient(delta);
   }
}
