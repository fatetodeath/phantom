package me.phantom.mixin.render;

import me.phantom.util.mc.PoolAccessor;

import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.GameRenderer;
import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({GameRenderer.class})
public abstract class GameRendererPoolMixin implements PoolAccessor {
   @Shadow
   @Final
   private CrossFrameResourcePool resourcePool;

   @Override
   public CrossFrameResourcePool getPool() {
      return this.resourcePool;
   }
}
