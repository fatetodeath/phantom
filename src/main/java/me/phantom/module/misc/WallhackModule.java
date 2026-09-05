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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
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
         MinecraftClient var3 = MinecraftClient.getInstance();
         EntityRenderDispatcher var4 = var3.getEntityRenderDispatcher();
         float var5 = var1.getTickCounter().getTickProgress(false);
         Vec3d var6 = var1.getCamera().getPos();
         Framebuffer var7 = var3.getFramebuffer();

         try {
            this.dtg.ysf(var7.viewportWidth, var7.viewportHeight);
            var4.setRenderShadows(false);
            Immediate var8 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
            this.pmu = true;

            for (Entity var10 : this.vsr.ffs(PhantomClient.uiManager.getContext())) {
               boolean var11 = var10.isGlowing();
               var10.setGlowing(false);
               Vec3d var12 = GameInteractionUtil.getLerpedPos(var10, var5).subtract(var6);
               var4.render(var10, var12.x, var12.y, var12.z, var5, var1.getMatrices(), var8, 14680064);
               var10.setGlowing(var11);
            }

            var8.draw();
         } finally {
            this.pmu = false;
            this.dtg.ipg();
            var4.setRenderShadows(true);
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
