package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;

import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({TextureManager.class})
public class TextureManagerTextureMixin {
   @Inject(
      at = {@At("HEAD")},
      method = {"getTexture"},
      cancellable = true
   )
   public void getTexture(Identifier isHoveredF, CallbackInfoReturnable<AbstractTexture> callback) {
      if (PhantomClient.initialized) {
         AbstractTexture tex = PhantomClient.uiManager.ucd(isHoveredF);
         if (tex != null) {
            callback.setReturnValue(tex);
         }
      }
   }
}
