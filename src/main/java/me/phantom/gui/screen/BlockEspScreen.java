package me.phantom.gui.screen;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.setting.BlockColorMapSetting;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.lwjgl.nanovg.NanoVG;

public class BlockEspScreen extends AbstractDialog {
   private final BlockColorMapSetting oxh;
   private float scrollOffset;
   private String wza;
   public int hjp;

   public BlockEspScreen(BlockColorMapSetting var1) {
      super(GlyphTextUtil.encode("BlockESP"), null);
      this.oxh = var1;
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
      this.scrollOffset -= var2 * 30.0F;
   }

   @Override
   public void fiv() {
      if (this.wza != null) {
         this.wza = null;
      } else {
         super.fiv();
      }
   }

   @Override
   public void yww() {
   }

   @Override
   public int vgh(int var1, int var2) {
      return this.wza != null ? 300 : 500;
   }

   @Override
   public int zch(int var1, int var2) {
      return this.wza != null ? 300 : 700;
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      if (this.wza != null) {
         float[] var16 = (float[])((Map)this.oxh.getValue()).get(this.wza);
         String var17 = I18n.get(GlyphTextUtil.encode("block.") + this.wza.replace(GlyphTextUtil.encode(":"), GlyphTextUtil.encode(".")), new Object[0]);
         var1.drawTextAuto(var2 / 2, 30.0F, var17, 32.0F, false, 10, 0.0F, 1.0F);
         var1.translate(var2 / 2 - 90, var3 / 4);
         this.hjp = var1.krq(var1, var4 - var1.mouseX, var5 - var1.mouseY, 180, 180, this.hjp, var16);
         if (!var1.xfy[0]) {
            this.hjp = 0;
         }

         var1.translate(-(var2 / 2 - 90), -var3 / 4);
      } else {
         byte var6 = 40;
         int var7 = var2 - 10;
         int var8 = ((Map)this.oxh.getDefaultValue()).size() * (var6 + 5) - 5;
         this.scrollOffset = Math.max(0.0F, Math.min((float)(var8 - var3 + 5), this.scrollOffset));
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, var2 - 8, 0.0F, 5.0F, var3, 2.0F);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(30, 30, 30, 200, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var1.nvg);
         float var9 = 1.0F * var3 / var8 * var3;
         if (var3 < var8) {
            NanoVG.nvgBeginPath(var1.nvg);
            NanoVG.nvgRoundedRect(var1.nvg, var2 - 8, this.scrollOffset / (var8 - var3 + 10) * (var3 - var9), 5.0F, var9, 2.0F);
            NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, 0.65F, 230, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var1.nvg);
         }

         NanoVG.nvgScissor(var1.nvg, 0.0F, 0.0F, var2 - 10, var3);
         NanoVG.nvgTranslate(var1.nvg, 0.0F, -this.scrollOffset);
         var5 = (int)(var5 + this.scrollOffset);
         float var10 = var5 - var1.mouseY - this.scrollOffset;
         if (var10 >= var3 || var10 <= 0.0F) {
            var5 = -1000;
         }

         int var11 = 0;

         for (Entry<String, float[]> var13 : this.oxh.getValue().entrySet()) {
            int var14 = var11++ * (var6 + 5);
            if (var14 > this.scrollOffset - var3 && var14 <= this.scrollOffset + var3) {
               NanoVG.nvgTranslate(var1.nvg, 0.0F, var14);
               this.krg(var1, (String)var13.getKey(), (float[])var13.getValue(), var4, var5 - var14, var7, var6);
               NanoVG.nvgTranslate(var1.nvg, 0.0F, -var14);
            }
         }

         NanoVG.nvgTranslate(var1.nvg, 0.0F, this.scrollOffset);
         NanoVG.nvgResetScissor(var1.nvg);
      }
   }

   public void krg(GuiRenderContext var1, String var2, float[] var3, int var4, int var5, int var6, int var7) {
      String var8 = I18n.get(GlyphTextUtil.decode(GlyphTextUtil.encode("block.") + var2.replace(GlyphTextUtil.encode(":"), GlyphTextUtil.encode("."))), new Object[0]);
      boolean var9 = var1.isHovered(var4, var5, 0, 0, var6, var7);
      boolean var10 = ((float[])((Map)this.oxh.getValue()).get(var2))[3] > 0.0F;
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 1.0F, var6, var7 - 2, 4.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, var10 ? 0.6F : 0.2F, var9 ? 220 : 120, GuiRenderContext.colorScratch2));
      NanoVG.nvgFill(var1.nvg);
      var1.drawButton(var4, var5, GlyphTextUtil.encode(""), var7, var7, true, ((Map)this.oxh.getValue()).containsKey(var2) ? 1.0F : 0.0F, var9 ? 1.0F : 0.0F);
      var1.drawTextAuto(48.0F, 10.0F, var8, 22.0F, false, 9, 0.0F, 1.0F);

      try {
         PhantomClient.uiManager
            .sdd()
            .tzk(
               new ItemStack(
                  (ItemLike) BuiltInRegistries.BLOCK
                     .getValue(Identifier.withDefaultNamespace(GlyphTextUtil.decode(var2.startsWith(GlyphTextUtil.encode("minecraft:")) ? var2.substring(GlyphTextUtil.encode("minecraft:").length()) : var2)))
               ),
               4.0F,
               4.0F,
               32.0F,
               32.0F,
               1.0F
            );
      } catch (Throwable var15) {
         PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("BlockESPScreen"), GlyphTextUtil.encode("Failed to render item ") + var2, var15);
      }

      boolean var11 = var1.isHovered(var4, var5, var6 - var7 + 6, 4, var7 - 10, var7 - 10);
      int var12 = var11 ? 40 : 20;
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var6 - var7 + 6, 4.0F, var7 - 10, var7 - 10, 4.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var12, var12, var12, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      Color var13 = Color.getHSBColor(var3[0], var3[1], var3[2]);
      byte var14 = 2;
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, var6 - var7 + 6 + var14, 4 + var14, var7 - 10 - var14 * 2, var7 - 10 - var14 * 2, 4.0F);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(var13.getRed(), var13.getGreen(), var13.getBlue(), var10 ? 255 : 120, GuiRenderContext.colorScratch));
      NanoVG.nvgFill(var1.nvg);
      if (var11) {
         var1.setTooltip(PhantomClient.translationManager.ry(GlyphTextUtil.encode("blockesp.color.tooltip")));
         if (var1.doj[0]) {
            this.wza = var2;
            var1.doj[0] = false;
         }
      }

      if (var9 && var1.doj[0]) {
         ((float[])((Map)this.oxh.getValue()).get(var2))[3] = var10 ? 0.0F : 1.0F;
         if (!var10 && PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("BlockESP")).isEnabled() && Minecraft.getInstance().player != null) {
            Minecraft.getInstance().levelRenderer.allChanged();
         }

         var1.doj[0] = false;
      }
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      return super.handleKey(var1, var2, var3, var4);
   }

   @Override
   public void na(char var1, int var2) {
   }
}
