package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.world.BlockCollisionShapeEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.world.level.block.WebBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.shapes.Shapes;

@ModuleMetadata(
   x = -20.5,
   y = -60.0,
   z = 5.5
)
public class AntiWebModule extends BaseModule {
   public AntiWebModule() {
      super(ModuleCategory.wld, ClientPlayStyle.ats, GlyphTextUtil.encode("AntiWeb"));
      this.markClipboardHint();
   }

   public void olx(BlockCollisionShapeEvent var1) {
      if (var1.kp() instanceof WebBlock && Minecraft.getInstance().player.onGround() && Minecraft.getInstance().player.getDeltaMovement().y <= 0.0) {
         var1.yvk(Shapes.block());
      }
   }
}
