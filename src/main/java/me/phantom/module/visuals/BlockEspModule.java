package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.world.ChunkOcclusionBlockEvent;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.BlockColorMapSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;

import java.awt.Color;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.opengl.GL11;
public class BlockEspModule extends BaseModule {
   public static final Map<String, float[]> jk = new LinkedHashMap<>();
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 0, 50, 100);
   public final IntegerSliderSetting txa = new IntegerSliderSetting(this, GlyphTextUtil.encode("max_blocks"), 128, 512, 4096);
   public final IntegerSliderSetting mpx = new IntegerSliderSetting(this, GlyphTextUtil.encode("view_distance"), 16, 64, 256);
   public final BlockColorMapSetting ruo = new BlockColorMapSetting(this, GlyphTextUtil.encode("blocks"), jk);
   public final List<BlockPos> gh = new LinkedList<>();

   private static float[] gm(Color var0, boolean var1) {
      float[] var2 = new float[4];
      Color.RGBtoHSB(var0.getRed(), var0.getGreen(), var0.getBlue(), var2);
      var2[3] = var1 ? 1.0F : 0.0F;
      return var2;
   }

   public BlockEspModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("BlockESP"));
      this.selectAllSettings();
   }

   @Override
   public boolean ma() {
      return !this.gh.isEmpty();
   }

   @Override
   public void onEnable() {
      MinecraftClient.getInstance().worldRenderer.reload();
      this.gh.clear();
   }

   @Override
   public void onDisable() {
      this.gh.clear();
      if (MinecraftClient.getInstance().player != null) {
         MinecraftClient.getInstance().worldRenderer.reload();
      }
   }

   public void tjy(TickEvent var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      Vec3d var3 = var2.player.getPos();
      double var4 = var3.x;
      double var6 = var3.y;
      double var8 = var3.z;
      this.gh.sort(Comparator.comparingDouble(var6x -> RotationMathUtil.square(var6x.getX() - var4) + RotationMathUtil.square(var6x.getY() - var6) + RotationMathUtil.square(var6x.getZ() - var8)));
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      try {
         Render3DUtil.ikib = false;
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         Iterator var2 = this.gh.iterator();
         long var3 = System.currentTimeMillis() + 16L;
         int var5 = (Integer)this.txa.getValue();
         int var6 = (int)RotationMathUtil.square(((Integer)this.mpx.getValue()).intValue());
         MinecraftClient var7 = MinecraftClient.getInstance();
         Vec3d var8 = var7.player.getPos();
         double var9 = var8.x;
         double var11 = var8.y;
         double var13 = var8.z;

         while (var2.hasNext()) {
            BlockPos var15 = (BlockPos)var2.next();
            Color var16 = this.iaw(var15);
            if (!(RotationMathUtil.square(var15.getX() - var9) + RotationMathUtil.square(var15.getY() - var11) + RotationMathUtil.square(var15.getZ() - var13) > var6)) {
               if (var16 == null) {
                  var2.remove();
               } else {
                  Render3DUtil.ehw(
                     var1.getMatrices(),
                     VoxelShapes.cuboid(new Box(var15.getX(), var15.getY(), var15.getZ(), var15.getX() + 1, var15.getY() + 1, var15.getZ() + 1)),
                     var16,
                     ((Integer)this.ep.getValue()).intValue() / 100.0F
                  );
                  if (--var5 <= 0 || System.currentTimeMillis() > var3) {
                     break;
                  }
               }
            }
         }

         MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers().draw();
      } finally {
         Render3DUtil.ikib = true;
         GL11.glEnable(2929);
      }
   }

   public void ihs(ChunkOcclusionBlockEvent var1) {
      BlockPos var2 = var1.fgg();
      if (this.iaw(var2) != null) {
         BlockPos var3 = new BlockPos(var2.getX(), var2.getY(), var2.getZ());
         if (!this.gh.contains(var3)) {
            this.gh.add(var3);
         }
      }
   }

   public Color iaw(BlockPos var1) {
      ClientWorld var2 = MinecraftClient.getInstance().world;
      Block var3 = var2.getBlockState(var1).getBlock();
      if (var3 == Blocks.AIR) {
         return null;
      } else {
         String var4 = Registries.BLOCK.getId(var3).toString();
         float[] var5 = (float[])((Map)this.ruo.getValue()).get(GlyphTextUtil.encodeChars(var4));
         return var5 != null ? Color.getHSBColor(var5[0], var5[1], var5[2]) : null;
      }
   }

   static {
      jk.put(GlyphTextUtil.encode("minecraft:diamond_ore"), gm(Color.CYAN, true));
      jk.put(GlyphTextUtil.encode("minecraft:diamond_block"), gm(Color.CYAN, true));
      jk.put(GlyphTextUtil.encode("minecraft:deepslate_diamond_ore"), gm(Color.CYAN, true));
      jk.put(GlyphTextUtil.encode("minecraft:ancient_debris"), gm(Color.ORANGE, true));
      jk.put(GlyphTextUtil.encode("minecraft:netherite_block"), gm(Color.ORANGE, true));
      jk.put(GlyphTextUtil.encode("minecraft:gold_ore"), gm(Color.YELLOW, false));
      jk.put(GlyphTextUtil.encode("minecraft:gold_block"), gm(Color.YELLOW, false));
      jk.put(GlyphTextUtil.encode("minecraft:deepslate_gold_ore"), gm(Color.YELLOW, false));
      jk.put(GlyphTextUtil.encode("minecraft:coal_ore"), gm(Color.BLACK, false));
      jk.put(GlyphTextUtil.encode("minecraft:coal_block"), gm(Color.BLACK, false));
      jk.put(GlyphTextUtil.encode("minecraft:deepslate_coal_ore"), gm(Color.BLACK, false));
      jk.put(GlyphTextUtil.encode("minecraft:iron_ore"), gm(Color.GRAY, false));
      jk.put(GlyphTextUtil.encode("minecraft:iron_block"), gm(Color.GRAY, false));
      jk.put(GlyphTextUtil.encode("minecraft:deepslate_iron_ore"), gm(Color.GRAY, false));
      jk.put(GlyphTextUtil.encode("minecraft:redstone_ore"), gm(Color.RED, false));
      jk.put(GlyphTextUtil.encode("minecraft:redstone_block"), gm(Color.RED, false));
      jk.put(GlyphTextUtil.encode("minecraft:deepslate_redstone_ore"), gm(Color.RED, false));
      jk.put(GlyphTextUtil.encode("minecraft:emerald_ore"), gm(Color.GREEN, true));
      jk.put(GlyphTextUtil.encode("minecraft:emerald_block"), gm(Color.GREEN, true));
      jk.put(GlyphTextUtil.encode("minecraft:deepslate_emerald_ore"), gm(Color.GREEN, true));
      jk.put(GlyphTextUtil.encode("minecraft:lapis_ore"), gm(Color.BLUE, false));
      jk.put(GlyphTextUtil.encode("minecraft:lapis_block"), gm(Color.BLUE, false));
      jk.put(GlyphTextUtil.encode("minecraft:deepslate_lapis_ore"), gm(Color.BLUE, false));
      jk.put(GlyphTextUtil.encode("minecraft:furnace"), gm(Color.GRAY, false));
      jk.put(GlyphTextUtil.encode("minecraft:obsidian"), gm(Color.BLUE.darker(), false));
      jk.put(GlyphTextUtil.encode("minecraft:tnt"), gm(Color.RED, false));
      jk.put(GlyphTextUtil.encode("minecraft:beacon"), gm(Color.WHITE, true));
   }
}
