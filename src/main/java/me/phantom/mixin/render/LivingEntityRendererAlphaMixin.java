package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.RenderAlphaEvent;

import java.awt.Color;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({LivingEntityRenderer.class})
public class LivingEntityRendererAlphaMixin {
   @ModifyArg(
      method = {"submit"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"
      ),
      index = 4,
      require = 0
   )
   public int modifyAlpha(int rgb) {
      if (!PhantomClient.initialized) {
         return rgb;
      } else {
         Color color = new Color(rgb, true);
         float alpha = PhantomClient.eventManager.dispatch(RenderAlphaEvent.ao(color.getAlpha() / 255.0F, false)).oqw();
         return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(alpha * 255.0F)).getRGB();
      }
   }

   @ModifyArg(
      method = {"submit"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;ZZZ)Lnet/minecraft/client/renderer/rendertype/RenderType;"
      ),
      index = 2,
      require = 0
   )
   public boolean modifyTranscluent(boolean transcluent) {
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(RenderAlphaEvent.ao(0.0F, transcluent)).swj() : transcluent;
   }
}
