package me.phantom.core.service;

import me.phantom.core.PhantomClient;
import me.phantom.core.module.ModuleCategory;
import me.phantom.gui.widget.ClickGuiCategoryWindow;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.DeveloperModule;
import me.phantom.module.internal.FriendModule;
import me.phantom.module.misc.NameProtectModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.JsonUtils;

import com.google.gson.JsonObject;
import java.util.HashSet;
import net.minecraft.client.Minecraft;

public class ClientConfigManager {
   private String oh;
   public long ulk;
   private String gy;

   public void dn() {
      PhantomClient.cloudApi.ek(this::cpy);
   }

   public void cpy(JsonObject var1) {
      for (BaseModule var5 : PhantomClient.moduleManager.getModules()) {
         var5.setKeybind(0, false);
      }

      if (var1.has(GlyphTextUtil.encode("keybinds"))) {
         JsonObject var12 = var1.get(GlyphTextUtil.encode("keybinds")).getAsJsonObject();
         JsonObject var14 = var1.has(GlyphTextUtil.encode("hold_keybinds")) ? var1.get(GlyphTextUtil.encode("hold_keybinds")).getAsJsonObject() : null;

         for (String var20 : var12.keySet()) {
            try {
               BaseModule var6 = PhantomClient.moduleManager.getModuleByName(var20);
               var6.setKeybind(var12.get(var20).getAsInt(), var14 != null && JsonUtils.itd(var14, var20, false));
            } catch (Throwable var11) {
               PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("ConfigManager"), "Unable to read module config for " + var20, var11);
            }
         }
      }

