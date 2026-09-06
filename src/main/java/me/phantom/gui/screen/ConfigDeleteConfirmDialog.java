package me.phantom.gui.screen;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.util.GlyphTextUtil;

import java.util.function.Consumer;

public class ConfigDeleteConfirmDialog extends AbstractDialog {
   public Consumer<Boolean> nin;

   public ConfigDeleteConfirmDialog(AbstractDialog var1, Consumer<Boolean> var2) {
      super(PhantomClient.translationManager.ry(GlyphTextUtil.encode("config.delete.confirm")), var1);
      this.nin = var2;
   }

   @Override
   public int vgh(int var1, int var2) {
      return 400;
   }

   @Override
   public int zch(int var1, int var2) {
      return 85;
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      byte var6 = 10;
      byte var7 = 12;
      var1.translate(var6, var7);
      int var8 = (var2 - 30) / 2;
      if (var1.drawButton(var4, var5, PhantomClient.translationManager.ry(GlyphTextUtil.encode("config.delete.confirm.no")), var8, 28, true, 0.5F, var1.isHovered(var4, var5, 0, 0, var8, 28) ? 1.0F : 0.0F)
         && var1.doj[0]) {
         this.nin.accept(false);
         this.fiv();
         var1.doj[0] = false;
      }

      var1.translate(var8 + 10, 0);
      if (var1.drawButton(var4, var5, PhantomClient.translationManager.ry(GlyphTextUtil.encode("config.delete.confirm.yes")), var8, 28, true, 0.5F, var1.isHovered(var4, var5, 0, 0, var8, 28) ? 1.0F : 0.0F)
         && var1.doj[0]) {
         this.nin.accept(true);
         this.fiv();
         var1.doj[0] = false;
      }

      var1.translate(-(var8 + 10), 0);
      var1.translate(-var6, -var7);
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      return super.handleKey(var1, var2, var3, var4);
   }

   @Override
   public void na(char var1, int var2) {
   }
}
