package me.phantom.gui.hud;

import me.phantom.gui.core.GuiRenderContext;

import java.util.LinkedList;
import java.util.List;

public class NotificationManager {
   private static final long xjt = 5000L;
   private static final int svr = 320;
   private static final int lxvh = 70;
   private static final int hiq = 8;
   public final List<NotificationToast> key = new LinkedList<>();

   public NotificationToast mfs(String var1, Object... var2) {
      NotificationToast var3 = new NotificationToast(this, HudIconGlyph.ang, var1, var2);
      synchronized (this.key) {
         this.key.add(var3);
         return var3;
      }
   }

   public NotificationToast klr(String var1, Object... var2) {
      NotificationToast var3 = new NotificationToast(this, HudIconGlyph.fof, var1, var2);
      synchronized (this.key) {
         this.key.add(var3);
         return var3;
      }
   }

   public NotificationToast tul(String var1, Object... var2) {
      NotificationToast var3 = new NotificationToast(this, HudIconGlyph.sxq, var1, var2);
      synchronized (this.key) {
         this.key.add(var3);
         return var3;
      }
   }

   public NotificationToast hru(String var1, Object... var2) {
      NotificationToast var3 = new NotificationToast(this, HudIconGlyph.xhx, var1, var2);
      synchronized (this.key) {
         this.key.add(var3);
         return var3;
      }
   }

   public void cva(NotificationToast var1) {
      synchronized (this.key) {
         if (this.key.size() > 10) {
            this.key.remove(0);
         }

         this.key.add(var1);
      }
   }

   public void render(GuiRenderContext var1, int var2, int var3, int var4) {
      synchronized (this.key) {
         this.key.removeIf(var0 -> var0.startTime != 0L && System.currentTimeMillis() - var0.startTime > var0.durationMs);
         int var6 = var1.framebufferWidth - 320 - 8;
         byte var7 = 8;
         var1.translate(var6, var7);

         for (int var8 = 0; var8 < this.key.size(); var8++) {
            NotificationToast var9 = this.key.get(var8);
            var9.render(var1, var2, var3, var4);
            var7 += 78;
            var1.translate(0, 78);
         }

         var1.translate(-var6, -var7);
      }
   }
}
