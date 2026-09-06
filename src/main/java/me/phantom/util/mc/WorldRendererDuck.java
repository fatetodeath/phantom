package me.phantom.util.mc;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.server.level.BlockDestructionProgress;

public interface WorldRendererDuck {
   Int2ObjectMap<BlockDestructionProgress> getBlockBreakingInfos();

   int getTicks();

   default Frustum getFrustum() {
      return Minecraft.getInstance().gameRenderer.getMainCamera().getCapturedFrustum();
   }
}
