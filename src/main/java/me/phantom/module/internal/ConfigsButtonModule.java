package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.gui.widget.ConfigBrowserPanel;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
public class ConfigsButtonModule extends BaseModule {
   public InterfaceModule dcl;

   public ConfigsButtonModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("Configs"));
   }

   @Override
   public boolean supportsHoldMode() {
      return false;
   }

   @Override
   public void setEnabled(boolean var1, boolean var2) {
      if (var1 && var2) {
         PhantomClient.uiManager.getContext().activeDialog = new ConfigBrowserPanel(Minecraft.getInstance().screen == null);
         this.dcl.setEnabled(true, true);
      }

      if (this.isEnabled()) {
         super.setEnabled(false, var2);
      }
   }
}
