package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.movement.SprintModule;
import me.phantom.render.LineStripBuilder;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
@ModuleMetadata(
   x = -20.5,
   y = -60.0,
   z = -34.5
)
public class JumpPredictionModule extends BaseModule {
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 0, 80, 100);
   public final ToggleSetting kqh = new ToggleSetting(this, GlyphTextUtil.encode("antibot"), false);
   public final FloatSliderSetting uau = new FloatSliderSetting(this, GlyphTextUtil.encode("dist"), 4.0F, 24.0F, 64.0F);
   public final ToggleSetting llx = new ToggleSetting(this, GlyphTextUtil.encode("visualize"), true);
   public final ToggleSetting nvx = new ToggleSetting(this, GlyphTextUtil.encode("visualize_others"), true);
   public final ColorSetting imc = new ColorSetting(this, GlyphTextUtil.encode("color"), 0.2F, 1.0F, 1.0F);
   public final Map<Integer, BlockPos> ik = new HashMap<>();
   public AntiBotModule pg;
   public SprintModule hdy;

   public JumpPredictionModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.or, GlyphTextUtil.encode("JumpPrediction"));
      this.markClipboardHint();
      this.selectSettings(new String[]{GlyphTextUtil.encode("color"), GlyphTextUtil.encode("visualize_others"), GlyphTextUtil.encode("visualize"), GlyphTextUtil.encode("opacity")});
      this.imc.setVisibleWhen(this.llx);
      this.nvx.setVisibleWhen(this.llx);
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      MatrixStack var2 = var1.getMatrices();
      float var3 = var1.tickDelta();
      ClientWorld var4 = MinecraftClient.getInstance().world;
      this.ik.clear();

      try {
         if ((Boolean)this.llx.getValue()) {
            GL11.glDisable(2929);
         }

         int var5 = 0;

         for (Entity var7 : WorldEntityUtil.getEntities()) {
            if (var7 instanceof PlayerEntity) {
               Vec3d var8 = GameInteractionUtil.getLerpedPos(var7, var3);
               if (!(var8.squaredDistanceTo(var1.yq()) > RotationMathUtil.square(((Float)this.uau.getValue()).floatValue())) && ((Boolean)this.kqh.getValue() || !this.pg.uei(var7))) {
                  if (var5++ >= 16) {
                     break;
                  }

                  PlayerEntity var9 = (PlayerEntity)var7;
                  float var10 = GameInteractionUtil.getLerpedYaw(var9, var3);
                  float var11 = 0.546F;
                  float var12 = var9.getMovementSpeed() * (0.16277136F / (var11 * var11 * var11));
                  int var13 = 0;
                  boolean var14 = var9.isSprinting();
                  if (var9 instanceof ClientPlayerEntity) {
                     var14 = this.hdy.isEnabled() || MinecraftClient.getInstance().options.sprintKey.isPressed();
                  }

                  int var15 = var7.getId();
                  Vec3d var16 = var7.getPos().subtract(var7.lastX, var7.lastY, var7.lastZ);
                  boolean var17 = var7.isOnGround();
                  var16 = var16.multiply(var17 ? 0.98 : 0.54, 1.0, var17 ? 0.98 : 0.54);
                  boolean var18 = true;
                  boolean var19 = (Boolean)this.nvx.getValue();
                  boolean var20 = var19;
                  if (var9 instanceof ClientPlayerEntity) {
                     var19 |= this.llx.getValue();
                     var20 = false;
                     var18 = MinecraftClient.getInstance().options.forwardKey.isPressed();
                     if (var17 && !var18) {
                        continue;
                     }
                  }

                  Vec3d var21 = new Vec3d(-Math.sin(Math.toRadians(var10)), 0.0, Math.cos(Math.toRadians(var10)));
                  if (var17) {
                     var16 = var16.add(var12 * var21.x, -var16.y + 0.42, var12 * var21.z);
                     var16 = var16.add(var21.x * (var14 ? 0.025479998 : 0.0196), -0.08, var21.z * (var14 ? 0.025479998 : 0.0196));
                  }

                  LineStripBuilder var22 = null;
                  if (var20) {
                     var22 = LineStripBuilder.create(var2);
                  }

                  try {
                     Vec3d var23 = var8;

                     for (Color var24 = this.imc.nqq(); var23.y > -64.0 && var13 <= 50; var13++) {
                        float var25 = var7.getWidth();
                        float var26 = var7.getHeight();
                        var23 = var23.add(var16);
                        if (var18) {
                           var16 = var16.add(var21.x * 0.0196, -0.08, var21.z * 0.0196);
                        } else {
                           var16 = var16.add(0.0, -0.08, 0.0);
                        }

                        var16 = var16.multiply(0.91, 0.98, 0.91);
                        Box var27 = new Box(
                           var23.x - var25 / 2.0F, var23.y, var23.z - var25 / 2.0F, var23.x + var25 / 2.0F, var23.y + var26, var23.z + var25 / 2.0F
                        );
                        double var28 = var16.y;
                        var16 = vhh(var4, var7, var16, var27);
                        if (var20) {
                           var22.vertex(var23.x, var23.y, var23.z, var24.getRed() / 255.0F, var24.getGreen() / 255.0F, var24.getBlue() / 255.0F, 1.0F);
                        }

                        if (var28 != var16.y) {
                           Vec3d var30 = var23.add(var16);
                           BlockPos var31 = BlockPos.ofFloored(var30.x, var30.y, var30.z);
                           this.ik.put(var15, var31);
                           if (var22 != null) {
                              var22.close();
                              var22 = null;
                           }

                           if (var19) {
                              for (VoxelShape var33 : var4.getBlockCollisions(
                                 var7, new Box(var31.getX(), var31.getY() - 1, var31.getZ(), var31.getX() + 1, var31.getY() + 1, var31.getZ() + 1)
                              )) {
                                 Render3DUtil.ehw(var1.getMatrices(), var33, var24, ((Integer)this.ep.getValue()).intValue() / 100.0F);
                              }
                           }
                           break;
                        }
                     }
                  } finally {
                     if (var22 != null) {
                        var22.close();
                     }
                  }
               }
            }
         }
      } finally {
         if ((Boolean)this.llx.getValue()) {
            GL11.glEnable(2929);
         }
      }
   }

   private static Vec3d vhh(World var0, Entity var1, Vec3d var2, Box var3) {
      Iterable var4 = var0.getBlockCollisions(var1, var3.stretch(var2));
      double var5 = var2.x;
      double var7 = var2.y;
      double var9 = var2.z;
      if (var7 != 0.0) {
         var7 = VoxelShapes.calculateMaxOffset(Axis.Y, var3, var4, var7);
         if (var7 != 0.0) {
            var3 = var3.offset(0.0, var7, 0.0);
         }
      }

      boolean var11 = Math.abs(var5) < Math.abs(var9);
      if (var11 && var9 != 0.0) {
         var9 = VoxelShapes.calculateMaxOffset(Axis.Z, var3, var4, var9);
         if (var9 != 0.0) {
            var3 = var3.offset(0.0, 0.0, var9);
         }
      }

      if (var5 != 0.0) {
         var5 = VoxelShapes.calculateMaxOffset(Axis.X, var3, var4, var5);
         if (!var11 && var5 != 0.0) {
            var3 = var3.offset(var5, 0.0, 0.0);
         }
      }

      if (!var11 && var9 != 0.0) {
         var9 = VoxelShapes.calculateMaxOffset(Axis.Z, var3, var4, var9);
      }

      return new Vec3d(var5, var7, var9);
   }

   @Override
   public void onDisable() {
      this.ik.clear();
   }

   @Override
   public void onEnable() {
      this.ik.clear();
   }

   public BlockPos liz(Entity var1) {
      return this.isEnabled() ? this.ik.get(var1.getId()) : null;
   }
}
