package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.world.ShouldDrawBlockSideEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class})
public class BlockShouldDrawSideMixin {
   @Inject(
      at = {@At("HEAD")},
      method = {"shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z"},
      cancellable = true
   )
   private static void shouldDrawSide(BlockState state, BlockState otherState, Direction side, CallbackInfoReturnable<Boolean> callback) {
      if (PhantomClient.initialized) {
         ShouldDrawBlockSideEvent blockSide = PhantomClient.eventManager.dispatch(ShouldDrawBlockSideEvent.td(state));
         if (blockSide.isCancelled()) {
            callback.setReturnValue(blockSide.dai());
         }
      }
   }
}
