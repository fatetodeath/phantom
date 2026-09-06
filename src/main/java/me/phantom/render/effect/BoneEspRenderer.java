package me.phantom.render.effect;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.visuals.EspModule;
import me.phantom.render.NanoVGFramebuffer;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.render.ColorSource;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.model.geom.ModelPart.Visitor;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.player.PlayerModelType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

public class BoneEspRenderer extends AbstractEspFeature {
   public final IntegerSliderSetting cer = new IntegerSliderSetting(this.vsr, GlyphTextUtil.encode("bone_size"), 10, 50, 100);
   public NanoVGFramebuffer ybh = new NanoVGFramebuffer();
   public boolean reg;

   public BoneEspRenderer(EspModule var1) {
      super(GlyphTextUtil.encode("Bone"), var1);
   }

   @Override
   public void uu(GuiRenderContext var1, PoseStack var2) {
      if (var1.framebufferWidth > 0) {
         Minecraft var3 = Minecraft.getInstance();
         RenderTarget var4 = var3.getMainRenderTarget();
         this.ybh.ysf(var4.width, var4.height);
         this.reg = true;
         EntityRenderDispatcher var5 = var3.getEntityRenderDispatcher();
         float var6 = var1.tickDelta;

         try {
            for (Entity var8 : this.vsr.ffs(var1)) {
               if (!(var8 instanceof AbstractClientPlayer var9)) {
                  return;
               }

               ColorSource var10 = this.ypg(var8);
               Vec3 var11 = GameInteractionUtil.getLerpedPos(var8, var6);
               AvatarRenderer var12 = (AvatarRenderer)var5.getRenderer(var9);
               PlayerModel var13 = (PlayerModel)var12.getModel();
                boolean var14 = var9.getSkin().model() == PlayerModelType.SLIM;
               float var15 = ((Integer)this.cer.getValue()).intValue() / 100.0F / 20.0F;
               AvatarRenderState var16 = (AvatarRenderState)var12.createRenderState(var9, var6);
               var13.setupAnim(var16);
               GL11.glDisable(2929);
               Color var17 = var10.nqq();
               float var18 = var17.getRed() / 255.0F;
               float var19 = var17.getGreen() / 255.0F;
               float var20 = var17.getBlue() / 255.0F;
               float var21 = ((Integer)this.ep.getValue()).intValue() / 100.0F;
               Visitor var22 = (var6x, var7, var8x, var9x) -> {
                  float var10x = (var9x.minX + var9x.maxX) / 2.0F / 16.0F;
                  float var11x = (var9x.minZ + var9x.maxZ) / 2.0F / 16.0F;
                  Render3DUtil.cpb(
                     var2,
                     Shapes.create(
                        new AABB(var10x - var15, var9x.minY / 16.0F, var11x - var15, var10x + var15, var9x.maxY / 16.0F - 0.0625F, var11x + var15)
                     ),
                     var18,
                     var19,
                     var20,
                     var21
                  );
                  Render3DUtil.cpb(
                     var2,
                     Shapes.create(
                        new AABB(
                           var8x > 0 ? var10x - var15 : var9x.minX / 16.0F,
                           var9x.minY / 16.0F - var15,
                           var11x - var15,
                           var8x > 0 ? var9x.maxX / 16.0F : var10x + var15,
                           var9x.minY / 16.0F + var15,
                           var11x + var15
                        )
                     ),
                     var18,
                     var19,
                     var20,
                     var21
                  );
               };
               Visitor var23 = (var6x, var7, var8x, var9x) -> {
                  float var10x = (var9x.minX + var9x.maxX) / 2.0F / 16.0F;
                  float var11x = (var9x.minZ + var9x.maxZ) / 2.0F / 16.0F;
                  Render3DUtil.cpb(
                     var2,
                     Shapes.create(
                        new AABB(var10x - var15, var9x.minY / 16.0F + 0.125F, var11x - var15, var10x + var15, var9x.maxY / 16.0F - 0.125F, var11x + var15)
                     ),
                     var18,
                     var19,
                     var20,
                     var21
                  );
               };
               Visitor var24 = (var7, var8x, var9x, var10x) -> {
                  float var11x = (var10x.minX + var10x.maxX) / 2.0F / 16.0F;
                  float var12x = (var10x.minZ + var10x.maxZ) / 2.0F / 16.0F;
                  Render3DUtil.cpb(
                     var2,
                     Shapes.create(new AABB(var11x - var15, var10x.minY / 16.0F, var12x - var15, var11x + var15, var10x.maxY / 16.0F, var12x + var15)),
                     var18,
                     var19,
                     var20,
                     var21
                  );
                  Render3DUtil.cpb(
                     var2,
                     Shapes.create(new AABB(-var15, -var15 + 0.1F, -var15, var15 + (var14 ? 0.35F : 0.38F), var15 + 0.1F, var15)),
                     var18,
                     var19,
                     var20,
                     var21
                  );
                  Render3DUtil.cpb(
                     var2,
                     Shapes.create(new AABB(-var15, -var15 + 0.1F, -var15, -(var15 + (var14 ? 0.35F : 0.38F)), var15 + 0.1F, var15)),
                     var18,
                     var19,
                     var20,
                     var21
                  );
               };
               Visitor var25 = (var8x, var9x, var10x, var11x) -> {
                  float var12x = (var11x.minX + var11x.maxX) / 2.0F / 16.0F;
                  float var13x = (var11x.minY + var11x.maxY) / 2.0F / 16.0F;
                  float var14x = (var11x.minZ + var11x.maxZ) / 2.0F / 16.0F;
                  float var15x = GameInteractionUtil.getLerpedPitch(var9, var6);
                  var2.mulPose(Axis.XP.rotationDegrees(-var15x * 2.0F));
                  Render3DUtil.cpb(
                     var2,
                     Shapes.create(new AABB(var12x - var15, var13x, var14x - var15, var12x + var15, var11x.maxY / 16.0F, var14x + var15)),
                     var18,
                     var19,
                     var20,
                     var21
                  );
                  var2.mulPose(Axis.XP.rotationDegrees(var15x * 2.0F));
               };
               var2.pushPose();
               var2.translate(var11.x, var11.y + var8.getBbHeight() - 0.4, var11.z);
               var2.mulPose(Axis.ZP.rotationDegrees(180.0F));
               var2.mulPose(Axis.YP.rotationDegrees(var9.yBodyRotO + (var9.yBodyRot - var9.yBodyRotO) * var6));
               var13.head.visit(var2, var25);
               var13.leftLeg.visit(var2, (var1x, var2x, var3x, var4x) -> var22.visit(var1x, var2x, 0, var4x));
               var13.rightLeg.visit(var2, (var1x, var2x, var3x, var4x) -> var22.visit(var1x, var2x, 1, var4x));
               var13.leftArm.visit(var2, (var1x, var2x, var3x, var4x) -> var23.visit(var1x, var2x, 0, var4x));
               var13.rightArm.visit(var2, (var1x, var2x, var3x, var4x) -> var23.visit(var1x, var2x, 1, var4x));
               var13.body.visit(var2, var24);
               GlStateManager._enableDepthTest();
               var2.popPose();
            }

            GL11.glEnable(3042);
         } finally {
            this.reg = false;
            this.ybh.ipg();
         }
      }
   }

