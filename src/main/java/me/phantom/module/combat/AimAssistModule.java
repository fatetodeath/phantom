package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.event.TickEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.IntRangeSliderSetting;
import me.phantom.setting.KeybindSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.combat.AimTargetSnapshot;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.ItemScoreCategory;
import me.phantom.util.math.FloatPoint2D;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.WorldEntityUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.nanovg.NanoVG;
@ModuleMetadata(
   x = -20.5,
   y = -60.0,
   z = 5.5
)
public class AimAssistModule extends BaseModule {
   private static int wx = 5;
   public final ToggleSetting iab = new ToggleSetting(this, GlyphTextUtil.encode("has_bind"), true);
   public final KeybindSetting roo = new KeybindSetting(this, GlyphTextUtil.encode("activate_key"), 100000);
   public final ToggleSetting yp = new ToggleSetting(this, GlyphTextUtil.encode("sticky"), false);
   public final IntegerSliderSetting bcz = new IntegerSliderSetting(this, GlyphTextUtil.encode("stickyTimer"), 5, 30, 60);
   public final ModeSetting modeSetting = new ModeSetting(this, GlyphTextUtil.encode("mode"), GlyphTextUtil.encode("lowest health"), GlyphTextUtil.encode("closest"), GlyphTextUtil.encode("farest"), GlyphTextUtil.encode("fov nearest"));
   public final FloatSliderSetting dj = new FloatSliderSetting(this, GlyphTextUtil.encode("strafeextend"), 0.0F, 0.4F, 2.0F);
   public final FloatSliderSetting em = new FloatSliderSetting(this, GlyphTextUtil.encode("reach"), 0.5F, 3.0F, 8.0F);
   public final FloatSliderSetting yxs = new FloatSliderSetting(this, GlyphTextUtil.encode("fov"), 40.0F, 90.0F, 180.0F);
   public final ToggleSetting upn = new ToggleSetting(this, GlyphTextUtil.encode("keeplasttarget"), false);
   public final IntRangeSliderSetting fwt = new IntRangeSliderSetting(this, GlyphTextUtil.encode("maxyaw"), 0, 80, 100, 180);
   public final IntRangeSliderSetting qwh = new IntRangeSliderSetting(this, GlyphTextUtil.encode("maxpitch"), 0, 40, 70, 180);
   public final ToggleSetting fr = new ToggleSetting(this, GlyphTextUtil.encode("preventaimlock"), false);
   public final IntegerSliderSetting ci = new IntegerSliderSetting(this, GlyphTextUtil.encode("aimlockenemycount"), 0, 1, 5);
   public final FloatSliderSetting jwy = new FloatSliderSetting(this, GlyphTextUtil.encode("preventaimlocktreshold"), 0.0F, 0.7F, 1.0F);
   public final IntegerSliderSetting br = new IntegerSliderSetting(this, GlyphTextUtil.encode("assistance"), 0, 10, 100);
   public final ToggleSetting oyh = new ToggleSetting(this, GlyphTextUtil.encode("only_sword"), false);
   public final ToggleSetting jwb = new ToggleSetting(this, GlyphTextUtil.encode("render_debug"), false);
   public final ToggleSetting kub = new ToggleSetting(this, GlyphTextUtil.encode("hurt_time"), false);
   public final ToggleSetting buf = new ToggleSetting(this, GlyphTextUtil.encode("best_hit"), false);
   public final FloatSliderSetting wyr = new FloatSliderSetting(this, GlyphTextUtil.encode("hitbox_multipler_width"), 0.05F, 0.6F, 1.0F);
   public final FloatSliderSetting cf = new FloatSliderSetting(this, GlyphTextUtil.encode("hitbox_multipler_height"), 0.05F, 0.6F, 1.0F);
   public final FloatSliderSetting cry = new FloatSliderSetting(this, GlyphTextUtil.encode("hitbox_offset_y"), 0.0F, 0.5F, 1.0F);
   public VeloCritModule ni;
   public AimHelperModule ia;
   public Entity mlj;
   public Entity ul;
   public float lz;
   public float wik;
   private float iv;
   private float mg;
   public int jzc = 0;
   public long ml;
   public boolean pxy;
   private List<Float> wr = new LinkedList<>();
   private List<AimTargetSnapshot> rn = new LinkedList<>();

