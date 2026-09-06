package me.phantom.mixin.world;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.WeatherGradientEvent;

import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Level.class})
public abstract class LevelWeatherMixin {
   @Final
   @Shadow
   public WritableLevelData levelData;

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
      method = {"updateSkyBrightness", "getThunderLevel"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/Level;getRainLevel(F)F"
      )
   )
   private static float getRainGradient(Level world, float delta) {
      return PhantomClient.initialized && PhantomClient.eventManager.dispatch(WeatherGradientEvent.cz()).isCancelled() ? 0.0F : world.getRainLevel(delta);
   }
}
