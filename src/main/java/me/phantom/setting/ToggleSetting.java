package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.EasedAnimation;
import me.phantom.module.base.BaseModule;
import me.phantom.util.JsonUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ToggleSetting extends AbstractSetting<Boolean> {
   private EasedAnimation env = new EasedAnimation(false);
   private EasedAnimation ix = new EasedAnimation(false);

   public ToggleSetting(BaseModule var1, String var2, boolean var3) {
      super(var1, var2, var3);
   }

   public void bdl(Boolean var1) {
      if (var1 != this.getValue()) {
         if (var1) {
            this.env.qou(300);
         } else {
            this.ix.qou(300);
         }
      }

      super.setValue(var1);
   }

   @Override
   public int getHeight() {
      return 30;
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.bdl(JsonUtils.uzc(var1));
   }

   @Override
   public JsonElement toJson() {
      return new JsonPrimitive(this.getValue());
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      float var7 = this.getValue() ? this.env.hee() : 1.0F - this.ix.hee();
      if (var1.pstk(var3, var4, var2, 26, this.getDisplayName(), this.getDescription(), this.getValue(), var7)) {
         this.bdl(!this.getValue());
      }
   }
}
