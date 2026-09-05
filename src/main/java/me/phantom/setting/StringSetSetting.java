package me.phantom.setting;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.StringSetEditorDialog;
import me.phantom.module.base.BaseModule;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.LinkedHashSet;
import java.util.Set;

public class StringSetSetting extends AbstractSetting<Set<String>> {
   public StringSetSetting(BaseModule var1, String var2) {
      super(var1, var2, new LinkedHashSet());
   }

   public void xmh(Set<String> var1) {
      throw new IllegalArgumentException();
   }

   @Override
   public int getHeight() {
      return 26;
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.getValue().clear();
      ((JsonArray)var1).forEach(var1x -> {
         if (var1x.isJsonPrimitive()) {
            this.getValue().add(var1x.getAsString().toLowerCase());
         }
      });
   }

   @Override
   public JsonElement toJson() {
      JsonArray var1 = new JsonArray();
      this.getValue().forEach(var1x -> var1.add(var1x.toLowerCase()));
      return var1;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, this.getHeight(), this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         this.poo();
         var1.doj[0] = false;
      }
   }

   public void poo() {
      PhantomClient.uiManager.getContext().activeDialog = new StringSetEditorDialog(this.getDisplayName(), this);
   }

   public boolean ovk(String var1) {
      return this.getValue().contains(var1.toLowerCase());
   }

   public void lcy(String var1) {
      this.getValue().remove(var1.toLowerCase());
   }

   public void swh(String var1) {
      this.getValue().add(var1.toLowerCase());
   }
}
