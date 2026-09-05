package me.phantom.gui.hud;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.HudModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import java.nio.FloatBuffer;
import org.lwjgl.nanovg.NanoVG;

public class FpsHudElement extends LabeledHudElement {
   public FpsHudElement(JsonObject var1) {
      super(var1);
   }

   @Override
   public float icg(GuiRenderContext var1) {
      long var2 = var1.nvg;
      NanoVG.nvgFontFace(var2, InterfaceModule.fontRegular);
      NanoVG.nvgFontSize(var2, 12.0F);
      GlyphTextUtil.setTextAlign(var2, 9);
      return GlyphTextUtil.measureText(var2, 0.0F, 0.0F, GlyphTextUtil.encode("FPS: 300"), (FloatBuffer)null) + this.yxe(var1) + 10.0F + 3.0F;
   }

   @Override
   public String oiuz(float var1) {
      return GlyphTextUtil.encode("FPS: ") + HudModule.sqy.size();
   }

   @Override
   public int uod() {
      return 127748;
   }
}
