package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.KeyBindingTimesPressedAccessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
public class FlyModule extends BaseModule {
   public final IntegerSliderSetting gla = new IntegerSliderSetting(this, GlyphTextUtil.encode("speed"), 1, 100, 300);
   public final FloatSliderSetting ztd = new FloatSliderSetting(this, GlyphTextUtil.encode("draft"), 0.0F, 0.6F, 1.0F);
   private Vec3d xia;
   public boolean kn;
   private boolean rwi;
   public boolean yws;
   public boolean lwd;
   private boolean wud;
   private boolean ai;

   public FlyModule() {
      super(ModuleCategory.wld, ClientPlayStyle.bq, GlyphTextUtil.encode("Fly"));
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
      this.xia = new Vec3d(0.0, 0.0, 0.0);
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      boolean var3 = false;

      try {
         KeyBindingTimesPressedAccessor var4 = (KeyBindingTimesPressedAccessor)MinecraftClient.getInstance().options.sprintKey;
         var3 = PhantomClient.uiManager.getContext().gml[var4.getBoundKey().getCode()];
      } catch (Throwable var12) {
      }

      float var13 = var2.getYaw();
      float var5 = !this.kn && !this.rwi ? 90.0F : 45.0F;
      if (this.lwd) {
         var13 += this.rwi ? -var5 : var5;
      }

      if (this.yws) {
         var13 += this.rwi ? var5 : -var5;
      }

      if (!this.rwi && (this.yws || this.lwd)) {
         this.kn = true;
      }

      double var6 = 0.25F * (((Integer)this.gla.getValue()).intValue() / 100.0F) * (var3 ? 2.0F : 1.0F);
      double var8 = this.kn == this.rwi ? 0.0 : (this.kn ? var6 : -var6);
      double var10 = Math.toRadians(var13);
      this.xia = this.xia
         .multiply(0.3F + (Float)this.ztd.getValue() * 0.5F)
         .add(-Math.sin(var10) * var8, this.ai == this.wud ? 0.0 : (this.ai ? -var6 : var6) * 1.2, Math.cos(var10) * var8);
      if (this.xia.lengthSquared() <= 0.01) {
         this.xia = new Vec3d(0.0, 0.0, 0.0);
      }

      var2.setSprinting(var3);
      var2.setVelocity(this.xia);
      var2.setOnGround(true);
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
            return;
      }

      var1.ywz(false);
   }
}
