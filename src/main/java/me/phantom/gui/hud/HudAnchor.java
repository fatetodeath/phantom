package me.phantom.gui.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.BossBarsAccessor;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.MinecraftClient;

public class HudAnchor {
   public static final HudAnchor obq = new HudAnchor(GlyphTextUtil.encode("TOP_LEFT"));
   public static final HudAnchor vgm = new HudAnchor(GlyphTextUtil.encode("TOP_CENTER"));
   public static final HudAnchor cns = new HudAnchor(GlyphTextUtil.encode("TOP_RIGHT"));
   public static final HudAnchor pwh = new HudAnchor(GlyphTextUtil.encode("MIDDLE_LEFT"));
   public static final HudAnchor qrx = new HudAnchor(GlyphTextUtil.encode("MIDDLE_RIGHT"));
   public static final HudAnchor rgc = new HudAnchor(GlyphTextUtil.encode("BOTTOM_LEFT"));
   public static final HudAnchor uov = new HudAnchor(GlyphTextUtil.encode("BOTTOM_CENTER"));
   public static final HudAnchor aio = new HudAnchor(GlyphTextUtil.encode("BOTTOM_RIGHT"));
   public static final HudAnchor[] oe = new HudAnchor[]{obq, vgm, cns, pwh, qrx, rgc, uov, aio};
   private final String name;
   public List<HudElementBase> sm = new LinkedList<>();

   public HudAnchor(String var1) {
      this.name = var1;
   }

   public static HudAnchor[] vwu() {
      return oe;
   }

   public String vr() {
      return this.name;
   }

   public float dvr(GuiRenderContext var1, float var2) {
      if (this.equals(uov) || this.equals(vgm)) {
         return var1.framebufferWidth / 2 - this.rzd(var1, var2) / 2.0F + 2.5F;
      } else if (this.equals(cns) || this.equals(aio)) {
         return var1.framebufferWidth - 5.0F - this.rzd(var1, var2);
      } else {
         return this.equals(qrx) ? var1.framebufferWidth - 5.0F : 5.0F;
      }
   }

   public float xtp(GuiRenderContext var1, float var2) {
      float var3 = var1.framebufferHeight / (1.0F * var1.aypf);
      if (this.equals(uov)) {
         byte var4 = 40;

         try {
            if (MinecraftClient.getInstance().player.isCreative()) {
               var4 = 23;
            }
         } catch (Throwable var6) {
         }

         return var1.framebufferHeight - 5.0F - (this == uov ? var4 * var3 : 0.0F);
      } else if (this.equals(uov) || this.equals(rgc) || this.equals(aio)) {
         return var1.framebufferHeight - 5.0F - (this == uov ? 40.0F * var3 : 0.0F);
      } else if (this.equals(qrx) || this.equals(pwh)) {
         return var1.framebufferHeight / 2 - 5.0F - this.pcd(var1, var2) / 2.0F;
      } else if (this.equals(vgm)) {
         try {
            return 5.0F + 20.0F * var3 * ((BossBarsAccessor)MinecraftClient.getInstance().inGameHud.getBossBarHud()).getBossBars().size();
         } catch (Throwable var7) {
            return 5.0F;
         }
      } else {
         return 5.0F;
      }
   }

   public float ktw() {
      return !this.equals(uov) && !this.equals(rgc) && !this.equals(aio) ? 0.0F : -1.0F;
   }

   public float rzd(GuiRenderContext var1, float var2) {
      if (!this.equals(pwh) && !this.equals(qrx)) {
         float var6 = 0.0F;

         for (HudElementBase var8 : this.sm) {
            var6 += var8.icg(var1) * var2 + 5.0F;
         }

         return var6;
      } else {
         float var3 = 0.0F;

         for (HudElementBase var5 : this.sm) {
            var3 = Math.max(var3, var5.icg(var1) * var2 + 5.0F);
         }

         return var3;
      }
   }

   public float pcd(GuiRenderContext var1, float var2) {
      if (!this.equals(pwh) && !this.equals(qrx)) {
         float var6 = 0.0F;

         for (HudElementBase var8 : this.sm) {
            var6 = Math.max(var6, var8.yxe(var1) * var2 + 5.0F);
         }

         return var6;
      } else {
         float var3 = 0.0F;

         for (HudElementBase var5 : this.sm) {
            var3 += var5.yxe(var1) * var2 + 5.0F;
         }

         return var3;
      }
   }
}
