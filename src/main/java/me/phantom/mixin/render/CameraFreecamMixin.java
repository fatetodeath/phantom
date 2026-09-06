package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.PositionEvent;

import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos.MutableBlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Camera.class})
public class CameraFreecamMixin {
   @Shadow
   public Vec3 position;
   @Shadow
   @Final
   public MutableBlockPos blockPosition;

   @Overwrite(
      aliases = {"setPos"}
   )
   public void setPosition(Vec3 in) {
      this.position = PhantomClient.initialized ? PhantomClient.eventManager.dispatch(PositionEvent.jv(in)).cl() : in;
      this.blockPosition.set(this.position.x, this.position.y, this.position.z);
   }
}
