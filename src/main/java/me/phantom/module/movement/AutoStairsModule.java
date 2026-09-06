package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
@ModuleMetadata(
   x = -4.5,
   y = -60.0,
   z = -34.5
)
public class AutoStairsModule extends BaseModule {
   public boolean uyn;
   private boolean ugp;

   public AutoStairsModule() {
      super(ModuleCategory.wld, ClientPlayStyle.zfw, GlyphTextUtil.encode("AutoStairs"));
      this.markClipboardHint();
   }

   public void tjy(TickEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      this.uyn = !var2.isUsingItem() && var2.zza > 0.0F && !var2.getAbilities().flying && !var2.getAbilities().mayfly && this.pmg(var2);
      this.ugp = this.uyn ? !this.ugp : false;
   }

   @Override
   public boolean ma() {
      return this.uyn;
   }

   public void sau(KeyStateEvent var1) {
      if (this.uyn) {
         if (var1.by().getName().equals(GlyphTextUtil.plain("key.sprint"))) {
            var1.ywz(true);
         } else if (var1.by().getName().equals(GlyphTextUtil.plain("key.jump"))) {
            var1.ywz(this.ugp);
         }
      }
   }

   boolean pmg(LocalPlayer var1) {
      AABB var2 = var1.getBoundingBox();
      Vec3 var3 = var1.getDeltaMovement();
      double var10004 = var2.minX + var3.x;
      double var10006 = var2.minZ + var3.z;

      for (VoxelShape var5 : Minecraft.getInstance()
         .level
         .getBlockCollisions(var1, new AABB(var10004, var2.minY, var10006, var2.maxX + var3.x, var2.minY + 1.0 + Math.max(0.0, var3.y), var2.maxZ + var3.z))) {
         if (!var5.isEmpty()) {
            AABB var6 = var5.bounds();
            Vec3 var7 = var6.getCenter();
            BlockState var8 = Minecraft.getInstance()
               .level
               .getBlockState(new BlockPos((int)Math.floor(var7.x), (int)Math.floor(var7.y), (int)Math.floor(var7.z)));
            Block var9 = var8.getBlock();
            if (var9 instanceof StairBlock) {
               return true;
            }
         }
      }

      return false;
   }

}
