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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
public class ScreenMoveModule extends BaseModule {
   public final ToggleSetting al = new ToggleSetting(this, GlyphTextUtil.encode("phantom_screen"), true);
   public final ToggleSetting tos = new ToggleSetting(this, GlyphTextUtil.encode("inv_move"), false);

   public ScreenMoveModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.ats, GlyphTextUtil.encode("ScreenMove"));
   }

   public void tjy(TickEvent var1) {
      Screen var2 = MinecraftClient.getInstance().currentScreen;
      if (var2 != null
         && (var2 instanceof HandledScreen && (Boolean)this.tos.getValue() || var2 instanceof BlankClientScreen && (Boolean)this.al.getValue() && !PhantomClient.uiManager.guf() && PhantomClient.uiManager.getContext().activeDialog == null)
         )
       {
         GameOptions var3 = MinecraftClient.getInstance().options;
         boolean[] var4 = PhantomClient.uiManager.getContext().gml;

         for (KeyBinding var8 : new KeyBinding[]{var3.sprintKey, var3.forwardKey, var3.backKey, var3.leftKey, var3.rightKey, var3.sneakKey, var3.jumpKey}) {
            KeyBindingTimesPressedAccessor var9 = (KeyBindingTimesPressedAccessor)var8;
            int var10 = var9.getBoundKey().getCode();
            var8.setPressed(var10 >= 0 && var10 < var4.length && var4[var9.getBoundKey().getCode()]);
         }
      }
   }

}
