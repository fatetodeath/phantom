package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.hud.ArmorHudElement;
import me.phantom.gui.hud.ArmorVerticalHudElement;
import me.phantom.gui.hud.ClockHudElement;
import me.phantom.gui.hud.CoordinatesHudElement;
import me.phantom.gui.hud.EnderChestHudElement;
import me.phantom.gui.hud.FpsHudElement;
import me.phantom.gui.hud.HudAnchor;
import me.phantom.gui.hud.HudConfigPanel;
import me.phantom.gui.hud.HudElementBase;
import me.phantom.gui.hud.InventoryItemsHudElement;
import me.phantom.gui.hud.LeftCpsHudIndicator;
import me.phantom.gui.hud.LogoHudElement;
import me.phantom.gui.hud.NetherCoordsHudElement;
import me.phantom.gui.hud.PingGraphHudElement;
import me.phantom.gui.hud.PotionsHudElement;
import me.phantom.gui.hud.RightCpsHudElement;
import me.phantom.gui.hud.ServerBrandHudElement;
import me.phantom.gui.hud.UsernameHudElement;
import me.phantom.gui.hud.HudServerInfoElement;
import me.phantom.gui.widget.KeybindsListPanel;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class HudElementsSetting extends AbstractSetting<JsonObject> {
   public static Map<String, Function<JsonObject, HudElementBase>> sfw = new LinkedHashMap<>();

   public HudElementsSetting(BaseModule var1, String var2, JsonObject var3) {
      super(var1, var2, var3);
   }

   @Override
   public int getHeight() {
      return 26;
   }

   @Override
   public void fromJson(JsonElement var1) {
      JsonObject var2 = (JsonObject)var1;

      for (HudAnchor var6 : HudAnchor.vwu()) {
         var6.sm.clear();
         if (var2.has(var6.vr())) {
            JsonArray var7 = var2.get(var6.vr()).getAsJsonArray();

            for (int var8 = 0; var8 < var7.size(); var8++) {
               JsonObject var9 = var7.get(var8).getAsJsonObject();
               HudElementBase var10 = sfw.get(var9.get(GlyphTextUtil.encode("type")).getAsString()).apply(var9);
               var6.sm.add(var10);
            }
         }
      }
   }

   public void psp(JsonObject var1) {
      throw new IllegalArgumentException();
   }

   @Override
   public JsonElement toJson() {
      JsonObject var1 = new JsonObject();

      for (HudAnchor var5 : HudAnchor.vwu()) {
         JsonArray var6 = new JsonArray();

         for (HudElementBase var8 : var5.sm) {
            var6.add(var8.qr());
         }

         var1.add(var5.vr(), var6);
      }

      return var1;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, this.getHeight(), this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         var1.activeDialog = new HudConfigPanel(this);
         var1.doj[0] = false;
      }
   }

   static {
      sfw.put(GlyphTextUtil.encode("watermark"), LogoHudElement::new);
      sfw.put(GlyphTextUtil.encode("cpsl"), LeftCpsHudIndicator::new);
      sfw.put(GlyphTextUtil.encode("cpsr"), RightCpsHudElement::new);
      sfw.put(GlyphTextUtil.encode("fps"), FpsHudElement::new);
      sfw.put(GlyphTextUtil.encode("ping"), PingGraphHudElement::new);
      sfw.put(GlyphTextUtil.encode("username"), UsernameHudElement::new);
      sfw.put(GlyphTextUtil.encode("server"), HudServerInfoElement::new);
      sfw.put(GlyphTextUtil.encode("enderchest"), EnderChestHudElement::new);
      sfw.put(GlyphTextUtil.encode("inventory"), InventoryItemsHudElement::new);
      sfw.put(GlyphTextUtil.encode("serverbrand"), ServerBrandHudElement::new);
      sfw.put(GlyphTextUtil.encode("clock"), ClockHudElement::new);
      sfw.put(GlyphTextUtil.encode("keybinds"), KeybindsListPanel::new);
      sfw.put(GlyphTextUtil.encode("armor"), ArmorHudElement::new);
      sfw.put(GlyphTextUtil.encode("armor_vertical"), ArmorVerticalHudElement::new);
      sfw.put(GlyphTextUtil.encode("coordinates"), CoordinatesHudElement::new);
      sfw.put(GlyphTextUtil.encode("nether_coordinates"), NetherCoordsHudElement::new);
      sfw.put(GlyphTextUtil.encode("potions"), PotionsHudElement::new);
   }
}
