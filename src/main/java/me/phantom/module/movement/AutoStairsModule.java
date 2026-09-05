package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
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
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      this.uyn = !var2.isUsingItem() && var2.forwardSpeed > 0.0F && !var2.getAbilities().flying && !var2.getAbilities().allowFlying && this.pmg(var2);
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

   boolean pmg(ClientPlayerEntity var1) {
      Box var2 = var1.getBoundingBox();
      Vec3d var3 = var1.getVelocity();
      double var10004 = var2.minX + var3.x;
      double var10006 = var2.minZ + var3.z;

      for (VoxelShape var5 : MinecraftClient.getInstance()
         .world
         .getBlockCollisions(var1, new Box(var10004, var2.minY, var10006, var2.maxX + var3.x, var2.minY + 1.0 + Math.max(0.0, var3.y), var2.maxZ + var3.z))) {
         if (!var5.isEmpty()) {
            Box var6 = var5.getBoundingBox();
            Vec3d var7 = var6.getCenter();
            BlockState var8 = MinecraftClient.getInstance()
               .world
               .getBlockState(new BlockPos((int)Math.floor(var7.x), (int)Math.floor(var7.y), (int)Math.floor(var7.z)));
            Block var9 = var8.getBlock();
            if (var9 instanceof StairsBlock) {
               return true;
            }
         }
      }

      return false;
   }

}
