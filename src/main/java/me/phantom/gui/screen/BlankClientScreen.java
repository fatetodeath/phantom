package me.phantom.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class BlankClientScreen extends Screen {
   public BlankClientScreen() {
      super(Text.empty());
   }

   public void render(DrawContext var1, int var2, int var3, float var4) {
   }

   public boolean shouldPause() {
      return false;
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }
}
