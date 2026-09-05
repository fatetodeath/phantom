package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.hud.NotificationToast;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import java.util.Random;

public class BugTrackingModule extends BaseModule {
   public static BugTrackingModule cup = null;
   public String uin;
   public NotificationToast fct;
   public StringBuilder tda = new StringBuilder(8388608);
   public ToggleSetting zg = new ToggleSetting(this, GlyphTextUtil.encode("disablecache"), false);
   public int pb;

   public BugTrackingModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("BugTracking"));
      cup = this;
   }

   @Override
   public JsonObject saveConfig(boolean var1, boolean var2) {
      JsonObject var3 = super.saveConfig(var1, var2);
      var3.addProperty(GlyphTextUtil.encode("enabled"), false);
      return var3;
   }

   @Override
   public void onEnable() {
      char[] var1 = new char[8];
      char[] var2 = GlyphTextUtil.encode("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
      Random var3 = new Random();

      for (int var4 = 0; var4 < var1.length; var4++) {
         var1[var4] = var2[var3.nextInt(var2.length)];
      }

      this.uin = new String(var1);
      this.pb = 0;
      this.fct = PhantomClient.uiManager
         .notifications()
         .hru(GlyphTextUtil.encode("module.bugtracking.capturing"), this.uin)
         .withOnClick(var1x -> this.setEnabled(false, true))
         .addButton(GlyphTextUtil.encode("cancel"), GuiRenderContext.iconBuffer(10006), Suppliers.ofInstance(true));
      this.tda.setLength(0);
      this.tda.append(GlyphTextUtil.encode("--- Tracehead ---\n"));
      this.tda.append(GlyphTextUtil.encode("\tToken: ")).append(PhantomClient.token).append(GlyphTextUtil.encode("\n"));
      this.tda.append(GlyphTextUtil.encode("\tBranch: ")).append(PhantomClient.branch).append(GlyphTextUtil.encode("\n"));
      this.tda.append(GlyphTextUtil.encode("\tVersion: ")).append(PhantomClient.version).append(GlyphTextUtil.encode("\n"));
      this.tda.append(GlyphTextUtil.encode("\tDebug: ")).append(PhantomClient.isDebugBuild).append(GlyphTextUtil.encode("\n"));
      this.tda.append(GlyphTextUtil.encode("\tUsername: ")).append(PhantomClient.username).append(GlyphTextUtil.encode("\n"));
      this.tda.append(GlyphTextUtil.encode("\tUserID: ")).append(PhantomClient.userId).append(GlyphTextUtil.encode("\n"));
      PhantomClient.appendDiagnostics(this.tda);
      PhantomClient.clientLogger.pvp(this.tda);
      this.tda.append(GlyphTextUtil.encode("--- Begin trace ---\n\n"));
      PhantomClient.traceEnabled = true;
   }

   public void xuy() {
      if (this.isEnabled()) {
         this.pb++;
         this.fct.description = PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.bugtracking.capturing.text"), this.pb);
         PhantomClient.trace(GlyphTextUtil.encode("PhantomClient"), GlyphTextUtil.encode("tick"), this.pb);
      }
   }

   @Override
   public void onDisable() {
      PhantomClient.traceEnabled = false;
      PhantomClient.cloudApi.tjl(this.uin, this.tda.toString());
      this.fct.createDismissRunnable().run();
      PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("module.bugtracking.done"), this.uin);
   }

   public void trace(Object var1, String var2, Object... var3) {
      if (this.isEnabled()) {
         StringBuilder var4 = new StringBuilder();
         var4.append(System.currentTimeMillis());
         var4.append(GlyphTextUtil.encode("="));
         var4.append(var1 instanceof BaseModule var5 ? var5.getName() : var1 + GlyphTextUtil.encode(""));
         var4.append(GlyphTextUtil.encode("="));
         var4.append(var2);

         for (int var8 = 0; var8 < var3.length; var8++) {
            var4.append(GlyphTextUtil.encode("="));
            var4.append(GlyphTextUtil.encode("") + var3[var8]);
         }

         var4.append(GlyphTextUtil.encode("\n"));
         if (PhantomClient.isDebugBuild) {
            System.out.println(GlyphTextUtil.decode(var4.toString()));
         } else if (this.tda != null) {
            synchronized (this.tda) {
               this.tda.append(var4.toString());
            }
         }
      }
   }

}
