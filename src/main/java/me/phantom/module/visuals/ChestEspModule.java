package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.world.ChunkOcclusionBlockEvent;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.minecart.MinecartChest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.chunk.LevelChunk;
import org.lwjgl.opengl.GL11;
public class ChestEspModule extends BaseModule {
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 0, 50, 100);
   public final ToggleSetting cs = new ToggleSetting(this, GlyphTextUtil.encode("chest_render"), true);
   public final ColorSetting px = new ColorSetting(this, GlyphTextUtil.encode("chest_color"), Color.green);
   public final ToggleSetting zes = new ToggleSetting(this, GlyphTextUtil.encode("looted_chest_render"), true);
   public final ColorSetting tjw = new ColorSetting(this, GlyphTextUtil.encode("looted_chest_color"), Color.magenta);
   public final ToggleSetting yuc = new ToggleSetting(this, GlyphTextUtil.encode("ender_chest_render"), true);
   public final ColorSetting gsk = new ColorSetting(this, GlyphTextUtil.encode("ender_chest_color"), Color.cyan);
   public final ToggleSetting rcz = new ToggleSetting(this, GlyphTextUtil.encode("trapped_chest_render"), true);
   public final ColorSetting ctl = new ColorSetting(this, GlyphTextUtil.encode("trapped_chest_color"), Color.red);
   public final ToggleSetting jhz = new ToggleSetting(this, GlyphTextUtil.encode("minecart_chest_render"), true);
   public final ColorSetting bypw = new ColorSetting(this, GlyphTextUtil.encode("minecart_chest_color"), Color.red);
   public final ToggleSetting ile = new ToggleSetting(this, GlyphTextUtil.encode("barrel_render"), false) {
      @Override
      public void bdl(Boolean var1) {
         super.bdl(var1);
         if (var1 == Boolean.TRUE) {
            ChestEspModule.this.sod = true;
         }
      }
   };
   public final ColorSetting pnd = new ColorSetting(this, GlyphTextUtil.encode("barrel_color"), Color.yellow);
   public boolean sod = false;
   private final Set<Vec3i> tacb = new HashSet<>();
   public final Set<BlockPos> lsw = new HashSet<>();
   private int itc;

   public ChestEspModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("ChestESP"));
      this.px.setVisibleWhen(this.cs);
      this.tjw.setVisibleWhen(this.zes);
      this.gsk.setVisibleWhen(this.yuc);
      this.ctl.setVisibleWhen(this.rcz);
      this.pnd.setVisibleWhen(this.ile);
      this.bypw.setVisibleWhen(this.jhz);
      this.selectAllSettings();
      this.markClipboardHint();
   }

   @Override
   public void onDisable() {
      this.lsw.clear();
   }

   @Override
   public void onEnable() {
      this.lsw.clear();
      if ((Boolean)this.ile.getValue()) {
         Minecraft.getInstance().levelRenderer.allChanged();
      }
   }

   public void ihs(ChunkOcclusionBlockEvent var1) {
      BlockPos var2 = var1.fgg();
      if (this.sqm(var2) != null) {
         this.lsw.add(new BlockPos(var2.getX(), var2.getY(), var2.getZ()));
      }
   }

   public ColorSetting sqm(BlockPos var1) {
      if (!(Boolean)this.ile.getValue()) {
         return null;
      } else {
         ClientLevel var2 = Minecraft.getInstance().level;
         Block var3 = var2.getBlockState(var1).getBlock();
         return var3 == Blocks.BARREL && this.ile.getValue() ? this.pnd : null;
      }
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      if (this.sod) {
         Minecraft.getInstance().levelRenderer.allChanged();
         this.sod = false;
      }

      ClientLevel var2 = Minecraft.getInstance().level;
      LocalPlayer var3 = Minecraft.getInstance().player;
      if (var3.getId() != this.itc) {
         this.itc = var3.getId();
         this.tacb.clear();
      }

      if (!(Boolean)this.zes.getValue()) {
         this.tacb.clear();
      }

      try {
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         Iterator var4 = this.lsw.iterator();

         while (var4.hasNext()) {
            BlockPos var5 = (BlockPos)var4.next();
            ColorSetting var6 = this.sqm(var5);
            if (var6 == null) {
               var4.remove();
            } else {
               Render3DUtil.ehw(
                  var1.getMatrices(),
                  Shapes.create(new AABB(var5.getX(), var5.getY(), var5.getZ(), var5.getX() + 1.0, var5.getY() + 1.0, var5.getZ() + 1.0)),
                  var6.nqq(),
                  ((Integer)this.ep.getValue()).intValue() / 100.0F
               );
            }
         }

         HashSet var28 = new HashSet();

         for (LevelChunk var7 : GameInteractionUtil.getLoadedChunks()) {
            for (Entry var9 : var7.getBlockEntities().entrySet()) {
               BlockPos var10 = (BlockPos)var9.getKey();
               BlockEntity var11 = (BlockEntity)var9.getValue();
               boolean var12 = false;
               if ((var11 instanceof ChestBlockEntity || var11 instanceof EnderChestBlockEntity) && (Boolean)this.zes.getValue()) {
                  var12 = this.tacb.contains(var10);
                  if (!var12 && ChestBlockEntity.getOpenCount(var2, var10) > 0) {
                     this.tacb.add(var10);
                     var12 = true;
                  }
               }

               Object var13 = null;
               if (var11 instanceof ChestBlockEntity) {
                  var13 = (var12 ? this.tjw : this.px).nqq();
               } else if (var11 instanceof TrappedChestBlockEntity && (Boolean)this.rcz.getValue()) {
                  var13 = (var12 ? this.tjw : this.ctl).nqq();
               } else {
                  if (!(var11 instanceof EnderChestBlockEntity) || !(Boolean)this.yuc.getValue()) {
                     continue;
                  }

                  var13 = (var12 ? this.tjw : this.gsk).nqq();
               }

               var28.add(var10);
               BlockState var14 = var2.getBlockState(var10);
               Block var15 = var14.getBlock();
               if (var15 instanceof ChestBlock) {
                  Direction var16 = (Direction)var14.getValue(ChestBlock.FACING);
                  ChestType var17 = (ChestType)var14.getValue(ChestBlock.TYPE);
                  if (var17 != ChestType.SINGLE) {
                     boolean var38 = false;
                     boolean var39 = false;
                     if (var16 == Direction.EAST) {
                        if (var17 == ChestType.RIGHT) {
                           continue;
                        }

                        var39 = true;
                     } else if (var16 == Direction.SOUTH) {
                        if (var17 == ChestType.LEFT) {
                           continue;
                        }

                        var38 = true;
                     } else if (var16 == Direction.WEST) {
                        if (var17 == ChestType.LEFT) {
                           continue;
                        }

                        var39 = true;
                     } else if (var16 == Direction.NORTH) {
                        if (var17 == ChestType.RIGHT) {
                           continue;
                        }

                        var38 = true;
                     }

                     double var20 = var10.getX() + 0.0625;
                     int var22 = var10.getY();
                     double var23 = var10.getZ() + 0.0625;
                     Render3DUtil.ehw(
                        var1.getMatrices(),
                        Shapes.create(new AABB(var20, var22, var23, var20 + (var38 ? 1.875 : 0.875), var22 + 0.875, var23 + (var39 ? 1.875 : 0.875))),
                        (Color)var13,
                        ((Integer)this.ep.getValue()).intValue() / 100.0F
                     );
                     continue;
                  }
               }

               double var37 = var10.getX() + 0.0625;
               int var18 = var10.getY();
               double var19 = var10.getZ() + 0.0625;
               Render3DUtil.ehw(
                  var1.getMatrices(),
                  Shapes.create(new AABB(var37, var18, var19, var37 + 0.875, var18 + 0.875, var19 + 0.875)),
                  (Color)var13,
                  ((Integer)this.ep.getValue()).intValue() / 100.0F
               );
            }
         }

         this.tacb.removeIf(var1x -> !var28.contains(var1x));
         var28.clear();
         if ((Boolean)this.jhz.getValue()) {
            for (Entity var31 : WorldEntityUtil.getEntities()) {
               if (var31 instanceof MinecartChest) {
                  Vec3 var32 = GameInteractionUtil.getLerpedPos(var31, var1.tickDelta());
                  double var33 = var32.x - 0.5 + 0.19;
                  double var34 = var32.y + 0.38;
                  double var36 = var32.z - 0.5 + 0.19;
                  Render3DUtil.ehw(
                     var1.getMatrices(),
                     Shapes.create(new AABB(var33, var34, var36, var33 + 0.625, var34 + 0.625, var36 + 0.625)),
                     this.bypw.nqq(),
                     ((Integer)this.ep.getValue()).intValue() / 100.0F
                  );
               }
            }
         }
      } finally {
         GL11.glEnable(2929);
      }
   }
}
