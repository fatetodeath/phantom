package me.phantom.util.mc;

import net.minecraft.network.chat.Component;

public interface ScoreboardEntryAccessor {
   void setDisplay(Component var1);

   void setOwner(String var1);
}
