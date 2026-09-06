package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.ItemCategoryPickerDialog;
import me.phantom.module.base.BaseModule;
import me.phantom.util.inventory.ItemScoreCategory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ItemCategorySetSetting extends AbstractSetting<Set<ItemScoreCategory>> {
   public ItemCategorySetSetting(BaseModule var1, String var2) {
      super(var1, var2, new HashSet<>(Arrays.asList(ItemScoreCategory.qfo())));
      this.setValue(new HashSet((Collection)this.getDefaultValue()));
   }

   @Override
   public int getHeight() {
      return 26;
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.getValue().clear();
      JsonArray var2 = (JsonArray)var1;

      for (int var3 = 0; var3 < var2.size(); var3++) {
         ItemScoreCategory var4;
         try {
            var4 = ItemScoreCategory.ol(var2.get(var3).getAsString());
         } catch (Throwable var6) {
            continue;
         }

         this.getValue().add(var4);
      }
   }

   @Override
   public JsonElement toJson() {
      JsonArray var1 = new JsonArray();
      this.getValue().forEach(var1x -> var1.add(var1x.vr()));
      return var1;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, this.getHeight(), this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         var1.activeDialog = new ItemCategoryPickerDialog(this);
         var1.doj[0] = false;
      }
   }
}
