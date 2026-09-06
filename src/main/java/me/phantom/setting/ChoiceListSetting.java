package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.XRayBlockListPanel;
import me.phantom.module.base.BaseModule;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;

public class ChoiceListSetting extends AbstractSetting<List<String>> {
   public ChoiceListSetting(BaseModule var1, String var2, List<String> var3) {
      super(var1, var2, var3);
      this.setValue(new ArrayList(var3));
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
         String var4 = var2.get(var3).getAsString();
         if (((List)this.getDefaultValue()).contains(var4) && !this.getValue().contains(var4)) {
            this.getValue().add(var4);
         }
      }
   }

   @Override
   public JsonElement toJson() {
      JsonArray var1 = new JsonArray();
      this.getValue().forEach(var1::add);
      return var1;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, this.getHeight(), this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         var1.activeDialog = new XRayBlockListPanel(this);
         var1.doj[0] = false;
      }
   }
}
