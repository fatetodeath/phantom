package me.phantom.mixin;

import me.phantom.core.PhantomClient;
import me.phantom.util.mc.WorldRendererDuck;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.server.level.BlockDestructionProgress;
import org.joml.Matrix4fc;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import org.joml.Vector4f;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LevelRenderer.class})
public abstract class LevelRendererRenderMixin implements WorldRendererDuck {
    @Inject(
       method = {"renderLevel"},
       at = {@At("RETURN")}
    )
    public void render(
            GraphicsResourceAllocator allocator,
            DeltaTracker tickCounter,
            boolean renderBlockOutline,
            CameraRenderState cameraState,
            Matrix4fc positionMatrix,
            GpuBufferSlice projectionBuffer,
            Vector4f relevantVector,
            boolean flag,
            ChunkSectionsToRender sections,
            CallbackInfo info
     ) {
        if (PhantomClient.initialized) {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.player != null && mc.level != null) {
                PoseStack poseStack = new PoseStack();
                float partialTick = tickCounter.getGameTimeDeltaPartialTick(false);
                PhantomClient.uiManager.suw(poseStack, partialTick);
            }
        }
     }

   @Accessor("destroyingBlocks")
   @Override
   public abstract Int2ObjectMap<BlockDestructionProgress> getBlockBreakingInfos();

   @Accessor("ticks")
   @Override
   public abstract int getTicks();
}
