package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;

import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({OutlineBufferSource.class})
public abstract class OutlineBufferSourceMixin {

   @Shadow
   @Final
   private MultiBufferSource.BufferSource outlineBufferSource;

   @Inject(
      method = {"getBuffer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getBuffer(RenderType layer, CallbackInfoReturnable<VertexConsumer> cir) {
      if (PhantomClient.effectRenderActive) {
         cir.setReturnValue(this.outlineBufferSource.getBuffer(layer));
      }
   }
}
