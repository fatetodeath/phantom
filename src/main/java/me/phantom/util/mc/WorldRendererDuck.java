package me.phantom.util.mc;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor.FramebufferSet;
import net.minecraft.client.render.Frustum;
import net.minecraft.entity.player.BlockBreakingInfo;

public interface WorldRendererDuck {
   Int2ObjectMap<BlockBreakingInfo> getBlockBreakingInfos();

   int getTicks();

   Frustum getFrustum();

   FramebufferSet getFramebufferSet();

   void kd(Framebuffer var1);
}
