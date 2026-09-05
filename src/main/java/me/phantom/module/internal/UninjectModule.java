package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.gui.screen.UninjectConfirmDialog;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.CommandLineSpoofer;
import me.phantom.util.GlyphTextUtil;

import java.io.File;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class UninjectModule extends BaseModule {
   public ToggleSetting jja = new ToggleSetting(this, GlyphTextUtil.encode("confirmation"), true);

   public UninjectModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("Uninject"));
   }

   @Override
   public void setEnabled(boolean var1, boolean var2) {
      if (var1) {
         super.setEnabled(false, true);
         if ((Boolean)this.jja.getValue()) {
            PhantomClient.uiManager.getContext().activeDialog = new UninjectConfirmDialog(null, var1x -> {
               if (var1x) {
                  hil();
               } else {
                  this.setEnabled(false, true);
               }
            });
         } else {
            hil();
         }
      }
   }

   public static void hil() {
      try {
         String var0 = System.getenv(GlyphTextUtil.plain("APPDATA"));
         new File(var0 + GlyphTextUtil.plain("/ZLWEWCLISRCNXYTQ"));
         File var1 = new File(var0 + GlyphTextUtil.plain("/.clientlauncher/res/lastlogin"));
         var1.delete();
         var1.getParentFile().delete();
         var1.getParentFile().getParentFile().delete();
      } catch (Throwable var4) {
      }

      try {
         for (BaseModule var3 : PhantomClient.moduleManager.getModules()) {
            if (!(var3 instanceof UninjectModule)) {
               var3.setEnabled(false, true);
            }
         }
      } catch (Throwable var5) {
      }

      PhantomClient.uninjecting = true;
      PhantomClient.scheduleTask(() -> {
         PhantomClient.initialized = false;
         PhantomClient.configManager = null;
         PhantomClient.eventManager = null;
         PhantomClient.translationManager = null;
         PhantomClient.clientLogger = null;
         PhantomClient.moduleManager = null;
         PhantomClient.uiManager = null;
         PhantomClient.videoManager = null;
         new Thread(GlyphTextUtil.encode("  ")).start();
         CommandLineSpoofer.cyx();
         Screen var0x = MinecraftClient.getInstance().currentScreen;
         if (var0x != null) {
            var0x.close();
         }

         System.exit(0);
      }, 0);
   }
}
