package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;

import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({OutlineVertexConsumerProvider.class})
public class OutlineVertexConsumerProviderMixin {
   @Redirect(
      method = {"getBuffer"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/VertexConsumers;union(Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/render/VertexConsumer;)Lnet/minecraft/client/render/VertexConsumer;"
      )
   )
   public VertexConsumer union(VertexConsumer a, VertexConsumer b) {
      return PhantomClient.effectRenderActive ? a : VertexConsumers.union(a, b);
   }
}
