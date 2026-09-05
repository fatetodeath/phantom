package me.phantom.core.service;

import me.phantom.core.PhantomClient;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.JsonUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;

public class CloudApiManager extends Thread {
   private List<Runnable> in = new LinkedList<>();

   public CloudApiManager() {
      String var1 = GlyphTextUtil.encode("PhantomAPI - Scheduler");
      if (PhantomClient.isDebugBuild) {
         var1 = GlyphTextUtil.decode(var1);
      }

      this.setName(var1);
      this.start();
   }

   @Override
   public void run() {
      while (MinecraftClient.getInstance().isRunning()) {
         try {
            Runnable var1;
            synchronized (this.in) {
               var1 = this.in.isEmpty() ? null : this.in.remove(0);
            }

            if (var1 == null) {
               Thread.sleep(5L);
            } else {
               try {
                  var1.run();
               } catch (Throwable var4) {
                  PhantomClient.fatalError(GlyphTextUtil.encode("PhantomAPI"), GlyphTextUtil.encode("Failed to run API task"), var4);
               }
            }
         } catch (Throwable var6) {
            PhantomClient.fatalError(GlyphTextUtil.encode("PhantomAPI"), GlyphTextUtil.encode("Failed to run API scheduler"), var6);
         }
      }
   }

   public void dk(Runnable var1) {
      synchronized (this.in) {
         this.in.add(var1);
      }
   }

