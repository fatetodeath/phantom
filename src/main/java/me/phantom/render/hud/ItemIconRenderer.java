package me.phantom.render.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public class ItemIconRenderer {
   private final GuiRenderContext uvqa;
   private final Map<String, Integer> slj = new HashMap<>();

   public ItemIconRenderer(GuiRenderContext var1) throws Throwable {
      this.uvqa = var1;
   }

   public void tzk(ItemStack var1, float var2, float var3, float var4, float var5, float var6) {
      if (var1 != null && !var1.isEmpty()) {
         String var7 = var1.getItem().getTranslationKey();
         Integer var8 = this.slj.get(var7);
         if (var8 == null) {
            try {
               this.slj.put(var7, var8 = NanoVG.nvgCreateImageMem(this.uvqa.nvg, 0, this.uvqa.loadResource(GlyphTextUtil.encode("items/") + GlyphTextUtil.encodeChars(var7) + GlyphTextUtil.encode(".png"))));
            } catch (Throwable var13) {
               this.slj.put(var7, null);
            }
         }

         if (var8 != null) {
            NVGPaint var9 = NanoVG.nvgImagePattern(this.uvqa.nvg, var2 + 1.0F, var3 + 1.0F, var4 - 2.0F, var5 - 2.0F, 0.0F, var8, var6, GuiRenderContext.paintScratch);
            if (var6 < 0.0F) {
               var9.innerColor(GuiRenderContext.rgba(0, 0, 0, (int)(255.0F * -var6), GuiRenderContext.colorScratch));
            }

            NanoVG.nvgBeginPath(this.uvqa.nvg);
            NanoVG.nvgRect(this.uvqa.nvg, var2, var3, var4, var5);
            NanoVG.nvgFillPaint(this.uvqa.nvg, var9);
            NanoVG.nvgFill(this.uvqa.nvg);
            if (var1.getItem() == Items.SPLASH_POTION || var1.getItem() == Items.POTION || var1.getItem() == Items.LINGERING_POTION) {
               boolean var10 = false;

               for (StatusEffectInstance var12 : GameInteractionUtil.tnw(var1)) {
                  if (var12.getEffectType() == StatusEffects.INSTANT_HEALTH
                     || var12.getEffectType() == StatusEffects.REGENERATION
                     || var12.getEffectType() == StatusEffects.STRENGTH
                     || var12.getEffectType() == StatusEffects.ABSORPTION
                     || var12.getEffectType() == StatusEffects.HEALTH_BOOST
                     || var12.getEffectType() == StatusEffects.FIRE_RESISTANCE
                     || var12.getEffectType() == StatusEffects.LUCK
                     || var12.getEffectType() == StatusEffects.JUMP_BOOST
                     || var12.getEffectType() == StatusEffects.SPEED) {
                     var10 = true;
                  }
               }

               ByteBuffer var14 = GuiRenderContext.iconBuffer(var10 ? '\ue4af' : '\ue4b0');
               NanoVG.nvgFontSize(this.uvqa.nvg, 16.0F);
               GlyphTextUtil.setTextAlign(this.uvqa.nvg, 9);
               NanoVG.nvgFontFace(this.uvqa.nvg, InterfaceModule.fontRegular);
               NanoVG.nvgFillColor(this.uvqa.nvg, var10 ? GuiRenderContext.rgba(20, 220, 20, 220, GuiRenderContext.colorScratch) : GuiRenderContext.rgba(220, 20, 20, 220, GuiRenderContext.colorScratch));
               GlyphTextUtil.drawTextBuffer(this.uvqa.nvg, var2 - 4.0F, var3, var14);
            }
         }
      }
   }
}
