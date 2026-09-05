package me.phantom.gui.core;

import me.phantom.core.PhantomClient;
import me.phantom.gui.screen.AbstractDialog;
import me.phantom.gui.widget.ClickGuiCategoryWindow;
import me.phantom.gui.widget.EasedAnimation;
import me.phantom.gui.widget.InlineDropdownWidget;
import me.phantom.gui.widget.VideoPlayerWidget;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.render.GlfwContextGuard;
import me.phantom.setting.AbstractSetting;
import me.phantom.setting.KeyBindable;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.ResourceLoader;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGLUFramebuffer;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

public class GuiRenderContext {
   public static final int[] formatCodeColors = new int[]{
      0,
      0,
      0,
      0,
      0,
      190,
      0,
      190,
      0,
      0,
      190,
      190,
      190,
      0,
      0,
      190,
      0,
      190,
      217,
      163,
      52,
      190,
      190,
      190,
      63,
      63,
      63,
      63,
      63,
      254,
      63,
      254,
      63,
      63,
      254,
      254,
      254,
      63,
      63,
      254,
      63,
      254,
      254,
      254,
      63,
      255,
      255,
      255
   };
   public static final Pattern gpi = Pattern.compile(GlyphTextUtil.encode("(?i)\\u00A7[0-9A-FK-OR]"));
   public static final NVGColor colorScratch = NVGColor.create();
   public static final NVGColor colorScratch2 = NVGColor.create();
   public static final NVGColor shadowColor = NVGColor.create();
   public static final NVGPaint paintScratch = NVGPaint.create();
   public static final NVGPaint onk = NVGPaint.create();
   public static final NVGPaint uru = NVGPaint.create();
   public Map<Character, Integer> dyb = new HashMap<>();
   public Map<Character, Integer> obct = new HashMap<>();
   public ClickGuiCategoryWindow kcr;
   public KeyBindable keybindCaptureTarget;
   public AbstractSetting<?> xwl;
   public int framebufferWidth;
   public int framebufferHeight;
   public int screenWidth;
   public int screenHeight;
   public static final int wmr = 8;
   public static final int tes = 1024;
   public boolean[] xfy = new boolean[8];
   public boolean[] doj = new boolean[8];
   public boolean[] mzx = new boolean[8];
   public boolean[] gml = new boolean[1024];
   public boolean[] dugn = new boolean[1024];
   public long nvg;
   public NVGLUFramebuffer jzs;
   public int dzt;
   public int qkg;
   public int mouseX;
   public int mouseY;
   public String tooltipText;
   public NVGColor panelColor = NVGColor.create();
   public NVGColor accentColor = NVGColor.create();
   public NVGColor textBaseColor = NVGColor.create();
   public NVGColor blurColor = NVGColor.create();
   public float tickDelta;
   public float worldTickDelta;
   public float lkq;
   public float effectTime;
   public long mcz = System.currentTimeMillis();
   public String vdd;
   public InlineDropdownWidget opo;
   public EasedAnimation dil;
   public EasedAnimation miwy;
   public int pbj;
   public int iconsFontId;
   public int wkl;
   public int xiq;
   public int glitchTextureId;
   public List<ByteBuffer> loadedResources = new ArrayList<>(3);
   public AbstractDialog activeDialog;
   public int gwj;
   public int aypf;
   public boolean ahm;
   public float nzo;
   public int glowAmount;
   public long contextWindow;
   public Set<String> loadedFonts = new HashSet<>();
   public static final float[] charWidthCache = new float[65536];

   public static NVGColor rgba(int var0, int var1, int var2, int var3, NVGColor var4) {
      var4.r(var0 / 255.0F);
      var4.g(var1 / 255.0F);
      var4.b(var2 / 255.0F);
      var4.a(var3 / 255.0F);
      return var4;
   }

   public static ByteBuffer iconBuffer(int var0) {
      return MemoryUtil.memUTF8(new String(Character.toChars(var0)), false);
   }