   @Override
   public boolean nsq() {
      return this.reg;
   }

   @Override
   public void lfc(GuiRenderContext var1) {
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRect(var1.nvg, 0.0F, 0.0F, var1.framebufferWidth, var1.framebufferHeight);
      NVGPaint var2 = NanoVG.nvgImagePattern(var1.nvg, 0.0F, var1.framebufferHeight, var1.framebufferWidth, -var1.framebufferHeight, 0.0F, this.ybh.lwe(), 1.0F, GuiRenderContext.paintScratch);
      NanoVG.nvgFillPaint(var1.nvg, var2);
      NanoVG.nvgFill(var1.nvg);
   }

   public static float pgq(Direction var0) {
      switch (var0) {
         case SOUTH:
            return 90.0F;
         case WEST:
            return 0.0F;
         case NORTH:
            return 270.0F;
         case EAST:
            return 180.0F;
         default:
            return 0.0F;
      }
   }

   protected void wjt(LivingEntity var1, PoseStack var2, float var3, float var4, float var5) {
      if (var1.isFullyFrozen()) {
         var4 += (float)(Math.cos(var1.tickCount * 3.25) * Math.PI * 0.4F);
      }

      if (!var1.hasPose(Pose.SLEEPING)) {
         var2.mulPose(Axis.YP.rotationDegrees(180.0F - var4));
      }

      if (var1.deathTime > 0) {
         float var6 = (var1.deathTime + var5 - 1.0F) / 20.0F * 1.6F;
         var6 = Mth.sqrt(var6);
         if (var6 > 1.0F) {
            var6 = 1.0F;
         }

         var2.mulPose(Axis.ZP.rotationDegrees(var6 * 90.0F));
      } else if (var1.isAutoSpinAttack()) {
         var2.mulPose(Axis.XP.rotationDegrees(-90.0F - var1.getXRot()));
         var2.mulPose(Axis.YP.rotationDegrees((var1.tickCount + var5) * -75.0F));
      } else if (var1.hasPose(Pose.SLEEPING)) {
         Direction var9 = var1.getBedOrientation();
         float var7 = var9 != null ? pgq(var9) : var4;
         var2.mulPose(Axis.YP.rotationDegrees(var7));
         var2.mulPose(Axis.ZP.rotationDegrees(90.0F));
         var2.mulPose(Axis.YP.rotationDegrees(270.0F));
       }
       // TODO: shouldFlipUpsideDown is now an instance method; needs LivingEntityRenderer instance
       // } else if (LivingEntityRenderer.shouldFlipUpsideDown(var1)) {
       //    var2.translate(0.0F, var1.getHeight() + 0.1F, 0.0F);
       //    var2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
       // }
   }

