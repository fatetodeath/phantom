package me.phantom.module.misc;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.visuals.EspModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.opengl.GL11;

public class BoxEspModule extends AbstractEspFeature {
   public final ColorSetting yap = new ColorSetting(this.vsr, GlyphTextUtil.encode("color_box"), 0.35F, 0.9F, 0.9F);

   public BoxEspModule(EspModule var1) {
      super(GlyphTextUtil.encode("Box"), var1);
   }

   @Override
   public void uu(GuiRenderContext var1, MatrixStack var2) {
      GL11.glDisable(2929);

      for (Entity var4 : this.vsr.ffs(var1)) {
         Vec3d var5 = GameInteractionUtil.getLerpedPos(var4, var1.tickDelta);
         Box var6 = var4.getBoundingBox().offset(var5.subtract(var4.getPos()));
         Color var7 = this.ypg(var4).nqq();
         float var8 = var7.getRed() / 255.0F;
         float var9 = var7.getGreen() / 255.0F;
         float var10 = var7.getBlue() / 255.0F;
         VoxelShape var11 = VoxelShapes.cuboid(var6);
         Render3DUtil.szb(var2, var11, var8, var9, var10, ((Integer)this.ep.getValue()).intValue() / 100.0F);
         Render3DUtil.cpb(var2, var11, var8, var9, var10, ((Integer)this.ep.getValue()).intValue() / 100.0F);
      }

      GL11.glEnable(2929);
   }
}
