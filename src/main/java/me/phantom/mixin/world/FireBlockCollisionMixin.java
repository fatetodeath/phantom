package me.phantom.mixin.world;

import me.phantom.core.PhantomClient;
import me.phantom.event.world.BlockCollisionShapeEvent;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({FireBlock.class})
public class FireBlockCollisionMixin extends Block {
   public FireBlockCollisionMixin(Settings settings) {
      super(settings);
   }

   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      VoxelShape before = super.getCollisionShape(state, world, pos, context);
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(BlockCollisionShapeEvent.fz(pos, this, before)).nss() : before;
   }
}
