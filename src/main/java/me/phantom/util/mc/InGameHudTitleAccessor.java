package me.phantom.util.mc;

import net.minecraft.network.chat.Component;

public interface InGameHudTitleAccessor {
   Component getTitle();

   Component getSubtitle();

   void setTitle_(Component var1);

   void setSubtitle_(Component var1);
}
