package me.phantom.mixin;

import me.phantom.core.PhantomClient;
import com.mojang.blaze3d.opengl.GlDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GlDebug.class})
public class GlDebugMixin {
   @Inject(
      method = {"printDebugLog(IIIIIJJ)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void phantom$suppressGlDebugSpam(int var1, int var2, int var3, int var4, int var5, long var6, long var8, CallbackInfo var10) {
      if (!PhantomClient.isDebugBuild) {
         var10.cancel();
      }
   }
}
