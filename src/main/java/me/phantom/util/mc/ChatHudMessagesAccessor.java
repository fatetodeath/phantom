package me.phantom.util.mc;

import java.util.List;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.ChatHudLine.Visible;

public interface ChatHudMessagesAccessor {
   List<ChatHudLine> getMessages();

   List<Visible> getVisibleMessages();
}
