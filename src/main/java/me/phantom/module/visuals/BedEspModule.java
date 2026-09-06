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

import java.awt.Color;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.chunk.LevelChunk;
import org.lwjgl.opengl.GL11;
public class BedEspModule extends BaseModule {
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 0, 50, 100);
   public final ToggleSetting dah = new ToggleSetting(this, GlyphTextUtil.encode("match_bed_color"), true);
   public final ColorSetting imc = new ColorSetting(this, GlyphTextUtil.encode("color"), Color.red);
   public final List<BedBlockEntity> bav = new LinkedList<>();

   public BedEspModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("BedESP"));
      this.selectAllSettings();
      this.imc.setVisibilitySupplier(() -> !(Boolean)this.dah.getValue());
   }

   @Override
   public boolean ma() {
      return !this.bav.isEmpty();
   }

   @Override
   public void onEnable() {
      this.bav.clear();
   }

   @Override
   public void onDisable() {
      this.bav.clear();
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      try {
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         long var2 = System.currentTimeMillis() + 16L;
         Vec3 var4 = GameInteractionUtil.getLerpedEyePos(Minecraft.getInstance().player, var1.tickDelta());
         this.bav.sort(Comparator.comparingDouble(var1x -> -var1x.getBlockPos().distToLowCornerSqr(var4.x, var4.y, var4.z)));

         for (LevelChunk var6 : GameInteractionUtil.getLoadedChunks()) {
            for (Entry var8 : var6.getBlockEntities().entrySet()) {
               if (var8.getValue() instanceof BedBlockEntity var9) {
                  BlockPos var25 = (BlockPos)var8.getKey();
                  Color var11 = new Color(var9.getColor().getTextureDiffuseColor());
                  BlockState var12 = var9.getBlockState();
                  Direction var13 = (Direction)var12.getValue(BedBlock.FACING);
                  BedPart var14 = (BedPart)var12.getValue(BedBlock.PART);
                  if (var14 != BedPart.FOOT) {
                     byte var15 = 0;
                     byte var16 = 0;
                     byte var17 = 1;
                     byte var18 = 1;
                     switch (var13) {
                        case NORTH:
                           var18 = 2;
                           var15 = 0;
                           break;
                        case EAST:
                           var17 = 2;
                           var15 = -1;
                           break;
                        case SOUTH:
                           var18 = 2;
                           var16 = -1;
                           break;
                        case WEST:
                           var17 = 2;
                           var15 = 0;
                           break;
                        default:
                           continue;
                     }

                     if (!(Boolean)this.dah.getValue()) {
                        var11 = this.imc.nqq();
                     }

                     int var19 = var25.getX() + var15;
                     int var20 = var25.getY();
                     int var21 = var25.getZ() + var16;
                     Render3DUtil.ehw(
                        var1.getMatrices(),
                        Shapes.create(new AABB(var19, var20, var21, var19 + var17, var20 + 0.5625, var21 + var18)),
                        var11,
                        ((Integer)this.ep.getValue()).intValue() / 100.0F
                     );
                     if (System.currentTimeMillis() > var2) {
                        break;
                     }
                  }
               }
            }
         }

         this.bav.clear();
      } finally {
         GL11.glEnable(2929);
      }
   }
}
