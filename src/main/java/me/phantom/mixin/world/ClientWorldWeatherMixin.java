package me.phantom.mixin.world;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.BlockParticleEvent;
import me.phantom.util.mc.PendingUpdateManagerProvider;

import net.minecraft.block.Block;
import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientWorld.class})
public abstract class ClientWorldWeatherMixin implements PendingUpdateManagerProvider {
   @Accessor("pendingUpdateManager")
   @Override
   public abstract PendingUpdateManager getPendingUpdateManager();

   @Inject(
      at = {@At("HEAD")},
      method = {"getBlockParticle"},
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
