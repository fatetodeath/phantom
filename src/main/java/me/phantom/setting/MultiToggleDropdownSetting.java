package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.InlineDropdownWidget;
import me.phantom.module.base.BaseModule;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.HashSet;
import java.util.Set;

public class MultiToggleDropdownSetting extends AbstractSetting<Set<String>> implements InlineDropdownWidget {
   private final String[] rhlb;
   public int yo;
   private int fyc;
   public int uyc;

   public MultiToggleDropdownSetting(BaseModule var1, String var2, String... var3) {
      super(var1, var2, new HashSet());
      this.rhlb = var3;
   }

   public MultiToggleDropdownSetting pxn() {
      for (String var4 : this.rhlb) {
         this.eo(var4);
      }

      return this;
   }

   public MultiToggleDropdownSetting eo(String var1) {
      this.getValue().add(var1);
      return this;
   }

   public MultiToggleDropdownSetting qb(String var1) {
      this.getValue().remove(var1);
      return this;
   }

   public boolean yue(String var1) {
      return this.getValue().contains(var1);
   }

   public void xmh(Set<String> var1) {
      throw new IllegalArgumentException();
   }

   public String[] yj() {
      return this.rhlb;
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.getValue().clear();
      ((JsonArray)var1).forEach(var1x -> this.getValue().add(var1x.getAsString()));
   }

   @Override
   public JsonElement toJson() {
      JsonArray var1 = new JsonArray();
      this.getValue().forEach(var1::add);
      return var1;
   }

   @Override
   public int getHeight() {
      return 30;
   }

   @Override
   public boolean kdf() {
      return false;
   }

   @Override
   public int cw() {
      return this.yj().length * 27 + 1;
   }

   @Override
   public int exl() {
      return this.uyc;
   }

   @Override
   public int dsx() {
      return this.yo;
   }

   @Override
   public int ivt() {
      return this.fyc;
   }

   @Override
   public void abu(GuiRenderContext var1, int var2, int var3) {
      var1.translate(0, 2);

      for (int var4 = 0; var4 < this.rhlb.length; var4++) {
         if (var1.drawHeaderButton(var2, var3 - 27 * var4, this.rhlb[var4], this.uyc, 25, this.yue(this.rhlb[var4])) && var1.doj[0]) {
            if (this.yue(this.rhlb[var4])) {
               this.qb(this.rhlb[var4]);
            } else {
               this.eo(this.rhlb[var4]);
            }

            var1.doj[0] = true;
         }

         var1.translate(0, 27);
      }

      var1.translate(0, -this.rhlb.length * 27 - 2);
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, 24, this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         var1.doj[0] = false;
         var1.qxy(this);
         this.yo = var1.mouseX;
         this.fyc = var1.mouseY + 25 + 1;
         this.uyc = var2;
      }
   }

   public boolean uyz(String... var1) {
      for (String var5 : var1) {
         if (this.yue(var5)) {
            return true;
         }
      }

      return false;
   }
}
