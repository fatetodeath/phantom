package me.phantom.gui.screen;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.EasedAnimation;

import org.lwjgl.nanovg.NanoVG;

public abstract class AbstractDialog {
   public final AbstractDialog rmh;
   public final String title;
   public final EasedAnimation env = new EasedAnimation(false);
   public final EasedAnimation ix = new EasedAnimation(false);
   public boolean bef;

   public abstract void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5);

   public abstract void puu(GuiRenderContext var1, float var2);

   public abstract void na(char var1, int var2);

   public abstract int vgh(int var1, int var2);

   public abstract int zch(int var1, int var2);

   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      if (var2 == 256 && var4 == 1) {
         this.fiv();
         return true;
      } else {
         return false;
      }
   }

   public void yww() {
   }

   public AbstractDialog(String var1, AbstractDialog var2) {
      this.title = var1;
      this.rmh = var2;
      this.env.qou(200);
   }

   public final void vvb(GuiRenderContext var1, int var2, int var3) {
      if (this.bef && !this.ix.sdx()) {
         this.bef = false;
         PhantomClient.uiManager.getContext().activeDialog = this.rmh;
         if (this.rmh != null) {
            this.rmh.yww();
         }
      } else {
         float var4 = this.env.sdx() ? this.env.hee() : (this.ix.sdx() ? 1.0F - this.ix.hee() : 1.0F);
         if (var4 != 1.0F) {
            NanoVG.nvgGlobalAlpha(var1.nvg, var4);
            NanoVG.nvgSave(var1.nvg);
            NanoVG.nvgTranslate(var1.nvg, var1.framebufferWidth / 2, var1.framebufferHeight / 2);
            NanoVG.nvgScale(var1.nvg, (float)(Math.tan(var4) / Math.tan(1.0)), (float)(Math.tan(var4) / Math.tan(1.0)));
            NanoVG.nvgTranslate(var1.nvg, -var1.framebufferWidth / 2, -var1.framebufferHeight / 2);
         }

         int var5 = this.vgh(var1.framebufferWidth, var1.framebufferHeight);
         int var6 = this.zch(var1.framebufferWidth, var1.framebufferHeight);
         int var7 = var1.framebufferWidth / 2 - var5 / 2;
         int var8 = var1.framebufferHeight / 2 - var6 / 2;

         try {
            var1.translate(var7, var8);
            var1.drawTooltipPanel(this.title, 0, var5, var6, 1.0F, false);
            var1.translate(0, 30);
            this.lrk(var1, var5, var6 - 30, var2, var3);
         } finally {
            var1.translate(-var7, -var8 - 30);
         }

         if (var4 != 1.0F) {
            NanoVG.nvgRestore(var1.nvg);
         }

         this.eau(var1, var2, var3);
      }
   }

   public void eau(GuiRenderContext var1, int var2, int var3) {
   }

   public void fiv() {
      if (!this.bef) {
         this.ix.qou(200);
         this.bef = true;
      }
   }
}
