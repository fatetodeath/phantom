package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.InventorySortEditorScreen;
import me.phantom.module.base.BaseModule;
import me.phantom.util.inventory.ItemScoreCategory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class InventorySortSetting extends AbstractSetting<ItemScoreCategory[]> {
   public InventorySortSetting(BaseModule var1, String var2, ItemScoreCategory[] var3) {
      super(var1, var2, var3);
   }

   @Override
   public int getHeight() {
      return 26;
   }

   @Override
   public void fromJson(JsonElement var1) {
      JsonArray var2 = (JsonArray)var1;
      ItemScoreCategory[] var3 = (ItemScoreCategory[])this.getValue();
      ItemScoreCategory[] var4 = ItemScoreCategory.qfo();

      for (int var5 = 0; var5 < var2.size(); var5++) {
         int var6 = var2.get(var5).getAsInt();
         var3[var5] = var6 > 0 ? var4[var6] : null;
      }
   }

   @Override
   public JsonElement toJson() {
      JsonArray var1 = new JsonArray();
      ItemScoreCategory[] var2 = (ItemScoreCategory[])this.getValue();

      for (int var3 = 0; var3 < var2.length; var3++) {
         var1.add(var2[var3] == null ? -1 : var2[var3].getOrderIndex());
      }

      return var1;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, this.getHeight(), this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         var1.activeDialog = new InventorySortEditorScreen(this);
         var1.doj[0] = false;
      }
   }
}
