package me.phantom.gui.hud;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.ItemScoreCategory;

import com.google.gson.JsonObject;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import org.lwjgl.nanovg.NanoVG;

public class InventoryItemsHudElement extends HudElementBase {
   public InventoryItemsHudElement(JsonObject var1) {
      super(var1);
   }

   private Map<Item, Integer> lif() {
      HashMap<Item, Integer> var1 = new HashMap<>();
      DefaultedList var2 = MinecraftClient.getInstance().player.getInventory().getMainStacks();

      for (int var3 = 0; var3 < var2.size(); var3++) {
         ItemStack var4 = (ItemStack)var2.get(var3);
         if (var4 != null && var4.getItem() != null && var4.getItem() != Items.AIR) {
            ItemScoreCategory var5 = InventoryModuleBase.getItemCategory(var4);
            if (var5 != ItemScoreCategory.ryh) {
               Item var6 = var5.stack.getItem();
               var1.put(var6, var1.getOrDefault(var6, 0) + var4.getCount());
            }
         }
      }

      return var1;
   }

   @Override
   public void jjc(GuiRenderContext var1) {
      Map<Item, Integer> var2 = this.lif();
      this.net(var1);
      if (var2 != null && !var2.isEmpty()) {
         long var3 = var1.nvg;
         float var5 = 5.0F;

         for (Entry<Item, Integer> var7 : var2.entrySet()) {
            PhantomClient.uiManager.sdd().tzk(new ItemStack((ItemConvertible)var7.getKey()), var5, 5.0F, 12.0F, 12.0F, 1.0F);
            NanoVG.nvgFontFace(var3, InterfaceModule.fontBold);
            NanoVG.nvgFontSize(var3, 10.0F);
            GlyphTextUtil.setTextAlign(var3, 36);
            String var8 = GlyphTextUtil.encode("") + Math.min(999, (Integer)var7.getValue());
            float var9 = var5 + 22.0F - 5.0F;
            float var10 = 22.0F;
            NanoVG.nvgFillColor(var3, GuiRenderContext.rgba(0, 0, 0, 255, GuiRenderContext.colorScratch));

            for (int var11 = -1; var11 <= 1; var11++) {
               for (int var12 = -1; var12 <= 1; var12++) {
                  GlyphTextUtil.drawText(var3, var9 + var11, var10 + var12, var8);
               }
            }

            NanoVG.nvgFillColor(var3, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
            GlyphTextUtil.drawText(var3, var9, var10, var8);
            var5 += 22.0F;
         }
      } else {
         NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(255, 255, 255, 255, GuiRenderContext.colorScratch));
         GlyphTextUtil.setTextAlign(var1.nvg, 9);
         NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontRegular);
         NanoVG.nvgFontSize(var1.nvg, 12.0F);
         GlyphTextUtil.drawText(var1.nvg, 5.0F, 6.0F, GlyphTextUtil.encode("No data"));
      }
   }

   @Override
   public float icg(GuiRenderContext var1) {
      Map<Item, Integer> var2 = this.lif();
      if (var2 != null && !var2.isEmpty()) {
         return var2.size() * 22.0F;
      } else {
         NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontRegular);
         NanoVG.nvgFontSize(var1.nvg, 12.0F);
         return GlyphTextUtil.measureText(var1.nvg, 0.0F, 0.0F, GlyphTextUtil.encode("No data"), (FloatBuffer)null) + 10.0F;
      }
   }

   @Override
   public float yxe(GuiRenderContext var1) {
      return 22.0F;
   }

   @Override
   public void fc(GuiRenderContext var1, float var2, float var3) {
      long var4 = var1.nvg;
      int var6 = 128092;
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
