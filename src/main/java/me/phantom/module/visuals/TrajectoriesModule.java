package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.hud.TrajectoryLandingIndicator;
import me.phantom.module.base.BaseModule;
import me.phantom.render.hud.CountdownBadgeRenderer;
import me.phantom.render.LineStripBuilder;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.setting.MultiToggleDropdownSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.FloatPoint2D;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.combat.ProjectilePathSimulator;
import me.phantom.util.combat.ProjectileType;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.opengl.GL11;
public class TrajectoriesModule extends BaseModule {
   public final ToggleSetting nlg = new ToggleSetting(this, GlyphTextUtil.encode("flyingtrajectories"), true);
   public final ToggleSetting cqp = new ToggleSetting(this, GlyphTextUtil.encode("enemytrajectories"), true);
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 1, 50, 100);
   public final ColorSetting phv = new ColorSetting(this, GlyphTextUtil.encode("farcolor"), 0.33F, 1.0F, 1.0F);
   public final ColorSetting mpe = new ColorSetting(this, GlyphTextUtil.encode("nearcolor"), 0.0F, 1.0F, 1.0F);
   public final ModeSetting gkc = new ModeSetting(this, GlyphTextUtil.encode("nametags"), GlyphTextUtil.encode("both"), GlyphTextUtil.encode("icon"), GlyphTextUtil.encode("text"), GlyphTextUtil.encode("none"));
   public final FloatSliderSetting kvg = new FloatSliderSetting(this, GlyphTextUtil.encode("hoffset"), -1.0F, 0.15F, 1.0F);
   public final FloatSliderSetting wvx = new FloatSliderSetting(this, GlyphTextUtil.encode("warndistance"), 0.0F, 4.0F, 16.0F);
   public final ToggleSetting muv = new ToggleSetting(this, GlyphTextUtil.encode("warn"), true);
   public final ModeSetting icm = new ModeSetting(this, GlyphTextUtil.encode("showposition"), GlyphTextUtil.encode("Impact"), GlyphTextUtil.encode("Current"), GlyphTextUtil.encode("Both"), GlyphTextUtil.encode("None"));
   public final ColorSetting sek = new ColorSetting(this, GlyphTextUtil.encode("blockcolor"), 0.0F, 1.0F, 1.0F);
   public final ColorSetting nvcc = new ColorSetting(this, GlyphTextUtil.encode("entitycolor"), 0.33F, 1.0F, 1.0F);
   public final MultiToggleDropdownSetting uij = new MultiToggleDropdownSetting(
         this,
         GlyphTextUtil.encode("targets"),
         GlyphTextUtil.encode("Crossbow"),
         GlyphTextUtil.encode("Arrow"),
         GlyphTextUtil.encode("Fishing bobber"),
         GlyphTextUtil.encode("Potion"),
         GlyphTextUtil.encode("Exp bottle"),
         GlyphTextUtil.encode("Ender Pearl"),
         GlyphTextUtil.encode("Egg"),
         GlyphTextUtil.encode("Snowball"),
         GlyphTextUtil.encode("Fireball"),
         GlyphTextUtil.encode("Trident")
      )
      .pxn();
   public final FloatPoint2D utv = new FloatPoint2D();
   public final List<CountdownBadgeRenderer> rnz = new LinkedList<>();
   public final List<TrajectoryLandingIndicator> zgr = new LinkedList<>();

   public TrajectoriesModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("Trajectories"));
      this.selectAllSettings();
      this.markClipboardHint();
   }

   public void wj(WorldRenderOverlayEvent var1) {
      GuiRenderContext var2 = var1.getContext();
      this.rnz.forEach(var1x -> var1x.jjc(var2));
      this.zgr.forEach(var1x -> var1x.jjc(var2));
      this.rnz.clear();
      this.zgr.clear();
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      this.rnz.clear();
      this.zgr.clear();
      float var2 = var1.tickDelta();
      ClientWorld var3 = MinecraftClient.getInstance().world;
      ClientPlayerEntity var4 = MinecraftClient.getInstance().player;
      float var5 = GameInteractionUtil.getLerpedYaw(var4, var2);

      for (Entity var7 : WorldEntityUtil.getEntities()) {
         if (!(var7 instanceof ClientPlayerEntity) && (!(var7 instanceof PlayerEntity) || !(Boolean)this.cqp.getValue())) {
            ProjectileType var20 = bf(var7);
            if (var20 != null && this.fho(var20)) {
               this.exi(var1.getMatrices(), var3, var1.yq(), var5, null, var7, var20, var7.getPos(), var7.getPos().subtract(var7.lastX, var7.lastY, var7.lastZ), var2);
            }
         } else {
            PlayerEntity var8 = (PlayerEntity)var7;

            for (ItemStack var12 : new ItemStack[]{var8.getMainHandStack(), var8.getOffHandStack()}) {
               if (var12 != null) {
                  Item var13 = var12.getItem();
                  ProjectileType var14 = aok(var13);
                  if (var14 != null && this.fho(var14)) {
                     Vec3d var15 = var8.getPos().add(0.0, var8.getStandingEyeHeight() - 0.1F, 0.0);
                     float var16 = GameInteractionUtil.getLerpedYaw(var8, var2);
                     float var17 = GameInteractionUtil.getLerpedPitch(var8, var2);
                     float var18 = var14.ayy(var8);
                     if (!(var18 <= 0.0F)) {
                        Vec3d var19 = RotationMathUtil.getLookVector(var16, var17).multiply(var18);
                        this.exi(var1.getMatrices(), var3, var1.yq(), var5, var7, null, var14, var15, var19, var2);
                     }
                  }
               }
            }
         }
      }
   }

   public boolean fho(ProjectileType var1) {
      return this.uij.yue(var1.name);
   }

   public static ProjectileType aok(Item var0) {
      if (var0 instanceof BowItem) {
         return ProjectileType.qju;
      } else if (var0 instanceof CrossbowItem) {
         return ProjectileType.zds;
      } else if (var0 instanceof TridentItem) {
         return ProjectileType.luu;
      } else if (var0 instanceof SplashPotionItem) {
         return ProjectileType.xxo;
      } else if (var0 instanceof ExperienceBottleItem) {
         return ProjectileType.wih;
      } else if (var0 instanceof EnderPearlItem) {
         return ProjectileType.olm;
      } else if (var0 instanceof EggItem) {
         return ProjectileType.xtu;
      } else if (var0 instanceof FireChargeItem) {
         return ProjectileType.voe;
      } else if (var0 instanceof SnowballItem) {
         return ProjectileType.rci;
      } else {
         return var0 instanceof FishingRodItem ? ProjectileType.za : null;
      }
   }

   public static ProjectileType bf(Entity var0) {
      if (var0 instanceof FishingBobberEntity) {
         return ProjectileType.za;
      } else if (var0 instanceof PotionEntity) {
         return ProjectileType.xxo;
      } else if (var0 instanceof ExperienceBottleEntity) {
         return ProjectileType.wih;
      } else if (var0 instanceof EnderPearlEntity) {
         return ProjectileType.olm;
      } else if (var0 instanceof EggEntity) {
         return ProjectileType.xtu;
      } else if (var0 instanceof SnowballEntity) {
         return ProjectileType.rci;
      } else if (var0 instanceof FireballEntity) {
         return ProjectileType.voe;
      } else if (var0 instanceof SmallFireballEntity) {
         return ProjectileType.voe;
      } else if (var0 instanceof TridentEntity) {
         return ProjectileType.luu;
      } else {
         return var0 instanceof ArrowEntity ? ProjectileType.qju : null;
      }
   }

   public void exi(MatrixStack var1, ClientWorld var2, Vec3d var3, float var4, Entity var5, Entity var6, ProjectileType var7, Vec3d var8, Vec3d var9, float var10) {
      if (!(var9.lengthSquared() <= 0.05)) {
         int var11 = 0;
         ProjectilePathSimulator var12 = new ProjectilePathSimulator(var2, var6, var8, var9, var7);
         EntityHitResult var13 = null;
         LineStripBuilder var14 = !(var5 instanceof ClientPlayerEntity) && this.nlg.getValue() ? LineStripBuilder.create(var1) : null;

         try {
            float var15 = 0.0F;
            float var16 = 0.0F;
            float var17 = 0.0F;

            while (!var12.pln()) {
               if (++var11 > 200) {
                  break;
               }

               Color var18 = this.mpe.nqq();
               Color var19 = this.phv.nqq();
               double var20 = var12.swx().distanceTo(var3);
               float var22 = 0.0F;
               if (var20 < 64.0) {
                  var22 = (float)(var20 / 64.0);
               }

               var15 = var18.getRed() / 255.0F + (var19.getRed() / 255.0F - var18.getRed() / 255.0F) * var22;
               var16 = var18.getGreen() / 255.0F + (var19.getGreen() / 255.0F - var18.getGreen() / 255.0F) * var22;
               var17 = var18.getBlue() / 255.0F + (var19.getBlue() / 255.0F - var18.getBlue() / 255.0F) * var22;
               Vec3d var23 = var12.swx();
               if (var14 != null) {
                  var14.vertex(var23.x, var23.y, var23.z, var15, var16, var17, 1.0F);
               }

               var13 = ProjectileUtil.getEntityCollision(
                  var2,
                  (ProjectileEntity)var6,
                  var23,
                  var23.add(var12.udz()),
                  var12.sq().stretch(var12.udz()),
                  var2x -> var2x.canBeHitByProjectile() && !Objects.equals(var6, var2x) && !Objects.equals(var5, var2x)
               );
               var12.tkm();
               if (var13 != null) {
                  break;
               }
            }

            Vec3d var29 = var12.swx();
            if (var14 != null) {
               var14.vertex(var29.x, var29.y, var29.z, var15, var16, var17, 1.0F);
            }
         } finally {
            if (var14 != null) {
               var14.close();
            }
         }

         Vec3d var27 = var12.swx();
         double var28 = var3.squaredDistanceTo(var8);
         if (var13 != null) {
            Entity var30 = var13.getEntity();
            Vec3d var32 = GameInteractionUtil.getLerpedPos(var30, var10).subtract(var30.lastX, var30.lastY, var30.lastZ);
            GL11.glDisable(2929);
            Render3DUtil.ehw(var1, VoxelShapes.cuboid(var13.getEntity().getBoundingBox().offset(var32)), this.nvcc.nqq(), ((Integer)this.ep.getValue()).intValue() / 100.0F);
            GL11.glEnable(2929);
         } else {
            GL11.glDisable(2929);
            Render3DUtil.ehw(
               var1,
               VoxelShapes.cuboid(
                  new Box(
                     var27.x - var7.hed / 2.0F, var27.y, var27.z - var7.hed / 2.0F, var27.x + var7.hed / 2.0F, var27.y + var7.hwfy, var27.z + var7.hed / 2.0F
                  )
               ),
               this.sek.nqq(),
               ((Integer)this.ep.getValue()).intValue() / 100.0F
            );
            GL11.glEnable(2929);
         }

         boolean var31 = false;
         boolean var33 = false;
         if (((String)this.icm.getValue()).equals(GlyphTextUtil.encode("Both"))) {
            var33 = true;
            var31 = true;
         } else if (((String)this.icm.getValue()).equals(GlyphTextUtil.encode("Both"))) {
            var31 = true;
         } else if (((String)this.icm.getValue()).equals(GlyphTextUtil.encode("Current"))) {
            var33 = true;
         }

         if (var31 && var6 != null && Render3DUtil.igxp(var27.x - var3.x, var27.y - var3.y, var27.z - var3.z, true, this.utv)) {
            this.rnz.add(new CountdownBadgeRenderer(this, var11, this.utv.axn, this.utv.oaz));
         }

         if (var33
            && var6 != null
            && Render3DUtil.igxp(var8.x - var3.x + var9.x * var10, var8.y - var3.y + var9.y * var10, var8.z - var3.z + var9.z * var10, true, this.utv)) {
            this.rnz.add(new CountdownBadgeRenderer(this, var11, this.utv.axn, this.utv.oaz));
         }

         if (var6 != null && var11 > 0 && (Boolean)this.muv.getValue() && var28 <= RotationMathUtil.square(((Float)this.wvx.getValue()).floatValue())) {
            float var34 = RotationMathUtil.getRotationTo(var27, var3).x - var4 + 90.0F;
            this.zgr.add(new TrajectoryLandingIndicator(this, var34));
         }
      }
   }
}
