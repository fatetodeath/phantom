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
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
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
         Minecraft var3 = Minecraft.getInstance();
         float var5 = var1.getTickCounter().getGameTimeDeltaPartialTick(false);
         Vec3 var6 = var1.getCamera().position();
         RenderTarget var7 = var3.getMainRenderTarget();

         try {
            this.dtg.ysf(var7.width, var7.height);

            this.pmu = true;

            // TODO: Entity outline depth pass - render API changed in MC 26.1
            // EntityRenderManager.render() now requires EntityRenderState + OrderedRenderCommandQueue
            // Entity re-rendering temporarily disabled

            this.pmu = false;
            this.dtg.ipg();

         } finally {
            this.pmu = false;
         }

         try {
            PhantomClient.effectRenderActive = true;
            this.vyg.ysf(var7.width, var7.height);

            this.jvq = true;
            OutlineBufferSource var21 = Minecraft.getInstance().renderBuffers().outlineBufferSource();

            // TODO: Entity outline color pass - render API changed in MC 26.1
            // EntityRenderManager.render() now requires EntityRenderState + OrderedRenderCommandQueue
            // Entity re-rendering temporarily disabled
            // OutlineVertexConsumerProvider.setColor(int,int,int,int) is now setColor(int) - pack ARGB:
            // var21.setColor((255 << 24) | (var25.getRed() << 16) | (var25.getGreen() << 8) | var25.getBlue());

            var21.endOutlineBatch();
         } finally {
            PhantomClient.effectRenderActive = false;
            this.jvq = false;
            this.vyg.ipg();

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
