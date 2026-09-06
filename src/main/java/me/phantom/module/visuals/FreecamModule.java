package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.entity.CrosshairTargetUpdateEvent;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.entity.PositionEvent;
import me.phantom.event.render.PreEntityRenderEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.entity.FreecamPlayerClone;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
public class FreecamModule extends BaseModule {
   public final IntegerSliderSetting gla = new IntegerSliderSetting(this, GlyphTextUtil.encode("speed"), 20, 100, 300);
   public final FloatSliderSetting ztd = new FloatSliderSetting(this, GlyphTextUtil.encode("draft"), 0.0F, 0.6F, 1.0F);
   public final ToggleSetting mn = new ToggleSetting(this, GlyphTextUtil.encode("damage_disable"), true);
   private FreecamPlayerClone uo;
   public Vec3 xvi;
   public Vec3 xnv;
   private Vec3 xia;
   private boolean kn;
   private boolean rwi;
   private boolean yws;
   private boolean lwd;
   public boolean wud;
   public boolean ai;
   private boolean pyx;
   public float qcc;

   public FreecamModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("Freecam"));
      this.selectAllSettings();
      this.markClipboardHint();
   }

   @Override
   public void onDisable() {
      if (this.uo != null) {
         LocalPlayer var1 = Minecraft.getInstance().player;
         if (var1 != null) {
            this.uo.discard();
         }
      }
   }

   @Override
   public void onEnable() {
      this.xnv = new Vec3(0.0, 0.0, 0.0);
      this.xvi = new Vec3(0.0, 0.0, 0.0);
      this.xia = new Vec3(0.0, 0.0, 0.0);
      this.uo = new FreecamPlayerClone(this);
      Minecraft.getInstance().level.addEntity(this.uo);
      this.qcc = Minecraft.getInstance().player.getHealth();
   }

   public void lud(PreEntityRenderEvent var1) {
      if (var1.tg() instanceof FreecamPlayerClone) {
         var1.setCancelled(true);
      }
   }

   public void tjy(TickEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      if ((Boolean)this.mn.getValue() && this.qcc > var2.getHealth()) {
         this.setEnabled(false, false);
      } else {
         this.xvi = this.xnv;
         float var3 = var2.getYRot();
         float var4 = !this.kn && !this.rwi ? 90.0F : 45.0F;
         if (this.lwd) {
            var3 += this.rwi ? -var4 : var4;
         }

         if (this.yws) {
            var3 += this.rwi ? var4 : -var4;
         }

         if (!this.rwi && (this.yws || this.lwd)) {
            this.kn = true;
         }

         double var5 = 0.25F * (((Integer)this.gla.getValue()).intValue() / 100.0F * 0.6F + 0.3F) * (this.pyx ? 1.4F : 1.0F);
         double var7 = this.kn == this.rwi ? 0.0 : (this.kn ? var5 : -var5);
         double var9 = Math.toRadians(var3);
         this.xia = this.xia
            .scale(0.3F + (Float)this.ztd.getValue() * 0.5F)
            .add(-Math.sin(var9) * var7, this.ai == this.wud ? 0.0 : (this.ai ? -var5 : var5) * 1.2, Math.cos(var9) * var7);
         if (this.xia.lengthSqr() <= 0.01) {
            this.xia = new Vec3(0.0, 0.0, 0.0);
         }

         this.xnv = this.xnv.add(this.xia);
      }
   }

   public void sau(KeyStateEvent var1) {
      String var2 = var1.by().getName();
      switch (var2) {
         case "key.forward":
            this.kn = var1.re();
            break;
         case "key.back":
            this.rwi = var1.re();
            break;
         case "key.left":
            this.yws = var1.re();
            break;
         case "key.right":
            this.lwd = var1.re();
            break;
         case "key.jump":
            this.wud = var1.re();
            break;
         case "key.sneak":
            this.ai = var1.re();
            break;
         case "key.sprint":
            this.pyx = var1.re();
            break;
         default:
            return;
      }

      var1.ywz(false);
   }

   public void sza(CrosshairTargetUpdateEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      float var3 = var1.oy();
      Vec3 var4 = var2.getPosition(var3)
         .add(Mth.lerp(var3, this.xvi.x, this.xnv.x), Mth.lerp(var3, this.xvi.y, this.xnv.y) + 1.8, Mth.lerp(var3, this.xvi.z, this.xnv.z));
      Minecraft.getInstance().hitResult = GameInteractionUtil.qmg(var2, var4, var4.add(var2.getViewVector(var3).scale(3.5)));
      var1.setCancelled(true);
   }

   public void gde(PositionEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      float var3 = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
      var1.edk(
         var2.getPosition(var3)
            .add(
               Mth.lerp(var3, this.xvi.x, this.xnv.x),
               Mth.lerp(var3, this.xvi.y, this.xnv.y) + 1.8,
               Mth.lerp(var3, this.xvi.z, this.xnv.z)
            )
      );
   }
}
