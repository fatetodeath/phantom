package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.build.AutoToolModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.module.misc.AntiBotModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.ItemScoreCategory;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.mc.MinecraftClientBridge;
import me.phantom.util.WorldEntityUtil;

import java.util.LinkedList;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
@ModuleMetadata(
   x = 21.5,
   y = -60.0,
   z = -4.5
)
public class AutoMaceModule extends BaseModule {
   public static final int wx = 11;
   public static final int kq = 2;
   public FloatSliderSetting tqj = new FloatSliderSetting(this, GlyphTextUtil.encode("min_fall_distance"), 2.0F, 3.0F, 8.0F);
   public ToggleSetting tok = new ToggleSetting(this, GlyphTextUtil.encode("switch_back"), true);
   public ToggleSetting uqc = new ToggleSetting(this, GlyphTextUtil.encode("after_ground_back"), true);
   public FloatSliderSetting duc = new FloatSliderSetting(this, GlyphTextUtil.encode("after_ground_time"), 0.0F, 0.2F, 1.0F);
   public ToggleSetting te = new ToggleSetting(this, GlyphTextUtil.encode("player_range_check"), true);
   public FloatSliderSetting dbi = new FloatSliderSetting(this, GlyphTextUtil.encode("min_player_distance"), 5.0F, 13.0F, 15.0F);
   public FloatSliderSetting yxs = new FloatSliderSetting(this, GlyphTextUtil.encode("fov"), 20.0F, 90.0F, 180.0F);
   public ToggleSetting xqe = new ToggleSetting(this, GlyphTextUtil.encode("silent"), false);
   public ToggleSetting rub = new ToggleSetting(this, GlyphTextUtil.encode("trigger_windcharge_on_failed_attack"), true);
   public AntiBotModule pg;
   public AimHelperModule ia;
   public WindChargeModule nnc;
   public int ntx = -1;
   public int bpf = -1;
   public int pek = 0;
   public boolean kk;
   public int ur;
   public int hitDelayTicks;
   public boolean yjr;
   public boolean sne;

   public AutoMaceModule() {
      super(ModuleCategory.vne, ClientPlayStyle.ats, GlyphTextUtil.encode("AutoMace"));
      this.markBeta();
      this.dbi.setVisibleWhen(this.te);
      this.duc.setVisibleWhen(this.uqc);
   }

   @Override
   public void onEnable() {
      this.bpf = -1;
      this.pek = 0;
   }

   @Override
   public void onDisable() {
      this.pek = 0;
      if ((Boolean)this.tok.getValue()) {
         this.uba();
      }
   }

   @Override
   public boolean ma() {
      return this.bpf != -1;
   }

   public boolean xl(boolean var1) {
      if (!(Boolean)this.te.getValue() && !var1) {
         return true;
      } else {
         MinecraftClient var2 = MinecraftClient.getInstance();
         ClientPlayerEntity var3 = var2.player;
         Vec3d var4 = var3.getEyePos();
         double var5 = RotationMathUtil.square(5.0);

         for (Entity var8 : WorldEntityUtil.getEntities()) {
            if (var8 instanceof LivingEntity var9 && !(var8 instanceof ClientPlayerEntity)) {
               if (var9.deathTime <= 0 && !this.pg.uei(var8) && !var9.isInvisible()) {
                  Vec3d var10 = var9.getPos();
                  if (!(var4.squaredDistanceTo(var10) > var5)) {
                     return true;
                  }

                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("range"));
                  }
               } else if (PhantomClient.traceEnabled) {
                  PhantomClient.trace(this, GlyphTextUtil.encode("target cancel"), GlyphTextUtil.encode("dead, bot or invisible"));
               }
            }
         }

