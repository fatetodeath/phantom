package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.BlockEspScreen;
import me.phantom.module.base.BaseModule;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BlockColorMapSetting extends AbstractSetting<Map<String, float[]>> {
   public BlockColorMapSetting(BaseModule var1, String var2, Map<String, float[]> var3) {
      super(var1, var2, var3);
      LinkedHashMap var4 = new LinkedHashMap();

      for (Entry var6 : var3.entrySet()) {
         var4.put((String)var6.getKey(), (float[])((float[])var6.getValue()).clone());
      }

      this.setValue(var4);
   }

   @Override
   public int getHeight() {
      return 26;
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.getValue().clear();
      this.getValue().putAll((Map)this.getDefaultValue());
      JsonArray var2 = (JsonArray)var1;

      for (int var3 = 0; var3 < var2.size(); var3++) {
         JsonArray var4 = var2.get(var3).getAsJsonArray();
         String var5 = var4.get(0).getAsString();
         if (this.getValue().containsKey(var5)) {
            this.getValue().put(var5, new float[]{var4.get(1).getAsFloat(), var4.get(2).getAsFloat(), var4.get(2).getAsFloat(), var4.get(3).getAsFloat()});
         }
      }
   }

   @Override
   public JsonElement toJson() {
      JsonArray var1 = new JsonArray();
      this.getValue().forEach((var1x, var2) -> {
         JsonArray var3 = new JsonArray();
         var3.add(var1x);
         var3.add(var2[0]);
         var3.add(var2[1]);
         var3.add(var2[2]);
         var3.add(var2[3]);
         var1.add(var3);
      });
      return var1;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, this.getHeight(), this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         var1.activeDialog = new BlockEspScreen(this);
         var1.doj[0] = false;
      }
   }
}
