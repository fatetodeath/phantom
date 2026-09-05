package me.phantom.render;

import java.awt.Color;
import me.phantom.gui.core.GuiRenderContext;
import org.lwjgl.nanovg.NVGColor;

public interface ColorSource {
   Color nqq();

   NVGColor xam(float var1, NVGColor var2);

   static ColorSource zto(Color var0) {
      return new ColorSource() {
         @Override
         public Color nqq() {
            return var0;
         }

         @Override
         public NVGColor xam(float var1, NVGColor var2) {
            return GuiRenderContext.rgba(var0.getRed(), var0.getGreen(), var0.getBlue(), (int)(var1 * 255.0F), var2);
         }
      };
   }
}
