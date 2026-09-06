package me.phantom.module.misc;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.visuals.EspModule;
import me.phantom.render.Render3DUtil;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import org.lwjgl.opengl.GL11;

public class OrientedBoxModule extends AbstractEspFeature {
   public boolean byx = false;

   public OrientedBoxModule(EspModule var1) {
      super(GlyphTextUtil.encode("OBox"), var1);
   }

   @Override
   public void uu(GuiRenderContext var1, PoseStack var2) {
      GL11.glDisable(2929);

      for (Entity var4 : this.vsr.ffs(var1)) {
         Vec3 var5 = GameInteractionUtil.getLerpedPos(var4, var1.tickDelta);
         AABB var6 = var4.getBoundingBox().move(var4.position().scale(-1.0));
         VoxelShape var7 = Shapes.create(var6);
         Color var8 = this.ypg(var4).nqq();
         float var9 = var8.getRed() / 255.0F;
         float var10 = var8.getGreen() / 255.0F;
         float var11 = var8.getBlue() / 255.0F;
         var2.pushPose();
         var2.translate(var5.x, var5.y, var5.z);
         var2.mulPose(Axis.YP.rotation(-((float)Math.toRadians(var4.getYRot()))));
         Render3DUtil.szb(var2, var7, var9, var10, var11, ((Integer)this.ep.getValue()).intValue() / 100.0F);
         Render3DUtil.cpb(var2, var7, var9, var10, var11, ((Integer)this.ep.getValue()).intValue() / 100.0F);
         var2.popPose();
      }

      GL11.glEnable(2929);
   }
}
