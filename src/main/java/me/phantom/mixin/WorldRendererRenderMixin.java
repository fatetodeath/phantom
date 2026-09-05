package me.phantom.mixin;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.WeatherGradientEvent;
import me.phantom.event.entity.StatusEffectEvent;
import me.phantom.util.mc.WorldRendererDuck;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.BlockBreakingInfo;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({WorldRenderer.class})
public abstract class WorldRendererRenderMixin implements WorldRendererDuck {
   @Redirect(
      method = {"method_62215"},
      at = @At(
         value = "INVOKE",
         target = "net/minecraft/client/world/ClientWorld.getRainGradient(F)F"
      )
   )
   private static float getRainGradient(ClientWorld world, float delta) {
      return PhantomClient.initialized && PhantomClient.eventManager.dispatch(WeatherGradientEvent.cz()).isCancelled() ? 0.0F : world.getRainGradient(delta);
   }

   @Inject(
      method = {"render"},
      at = {@At("RETURN")}
   )
   public void render(
      ObjectAllocator allocator,
      RenderTickCounter tickCounter,
      boolean renderBlockOutline,
      Camera camera,
      Matrix4f positionMatrix,
      Matrix4f projectionMatrix,
      CallbackInfo info
   ) {
      if (PhantomClient.initialized) {
         MatrixStack matrixStack = new MatrixStack();
         matrixStack.peek().getPositionMatrix().set(positionMatrix);
         matrixStack.peek().getNormalMatrix().set(projectionMatrix);
         PhantomClient.uiManager.suw(matrixStack, tickCounter.getTickProgress(false));
      }
   }

   @Inject(
      method = {"hasBlindnessOrDarkness(Lnet/minecraft/client/render/Camera;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void hasBlindnessOrDarkness(Camera camera, CallbackInfoReturnable<Boolean> callback) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(StatusEffectEvent.ofv()).isCancelled()) {
         callback.setReturnValue(false);
      }
   }

   @Accessor("blockBreakingInfos")
   @Override
   public abstract Int2ObjectMap<BlockBreakingInfo> getBlockBreakingInfos();

   @Accessor("ticks")
   @Override
   public abstract int getTicks();

   @Accessor("capturedFrustum")
   @Override
   public abstract Frustum getFrustum();
}
