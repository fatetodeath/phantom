package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.entity.PositionEvent;

import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Camera.class})
public class CameraFreecamMixin {
   @Shadow
   public Vec3d pos;
   @Shadow
   @Final
   public Mutable blockPos;

   @Overwrite(
      aliases = {"setPos"}
   )
   public void setPos(Vec3d in) {
      this.pos = PhantomClient.initialized ? PhantomClient.eventManager.dispatch(PositionEvent.jv(in)).cl() : in;
      this.blockPos.set(this.pos.x, this.pos.y, this.pos.z);
   }
}
