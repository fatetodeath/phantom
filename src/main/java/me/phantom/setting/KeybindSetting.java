package me.phantom.setting;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.core.UiManager;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Objects;

public class KeybindSetting extends AbstractSetting<Integer> implements KeyBindable {
   public KeybindSetting(BaseModule var1, String var2, int var3) {
      super(var1, var2, var3);
   }

   @Override
   public boolean isHoldMode() {
      return false;
   }

   public void esd(Integer var1) {
      super.setValue(var1);
   }

   @Override
   public JsonElement toJson() {
      return new JsonPrimitive((Integer)this.getValue());
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.esd(var1.getAsInt());
   }

   @Override
   public int getHeight() {
      return 42;
   }

   @Override
   public String getDisplayName() {
      String var1 = this.getValue() == 0 ? "None" : UiManager.getKeyName((Integer)this.getValue());
      return this.cachedLabel.replace(GlyphTextUtil.encode("$1"), var1 == null ? GlyphTextUtil.encode("None") : var1);
   }

   @Override
   public String getDescription() {
      String var1 = this.getValue() == 0 ? "None" : UiManager.getKeyName((Integer)this.getValue());
      return this.cachedDescription.replace(GlyphTextUtil.encode("$1"), var1 == null ? GlyphTextUtil.encode("None") : var1);
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      String var7 = Objects.equals(var1.keybindCaptureTarget, this)
         ? (System.currentTimeMillis() % 1000L <= 500L ? GlyphTextUtil.encode("- ") + PhantomClient.translationManager.ry(GlyphTextUtil.encode("keybind.msg")) + GlyphTextUtil.encode(" -") : GlyphTextUtil.encode("-  -"))
         : this.getDisplayName();
      if (var1.iut(var3, var4, var2, 24, var7, this.getDescription()) && var1.doj[0]) {
         var1.doj[0] = false;
         var1.keybindCaptureTarget = this;
      }
   }

   public boolean rrq() {
      int var1 = (Integer)this.getValue();
      if (var1 >= 100000 && var1 < 100008) {
         return PhantomClient.uiManager.getContext().xfy[var1 - 100000];
      } else {
         return var1 > 0 && var1 < 1024 ? PhantomClient.uiManager.getContext().gml[var1] : false;
      }
   }

   @Override
   public void setKeybind(int var1, boolean var2) {
      this.esd(var1);
   }

   @Override
   public int getKeyCode() {
      return (Integer)this.getValue();
   }
}
