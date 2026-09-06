package me.phantom.gui.screen;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.FramebufferBindEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.core.UiManager;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.util.GlyphTextUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lwjgl.nanovg.NanoVG;

public class KeybindsScreen extends AbstractDialog {
   int[] mxz = new int[]{
      96,
      6,
      55,
      21,
      22,
      258,
      6,
      80,
      35,
      22,
      280,
      6,
      107,
      43,
      21,
      340,
      6,
      132,
      31,
      22,
      341,
      6,
      158,
      35,
      22,
      -1,
      7,
      7,
      21,
      21,
      49,
      30,
      55,
      22,
      22,
      81,
      44,
      80,
      22,
      22,
      290,
      50,
      7,
      22,
      21,
      65,
      52,
      107,
      22,
      21,
      50,
      54,
      55,
      22,
      22,
      162,
      40,
      132,
      22,
      22,
      90,
      64,
      132,
      22,
      22,
      87,
      68,
      80,
      22,
      22,
      343,
      44,
      158,
      21,
      22,
      342,
      68,
      158,
      31,
      22,
      291,
      74,
      7,
      22,
      21,
      83,
      76,
      107,
      22,
      21,
      51,
      79,
      55,
      21,
      22,
      88,
      88,
      132,
      22,
      22,
      69,
      92,
      80,
      22,
      22,
      292,
      98,
      7,
      22,
      21,
      68,
      100,
      107,
      22,
      21,
      32,
      102,
      158,
      171,
      22,
      52,
      103,
      55,
      21,
      22,
      67,
      112,
      132,
      22,
      22,
      82,
      116,
      80,
      22,
      22,
      293,
      122,
      7,
      22,
      21,
      70,
      124,
      107,
      22,
      21,
      53,
      127,
      55,
      21,
      22,
      86,
      136,
      132,
      22,
      22,
      84,
      140,
      80,
      22,
      22,
      71,
      148,
      107,
      22,
      21,
      54,
      151,
      55,
      21,
      22,
      66,
      160,
      132,
      22,
      22,
      294,
      161,
      7,
      22,
      21,
      89,
      164,
      80,
      22,
      22,
      72,
      172,
      107,
      22,
      21,
      55,
      175,
      55,
      21,
      22,
      78,
      184,
      132,
      22,
      22,
      295,
      185,
      7,
      22,
      21,
      85,
      188,
      80,
      22,
      22,
      74,
      196,
      107,
      22,
      21,
      56,
      199,
      55,
      21,
      22,
      77,
      208,
      132,
      22,
      22,
      296,
      209,
      7,
      22,
      21,
      73,
      212,
      80,
      22,
      22,
      75,
      220,
      107,
      22,
      21,
      57,
      223,
      55,
      21,
      22,
      44,
      232,
      132,
      22,
      22,
      297,
      233,
      7,
      22,
      21,
      79,
      236,
      80,
      22,
      22,
      76,
      244,
      107,
      22,
      21,
      48,
      247,
      55,
      21,
      22,
      46,
      256,
      132,
      22,
      22,
      80,
      260,
      80,
      22,
      22,
      59,
      268,
      107,
      22,
      21,
      45,
      271,
      55,
      21,
      22,
      298,
      272,
      7,
      22,
      21,
      346,
      276,
      158,
      31,
      22,
      348,
      310,
      158,
      20,
      22,
      47,
      280,
      132,
      22,
      22,
      91,
      284,
      80,
      22,
      22,
      39,
      292,
      107,
      22,
      21,
      92,
      316,
      107,
      22,
      21,
      61,
      295,
      55,
      21,
      22,
      299,
      296,
      7,
      22,
      21,
      93,
      308,
      80,
      22,
      22,
      300,
      320,
      7,
      22,
      21,
      345,
      333,
      158,
      32,
      22,
      257,
      333,
      80,
      33,
      48,
      301,
      344,
      7,
      22,
      21,
      283,
      385,
      7,
      22,
      22,
      260,
      385,
      56,
      22,
      22,
      261,
      385,
      82,
      22,
      21,
      263,
      385,
      158,
      22,
      22,
      281,
      409,
      7,
      22,
      22,
      268,
      409,
      56,
      22,
      22,
      269,
      409,
      82,
      22,
      21,
      264,
      409,
      158,
      22,
      22,
      265,
      409,
      134,
      22,
      22,
      284,
      433,
      7,
      22,
      22,
      266,
      433,
      56,
      22,
      22,
      267,
      433,
      82,
      22,
      21,
      262,
      433,
      158,
      22,
      22,
      282,
      474,
      56,
      22,
      22,
      327,
      474,
      81,
      22,
      22,
      324,
      474,
      107,
      22,
      22,
      321,
      474,
      132,
      22,
      22,
      320,
      474,
      159,
      46,
      21,
      331,
      498,
      56,
      22,
      22,
      328,
      498,
      81,
      22,
      22,
      325,
      498,
      107,
      22,
      22,
      322,
      498,
      132,
      22,
      22,
      332,
      522,
      56,
      22,
      22,
      329,
      522,
      81,
      22,
      22,
      326,
      522,
      107,
      22,
      22,
      323,
      522,
      132,
      22,
      22,
      330,
      522,
      159,
      22,
      21,
      333,
      546,
      56,
      22,
      22,
      334,
      547,
      82,
      21,
      47,
      335,
      547,
      134,
      21,
      46,
      344,
      305,
      132,
      60,
      22,
      259,
      319,
      55,
      46,
      22
   };
   public static long zm = 200L;
   public static long gyy = 1000L;
   public int jqu = -1;
   public String searchQuery = GlyphTextUtil.encode("");
   public long mvu = System.currentTimeMillis();
   public Map<Integer, Long> vgs = new HashMap<>();
   public Set<Integer> eyr = new HashSet<>();

