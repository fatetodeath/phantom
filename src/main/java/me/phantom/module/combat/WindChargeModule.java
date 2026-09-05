package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.build.AutoToolModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.module.misc.AntiBotModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.WindChargeItem;
@ModuleMetadata(
   x = 21.5,
   y = -60.0,
   z = -4.5
)
public class WindChargeModule extends BaseModule {
   public static final int wx = 11;
   public ToggleSetting tok = new ToggleSetting(this, GlyphTextUtil.encode("switch_back"), true);
   public ToggleSetting xqe = new ToggleSetting(this, GlyphTextUtil.encode("silent"), true);
   public FloatRangeSliderSetting ag = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("aim_speed"), 0.0F, 0.05F, 0.05F, 0.2F);
   public AntiBotModule pg;
   public AimHelperModule ia;
   public AutoMaceModule mlv;
   public int lb = -1;
   public float hln;
   public float uhx;
   public int xwb = 0;
   public int rri = 0;
   private int ur;
   public int hitDelayTicks;
   public boolean hvq;

   public WindChargeModule() {
      super(ModuleCategory.vne, ClientPlayStyle.ats, GlyphTextUtil.encode("WindCharge"));
      this.markBeta();
   }

   @Override
   public void onEnable() {
      this.hvq = true;
   }

   public void qz() {
      if (this.xnz()) {
         this.xwb = 1;
         this.ur = 0;
         this.hitDelayTicks = 2;
      } else {
         this.xwb = 0;
      }
   }

   public boolean xnz() {
      MinecraftClient var1 = MinecraftClient.getInstance();
      ClientPlayerEntity var2 = var1.player;
      return var2.getOffHandStack() != null && var2.getOffHandStack().getItem() instanceof WindChargeItem
         || var2.getMainHandStack() != null && var2.getMainHandStack().getItem() instanceof WindChargeItem
         || AutoToolModule.nn(var0 -> var0.getItem() instanceof WindChargeItem ? 1.0F : 0.0F) != var2.getInventory().getSelectedSlot();
   }

   public void wl() {
      MinecraftClient var1 = MinecraftClient.getInstance();
      ClientPlayerEntity var2 = var1.player;
      this.lb = -1;
      if (var2.getOffHandStack() == null || !(var2.getOffHandStack().getItem() instanceof WindChargeItem)) {
         this.lb = var2.getInventory().getSelectedSlot();
         var2.getInventory().setSelectedSlot(AutoToolModule.nn(var0 -> var0.getItem() instanceof WindChargeItem ? 1.0F : 0.0F));
         if (!(var2.getMainHandStack().getItem() instanceof WindChargeItem)) {
            this.lb = -1;
         }
      }
   }

   public void uba() {
      MinecraftClient var1 = MinecraftClient.getInstance();
      ClientPlayerEntity var2 = var1.player;
      if (this.lb != -1) {
         var2.getInventory().setSelectedSlot(this.lb);
         this.lb = -1;
      }
   }

   @Override
   public void onDisable() {
      if ((Boolean)this.tok.getValue()) {
         this.uba();
      }
   }

   @Override
   public boolean ma() {
      return this.xwb > 0;
   }

   public void tjy(TickEvent var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      ClientPlayerEntity var3 = var2.player;
      if (var3.isOnGround() || !this.mlv.isEnabled() || !this.mlv.xl(true) || this.hvq) {
         if (this.xnz() && var2.currentScreen == null) {
            if (var3.isOnGround()) {
               this.xwb = 0;
               if (this.hitDelayTicks == 0) {
                  this.ur = 0;
                  this.hitDelayTicks = this.ag.cah();
               }

               float var4 = (90.0F - var3.getPitch()) * (this.hitDelayTicks == 0 ? 1.0F : this.ur / (1.0F * this.hitDelayTicks));
               this.ia.vc(11, 0.0F, var4, (Boolean)this.xqe.getValue());
               if (this.ur < this.hitDelayTicks && this.hitDelayTicks != 0) {
                  this.ur++;
               } else {
                  this.xwb = 1;
                  this.wl();
                  GameInteractionUtil.zgx();
               }
            } else if (this.xwb == 1) {
               this.xwb = 2;
               this.ia.vc(11, 0.0F, 90.0F, (Boolean)this.xqe.getValue());
            } else if (this.xwb == 2) {
               this.ia.vc(11, 0.0F, 90.0F, (Boolean)this.xqe.getValue());
               this.uba();
               this.hvq = false;
               if (this.ur > 0) {
                  this.ur--;
               } else {
                  this.hitDelayTicks = 0;
                  this.xwb = 3;
               }

               float var5 = (90.0F - var3.getPitch()) * (this.ur / (1.0F * this.hitDelayTicks));
               this.ia.vc(11, 0.0F, var5, (Boolean)this.xqe.getValue());
            }
         } else {
            this.uba();
         }
      }
   }

   public void sau(KeyStateEvent var1) {
      if (var1.by().getName().equals("key.jump") && MinecraftClient.getInstance().currentScreen == null && this.xnz() && this.xwb == 1) {
         var1.ywz(true);
      }
   }
}
