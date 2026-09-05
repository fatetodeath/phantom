package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.combat.AimAssistModule;
import me.phantom.module.combat.ReachModule;
import me.phantom.module.internal.FriendModule;
import me.phantom.module.misc.AntiBotModule;
import me.phantom.render.LineStripBuilder;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
public class NearbyWarnModule extends BaseModule {
   public final ModeSetting usv = new ModeSetting(this, GlyphTextUtil.encode("reach"), GlyphTextUtil.encode("default"), GlyphTextUtil.encode("reach"), GlyphTextUtil.encode("aimassist"));
   public final ToggleSetting bvf = new ToggleSetting(this, GlyphTextUtil.encode("ignore_friend"), true);
   public final IntegerSliderSetting yoa = new IntegerSliderSetting(this, GlyphTextUtil.encode("near_range"), 0, 0, 64).kyc(var0 -> var0 == 0 ? GlyphTextUtil.encode("Auto") : GlyphTextUtil.encode("") + var0);
   public final FloatSliderSetting vpk = new FloatSliderSetting(this, GlyphTextUtil.encode("line_thickness"), 0.5F, 4.0F, 8.0F);
   public final ColorSetting imc = new ColorSetting(this, GlyphTextUtil.encode("color"), Color.red);
   public AimAssistModule ycl;
   public ReachModule mk;
   public FriendModule bd;
   public AntiBotModule pg;

   public NearbyWarnModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("NearbyWarn"));
      this.selectAllSettings();
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      float var2 = var1.tickDelta();
      ClientPlayerEntity var3 = MinecraftClient.getInstance().player;
      MatrixStack var4 = var1.getMatrices();
      float var5 = 3.5F;
      if ((Integer)this.yoa.getValue() != 0) {
         var5 = ((Integer)this.yoa.getValue()).intValue();
      } else if (((String)this.usv.getValue()).equals(GlyphTextUtil.encode("aimassist"))) {
         if (this.ycl.isEnabled()) {
            var5 = (Float)this.ycl.em.getValue();
         }
      } else if (((String)this.usv.getValue()).equals(GlyphTextUtil.encode("reach")) && this.mk.isEnabled()) {
         var5 = this.mk.cr.wgp();
      }

      double var6 = 2.0F * ((float)(System.currentTimeMillis() % 500L) / 500.0F);
      Color var8 = this.imc.nqq();

      for (Entity var10 : WorldEntityUtil.getEntities()) {
         if (var10 instanceof PlayerEntity
            && !(var10 instanceof ClientPlayerEntity)
            && !this.pg.uei(var10)
            && (!(Boolean)this.bvf.getValue() || !this.bd.tdv(var10))) {
            Vec3d var11 = GameInteractionUtil.getLerpedPos(var10, var2);
            if (!(var11.squaredDistanceTo(var1.yq()) > var5 * var5) && var3.canSee(var10)) {
               LineStripBuilder var12 = LineStripBuilder.create(var4);

               try {
                  for (int var13 = 0; var13 <= 90; var13++) {
                     double var14 = Math.toRadians(var13 * 4);
                     var12.vertex(
                        var11.x - Math.sin(var14) * var6,
                        var11.y + 1.0,
                        var11.z + Math.cos(var14) * var6,
                        var8.getRed() / 255.0F,
                        var8.getGreen() / 255.0F,
                        var8.getBlue() / 255.0F,
                        1.0F
                     );
                  }
               } catch (Throwable var17) {
                  if (var12 != null) {
                     try {
                        var12.close();
                     } catch (Throwable var16) {
                        var17.addSuppressed(var16);
                     }
                  }

                  throw var17;
               }

               if (var12 != null) {
                  var12.close();
               }
            }
         }
      }
   }

}
