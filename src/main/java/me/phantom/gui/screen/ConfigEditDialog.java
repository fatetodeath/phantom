package me.phantom.gui.screen;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.JsonUtils;

import com.google.gson.JsonObject;
import java.util.Arrays;
import org.lwjgl.nanovg.NanoVG;

public class ConfigEditDialog extends AbstractDialog {
   public static final String[] fgy = new String[]{GlyphTextUtil.encode("Public"), GlyphTextUtil.encode("Friends"), GlyphTextUtil.encode("Private")};
   private JsonObject kyu;
   private String title = GlyphTextUtil.encode("");
   private boolean is;
   private boolean aqq;
   private int act;

   public ConfigEditDialog(AbstractDialog var1, JsonObject var2) {
      super(GlyphTextUtil.encode("Configs"), var1);
      this.kyu = var2;
      if (var2 != null) {
         this.title = var2.get(GlyphTextUtil.encode("title")).getAsString();
         this.act = var2.get(GlyphTextUtil.encode("visibility")).getAsInt();
         this.aqq = JsonUtils.itd(var2, GlyphTextUtil.encode("visual"), false);
      }
   }

   @Override
   public int vgh(int var1, int var2) {
      return 400;
   }

   @Override
   public int zch(int var1, int var2) {
      return 230;
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      if (this.is) {
         Arrays.fill(var1.doj, false);
      }

      byte var6 = 20;
      int var7 = 16;
      var1.translate(var6, var7);
      var1.drawTextAt(var1, GlyphTextUtil.encode("Title:"), (var2 - 40) / 2, 0);
      var7 += 15;
      var1.translate(0, 15);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 0.0F, var2 - 40, 28.0F, 5.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, 0.25F, 230, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      GlyphTextUtil.setTextAlign(var1.nvg, 9);
      NanoVG.nvgFontBlur(var1.nvg, 2.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(0, 0, 0, 128, GuiRenderContext.colorScratch));
      GlyphTextUtil.drawText(var1.nvg, 5.0F, 5.0F, this.title);
      NanoVG.nvgFontBlur(var1.nvg, 0.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(220, 220, 220, 160, GuiRenderContext.colorScratch));
      GlyphTextUtil.drawText(var1.nvg, 5.0F, 5.0F, this.title);
      var7 += 40;
      var1.translate(0, 40);
      if (var1.iut(var4, var5, var2 - 40, 28, GlyphTextUtil.encode("Visbility: ") + fgy[this.act], GlyphTextUtil.encode("")) && var1.doj[0]) {
         this.act = (this.act + 1) % fgy.length;
         var1.doj[0] = false;
      }

      var7 += 40;
      var1.translate(0, 40);
      var1.translate(var2 / 2 - 90, 0);
      if (var1.pstk(var4, var5, var2 - 40, 28, GlyphTextUtil.encode("Visual config"), PhantomClient.translationManager.ry(GlyphTextUtil.encode("config.visual")), this.aqq, 0.0F)) {
         this.aqq = !this.aqq;
         var1.doj[0] = false;
      }

      var1.translate(-(var2 / 2 - 90), 0);
      var7 += 40;
      var1.translate(0, 40);
      int var8 = (var2 - 40) / 2 - 5;
      if (var1.drawButton(var4, var5, GlyphTextUtil.encode("Cancel"), var8, 28, true, 1.0F, var1.isHovered(var4, var5, 0, 0, var8, 28) ? 1.0F : 0.0F) && var1.doj[0]) {
         this.fiv();
         var1.doj[0] = false;
      }

      var1.translate((var2 - 40) / 2 + 5, 0);
      if (var1.drawButton(
            var4,
            var5,
            this.kyu == null ? GlyphTextUtil.encode("Create") : GlyphTextUtil.encode("Update"),
            var8,
            28,
            true,
            this.title.length() >= 3 ? 1.0F : 0.0F,
            var1.isHovered(var4, var5, 0, 0, var8, 28) ? 1.0F : 0.0F
         )
         && var1.doj[0]) {
         if (this.title.length() >= 3) {
            if (this.kyu == null) {
               JsonObject var9 = new JsonObject();
               var9.addProperty(GlyphTextUtil.encode("title"), this.title);
               var9.addProperty(GlyphTextUtil.encode("visibility"), this.act);
               var9.addProperty(GlyphTextUtil.encode("visual"), this.aqq);
               var9.addProperty(GlyphTextUtil.encode("content"), PhantomClient.configManager.jm(this.aqq).toString());
               PhantomClient.cloudApi.zy(var9, var1x -> {
                  if (var1x != null) {
                     PhantomClient.configManager.ex(var1x);
                  }

                  this.is = false;
                  this.fiv();
               });
            } else {
               PhantomClient.cloudApi.mi(this.kyu.get(GlyphTextUtil.encode("id")).getAsString(), this.title, this.act, this.aqq, var1x -> {
                  if (var1x != null) {
                     PhantomClient.configManager.ex(var1x);
                  }

                  this.is = false;
                  this.fiv();
               });
            }
         }

         var1.doj[0] = false;
      }

      var1.translate(-var2 / 2 - 5, 0);
      var1.translate(-20, -var7);
      if (this.is) {
         var1.ique(var2, var3);
      }
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      switch (var2) {
         case 259:
            if (this.title.length() > 0 && var4 >= 1) {
               this.title = this.title.substring(0, this.title.length() - 1);
            }
         default:
            return super.handleKey(var1, var2, var3, var4);
      }
   }

   @Override
   public void na(char var1, int var2) {
      if (this.title.length() < 24
         && (Character.isDigit(var1) || var1 >= 'a' && var1 <= 'z' || var1 >= 'A' && var1 <= 'Z' || GlyphTextUtil.encode("_()[].,/äöüÄÖÜ- ").contains(GlyphTextUtil.encode("") + GlyphTextUtil.encodeChar(var1)))
         )
       {
         this.title = this.title + GlyphTextUtil.encodeChar(var1);
      }
   }
}
