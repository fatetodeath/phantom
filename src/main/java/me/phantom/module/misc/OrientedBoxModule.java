package me.phantom.module.misc;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.visuals.EspModule;
import me.phantom.render.Render3DUtil;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.opengl.GL11;

public class OrientedBoxModule extends AbstractEspFeature {
   public boolean byx = false;

   public OrientedBoxModule(EspModule var1) {
      super(GlyphTextUtil.encode("OBox"), var1);
   }

   @Override
   public void uu(GuiRenderContext var1, MatrixStack var2) {
      GL11.glDisable(2929);

      for (Entity var4 : this.vsr.ffs(var1)) {
         Vec3d var5 = GameInteractionUtil.getLerpedPos(var4, var1.tickDelta);
         Box var6 = var4.getBoundingBox().offset(var4.getPos().multiply(-1.0));
         VoxelShape var7 = VoxelShapes.cuboid(var6);
         Color var8 = this.ypg(var4).nqq();
         float var9 = var8.getRed() / 255.0F;
         float var10 = var8.getGreen() / 255.0F;
         float var11 = var8.getBlue() / 255.0F;
         var2.push();
         var2.translate(var5.x, var5.y, var5.z);
         var2.multiply(RotationAxis.POSITIVE_Y.rotation(-((float)Math.toRadians(var4.getYaw()))));
         Render3DUtil.szb(var2, var7, var9, var10, var11, ((Integer)this.ep.getValue()).intValue() / 100.0F);
         Render3DUtil.cpb(var2, var7, var9, var10, var11, ((Integer)this.ep.getValue()).intValue() / 100.0F);
         var2.pop();
      }

      GL11.glEnable(2929);
   }
}
