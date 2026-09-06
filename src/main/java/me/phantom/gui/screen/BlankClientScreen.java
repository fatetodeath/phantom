package me.phantom.gui.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BlankClientScreen extends Screen {
   public BlankClientScreen() {
      super(Component.empty());
   }

   public void extractRenderState(GuiGraphicsExtractor var1, int var2, int var3, float var4) {
   }

   public boolean isPauseScreen() {
      return false;
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }
}