   public GuiRenderContext(InterfaceModule var1) throws Throwable {
      GlfwContextGuard var2 = new GlfwContextGuard();

      try {
         this.dil = new EasedAnimation(false);
         this.miwy = new EasedAnimation(false);
         GLFW.glfwWindowHint(131076, 0);
         GLFW.glfwWindowHint(139266, 3);
         GLFW.glfwWindowHint(139267, 3);
         GLFW.glfwWindowHint(139272, 204801);
         GLFW.glfwWindowHint(135174, 8);
         GLFW.glfwWindowHint(135173, 24);
         GLFW.glfwWindowHint(135181, 4);
         this.contextWindow = GLFW.glfwCreateWindow(1, 1, "Shared Context", 0L, MinecraftClient.getInstance().getWindow().getHandle());
         GLFW.glfwMakeContextCurrent(this.contextWindow);
         GL.createCapabilities();
         this.nvg = NanoVGGL3.nvgCreate(3);
         if (this.nvg == 0L) {
            throw new RuntimeException(GlyphTextUtil.encode("Could not init nanovg."));
         }

         this.iconsFontId = this.loadFontFile(GlyphTextUtil.encode("icons"), GlyphTextUtil.encode("icons"));
         this.glitchTextureId = NanoVG.nvgCreateImageMem(this.nvg, 6, this.loadResource(GlyphTextUtil.encode("textures/enchantment_glitch.png")));
         this.loadUiFonts(var1);
      } catch (Throwable var6) {
         try {
            var2.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      var2.close();
   }

   public String loadFont(String var1, String var2) {
      String var3 = var1 + "_" + var2;
      if (this.loadedFonts.add(var3)) {
         int var4 = NanoVG.nvgCreateFontMem(this.nvg, var3, this.loadResource(GlyphTextUtil.encode("fonts/") + var1 + "/" + var2 + GlyphTextUtil.encode(".ttf")), false);
         if (var4 == -1) {
            throw new RuntimeException(GlyphTextUtil.encode("Failed to load font ") + var3 + GlyphTextUtil.encode(" in file ") + var1);
         }

         NanoVG.nvgAddFallbackFontId(this.nvg, var4, this.iconsFontId);
      }

      return var3;
   }

   public void loadUiFonts(InterfaceModule var1) {
      String var2 = (String)var1.tbw.getValue();
      InterfaceModule.fontRegular = this.loadFont(var2, GlyphTextUtil.encode("regular"));
      InterfaceModule.fontBold = this.loadFont(var2, GlyphTextUtil.encode("bold"));
   }

   public int loadFontFile(String var1, String var2) {
      int var3 = NanoVG.nvgCreateFontMem(this.nvg, var1, this.loadResource(GlyphTextUtil.encode("fonts/") + var2 + GlyphTextUtil.encode(".ttf")), false);
      if (var3 == -1) {
         throw new RuntimeException(GlyphTextUtil.encode("Failed to load font ") + var1 + GlyphTextUtil.encode(" in file ") + var2);
      } else {
         return var3;
      }
   }

   public ByteBuffer loadResource(String var1) {
      byte[] var2 = ResourceLoader.ogt(var1);
      ByteBuffer var3 = BufferUtils.createByteBuffer(var2.length);
      var3.put(var2);
      var3.flip();
      this.loadedResources.add(var3);
      return var3;
   }

   public void wjo() {
      NanoVG.nvgSave(this.nvg);
   }

   public void mev() {
      NanoVG.nvgSave(this.nvg);
   }

   public void translate(int var1, int var2) {
      NanoVG.nvgTranslate(this.nvg, var1, var2);
      this.mouseX += var1;
      this.mouseY += var2;
   }

   public boolean isHovered(int var1, int var2, int var3, int var4, int var5, int var6) {
      return var1 >= var3 + this.mouseX && var2 >= var4 + this.mouseY && var1 <= var3 + var5 + this.mouseX && var2 <= var4 + var6 + this.mouseY;
   }

   public boolean isHoveredF(float var1, float var2, float var3, float var4, float var5, float var6) {
      return var1 >= var3 + this.mouseX && var2 >= var4 + this.mouseY && var1 <= var3 + var5 + this.mouseX && var2 <= var4 + var6 + this.mouseY;
   }

   public boolean isClicked(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (this.doj[var7] && this.isHovered(var1, var2, var3, var4, var5, var6)) {
         this.doj[var7] = false;
         return true;
      } else {
         return false;
      }
   }

   public NVGColor lerpColor(NVGColor var1, NVGColor var2, NVGColor var3, float var4, float var5) {
      return NanoVG.nvgRGBAf(
         var1.r() + (var2.r() - var1.r()) * var4,
         var1.g() + (var2.g() - var1.g()) * var4,
         var1.b() + (var2.b() - var1.b()) * var4,
         var5,
         var3
      );
   }

   public NVGColor shadeColor(NVGColor var1, float var2, int var3, NVGColor var4) {
      return NanoVG.nvgRGBAf(var1.r() * var2, var1.g() * var2, var1.b() * var2, var3 / 255.0F, var4);
   }

   public float measureTextWidth(String var1) {
      return GlyphTextUtil.measureText(this.nvg, 0.0F, 0.0F, var1, (FloatBuffer)null);
   }

   public void setTooltip(String var1) {
      this.tooltipText = var1;
   }

   public void drawIconText(int var1, int var2, int var3, float var4, boolean var5, int var6, float var7, float var8) {
      NanoVG.nvgFontFace(this.nvg, var5 ? InterfaceModule.fontBold : InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(this.nvg, var4);
      ByteBuffer var9 = MemoryUtil.memUTF8(new String(Character.toChars(var3)), false);
      NanoVG.nvgFontFace(this.nvg, GlyphTextUtil.encode("icons"));
      GlyphTextUtil.setTextAlign(this.nvg, var6);
      NanoVG.nvgFillColor(this.nvg, rgba(0, 0, 0, (int)(60.0F * var8), colorScratch));

      for (int var10 = -1; var10 <= 1; var10++) {
         for (int var11 = -1; var11 <= 1; var11++) {
            GlyphTextUtil.drawTextBuffer(this.nvg, var1 + var10, var2 + var11, var9);
         }
      }

      NanoVG.nvgFillColor(this.nvg, this.shadeColor(this.textBaseColor, 0.7F + var7 * 0.3F, (int)(var8 * (220.0F + var7 * 35.0F)), colorScratch));
      GlyphTextUtil.drawTextBuffer(this.nvg, var1, var2, var9);
   }

   public void precacheGlyphs(String var1) {
      NanoVG.nvgFontSize(this.nvg, 64.0F);
      char[] var2 = var1.toCharArray();

      for (int var3 = 0; var3 < var2.length; var3++) {
         char var4 = var2[var3];
         float var5 = charWidthCache[var4];
         if (var5 == -1.0F) {
            charWidthCache[var4] = GlyphTextUtil.measureText(this.nvg, 0.0F, 0.0F, GlyphTextUtil.encode("") + var4, (FloatBuffer)null) / 64.0F;
         }
      }
   }

   public String stripColorCodes(String var1) {
      return gpi.matcher(var1).replaceAll(GlyphTextUtil.encode(""));
   }

   public float measureTextSize(String var1, float var2) {
      NanoVG.nvgFontSize(this.nvg, var2);
      return GlyphTextUtil.measureText(this.nvg, 0.0F, 0.0F, this.stripColorCodes(var1), (FloatBuffer)null);
   }

   public float measureTextComponent(Text var1, float var2) {
      float[] var3 = new float[]{0.0F};
      var1.visit((var3x, var4) -> {
         var3[0] += this.measureTextSize(var4, var2);
         return Optional.empty();
      }, Style.EMPTY);
      return var3[0];
   }

   public void drawTextComponent(float var1, float var2, Text var3, float var4, boolean var5, int var6, float var7, float var8) {
      float[] var9 = new float[]{var1};
      var3.visit(
         (var8x, var9x) -> {
            Color var10 = var8x.getColor() == null ? Color.WHITE : new Color(var8x.getColor().getRgb()).brighter();
            var9[0] += this.drawTextWeighted(
               var9[0], var2, var9x, var4, var8x.isBold() || var5, var6, var7, var8, rgba(var10.getRed(), var10.getGreen(), var10.getBlue(), 255, colorScratch)
            );
            return Optional.empty();
         },
         Style.EMPTY
      );
   }

   public float drawTextAuto(float var1, float var2, String var3, float var4, boolean var5, int var6, float var7, float var8) {
      return this.drawTextWeighted(var1, var2, var3, var4, var5, var6, var7, var8, this.shadeColor(this.textBaseColor, 0.7F + var7 * 0.3F, (int)(var8 * (220.0F + var7 * 35.0F)), colorScratch));
   }

   public float drawTextWeighted(float var1, float var2, String var3, float var4, boolean var5, int var6, float var7, float var8, NVGColor var9) {
      return this.drawTextAligned(var1, var2, var3, var4, var5 ? InterfaceModule.fontBold : InterfaceModule.fontRegular, var6, var7, var8, var9);
   }

   public float drawTextAligned(float var1, float var2, String var3, float var4, String var5, int var6, float var7, float var8, NVGColor var9) {
      NanoVG.nvgFontFace(this.nvg, var5);
      GlyphTextUtil.setTextAlign(this.nvg, var6 & -7 | 1);
      if (this.glowAmount > 0) {
         this.shadeColor(this.blurColor, 1.0F, (int)(var8 * this.glowAmount), shadowColor);
      }

      if ((var6 & 2) != 0) {
         var1 -= this.measureTextSize(var3, Math.abs(var4)) / 2.0F;
      } else if ((var6 & 4) != 0) {
         var1 += this.measureTextSize(var3, Math.abs(var4)) / 2.0F;
      }

      if (var3.contains(GlyphTextUtil.encode("§"))) {
         StringBuilder var10 = new StringBuilder();
         boolean var11 = false;
         float var12 = 0.0F;
         NanoVG.nvgFillColor(this.nvg, var9);

         for (int var13 = 0; var13 < var3.length(); var13++) {
            char var14 = var3.charAt(var13);
            if (var14 == 167) {
               var11 = true;
            } else if (var11) {
               if (var10.length() > 0) {
                  var12 += this.renderTextRun(var1 + var12, var2, var10.toString(), var4, var9, shadowColor);
                  var10.setLength(0);
               }

               var14 = GlyphTextUtil.lowercase(String.valueOf(var14)).charAt(0);
               if (var14 == 'r') {
                  var14 = 'f';
               }

               int var15 = GlyphTextUtil.encode("0123456789abcdef").indexOf(var14);
               if (var15 != -1) {
                  NanoVG.nvgFillColor(this.nvg, rgba(formatCodeColors[var15 * 3], formatCodeColors[var15 * 3 + 1], formatCodeColors[var15 * 3 + 2], (int)(var9.a() * 255.0F), var9));
               }

               var11 = false;
            } else {
               var10.append(var14);
            }
         }

         return var12 + this.renderTextRun(var1 + var12, var2, var10.toString(), var4, var9, shadowColor);
      } else {
         return this.renderTextRun(var1, var2, var3, var4, var9, shadowColor);
      }
   }

   public float renderTextRun(float var1, float var2, String var3, float var4, NVGColor var5, NVGColor var6) {
      NanoVG.nvgFontSize(this.nvg, Math.abs(var4));
      NanoVG.nvgSave(this.nvg);
      if (this.glowAmount > 0 && var4 > 0.0F) {
         NanoVG.nvgFontBlur(this.nvg, Math.max(0.0F, this.glowAmount / 25.0F - 1.0F));
         NanoVG.nvgFillColor(this.nvg, var6);

         for (int var7 = -2; var7 <= 2; var7++) {
            for (int var8 = -2; var8 <= 2; var8++) {
               GlyphTextUtil.drawText(this.nvg, var1 + var7, var2 + var8, var3);
            }
         }

         NanoVG.nvgFontBlur(this.nvg, 0.0F);
      }

      NanoVG.nvgFillColor(this.nvg, var5);
      float var9 = GlyphTextUtil.drawText(this.nvg, var1, var2, var3) - var1;
      NanoVG.nvgRestore(this.nvg);
      return var9;
   }

   public void drawTooltipPanel(String var1, int var2, int var3, int var4, float var5, boolean var6) {
      float var7 = 6.0F;
      NVGPaint var8 = NanoVG.nvgBoxGradient(this.nvg, 0.0F, 0.0F, var3, var4, var7 * 2.0F, 15.0F, rgba(0, 0, 0, 120, colorScratch), rgba(0, 0, 0, 0, colorScratch2), paintScratch);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRect(this.nvg, -10.0F, -10.0F, var3 + 20, var4 + 20);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var3, var4, var7);
      NanoVG.nvgPathWinding(this.nvg, 2);
      NanoVG.nvgFillPaint(this.nvg, var8);
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var3, var4, var7);
      NanoVG.nvgFillColor(this.nvg, this.shadeColor(this.panelColor, 1.0F, 230, colorScratch));
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgFontFace(this.nvg, InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(this.nvg, 18.0F);
      float var9 = this.measureTextWidth(var1);
      NVGPaint var10 = NanoVG.nvgRadialGradient(
         this.nvg, var3 / 2, -30.0F, -360.0F, var9 * 1.4F, this.shadeColor(this.panelColor, 1.0F, 255, colorScratch), this.shadeColor(this.panelColor, 0.3F, 140, colorScratch2), paintScratch
      );
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 1.0F, 1.0F, var3 - 2, 28.0F, var7 - 1.0F);
      NanoVG.nvgFillPaint(this.nvg, var10);
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgStrokeColor(this.nvg, rgba(0, 0, 0, 32, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      int var11 = var3 - 15;
      byte var12 = 11;
      if (var6) {
         int var13 = Math.round(5.0F - var5 * 5.0F);
         NanoVG.nvgBeginPath(this.nvg);
         NanoVG.nvgMoveTo(this.nvg, var11 - 5, var12 + var13);
         NanoVG.nvgLineTo(this.nvg, var11, var12 + 5 - var13);
         NanoVG.nvgLineTo(this.nvg, var11 + 5, var12 + var13);
         NanoVG.nvgStrokeColor(this.nvg, rgba(255, 255, 255, 255, colorScratch));
         NanoVG.nvgStroke(this.nvg);
      }

      this.drawTextAuto(var3 / 2, 16.0F, var1, 18.0F, true, 18, var5, 1.0F);
      if (var2 != 0) {
         this.drawIconText(16, 16, var2, 18.0F, true, 18, var5, 1.0F);
      }
   }

   public boolean drawHeaderButton(int var1, int var2, String var3, int var4, int var5, boolean var6) {
      boolean var7 = this.isHovered(var1, var2, -this.mouseX, -this.mouseY, var4, var5);
      float var8 = 4.0F;
      NanoVG.nvgFontFace(this.nvg, InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(this.nvg, 18.0F);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var4, var5, var8 - 1.0F);
      if (var7) {
         NanoVG.nvgFillPaint(
            this.nvg,
            NanoVG.nvgLinearGradient(this.nvg, 0.0F, 0.0F, 0.0F, var5 + 0.45F * var2, this.shadeColor(this.accentColor, 0.8F, 255, colorScratch), this.shadeColor(this.accentColor, 0.5F, 255, colorScratch2), paintScratch)
         );
      } else {
         NanoVG.nvgFillPaint(
            this.nvg, NanoVG.nvgLinearGradient(this.nvg, 0.0F, 0.0F, 0.0F, var5, this.shadeColor(this.panelColor, 1.6F, 255, colorScratch), this.shadeColor(this.panelColor, 1.4F, 255, colorScratch2), paintScratch)
         );
      }

      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgFontSize(this.nvg, 16.0F);
      NanoVG.nvgFontFace(this.nvg, InterfaceModule.fontRegular);
      this.drawTextAuto(6.0F, 6.0F, var3, 16.0F, false, 9, var6 ? 1.0F : 0.0F, 1.0F);
      if (var6) {
         this.drawIconText(var4 - 6, 4, 8592, 18.0F, false, 12, var6 ? 1.0F : 0.0F, 1.0F);
      }

      return var7;
   }

   public boolean drawButton(int var1, int var2, String var3, int var4, int var5, boolean var6, float var7, float var8) {
      float var9 = 4.0F;
      NanoVG.nvgFontFace(this.nvg, InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(this.nvg, 16.0F);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var4, var5, var9 - 1.0F);
      NanoVG.nvgFillPaint(
         this.nvg,
         NanoVG.nvgLinearGradient(
            this.nvg,
            0.0F,
            0.0F,
            0.0F,
            var5 + 0.45F * (var2 - this.mouseY),
            this.shadeColor(this.panelColor, 1.6F + var8 * 0.65F, 255, colorScratch),
            this.shadeColor(this.panelColor, 1.6F + var8 * 0.45F, 255, colorScratch2),
            paintScratch
         )
      );
      NanoVG.nvgFill(this.nvg);
      if (var7 > 0.0F) {
         this.drawGlitchOverlay(var4, var5, var9 - 1.0F, var3.hashCode(), var7);
      }

      NanoVG.nvgStrokeWidth(this.nvg, 1.0F);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var4, var5, var9 - 1.0F);
      NanoVG.nvgStrokeColor(this.nvg, rgba(60, 60, 60, 60, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      NanoVG.nvgFontSize(this.nvg, 14.0F);
      NanoVG.nvgFontFace(this.nvg, InterfaceModule.fontRegular);
      if (var6) {
         this.drawTextAuto(var4 / 2, var5 / 2 + 1, var3, 15.0F, false, 18, var7, 1.0F);
      } else {
         this.drawTextAuto(24.0F, var5 / 2 + 1, var3, 15.0F, false, 17, var7, 1.0F);
      }

      return this.isHovered(var1, var2, 0, 0, var4, var5);
   }

   public void drawGlitchOverlay(int var1, int var2, float var3, int var4, float var5) {
      this.drawTintedGlitchOverlay(var1, var2, var3, var4, var5, this.accentColor);
   }

   public void drawTintedGlitchOverlay(int var1, int var2, float var3, int var4, float var5, NVGColor var6) {
      float[] var7 = new float[8];
      NanoVG.nvgCurrentTransform(this.nvg, var7);
      float var8 = this.effectTime / 1500.0F;
      float var9 = var4 % 5000 / 5000.0F;
      byte var10 = 4;

      for (int var11 = 0; var11 < var10; var11++) {
         PerlinNoiseSampler var12 = new PerlinNoiseSampler(new CheckedRandom(1337 + var11));
         NanoVG.nvgBeginPath(this.nvg);
         NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var1, var2, var3);
         NanoVG.nvgImagePattern(
            this.nvg,
            512.0F * (float)var12.sample(var9 + var8, var9, 0.0),
            512.0F * (float)var12.sample(var9, var9 + var8, 256.0),
            512.0F,
            512.0F,
            (float)((Math.PI * 4) * var12.sample(var9, var9 + var8, 128.0)),
            this.glitchTextureId,
            1.0F,
            paintScratch
         );
         colorScratch.r(var6.r() * 2.0F);
         colorScratch.g(var6.g() * 2.0F);
         colorScratch.b(var6.b() * 2.0F);
         colorScratch.a(var5 * (1.0F / var10));
         paintScratch.innerColor(colorScratch);
         NanoVG.nvgFillPaint(this.nvg, paintScratch);
         NanoVG.nvgFill(this.nvg);
      }
   }

   public boolean drawSettingRow(int var1, int var2, int var3, int var4, String var5, String var6, Color var7, float var8) {
      float var9 = 4.0F;
      boolean var10 = this.isHovered(var1, var2, 0, 0, var3, var4);
      NVGPaint var11 = NanoVG.nvgLinearGradient(
         this.nvg,
         0.0F,
         0.0F,
         0.0F,
         var4,
         this.shadeColor(this.panelColor, 1.0F + (var10 ? 1.0F : 0.8F), 255, colorScratch),
         this.shadeColor(this.panelColor, 1.0F + (var10 ? 0.8F : 0.6F), 255, colorScratch2),
         paintScratch
      );
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 1.0F, var3, var4 - 2, var9 - 1.0F);
      NanoVG.nvgFillPaint(this.nvg, var11);
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgStrokeWidth(this.nvg, 1.0F);
      Color var12 = var7.darker();
      var11 = NanoVG.nvgLinearGradient(
         this.nvg,
         0.0F,
         0.0F,
         0.0F,
         var4,
         rgba(var7.getRed(), var7.getGreen(), var7.getBlue(), 255, colorScratch),
         rgba(var12.getRed(), var12.getGreen(), var12.getBlue(), 255, colorScratch2),
         paintScratch
      );
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, var3 - var4 + 4, 3.0F, var4 - 6, var4 - 6, var9 - 1.0F);
      NanoVG.nvgStrokeColor(this.nvg, this.shadeColor(this.panelColor, 1.0F, 255, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      NanoVG.nvgFillPaint(this.nvg, var11);
      NanoVG.nvgFill(this.nvg);
      this.drawIconText(var4 / 2 - 2, var4 / 2 + 1, 59217, 16.0F, false, 18, var8, 1.0F);
      this.drawTextAuto(var3 / 2, var4 / 2 + 1, var5, 16.0F, false, 18, var8, 1.0F);
      return var10;
   }

   public boolean iut(int var1, int var2, int var3, int var4, String var5, String var6) {
      float var7 = 4.0F;
      boolean var8 = this.isHovered(var1, var2, 0, 0, var3, var4);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var3, var4, var7 - 1.0F);
      NanoVG.nvgFillPaint(
         this.nvg, NanoVG.nvgLinearGradient(this.nvg, 0.0F, 0.0F, var3, var4, this.shadeColor(this.panelColor, 2.0F, 255, colorScratch), this.shadeColor(this.panelColor, 2.7F, 255, colorScratch2), paintScratch)
      );
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, var3 - var4, 0.0F, var4, var4, var7 - 1.0F);
      NanoVG.nvgFillColor(this.nvg, this.shadeColor(this.accentColor, 0.1F, 60, colorScratch));
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgFontFace(this.nvg, InterfaceModule.fontRegular);
      float var9 = 16.0F;
      NanoVG.nvgFontSize(this.nvg, var9);

      int var10;
      for (var10 = 0; GlyphTextUtil.measureText(this.nvg, 0.0F, 0.0F, var5, (FloatBuffer)null) > var3 - 20 - var10 && var9 >= 8.0F; var10++) {
         NanoVG.nvgFontSize(this.nvg, var9 -= 2.0F);
      }

      this.drawTextAuto(var3 / 2 - var4 / 4 - var10, var4 / 2 + 1, var5, var9, false, 18, 1.0F, 1.0F);
      this.drawIconText(var3 - var4 / 2, var4 / 2 + 1, 59236, var9, false, 18, 1.0F, 1.0F);
      if (var8) {
         this.tooltipText = var6;
      }

      return var8;
   }

   public boolean pstk(int var1, int var2, int var3, int var4, String var5, String var6, boolean var7, float var8) {
      float var9 = 4.0F;
      boolean var10 = this.isHovered(var1, var2, 0, 0, var3, var4);
      NVGPaint var11 = NanoVG.nvgLinearGradient(
         this.nvg, 0.0F, 0.0F, 0.0F, var4, this.shadeColor(this.accentColor, var10 ? 0.8F : 0.6F, 255, colorScratch), this.shadeColor(this.accentColor, var10 ? 0.6F : 0.4F, 255, colorScratch2), paintScratch
      );
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, -1.0F, 1.0F, var4 - 2, var4 - 2, var9 - 1.0F);
      NanoVG.nvgFillPaint(this.nvg, var11);
      NanoVG.nvgFill(this.nvg);
      if (var8 % 1.0F != 0.0F) {
         this.drawIconText(var4 / 2 - 2, var4 / 2, 10003, 20.0F, false, 18, var8, var8);
         this.drawIconText(var4 / 2 - 2, var4 / 2, 10005, 20.0F, false, 18, var8, 1.0F - var8);
      } else {
         this.drawIconText(var4 / 2 - 2, var4 / 2, var7 ? 10003 : 10005, 20.0F, false, 18, var8, 1.0F);
      }

      this.drawTextAuto(var4 + 4, var4 / 2 + 2, var5, 16.0F, false, 17, var8, 1.0F);
      if (var10) {
         this.tooltipText = var6;
      }

      if (var10 && this.doj[0]) {
         this.doj[0] = false;
         return true;
      } else {
         return false;
      }
   }

