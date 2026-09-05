package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
@ModuleMetadata(
   x = -20.5,
   y = -60.0,
   z = 5.5
)
public class CrystalPlacerModule extends BaseModule {
   public FloatSliderSetting tah = new FloatSliderSetting(this, GlyphTextUtil.encode("range"), 3.0F, 4.0F, 9.0F);
   public AimHelperModule ia;
   public int[] yqt;

   public CrystalPlacerModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("CrystalPlacer"));
      this.markClipboardHint();
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      Vec3d var3 = var2.getEyePos();
      double var4 = Math.pow(((Float)this.tah.getValue()).floatValue(), 2.0);
      byte var6 = 6;
      if (this.yqt == null || this.yqt.length != 13) {
         int[] var7 = new int[13];

         for (int var8 = 0; var8 < 6; var8++) {
            var7[var8 * 2 + 1] = var8;
            var7[var8 * 2 + 2] = -var8;
         }

         this.yqt = var7;
      }

      for (Entity var14 : var2.clientWorld.getEntities()) {
         if (var14 instanceof PlayerEntity && !(var14 instanceof ClientPlayerEntity)) {
            Vec3d var9 = var14.getPos().add(0.0, var14.getHeight() / 2.0F, 0.0);
            if (!(var9.squaredDistanceTo(var3) >= var4)) {
               for (int var10 = 0; var10 <= 6; var10++) {
                  for (int var11 = 0; var11 <= 6; var11++) {
                     int var12 = 0;

                     while (var12 <= 6) {
                        var12++;
                     }
                  }
               }
            }
         }
      }
   }
}
