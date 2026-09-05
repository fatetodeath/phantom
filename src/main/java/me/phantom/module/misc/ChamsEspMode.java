package me.phantom.module.misc;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.WorldRenderSceneEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.visuals.EspModule;
import me.phantom.render.effect.BlurShaderProgram;
import me.phantom.render.NanoVGFramebuffer;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ChamsEspMode extends AbstractEspFeature {
   public final ColorSetting nja = new ColorSetting(this.vsr, GlyphTextUtil.encode("chams_color_inside"), 0.0F, 0.9F, 0.9F);
   public final ColorSetting bkv = new ColorSetting(this.vsr, GlyphTextUtil.encode("chams_color_outside"), 0.33F, 0.9F, 0.9F);
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this.vsr, GlyphTextUtil.encode("opacity"), 0, 100, 100);
   public NanoVGFramebuffer dtg = new NanoVGFramebuffer();
   public BlurShaderProgram wom = new BlurShaderProgram();
   public boolean jvq;
   public boolean pmu;

   public ChamsEspMode(EspModule var1) {
      super(GlyphTextUtil.encode("Chams"), var1);
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

         try {
            PhantomClient.effectRenderActive = true;
            var4.setRenderShadows(false);
            OutlineVertexConsumerProvider var7 = MinecraftClient.getInstance().getBufferBuilders().getOutlineVertexConsumers();
            this.pmu = true;
            Color var8 = this.nja.nqq();
            GL11.glDisable(2929);

            for (Entity var10 : this.vsr.ffs(PhantomClient.uiManager.getContext())) {
               boolean var11 = var10.isGlowing();
               var10.setGlowing(false);
               GL11.glDisable(2929);
               var7.setColor(var8.getRed(), var8.getGreen(), var8.getBlue(), 255);
               Vec3d var12 = GameInteractionUtil.getLerpedPos(var10, var5).subtract(var6);
               var4.render(var10, var12.x, var12.y, var12.z, var5, var1.getMatrices(), var7, 14680064);
               var10.setGlowing(var11);
            }

            var7.draw();
            GL11.glEnable(2929);
         } finally {
            PhantomClient.effectRenderActive = false;
            this.pmu = false;
            var4.setRenderShadows(true);
         }
      }
   }

   @Override
   public void uu(GuiRenderContext var1, MatrixStack var2) {
   }

   @Override
   public void lfc(GuiRenderContext var1) {
   }
}
