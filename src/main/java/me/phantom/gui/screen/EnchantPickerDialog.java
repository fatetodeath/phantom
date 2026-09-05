package me.phantom.gui.screen;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.setting.EnchantListSetting;
import me.phantom.util.GlyphTextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import org.lwjgl.nanovg.NanoVG;

public class EnchantPickerDialog extends AbstractDialog {
   public final EnchantListSetting adp;
   private float scrollOffset;
   private final List<ItemStack> xcv;

   public EnchantPickerDialog(EnchantListSetting var1, List<ItemStack> var2) {
      super(GlyphTextUtil.encode("Allowed enchantments"), null);
      this.adp = var1;
      this.xcv = var2;
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
      this.scrollOffset -= var2 * 30.0F;
   }

   @Override
   public void yww() {
   }

   @Override
   public int vgh(int var1, int var2) {
      return 500;
   }

   @Override
   public int zch(int var1, int var2) {
      return 700;
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      if (MinecraftClient.getInstance().world != null) {
         ArrayList<Enchantment> var6 = new ArrayList<>(20);
         ArrayList<Enchantment> var7 = new ArrayList<>();
         MinecraftClient.getInstance().world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).forEach(var7::add);
         var7.sort((var0, var1x) -> var0.description().getString().compareTo(var1x.description().getString()));

         for (Enchantment var9 : var7) {
            for (ItemStack var11 : this.xcv) {
               if (var9.isAcceptableItem(var11)) {
                  var6.add(var9);
                  break;
               }
            }
         }

         byte var18 = 40;
         int var19 = var2 - 10;
         int var20 = var6.size() * (var18 + 5) - 5;
         float var21 = 1.0F * var3 / var20 * var3;
         this.scrollOffset = Math.max(0.0F, Math.min((float)(var20 - var3 + 5), this.scrollOffset));
         NanoVG.nvgBeginPath(var1.nvg);
         NanoVG.nvgRoundedRect(var1.nvg, var2 - 8, 0.0F, 5.0F, var3, 2.0F);
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(30, 30, 30, 200, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var1.nvg);
         if (var3 < var20) {
            NanoVG.nvgBeginPath(var1.nvg);
            NanoVG.nvgRoundedRect(var1.nvg, var2 - 8, this.scrollOffset / (var20 - var3 + 10) * (var3 - var21), 5.0F, var21, 2.0F);
            NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, 0.65F, 230, GuiRenderContext.colorScratch));
            NanoVG.nvgFill(var1.nvg);
         }

         NanoVG.nvgScissor(var1.nvg, 0.0F, 0.0F, var2 - 10, var3);
         NanoVG.nvgTranslate(var1.nvg, 0.0F, -this.scrollOffset);
         var5 = (int)(var5 + this.scrollOffset);
         float var12 = var5 - var1.mouseY - this.scrollOffset;
         if (var12 >= var3 || var12 <= 0.0F) {
            var5 = -1000;
         }

         int var13 = 0;

         for (Enchantment var15 : var6) {
            int var16 = var13++ * (var18 + 5);
            if (var16 > this.scrollOffset - var3 && var16 <= this.scrollOffset + var3) {
               NanoVG.nvgTranslate(var1.nvg, 0.0F, var16);
               this.tvrc(var1, var15, var4, var5 - var16, var19, var18);
               NanoVG.nvgTranslate(var1.nvg, 0.0F, -var16);
            }
         }

         NanoVG.nvgTranslate(var1.nvg, 0.0F, this.scrollOffset);
         NanoVG.nvgResetScissor(var1.nvg);
      }
   }

   private void tvrc(GuiRenderContext var1, Enchantment var2, int var3, int var4, int var5, int var6) {
      String var7 = var2.description().getString();
      boolean var8 = var1.isHovered(var3, var4, 0, 0, var5, var6);
      boolean var9 = !((Set)this.adp.getValue()).contains(var7);
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 0.0F, 1.0F, var5, var6 - 2, 4.0F);
      NanoVG.nvgFillColor(var1.nvg, var1.shadeColor(var1.accentColor, var9 ? 0.6F : 0.2F, var8 ? 220 : 120, GuiRenderContext.colorScratch2));
      NanoVG.nvgFill(var1.nvg);
      var1.drawTextAuto(var5 / 2 - 2, 10.0F, var7, 22.0F, false, 10, 0.0F, 1.0F);
      if (var8 && var1.doj[0]) {
         if (((Set)this.adp.getValue()).contains(var7)) {
            ((Set)this.adp.getValue()).remove(var7);
         } else {
            ((Set)this.adp.getValue()).add(var7);
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
