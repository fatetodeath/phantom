package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.WorldRendererDuck;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.BlockBreakingInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.lwjgl.opengl.GL11;
public class BlockOverlayModule extends BaseModule {
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 1, 35, 100);
   public final ColorSetting imc = new ColorSetting(this, GlyphTextUtil.encode("color"), 0.6F, 1.0F, 1.0F);
   public final ToggleSetting bo = new ToggleSetting(this, GlyphTextUtil.encode("breaking"), true);
   public final ColorSetting cmi = new ColorSetting(this, GlyphTextUtil.encode("breaking_color"), 0.3F, 1.0F, 1.0F);
   public final ToggleSetting osz = new ToggleSetting(this, GlyphTextUtil.encode("others"), true);

   public BlockOverlayModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("BlockOverlay"));
      this.cmi.setVisibleWhen(this.bo);
      this.selectAllSettings();
      this.markClipboardHint();
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      float var2 = var1.tickDelta();
      ClientPlayerEntity var3 = MinecraftClient.getInstance().player;
      if ((Boolean)this.osz.getValue()) {
         for (Entity var5 : WorldEntityUtil.getEntities()) {
            if (var5 instanceof PlayerEntity) {
               this.hbz(var1.getMatrices(), (PlayerEntity)var5, var2);
            }
         }
      } else {
         this.hbz(var1.getMatrices(), var3, var2);
      }
   }

   private void hbz(MatrixStack var1, PlayerEntity var2, float var3) {
      MinecraftClient var4 = MinecraftClient.getInstance();
      ClientWorld var5 = var4.world;
      Vec3d var6 = GameInteractionUtil.getLerpedEyePos(var2, var3);
      Vec3d var7 = var6.add(GameInteractionUtil.getRotationVector(var2, var3).multiply(5.0));
      BlockHitResult var8 = var5.raycast(new RaycastContext(var6, var7, ShapeType.OUTLINE, FluidHandling.ANY, var2));
      if (var8 != null && var8.getType() == Type.BLOCK) {
         BlockPos var9 = var8.getBlockPos();
         BlockState var10 = var5.getBlockState(var9);
         VoxelShape var11 = var10.getOutlineShape(var5, var9);
         if (var11.isEmpty()) {
            return;
         }

         var1.push();

         try {
            var1.translate(var9.getX(), var9.getY(), var9.getZ());
            GL11.glDisable(2929);
            if ((Boolean)this.bo.getValue()) {
               List<Box> var12 = var11.getBoundingBoxes();
               Box var13 = (Box)var12.get(0);

               for (Box var15 : var12) {
                  var13 = var13.union(var15);
               }

               WorldRendererDuck var30 = (WorldRendererDuck)var4.worldRenderer;
               BlockBreakingInfo var31 = (BlockBreakingInfo)var30.getBlockBreakingInfos().get(var2.getId());
               if (var31 != null) {
                  float var16 = 0.5F + 0.5F * (var31.getStage() / 10.0F);
                  Vec3d var17 = var13.getCenter();
                  double var18 = var13.getLengthX() * var16;
                  double var20 = var13.getLengthY() * var16;
                  double var22 = var13.getLengthZ() * var16;
                  Box var24 = new Box(
                     var17.x - var18 / 2.0, var17.y - var20 / 2.0, var17.z - var22 / 2.0, var17.x + var18 / 2.0, var17.y + var20 / 2.0, var17.z + var22 / 2.0
                  );
                  Color var25 = this.cmi.nqq();
                  Render3DUtil.szb(
                     var1,
                     VoxelShapes.cuboid(var24),
                     var25.getRed() / 255.0F,
                     var25.getGreen() / 255.0F,
                     var25.getBlue() / 255.0F,
                     ((Integer)this.ep.getValue()).intValue() / 100.0F
                  );
                  Render3DUtil.cpb(
                     var1,
                     VoxelShapes.cuboid(var24),
                     var25.getRed() / 255.0F,
                     var25.getGreen() / 255.0F,
                     var25.getBlue() / 255.0F,
                     ((Integer)this.ep.getValue()).intValue() / 100.0F
                  );
               }
            }

            Color var29 = this.imc.nqq();
            Render3DUtil.szb(var1, var11, var29.getRed() / 255.0F, var29.getGreen() / 255.0F, var29.getBlue() / 255.0F, ((Integer)this.ep.getValue()).intValue() / 100.0F);
            Render3DUtil.cpb(var1, var11, var29.getRed() / 255.0F, var29.getGreen() / 255.0F, var29.getBlue() / 255.0F, ((Integer)this.ep.getValue()).intValue() / 100.0F);
         } finally {
            var1.pop();
            GL11.glEnable(2929);
         }
      }
   }
}
