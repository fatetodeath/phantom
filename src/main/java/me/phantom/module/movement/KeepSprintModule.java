package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
public class KeepSprintModule extends BaseModule {
   private boolean ksf;

   public KeepSprintModule() {
      super(ModuleCategory.wld, ClientPlayStyle.zfw, GlyphTextUtil.encode("KeepSprint"));
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
      this.ksf = false;
   }

   public void sau(KeyStateEvent var1) {
      if (Minecraft.getInstance().screen != null) {
         this.ksf = false;
      } else if (var1.by().getName().equals(GlyphTextUtil.plain("key.attack"))) {
         if (var1.by().getTimesPressed() > 0) {
            this.ksf = true;
         }
      } else if (var1.by().getName().equals(GlyphTextUtil.plain("key.sprint")) && this.ksf) {
         var1.ywz(true);
         this.ksf = false;
      }
   }
}
