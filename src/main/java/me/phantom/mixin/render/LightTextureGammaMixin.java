package me.phantom.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.phantom.core.PhantomClient;
import me.phantom.event.misc.ValueOverrideEvent;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightmapRenderStateExtractor.class)
public class LightTextureGammaMixin {

   @ModifyExpressionValue(
           method = "extract",
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;",
                   ordinal = 1
           )
   )
   private Object getGammaLevel(Object value) {
      return PhantomClient.initialized
              ? PhantomClient.eventManager
              .dispatch(ValueOverrideEvent.fge(value))
              .getValue()
              : value;
   }
}
