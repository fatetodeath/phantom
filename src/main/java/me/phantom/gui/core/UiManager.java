package me.phantom.gui.core;

import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.GuiScrollEvent;
import me.phantom.event.render.UiRenderEvent;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.gui.hud.HudConfigPanel;
import me.phantom.gui.hud.NotificationManager;
import me.phantom.gui.screen.BlankClientScreen;
import me.phantom.gui.widget.ClickGuiCategoryWindow;
import me.phantom.gui.widget.EasedAnimation;
import me.phantom.gui.widget.SearchHighlightRect;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.ArrayListHudModule;
import me.phantom.module.internal.ConfigsButtonModule;
import me.phantom.module.internal.HudModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.module.internal.QuickConsoleModule;
import me.phantom.module.misc.ScreenMoveModule;
import me.phantom.render.GlfwContextGuard;
import me.phantom.render.GpuTextureWrapper;
import me.phantom.render.hud.ItemIconRenderer;
import me.phantom.render.NanoVGFramebuffer;
import me.phantom.render.Render3DUtil;
import me.phantom.render.VideoManager;
import me.phantom.setting.ColorSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.PoolAccessor;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

public class UiManager {
   public List<ClickGuiCategoryWindow> jeo = new LinkedList<>();
   public final NotificationManager xhe;
   private final GuiRenderContext context;
   public final ItemIconRenderer elz;
   private boolean etu = false;
   public final EasedAnimation enableAnimation = new EasedAnimation(false);
   public final EasedAnimation disableAnimation = new EasedAnimation(false);
   public final ArrayListHudModule vsn;
   public final HudModule itp;
   public final InterfaceModule interfaceModule;
   public final ScreenMoveModule gmx;
   public final QuickConsoleModule syz;
   public float nzo;
   public String searchQuery = GlyphTextUtil.encode("");
   public NanoVGFramebuffer gbm = new NanoVGFramebuffer();
   public int hpp = -1;
   public int ncy = -1;

