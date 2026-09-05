package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.InlineDropdownWidget;
import me.phantom.module.base.BaseModule;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Objects;

public class ModeSetting extends AbstractSetting<String> implements InlineDropdownWidget {
   private final String[] rhlb;
   private int yo;
   public int fyc;
   private int uyc;

   public ModeSetting(BaseModule var1, String var2, String... var3) {
      super(var1, var2, var3[0]);
      this.rhlb = var3;
   }

   public void toh(String var1) {
      for (String var5 : this.rhlb) {
         if (Objects.equals(var5, var1)) {
            super.setValue(var1);
            break;
         }
      }
   }

   public String[] yj() {
      return this.rhlb;
   }

   @Override
   public JsonElement toJson() {
      return new JsonPrimitive((String)this.getValue());
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.toh(var1.getAsString());
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
         if (var1.drawHeaderButton(var2, var3 - 27 * var4, this.rhlb[var4], this.uyc, 25, ((String)this.getValue()).equals(this.rhlb[var4])) && var1.doj[0]) {
            this.toh(this.rhlb[var4]);
            var1.doj[0] = true;
         }

         var1.translate(0, 27);
      }

      var1.translate(0, -this.rhlb.length * 27 - 2);
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, 24, this.getDisplayName(), this.getDescription())) {
         if (var1.doj[0]) {
            var1.doj[0] = false;
            var1.qxy(this);
            this.yo = var1.mouseX;
            this.fyc = var1.mouseY + 25 + 1;
            this.uyc = var2;
         } else if (var1.doj[1]) {
            var1.doj[1] = false;

            for (int var7 = 0; var7 < this.rhlb.length; var7++) {
               if (((String)this.getValue()).equals(this.rhlb[var7])) {
                  this.toh(this.rhlb[(var7 + 1) % this.rhlb.length]);
                  break;
               }
            }
         }
      }
   }
}