   private void uc(String var1, JsonObject var2, Consumer<JsonObject> var3, Consumer<String> var4) {
      this.dk(() -> {
         try (Socket var4x = new Socket(GlyphTextUtil.plain("162.19.229.204"), 5712)) {
            DataOutputStream var5 = new DataOutputStream(var4x.getOutputStream());
            var5.writeInt(57005);
            var5.writeInt(48879);
            var5.writeLong(0L);
            var5.writeByte(1);
            byte[] var6 = GlyphTextUtil.encodeChars(PhantomClient.token).getBytes();
            var5.writeByte(var6.length);
            var5.write(var6);
            byte[] var7 = var1.getBytes();
            var5.writeByte(var7.length);
            var5.write(var7);
            byte[] var8 = var2.toString().getBytes();
            var5.writeInt(var8.length);
            var5.write(var8);
            var5.flush();
            DataInputStream var9 = new DataInputStream(var4x.getInputStream());
            var9.readShort();
            byte[] var10 = new byte[var9.readInt()];
            var9.readFully(var10);
            String var11 = new String(var10, StandardCharsets.UTF_8);

            JsonObject var12;
            try {
               var12 = JsonParser.parseString(var11).getAsJsonObject();
            } catch (Throwable var16) {
               PhantomClient.clientLogger.cim(GlyphTextUtil.encode("API"), GlyphTextUtil.encode("Malformed JSON response: '") + var11 + GlyphTextUtil.encode("'"));
               var4.accept(GlyphTextUtil.encode("Malformed response"));
               return;
            }

            PhantomClient.clientLogger.sdj(GlyphTextUtil.encode("PhantomAPI"), GlyphTextUtil.encode("Responded ") + var1 + GlyphTextUtil.encode(": ") + var12);
            if (var12.has(GlyphTextUtil.encode("error"))) {
               var4.accept(var12.get(GlyphTextUtil.encode("error")).getAsString());
               return;
            } else {
               try {
                  var3.accept(var12);
               } catch (Throwable var15) {
                  PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("API"), GlyphTextUtil.encode("Failed to process callback for '") + var1 + GlyphTextUtil.encode("'"), var15);
                  var4.accept(GlyphTextUtil.encode("Callback error"));
               }
            }
         } catch (Throwable var18) {
            PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("API"), GlyphTextUtil.encode("General request fail for '") + var1 + GlyphTextUtil.encode("'"), var18);
            var4.accept(GlyphTextUtil.encode("General fail"));
         }
      });
   }

   public void tbi(int var1, Consumer<JsonArray> var2) {
      JsonObject var3 = new JsonObject();
      var3.addProperty(GlyphTextUtil.encode("page"), 0);
      var3.addProperty(GlyphTextUtil.encode("sort"), 7);
      var3.addProperty(GlyphTextUtil.encode("title"), GlyphTextUtil.encode(""));
      var3.addProperty(GlyphTextUtil.encode("client"), GlyphTextUtil.encode("Phantom2"));
      var3.addProperty(
         GlyphTextUtil.encode("category"), new String[]{GlyphTextUtil.encode("my"), GlyphTextUtil.encode("newest"), GlyphTextUtil.encode("favorite"), GlyphTextUtil.encode("friends"), GlyphTextUtil.encode("popular"), GlyphTextUtil.encode("visual")}[var1]
      );
      this.uc(GlyphTextUtil.encode("list_config"), var3, var1x -> var2.accept(var1x.get(GlyphTextUtil.encode("configs")).getAsJsonArray()), var1x -> {
         PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("config.listfailed"));
         var2.accept(new JsonArray());
      });
   }

   public void mi(String var1, String var2, int var3, boolean var4, Consumer<String> var5) {
      Runnable var6 = PhantomClient.uiManager.notifications().hru(GlyphTextUtil.encode("config.save.loader"), var2).createDismissRunnable();
      JsonObject var7 = new JsonObject();
      var7.addProperty(GlyphTextUtil.encode("id"), var1);
      var7.addProperty(GlyphTextUtil.encode("title"), var2);
      var7.addProperty(GlyphTextUtil.encode("client"), GlyphTextUtil.encode("Phantom2"));
      var7.addProperty(GlyphTextUtil.encode("visibility"), var3);
      var7.addProperty(GlyphTextUtil.encode("description"), GlyphTextUtil.encode(""));
      var7.addProperty(GlyphTextUtil.encode("visual"), var4);
      var7.addProperty(GlyphTextUtil.encode("branch"), PhantomClient.branch);
      this.uc(GlyphTextUtil.encode("upload_config"), var7, var4x -> {
         PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.save.updated"), var2);
         var6.run();
         var5.accept(var1);
      }, var2x -> {
         PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("config.save.failed"));
         var6.run();
         var5.accept(null);
      });
   }

   public void zy(JsonObject var1, Consumer<String> var2) {
      Runnable var3 = PhantomClient.uiManager.notifications().hru(GlyphTextUtil.encode("config.save.loader"), var1.get(GlyphTextUtil.encode("title")).getAsString()).createDismissRunnable();
      JsonObject var4 = new JsonObject();
      var4.addProperty(GlyphTextUtil.encode("title"), var1.get(GlyphTextUtil.encode("title")).getAsString());
      var4.addProperty(GlyphTextUtil.encode("client"), GlyphTextUtil.encode("Phantom2"));
      var4.addProperty(GlyphTextUtil.encode("visibility"), var1.get(GlyphTextUtil.encode("visibility")).getAsInt());
      var4.addProperty(GlyphTextUtil.encode("visual"), JsonUtils.uzc(var1.get(GlyphTextUtil.encode("visual"))));
      var4.addProperty(GlyphTextUtil.encode("description"), GlyphTextUtil.encode(""));
      var4.addProperty(GlyphTextUtil.encode("content"), var1.get(GlyphTextUtil.encode("content")).getAsString());
      var4.addProperty(GlyphTextUtil.encode("branch"), PhantomClient.branch);
      this.uc(GlyphTextUtil.encode("upload_config"), var4, var3x -> {
         PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.save.create"), var1.get(GlyphTextUtil.encode("title")).getAsString());
         var3.run();
         var2.accept(var3x.get(GlyphTextUtil.encode("id")).getAsString());
      }, var2x -> {
         PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("config.save.failed"));
         var3.run();
         var2.accept(null);
      });
   }

   public void ek(Consumer<JsonObject> var1) {
      Runnable var2 = PhantomClient.uiManager.notifications().hru(GlyphTextUtil.encode("config.personal.loader")).createDismissRunnable();
      this.uc(GlyphTextUtil.encode("phantom_personal"), new JsonObject(), var2x -> {
         try {
            var1.accept(var2x.get(GlyphTextUtil.encode("content")).getAsJsonObject());
         } finally {
            var2.run();
         }
      }, var1x -> {
         PhantomClient.clientLogger.cim(GlyphTextUtil.encode("Config"), GlyphTextUtil.encode("Failed to load default config: ") + var1x);
         PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("config.personal.failed"));
         var2.run();
      });
   }

   public void dd(JsonObject var1) {
      JsonObject var2 = new JsonObject();
      var2.add(GlyphTextUtil.encode("content"), var1);
      this.uc(GlyphTextUtil.encode("phantom_personal"), var2, var0 -> {}, var0 -> {});
   }

   public void jh(String var1) {
      if (PhantomClient.isDebugBuild) {
         throw new RuntimeException(var1);
      } else {
         try {
            CountDownLatch var2 = new CountDownLatch(1);
            JsonObject var3 = new JsonObject();
            var3.addProperty(GlyphTextUtil.encode("clientName"), GlyphTextUtil.encode("Phantom2"));
            var3.addProperty(GlyphTextUtil.encode("branch"), PhantomClient.branch);
            var3.addProperty(GlyphTextUtil.encode("crashreport"), Base64.getEncoder().encodeToString(var1.getBytes(GlyphTextUtil.plain("UTF-8"))));
            this.uc(GlyphTextUtil.encode("crashreport"), var3, var0 -> System.exit(0), var0 -> System.exit(0));
            var2.await();
         } catch (Throwable var7) {
         } finally {
            System.exit(0);
         }
      }
   }

   public void bz(String var1, boolean var2, Runnable var3) {
      JsonObject var4 = new JsonObject();
      var4.addProperty(GlyphTextUtil.encode("id"), var1);
      var4.addProperty(GlyphTextUtil.encode("state"), var2);
      this.uc(GlyphTextUtil.encode("config_favorite"), var4, var1x -> var3.run(), var1x -> var3.run());
   }

   public void cmh(String var1, String var2, Consumer<JsonObject> var3) {
      Runnable var4 = PhantomClient.uiManager.notifications().hru(GlyphTextUtil.encode("config.load.loader"), var2).createDismissRunnable();
      JsonObject var5 = new JsonObject();
      var5.addProperty(GlyphTextUtil.encode("id"), var1);
      this.uc(GlyphTextUtil.encode("download_config"), var5, var2x -> {
         try {
            if (var2x.has(GlyphTextUtil.encode("content"))) {
               var3.accept(JsonParser.parseString(var2x.get(GlyphTextUtil.encode("content")).getAsString()).getAsJsonObject());
            } else {
               PhantomClient.clientLogger.cim(GlyphTextUtil.encode("Config"), GlyphTextUtil.encode("Failed to load config: ") + var2x);
               PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("config.load.failed"));
            }
         } finally {
            var4.run();
         }
      }, var1x -> {
         PhantomClient.clientLogger.cim(GlyphTextUtil.encode("Config"), GlyphTextUtil.encode("Failed to load config: ") + var1x);
         PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("config.load.failed"));
         var4.run();
      });
   }

   public void hl(String var1, String var2, Runnable var3) {
      Runnable var4 = PhantomClient.uiManager.notifications().hru(GlyphTextUtil.encode("config.delete.loader"), var2).createDismissRunnable();
      JsonObject var5 = new JsonObject();
      var5.addProperty(GlyphTextUtil.encode("id"), var1);
      this.uc(GlyphTextUtil.encode("delete_config"), var5, var3x -> {
         try {
            var3.run();
         } finally {
            var4.run();
            PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.delete.loaded"), var2);
         }
      }, var1x -> {
         var3.run();
         PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("config.delete.failed"));
      });
   }

   public void ya(String var1, String var2, JsonObject var3, Runnable var4) {
      Runnable var5 = PhantomClient.uiManager.notifications().hru(GlyphTextUtil.encode("config.save.loader"), var2).createDismissRunnable();
      JsonObject var6 = new JsonObject();
      var6.addProperty(GlyphTextUtil.encode("id"), var1);
      var6.addProperty(GlyphTextUtil.encode("content"), var3.toString());
      this.uc(GlyphTextUtil.encode("update_config"), var6, var3x -> {
         try {
            var4.run();
         } finally {
            PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.save.updated"), var2);
            var5.run();
         }
      }, var1x -> {
         PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("config.save.failed"));
         var5.run();
      });
   }

   public void fb(String var1, String var2) {
      JsonObject var3 = new JsonObject();
      var3.addProperty(GlyphTextUtil.encode("username"), var1);
      var3.addProperty(GlyphTextUtil.encode("server"), var2);
      PhantomClient.clientLogger.sdj(GlyphTextUtil.encode("Telemetry"), GlyphTextUtil.encode("Reported telemetry ") + var3);
      this.uc(GlyphTextUtil.encode("setUsername"), var3, var0 -> {}, var0 -> {});
   }

   public void tjl(String var1, String var2) {
      JsonObject var3 = new JsonObject();
      var3.addProperty(GlyphTextUtil.encode("clientName"), GlyphTextUtil.encode("Phantom2"));
      var3.addProperty(GlyphTextUtil.encode("traceId"), var1);
      var3.addProperty(GlyphTextUtil.encode("branch"), PhantomClient.branch);
      var3.addProperty(GlyphTextUtil.encode("tracereport"), var2);
      PhantomClient.clientLogger.sdj(GlyphTextUtil.encode("Telemetry"), GlyphTextUtil.encode("Reported trace ") + var1);
      this.uc(GlyphTextUtil.encode("tracereport"), var3, var0 -> {}, var0 -> {});
   }

   public byte[] xct(String var1) throws Throwable {
      byte[] var7;
      try (Socket var2 = new Socket(GlyphTextUtil.plain("162.19.229.204"), 5712)) {
         DataOutputStream var3 = new DataOutputStream(var2.getOutputStream());
         var3.writeInt(57005);
         var3.writeInt(48879);
         var3.writeLong(0L);
         var3.writeByte(2);
         byte[] var4 = var1.getBytes(StandardCharsets.UTF_8);
         var3.writeByte(var4.length);
         var3.write(var4);
         var3.flush();
         DataInputStream var5 = new DataInputStream(var2.getInputStream());
         byte[] var6 = new byte[var5.readInt()];
         var5.readFully(var6);
         var7 = var6;
      }

      return var7;
   }
}
