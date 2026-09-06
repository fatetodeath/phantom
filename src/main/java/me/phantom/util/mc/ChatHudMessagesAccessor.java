package me.phantom.util.mc;

import java.util.List;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.client.multiplayer.chat.GuiMessage.Line;

public interface ChatHudMessagesAccessor {
   List<GuiMessage> getMessages();

   List<Line> getVisibleMessages();
}
