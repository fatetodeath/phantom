package me.phantom.mixin;

import me.phantom.core.PhantomClient;
import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Window.class})
public class WindowGlErrorLogMixin {
   @Inject(
      method = {"defaultErrorCallback(IJ)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void phantom$suppressGlErrorSpam(int var1, long var2, CallbackInfo var4) {
      if (!PhantomClient.isDebugBuild) {
         var4.cancel();
      }
   }
}
