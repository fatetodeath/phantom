package me.phantom.mixin.world;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.WeatherGradientEvent;

import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({World.class})
public abstract class WorldWeatherMixin {
   @Final
   @Shadow
   public MutableWorldProperties properties;

   @Inject(
      method = {"isThundering()Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isThundering(CallbackInfoReturnable<Boolean> info) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(WeatherGradientEvent.cz()).isCancelled()) {
         info.setReturnValue(false);
      }
   }

   @Redirect(
      method = {"calculateAmbientDarkness", "getThunderGradient"},
      at = @At(
         value = "INVOKE",
         target = "net/minecraft/world/World.getRainGradient(F)F"
      )
   )
   private static float getRainGradient(World world, float delta) {
      return PhantomClient.initialized && PhantomClient.eventManager.dispatch(WeatherGradientEvent.cz()).isCancelled() ? 0.0F : world.getRainGradient(delta);
   }
}
