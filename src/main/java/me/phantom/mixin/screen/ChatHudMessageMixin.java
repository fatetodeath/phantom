package me.phantom.mixin.screen;

import me.phantom.module.misc.NameProtectModule;
import me.phantom.util.mc.ChatHudMessagesAccessor;

import java.util.List;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.ChatHudLine.Visible;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({ChatHud.class})
public abstract class ChatHudMessageMixin implements ChatHudMessagesAccessor {
   @ModifyVariable(
      method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"},
      at = @At("HEAD")
   )
   public Text addMessage(Text message) {
      return (Text)NameProtectModule.jue(message);
   }

   @Accessor("messages")
   @Override
   public abstract List<ChatHudLine> getMessages();

   @Accessor("visibleMessages")
   @Override
   public abstract List<Visible> getVisibleMessages();
}
