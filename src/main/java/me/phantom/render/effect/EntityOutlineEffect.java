package me.phantom.render.effect;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.PreEntityRenderEvent;
import me.phantom.event.render.WorldRenderSceneEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.visuals.EspModule;
import me.phantom.render.NanoVGFramebuffer;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public class EntityOutlineEffect extends AbstractEspFeature {
   public final ColorSetting zti = new ColorSetting(this.vsr, GlyphTextUtil.encode("color_outline_new"), 0.35F, 0.9F, 0.9F);
   public final IntegerSliderSetting ump = new IntegerSliderSetting(this.vsr, GlyphTextUtil.encode("outline_glow_new"), 0, 1, 6);
   public NanoVGFramebuffer dtg = new NanoVGFramebuffer();
   public NanoVGFramebuffer vyg = new NanoVGFramebuffer();
   public BlurShaderProgram wom = new BlurShaderProgram();
   public boolean jvq;
   public boolean pmu;

   public EntityOutlineEffect(EspModule var1) {
      super(GlyphTextUtil.encode("Outline"), var1);
   }

   @Override
   public void wji(PreEntityRenderEvent var1) {
      if (this.pmu || this.jvq) {
         var1.setCancelled(true);
      }
   }

   @Override
   public boolean nsq() {
      return this.pmu || this.jvq;
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
            var8.draw();
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

         try {
            PhantomClient.effectRenderActive = true;
            this.vyg.ysf(var7.viewportWidth, var7.viewportHeight);
            var4.setRenderShadows(false);
            this.jvq = true;
            OutlineVertexConsumerProvider var21 = MinecraftClient.getInstance().getBufferBuilders().getOutlineVertexConsumers();

            for (Entity var23 : this.vsr.ffs(PhantomClient.uiManager.getContext())) {
               boolean var24 = var23.isGlowing();
               var23.setGlowing(false);
               Color var25 = this.ypg(var23).nqq();
               var21.setColor(var25.getRed(), var25.getGreen(), var25.getBlue(), 255);
               Vec3d var13 = GameInteractionUtil.getLerpedPos(var23, var5).subtract(var6);
               var4.render(var23, var13.x, var13.y, var13.z, var5, var1.getMatrices(), var21, 14680064);
               var23.setGlowing(var24);
            }

            var21.draw();
         } finally {
            PhantomClient.effectRenderActive = false;
            this.jvq = false;
            this.vyg.ipg();
            var4.setRenderShadows(true);
         }
      }
   }

   @Override
   public void lfc(GuiRenderContext var1) {
      float var2 = var1.framebufferWidth;
      float var3 = var1.framebufferHeight;
      int var4 = (Integer)this.ump.getValue() + 2;
      float var5 = (float)(4.0 - Math.sqrt(var4));
      float var6 = 2.0F;
      float var7 = 0.15F;

      for (int var8 = -var4; var8 <= var4; var8++) {
         for (int var9 = -var4; var9 <= var4; var9++) {
            if (var8 * var8 + var9 * var9 < var4 * var4) {
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRect(var1.nvg, var8 * var5, var9 * var5, var2, var3);
               NVGPaint var10 = NanoVG.nvgImagePattern(var1.nvg, var8 * var5, var9 * var5 + var3, var2, -var3, 0.0F, this.vyg.lwe(), 1.0F, GuiRenderContext.paintScratch);
               var10.innerColor(GuiRenderContext.colorScratch.r(var6).g(var6).b(var6).a(var7));
               NanoVG.nvgFillPaint(var1.nvg, var10);
               NanoVG.nvgFill(var1.nvg);
            }
         }
      }

      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRect(var1.nvg, 0.0F, 0.0F, var1.framebufferWidth, var1.framebufferHeight);
      NVGPaint var11 = NanoVG.nvgImagePattern(var1.nvg, 0.0F, var1.framebufferHeight, var1.framebufferWidth, -var1.framebufferHeight, 0.0F, this.dtg.lwe(), 1.0F, GuiRenderContext.paintScratch);
      NanoVG.nvgFillPaint(var1.nvg, var11);
      NanoVG.nvgFill(var1.nvg);
   }
}
