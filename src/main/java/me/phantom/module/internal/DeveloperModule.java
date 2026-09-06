package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.UiRenderEvent;
import me.phantom.event.TickEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.LanguageFile;

import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Map.Entry;
import org.lwjgl.nanovg.NanoVG;
public class DeveloperModule extends BaseModule {
   public static DeveloperModule yyt;
   public ToggleSetting gba = new ToggleSetting(this, "translation_test", true);
   private File hq;
   public File xgm;
   public long zkb = 0L;
   public LanguageFile mt;

   public DeveloperModule() {
      super(ModuleCategory.yy, ClientPlayStyle.bq, GlyphTextUtil.encode("Developer"));
      yyt = this;
   }

   @Override
   public void onEnable() {
      this.mt = null;
      PhantomClient.uiManager.notifications().klr(GlyphTextUtil.encode("module.developer.lang.enabled"));
   }

   @Override
   public JsonObject saveConfig(boolean var1, boolean var2) {
      JsonObject var3 = super.saveConfig(var1, var2);
      var3.addProperty(GlyphTextUtil.encode("enabled"), false);
      return var3;
   }

   @Override
   public void onDisable() {
      if (this.mt != null) {
         this.mt = null;

         try {
            PhantomClient.refresh();
         } catch (Throwable var2) {
         }
      }
   }

   public void up(UiRenderEvent var1) {
      GuiRenderContext var2 = PhantomClient.uiManager.getContext();
      float var3 = var1.getContext().measureTextSize(GlyphTextUtil.encode("DEVELOPER MODE"), 32.0F);
      NanoVG.nvgBeginPath(var2.nvg);
      NanoVG.nvgRoundedRect(var2.nvg, 2.0F, 2.0F, var3 + 12.0F, 36.0F, 4.0F);
      NanoVG.nvgFillColor(var2.nvg, GuiRenderContext.rgba(40, 40, 40, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var2.nvg);
      var1.getContext().drawTextAuto(6.0F, 6.0F, GlyphTextUtil.encode("DEVELOPER MODE"), 32.0F, true, 9, 1.0F, 1.0F);
   }

   public void tjy(TickEvent var1) {
      if ((Boolean)this.gba.getValue()) {
         this.azl();
      } else {
         this.mt = null;
      }
   }

   public void wd() {
      if (this.hq == null || !this.hq.isDirectory()) {
         this.hq = new File(System.getenv(GlyphTextUtil.decode(GlyphTextUtil.encode("APPDATA"))) + GlyphTextUtil.decode(GlyphTextUtil.encode("/pdev")));
         this.hq.mkdirs();
      }
   }

   public void azl() {
      this.wd();
      if (this.xgm == null) {
         this.xgm = new File(this.hq, GlyphTextUtil.plain("test.lang"));
      }

      if (this.zkb == 0L || !this.xgm.exists() || this.zkb != this.xgm.lastModified() || this.mt == null) {
         try {
            this.mt = new LanguageFile("test");
            if (this.xgm.exists()) {
               char var1 = GlyphTextUtil.encode("=").charAt(0);

               for (String var5 : GlyphTextUtil.encodeChars(Files.readString(this.xgm.toPath(), StandardCharsets.UTF_8)).replace(GlyphTextUtil.encode("\r\n"), GlyphTextUtil.encode("\n")).split(GlyphTextUtil.encode("\n"))) {
                  if (!var5.isEmpty() && !var5.startsWith(GlyphTextUtil.encode("#"))) {
                     String var6 = var5.substring(0, var5.indexOf(var1));
                     String var7 = var5.substring(var6.length() + 1);
                     if (this.mt.zim().put(var6, var7) != null) {
                        throw new RuntimeException(GlyphTextUtil.encode("Duplicate key: '") + GlyphTextUtil.decode(var6) + GlyphTextUtil.encode("'"));
                     }
                  }
               }

               this.zkb = this.xgm.lastModified();
            }

            LanguageFile var11 = PhantomClient.translationManager.oja("en_US");
            int var12 = 0;

            for (Entry var15 : var11.zim().entrySet()) {
               if (!this.mt.zim().containsKey(var15.getKey())) {
                  this.mt.zim().put((String)var15.getKey(), (String)var15.getValue());
                  var12++;
               }
            }

            if (var12 > 0) {
               try (PrintStream var14 = new PrintStream(this.xgm)) {
                  this.mt
                     .zim()
                     .entrySet()
                     .stream()
                     .sorted((var0, var1x) -> var0.getKey().compareTo(var1x.getKey()))
                     .forEach(var1x -> var14.println(GlyphTextUtil.decode(var1x.getKey()) + "=" + GlyphTextUtil.decode(var1x.getValue())));
               }

               PhantomClient.uiManager.notifications().klr(GlyphTextUtil.encode("module.developer.lang.reload_added"), var12);
            } else {
               PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("module.developer.lang.reloaded"));
            }

            PhantomClient.refresh();
         } catch (Throwable var10) {
            PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("module.developer.lang.failed"), var10.getMessage());
         }
      }
   }
}