   public AimAssistModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("AimAssist"));
      this.wyr.setVisibilitySupplier(() -> !(Boolean)this.buf.getValue());
      this.cf.setVisibilitySupplier(() -> !(Boolean)this.buf.getValue());
      this.cry.setVisibilitySupplier(() -> !(Boolean)this.buf.getValue());
      this.jwy.setVisibleWhen(this.fr);
      this.ci.setVisibleWhen(this.fr);
      this.roo.setVisibleWhen(this.iab);
      this.bcz.setVisibleWhen(this.yp);
   }

   @Override
   public boolean ma() {
      return this.pxy;
   }

   @Override
   public String getValueSuffix() {
      return this.br.getValue() + GlyphTextUtil.encode("%");
   }

   @Override
   public void onEnable() {
      LocalPlayer var1 = Minecraft.getInstance().player;
      this.lz = var1 == null ? 0.0F : var1.getYRot();
      this.wik = var1 == null ? 0.0F : var1.getXRot();
      this.ml = 0L;
   }

   public void wj(WorldRenderOverlayEvent var1) {
      if ((Boolean)this.jwb.getValue()) {
         GuiRenderContext var2 = var1.getContext();
         this.rn.clear();
         this.grh();

         for (AimTargetSnapshot var4 : this.rn) {
            float var5 = Float.MAX_VALUE;
            float var6 = Float.MAX_VALUE;
            float var7 = Float.MIN_VALUE;
            float var8 = Float.MIN_VALUE;
            if (var4.visiblePoints.size() == 8) {
               for (FloatPoint2D var10 : var4.visiblePoints) {
                  var5 = Math.min(var5, var10.axn);
                  var6 = Math.min(var6, var10.oaz);
                  var7 = Math.max(var7, var10.axn);
                  var8 = Math.max(var8, var10.oaz);
               }

               NanoVG.nvgBeginPath(var2.nvg);
               NanoVG.nvgRect(var2.nvg, var5, var6, var7 - var5, var8 - var6);
               NanoVG.nvgFillColor(var2.nvg, PhantomClient.uiManager.interfaceModule.jfj.xam(0.5F, GuiRenderContext.colorScratch));
               NanoVG.nvgFill(var2.nvg);
               NanoVG.nvgBeginPath(var2.nvg);
               NanoVG.nvgRect(var2.nvg, var5, var6, var7 - var5, var8 - var6);
               NanoVG.nvgStrokeColor(var2.nvg, PhantomClient.uiManager.interfaceModule.jfj.xam(1.0F, GuiRenderContext.colorScratch));
               NanoVG.nvgStrokeWidth(var2.nvg, 8.0F);
               NanoVG.nvgStroke(var2.nvg);

               for (FloatPoint2D var12 : var4.visiblePoints) {
                  NanoVG.nvgBeginPath(var2.nvg);
                  NanoVG.nvgCircle(var2.nvg, var12.axn, var12.oaz, 4.0F);
                  NanoVG.nvgStrokeWidth(var2.nvg, 4.0F);
                  NanoVG.nvgStrokeColor(var2.nvg, PhantomClient.uiManager.interfaceModule.mbw.xam(0.8F, GuiRenderContext.colorScratch));
                  NanoVG.nvgStroke(var2.nvg);
               }
            }
         }
      }
   }

   public void tjy(TickEvent var1) {
      if (!this.ni.isEnabled() || !this.ni.ma()) {
         this.rn.clear();
         this.pxy = false;
         if ((Boolean)this.oyh.getValue()) {
            ItemStack var2 = Minecraft.getInstance().player.getMainHandItem();
            ItemScoreCategory var3 = InventoryModuleBase.getItemCategory(var2);
            if (var3 == null || !var3.srz()) {
               return;
            }
         }

         LocalPlayer var12 = Minecraft.getInstance().player;
         float var13 = var12.getYRot();
         float var4 = var12.getXRot();
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, GlyphTextUtil.encode("tick"));
         }

         if (var12.getMainHandItem() != null && var12.getMainHandItem().getItem() instanceof BowItem && var12.getTicksUsingItem() > 0) {
            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("cancel"), GlyphTextUtil.encode("bow"));
            }
         } else {
            if (this.wr.size() > 6) {
               this.wr.remove(0);
            }

            float var5 = (float)Math.sqrt(RotationMathUtil.square(var13 - this.lz - this.iv) + RotationMathUtil.square(var4 - this.wik - this.mg));
            this.wr.add(var5);
            float var6 = 0.0F;
            if (!this.wr.isEmpty()) {
               for (float var8 : this.wr) {
                  var6 += var8;
               }

               var6 /= this.wr.size();
            }

            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("mouseSpeed"), var5, var6);
            }

            this.lz = var13;
            this.wik = var4;
            if ((!(Boolean)this.iab.getValue() || this.roo.rrq()) && PhantomClient.isScreenClosed()) {
               float var15 = ((Integer)this.br.getValue()).intValue() / 100.0F;
               float var16 = Math.min(var6, 20.0F) / 20.0F * (1.0F - var15) + var15;
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, GlyphTextUtil.encode("assistance"), var15, var16);
               }

               AimTargetSnapshot var9 = this.grh();
               if (System.currentTimeMillis() > this.ml) {
                  this.mlj = null;
               }

               if (this.mlj != null) {
                  Entity var10 = Minecraft.getInstance().level.getEntity(this.mlj.getId());
                  if (!(var10 != null && var10 instanceof LivingEntity var11) || var11.deathTime > 0) {
                     this.mlj = null;
                  }
               }

               if (var9 == null) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("cancel"), GlyphTextUtil.encode("entry is null"));
                  }
               } else if ((Boolean)this.fr.getValue() && ((Integer)this.ci.getValue() == 0 || this.jzc <= (Integer)this.ci.getValue()) && var16 > (Float)this.jwy.getValue()) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("cancel"), GlyphTextUtil.encode("prev aim lock"));
                  }
               } else {
                  if ((Boolean)this.yp.getValue()) {
                     if (this.mlj == null) {
                        this.mlj = var9.target;
                        this.ml = System.currentTimeMillis() + ((Integer)this.bcz.getValue()).intValue();
                     } else if (!Objects.equals(this.mlj, var9.target)) {
                        return;
                     }
                  }

                  float var17 = var9.yawDiff;
                  float var19 = var9.pitchDiff;
                  var13 = RotationMathUtil.wrapDegrees(var13) + 180.0F;
                  if (Math.abs(var17) <= 1.0F) {
                     var17 = Math.signum(var17);
                  }

                  if (Math.abs(var19) <= 1.0F) {
                     var19 = Math.signum(var19);
                  }

                  if (var13 >= var9.minX && var13 <= var9.maxX) {
                     var17 = 0.0F;
                  }

                  if (var4 + 90.0F >= var9.minY && var4 + 90.0F <= var9.maxY) {
                     var19 = 0.0F;
                  }

                  var17 = Math.signum(var17) * Math.min(Math.abs(var17), (float)this.fwt.koj());
                  var19 = Math.signum(var19) * Math.min(Math.abs(var19), (float)this.qwh.koj());
                  this.pxy = true;
                  this.iv = var17 * var16;
                  this.mg = var19 * var16;
                  this.ia.vc(wx, this.iv, this.mg, false);
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("aim"), this.iv, this.mg);
                  }
               }
            } else {
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, GlyphTextUtil.encode("cancel"), GlyphTextUtil.encode("not_pressed"));
               }

               this.ul = null;
            }
         }
      }
   }

   public AimTargetSnapshot grh() {
      LocalPlayer var1 = Minecraft.getInstance().player;
      boolean var2 = var1.xxa != 0.0F;
      float var3 = (float)RotationMathUtil.square((Float)this.em.getValue() + (var2 ? (Float)this.dj.getValue() : 0.0F));
      float var4 = var1.getYRot();
      float var5 = var1.getXRot();
      Vec3 var6 = GameInteractionUtil.getLerpedEyePos(var1, PhantomClient.uiManager.getContext().tickDelta);
      this.jzc = 0;
      AimTargetSnapshot var7 = null;
      float var8 = 0.0F;
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this, GlyphTextUtil.encode("findNearestEntity"));
      }

      for (List<Entity> var10 : WorldEntityUtil.getPartitionedEntities()) {
         for (Entity var12 : var10) {
            if (var12 instanceof LivingEntity && !(var12 instanceof LocalPlayer)) {
               LivingEntity var13 = (LivingEntity)var12;
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, GlyphTextUtil.encode("target"), var13.getType().toShortString(), var13.getId());
               }

               if (var13.hurtTime > 0 && (Boolean)this.kub.getValue()) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("hurt time"));
                  }
               } else if (var13.deathTime > 0) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("dead"));
                  }
               } else if (!PhantomClient.targetSelector.csu(var13)) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("filter"));
                  }
               } else {
                  Vec3 var14 = GameInteractionUtil.getLerpedPos(var13, PhantomClient.uiManager.getContext().tickDelta);
                  if (!(var6.distanceToSqr(var14) > var3) && !var13.isInvisible()) {
                     if (var1.hasLineOfSight(var13)) {
                        double var15 = var13.getBbWidth();
                        double var17 = var13.getBbHeight();
                        double var19 = this.buf.getValue() ? 0.5 : ((Float)this.wyr.getValue()).floatValue();
                        double var21 = this.buf.getValue() ? 0.15 : ((Float)this.cf.getValue()).floatValue();
                        AimTargetSnapshot var23 = new AimTargetSnapshot(this);
                        var23.target = var13;
                        float var25 = 0.4F;
                        double var26 = (this.buf.getValue() ? 0.7 : ((Float)this.cry.getValue()).floatValue()) * var17 - var25 * 2.0F;
                        this.poa(
                           var6,
                           var4,
                           var5,
                           var14,
                           -var15 / 2.0 * var19,
                           Math.min(var17, Math.max(0.0, (var25 + var21 * (1.0F - var25)) * var17 + var26)),
                           -var15 / 2.0 * var19,
                           var23
                        );
                        this.poa(
                           var6,
                           var4,
                           var5,
                           var14,
                           var15 / 2.0 * var19,
                           Math.min(var17, Math.max(0.0, (var25 + var21 * (1.0F - var25)) * var17 + var26)),
                           -var15 / 2.0 * var19,
                           var23
                        );
                        this.poa(
                           var6,
                           var4,
                           var5,
                           var14,
                           -var15 / 2.0 * var19,
                           Math.min(var17, Math.max(0.0, (var25 + var21 * (1.0F - var25)) * var17 + var26)),
                           var15 / 2.0 * var19,
                           var23
                        );
                        this.poa(
                           var6,
                           var4,
                           var5,
                           var14,
                           var15 / 2.0 * var19,
                           Math.min(var17, Math.max(0.0, (var25 + var21 * (1.0F - var25)) * var17 + var26)),
                           var15 / 2.0 * var19,
                           var23
                        );
                        this.poa(
                           var6,
                           var4,
                           var5,
                           var14,
                           -var15 / 2.0 * var19,
                           Math.min(var17, Math.max(0.0, (var25 - var21 * (1.0F - var25)) * var17 + var26)),
                           -var15 / 2.0 * var19,
                           var23
                        );
                        this.poa(
                           var6,
                           var4,
                           var5,
                           var14,
                           var15 / 2.0 * var19,
                           Math.min(var17, Math.max(0.0, (var25 - var21 * (1.0F - var25)) * var17 + var26)),
                           -var15 / 2.0 * var19,
                           var23
                        );
                        this.poa(
                           var6,
                           var4,
                           var5,
                           var14,
                           -var15 / 2.0 * var19,
                           Math.min(var17, Math.max(0.0, (var25 - var21 * (1.0F - var25)) * var17 + var26)),
                           var15 / 2.0 * var19,
                           var23
                        );
                        this.poa(
                           var6,
                           var4,
                           var5,
                           var14,
                           var15 / 2.0 * var19,
                           Math.min(var17, Math.max(0.0, (var25 - var21 * (1.0F - var25)) * var17 + var26)),
                           var15 / 2.0 * var19,
                           var23
                        );
                        if ((Boolean)this.yp.getValue() && Objects.equals(this.mlj, var13)) {
                           if (PhantomClient.traceEnabled) {
                              PhantomClient.trace(this, GlyphTextUtil.encode("sticky cancel"), GlyphTextUtil.encode("is sticky"));
                           }

                           return var23;
                        }

                        if (Objects.equals(this.ul, var13)) {
                           if (PhantomClient.traceEnabled) {
                              PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("is last"));
                           }

                           return var23;
                        }

                        if (!(Math.abs(var23.yawDiff) > (Float)this.yxs.getValue())) {
                           this.jzc++;
                           float var28;
                           if (GlyphTextUtil.encode("lowest health").equals(this.modeSetting.getValue())) {
                              var28 = var13.getHealth();
                           } else if (GlyphTextUtil.encode("closest").equals(this.modeSetting.getValue())) {
                              var28 = (float)var6.distanceToSqr(var14);
                           } else if (GlyphTextUtil.encode("farest").equals(this.modeSetting.getValue())) {
                              var28 = (float)(var3 - var6.distanceToSqr(var14));
                           } else {
                              if (!GlyphTextUtil.encode("fov nearest").equals(this.modeSetting.getValue())) {
                                 throw new RuntimeException(GlyphTextUtil.encode("Invalid aimassist mode"));
                              }

                              var28 = (float)Math.sqrt(RotationMathUtil.square(var23.yawDiff / (Float)this.yxs.getValue()) + RotationMathUtil.square(var23.pitchDiff / 180.0F + 0.5F));
                           }

                           if (PhantomClient.traceEnabled) {
                              PhantomClient.trace(this, GlyphTextUtil.encode("target_sort"), var8, var28);
                           }

                           if (var7 == null || var8 > var28) {
                              var7 = var23;
                              var8 = var28;
                           }
                        } else if (PhantomClient.traceEnabled) {
                           PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("fov"));
                        }
                     } else if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("trace"));
                     }
                  } else if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("range"));
                  }
               }
            }
         }

         if (var7 != null) {
            this.rn.add(var7);
            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("findNearestEntity_end"), var7 != null);
            }

            this.ul = this.upn.getValue() ? var7.target : null;
            return var7;
         }
      }

      return null;
   }

   public void poa(Vec3 var1, float var2, float var3, Vec3 var4, double var5, double var7, double var9, AimTargetSnapshot var11) {
      Vec3 var12 = var4.add(var5, var7, var9);
      if ((Boolean)this.jwb.getValue() && Render3DUtil.worldToScreen(var12.subtract(var1), true, RotationMathUtil.projection)) {
         var11.visiblePoints.add(new FloatPoint2D(RotationMathUtil.projection.axn, RotationMathUtil.projection.oaz));
      }

      Vec2 var13 = RotationMathUtil.getRotationTo(var1, var12);
      float var14 = RotationMathUtil.wrapDegrees(var13.x) + 180.0F;
      float var15 = var13.y + 90.0F;
      var11.minX = Math.min(var14, var11.minX);
      var11.maxX = Math.max(var14, var11.maxX);
      var11.minY = Math.min(var15, var11.minY);
      var11.maxY = Math.max(var15, var11.maxY);
      float var16 = RotationMathUtil.angleDifference(var13.x, var2);
      float var17 = var13.y - var3;
      if (Math.abs(var11.yawDiff) > Math.abs(var16)) {
         var11.yawDiff = var16;
      }

      if (Math.abs(var11.pitchDiff) > Math.abs(var17)) {
         var11.pitchDiff = var17;
      }

      if (var11.maxYawDiff == 0.0F || Math.abs(var11.maxYawDiff) < Math.abs(var16)) {
         var11.maxYawDiff = var16;
      }

      if (var11.maxPitchDiff == 0.0F || Math.abs(var11.maxPitchDiff) < Math.abs(var17)) {
         var11.maxPitchDiff = var17;
      }
   }
}
