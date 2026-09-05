package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.InlineDropdownWidget;
import me.phantom.module.base.BaseModule;
import me.phantom.render.ColorSource;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import org.lwjgl.nanovg.NVGColor;

public class ColorSetting extends AbstractSetting<float[]> implements InlineDropdownWidget, ColorSource {
   public static ColorSetting rwat;
   private int yo;
   private int fyc;
   public int hjp;
   private Color mvy;

   public ColorSetting(BaseModule var1, String var2, Color var3) {
      super(var1, var2, Color.RGBtoHSB(var3.getRed(), var3.getGreen(), var3.getBlue(), new float[3]));
      this.zhx();
   }

   public ColorSetting(BaseModule var1, String var2, float var3, float var4, float var5) {
      super(var1, var2, new float[]{var3, var4, var5});
      this.zhx();
   }

   public void zhx() {
      float[] var1 = (float[])this.getValue();
      this.mvy = Color.getHSBColor(var1[0], var1[1], var1[2]);
   }

   public void twak(float[] var1) {
      super.setValue(var1);
      this.zhx();
   }

   @Override
   public int getHeight() {
      return 30;
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
   public int exl() {
      return 200;
   }

   @Override
   public int cw() {
      return 200;
   }

   @Override
   public void abu(GuiRenderContext var1, int var2, int var3) {
      byte var4 = 10;
      var1.translate(10, 10);
      this.hjp = var1.krq(var1, var2 - 10, var3 - 10, this.exl() - 20, this.cw() - 20, this.hjp, (float[])this.getValue());
      var1.translate(-10, -10);
      if (this.hjp > 0) {
         this.zhx();
      }

      if (!var1.xfy[0]) {
         this.hjp = 0;
      }
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.drawSettingRow(var3, var4, var2, 26, this.getDisplayName(), this.getDescription(), this.nqq(), 1.0F)) {
         rwat = this;
         if (var1.doj[0]) {
            this.yo = var1.mouseX - 5;
            this.fyc = var1.mouseY + 25 + 3;
            var1.qxy(this);
            var1.doj[0] = false;
         }
      }
   }

   @Override
   public void fromJson(JsonElement var1) {
      JsonObject var2 = (JsonObject)var1;
      this.twak(new float[]{var2.get(GlyphTextUtil.encode("h")).getAsFloat(), var2.get(GlyphTextUtil.encode("s")).getAsFloat(), var2.get(GlyphTextUtil.encode("elementCodec")).getAsFloat()});
   }

   @Override
   public JsonElement toJson() {
      float[] var1 = (float[])this.getValue();
      JsonObject var2 = new JsonObject();
      var2.addProperty(GlyphTextUtil.encode("h"), (double)var1[0]);
      var2.addProperty(GlyphTextUtil.encode("s"), (double)var1[1]);
      var2.addProperty(GlyphTextUtil.encode("elementCodec"), (double)var1[2]);
      return var2;
   }

   @Override
   public NVGColor xam(float var1, NVGColor var2) {
      return GuiRenderContext.rgba(this.mvy.getRed(), this.mvy.getGreen(), this.mvy.getBlue(), (int)(var1 * 255.0F), var2);
   }

   @Override
   public Color nqq() {
      return this.mvy;
   }

   public Color ke(int var1) {
      return var1 == 255 ? this.mvy : new Color(this.mvy.getRed(), this.mvy.getGreen(), this.mvy.getBlue(), var1);
   }

   public String fnz() {
      Color var1 = this.nqq();
      return String.format(GlyphTextUtil.plain("#%02x%02x%02x"), var1.getRed(), var1.getGreen(), var1.getBlue());
   }

   public void zpvg(String var1) {
      Color var2 = Color.decode(var1);
      Color.RGBtoHSB(var2.getRed(), var2.getGreen(), var2.getBlue(), (float[])this.getValue());
      this.zhx();
   }
}