         return false;
      }
   }

   public void tjy(TickEvent var1) {
      if (PhantomClient.isScreenClosed()) {
         MinecraftClient var2 = MinecraftClient.getInstance();
         ClientPlayerEntity var3 = var2.player;
         this.zb();
         ItemScoreCategory var4 = InventoryModuleBase.getItemCategory(var3.getMainHandStack());
         if (var4 == ItemScoreCategory.yaq) {
            this.jui();
         }
      }
   }

   public void zb() {
      MinecraftClient var1 = MinecraftClient.getInstance();
      ClientPlayerEntity var2 = var1.player;
      boolean var3 = var2.isOnGround();
      if (this.bpf == -1) {
         this.pek = 0;
         if (PhantomClient.fallDistance > 2.0 && this.xl(false)) {
            int var4 = var2.getInventory().getSelectedSlot();
            int var5 = AutoToolModule.nn(var0 -> var0.getItem() == Items.MACE ? 1.0F : 0.0F);
            if (var5 != -1 && var5 != var4) {
               this.bpf = var4;
               this.ntx = var5;
               var2.getInventory().setSelectedSlot(var5);
            }
         }
      } else if (var2.getInventory().getSelectedSlot() != this.ntx) {
         this.bpf = -1;
         this.ntx = -1;
      } else if (var3 && (Boolean)this.uqc.getValue()) {
         if (this.pek++ >= (Float)this.duc.getValue() * 20.0F) {
            this.uba();
         }
      } else if ((Boolean)this.uqc.getValue()) {
         this.pek = 0;
      }
   }

   public void jui() {
      MinecraftClient var1 = MinecraftClient.getInstance();
      ClientPlayerEntity var2 = var1.player;
      if (!var2.isOnGround() && !(PhantomClient.fallDistance <= ((Float)this.tqj.getValue()).floatValue())) {
         LinkedList<BlockPos> var3 = new LinkedList<>();
         BlockView.collectCollisionsBetween(
            new Vec3d(var2.lastX, var2.lastY, var2.lastZ), var2.getPos(), var2.getBoundingBox(), (var1x, var2x) -> var3.add(var1x)
         );

         for (BlockPos var5 : var3) {
            BlockState var6 = var2.getWorld().getBlockState(var5);
            if (var6 != null && var6.getBlock() instanceof CobwebBlock) {
               this.yjr = false;
               this.sne = false;
               return;
            }
         }

         Vec3d var20 = var2.getEyePos();
         float var21 = var2.getYaw();
         double var22 = Double.MAX_VALUE;
         Vec2f var8 = null;
         Entity var9 = null;
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace("AutoMace", "entity", "checking");
         }

         for (Entity var11 : WorldEntityUtil.getEntities()) {
            if (PhantomClient.traceEnabled) {
               PhantomClient.trace("AutoMace", "entity", var11.toString());
            }

            if (var11 instanceof LivingEntity var12 && !(var11 instanceof ClientPlayerEntity)) {
               if (var12.deathTime <= 0 && !this.pg.uei(var11) && !var12.isInvisible()) {
                  if (PhantomClient.targetSelector.csu(var11)) {
                     Vec3d var13 = var11.getPos().add(0.0, var11.getHeight() / 4.0F * 3.0F, 0.0);
                     double var14 = var13.squaredDistanceTo(var20);
                     if (!(var14 > 25.0)) {
                        Vec2f var16 = RotationMathUtil.getRotationTo(var20, var13);
                        if (!Float.isNaN(var16.x) && !Float.isNaN(var16.y) && var2.canSee(var11)) {
                           float var17 = RotationMathUtil.angleDifference(var16.x, var21);
                           if (!(var17 > (Float)this.yxs.getValue()) && (var8 == null || var22 > var14)) {
                              var22 = var14;
                              var9 = var11;
                              var8 = var16;
                           }
                        }
                     }
                  } else if (PhantomClient.traceEnabled) {
                     PhantomClient.trace("AutoMace", "entity", "filtered");
                  }
               } else if (PhantomClient.traceEnabled) {
                  PhantomClient.trace("AutoMace", "entity", "invisible");
               }
            }
         }

         if (var8 != null) {
            if (this.hitDelayTicks == 0) {
               this.hitDelayTicks = 2;
               this.ur = 0;
            }

            float var23 = Math.min(this.ur++, this.hitDelayTicks) / (1.0F * this.hitDelayTicks);
            float var24 = RotationMathUtil.angleDifference(var8.x, var2.getYaw()) * var23;
            float var25 = (var8.y - var2.getPitch()) * var23;
            double var26 = 3.0;
            Vec3d var27 = var20.add(RotationMathUtil.getLookVector(var2.getYaw() + var24, var2.getPitch() + var25).multiply(3.0));
            Box var28 = var9.getBoundingBox().expand(var9.getTargetingMargin());
            Optional var18 = var28.raycast(var20, var27);
            boolean var19 = var28.contains(var20) || var18.isPresent() && var20.squaredDistanceTo((Vec3d)var18.get()) <= var26 * var26;
            if (var19 && this.kk) {
               ((MinecraftClientBridge)MinecraftClient.getInstance()).getInteractionManager().attackEntity(var2, var9);
               var2.swingHand(Hand.MAIN_HAND);
               this.kk = false;
               this.sne = true;
            }

            this.ia.vc(11, var24, var25, (Boolean)this.xqe.getValue());
            this.kk = true;
            this.yjr = true;
         } else {
            this.ur = 0;
            this.hitDelayTicks = 2;
            this.yjr = false;
            this.sne = false;
         }
      } else {
         this.ur = 0;
         this.hitDelayTicks = 0;
         if (this.yjr && !this.sne && this.nnc.isEnabled() && (Boolean)this.rub.getValue()) {
            this.nnc.qz();
         }

         this.yjr = false;
         this.sne = false;
      }
   }

   public void uba() {
      MinecraftClient var1 = MinecraftClient.getInstance();
      ClientPlayerEntity var2 = var1.player;
      this.pek = 0;
      if (this.bpf != -1) {
         var2.getInventory().setSelectedSlot(this.bpf);
         this.bpf = -1;
      }
   }
}
