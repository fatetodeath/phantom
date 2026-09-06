package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
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
      LocalPlayer var2 = Minecraft.getInstance().player;
      Vec3 var3 = var2.getEyePosition();
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

      for (Entity var14 : ((ClientLevel)var2.level()).entitiesForRendering()) {
         if (var14 instanceof Player && !(var14 instanceof LocalPlayer)) {
            Vec3 var9 = var14.position().add(0.0, var14.getBbHeight() / 2.0F, 0.0);
            if (!(var9.distanceToSqr(var3) >= var4)) {
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
