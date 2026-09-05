package me.phantom.gui.widget;

import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.BlatantFilterModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.JsonUtils;
import me.phantom.util.math.RotationMathUtil;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.Objects;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

public class ClickGuiCategoryWindow {
   public final ModuleCategory category;
   public final List<BaseModule> sgi;
   public final EasedAnimation expandAnimation = new EasedAnimation(false);
   public final EasedAnimation collapseAnimation = new EasedAnimation(false);
   public int x;
   public int y;
   public int eec;
   public int dar;
   public int ckc;
   public int hbba;
   public float bjy;
   public float nwmm;
   public boolean tza;

   public ClickGuiCategoryWindow(ModuleCategory var1, int var2, int var3) {
      this.sgi = PhantomClient.moduleManager.getModulesByCategory(var1);
      this.category = var1;
      this.x = var2;
      this.y = var3;
   }

   public List<BaseModule> jqr() {
      return this.sgi.stream().filter(var0 -> var0.playStyle.getLevel() >= BlatantFilterModule.getMinimumStyle().getLevel()).toList();
   }

   public void yzf(GuiRenderContext var1) {
      int var2 = 0;
      short var3 = 230;
      boolean var4 = this.tza;
      boolean var5 = false;
      if (GlyphTextUtil.trim(PhantomClient.uiManager.searchQuery).length() > 0 && this.znz()) {
         var4 = true;
         var5 = true;
      }

      if (!var4 && !this.collapseAnimation.sdx()) {
         var2 = 34;
      } else {
         var2 = 34;
         BaseModule var6 = (BaseModule)null;

         for (BaseModule var8 : this.jqr()) {
            if (var8.isExpanded() || var8.collapseAnimation.sdx()) {
               var6 = var8;
            }

            var2 += 28;
         }

         if (var6 != null) {
            float var11 = var6.isExpanded() ? var6.expandAnimation.hee() : 1.0F - var6.collapseAnimation.hee();
            var2 = (int)((1.0F - var11) * var2 + (34 + var6.getHeight()) * var11);
         }
      }

      var2++;
      this.eec = var3;
      this.dar = (int)(28.0F + (var2 - 28) * (var5 ? 1.0F : (this.tza ? this.expandAnimation.hee() : 1.0F - this.collapseAnimation.hee())));
   }

   public boolean znz() {
      for (BaseModule var2 : this.jqr()) {
         if (GlyphTextUtil.lowercase(var2.getName()).contains(GlyphTextUtil.lowercase(GlyphTextUtil.trim(PhantomClient.uiManager.searchQuery)))) {
            return true;
         }
      }

      return false;
   }

   public void update() {
      this.bjy = this.nwmm;
      if (Math.abs(this.nwmm) > 0.03) {
         this.nwmm = (float)(this.nwmm * 0.46);
      } else {
         this.nwmm = 0.0F;
      }
   }

   public void sfg(boolean var1) {
      if (this.tza != var1) {
         this.tza = var1;
         if (this.tza) {
            this.expandAnimation.qou(150);
         } else {
            this.collapseAnimation.qou(150);
         }

         for (BaseModule var3 : this.sgi) {
            var3.setExpanded(false);
         }
      }
   }

