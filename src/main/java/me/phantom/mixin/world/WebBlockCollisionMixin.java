package me.phantom.mixin.world;

import me.phantom.core.PhantomClient;
import me.phantom.event.world.BlockCollisionShapeEvent;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({WebBlock.class})
public class WebBlockCollisionMixin extends Block {
   public WebBlockCollisionMixin(Properties settings) {
      super(settings);
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      VoxelShape before = super.getCollisionShape(state, world, pos, context);
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(BlockCollisionShapeEvent.fz(pos, this, before)).nss() : before;
   }
}
