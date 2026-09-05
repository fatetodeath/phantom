package me.phantom.gui.hud;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.HudModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import org.lwjgl.nanovg.NanoVG;

public class ArmorVerticalHudElement extends HudElementBase {
   public ArmorVerticalHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public void jjc(GuiRenderContext var1) {
      this.net(var1);

      try {
         EquipmentSlot[] var2 = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
         float var3 = 2.5F;

         for (int var4 = 0; var4 < var2.length; var4++) {
            HudModule.bvg(var1, var4, 2.5F, var3, MinecraftClient.getInstance().player.getEquippedStack(var2[var4]), true);
            var3 += 22.0F;
         }
      } catch (Throwable var5) {
         if (PhantomClient.traceEnabled) {
            PhantomClient.fatalError(GlyphTextUtil.encode("Armor"), GlyphTextUtil.encode("Rendering armor"), var5);
         }
      }
   }

   @Override
   public float icg(GuiRenderContext var1) {
      return 27.0F;
   }

   @Override
   public float yxe(GuiRenderContext var1) {
      return 95.5F;
   }

   @Override
   public void fc(GuiRenderContext var1, float var2, float var3) {
      long var4 = var1.nvg;
      int var6 = 128100;
      NanoVG.nvgFontFace(var4, GlyphTextUtil.encode("icons"));
      NanoVG.nvgFontSize(var4, 12.0F);
      GlyphTextUtil.setTextAlign(var4, 18);
      NanoVG.nvgFillColor(var4, GuiRenderContext.rgba(100, 100, 100, 220, GuiRenderContext.colorScratch));
      NanoVG.nvgFontBlur(var4, 2.0F);
      GlyphTextUtil.drawTextBuffer(var4, var2, var3, GuiRenderContext.iconBuffer(var6));
      NanoVG.nvgFontBlur(var4, 0.0F);
      NanoVG.nvgFillColor(var4, GuiRenderContext.rgba(200, 200, 200, 220, GuiRenderContext.colorScratch));
      GlyphTextUtil.drawTextBuffer(var4, var2, var3, GuiRenderContext.iconBuffer(var6));
   }
}
