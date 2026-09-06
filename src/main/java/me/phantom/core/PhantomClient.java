package me.phantom.core;

import me.phantom.core.module.ModuleManager;
import me.phantom.core.service.ClientConfigManager;
import me.phantom.core.service.CloudApiManager;
import me.phantom.core.service.TranslationManager;
import me.phantom.event.EventManager;
import me.phantom.gui.core.UiManager;
import me.phantom.module.base.BaseModule;
import me.phantom.module.combat.AutoClickerModule;
import me.phantom.module.internal.BugTrackingModule;
import me.phantom.render.VideoManager;
import me.phantom.setting.AbstractSetting;
import me.phantom.util.BufferedClientLogger;
import me.phantom.util.CommandLineSpoofer;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.MinecraftClientBridge;
import me.phantom.util.ScheduledTask;
import me.phantom.util.net.ServerSessionTracker;
import me.phantom.core.module.TargetSelector;

import com.sun.jna.Platform;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PhantomClient {
   public static String[] stringTable;
   public static String version;
   public static int buildNumber;
   public static boolean isDebugBuild;
   public static boolean traceEnabled;
   public static String username;
   public static String userId;
   public static String branch;
   public static String token;
   public static boolean uninjecting;
   public static boolean initialized;
   public static TranslationManager translationManager;
   public static EventManager eventManager;
   public static ModuleManager moduleManager;
   public static TargetSelector targetSelector;
   public static UiManager uiManager;
   public static BufferedClientLogger clientLogger;
   public static ClientConfigManager configManager;
   public static CloudApiManager cloudApi;
   public static VideoManager videoManager;
   public static boolean sendingPacket;
   public static ServerSessionTracker sessionTracker;
   public static List<ScheduledTask> scheduledTasks;
   private static boolean wasInWorld;
   public static long reservedLong;
   public static long lastThreadScanTime;
   public static Entity renderedEntity;
   public static boolean effectRenderActive;
   public static double fallDistance;

   public static boolean isScreenClosed() {
      return Minecraft.getInstance() != null && Minecraft.getInstance().screen == null;
   }

   public static boolean isInWorld() {
      return Minecraft.getInstance().level != null && Minecraft.getInstance().player != null;
   }

   private static void loadBuildInfo() {
      version = "2.27 (rel-297)";
      buildNumber = 297;
      username = "lucky";
      branch = "master";
      token = "dafe4c8d072137138115d67685a23d01";
      isDebugBuild = false;
      traceEnabled = false;
   }

   public static String getString(int var0) {
      return stringTable[var0 ^ -559038737];
   }

   public static void initialize() {
      if (!uninjecting) {
         if (!initialized) {
            if (isDebugBuild) {
               try {
                  System.setProperty(
                     GlyphTextUtil.plain("org.lwjgl.librarypath"), System.getProperty(GlyphTextUtil.plain("org.lwjgl.librarypath"), GlyphTextUtil.plain("")) + GlyphTextUtil.plain(";../res/natives_arm64/")
                  );
               } catch (Throwable var7) {
               }
            }

            try {
               clientLogger = new BufferedClientLogger();
               sessionTracker = new ServerSessionTracker();
               cloudApi = new CloudApiManager();
               translationManager = new TranslationManager();
               eventManager = new EventManager();
               moduleManager = new ModuleManager();
               targetSelector = new TargetSelector(moduleManager);
               uiManager = new UiManager();
               configManager = new ClientConfigManager();
               configManager.dn();
               videoManager = new VideoManager();
               moduleManager.registerModules();
               CommandLineSpoofer.cyx();
            } catch (Throwable var5) {
               fatalError(GlyphTextUtil.encode("PhantomClient"), GlyphTextUtil.encode("Failed to initialize phantom"), var5);
            } finally {
               initialized = true;
            }
         }
      }
   }

   public static void refresh() throws Throwable {
      translationManager.refresh();

      for (BaseModule var3 : moduleManager.getModules()) {
         var3.refresh();
      }
   }

   public static void update() {
      try {
         Minecraft var0 = Minecraft.getInstance();
         LocalPlayer var1 = var0.player;
         if (var1 == null) {
            fallDistance = 0.0;
         } else {
            double var2 = var1.getDeltaMovement().y();
            boolean var4 = var1.onGround();
            if (var2 <= 0.0 && !var4) {
               fallDistance = fallDistance + Math.abs(var2);
            } else {
               var4 = true;
               fallDistance = 0.0;
            }
         }

         for (String var5 : new String[]{GlyphTextUtil.encode("AntiBot"), GlyphTextUtil.encode("InventoryAction"), GlyphTextUtil.encode("PacketJam"), GlyphTextUtil.encode("Friend"), GlyphTextUtil.encode("Aim")}) {
            BaseModule var6 = moduleManager.getModuleByName(var5);
            if (!var6.isEnabled()) {
               var6.setEnabled(true, true);
            }
         }

         if (traceEnabled && BugTrackingModule.cup != null) {
            BugTrackingModule.cup.xuy();
         }

         if (System.currentTimeMillis() - lastThreadScanTime > 1000L) {
            try {
               lastThreadScanTime = System.currentTimeMillis();

               for (Thread var18 : Thread.getAllStackTraces().keySet()) {
                  if (var18.getName().equals(GlyphTextUtil.encode("Attach Listener"))) {
                     try {
                         var18.interrupt();
                     } catch (Throwable var11) {
                     }
                  }
               }
            } catch (Throwable var13) {
            }
         }

         sessionTracker.update();
         LinkedList<ScheduledTask> var17 = new LinkedList<>();
         synchronized (scheduledTasks) {
            for (int var25 = 0; var25 < scheduledTasks.size(); var25++) {
               ScheduledTask var29 = scheduledTasks.get(var25);
               if (var29.ticksRemaining-- <= 0) {
                  var17.add(var29);
               }
            }

            scheduledTasks.removeAll(var17);
         }

         for (ScheduledTask var26 : var17) {
            var26.runnable.run();
         }

         if (!initialized) {
            return;
         }

         if (isInWorld()) {
            if (!wasInWorld) {
               for (BaseModule var32 : moduleManager.getModules()) {
                  if (var32.isEnabled()) {
                     try {
                        if (traceEnabled) {
                           trace(GlyphTextUtil.encode("PhantomClient"), GlyphTextUtil.encode("module_onEnable"), var32.getName());
                        }

                        var32.onEnable();
                     } catch (Throwable var10) {
                        fatalError(GlyphTextUtil.encode("Enabling ") + var32.getName() + GlyphTextUtil.encode(" # Sanitizer"), GlyphTextUtil.encode("Failed to enable module"), var10);
                     }
                  }
               }
            }

            wasInWorld = true;
         } else {
            if (wasInWorld) {
               for (BaseModule var33 : moduleManager.getModules()) {
                  if (var33.isEnabled()) {
                     try {
                        if (traceEnabled) {
                           trace(GlyphTextUtil.encode("PhantomClient"), GlyphTextUtil.encode("module_onDisable"), var33.getName());
                        }

                        var33.onDisable();
                     } catch (Throwable var9) {
                        fatalError(GlyphTextUtil.encode("Disabling ") + var33.getName() + GlyphTextUtil.encode(" # Sanitizer"), GlyphTextUtil.encode("Failed to disable module"), var9);
                     }
                  }
               }
            }

            wasInWorld = false;
         }
      } catch (Throwable var14) {
         fatalError(GlyphTextUtil.encode("Sanitizer"), GlyphTextUtil.encode("Failed to run sanitizer"), var14);
      }

      try {
         uiManager.update();
         configManager.update();
      } catch (Throwable var8) {
         fatalError(GlyphTextUtil.encode("Sanitizer"), GlyphTextUtil.encode("Failed to update phantom"), var8);
      }
   }

   public static void appendDiagnostics(StringBuilder var0) {
      var0.append(GlyphTextUtil.encode("--- Active mods ---\n"));

      for (ModContainer var2 : FabricLoader.getInstance().getAllMods()) {
         var0.append(GlyphTextUtil.encode("\t- ")).append(var2.getMetadata().getName()).append(GlyphTextUtil.encode(" ")).append(var2.getMetadata().getVersion()).append(GlyphTextUtil.encode("\n"));
      }

      var0.append(GlyphTextUtil.encode("\n"));
      var0.append(GlyphTextUtil.encode("--- Enabled modules ---\n"));
      if (moduleManager == null) {
         var0.append(GlyphTextUtil.encode("None, modules are not loaded\n"));
      } else {
         for (BaseModule var4 : moduleManager.getModules()) {
            var0.append(GlyphTextUtil.encode("- ")).append(var4.getName()).append(GlyphTextUtil.encode("\n"));
            var0.append(GlyphTextUtil.encode("- Bind: ")).append(var4.getKeyCode()).append(GlyphTextUtil.encode("\n"));

            for (AbstractSetting var6 : var4.getSettings()) {
               try {
                  var0.append(GlyphTextUtil.encode("\t- ")).append(var6.getName()).append(GlyphTextUtil.encode(" = ")).append(var6.toJson()).append(GlyphTextUtil.encode("\n"));
               } catch (Throwable var8) {
                  var0.append(GlyphTextUtil.encode("\t- "))
                     .append(var6.getName())
                     .append(GlyphTextUtil.encode(" = "))
                     .append(var8)
                     .append(GlyphTextUtil.encode(": "))
                     .append(var8.getMessage())
                     .append(GlyphTextUtil.encode("\n"));
               }
            }
         }

         var0.append(GlyphTextUtil.encode("\n"));
      }
   }

   public static void fatalError(String var0, String var1, Throwable var2) {
      if (clientLogger == null) {
         var2.printStackTrace();
      } else {
         clientLogger.xdk(var0, var1, var2);
      }

      StringBuilder var3 = new StringBuilder();
      var3.append(GlyphTextUtil.encode("\n--- Phantom crash ---\n"));
      var3.append(GlyphTextUtil.encode("\tToken: ")).append(token).append(GlyphTextUtil.encode("\n"));
      var3.append(GlyphTextUtil.encode("\tBranch: ")).append(branch).append(GlyphTextUtil.encode("\n"));
      var3.append(GlyphTextUtil.encode("\tVersion: ")).append(version).append(GlyphTextUtil.encode("\n"));
      var3.append(GlyphTextUtil.encode("\tDebug: ")).append(isDebugBuild).append(GlyphTextUtil.encode("\n"));
      var3.append(GlyphTextUtil.encode("\tUsername: ")).append(username).append(GlyphTextUtil.encode("\n"));
      var3.append(GlyphTextUtil.encode("\tUserID: ")).append(userId).append(GlyphTextUtil.encode("\n"));
      var3.append(GlyphTextUtil.encode("\tLocation: ")).append(var0).append(GlyphTextUtil.encode("\n"));
      var3.append(GlyphTextUtil.encode("\tDescription: ")).append(var1).append(GlyphTextUtil.encode("\n"));
      var3.append(GlyphTextUtil.encode("\n"));
      appendDiagnostics(var3);
      if (var2 != null) {
         try {
            var3.append(GlyphTextUtil.encode("\n--- Caught ---\n"));
            Object var4 = Class.forName("java.io.ByteArrayOutputStream").getConstructor().newInstance();

            try (PrintStream var5 = new PrintStream((OutputStream)var4)) {
               var2.printStackTrace(var5);
            }

            var3.append(var4.toString());
         } catch (Throwable var13) {
         }
      }

      System.err.println(var3.toString());

      try {
         clientLogger.sdj(var0, var3.toString());
      } catch (Throwable var10) {
         System.err.println(var3.toString());
         System.err.println();
         System.err.println(GlyphTextUtil.encode("Failed to write crashlog to logger"));
         var10.printStackTrace();
      }

      if (cloudApi != null && !isDebugBuild) {
         try {
            var3.append(GlyphTextUtil.encode("--- Log ---\n"));
            clientLogger.pvp(var3);
            var3.append(GlyphTextUtil.encode("\n"));
            cloudApi.jh(var3.toString());
         } catch (Throwable var9) {
            System.err.println(GlyphTextUtil.encode("Failed to report crashlog"));
            var9.printStackTrace();
         }
      }

      if (!isDebugBuild) {
         try {
            Thread.sleep(10000L);
         } catch (InterruptedException var8) {
         }

         System.exit(5);
      }
   }

   public static MinecraftClientBridge mc() {
      return (MinecraftClientBridge) Minecraft.getInstance();
   }

   public static void scheduleTask(Runnable var0, int var1) {
      synchronized (scheduledTasks) {
         scheduledTasks.add(new ScheduledTask(var0, var1));
      }
   }

   public static void trace(Object var0, String var1, Object... var2) {
      if (!isDebugBuild || var0 instanceof AutoClickerModule) {
         if (BugTrackingModule.cup.isEnabled()) {
            BugTrackingModule.cup.trace(var0, var1, var2);
         }
      }
   }

   public static void onAttackHook(Player var0, Entity var1) {
   }

   public static float getUiScaleFactor() {
      return Platform.isMac() ? 0.5F : 1.0F;
   }

   static {
      try {
         stringTable = (String[])ClassLoader.getSystemClassLoader()
            .loadClass(
               new String(
                  new byte[]{
                     110,
                     101,
                     116,
                     46,
                     102,
                     97,
                     98,
                     114,
                     105,
                     99,
                     109,
                     99,
                     46,
                     108,
                     111,
                     97,
                     100,
                     101,
                     114,
                     46,
                     105,
                     109,
                     112,
                     108,
                     46,
                     108,
                     97,
                     117,
                     110,
                     99,
                     104,
                     46,
                     107,
                     110,
                     111,
                     116,
                     46,
                     80,
                     73,
                     110,
                     106,
                     101,
                     99,
                     116,
                     111,
                     114
                  }
               )
            )
            .getField(new String(new byte[]{99}))
            .get(null);
      } catch (Throwable var1) {
      }

      loadBuildInfo();
      scheduledTasks = new LinkedList<>();
   }
}
