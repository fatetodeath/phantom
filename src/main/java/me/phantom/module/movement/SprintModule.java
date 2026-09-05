package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.combat.AutoClickerModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
public class SprintModule extends BaseModule {
   public AutoClickerModule oqk;
   public ToggleSetting bzo = new ToggleSetting(this, GlyphTextUtil.encode("deinterrupt_chest"), false);

   public SprintModule() {
      super(ModuleCategory.wld, ClientPlayStyle.or, GlyphTextUtil.encode("Sprint"));
      this.markClipboardHint();
   }

   public void sau(KeyStateEvent var1) {
      if (var1.by().getName().equals(GlyphTextUtil.plain("key.sprint")) && (!this.oqk.isEnabled() || this.oqk.ind <= 0) && MinecraftClient.getInstance().currentScreen == null) {
         var1.ywz(true);
      }

      if ((Boolean)this.bzo.getValue()
         && var1.by().getName().equals("key.use")
         && var1.re()
         && PhantomClient.mc().getCrosshairTarget() instanceof BlockHitResult var2
         && var2.getType() == Type.BLOCK) {
         MinecraftClient.getInstance().player.setSprinting(false);
      }
   }
}
