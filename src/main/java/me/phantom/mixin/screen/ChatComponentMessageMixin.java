package me.phantom.mixin.screen;

import me.phantom.module.misc.NameProtectModule;
import me.phantom.util.mc.ChatHudMessagesAccessor;

import java.util.List;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.client.multiplayer.chat.GuiMessage.Line;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({ChatComponent.class})
public abstract class ChatComponentMessageMixin implements ChatHudMessagesAccessor {
   @ModifyVariable(
      method = {"addPlayerMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/multiplayer/chat/GuiMessageTag;)V"},
      at = @At("HEAD")
   )
   public Component addMessage(Component message) {
      return (Component)NameProtectModule.jue(message);
   }

   @Accessor("allMessages")
   @Override
   public abstract List<GuiMessage> getMessages();

   @Accessor("trimmedMessages")
   @Override
   public abstract List<Line> getVisibleMessages();
}
