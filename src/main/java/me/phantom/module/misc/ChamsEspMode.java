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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
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
         Minecraft var3 = Minecraft.getInstance();
         EntityRenderDispatcher var4 = var3.getEntityRenderDispatcher();
         float var5 = var1.getTickCounter().getGameTimeDeltaPartialTick(false);
         Vec3 var6 = var1.getCamera().position();

         try {
            PhantomClient.effectRenderActive = true;

OutlineBufferSource var7 = Minecraft.getInstance().renderBuffers().outlineBufferSource();
            this.pmu = true;
            Color var8 = this.nja.nqq();
            GL11.glDisable(2929);

            for (Entity var10 : this.vsr.ffs(PhantomClient.uiManager.getContext())) {
               boolean var11 = var10.isCurrentlyGlowing();
               var10.setGlowingTag(false);
               GL11.glDisable(2929);
               var7.setColor((255 << 24) | (var8.getRed() << 16) | (var8.getGreen() << 8) | var8.getBlue());
               Vec3 var12 = GameInteractionUtil.getLerpedPos(var10, var5).subtract(var6);
               // TODO: EntityRenderManager.render() API changed in 1.21.11
               var10.setGlowingTag(var11);
            }

            var7.endOutlineBatch();
            GL11.glEnable(2929);
         } finally {
            PhantomClient.effectRenderActive = false;
            this.pmu = false;

}
      }
   }

   @Override
   public void uu(GuiRenderContext var1, PoseStack var2) {
   }

   @Override
   public void lfc(GuiRenderContext var1) {
   }
}
