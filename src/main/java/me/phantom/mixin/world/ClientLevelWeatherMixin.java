package me.phantom.mixin.world;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.BlockParticleEvent;
import me.phantom.util.mc.PendingUpdateManagerProvider;

import net.minecraft.world.level.block.Block;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientLevel.class})
public abstract class ClientLevelWeatherMixin implements PendingUpdateManagerProvider {
   @Accessor("blockStatePredictionHandler")
   @Override
   public abstract BlockStatePredictionHandler getPendingUpdateManager();

   @Inject(
      at = {@At("HEAD")},
      method = {"getMarkerParticleTarget"},
      cancellable = true
   )
   private void getBlockParticle(CallbackInfoReturnable<Block> callback) {
      if (PhantomClient.initialized) {
         Block block = PhantomClient.eventManager.dispatch(BlockParticleEvent.xeu()).kxf();
         if (block != null) {
            callback.setReturnValue(block);
         }
      }
   }
}
