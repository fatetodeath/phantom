package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.misc.SignalEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
public class AimHelperModule extends BaseModule {
   public int hbc;
   public float nz;
   public float mnb;
   public int tkq;
   private float silentYaw;
   public float silentPitch;

   public AimHelperModule() {
      super(ModuleCategory.yy, ClientPlayStyle.zfw, GlyphTextUtil.encode("Aim"));
      this.setEnabled(true, false);
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      float var2 = var1.wt();
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this, GlyphTextUtil.encode("aim_tick"), this.hbc, this.nz, this.mnb);
      }

      if (this.hbc > 0) {
         Minecraft var3 = Minecraft.getInstance();
         LocalPlayer var4 = var3.player;
         double var5 = (Double)var3.options.sensitivity().get() * 0.6F + 0.2F;
         double var7 = var5 * var5 * var5 * 8.0 * 0.15F;
         GameInteractionUtil.tns(var4, (float)((int)(this.nz * var2 / var7) * var7), (float)((int)(this.mnb * var2 / var7) * var7));
      }
   }

   public void ln(SignalEvent var1) {
      if (this.tkq > 0) {
         Minecraft var2 = Minecraft.getInstance();
         LocalPlayer var3 = var2.player;
         double var4 = (Double)var2.options.sensitivity().get() * 0.6F + 0.2F;
         double var6 = var4 * var4 * var4 * 8.0 * 0.15F;
         GameInteractionUtil.tns(var3, (float)((int)(this.silentYaw / var6) * var6), (float)((int)(this.silentPitch / var6) * var6));
      }
   }

   public void tjy(TickEvent var1) {
      this.hbc = 0;
      this.tkq = 0;
   }

   public void vc(int var1, float var2, float var3, boolean var4) {
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this, GlyphTextUtil.encode("aim"), this.hbc, var1, var2, var3, var4);
      }

      if (var4) {
         if (this.tkq < var1) {
            this.silentYaw = var2;
            this.silentPitch = var3;
            this.tkq = var1;
            if (PhantomClient.traceEnabled) {
               PhantomClient.trace(this, GlyphTextUtil.encode("aim_silent"), this.tkq, this.silentYaw, this.silentPitch);
            }
         }
      } else if (this.hbc < var1) {
         this.nz = var2;
         this.mnb = var3;
         this.hbc = var1;
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, GlyphTextUtil.encode("aim_hard"), this.hbc, this.nz, this.mnb);
         }
      }
   }

   @Override
   public boolean lvr() {
      return false;
   }
}