   public UiManager() throws Throwable {
      this.xhe = new NotificationManager();
      this.vsn = (ArrayListHudModule)PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("List"));
      this.itp = (HudModule)PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("Hud"));
      this.interfaceModule = (InterfaceModule)PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("UI"));
      this.gmx = (ScreenMoveModule)PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("ScreenMove"));
      this.syz = (QuickConsoleModule)PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("QuickConsole"));
      this.context = new GuiRenderContext(this.interfaceModule);
      this.elz = new ItemIconRenderer(this.context);
      this.hwv();
   }

   public List<ClickGuiCategoryWindow> opt() {
      return Collections.unmodifiableList(this.jeo);
   }

   public void hwv() {
      this.jeo.clear();
      int var1 = 0;

      for (ModuleCategory var5 : ModuleCategory.nku()) {
         this.jeo.add(new ClickGuiCategoryWindow(var5, 20, 20 + var1++ * 45));
      }
   }

   public void update() {
      for (int var1 = 0; var1 < this.jeo.size(); var1++) {
         this.jeo.get(var1).update();
      }
   }

   public boolean nde() {
      return MinecraftClient.getInstance().currentScreen instanceof BlankClientScreen;
   }

   public void qcs() {
      boolean[] var1 = this.context.xfy;
      boolean[] var2 = this.context.doj;
      long var3 = MinecraftClient.getInstance().getWindow().getHandle();

      for (int var5 = 0; var5 < 8; var5++) {
         boolean var6 = var1[var5];
         boolean var7 = GLFW.glfwGetMouseButton(var3, var5) == 1;
         boolean var8 = var7 && !var6;
         if (var8) {
            if (this.interfaceModule.isEnabled()) {
               if (this.context.keybindCaptureTarget != null) {
                  this.context.keybindCaptureTarget.setKeybind(100000 + var5, false);
                  this.context.keybindCaptureTarget = null;
                  var8 = false;
               }
            } else if (MinecraftClient.getInstance().currentScreen == null) {
               for (BaseModule var12 : PhantomClient.moduleManager.getModules()) {
                  if (!var12.isHoldMode() && var12.getKeyCode() != 0 && var12.getKeyCode() == 100000 + var5) {
                     var12.setEnabled(!var12.isEnabled(), true);
                     var8 = false;
                  }
               }
            }
         }

         var2[var5] = var8;
         var1[var5] = var7;
      }

      for (BaseModule var16 : PhantomClient.moduleManager.getModules()) {
         if (var16.isHoldMode() && var16.getKeyCode() != 0) {
            var16.setEnabled(var16.getKeyCode() >= 100000 && var16.getKeyCode() < 100008 ? var1[var16.getKeyCode() - 100000] : var16.getKeyCode() < 1024 && this.context.gml[var16.getKeyCode()] && PhantomClient.isScreenClosed(), true);
         }
      }
   }

   public void suw(MatrixStack var1, float var2) {
      try {
         GlfwContextGuard var3 = new GlfwContextGuard();

         try {
            GuiRenderContext var4 = this.context;

            try {
               MinecraftClient var5 = MinecraftClient.getInstance();
               GameRenderer var6 = var5.gameRenderer;
               Camera var7 = var6.getCamera();
               Vec3d var8 = var7.getPos();
               Vec3d var9 = GameInteractionUtil.getLerpedEyePos(var5.player, var2);
               var1.push();
               var1.translate(-var8.x, -var8.y, -var8.z);
               var1.translate(var9.x, var9.y, var9.z);
               Render3DUtil.sgd(var1.peek());
               var1.pop();
               GL11.glEnable(3042);
               var1.push();
               var1.translate(-var8.x, -var8.y, -var8.z);
               float var10 = var2 - var4.tickDelta;
               if (var10 < 0.0F) {
                  var10 = var2;
               }

               var4.worldTickDelta = var10;
               var4.tickDelta = var2;
               var4.lkq = var4.lkq + var4.worldTickDelta;
               var4.effectTime = var4.effectTime + var4.worldTickDelta * (Float)this.interfaceModule.ydq.getValue();
               this.gbm.ysf(var4.framebufferWidth, var4.framebufferHeight);
               PhantomClient.eventManager.dispatch(WorldRenderMatricesEvent.oki(var1, var2, var4.worldTickDelta, var8));
               this.gbm.ipg();
            } finally {
               var1.pop();
            }
         } catch (Throwable var18) {
            try {
               var3.close();
            } catch (Throwable var16) {
               var18.addSuppressed(var16);
            }

            throw var18;
         }

         var3.close();
      } catch (Throwable var19) {
         PhantomClient.fatalError(GlyphTextUtil.encode("UIManager"), GlyphTextUtil.encode("General render world crash"), var19);
      }
   }

   public void euj() {
      Integer var1 = (Integer)this.interfaceModule.hzx.getValue();
      if (var1 > 0) {
         MinecraftClient var2 = MinecraftClient.getInstance();
         float var3 = MinecraftClient.getInstance() != null && !this.interfaceModule.isEnabled() ? 1.0F - this.disableAnimation.hee() : this.enableAnimation.hee();
         if (var3 > 0.0F) {
            PostEffectProcessor var4 = var2.getShaderLoader().loadPostEffect(Identifier.ofVanilla("blur"), DefaultFramebufferSet.MAIN_ONLY);
            if (var4 != null) {
               float var5 = var3 * (var1.intValue() / 100.0F) * 16.0F;
               var4.render(var2.getFramebuffer(), ((PoolAccessor)var2.gameRenderer).getPool(), var1x -> var1x.setUniform("Radius", new float[]{var5}));
            }
         }
      }
   }

   public void upfx(DrawContext var1, float var2) {
      this.euj();

      try {
         GlfwContextGuard var3 = new GlfwContextGuard();

         try {
            GLFW.glfwMakeContextCurrent(this.context.contextWindow);
            Render3DUtil.jmb("postRenderWorld");
            GuiRenderContext var4 = this.context;
            if (!this.context.xfy[0]) {
               this.nzo = 1.0F / (((Integer)this.interfaceModule.rfv.getValue()).intValue() / 100.0F) * PhantomClient.getUiScaleFactor();
            }

            Window var5 = MinecraftClient.getInstance().getWindow();
            var4.framebufferWidth = (int)(var5.getWidth() * this.nzo / PhantomClient.getUiScaleFactor());
            var4.framebufferHeight = (int)(var5.getHeight() * this.nzo / PhantomClient.getUiScaleFactor());
            var4.screenWidth = (int)(MinecraftClient.getInstance().mouse.getX() * this.nzo / PhantomClient.getUiScaleFactor());
            var4.screenHeight = (int)(MinecraftClient.getInstance().mouse.getY() * this.nzo / PhantomClient.getUiScaleFactor());
            var4.tickDelta = var2;
            var4.mouseX = 0;
            var4.mouseY = 0;
            var4.gwj = var1.getScaledWindowWidth();
            var4.aypf = var1.getScaledWindowHeight();
            this.interfaceModule.jfj.xam(1.0F, var4.panelColor);
            this.interfaceModule.mbw.xam(1.0F, var4.accentColor);
            this.interfaceModule.nkj.xam(1.0F, var4.textBaseColor);
            this.interfaceModule.qir.xam(1.0F, var4.blurColor);
            var4.glowAmount = (Integer)this.interfaceModule.uxm.getValue();
            if (this.context.ahm) {
               this.context.keybindCaptureTarget = null;
               this.context.ahm = false;
            }

            this.qcs();
            Render3DUtil.jmb("processInput");
            if (var4.opo != null) {
               var4.opo.jdm(var4, var4.screenWidth - var4.opo.dsx(), var4.screenHeight - var4.opo.dsx());
            }

            Render3DUtil.jmb("updateUI");
            PhantomClient.videoManager.jyu(var4);
            Render3DUtil.jmb("videoManager");
            this.gbm.ysf(var4.framebufferWidth, var4.framebufferHeight);
            Render3DUtil.jmb("frame render");
            NanoVG.nvgBeginFrame(var4.nvg, var4.framebufferWidth, var4.framebufferHeight, 1.0F / this.nzo);
            Render3DUtil.jmb("begin frame");
            PhantomClient.eventManager.dispatch(WorldRenderOverlayEvent.get(var4));
            this.qyqi(var1, var2, true);
            Render3DUtil.jmb("render hud");
            PhantomClient.eventManager.dispatch(UiRenderEvent.get(var4));
            NanoVG.nvgEndFrame(var4.nvg);
            Render3DUtil.jmb("end frame");
            GL11.glFlush();
            Render3DUtil.jmb("flush");
            this.gbm.ipg();
         } catch (Throwable var7) {
            try {
               var3.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }

            throw var7;
         }

         var3.close();
      } catch (Throwable var8) {
         PhantomClient.fatalError(GlyphTextUtil.encode("UIManager"), GlyphTextUtil.encode("General post render world crash"), var8);
      }

      this.gbm.drm(var1, 0, 0, var1.getScaledWindowWidth(), var1.getScaledWindowHeight());
      Render3DUtil.jmb("blit");
   }

   public void qyqi(DrawContext var1, float var2, boolean var3) {
      try {
         GuiRenderContext var4 = this.context;
         boolean var5 = this.interfaceModule.isEnabled();
         ColorSetting.rwat = null;
         boolean var6 = false;
         if (var6) {
            NanoVG.nvgBeginPath(var4.nvg);
            NanoVG.nvgRect(var4.nvg, 0.0F, 0.0F, var4.framebufferWidth, var4.framebufferHeight);
            NanoVG.nvgFillColor(var4.nvg, GuiRenderContext.rgba(0, 0, 0, 220, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var4.nvg);
            NanoVG.nvgFontFace(var4.nvg, GlyphTextUtil.encode("icons"));
            NanoVG.nvgFontSize(var4.nvg, 20.0F);
            GlyphTextUtil.setTextAlign(var4.nvg, 9);

            try {
               int[] var22 = new int[]{
                  9834,
                  9835,
                  127925,
                  128269,
                  128294,
                  9993,
                  9829,
                  9825,
                  9733,
                  9734,
                  128100,
                  128101,
                  59136,
                  127916,
                  127748,
                  128247,
                  9871,
                  9776,
                  10003,
                  10005,
                  10006,
                  10062,
                  43,
                  10133,
                  8862,
                  45,
                  10134,
                  8863,
                  10067,
                  59140,
                  8505,
                  59141,
                  128281,
                  8962,
                  128279,
                  128206,
                  128274,
                  128275,
                  59148,
                  128278,
                  128209,
                  9873,
                  128077,
                  128078,
                  128229,
                  128228,
                  59153,
                  59154,
                  59155,
                  10150,
                  10078,
                  59156,
                  59157,
                  10002,
                  59158,
                  59159,
                  9000,
                  59160,
                  59168,
                  128276,
                  9888,
                  128165,
                  59170,
                  59171,
                  59172,
                  59175,
                  10146,
                  59176,
                  9749,
                  59177,
                  59184,
                  59190,
                  59191,
                  128196,
                  59185,
                  128240,
                  128214,
                  128213,
                  128193,
                  59192,
                  128230,
                  59194,
                  128222,
                  9881,
                  9874,
                  59196,
                  59198,
                  59197,
                  128092,
                  128197,
                  59200,
                  59201,
                  127908,
                  128263,
                  128266,
                  59202,
                  128340,
                  9203,
                  128161,
                  128261,
                  128262,
                  9681,
                  128683,
                  59204,
                  59206,
                  59212,
                  59213,
                  59214,
                  59215,
                  59224,
                  59225,
                  59226,
                  59227,
                  59228,
                  59229,
                  59230,
                  59231,
                  59232,
                  59233,
                  59234,
                  59235,
                  59236,
                  59237,
                  59238,
                  59239,
                  11015,
                  11013,
                  10145,
                  11014,
                  9662,
                  9666,
                  9656,
                  9652,
                  58544,
                  58541,
                  58542,
                  58543,
                  8595,
                  8592,
                  8594,
                  8593,
                  10226,
                  10227,
                  128260,
                  8627,
                  8624,
                  128256,
                  128257,
                  8646,
                  9654,
                  9632,
                  9097,
                  9899,
                  9197,
                  9198,
                  9193,
                  9194,
                  59240,
                  59241,
                  59242,
                  59243,
                  127919,
                  127912,
                  57349,
                  57347,
                  128246,
                  127942,
                  128267,
                  59249,
                  128187,
                  128241,
                  59254,
                  59255,
                  59256,
                  127758,
                  9729,
                  9928,
                  9889,
                  9789,
                  9992,
                  59291,
                  127810,
                  59272,
                  59273,
                  128188,
                  59278,
                  59275,
                  59276,
                  59277,
                  59290,
                  59297,
                  8734,
                  9003,
                  59217,
                  128200,
                  128202,
                  128318,
                  9991,
                  127891,
                  59218,
                  127915,
                  128166,
                  128167,
                  59219,
                  128179,
                  128190,
                  128203,
                  128227,
                  59220,
                  59221,
                  59222,
                  59223,
                  128273,
                  59280,
                  59281,
                  59282,
                  59283,
                  59284,
                  128640,
                  59298,
                  59299,
                  59301,
                  59302,
                  59303,
                  59304,
                  59305,
                  59306,
                  59307,
                  59308,
                  59309,
                  59310,
                  59311,
                  62208,
                  62209,
                  62211,
                  62212,
                  62214,
                  62215,
                  62217,
                  62218,
                  62220,
                  62221,
                  62222,
                  62223,
                  62224,
                  62226,
                  62227,
                  62229,
                  62230,
                  62232,
                  62233,
                  62235,
                  62236,
                  62238,
                  62239,
                  62241,
                  62242,
                  62244,
                  62245,
                  62247,
                  62248,
                  62250,
                  62253,
                  62256,
                  62259,
                  62262,
                  62265,
                  62266,
                  62268,
                  62271,
                  62274,
                  62277,
                  62280,
                  62283,
                  62286,
                  62289,
                  62292,
                  62295,
                  62977,
                  62976,
                  62979
               };

               for (int var23 = 0; var23 < var22.length; var23++) {
                  int var24 = var23 / 12 * 35 + 5;
                  int var25 = var23 % 12 * 35 + 5;
                   if (var4.isHovered(var4.screenWidth, var4.screenHeight, var24, var25, 20, 20)) {
                      NanoVG.nvgFillColor(var4.nvg, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
                  } else {
                     NanoVG.nvgFillColor(var4.nvg, GuiRenderContext.rgba(200, 200, 200, 255, GuiRenderContext.colorScratch));
                  }

                  GlyphTextUtil.drawTextBuffer(var4.nvg, var24 + 4, var25 + 4, GuiRenderContext.iconBuffer(var22[var23]));
               }
            } catch (Throwable var20) {
               var20.printStackTrace();
            }
         } else {
            Render3DUtil.jmb("render hud");
            if (MinecraftClient.isHudEnabled() && !MinecraftClient.getInstance().getDebugHud().shouldShowDebugHud()) {
               if (PhantomClient.isInWorld()) {
                  if (PhantomClient.isScreenClosed()) {
                     PhantomClient.eventManager.dispatch(UiRenderEvent.get(var4));
                  }

                  if (PhantomClient.isScreenClosed() || var4.activeDialog instanceof HudConfigPanel || PhantomClient.isDebugBuild) {
                     this.vsn.fkf(var4);
                     this.itp.lfc(var4);
                  }
               }

               if (!(Boolean)this.interfaceModule.wcc.getValue() || var5) {
                  this.xhe.render(var4, var4.screenWidth, var4.screenHeight, 0);
               }
            }

            boolean[] var7 = null;
            boolean[] var8 = null;
            if (!var5) {
               var7 = (boolean[])this.context.doj.clone();
               var8 = (boolean[])this.context.xfy.clone();
               Arrays.fill(this.context.doj, false);
               Arrays.fill(this.context.xfy, false);
            }

            if (var5) {
               if (!(MinecraftClient.getInstance().currentScreen instanceof DownloadingTerrainScreen)
                     && !(MinecraftClient.getInstance().currentScreen instanceof BlankClientScreen)
                  || MinecraftClient.getInstance().currentScreen == null
                  || MinecraftClient.getInstance().world == null && !(MinecraftClient.getInstance().currentScreen instanceof BlankClientScreen)) {
                  MinecraftClient.getInstance().setScreen(new BlankClientScreen());
               }

               this.etu = true;
               if (var4.activeDialog != null) {
                  NanoVG.nvgBeginPath(var4.nvg);
                  NanoVG.nvgRect(var4.nvg, 0.0F, 0.0F, var4.framebufferWidth, var4.framebufferHeight);
                  NanoVG.nvgFillColor(var4.nvg, GuiRenderContext.rgba(0, 0, 0, 200, GuiRenderContext.colorScratch));
                  NanoVG.nvgFill(var4.nvg);
                  var4.activeDialog.vvb(this.context, var4.screenWidth, var4.screenHeight);
               } else {
                  this.npu(var4, var4.screenWidth, var4.screenHeight);
               }
            } else if (this.etu) {
               var4.activeDialog = null;
               this.disableAnimation.qou(300);
               this.etu = false;
               this.interfaceModule.setEnabled(false, true);
               this.npu(var4, var4.screenWidth, var4.screenHeight);
            } else if (this.disableAnimation.sdx()) {
               var4.activeDialog = null;
               this.npu(var4, var4.screenWidth, var4.screenHeight);
            } else {
               var4.activeDialog = null;
               var4.opo = null;
               var4.tooltipText = null;
               var4.kcr = null;
               var4.xwl = null;
            }

            Render3DUtil.jmb("notification renderer");
            if (!(Boolean)this.interfaceModule.wcc.getValue() || var5) {
               this.xhe.render(var4, var4.screenWidth, var4.screenHeight, 1);
            }

            if (var4.tooltipText != null) {
               var4.nik(var4.tooltipText, var4.screenWidth, var4.screenHeight, false);
               var4.tooltipText = null;
            }

            Render3DUtil.jmb("render tooltip");
            if (!var5 || !(Boolean)this.interfaceModule.owv.getValue() || this.context.opo != null) {
               this.hpp = -1;
               this.ncy = -1;
            } else if (var4.doj[0]) {
               this.hpp = var4.screenWidth;
               this.ncy = var4.screenHeight;
            } else if (!var4.xfy[0] && this.hpp != -1 && this.ncy != -1) {
               int var9 = var4.screenWidth - this.hpp;
               int var10 = var4.screenHeight - this.ncy;
               double var11 = Math.sqrt(var9 * var9 + var10 * var10);
               if (var11 > 40.0) {
                  int var13 = (int)(Math.acos(var9 / var11) * 180.0 / Math.PI);
                  if (var10 < 0.0) {
                     var13 += Math.abs(180 - var13) * 2;
                  }

                  if (var13 < 45 || var13 > 135) {
                     if (var13 < 200 && var13 > 135) {
                        if (var4.screenWidth <= var4.framebufferWidth / 3) {
                           ArrayList<ClickGuiCategoryWindow> var27 = new ArrayList<>(this.jeo);
                           var27.sort(Comparator.comparingInt(var0 -> var0.y));
                           byte var29 = 60;

                           for (ClickGuiCategoryWindow var35 : var27) {
                              var35.sfg(false);
                              var35.x = 20;
                              var35.y = var29;
                              var29 += 45;
                           }
                        }
                     } else if (var13 > 220 && var13 <= 300) {
                        if (var4.screenHeight <= var4.framebufferHeight / 3) {
                           ArrayList<ClickGuiCategoryWindow> var26 = new ArrayList<>(this.jeo);
                           var26.sort(Comparator.comparingInt(var0 -> var0.x));
                           int var28 = 5;

                           for (ClickGuiCategoryWindow var34 : var26) {
                              var34.sfg(true);

                              for (BaseModule var19 : var34.sgi) {
                                 var19.setExpanded(false);
                              }

                              var34.x = var28;
                              var34.y = 50;
                              var28 += var34.eec + 5;
                           }
                        }
                     } else if (var4.screenWidth >= var4.framebufferWidth / 3 * 2) {
                        ArrayList<ClickGuiCategoryWindow> var14 = new ArrayList<>(this.jeo);
                        var14.sort(Comparator.comparingInt(var0 -> var0.y));
                        int var15 = 0;

                        for (ClickGuiCategoryWindow var17 : var14) {
                           var15 = Math.max(var17.eec, var15);
                        }

                        byte var30 = 60;

                        for (ClickGuiCategoryWindow var18 : var14) {
                           var18.sfg(false);
                           var18.x = var4.framebufferWidth - 20 - var15;
                           var18.y = var30;
                           var30 += 45;
                        }
                     }
                  }
               }

               this.hpp = -1;
               this.ncy = -1;
            }

            if (var7 != null) {
               System.arraycopy(var7, 0, this.context.doj, 0, var7.length);
               System.arraycopy(var8, 0, this.context.xfy, 0, var8.length);
            }
         }

         Render3DUtil.jmb("post render");
      } catch (Throwable var21) {
         PhantomClient.fatalError(GlyphTextUtil.encode("PhantomClient"), GlyphTextUtil.encode("Failed to render phantom hud"), var21);
      }
   }

   public void iss(GuiRenderContext var1, float var2, float var3, float var4, float var5) {
      NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(var1.nvg, 22.0F);
      String var6 = GlyphTextUtil.trim(this.searchQuery);
      var4 = GlyphTextUtil.measureText(var1.nvg, 0.0F, 0.0F, var6, (FloatBuffer)null) + 16.0F;
      var2 = var1.framebufferWidth / 2 - var4 / 2.0F;
      var3 = 10.0F;
      var5 = 40.0F;
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var2 + 2.0F, var3 + 2.0F, var4 - 4.0F, var5 - 4.0F, 4.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(130, 130, 130, 120, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      GlyphTextUtil.setTextAlign(var1.nvg, 18);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(20, 20, 20, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFontBlur(var1.nvg, 2.0F);
      GlyphTextUtil.drawText(var1.nvg, var2 + var4 / 2.0F, var3 + var5 / 2.0F, var6);
      NanoVG.nvgFontBlur(var1.nvg, 0.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(220, 220, 220, 220, GuiRenderContext.colorScratch));
      GlyphTextUtil.drawText(var1.nvg, var2 + var4 / 2.0F, var3 + var5 / 2.0F, var6);
   }

   private void npu(GuiRenderContext var1, int var2, int var3) {
      float var4 = MinecraftClient.getInstance() != null && !this.interfaceModule.isEnabled() ? 1.0F - this.disableAnimation.hee() : this.enableAnimation.hee();
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRect(var1.nvg, 0.0F, 0.0F, var1.framebufferWidth, var1.framebufferHeight);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(0, 0, 0, (int)(200.0F * var4), GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      if (var4 != 1.0F) {
         NanoVG.nvgGlobalAlpha(var1.nvg, var4);
         NanoVG.nvgSave(var1.nvg);
         NanoVG.nvgTranslate(var1.nvg, var1.framebufferWidth / 2, var1.framebufferHeight / 2);
         NanoVG.nvgScale(var1.nvg, (float)(Math.tan(var4) / Math.tan(1.0)), (float)(Math.tan(var4) / Math.tan(1.0)));
         NanoVG.nvgTranslate(var1.nvg, -var1.framebufferWidth / 2, -var1.framebufferHeight / 2);
      }

      for (int var5 = this.jeo.size() - 1; var5 >= 0; var5--) {
         ClickGuiCategoryWindow var6 = this.jeo.get(var5);
         var6.yzf(var1);
      }

      var1.tooltipText = null;
      ClickGuiCategoryWindow var13 = var1.kcr;
      if (var13 == null) {
         for (int var14 = 0; var14 < this.jeo.size(); var14++) {
            ClickGuiCategoryWindow var7 = this.jeo.get(var14);
            if (var7.lzu(var1, var2, var3)) {
               var13 = var7;
               break;
            }
         }
      }

      for (int var15 = this.jeo.size() - 1; var15 >= 0; var15--) {
         ClickGuiCategoryWindow var18 = this.jeo.get(var15);
         boolean var8 = Objects.equals(var18, var13);
         if ((!var8 || var1.opo != null) && var1.xwl == null) {
            var18.mam(var1, -1, -1, var1.mzx, var1.mzx);
         } else {
            var18.mam(var1, var2, var3, var1.doj, var1.xfy);
         }
      }

      if (var1.dil.sdx()) {
         var1.pnb((1.0F - var1.dil.hee()) * 0.65F);
      } else if (var1.opo != null) {
         var1.pnb(var1.miwy.hee() * 0.65F);
         NanoVG.nvgGlobalAlpha(var1.nvg, var1.miwy.hee());
         int var16 = var1.opo.dsx();
         int var19 = var1.opo.ivt();
         var1.translate(var16, var19);
         var1.zvk(var1.opo.exl(), var1.opo.cw(), var1.opo.kdf());
         if (var1.doj[0] && !var1.isHovered(var2, var3, 0, 0, var1.opo.exl(), var1.opo.cw())) {
            var1.doj[0] = false;
            var1.qxy(null);
         } else {
            var1.opo.abu(var1, var2 - var16, var3 - var19);
         }

         var1.translate(-var16, -var19);
         NanoVG.nvgGlobalAlpha(var1.nvg, 1.0F);
      }

      if (this.searchQuery.length() > 0) {
         long var17 = var1.nvg;
         NanoVG.nvgBeginPath(var17);
         NanoVG.nvgRect(var17, 0.0F, 0.0F, var1.framebufferWidth, var1.framebufferHeight);
         NanoVG.nvgFillColor(var17, GuiRenderContext.rgba(0, 0, 0, 180, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var17);

         for (BaseModule var11 : PhantomClient.moduleManager.getModules()) {
            if (var11.searchHighlight != null) {
               SearchHighlightRect var12 = var11.searchHighlight;
               NanoVG.nvgSave(var1.nvg);
               NanoVG.nvgTranslate(var1.nvg, var12.x, var12.y);
               var11.renderModuleEntry(var1, var12.width, var1.screenWidth, var1.screenHeight, var1.doj, var1.xfy);
               NanoVG.nvgRestore(var1.nvg);
               NanoVG.nvgSave(var1.nvg);
               NanoVG.nvgTranslate(var1.nvg, var12.x, var12.y);
               NanoVG.nvgBeginPath(var17);
               NanoVG.nvgRoundedRect(var17, 0.0F, 0.0F, var12.width, var12.height, 3.0F);
               NanoVG.nvgStrokeWidth(var1.nvg, 2.0F);
               NanoVG.nvgStrokeColor(var17, var1.shadeColor(var1.accentColor, 0.6F, 200, GuiRenderContext.colorScratch));
               NanoVG.nvgStroke(var17);
               NanoVG.nvgRestore(var1.nvg);
            }
         }

         float var21 = var1.framebufferWidth / 3;
         this.iss(var1, var1.framebufferWidth / 2 - var21 / 2.0F, 6.0F, var21, 48.0F);
      }

      if (!var1.xfy[0]) {
         var1.kcr = null;
         var1.xwl = null;
      }

      if (var13 != null && (var1.xfy[0] || var1.xfy[1]) && !Objects.equals(this.jeo.get(0), var13)) {
         this.jeo.remove(var13);
         this.jeo.add(0, var13);
      }

      if (var4 != 1.0F) {
         NanoVG.nvgGlobalAlpha(var1.nvg, 1.0F);
         NanoVG.nvgRestore(var1.nvg);
      }
   }

   public boolean xki(float var1) {
      try {
         if (this.etu) {
            if (this.context.activeDialog != null) {
               this.context.activeDialog.puu(this.context, var1);
            }

            return true;
         } else {
            return PhantomClient.eventManager.dispatch(GuiScrollEvent.wf(var1)).isCancelled();
         }
      } catch (Throwable var3) {
         PhantomClient.fatalError(GlyphTextUtil.encode("UIManager"), GlyphTextUtil.encode("Failed to process scroll event ") + var1, var3);
         return false;
      }
   }

   public GuiRenderContext getContext() {
      return this.context;
   }

   public NotificationManager notifications() {
      return this.xhe;
   }

   public ItemIconRenderer sdd() {
      return this.elz;
   }

   public boolean guf() {
      return this.searchQuery.length() > 0 || (Boolean)this.interfaceModule.pvs.getValue() && (!this.gmx.isEnabled() || !(Boolean)this.gmx.al.getValue());
   }

   public boolean okr(char var1, int var2) {
      if (this.interfaceModule.isEnabled()) {
         if (this.context.xwl != null) {
            return true;
         } else if (this.context.keybindCaptureTarget != null) {
            return true;
         } else if (this.context.activeDialog != null) {
            this.context.activeDialog.na(var1, var2);
            return true;
         } else {
            if (this.guf() && this.searchQuery.length() <= 24) {
               this.searchQuery = this.searchQuery + GlyphTextUtil.encodeChars(String.valueOf(GlyphTextUtil.lowercase(String.valueOf(var1)).charAt(0)));
            }

            return true;
         }
      } else {
         if (this.syz.isEnabled()) {
            this.syz.na(var1, var2);
         }

         return false;
      }
   }

   public boolean hcj(int var1, int var2, int var3, int var4) {
      try {
         if (var1 >= 0 && var1 < 1024) {
            this.context.gml[var1] = var3 != 0;
            this.context.dugn[var2] = var3 != 0;
         }

         if (this.interfaceModule.isEnabled()) {
            if (var1 == 70 && var3 == 1 && var4 == 2 && this.searchQuery.length() <= 1) {
               this.searchQuery = GlyphTextUtil.encode(" ");
            }

            if (ColorSetting.rwat != null) {
               long var13 = MinecraftClient.getInstance().getWindow().getHandle();
               if (var1 != 67 || var3 != 1 || var4 != 2 && var4 != 8) {
                  if (var1 == 86 && var3 == 1 && (var4 == 2 || var4 == 8)) {
                     try {
                        String var15 = GLFW.glfwGetClipboardString(var13);
                        if (!var15.startsWith(GlyphTextUtil.plain("#")) || var15.length() != 7) {
                           throw new IllegalArgumentException();
                        }

                        ColorSetting.rwat.zpvg(var15);
                        this.xhe.mfs(GlyphTextUtil.encode("color.pasted"));
                     } catch (Throwable var11) {
                        this.xhe.klr(GlyphTextUtil.encode("color.paste.wrong"));
                     }
                  }
               } else {
                  this.xhe.mfs(GlyphTextUtil.encode("color.copied"));
                  String var14 = ColorSetting.rwat.fnz();
                  GLFW.glfwSetClipboardString(var13, GlyphTextUtil.decode(var14));
               }
            }

            if (this.context.xwl != null) {
               return true;
            }

            if (this.context.keybindCaptureTarget != null) {
               if (var3 == 1) {
                  this.context.keybindCaptureTarget.setKeybind(var1 == 256 ? 0 : var1, false);
               }

               this.context.ahm = true;
               return true;
            }

            if (this.context.activeDialog != null) {
               this.context.activeDialog.handleKey(this.context, var1, var4, var3);
               return true;
            }

            if (this.searchQuery.length() <= 0) {
               if (var1 == 256 && var3 == 1) {
                  MinecraftClient.getInstance().setScreen(null);
                  this.interfaceModule.setEnabled(false, true);
               }

               return true;
            }

            if ((var3 == 1 || var3 == 2) && var1 == 259) {
               this.searchQuery = this.searchQuery.substring(0, this.searchQuery.length() - 1);
               if (this.searchQuery.length() <= 1) {
                  this.searchQuery = GlyphTextUtil.encode("");
               }
            } else if (var1 == 256 && var3 == 1) {
               this.searchQuery = GlyphTextUtil.encode("");
            }

            return true;
         }

         if (this.syz.isEnabled()) {
            this.syz.dpf(var1, var2, var3, var4);
            return true;
         }

         if (MinecraftClient.getInstance() != null && getKeyName(var1) != null && var3 == 1 && this.pie()) {
            boolean var5 = MinecraftClient.getInstance().currentScreen == null;
            boolean var6 = false;

            for (BaseModule var10 : PhantomClient.moduleManager.getModules()) {
               if (!var10.isHoldMode() && var10.getKeyCode() != 0 && var10.getKeyCode() == var1 && (var5 || var10 instanceof InterfaceModule || var10 instanceof ConfigsButtonModule)) {
                  var10.setEnabled(!var10.isEnabled(), true);
                  var6 = true;
               }
            }

            return var6;
         }
      } catch (Throwable var12) {
         PhantomClient.fatalError(GlyphTextUtil.encode("UIManager"), GlyphTextUtil.encode("Failed to process typing event for ") + var1 + GlyphTextUtil.encode(", ") + var2 + GlyphTextUtil.encode(", ") + var3 + GlyphTextUtil.encode(", ") + var4, var12);
      }

      return false;
   }

   public boolean pie() {
      return MinecraftClient.getInstance().currentScreen == null
         || MinecraftClient.getInstance().currentScreen instanceof TitleScreen
         || MinecraftClient.getInstance().currentScreen instanceof MultiplayerScreen;
   }

   public static String getKeyName(int var0) {
      if (var0 < 0) {
         return GlyphTextUtil.encode("NONE");
      } else {
         switch (var0) {
            case 0:
               return GlyphTextUtil.encode("ESC");
            case 32:
               return GlyphTextUtil.encode("SPACE");
            case 257:
               return GlyphTextUtil.encode("ENTER");
            case 258:
               return GlyphTextUtil.encode("TAB");
            case 259:
               return GlyphTextUtil.encode("BACKSPACE");
            case 260:
               return GlyphTextUtil.encode("INS");
            case 261:
               return GlyphTextUtil.encode("DEL");
            case 262:
               return GlyphTextUtil.encode("RIGHT");
            case 263:
               return GlyphTextUtil.encode("LEFT");
            case 264:
               return GlyphTextUtil.encode("DOWN");
            case 265:
               return GlyphTextUtil.encode("UP");
            case 266:
               return GlyphTextUtil.encode("PGUP");
            case 267:
               return GlyphTextUtil.encode("PGDOWN");
            case 268:
               return GlyphTextUtil.encode("POS1");
            case 269:
               return GlyphTextUtil.encode("END");
            case 280:
               return GlyphTextUtil.encode("CAPS");
            case 281:
               return GlyphTextUtil.encode("ROLL");
            case 282:
               return GlyphTextUtil.encode("NUM");
            case 283:
               return GlyphTextUtil.encode("PRINT");
            case 284:
               return GlyphTextUtil.encode("PAUSE");
            case 290:
               return GlyphTextUtil.encode("F1");
            case 291:
               return GlyphTextUtil.encode("F2");
            case 292:
               return GlyphTextUtil.encode("F3");
            case 293:
               return GlyphTextUtil.encode("F4");
            case 294:
               return GlyphTextUtil.encode("F5");
            case 295:
               return GlyphTextUtil.encode("F6");
            case 296:
               return GlyphTextUtil.encode("F7");
            case 297:
               return GlyphTextUtil.encode("F8");
            case 298:
               return GlyphTextUtil.encode("F9");
            case 299:
               return GlyphTextUtil.encode("F10");
            case 300:
               return GlyphTextUtil.encode("F11");
            case 301:
               return GlyphTextUtil.encode("F12");
            case 335:
               return GlyphTextUtil.encode("NUMENT");
            case 340:
               return GlyphTextUtil.encode("LSHIFT");
            case 341:
               return GlyphTextUtil.encode("LCTRL");
            case 342:
               return GlyphTextUtil.encode("LALT");
            case 343:
               return GlyphTextUtil.encode("WIN");
            case 344:
               return GlyphTextUtil.encode("RSHIFT");
            case 345:
               return GlyphTextUtil.encode("RCTRL");
            case 346:
               return GlyphTextUtil.encode("ALTGR");
            case 348:
               return GlyphTextUtil.encode("ACTION");
            default:
               if (var0 >= 100000 && var0 < 100008) {
                  return GlyphTextUtil.encode("M") + (var0 - 100000 + 1);
               } else {
                  String var1 = GLFW.glfwGetKeyName(var0, -1);
                  return var1 == null ? null : (GlyphTextUtil.encode("") + var1).toUpperCase();
               }
         }
      }
   }

   public AbstractTexture ucd(Identifier var1) {
      return var1.getNamespace().equals("static") ? new GpuTextureWrapper(Render3DUtil.qpv(1, 1, Integer.parseInt(var1.getPath()))) : null;
   }
}
