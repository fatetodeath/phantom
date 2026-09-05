package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.world.ChunkOcclusionBlockEvent;

import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChunkOcclusionDataBuilder.class})
public class ChunkOcclusionXrayMixin {
   @Inject(
      at = {@At("HEAD")},
      method = {"markClosed(Lnet/minecraft/util/math/BlockPos;)V"},
      cancellable = true
   )
   public void markClosed(BlockPos pos, CallbackInfo ci) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(ChunkOcclusionBlockEvent.py(pos)).isCancelled()) {
         ci.cancel();
      }
   }
}
