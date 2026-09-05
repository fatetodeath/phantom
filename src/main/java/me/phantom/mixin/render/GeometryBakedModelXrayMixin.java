package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import java.util.Arrays;
import java.util.List;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.GeometryBakedModel;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GeometryBakedModel.class})
public class GeometryBakedModelXrayMixin {
   private static List<BakedQuad> EMPTY = Arrays.asList();
   private static BaseModule xrayCache;

   @Inject(
      at = {@At("HEAD")},
      method = {"getQuads(Lnet/minecraft/util/math/Direction;)Ljava/util/List;"},
      cancellable = true
   )
   private void getQuads(Direction face, CallbackInfoReturnable<List<BakedQuad>> callback) {
      if (PhantomClient.initialized && face == null) {
         if (xrayCache == null) {
            xrayCache = PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("XRay"));
         }

         if (xrayCache != null && xrayCache.isEnabled()) {
            callback.setReturnValue(EMPTY);
         }
      }
   }
}
