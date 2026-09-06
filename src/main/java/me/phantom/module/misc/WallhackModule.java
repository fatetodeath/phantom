package me.phantom.module.misc;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.PreEntityRenderEvent;
import me.phantom.event.render.WorldRenderSceneEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.visuals.EspModule;
import me.phantom.render.NanoVGFramebuffer;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public class WallhackModule extends AbstractEspFeature {
   public NanoVGFramebuffer dtg = new NanoVGFramebuffer();
   public boolean pmu;

   public WallhackModule(EspModule var1) {
      super(GlyphTextUtil.encode("Wallhack"), var1);
   }

   @Override
   public void wji(PreEntityRenderEvent var1) {
      if (this.pmu) {
         var1.setCancelled(true);
      }
   }

   @Override
   public boolean nsq() {
      return this.pmu;
   }

   @Override
   public void kow(WorldRenderSceneEvent var1) {
      GuiRenderContext var2 = PhantomClient.uiManager.getContext();
      if (var2.framebufferWidth > 0) {
         Minecraft var3 = Minecraft.getInstance();
         EntityRenderDispatcher var4 = var3.getEntityRenderDispatcher();
         float var5 = var1.getTickCounter().getGameTimeDeltaPartialTick(false);
         Vec3 var6 = var1.getCamera().position();
         RenderTarget var7 = var3.getMainRenderTarget();

         try {
            this.dtg.ysf(var7.width, var7.height);

BufferSource var8 = Minecraft.getInstance().renderBuffers().bufferSource();
            this.pmu = true;

            for (Entity var10 : this.vsr.ffs(PhantomClient.uiManager.getContext())) {
               boolean var11 = var10.isCurrentlyGlowing();
               var10.setGlowingTag(false);
               Vec3 var12 = GameInteractionUtil.getLerpedPos(var10, var5).subtract(var6);
               // TODO: EntityRenderManager.render() API changed in 1.21.11
               var10.setGlowingTag(var11);
            }

            var8.endBatch();
         } finally {
            this.pmu = false;
            this.dtg.ipg();

}
      }
   }

   @Override
   public void lfc(GuiRenderContext var1) {
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRect(var1.nvg, 0.0F, 0.0F, var1.framebufferWidth, var1.framebufferHeight);
      NVGPaint var2 = NanoVG.nvgImagePattern(var1.nvg, 0.0F, var1.framebufferHeight, var1.framebufferWidth, -var1.framebufferHeight, 0.0F, this.dtg.lwe(), 1.0F, GuiRenderContext.paintScratch);
      NanoVG.nvgFillPaint(var1.nvg, var2);
      NanoVG.nvgFill(var1.nvg);
   }
}
