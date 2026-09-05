package me.phantom.setting;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.FriendListScreen;
import me.phantom.module.base.BaseModule;
import me.phantom.module.misc.NameProtectModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class NameListSetting extends AbstractSetting<Map<String, NameProtectEntry>> {
   public static String dpg() {
      Random var0 = new Random();
      StringBuilder var1 = new StringBuilder();
      var1.append(NameProtectModule.le.get(var0.nextInt(NameProtectModule.le.size())));
      if (var0.nextInt(4) != 1) {
         var1.append(var0.nextInt(999));
      }

      if (var1.length() < 12 && var0.nextInt(4) == 1) {
         var1.append(NameProtectModule.le.get(var0.nextInt(NameProtectModule.le.size())));
      }

      return var1.toString();
   }

   public NameListSetting(BaseModule var1, String var2) {
      super(var1, var2, new LinkedHashMap());
   }

   public void qwk(Map<String, NameProtectEntry> var1) {
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
            this.getValue().put(var1x.getAsString().toLowerCase(), new NameProtectEntry(this, var1x.getAsString()));
         } else if (var1x instanceof JsonObject var2) {
            String var3 = var2.get(GlyphTextUtil.encode("name")).getAsString().toLowerCase();
            if (var3.length() >= 2) {
               this.getValue().put(var3, new NameProtectEntry(this, var2));
            }
         }
      });
   }

   @Override
   public JsonElement toJson() {
      JsonArray var1 = new JsonArray();
      this.getValue().values().forEach(var1x -> var1.add(var1x.qr()));
      return var1;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, this.getHeight(), this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         var1.activeDialog = new FriendListScreen(this);
         var1.doj[0] = false;
      }
   }

   public boolean ovk(String var1) {
      return this.getValue().containsKey(var1.toLowerCase());
   }

   public NameProtectEntry knb(String var1) {
      return (NameProtectEntry)this.getValue().get(var1.toLowerCase());
   }

   public void lcy(String var1) {
      this.getValue().remove(var1.toLowerCase());
      if (NameProtectModule.zj.isEnabled()) {
         NameProtectModule.zj.hhk();
         PhantomClient.configManager.fl();
      }
   }

   public void swh(String var1) {
      this.getValue().put(var1.toLowerCase(), new NameProtectEntry(this, var1));
      if (NameProtectModule.zj.isEnabled()) {
         NameProtectModule.zj.hhk();
         PhantomClient.configManager.fl();
      }
   }
}