      BaseModule var13 = PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("UI"));
      if (var13.getKeyCode() == 0) {
         var13.setKeybind(344, false);
      }

      if (var1.has(GlyphTextUtil.encode("modules"))) {
         JsonObject var15 = var1.get(GlyphTextUtil.encode("modules")).getAsJsonObject();

         for (String var21 : var15.keySet()) {
            try {
               JsonObject var23 = var15.get(var21).getAsJsonObject();
               BaseModule var7 = PhantomClient.moduleManager.getModuleByName(var21);
               var7.loadConfig(var23, var7.getCategory() != ModuleCategory.yy, false, false);
            } catch (Throwable var9) {
               PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("ConfigManager"), "Unable to read module config for " + var21, var9);
            }
         }
      }

      if (var1.has(GlyphTextUtil.encode("windows"))) {
         try {
            JsonObject var16 = var1.get(GlyphTextUtil.encode("windows")).getAsJsonObject();

            for (ClickGuiCategoryWindow var22 : PhantomClient.uiManager.opt()) {
               try {
                  JsonObject var24 = var16.get(var22.getCategory().getName()).getAsJsonObject();
                  var22.vpe(var24);
               } catch (Throwable var8) {
                  PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("ConfigManager"), GlyphTextUtil.encode("Failed to load UI config: ") + var22.getCategory().getName(), var8);
               }
            }
         } catch (Throwable var10) {
            PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("ConfigManager"), GlyphTextUtil.encode("Failed to load UI config"), var10);
         }
      }

      if (var1.has(GlyphTextUtil.encode("defaultConfig"))) {
         this.oh = var1.get(GlyphTextUtil.encode("defaultConfig")).getAsString();
         this.tr(this.oh);
      }

      if (JsonUtils.jwk(var1, GlyphTextUtil.encode("lastVersion"), 0) < PhantomClient.buildNumber) {
         PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.personal.loaded_newver"), PhantomClient.username, PhantomClient.version);
         this.fl();
      } else {
         PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.personal.loaded"), PhantomClient.username);
      }
   }

   public void tr(String var1) {
      PhantomClient.cloudApi.cmh(var1, GlyphTextUtil.encode("Default"), var0 -> PhantomClient.scheduleTask(() -> {
         PhantomClient.configManager.ii(var0, true, false, false);
         PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.load.loaded"), GlyphTextUtil.encode("Default"));
      }, 0));
   }

   public void yv() {
      PhantomClient.clientLogger.sdj(GlyphTextUtil.encode("ConfigManager"), GlyphTextUtil.encode("Saving client config"));
      JsonObject var1 = new JsonObject();
      JsonObject var2 = new JsonObject();
      JsonObject var3 = new JsonObject();
      JsonObject var4 = new JsonObject();

      for (BaseModule var8 : PhantomClient.moduleManager.getModules()) {
         var1.add(var8.getName(), var8.saveConfig(var8.getCategory() != ModuleCategory.yy, false));
         if (var8.getKeyCode() != 0) {
            var2.addProperty(var8.getName(), var8.getKeyCode());
            if (var8.isHoldMode()) {
               var3.addProperty(var8.getName(), true);
            }
         }
      }

      if (var2.has(GlyphTextUtil.encode("ui"))) {
         var2.addProperty(GlyphTextUtil.encode("ui"), 21);
      }

      for (ClickGuiCategoryWindow var11 : PhantomClient.uiManager.opt()) {
         var4.add(var11.getCategory().getName(), var11.mqq());
      }

      JsonObject var10 = new JsonObject();
      var10.add(GlyphTextUtil.encode("windows"), var4);
      var10.add(GlyphTextUtil.encode("modules"), var1);
      var10.add(GlyphTextUtil.encode("keybinds"), var2);
      var10.add(GlyphTextUtil.encode("hold_keybinds"), var3);
      var10.addProperty(GlyphTextUtil.encode("lastVersion"), PhantomClient.buildNumber);
      if (this.oh != null) {
         var10.addProperty(GlyphTextUtil.encode("defaultConfig"), this.oh);
      }

      PhantomClient.cloudApi.dd(var10);
   }

   public void update() {
      if (this.ulk != 0L && System.currentTimeMillis() - this.ulk >= 1000L) {
         this.yv();
         this.ulk = 0L;
      }
   }

   public void fl() {
      this.ulk = System.currentTimeMillis();
   }

   public JsonObject jm(boolean var1) {
      JsonObject var2 = new JsonObject();
      JsonObject var3 = new JsonObject();

      for (BaseModule var7 : PhantomClient.moduleManager.getModules()) {
         if (!(var7 instanceof NameProtectModule) || !(var7 instanceof FriendModule)) {
            var3.add(var7.getName(), var7.saveConfig(false, false));
         }
      }

      var2.add(GlyphTextUtil.encode("modules"), var3);
      return var2;
   }

   public void ii(JsonObject var1, boolean var2, boolean var3, Boolean var4) {
      JsonObject var5 = var1.get(GlyphTextUtil.encode("modules")).getAsJsonObject();
      HashSet var6 = new HashSet();

      for (String var8 : var5.keySet()) {
         JsonObject var9 = var5.get(var8).getAsJsonObject();
         var6.add(var8);

         try {
            BaseModule var10 = PhantomClient.moduleManager.getModuleByName(var8);
            if ((var10.getCategory() != ModuleCategory.yxw && var10.getCategory() != ModuleCategory.yy || var2)
               && var10 != null
               && !(var10 instanceof NameProtectModule)
               && !(var10 instanceof DeveloperModule)
               && !(var10 instanceof FriendModule)) {
               var10.loadConfig(var9, false, var3, var4);
            }
         } catch (Throwable var11) {
            PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("ConfigManager"), GlyphTextUtil.encode("Failed to load config element for '") + var8 + GlyphTextUtil.encode("' (") + var9 + GlyphTextUtil.encode(")"), var11);
         }
      }

      for (BaseModule var15 : PhantomClient.moduleManager.getModules()) {
         if (var15.getCategory() != ModuleCategory.yy && !var6.contains(var15.getName()) && !var15.getName().equals(GlyphTextUtil.encode("Nameprotect"))) {
            var15.setEnabled(false, true);
         }
      }

      if ((PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("XRay")).isEnabled() || PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("BlockESP")).isEnabled()) && Minecraft.getInstance().player != null) {
         Minecraft.getInstance().levelRenderer.allChanged();
      }
   }

   public void ex(String var1) {
      this.gy = var1;
   }

   public String vq() {
      return this.gy;
   }

   public void ks(String var1) {
      this.oh = var1;
   }

   public String fs() {
      return this.oh;
   }
}