   public boolean thg(int var1, int var2, int var3, int var4, float var5, float var6, String var7, boolean var8) {
      var8 |= this.isHovered(var1, var2, 0, 0, var3, var4);
      float var9 = 26.0F;
      NanoVG.nvgStrokeWidth(this.nvg, 1.0F);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, var9 - 2.0F, var3, 12.0F, 2.0F);
      NanoVG.nvgFillPaint(
         this.nvg, NanoVG.nvgBoxGradient(this.nvg, 0.0F, var9 - 2.0F + 1.0F, var3, 12.0F, 2.0F, 2.0F, rgba(0, 0, 0, 48, colorScratch), rgba(0, 0, 0, 128, colorScratch2), paintScratch)
      );
      NanoVG.nvgFill(this.nvg);
      float var10 = 1.0F + (var3 - 2) * var5;
      float var11 = var9 - 1.0F;
      NanoVG.nvgTranslate(this.nvg, var10, var11);
      this.drawGlitchOverlay((int)((var3 - 2) * (var6 - var5)), 10, 2.0F, var7.charAt(0), var8 ? 2.0F : 1.0F);
      NanoVG.nvgTranslate(this.nvg, -var10, -var11);
      this.drawTextAuto(var3 / 2, 13.0F, var7, 16.0F, false, 18, var8 ? 1.0F : 0.0F, 1.0F);
      return var8;
   }

   public boolean lgo(int var1, int var2, int var3, int var4, float var5, String var6, boolean var7) {
      var7 |= this.isHovered(var1, var2, 0, 0, var3, var4);
      float var8 = 26.0F;
      NanoVG.nvgStrokeWidth(this.nvg, 1.0F);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, var8 - 2.0F, var3, 12.0F, 2.0F);
      NanoVG.nvgFillPaint(
         this.nvg, NanoVG.nvgBoxGradient(this.nvg, 0.0F, var8 - 2.0F + 1.0F, var3, 12.0F, 2.0F, 2.0F, rgba(0, 0, 0, 48, colorScratch), rgba(0, 0, 0, 128, colorScratch2), paintScratch)
      );
      NanoVG.nvgFill(this.nvg);
      float var9 = 1.0F;
      float var10 = var8 - 1.0F;
      NanoVG.nvgTranslate(this.nvg, var9, var10);
      this.drawGlitchOverlay((int)((var3 - 2) * var5), 10, 2.0F, var6.charAt(0), var7 ? 2.0F : 1.0F);
      NanoVG.nvgTranslate(this.nvg, -var9, -var10);
      this.drawTextAuto(var3 / 2, 13.0F, var6, 16.0F, false, 18, var7 ? 1.0F : 0.0F, 1.0F);
      return var7;
   }

   public void nik(String var1, int var2, int var3, boolean var4) {
      String[] var5 = var1.replace(GlyphTextUtil.encode("\\n"), GlyphTextUtil.encode("\n")).split(GlyphTextUtil.encode("\n"));
      boolean var6 = PhantomClient.isInWorld() && (Boolean)PhantomClient.uiManager.interfaceModule.htk.getValue();
      ArrayList<String> var7 = new ArrayList<>(var5.length + 1);
      byte var8 = 32;

      for (int var9 = 0; var9 < var5.length; var9++) {
         String var10 = var5[var9];
         String[] var11 = var10.split(GlyphTextUtil.encode(" "));
         StringBuilder var12 = new StringBuilder();

         for (String var16 : var11) {
            if (var12.length() + var16.length() <= var8) {
               var12.append(var16).append(GlyphTextUtil.encode(" "));
            } else {
               var7.add(GlyphTextUtil.trim(var12.toString()));
               var12 = new StringBuilder(var16).append(GlyphTextUtil.encode(" "));
            }
         }

         if (var12.length() > 0) {
            var7.add(GlyphTextUtil.trim(var12.toString()));
         }
      }

      NanoVG.nvgFontFace(this.nvg, InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(this.nvg, 18.0F);
      int var27 = 0;
      int var28 = 0;
      int var29 = 0;

      for (String var32 : var7) {
         if (var32.startsWith(GlyphTextUtil.encode("clip:"))) {
            var29++;
         } else {
            var27 = Math.max(var27, (int)this.measureTextWidth(var32) + (var32.startsWith(GlyphTextUtil.encode("- ")) ? 30 : 8));
            var28 += 24;
         }
      }

      int var31 = var27 - 4;
      int var33 = (int)(var31 * 0.5620609F);
      if (var6) {
         var28 += (var33 + 5) * var29;
      } else if (var29 > 0) {
         var28 -= 24;
      }

      var2 = Math.max(0, Math.min(this.framebufferWidth - var27, var2));
      var3 = Math.max(0, Math.min(this.framebufferHeight - var28, var3));
      if (var4) {
         var2 -= var27 / 2;
      }

      var2 += 12;
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, var2, var3, var27, var28, 2.0F);
      NanoVG.nvgStrokeColor(this.nvg, this.shadeColor(this.panelColor, 0.25F, 235, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      NVGPaint var34 = NanoVG.nvgLinearGradient(
         this.nvg, var2, var3, var2, var3 + var28, this.shadeColor(this.panelColor, 0.5F, 200, colorScratch), this.shadeColor(this.panelColor, 0.3F, 200, colorScratch2), paintScratch
      );
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, var2 + 0.5F, var3 + 0.5F, var27 - 1, var28 - 1, 2.0F);
      NanoVG.nvgFillPaint(this.nvg, var34);
      NanoVG.nvgFill(this.nvg);
      byte var35 = 3;

      for (String var17 : var7) {
         if (var17.startsWith(GlyphTextUtil.encode("- "))) {
            this.drawIconText(var2 + 4, var3 + var35, 8594, 18.0F, false, 9, 1.0F, 1.0F);
            this.drawTextAuto(var2 + 34, var3 + var35, var17.substring(2), 18.0F, false, 9, 1.0F, 1.0F);
         } else if (var17.startsWith(GlyphTextUtil.encode("clip:"))) {
            if (var6) {
               String var18 = var17.substring(GlyphTextUtil.encode("clip:").length());
               int var19 = var2 + 4 + (var27 - 8) / 2 - var31 / 2;
               int var20 = var3 + var35;
               NanoVG.nvgBeginPath(this.nvg);
               NanoVG.nvgRoundedRect(this.nvg, var19, var20, var31, var33, 5.0F);
               NanoVG.nvgFillColor(this.nvg, rgba(20, 20, 20, 255, colorScratch));
               NanoVG.nvgFill(this.nvg);
               VideoPlayerWidget var21 = PhantomClient.videoManager.okh(this, var18);
               int var22 = 0;
               if (var21 != null) {
                  var22 = var21.glp(this);
               }

               if (var22 == 0) {
                  NanoVG.nvgSave(this.nvg);
                  NanoVG.nvgTranslate(this.nvg, var19, var20);
                  this.ique(var31, var33);
                  NanoVG.nvgRestore(this.nvg);
               } else {
                  byte var23 = 2;
                  NanoVG.nvgBeginPath(this.nvg);
                  NanoVG.nvgRoundedRect(this.nvg, var19 + var23, var20 + var23, var31 - var23 * 2, var33 - var23 * 2, 5.0F);
                  NanoVG.nvgFillPaint(
                     this.nvg, NanoVG.nvgImagePattern(this.nvg, var19 + var23, var20 + var23, var31 - var23 * 2, var33 - var23 * 2, 0.0F, var22, 1.0F, paintScratch)
                  );
                  NanoVG.nvgFill(this.nvg);
               }
            }
         } else if (var17.startsWith(GlyphTextUtil.encode("center:"))) {
            this.drawTextAuto(var2 + var27 / 2, var3 + var35, var17.substring(GlyphTextUtil.encode("center:").length()), 18.0F, false, 10, 1.0F, 1.0F);
         } else {
            this.drawTextAuto(var2 + 4, var3 + var35, var17, 18.0F, false, 9, 1.0F, 1.0F);
         }

         var35 += 24;
      }
   }

   public void pnb(float var1) {
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRect(this.nvg, 0.0F, 0.0F, this.framebufferWidth, this.framebufferHeight);
      NanoVG.nvgFillColor(this.nvg, rgba(20, 20, 20, (int)(var1 * 255.0F), colorScratch));
      NanoVG.nvgFill(this.nvg);
   }

   public void zvk(int var1, int var2, boolean var3) {
      NVGPaint var4 = NanoVG.nvgBoxGradient(this.nvg, 0.0F, 0.0F, var1, var2, 4.0F, 15.0F, rgba(0, 0, 0, 120, colorScratch), rgba(0, 0, 0, 0, colorScratch2), paintScratch);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRect(this.nvg, -10.0F, -10.0F, var1 + 20, var2 + 20);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var1, var2, 2.0F);
      NanoVG.nvgFillPaint(this.nvg, var4);
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgBeginPath(this.nvg);
      if (var3) {
         NanoVG.nvgMoveTo(this.nvg, 8.0F, 0.0F);
         NanoVG.nvgLineTo(this.nvg, 16.0F, -14.0F);
         NanoVG.nvgLineTo(this.nvg, 24.0F, 0.0F);
      }

      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var1, var2, 2.0F);
      NanoVG.nvgStrokeColor(this.nvg, this.shadeColor(this.panelColor, 0.25F, 235, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      NVGPaint var5 = NanoVG.nvgLinearGradient(this.nvg, 0.0F, 0.0F, var1, var2, this.shadeColor(this.panelColor, 1.0F, 255, colorScratch), this.shadeColor(this.panelColor, 0.7F, 255, colorScratch2), paintScratch);
      if (var3) {
         NanoVG.nvgBeginPath(this.nvg);
         NanoVG.nvgMoveTo(this.nvg, 8.0F, 0.0F);
         NanoVG.nvgLineTo(this.nvg, 16.0F, -14.0F);
         NanoVG.nvgLineTo(this.nvg, 24.0F, 0.0F);
         NanoVG.nvgFillPaint(this.nvg, var5);
         NanoVG.nvgFill(this.nvg);
      }

      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var1, var2, 2.0F);
      NanoVG.nvgFillPaint(this.nvg, var5);
      NanoVG.nvgFill(this.nvg);
   }

   public void emu(String var1, int var2, int var3) {
      int var4 = this.framebufferWidth / 3;
      byte var5 = 30;
      int var6 = this.framebufferWidth / 2 - var4 / 2;
      byte var7 = 4;
      this.translate(var6, var7);
      NVGPaint var8 = NanoVG.nvgLinearGradient(this.nvg, 0.0F, 0.0F, var4, var5, this.shadeColor(this.panelColor, 1.0F, 160, colorScratch), this.shadeColor(this.panelColor, 0.95F, 160, colorScratch2), paintScratch);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var4, var5, 6.0F);
      NanoVG.nvgFillPaint(this.nvg, var8);
      NanoVG.nvgFill(this.nvg);
      this.drawTextAuto(var4 / 2, var5 / 2 + 1, var1, 18.0F, true, 18, 0.5F, 1.0F);
      this.drawIconText(8, var5 / 2 + 1, 128269, 18.0F, true, 17, 0.5F, 1.0F);
      this.translate(-var6, -var7);
   }

   public int krq(GuiRenderContext var1, int var2, int var3, int var4, int var5, int var6, float[] var7) {
      float var8 = var7[0];
      NanoVG.nvgSave(this.nvg);
      float var9 = (float)(var8 * Math.PI * 2.0);
      float var10 = var4 * 0.5F;
      float var11 = var5 * 0.5F;
      float var12 = (var4 < var5 ? var4 : var5) * 0.5F - 5.0F;
      float var13 = var12 - 20.0F;
      int var14 = var4 / 2;
      int var15 = var5 / 2;
      float var16 = 90.0F;
      float var17 = (var13 + var12) / 2.0F;

      for (int var18 = 0; var18 < var16; var18++) {
         float var19 = (float)((var18 - 0.5F) / var16 * Math.PI * 2.0);
         float var20 = (float)((var18 + 1.5F) / var16 * Math.PI * 2.0);
         NanoVG.nvgBeginPath(this.nvg);
         NanoVG.nvgMoveTo(this.nvg, var10 + (float)Math.cos(var19) * var17, var11 + (float)Math.sin(var19) * var17);
         NanoVG.nvgLineTo(this.nvg, var10 + (float)Math.cos(var20) * var17, var11 + (float)Math.sin(var20) * var17);
         NanoVG.nvgClosePath(this.nvg);
         NanoVG.nvgStrokeColor(this.nvg, NanoVG.nvgHSL(var18 / var16, 1.0F, 0.5F, colorScratch));
         NanoVG.nvgStrokeWidth(this.nvg, var12 - var13);
         NanoVG.nvgStroke(this.nvg);
      }

      NanoVG.nvgStrokeWidth(this.nvg, 1.0F);
      NanoVG.nvgBeginPath(this.nvg);

      for (int var42 = 0; var42 < 360; var42++) {
         if (var42 == 0) {
            NanoVG.nvgMoveTo(
               this.nvg, var10 + (float)Math.cos(var42 / 360.0F * Math.PI * 2.0) * var13, var11 + (float)Math.sin(var42 / 360.0F * Math.PI * 2.0) * var13
            );
         } else {
            NanoVG.nvgLineTo(
               this.nvg, var10 + (float)Math.cos(var42 / 360.0F * Math.PI * 2.0) * var13, var11 + (float)Math.sin(var42 / 360.0F * Math.PI * 2.0) * var13
            );
         }
      }

      NanoVG.nvgClosePath(this.nvg);
      NanoVG.nvgStrokeColor(this.nvg, rgba(0, 0, 0, 120, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      NanoVG.nvgBeginPath(this.nvg);

      for (int var43 = 0; var43 < 360; var43++) {
         if (var43 == 0) {
            NanoVG.nvgMoveTo(
               this.nvg, var10 + (float)Math.cos(var43 / 360.0F * Math.PI * 2.0) * var12, var11 + (float)Math.sin(var43 / 360.0F * Math.PI * 2.0) * var12
            );
         } else {
            NanoVG.nvgLineTo(
               this.nvg, var10 + (float)Math.cos(var43 / 360.0F * Math.PI * 2.0) * var12, var11 + (float)Math.sin(var43 / 360.0F * Math.PI * 2.0) * var12
            );
         }
      }

      NanoVG.nvgClosePath(this.nvg);
      NanoVG.nvgStrokeColor(this.nvg, rgba(0, 0, 0, 120, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgCircle(this.nvg, var10, var11, var13 - 0.5F);
      NanoVG.nvgCircle(this.nvg, var10, var11, var12 + 0.5F);
      NanoVG.nvgStrokeColor(this.nvg, rgba(0, 0, 0, 64, colorScratch));
      NanoVG.nvgStrokeWidth(this.nvg, 1.0F);
      NanoVG.nvgStroke(this.nvg);
      NanoVG.nvgTranslate(this.nvg, var10, var11);
      NanoVG.nvgRotate(this.nvg, var9);
      NanoVG.nvgSave(this.nvg);
      NanoVG.nvgStrokeWidth(this.nvg, 2.0F);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRect(this.nvg, var13 - 1.0F, -3.0F, var12 - var13 + 2.0F, 6.0F);
      NanoVG.nvgStrokeColor(this.nvg, rgba(255, 255, 255, 192, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      NVGPaint var44 = NanoVG.nvgBoxGradient(
         this.nvg, var13 - 3.0F, -5.0F, var12 - var13 + 6.0F, 10.0F, 2.0F, 4.0F, rgba(0, 0, 0, 128, colorScratch), rgba(0, 0, 0, 0, colorScratch2), paintScratch
      );
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRect(this.nvg, var13 - 2.0F - 10.0F, -14.0F, var12 - var13 + 4.0F + 20.0F, 28.0F);
      NanoVG.nvgRect(this.nvg, var13 - 2.0F, -4.0F, var12 - var13 + 4.0F, 8.0F);
      NanoVG.nvgPathWinding(this.nvg, 2);
      NanoVG.nvgFillPaint(this.nvg, var44);
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgRestore(this.nvg);
      var17 = var13 - 6.0F;
      float var48 = (float)Math.cos(2.0943951648110333) * var17;
      float var50 = (float)Math.sin(2.0943951648110333) * var17;
      float var21 = (float)Math.cos(-2.0943951648110333) * var17;
      float var22 = (float)Math.sin(-2.0943951648110333) * var17;
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgMoveTo(this.nvg, var17, 0.0F);
      NanoVG.nvgLineTo(this.nvg, var48, var50);
      NanoVG.nvgLineTo(this.nvg, var21, var22);
      NanoVG.nvgClosePath(this.nvg);
      var44 = NanoVG.nvgLinearGradient(this.nvg, var17, 0.0F, var48, var50, NanoVG.nvgHSL(var8, 1.0F, 0.5F, colorScratch), rgba(255, 255, 255, 255, colorScratch2), paintScratch);
      NanoVG.nvgFillPaint(this.nvg, var44);
      NanoVG.nvgFill(this.nvg);
      var44 = NanoVG.nvgLinearGradient(this.nvg, (var17 + var48) * 0.5F, (0.0F + var50) * 0.5F, var21, var22, rgba(0, 0, 0, 0, colorScratch), rgba(0, 0, 0, 255, colorScratch2), paintScratch);
      NanoVG.nvgFillPaint(this.nvg, var44);
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgStrokeColor(this.nvg, rgba(0, 0, 0, 64, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      float var23 = (float)Math.cos(0.0) * var17;
      float var24 = (float)Math.sin(0.0) * var17;
      float var25 = (float)Math.cos(-2.0943951648110333) * var17;
      float var26 = (float)Math.sin(-2.0943951648110333) * var17;
      float var27 = (float)Math.cos(2.0943951648110333) * var17;
      float var28 = (float)Math.sin(2.0943951648110333) * var17;
      float var29 = var7[1];
      float var30 = var7[2];
      if (var30 < var29) {
         var30 = var29;
      }

      Vec2f var31 = this.ttn(var29, 1.0F - var30, var23, var24, var25, var26, var27, var28);
      var48 = var31.x;
      var50 = var31.y;
      NanoVG.nvgStrokeWidth(this.nvg, 2.0F);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgCircle(this.nvg, var48, var50, 5.0F);
      NanoVG.nvgStrokeColor(this.nvg, rgba(255, 255, 255, 192, colorScratch));
      NanoVG.nvgStroke(this.nvg);
      var44 = NanoVG.nvgRadialGradient(this.nvg, var48, var50, 7.0F, 9.0F, rgba(0, 0, 0, 64, colorScratch), rgba(0, 0, 0, 0, colorScratch2), paintScratch);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRect(this.nvg, var48 - 20.0F, var50 - 20.0F, 40.0F, 40.0F);
      NanoVG.nvgCircle(this.nvg, var48, var50, 7.0F);
      NanoVG.nvgPathWinding(this.nvg, 2);
      NanoVG.nvgFillPaint(this.nvg, var44);
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgRestore(this.nvg);
      double var32 = -var9;
      int var34 = (int)(var14 + ((double)var2 - var14) * Math.cos(var32) - ((double)var3 - var15) * Math.sin(var32));
      int var35 = (int)(var15 + ((double)var2 - var14) * Math.sin(var32) + ((double)var3 - var15) * Math.cos(var32));
      int var36 = (int)Math.sqrt(RotationMathUtil.square((double)var14 - var2) + RotationMathUtil.square((double)var15 - var3));
      switch (var6) {
         case 1:
            if (var36 >= 10) {
               double var52 = RotationMathUtil.square((double)var14 - var2) + RotationMathUtil.square((double)var15 - var3);
               double var39 = Math.toDegrees(Math.acos(((double)var2 - var14) / Math.sqrt(var52)));
               if (var3 < var15) {
                  var39 += Math.abs(180.0 - var39) * 2.0;
               }

               var7[0] = (float)(var39 / 360.0);
            }

            return var6;
         case 2:
            Vec2f var37 = this.eqo(var34 - var14, var35 - var15, var23, var24, var25, var26, var27, var28);
            var7[1] = var37.x;
            var7[2] = 1.0F - var37.y;
            return var6;
         default:
            if (this.doj[0]) {
               if (var36 <= 85 && var36 >= 65) {
                  return 1;
               }

               if (var36 < 60) {
                  return 2;
               }
            }

            return 0;
      }
   }

   private Vec2f ttn(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = 1.0F - var1 - var2;
      var9 = Math.max(0.0F, Math.min(1.0F, var9));
      return new Vec2f(var1 * var3 + var2 * var5 + var9 * var7, var1 * var4 + var2 * var6 + var9 * var8);
   }

   public Vec2f eqo(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      var5 -= var3;
      var7 -= var3;
      var1 -= var3;
      var3 = 0.0F;
      var6 -= var4;
      var8 -= var4;
      var2 -= var4;
      var4 = 0.0F;
      float var9 = var6 * var7 - var5 * var8;
      float var10 = (var1 * (var8 - var6) + var5 * (var2 - var8) + var7 * (var6 - var2)) / var9;
      float var11 = (var1 * var8 - var7 * var2) / -var9;
      var10 = Math.max(0.0F, Math.min(1.0F, var10));
      var11 = Math.max(0.0F, Math.min(1.0F, var11));
      return new Vec2f(var10, var11);
   }

   public void qxy(InlineDropdownWidget var1) {
      if (!Objects.equals(var1, this.opo)) {
         this.opo = var1;
         if (var1 == null) {
            this.dil.qou(420);
         } else {
            this.miwy.qou(420);
         }
      }
   }

   public void ique(int var1, int var2) {
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, 0.0F, 0.0F, var1, var2, 5.0F);
      NanoVG.nvgFillColor(this.nvg, rgba(15, 15, 15, 230, colorScratch));
      NanoVG.nvgFill(this.nvg);
      float var3 = 120.0F;
      float var4 = 10.0F;
      float var5 = (float)(0.5 + 0.5 * Math.sin(System.currentTimeMillis() % 1000L / 1000.0 * Math.PI * 2.0));
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, var1 / 2 - var3 / 2.0F, var2 / 2 - var4, var3, var4, 5.0F);
      NanoVG.nvgFillColor(this.nvg, this.shadeColor(this.panelColor, 0.85F, 230, colorScratch));
      NanoVG.nvgFill(this.nvg);
      NanoVG.nvgBeginPath(this.nvg);
      NanoVG.nvgRoundedRect(this.nvg, var1 / 2 - var3 / 2.0F + (var3 - var3 * 0.1F) * var5, var2 / 2 - var4, var3 * 0.1F, var4, 5.0F);
      NanoVG.nvgFillColor(this.nvg, this.shadeColor(this.accentColor, 0.85F, 230, colorScratch));
      NanoVG.nvgFill(this.nvg);
   }

   public void drawIconGlyph(GuiRenderContext var1, ByteBuffer var2, int var3, int var4) {
      NanoVG.nvgFontBlur(var1.nvg, 2.0F);
      NanoVG.nvgFillColor(var1.nvg, rgba(0, 0, 0, 128, colorScratch));
      GlyphTextUtil.drawTextBuffer(var1.nvg, var3, var4, var2);
      NanoVG.nvgFontBlur(var1.nvg, 0.0F);
      NanoVG.nvgFillColor(var1.nvg, rgba(220, 220, 220, 160, colorScratch));
      GlyphTextUtil.drawTextBuffer(var1.nvg, var3, var4, var2);
   }

   public void drawTextAt(GuiRenderContext var1, String var2, int var3, int var4) {
      NanoVG.nvgFontBlur(var1.nvg, 2.0F);
      NanoVG.nvgFillColor(var1.nvg, rgba(0, 0, 0, 128, colorScratch));
      GlyphTextUtil.drawText(var1.nvg, var3, var4, var2);
      NanoVG.nvgFontBlur(var1.nvg, 0.0F);
      NanoVG.nvgFillColor(var1.nvg, rgba(220, 220, 220, 160, colorScratch));
      GlyphTextUtil.drawText(var1.nvg, var3, var4, var2);
   }

   static {
      Arrays.fill(charWidthCache, -1.0F);
   }
}
