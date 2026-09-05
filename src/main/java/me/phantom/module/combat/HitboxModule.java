package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.entity.EntityBoundingBoxOverrideEvent;
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
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.opengl.GL11;
public class HitboxModule extends BaseModule {
   public final ToggleSetting vpp = new ToggleSetting(this, GlyphTextUtil.encode("renderBox"), false);
   public final ColorSetting rki = new ColorSetting(this, GlyphTextUtil.encode("inner_boxcolor"), Color.blue);
   public final ColorSetting yhu = new ColorSetting(this, GlyphTextUtil.encode("outer_boxcolor"), Color.red);
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 1, 35, 100);
   public final IntegerSliderSetting rgn = new IntegerSliderSetting(this, GlyphTextUtil.encode("width_increase"), 1, 20, 100);
   public final IntegerSliderSetting rvz = new IntegerSliderSetting(this, GlyphTextUtil.encode("height_increase"), 1, 20, 100);

   public HitboxModule() {
      super(ModuleCategory.vne, ClientPlayStyle.bq, GlyphTextUtil.encode("Hitbox"));
      this.markClipboardHint();
      this.selectSettings(new String[]{GlyphTextUtil.encode("opacity"), GlyphTextUtil.encode("inner_boxcolor"), GlyphTextUtil.encode("outer_boxcolor"), GlyphTextUtil.encode("renderBox")});
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      if ((Boolean)this.vpp.getValue()) {
         try {
            GL11.glDisable(2929);

            for (Entity var3 : WorldEntityUtil.getEntities()) {
               if (!(var3 instanceof ClientPlayerEntity) && PhantomClient.targetSelector.csu(var3)) {
                  Vec3d var4 = GameInteractionUtil.getLerpedPos(var3, var1.tickDelta());
                  Box var5 = var3.getBoundingBox().offset(var4.subtract(var3.getPos()));
                  Box var6 = this.dsu(var3).offset(var4);
                  Render3DUtil.ehw(var1.getMatrices(), VoxelShapes.cuboid(var6), this.yhu.nqq(), ((Integer)this.ep.getValue()).intValue() / 100.0F);
                  Render3DUtil.ehw(var1.getMatrices(), VoxelShapes.cuboid(var5), this.rki.nqq(), ((Integer)this.ep.getValue()).intValue() / 100.0F);
               }
            }
         } finally {
            GL11.glEnable(2929);
         }
      }
   }

   public void vnt(EntityBoundingBoxOverrideEvent var1) {
      Entity var2 = var1.tg();
      if (PhantomClient.targetSelector.csu(var2)) {
         var1.uir(this.dsu(var2).offset(var2.getPos()));
      }
   }

   public Box dsu(Entity var1) {
      Box var2 = var1.getBoundingBox();
      double var3 = var2.getLengthX() / 2.0;
      double var5 = var2.getLengthY() / 2.0;
      double var7 = var2.getLengthZ() / 2.0;
      if (!PhantomClient.targetSelector.csu(var1)) {
         return new Box(-var3, 0.0, -var7, var3, var5, var7);
      } else {
         float var9 = 1.0F + ((Integer)this.rgn.getValue()).intValue() / 100.0F;
         float var10 = ((Integer)this.rvz.getValue()).intValue() / 100.0F;
         return new Box(-var3 * var9, -var5 * var10, -var7 * var9, var3 * var9, var5 * (2.0F + var10), var7 * var9);
      }
   }
}
