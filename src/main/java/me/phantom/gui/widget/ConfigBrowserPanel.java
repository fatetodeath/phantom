package me.phantom.gui.widget;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.AbstractDialog;
import me.phantom.gui.screen.ConfigDeleteConfirmDialog;
import me.phantom.gui.screen.ConfigEditDialog;
import me.phantom.gui.screen.ConfigKeybindsLoadDialog;
import me.phantom.gui.screen.ConfigLoadConfirmDialog;
import me.phantom.gui.screen.ConfigResetConfirmDialog;
import me.phantom.gui.screen.ConfigSaveConfirmDialog;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.setting.AbstractSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.JsonUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.nanovg.NanoVG;

public class ConfigBrowserPanel extends AbstractDialog {
   private int too;
   private float scrollOffset;
   public JsonArray hmp = new JsonArray();
   public boolean is;
   private String ydp = GlyphTextUtil.encode("");
   public boolean fot;

   public ConfigBrowserPanel(boolean var1) {
      super(GlyphTextUtil.encode("Configs"), null);
      this.fot = var1;
      this.rtz(0);
   }

   @Override
   public void fiv() {
      if (this.fot) {
         PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("UI")).setEnabled(false, false);
         MinecraftClient.getInstance().setScreen(null);
      } else {
         super.fiv();
      }
   }

   public void rtz(int var1) {
      if (var1 == 6) {
         PhantomClient.uiManager.getContext().activeDialog = new ConfigEditDialog(this, null);
      } else if (var1 == 7) {
         PhantomClient.uiManager.getContext().activeDialog = new ConfigResetConfirmDialog(this, var0 -> {
            for (BaseModule var4 : PhantomClient.moduleManager.getModules()) {
               var4.getSettings().forEach(AbstractSetting::resetToDefault);
            }

            if (var0) {
               PhantomClient.uiManager.hwv();
               PhantomClient.configManager.cpy(new JsonObject());
               PhantomClient.configManager.yv();
            }
         });
      } else {
         this.too = var1;
         this.is = true;
         PhantomClient.cloudApi.tbi(var1, var1x -> {
            this.is = false;
            this.hmp = var1x;
         });
      }
   }

   @Override
   public void yww() {
      this.rtz(this.too);
   }

   @Override
   public int vgh(int var1, int var2) {
      int var3 = (int)(var2 * 0.9F);
      return var3 / 9 * 16;
   }

   @Override
   public int zch(int var1, int var2) {
      return (int)(var2 * 0.8F);
   }

   @Override
   public void na(char var1, int var2) {
      if (this.ydp.length() < 24
         && (Character.isDigit(var1) || var1 >= 'a' && var1 <= 'z' || var1 >= 'A' && var1 <= 'Z' || GlyphTextUtil.encode("_()[].,/äöüÄÖÜ").contains(GlyphTextUtil.encode("") + GlyphTextUtil.encodeChar(var1)))) {
         this.ydp = this.ydp + GlyphTextUtil.encodeChar(var1);
      }
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      if (this.is) {
         Arrays.fill(var1.doj, false);
      }

      String[] var6 = new String[]{
         GlyphTextUtil.encode("my"), GlyphTextUtil.encode("newest"), GlyphTextUtil.encode("favorites"), GlyphTextUtil.encode("friends"), GlyphTextUtil.encode("popular"), GlyphTextUtil.encode("theme"), GlyphTextUtil.encode("create"), GlyphTextUtil.encode("reset")
      };
      int[] var7 = new int[]{128100, 128340, 9734, 128101, 128077, 10002, 59227, 8962};
      short var8 = 160;
      byte var9 = 0;
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 0.0F, var8 + 10, var3, 5.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.panelColor, 0.85F, 230, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var8 + 12, 5.0F, var2 - var8 - 16, 20.0F, 3.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, 0.3F, 230, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      if (!this.ydp.isEmpty()) {
         GlyphTextUtil.setTextAlign(var1.nvg, 9);
         NanoVG.nvgFontBlur(var1.nvg, 2.0F);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(0, 0, 0, 128, GuiRenderContext.colorScratch));
         GlyphTextUtil.drawText(var1.nvg, var8 + 16, 7.0F, this.ydp);
         NanoVG.nvgFontBlur(var1.nvg, 0.0F);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(220, 220, 220, 160, GuiRenderContext.colorScratch));
         GlyphTextUtil.drawText(var1.nvg, var8 + 16, 7.0F, this.ydp);
      }

      int var10 = -1;

      for (int var11 = 0; var11 < var6.length; var11++) {
         var1.translate(5, var9);
         boolean var12 = var1.isHovered(var4, var5, 0, 0, var8, 28);
         if (var1.drawButton(
               var4, var5, GlyphTextUtil.encode(" ") + PhantomClient.translationManager.ry(GlyphTextUtil.encode("config.button.") + var6[var11]), var8, 28, false, this.too == var11 ? 1.0F : 0.0F, var12 ? 1.0F : 0.0F
            )
            && !this.is
            && var1.doj[0]) {
            var10 = var11;
         }

         var1.drawIconGlyph(var1, GuiRenderContext.iconBuffer(var7[var11]), 8, 15);
         var1.translate(-5, -var9);
         var9 += 32;
      }

      if (var10 != -1) {
         this.rtz(var10);
      }

      var1.translate(var8 + 10, 32);
      this.hfr(var1, var2 - var8 - 10, var3 - 38, var4, var5);
      var1.translate(-var8 - 10, -32);
      if (this.is) {
         var1.ique(var2, var3);
      }
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
      this.scrollOffset -= var2 * 15.0F;
   }

   public boolean lts(String var1, String var2) {
      var1 = GlyphTextUtil.lowercase(var1);
      var2 = GlyphTextUtil.lowercase(var2);
      return var2.isEmpty() || var1.contains(var2) || var2.contains(var1);
   }

   public void hfr(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      byte var6 = 0;
      int var7 = 0;
      byte var8 = 92;
      int var9 = (var2 - 10) / 2;
      String var10 = this.ydp;
      int var11 = this.hmp.size();
      if (!var10.isEmpty()) {
         var11 = 0;

         for (int var12 = 0; var12 < this.hmp.size(); var12++) {
            JsonObject var13 = this.hmp.get(var12).getAsJsonObject();
            if (this.lts(var13.get(GlyphTextUtil.encode("title")).getAsString(), var10) || this.lts(var13.get(GlyphTextUtil.encode("author")).getAsString(), var10)) {
               var11++;
            }
         }
      }

      int var19 = (int)(Math.ceil(var11 / 2.0) * var8) - 5;
      this.scrollOffset = Math.max(0.0F, Math.min((float)(var19 - var3 + 5), this.scrollOffset));
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var2 - 8, 0.0F, 5.0F, var3, 2.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(30, 30, 30, 200, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      float var20 = 1.0F * var3 / var19 * var3;
      if (var3 < var19) {
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, var2 - 8, this.scrollOffset / (var19 - var3 + 5) * (var3 - var20), 5.0F, var20, 2.0F);
         NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, 0.65F, 230, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var1.nvg);
      }

      NanoVG.nvgScissor(var1.nvg, 0.0F, 0.0F, var2 - 10, var3);
      NanoVG.nvgTranslate(var1.nvg, 0.0F, -this.scrollOffset);
      var5 = (int)(var5 + this.scrollOffset);
      float var14 = var5 - var1.mouseY - this.scrollOffset;
      if (var14 >= var3 || var14 <= 0.0F) {
         var5 = -1000;
      }

      for (int var15 = 0; var15 < this.hmp.size(); var15++) {
         JsonObject var16 = this.hmp.get(var15).getAsJsonObject();
         if (this.lts(var16.get(GlyphTextUtil.encode("title")).getAsString(), var10) || this.lts(var16.get(GlyphTextUtil.encode("author")).getAsString(), var10)) {
            int var17 = var7 * var9;
            if (var6 > this.scrollOffset - var3 && var6 <= this.scrollOffset + var3) {
               NanoVG.nvgTranslate(var1.nvg, var17, var6);
               this.bhj(var1, var16, var4 - var17, var5 - var6, var9, var8);
               NanoVG.nvgTranslate(var1.nvg, -var17, -var6);
            }

            if (++var7 >= 2) {
               var7 = 0;
               var6 += var8;
            }
         }
      }

      NanoVG.nvgTranslate(var1.nvg, 0.0F, this.scrollOffset);
      NanoVG.nvgResetScissor(var1.nvg);
   }

   public void bhj(GuiRenderContext var1, JsonObject var2, int var3, int var4, int var5, int var6) {
      String var7 = var2.get(GlyphTextUtil.encode("id")).getAsString();
      String var8 = var2.get(GlyphTextUtil.encode("title")).getAsString();
      String var9 = var8.length() > 20 ? var8.substring(0, 20) + GlyphTextUtil.encode("...") : var8;
      String var10 = var2.get(GlyphTextUtil.encode("description")).getAsString();
      String var11 = var2.get(GlyphTextUtil.encode("author")).getAsString();
      String var12 = ConfigEditDialog.fgy[JsonUtils.jwk(var2, GlyphTextUtil.encode("visibility"), 0)];
      String var13 = new SimpleDateFormat(GlyphTextUtil.plain("dd.MM.YYYY")).format(JsonUtils.wit(var2, GlyphTextUtil.encode("last_update"), 0L));
      boolean var14 = JsonUtils.itd(var2, GlyphTextUtil.encode("favorite"), false);
      int var15 = JsonUtils.jwk(var2, GlyphTextUtil.encode("downloads"), 0);
      boolean var16 = JsonUtils.itd(var2, GlyphTextUtil.encode("my"), false);
      boolean var17 = JsonUtils.itd(var2, GlyphTextUtil.encode("visual"), false);
      boolean var18 = Objects.equals(var7, PhantomClient.configManager.fs());
      boolean var19 = var1.isHovered(var3, var4, 0, 0, var5, var6);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 0.0F, var5 - 1, var6 - 1, 2.0F);
      int var20 = var14 ? 40 : 30;
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var20, var20, var20, var19 ? 200 : 170, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgFontSize(var1.nvg, 24.0F);
      NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontBold);
      GlyphTextUtil.setTextAlign(var1.nvg, 9);
      var1.drawTextAt(var1, var9, 8, 13);
      NanoVG.nvgFontSize(var1.nvg, 20.0F);
      NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontRegular);
      var1.drawTextAt(var1, GlyphTextUtil.encode("by ") + var11, 10, 42);
      NanoVG.nvgFontSize(var1.nvg, 14.0F);
      if (var17) {
         var1.drawTextAt(var1, GlyphTextUtil.encode("(Visual)"), 10, 68);
      }

      GlyphTextUtil.setTextAlign(var1.nvg, 12);
      var1.drawTextAt(var1, String.valueOf(var15), var5 - 26, 4);
      var1.drawIconGlyph(var1, GuiRenderContext.iconBuffer(128229), var5 - 8, 4);
      var1.drawIconGlyph(var1, GuiRenderContext.iconBuffer(128340), var5 - 8, 22);
      var1.drawTextAt(var1, var13, var5 - 26, 22);
      var1.drawIconGlyph(var1, GuiRenderContext.iconBuffer(128101), var5 - 8, 40);
      var1.drawTextAt(var1, var12, var5 - 26, 40);
      if (var19) {
         var1.tooltipText = var9 + GlyphTextUtil.encode("\n") + var10;
      }

      GlyphTextUtil.setTextAlign(var1.nvg, 10);
      NanoVG.nvgFontSize(var1.nvg, 18.0F);
      byte var21 = 30;
      ArrayList<ByteBuffer> var22 = new ArrayList<>();
      ArrayList<String> var23 = new ArrayList<>();
      ArrayList<Runnable> var24 = new ArrayList<>();
      if (!var17) {
         var22.add(GuiRenderContext.iconBuffer(var18 ? 9829 : 9825));
         var23.add(var18 ? GlyphTextUtil.encode("remove_default") : GlyphTextUtil.encode("set_default"));
         var24.add(() -> {
            this.is = true;
            if (var18) {
               PhantomClient.configManager.ks(null);
            } else {
               PhantomClient.configManager.ks(var7);
            }

            PhantomClient.configManager.fl();
            this.rtz(this.too);
         });
      }

      var22.add(GuiRenderContext.iconBuffer(var14 ? 9733 : 9734));
      var23.add(var14 ? GlyphTextUtil.encode("remove_favorite") : GlyphTextUtil.encode("add_favorite"));
      var24.add(() -> {
         this.is = true;
         PhantomClient.cloudApi.bz(var7, !var14, () -> this.rtz(this.too));
      });
      var23.add(GlyphTextUtil.encode("download"));
      var22.add(GuiRenderContext.iconBuffer(128229));
      var24.add(() -> PhantomClient.cloudApi.cmh(var7, var9, var3xx -> PhantomClient.scheduleTask(() -> {
         Consumer<Boolean> var4x = var4xx -> {
            Consumer<Boolean> var5x = var4xxx -> {
               JsonObject var5xx = PhantomClient.configManager.jm(false);
               PhantomClient.configManager.ii(var3xx, var4xx, var17, var4xxx);
               PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.load.loaded"), var9).addButton(PhantomClient.translationManager.ry(GlyphTextUtil.encode("config.undo")), GuiRenderContext.iconBuffer(128281), () -> {
                  PhantomClient.configManager.ii(var5xx, true, false, true);
                  PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("config.undo"));
                  return true;
               });
            };
            if (var17) {
               var5x.accept(true);
            } else {
               PhantomClient.uiManager.getContext().activeDialog = new ConfigKeybindsLoadDialog(this, var5x);
            }
         };
         if (var17) {
            var4x.accept(true);
         } else {
            PhantomClient.uiManager.getContext().activeDialog = new ConfigLoadConfirmDialog(this, var4x);
         }
      }, 0)));
      if (var16) {
         var23.add(GlyphTextUtil.encode("overwrite"));
         var22.add(GuiRenderContext.iconBuffer(128228));
         var24.add(() -> PhantomClient.uiManager.getContext().activeDialog = new ConfigSaveConfirmDialog(this, var4x -> {
            if (var4x) {
               this.is = true;
               JsonObject var5x = PhantomClient.configManager.jm(var17);
               PhantomClient.cloudApi.ya(var7, var9, var5x, () -> this.is = false);
            }
         }));
         var22.add(GuiRenderContext.iconBuffer(59290));
         var23.add(GlyphTextUtil.encode("edit"));
         var24.add(() -> PhantomClient.uiManager.getContext().activeDialog = new ConfigEditDialog(this, var2));
         var22.add(GuiRenderContext.iconBuffer(10006));
         var23.add(GlyphTextUtil.encode("remove"));
         var24.add(() -> PhantomClient.uiManager.getContext().activeDialog = new ConfigDeleteConfirmDialog(this, var3x -> {
            if (var3x) {
               this.is = true;
               PhantomClient.cloudApi.hl(var7, var9, () -> {
                  this.is = false;
                  this.yww();
               });
            }
         }));
      }

      for (int var25 = 0; var25 < var22.size(); var25++) {
         var19 = var1.isHovered(var3, var4, var5 - var25 * var21 - var21, var6 - 30, 30, 30);
         var20 = var19 ? 10 : 20;
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, var5 - var25 * var21 - var21 - 3, var6 - var21 - 3, var21 - 2, var21 - 2, 5.0F);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var20, var20, var20, var19 ? 200 : 170, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var1.nvg);
         if (var19) {
            var1.tooltipText = PhantomClient.translationManager.ry(GlyphTextUtil.encode("config.button.") + (String)var23.get(var25));
            if (var1.doj[0]) {
               ((Runnable)var24.get(var25)).run();
               var1.doj[0] = false;
            }
         }

         var1.drawIconGlyph(var1, (ByteBuffer)var22.get(var25), var5 - var25 * var21 - 19, var6 - 27);
      }
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      switch (var2) {
         case 259:
            if (this.ydp.length() > 0 && var4 >= 1) {
               this.ydp = this.ydp.substring(0, this.ydp.length() - 1);
            }
         default:
            return super.handleKey(var1, var2, var3, var4);
      }
   }
}
