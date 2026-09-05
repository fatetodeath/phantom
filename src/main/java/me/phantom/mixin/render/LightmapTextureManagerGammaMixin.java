package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.misc.ValueOverrideEvent;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({LightmapTextureManager.class})
public class LightmapTextureManagerGammaMixin {
   @ModifyExpressionValue(
      method = {"update(F)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;",
         ordinal = 1
      )}
   )
   private Object getGammaLevel(Object value) {
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(ValueOverrideEvent.fge(value)).getValue() : value;
   }
}
