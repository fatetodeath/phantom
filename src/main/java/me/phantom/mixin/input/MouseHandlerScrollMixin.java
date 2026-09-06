package me.phantom.mixin.input;

import me.phantom.core.PhantomClient;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MouseHandler.class})
public class MouseHandlerScrollMixin {
   @Inject(
      method = {"onScroll(JDD)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMouseScroll(CallbackInfo info, @Local(ordinal = 1) double vertical) {
      if (PhantomClient.initialized && PhantomClient.uiManager.xki((float)vertical)) {
         info.cancel();
      }
   }
}