   protected void zuyl(AbstractClientPlayer var1, PoseStack var2, float var3, float var4, float var5) {
      float var6 = var1.getSwimAmount(var5);
      float var7 = var1.getViewXRot(var5);
      if (var1.isFallFlying()) {
         this.wjt(var1, var2, var3, var4, var5);
         float var8 = var1.getSwimAmount(var5);
         float var9 = Mth.clamp(var8 * var8 / 100.0F, 0.0F, 1.0F);
         if (!var1.isAutoSpinAttack()) {
            var2.mulPose(Axis.XP.rotationDegrees(var9 * (-90.0F - var7)));
         }

         Vec3 var10 = var1.getViewVector(var5);
         Vec3 var11 = var1.position();
         double var12 = var11.horizontalDistanceSqr();
         double var14 = var10.horizontalDistanceSqr();
         if (var12 > 0.0 && var14 > 0.0) {
            double var16 = (var11.x * var10.x + var11.z * var10.z) / Math.sqrt(var12 * var14);
            double var18 = var11.x * var10.z - var11.z * var10.x;
            var2.mulPose(Axis.YP.rotation((float)(Math.signum(var18) * Math.acos(var16))));
         }
      } else if (var6 > 0.0F) {
         this.wjt(var1, var2, var3, var4, var5);
         float var20 = var1.isInWater() ? -90.0F - var7 : -90.0F;
         float var21 = Mth.lerp(var6, 0.0F, var20);
         var2.mulPose(Axis.XP.rotationDegrees(var21));
         if (var1.isVisuallySwimming()) {
            var2.translate(0.0F, -1.0F, 0.3F);
         }
      } else {
         this.wjt(var1, var2, var3, var4, var5);
      }
   }
}
