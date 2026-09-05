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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.model.ModelPart.CuboidConsumer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures.Model;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
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
   public void uu(GuiRenderContext var1, MatrixStack var2) {
      if (var1.framebufferWidth > 0) {
         MinecraftClient var3 = MinecraftClient.getInstance();
         Framebuffer var4 = var3.getFramebuffer();
         this.ybh.ysf(var4.viewportWidth, var4.viewportHeight);
         this.reg = true;
         EntityRenderDispatcher var5 = var3.getEntityRenderDispatcher();
         float var6 = var1.tickDelta;

         try {
            for (Entity var8 : this.vsr.ffs(var1)) {
               if (!(var8 instanceof AbstractClientPlayerEntity var9)) {
                  return;
               }

               ColorSource var10 = this.ypg(var8);
               Vec3d var11 = GameInteractionUtil.getLerpedPos(var8, var6);
               PlayerEntityRenderer var12 = (PlayerEntityRenderer)var5.getRenderer(var9);
               PlayerEntityModel var13 = (PlayerEntityModel)var12.getModel();
               boolean var14 = var9.getSkinTextures().model() == Model.SLIM;
               float var15 = ((Integer)this.cer.getValue()).intValue() / 100.0F / 20.0F;
               PlayerEntityRenderState var16 = (PlayerEntityRenderState)var12.getAndUpdateRenderState(var9, var6);
               var13.setAngles(var16);
               GL11.glDisable(2929);
               Color var17 = var10.nqq();
               float var18 = var17.getRed() / 255.0F;
               float var19 = var17.getGreen() / 255.0F;
               float var20 = var17.getBlue() / 255.0F;
               float var21 = ((Integer)this.ep.getValue()).intValue() / 100.0F;
               CuboidConsumer var22 = (var6x, var7, var8x, var9x) -> {
                  float var10x = (var9x.minX + var9x.maxX) / 2.0F / 16.0F;
                  float var11x = (var9x.minZ + var9x.maxZ) / 2.0F / 16.0F;
                  Render3DUtil.cpb(
                     var2,
                     VoxelShapes.cuboid(
                        new Box(var10x - var15, var9x.minY / 16.0F, var11x - var15, var10x + var15, var9x.maxY / 16.0F - 0.0625F, var11x + var15)
                     ),
                     var18,
                     var19,
                     var20,
                     var21
                  );
                  Render3DUtil.cpb(
                     var2,
                     VoxelShapes.cuboid(
                        new Box(
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
               CuboidConsumer var23 = (var6x, var7, var8x, var9x) -> {
                  float var10x = (var9x.minX + var9x.maxX) / 2.0F / 16.0F;
                  float var11x = (var9x.minZ + var9x.maxZ) / 2.0F / 16.0F;
                  Render3DUtil.cpb(
                     var2,
                     VoxelShapes.cuboid(
                        new Box(var10x - var15, var9x.minY / 16.0F + 0.125F, var11x - var15, var10x + var15, var9x.maxY / 16.0F - 0.125F, var11x + var15)
                     ),
                     var18,
                     var19,
                     var20,
                     var21
                  );
               };
               CuboidConsumer var24 = (var7, var8x, var9x, var10x) -> {
                  float var11x = (var10x.minX + var10x.maxX) / 2.0F / 16.0F;
                  float var12x = (var10x.minZ + var10x.maxZ) / 2.0F / 16.0F;
                  Render3DUtil.cpb(
                     var2,
                     VoxelShapes.cuboid(new Box(var11x - var15, var10x.minY / 16.0F, var12x - var15, var11x + var15, var10x.maxY / 16.0F, var12x + var15)),
                     var18,
                     var19,
                     var20,
                     var21
                  );
                  Render3DUtil.cpb(
                     var2,
                     VoxelShapes.cuboid(new Box(-var15, -var15 + 0.1F, -var15, var15 + (var14 ? 0.35F : 0.38F), var15 + 0.1F, var15)),
                     var18,
                     var19,
                     var20,
                     var21
                  );
                  Render3DUtil.cpb(
                     var2,
                     VoxelShapes.cuboid(new Box(-var15, -var15 + 0.1F, -var15, -(var15 + (var14 ? 0.35F : 0.38F)), var15 + 0.1F, var15)),
                     var18,
                     var19,
                     var20,
                     var21
                  );
               };
               CuboidConsumer var25 = (var8x, var9x, var10x, var11x) -> {
                  float var12x = (var11x.minX + var11x.maxX) / 2.0F / 16.0F;
                  float var13x = (var11x.minY + var11x.maxY) / 2.0F / 16.0F;
                  float var14x = (var11x.minZ + var11x.maxZ) / 2.0F / 16.0F;
                  float var15x = GameInteractionUtil.getLerpedPitch(var9, var6);
                  var2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-var15x * 2.0F));
                  Render3DUtil.cpb(
                     var2,
                     VoxelShapes.cuboid(new Box(var12x - var15, var13x, var14x - var15, var12x + var15, var11x.maxY / 16.0F, var14x + var15)),
                     var18,
                     var19,
                     var20,
                     var21
                  );
                  var2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var15x * 2.0F));
               };
               var2.push();
               var2.translate(var11.x, var11.y + var8.getHeight() - 0.4, var11.z);
               var2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
               var2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var9.lastBodyYaw + (var9.bodyYaw - var9.lastBodyYaw) * var6));
               var13.head.forEachCuboid(var2, var25);
               var13.leftLeg.forEachCuboid(var2, (var1x, var2x, var3x, var4x) -> var22.accept(var1x, var2x, 0, var4x));
               var13.rightLeg.forEachCuboid(var2, (var1x, var2x, var3x, var4x) -> var22.accept(var1x, var2x, 1, var4x));
               var13.leftArm.forEachCuboid(var2, (var1x, var2x, var3x, var4x) -> var23.accept(var1x, var2x, 0, var4x));
               var13.rightArm.forEachCuboid(var2, (var1x, var2x, var3x, var4x) -> var23.accept(var1x, var2x, 1, var4x));
               var13.body.forEachCuboid(var2, var24);
               GlStateManager._enableDepthTest();
               var2.pop();
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

   protected void wjt(LivingEntity var1, MatrixStack var2, float var3, float var4, float var5) {
      if (var1.isFrozen()) {
         var4 += (float)(Math.cos(var1.age * 3.25) * Math.PI * 0.4F);
      }

      if (!var1.isInPose(EntityPose.SLEEPING)) {
         var2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - var4));
      }

      if (var1.deathTime > 0) {
         float var6 = (var1.deathTime + var5 - 1.0F) / 20.0F * 1.6F;
         var6 = MathHelper.sqrt(var6);
         if (var6 > 1.0F) {
            var6 = 1.0F;
         }

         var2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(var6 * 90.0F));
      } else if (var1.isUsingRiptide()) {
         var2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F - var1.getPitch()));
         var2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((var1.age + var5) * -75.0F));
      } else if (var1.isInPose(EntityPose.SLEEPING)) {
         Direction var9 = var1.getSleepingDirection();
         float var7 = var9 != null ? pgq(var9) : var4;
         var2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var7));
         var2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
         var2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270.0F));
      } else if (LivingEntityRenderer.shouldFlipUpsideDown(var1)) {
         var2.translate(0.0F, var1.getHeight() + 0.1F, 0.0F);
         var2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
      }
   }

   protected void zuyl(AbstractClientPlayerEntity var1, MatrixStack var2, float var3, float var4, float var5) {
      float var6 = var1.getLeaningPitch(var5);
      float var7 = var1.getPitch(var5);
      if (var1.isGliding()) {
         this.wjt(var1, var2, var3, var4, var5);
         float var8 = var1.getLeaningPitch(var5);
         float var9 = MathHelper.clamp(var8 * var8 / 100.0F, 0.0F, 1.0F);
         if (!var1.isUsingRiptide()) {
            var2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var9 * (-90.0F - var7)));
         }

         Vec3d var10 = var1.getRotationVec(var5);
         Vec3d var11 = var1.lerpVelocity(var5);
         double var12 = var11.horizontalLengthSquared();
         double var14 = var10.horizontalLengthSquared();
         if (var12 > 0.0 && var14 > 0.0) {
            double var16 = (var11.x * var10.x + var11.z * var10.z) / Math.sqrt(var12 * var14);
            double var18 = var11.x * var10.z - var11.z * var10.x;
            var2.multiply(RotationAxis.POSITIVE_Y.rotation((float)(Math.signum(var18) * Math.acos(var16))));
         }
      } else if (var6 > 0.0F) {
         this.wjt(var1, var2, var3, var4, var5);
         float var20 = var1.isTouchingWater() ? -90.0F - var7 : -90.0F;
         float var21 = MathHelper.lerp(var6, 0.0F, var20);
         var2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var21));
         if (var1.isInSwimmingPose()) {
            var2.translate(0.0F, -1.0F, 0.3F);
         }
      } else {
         this.wjt(var1, var2, var3, var4, var5);
      }
   }
}
