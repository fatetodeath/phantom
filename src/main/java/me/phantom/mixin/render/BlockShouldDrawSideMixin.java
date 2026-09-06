package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.world.ShouldDrawBlockSideEvent;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class})
public class BlockShouldDrawSideMixin {
   @Inject(
      at = {@At("HEAD")},
      method = {"shouldRenderFace(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z"},
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
