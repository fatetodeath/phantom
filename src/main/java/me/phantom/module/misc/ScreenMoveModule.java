package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.gui.screen.BlankClientScreen;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.KeyBindingTimesPressedAccessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.Options;
import net.minecraft.client.KeyMapping;
public class ScreenMoveModule extends BaseModule {
   public final ToggleSetting al = new ToggleSetting(this, GlyphTextUtil.encode("phantom_screen"), true);
   public final ToggleSetting tos = new ToggleSetting(this, GlyphTextUtil.encode("inv_move"), false);

   public ScreenMoveModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.ats, GlyphTextUtil.encode("ScreenMove"));
   }

   public void tjy(TickEvent var1) {
      Screen var2 = Minecraft.getInstance().screen;
      if (var2 != null
         && (var2 instanceof AbstractContainerScreen && (Boolean)this.tos.getValue() || var2 instanceof BlankClientScreen && (Boolean)this.al.getValue() && !PhantomClient.uiManager.guf() && PhantomClient.uiManager.getContext().activeDialog == null)
         )
       {
         Options var3 = Minecraft.getInstance().options;
         boolean[] var4 = PhantomClient.uiManager.getContext().gml;

         for (KeyMapping var8 : new KeyMapping[]{var3.keySprint, var3.keyUp, var3.keyDown, var3.keyLeft, var3.keyRight, var3.keyShift, var3.keyJump}) {
            KeyBindingTimesPressedAccessor var9 = (KeyBindingTimesPressedAccessor)var8;
            int var10 = var9.getBoundKey().getValue();
            var8.setDown(var10 >= 0 && var10 < var4.length && var4[var9.getBoundKey().getValue()]);
         }
      }
   }

}