   public void mam(GuiRenderContext var1, int var2, int var3, boolean[] var4, boolean[] var5) {
      if (var1.isHovered(var2, var3, this.x + this.eec - 27, this.y, 27, 27)) {
         var1.setTooltip(PhantomClient.translationManager.ry(this.tza ? GlyphTextUtil.encode("window.close") : GlyphTextUtil.encode("window.open")));
         if (var1.doj[0]) {
            var1.doj[0] = false;
            this.sfg(!this.tza);
         }
      }

      byte var6 = 20;
      int var7 = Math.max(-var6, Math.min(var1.framebufferWidth + var6, var2));
      int var8 = Math.max(-var6, Math.min(var1.framebufferHeight + var6 - this.dar / 2, var3));
      if (var1.isClicked(var2, var3, this.x, this.y, this.eec, 27, 0)) {
         var1.kcr = this;
         this.ckc = var7;
         this.hbba = var8;
      }

      if (var1.isClicked(var2, var3, this.x, this.y, this.eec, 27, 1)) {
         this.sfg(!this.tza);
      }

      if (Objects.equals(var1.kcr, this)) {
         int var9 = var7 - this.ckc;
         int var10 = var8 - this.hbba;
         if ((Boolean)PhantomClient.uiManager.interfaceModule.twz.getValue()) {
            this.nwmm = (float)(this.nwmm + var9 * 0.42F * (1.0 / Math.sqrt(Math.abs(this.nwmm) + 1.0F)));
         }

         this.x = Math.max(0, this.x + var9);
         this.y = Math.max(0, this.y + var10);
         this.ckc = var7;
         this.hbba = var8;
         PhantomClient.configManager.fl();
      }

      this.x = Math.max(0, this.x);
      this.y = Math.max(0, this.y);
      float var22 = RotationMathUtil.lerp(this.bjy, this.nwmm, var1.tickDelta);
      int var23 = 0;
      int var11 = 0;
      boolean var12 = this.tza;
      boolean var13 = false;
      if (GlyphTextUtil.trim(PhantomClient.uiManager.searchQuery).length() > 0) {
         if (!this.znz()) {
            return;
         }

         var12 = true;
         var13 = true;
      }

      var1.translate(this.x, this.y);
      if (Math.abs(var22) > 0.005) {
         var23 = var1.mouseX;
         var11 = var1.mouseY;
         NanoVG.nvgSave(var1.nvg);
         NanoVG.nvgTranslate(var1.nvg, this.eec / 2, 0.0F);
         NanoVG.nvgRotate(var1.nvg, (float)Math.toRadians(var22 * 0.45F));
         NanoVG.nvgTranslate(var1.nvg, -this.eec / 2, 0.0F);
      }

      var1.drawTooltipPanel(this.category.getName(), this.category.zs(), this.eec, this.dar, var13 ? 1.0F : (var12 ? this.expandAnimation.hee() : 1.0F - this.collapseAnimation.hee()), true);
      int var14 = 0;
      byte var15 = 0;
      BaseModule var16 = (BaseModule)null;
      int var17 = this.eec - 8;

      for (BaseModule var19 : this.jqr()) {
         if (var19.isExpanded() || var19.collapseAnimation.sdx()) {
            var16 = var19;
         }
      }

      boolean var29 = var16 != null || this.expandAnimation.sdx() || this.collapseAnimation.sdx();
      if (var29) {
         NanoVG.nvgScissor(var1.nvg, var15, var14 + 28, this.eec, this.dar - 28);
      }

      if (var12 || this.collapseAnimation.sdx()) {
         var15 = 4;
         var14 += 34;
         var1.translate(4, 34);
         if (var16 == null) {
            for (BaseModule var20 : this.jqr()) {
               var14 += var20.renderModuleEntry(var1, var17, var2, var3, var4, var5);
               var1.translate(0, 3);
               var14 += 3;
            }
         } else {
            this.tza = true;
            float var31 = var16.isExpanded() ? var16.expandAnimation.hee() : 1.0F - var16.collapseAnimation.hee();
            if (var31 < 1.0F) {
               NanoVG.nvgGlobalAlpha(var1.nvg, 1.0F - var31);

               for (BaseModule var21 : this.jqr()) {
                  var14 += var21.renderModuleEntry(var1, var17, -1, -1, var1.mzx, var1.mzx);
                  var1.translate(0, 3);
                  var14 += 3;
               }

               NanoVG.nvgGlobalAlpha(var1.nvg, 1.0F);
            }

            NanoVG.nvgGlobalAlpha(var1.nvg, var31);
            var1.translate(0, -var14 + 34);
            int var26 = 34;
            var26 += var16.renderModuleEntry(var1, var17, var2, var3, var4, var5);
            var26 += var16.renderSettings(var1, var17, var2, var3, var4, var5);
            var1.translate(0, 3);
            var14 = var26 + 3;
            NanoVG.nvgGlobalAlpha(var1.nvg, 1.0F);
            NanoVG.nvgResetScissor(var1.nvg);
         }
      }

      if (var29) {
         NanoVG.nvgResetScissor(var1.nvg);
      }

      if (Math.abs(var22) > 0.01) {
         NanoVG.nvgRestore(var1.nvg);
         NanoVG.nvgTranslate(var1.nvg, var1.mouseX - var23, var1.mouseY - var11);
      }

      var1.translate(-(this.x + var15), -(this.y + var14));
   }

   public boolean lzu(GuiRenderContext var1, int var2, int var3) {
      return var1.isHovered(var2, var3, this.x, this.y, this.eec, this.dar);
   }

   public void rlq(GuiRenderContext var1) {
      byte var2 = 20;
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
      GL11.glBegin(7);
      GL11.glVertex2d(this.x - var2, this.y - var2);
      GL11.glVertex2d(this.x - var2, this.y + this.dar + var2);
      GL11.glVertex2d(this.x + this.eec + var2, this.y + this.dar + var2);
      GL11.glVertex2d(this.x + this.eec + var2, this.y - var2);
      GL11.glEnd();
   }

   public ModuleCategory getCategory() {
      return this.category;
   }

   public JsonObject mqq() {
      JsonObject var1 = new JsonObject();
      var1.addProperty(GlyphTextUtil.encode("x"), this.x);
      var1.addProperty(GlyphTextUtil.encode("y"), this.y);
      var1.addProperty(GlyphTextUtil.encode("isOpen"), this.tza);
      return var1;
   }

   public void vpe(JsonObject var1) {
      this.x = var1.get(GlyphTextUtil.encode("x")).getAsInt();
      this.y = var1.get(GlyphTextUtil.encode("y")).getAsInt();
      this.tza = JsonUtils.uzc(var1.get(GlyphTextUtil.encode("isOpen")));
   }
}
