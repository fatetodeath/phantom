package me.phantom.mixin.input;

import me.phantom.core.PhantomClient;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Mouse.class})
public class MouseScrollMixin {
   @Inject(
      method = {"onMouseScroll(JDD)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMouseScroll(CallbackInfo info, @Local(ordinal = 1) double vertical) {
      if (PhantomClient.initialized && PhantomClient.uiManager.xki((float)vertical)) {
         info.cancel();
      }
   }
}
