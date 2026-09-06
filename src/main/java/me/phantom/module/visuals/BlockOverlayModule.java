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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.ClipContext.Block;
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
      LocalPlayer var3 = Minecraft.getInstance().player;
      if ((Boolean)this.osz.getValue()) {
         for (Entity var5 : WorldEntityUtil.getEntities()) {
            if (var5 instanceof Player) {
               this.hbz(var1.getMatrices(), (Player)var5, var2);
            }
         }
      } else {
         this.hbz(var1.getMatrices(), var3, var2);
      }
   }

   private void hbz(PoseStack var1, Player var2, float var3) {
      Minecraft var4 = Minecraft.getInstance();
      ClientLevel var5 = var4.level;
      Vec3 var6 = GameInteractionUtil.getLerpedEyePos(var2, var3);
      Vec3 var7 = var6.add(GameInteractionUtil.getRotationVector(var2, var3).scale(5.0));
      BlockHitResult var8 = var5.clip(new ClipContext(var6, var7, Block.OUTLINE, Fluid.ANY, var2));
      if (var8 != null && var8.getType() == Type.BLOCK) {
         BlockPos var9 = var8.getBlockPos();
         BlockState var10 = var5.getBlockState(var9);
         VoxelShape var11 = var10.getShape(var5, var9);
         if (var11.isEmpty()) {
            return;
         }

         var1.pushPose();

         try {
            var1.translate(var9.getX(), var9.getY(), var9.getZ());
            GL11.glDisable(2929);
            if ((Boolean)this.bo.getValue()) {
               List<AABB> var12 = var11.toAabbs();
               AABB var13 = (AABB)var12.get(0);

               for (AABB var15 : var12) {
                  var13 = var13.minmax(var15);
               }

               WorldRendererDuck var30 = (WorldRendererDuck)var4.levelRenderer;
               BlockDestructionProgress var31 = (BlockDestructionProgress)var30.getBlockBreakingInfos().get(var2.getId());
               if (var31 != null) {
                  float var16 = 0.5F + 0.5F * (var31.getProgress() / 10.0F);
                  Vec3 var17 = var13.getCenter();
                  double var18 = var13.getXsize() * var16;
                  double var20 = var13.getYsize() * var16;
                  double var22 = var13.getZsize() * var16;
                  AABB var24 = new AABB(
                     var17.x - var18 / 2.0, var17.y - var20 / 2.0, var17.z - var22 / 2.0, var17.x + var18 / 2.0, var17.y + var20 / 2.0, var17.z + var22 / 2.0
                  );
                  Color var25 = this.cmi.nqq();
                  Render3DUtil.szb(
                     var1,
                     Shapes.create(var24),
                     var25.getRed() / 255.0F,
                     var25.getGreen() / 255.0F,
                     var25.getBlue() / 255.0F,
                     ((Integer)this.ep.getValue()).intValue() / 100.0F
                  );
                  Render3DUtil.cpb(
                     var1,
                     Shapes.create(var24),
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
            var1.popPose();
            GL11.glEnable(2929);
         }
      }
   }
}