   public KeybindsScreen() {
      super(GlyphTextUtil.encode("Keybinds"), null);
   }

   @Override
   public void fiv() {
      super.fiv();
   }

   @Override
   public void yww() {
   }

   @Override
   public int vgh(int var1, int var2) {
      return (int)(var1 * 0.8F);
   }

   @Override
   public int zch(int var1, int var2) {
      float var3 = 579.0F;
      float var4 = 184.0F;
      return (int)(this.vgh(var1, var2) * (var4 / var3));
   }

   @Override
   public void na(char var1, int var2) {
      if (this.jqu >= 0) {
         this.searchQuery = this.searchQuery + GlyphTextUtil.encodeChars(String.valueOf(var1));
      }
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      this.vgs.values().removeIf(var0 -> System.currentTimeMillis() - var0 >= zm);
      float var6 = 579.0F;
      float var7 = 184.0F;
      float var8 = var2;
      float var9 = var3;
      int var10 = 0;
      HashMap<Integer, List<BaseModule>> var11 = new HashMap<>();

      for (BaseModule var15 : PhantomClient.moduleManager.getModules()) {
         if (var15.getKeyCode() != 0) {
            var11.computeIfAbsent(var15.getKeyCode(), var0 -> new ArrayList<>()).add(var15);
         }
      }

      long var37 = System.currentTimeMillis() - this.mvu;
      float var38 = var37 > gyy ? -1.0F : (float)var37 / ((float)gyy * 1.0F) * 1.3F;

      while (var10 < this.mxz.length) {
         int var39 = this.mxz[var10++];
         int var16 = this.mxz[var10++];
         int var17 = this.mxz[var10++];
         int var18 = this.mxz[var10++];
         int var19 = this.mxz[var10++];
         if (var39 != -1) {
            int var20 = (int)(var16 / var6 * var8);
            int var21 = (int)(var17 / var7 * var9);
            int var22 = (int)(var18 / var6 * var8);
            int var23 = (int)(var19 / var7 * var9);
            float var24 = var16 / var6 + var17 / var7 * 0.1F;
            boolean var25 = this.jqu == -1 && var1.isHovered(var4, var5, var20, var21, var22, var23);
            if (var25 && var1.doj[0]) {
               this.jqu = var10 - 5;
               this.searchQuery = GlyphTextUtil.encode("");
               var1.doj[0] = false;
            }

            List<BaseModule> var26 = var11.getOrDefault(var39, Collections.emptyList());
            if (var26.isEmpty()) {
               int var27 = var25 ? 60 : 40;
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var27, var27, var27, 255, GuiRenderContext.colorScratch));
               if (var39 == 257) {
                  NanoVG.nvgBeginPath(var1.nvg);
                  NanoVG.nvgRoundedRect(var1.nvg, var20, var21, var22, var23 / 2, 3.0F);
                  NanoVG.nvgFill(var1.nvg);
                  NanoVG.nvgBeginPath(var1.nvg);
                  NanoVG.nvgRoundedRect(var1.nvg, var20 + var22 * 0.25F, var21 + var23 / 2 - 3, var22 * 0.75F, var23 / 2 + 3, 3.0F);
                  NanoVG.nvgFill(var1.nvg);
               } else {
                  NanoVG.nvgBeginPath(var1.nvg);
                  NanoVG.nvgRoundedRect(var1.nvg, var20, var21, var22, var23, 3.0F);
                  NanoVG.nvgFill(var1.nvg);
               }
            } else {
               int var53 = var25 ? 60 : 40;
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var53, var53 + 50, var53, 255, GuiRenderContext.colorScratch));
               NanoVG.nvgTranslate(var1.nvg, var20, var21);
               var1.drawGlitchOverlay(var22, var23, 3.0F, var10, 1.0F);
               NanoVG.nvgTranslate(var1.nvg, -var20, -var21);
            }

            NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(255, 80, 20, 255, GuiRenderContext.colorScratch));
            if (this.eyr.contains(var39)) {
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRoundedRect(var1.nvg, var20, var21, var22, var23, 3.0F);
               NanoVG.nvgFill(var1.nvg);
            } else {
               Long var54 = this.vgs.get(var39);
               if (var54 != null) {
                  float var28 = 1.0F - (float)(System.currentTimeMillis() - var54) / ((float)zm * 1.0F);
                  NanoVG.nvgBeginPath(var1.nvg);
                  NanoVG.nvgRoundedRect(var1.nvg, var20 + var22 / 2 * (1.0F - var28), var21 + var23 / 2 * (1.0F - var28), var22 * var28, var23 * var28, 3.0F);
                  NanoVG.nvgFill(var1.nvg);
               }
            }

            if (var38 > 0.0F && Math.abs(var24 - var38) <= 0.1F) {
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(40, 40, 80, (int)((1.0F - Math.abs(var24 - var38) / 0.1F) * 255.0F), GuiRenderContext.colorScratch));
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRoundedRect(var1.nvg, var20, var21, var22, var23, 3.0F);
               NanoVG.nvgFill(var1.nvg);
            }

            String var55 = UiManager.getKeyName(var39);
            NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
            NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontRegular);
            GlyphTextUtil.setTextAlign(var1.nvg, 18);
            int var56 = var2 / 60;
            float var29;
            if (var55 != null) {
               do {
                  NanoVG.nvgFontSize(var1.nvg, var56 - 2);
                  var29 = GlyphTextUtil.measureText(var1.nvg, 0.0F, 0.0F, var55, (FloatBuffer)null);
               } while (--var56 > 3 && var29 > var22 - 2);
            }

            if (var55 != null) {
               GlyphTextUtil.drawText(var1.nvg, var20 + var22 / 2, var21 + var23 / 2 - (var39 == 257 ? var23 / 4 : 0), var55);
            }

            if (var25) {
               StringBuilder var30 = new StringBuilder();
               if (var26.isEmpty()) {
                  var30.append(PhantomClient.translationManager.ry(GlyphTextUtil.encode("keyboard.bind")));
               } else {
                  for (BaseModule var32 : var26) {
                     var30.append(var32.getTranslatedName()).append(GlyphTextUtil.encode("\n"));
                  }
               }

               var1.setTooltip(var30.toString());
            }
         }
      }

      if (this.jqu != -1) {
         int var40 = this.mxz[this.jqu];
         short var41 = 400;
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(10, 10, 10, 245, GuiRenderContext.colorScratch));
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRect(var1.nvg, -var1.mouseX, -var1.mouseY, var1.mouseX * 2 + var2, var1.mouseY * 2 + var3);
         NanoVG.nvgFill(var1.nvg);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(70, 70, 70, 200, GuiRenderContext.colorScratch));
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, var2 / 2 - var41 / 2, -var1.mouseY + 6, var41, 40.0F, 5.0F);
         NanoVG.nvgFill(var1.nvg);
         int var42 = -var1.mouseY + 14;
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
         NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontRegular);
         NanoVG.nvgFontSize(var1.nvg, 25.0F);
         GlyphTextUtil.setTextAlign(var1.nvg, 10);
         GlyphTextUtil.drawText(var1.nvg, var2 / 2, var42, this.searchQuery.isEmpty() ? PhantomClient.translationManager.ry(GlyphTextUtil.encode("keyboard.filter")) : this.searchQuery);
         var42 += 35;
         NanoVG.nvgFontSize(var1.nvg, 16.0F);
         BaseModule[] var44 = PhantomClient.moduleManager.getModules();
         ArrayList<BaseModule> var45 = new ArrayList<>(var44.length);

         for (BaseModule var52 : var44) {
            if (var52.lvr() && GlyphTextUtil.lowercase(var52.getDisplayName()).contains(GlyphTextUtil.lowercase(this.searchQuery))) {
               var45.add(var52.getKeyCode() == var40 ? 0 : var45.size(), var52);
            }
         }

         for (BaseModule var49 : var45) {
            boolean var51 = var1.isHovered(var4, var5, var2 / 2 - var41 / 2, var42, var41, 25);
            if (var45.size() != 1 && var49.getKeyCode() != var40) {
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(30, 30, 30, var51 ? 255 : 200, GuiRenderContext.colorScratch));
            } else {
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(30, 130, 30, var51 ? 255 : 200, GuiRenderContext.colorScratch));
            }

            NanoVG.nvgBeginPath(var1.nvg);
            NanoVG.nvgRoundedRect(var1.nvg, var2 / 2 - var41 / 2, var42, var41, 25.0F, 5.0F);
            NanoVG.nvgFill(var1.nvg);
            NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(200, 200, 200, 255, GuiRenderContext.colorScratch));
            GlyphTextUtil.drawText(var1.nvg, var2 / 2, var42 + 7, var49.getName());
            if (var51 && var1.doj[0]) {
               var49.setKeybind(var49.getKeyCode() == var40 ? 0 : var40, false);
               this.jqu = -1;
               this.searchQuery = GlyphTextUtil.encode("");
               var1.doj[0] = false;
            }

            var42 += 26;
         }
      }
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      if (this.jqu == -1) {
         if (var4 == 0) {
            this.vgs.put(var2, System.currentTimeMillis());
            this.eyr.remove(var2);
         } else if (var4 == 1) {
            this.eyr.add(var2);
         }

         return super.handleKey(var1, var2, var3, var4);
      } else {
         if (var2 == 261 && var4 == 1) {
            int var5 = this.mxz[this.jqu];

            for (BaseModule var9 : PhantomClient.moduleManager.getModules()) {
               if (var9.getKeyCode() == var5) {
                  var9.setKeybind(0, false);
               }
            }

            this.searchQuery = GlyphTextUtil.encode("");
            this.jqu = -1;
         }

         if (this.searchQuery.length() > 0) {
            if (var4 == 1 && var2 == 257) {
               BaseModule[] var11 = PhantomClient.moduleManager.getModules();
               ArrayList var12 = new ArrayList(var11.length);

               for (BaseModule var10 : var11) {
                  if (var10.lvr() && GlyphTextUtil.lowercase(var10.getDisplayName()).contains(GlyphTextUtil.lowercase(this.searchQuery))) {
                     var12.add(var10);
                  }
               }

               if (var12.size() == 1) {
                  BaseModule var14 = (BaseModule)var12.get(0);
                  int var16 = this.mxz[this.jqu];
                  var14.setKeybind(var14.getKeyCode() == var16 ? 0 : var16, false);
               }

               this.jqu = -1;
            } else if ((var4 == 1 || var4 == 2) && var2 == 259) {
               this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
               if (this.searchQuery.length() <= 1) {
                  this.searchQuery = GlyphTextUtil.encode("");
               }
            }
         }

         if (var2 == 256 && var4 == 1) {
            this.searchQuery = GlyphTextUtil.encode("");
            this.jqu = -1;
         }

         return true;
      }
   }
}
