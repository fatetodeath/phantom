package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.packet.PacketEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.internal.FriendModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.IntRangeSliderSetting;
import me.phantom.setting.KeybindSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.ItemScoreCategory;
import me.phantom.util.mc.KeyBindingTimesPressedAccessor;

import java.util.LinkedList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
@ModuleMetadata(
   x = -20.5,
   y = -60.0,
   z = 5.5
)
public class AutoClickerModule extends BaseModule {
   public final ToggleSetting iab = new ToggleSetting(this, GlyphTextUtil.encode("has_bind"), true);
   public final KeybindSetting roo = new KeybindSetting(this, GlyphTextUtil.encode("activate_key"), 100000);
   public final ToggleSetting exh = new ToggleSetting(this, GlyphTextUtil.encode("unlegit"), false);
   public final ToggleSetting dpq = new ToggleSetting(this, GlyphTextUtil.encode("disablemine"), true);
   public final ModeSetting modeSetting = new ModeSetting(this, GlyphTextUtil.encode("mode"), GlyphTextUtil.encode("triggerbot"), GlyphTextUtil.encode("click"));
   public final FloatSliderSetting lgh = new FloatSliderSetting(this, GlyphTextUtil.encode("triggerbot_reach"), 3.0F, 4.0F, 7.0F);
   public final ToggleSetting cv = new ToggleSetting(this, GlyphTextUtil.encode("hitbox_extend"), true);
   public final FloatRangeSliderSetting ium = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("hitbox_extension"), 0.0F, 0.2F, 0.4F, 1.0F);
   public final IntegerSliderSetting fan = new IntegerSliderSetting(this, GlyphTextUtil.encode("hitbox_extension_chance"), 0, 70, 100);
   public final ToggleSetting uwi = new ToggleSetting(this, GlyphTextUtil.encode("shield_block"), false);
   public final ToggleSetting kub = new ToggleSetting(this, GlyphTextUtil.encode("hurt_time"), false);
   public final ToggleSetting ji = new ToggleSetting(this, GlyphTextUtil.encode("cooldown"), true);
   public final IntegerSliderSetting iow = new IntegerSliderSetting(this, GlyphTextUtil.encode("prepercentage"), 70, 95, 100);
   public final FloatRangeSliderSetting cvv = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("additional_cooldown"), 0.0F, 0.05F, 0.1F, 0.5F);
   public final IntRangeSliderSetting ux = new IntRangeSliderSetting(this, GlyphTextUtil.encode("cps"), 1, 5, 10, 20);
   public final ToggleSetting oyh = new ToggleSetting(this, GlyphTextUtil.encode("only_sword"), false);
   public final ToggleSetting vz = new ToggleSetting(this, GlyphTextUtil.encode("hit_selecting"), false);
   public final KeybindSetting zvd = new KeybindSetting(this, GlyphTextUtil.encode("hit_selecting_key"), 100003);
   public boolean qcm;
   public final ToggleSetting bxx = new ToggleSetting(this, GlyphTextUtil.encode("prio_crit"), false);
   public final FloatSliderSetting xb = new FloatSliderSetting(this, GlyphTextUtil.encode("prio_crit_min_air"), 0.0F, 0.0F, 2.0F);
   public final KeybindSetting trs = new KeybindSetting(this, GlyphTextUtil.encode("prio_crit_disable"), 0);
   public ReachModule mk;
   public FriendModule bd;
   public AutoShieldModule hh;
   public VeloCritModule ni;
   public long bw;
   public boolean ewe;
   public boolean wo;
   public boolean pxy;
   public int ind;
   public int wux;
   public boolean ndu;

   public AutoClickerModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("AutoClicker"));
      this.cv.setVisibilitySupplier(() -> ((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("triggerbot")));
      this.ium.setVisibilitySupplier(() -> (Boolean)this.cv.getValue() && ((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("triggerbot")));
      this.fan.setVisibilitySupplier(() -> (Boolean)this.cv.getValue() && ((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("triggerbot")));
      this.lgh.setVisibilitySupplier(() -> ((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("triggerbot")));
      this.cvv.setVisibleWhen(this.ji);
      this.iow.setVisibleWhen(this.ji);
      this.ux.setVisibilitySupplier(() -> !(Boolean)this.ji.getValue());
      this.roo.setVisibleWhen(this.iab);
      this.zvd.setVisibleWhen(this.vz);
      this.xb.setVisibleWhen(this.bxx);
      this.markClipboardHint();
   }

   @Override
   public boolean ma() {
      return this.pxy;
   }

   @Override
   public String getValueSuffix() {
      return this.ux.nsp() + GlyphTextUtil.encode("-") + this.ux.ciz();
   }

   public void igl(PacketEvent var1) {
      if ((Boolean)this.vz.getValue() && this.zvd.rrq()) {
         LocalPlayer var2 = Minecraft.getInstance().player;
         if (((Packet<?>)var1.wmo()) instanceof ClientboundSetHealthPacket var3 && var3.getHealth() < var2.getHealth()) {
            this.qcm = true;
         }
      }
   }

   public void tjy(TickEvent var1) {
      this.pxy = false;
      if (!this.ni.isEnabled() || !this.ni.ma()) {
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, "tick");
         }

         if (this.ind > 0) {
            this.ind--;
         }

         Minecraft var2 = Minecraft.getInstance();
         LocalPlayer var3 = var2.player;
         boolean var4 = this.ndu;
         this.ndu = false;
         if (var3.onGround()) {
            this.wux++;
         } else {
            this.wux = 0;
         }

         if ((Boolean)this.vz.getValue()) {
            if (this.zvd.rrq()) {
               if (!this.qcm) {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, "cancel", "hit_selected");
                  }

                  return;
               }
            } else {
               this.qcm = false;
            }
         } else {
            this.qcm = false;
         }

         if ((Boolean)this.oyh.getValue()) {
            ItemStack var5 = var3.getMainHandItem();
            ItemScoreCategory var6 = InventoryModuleBase.getItemCategory(var5);
            if (var6 == null || !var6.srz()) {
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, "cancel", "only_sword");
               }

               return;
            }
         }

         if ((!(Boolean)this.iab.getValue() || this.roo.rrq()) && PhantomClient.isScreenClosed()) {
            this.pxy = true;
            if ((Boolean)this.dpq.getValue()) {
               var2.options.keyAttack.setDown(false);
               KeyBindingTimesPressedAccessor var11 = (KeyBindingTimesPressedAccessor)var2.options.keyAttack;
               var11.setTimesPressed(0);
            }

            if ((Boolean)this.ji.getValue() && var3.getAttackStrengthScale(0.0F) < ((Integer)this.iow.getValue()).intValue() / 100.0F) {
               this.bw = System.currentTimeMillis() + (int)(1000.0F * this.cvv.fbz());
               this.ewe = false;
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, "cancel", "cooldown");
               }
            } else if (this.bw > System.currentTimeMillis()) {
               this.ewe = false;
               if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, "cancel", "unblock");
               }
            } else {
               if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("triggerbot"))) {
                  Vec3 var12 = var3.getEyePosition();
                  Vec3 var15 = var12.add(var3.getLookAngle().scale(((Float)this.lgh.getValue()).floatValue()));
                  Entity var7 = GameInteractionUtil.getCrosshairEntity();
                  if (var7 == null && (Boolean)this.cv.getValue() && ((Integer)this.fan.getValue()).intValue() >= 100.0 * Math.random()) {
                     float var8 = this.ium.fbz() / 2.0F;
                     var7 = GameInteractionUtil.raycastCombined(var3, var12, var15, var1x -> var1x.getBoundingBox().inflate(var8, var8, var8)) instanceof EntityHitResult var10
                        ? var10.getEntity()
                        : null;
                  }

                  if (var7 == null
                     || !PhantomClient.targetSelector.csu(var7)
                     || (Boolean)this.uwi.getValue() && var7 instanceof Player var19 && var19.isBlocking()
                     || (Boolean)this.kub.getValue() && var7 instanceof LivingEntity var21 && var21.hurtTime > 0) {
                     this.ewe = false;
                     if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, "cancel", "friend or hurt");
                     }

                     return;
                  }
               }

               if (!this.ewe && this.hh.isEnabled() && this.hh.hbb(false)) {
                  this.ewe = true;
               } else {
                  if ((Boolean)this.bxx.getValue() && ((Integer)this.trs.getValue() == 0 || !this.trs.rrq())) {
                     if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, "crit", "start");
                     }

                     boolean var13 = false;
                     LinkedList<BlockPos> var16 = new LinkedList<>();
                     BlockGetter.forEachBlockIntersectedBetween(
                        new Vec3(var3.xo, var3.yo, var3.zo), var3.position(), var3.getBoundingBox(), (var1x, var2x) -> var16.add(var1x)
                     );

                     for (BlockPos var20 : var16) {
                        BlockState var22 = var3.level().getBlockState(var20);
                        if (var22 != null && var22.getBlock() instanceof WebBlock) {
                           var13 = true;
                        }
                     }

                     if (!var3.isUnderWater() && !var3.isInWater() && !var13 && !var3.onClimbable()) {
                        if (!var3.onGround()
                           && !var3.onClimbable()
                           && !var3.isInWater()
                           && !var3.hasEffect(MobEffects.BLINDNESS)
                           && !var3.isPassenger()) {
                           boolean var18 = var3.getAttackStrengthScale(0.5F) > 0.9F && PhantomClient.fallDistance > ((Float)this.xb.getValue()).floatValue();
                           if (!var18) {
                              if (PhantomClient.traceEnabled) {
                                 PhantomClient.trace(this, "notcrit", var3.getAttackStrengthScale(0.5F), PhantomClient.fallDistance);
                              }

                              return;
                           }

                           if (var3.isSprinting()) {
                              if (PhantomClient.traceEnabled) {
                                 PhantomClient.trace(this, "notcrit2", "disable sprint");
                              }

                              this.ind = 3;
                              var3.setSprinting(false);
                              return;
                           }

                           if (!var4) {
                              if (PhantomClient.traceEnabled) {
                                 PhantomClient.trace(this, "crit", "successfully->prio_crit->set falling property");
                              }

                              this.ndu = true;
                              return;
                           }

                           if (PhantomClient.traceEnabled) {
                              PhantomClient.trace(this, "crit", "successfully->prio_crit");
                           }
                        } else if (this.wux <= 2) {
                           if (PhantomClient.traceEnabled) {
                              PhantomClient.trace(this, "ground check", var3.onGround(), var3.onClimbable(), var3.isInWater(), var3.isPassenger());
                           }

                           return;
                        }
                     } else if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, "notcrit2", "submerged");
                     }

                     if (PhantomClient.traceEnabled) {
                        PhantomClient.trace(this, "crit", "success");
                     }
                  }

                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, "success", "act");
                  }

                  this.pxy = true;
                  if ((Boolean)this.exh.getValue()) {
                     PhantomClient.mc().setAttackCooldown(0);
                  }

                  GameInteractionUtil.uoo();
                  if (!(Boolean)this.ji.getValue()) {
                     long var14 = Math.max(-50L, this.bw - System.currentTimeMillis());
                     this.bw = System.currentTimeMillis() + (int)(1000.0F / this.ux.koj()) + var14;
                  }

                  this.ewe = false;
               }
            }
         } else {
            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, "cancel", "has_key_bind_off");
            }

            this.bw = 0L;
            this.ewe = false;
         }
      }
   }

   public void sau(KeyStateEvent var1) {
      if ((var1.by().getName().equals(GlyphTextUtil.plain("key.sprint")) || var1.by().getName().equals(GlyphTextUtil.plain("key.forward"))) && this.ind > 0) {
         var1.ywz(false);
      }
   }
}
