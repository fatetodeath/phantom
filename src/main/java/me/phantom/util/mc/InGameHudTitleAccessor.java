package me.phantom.util.mc;

import net.minecraft.text.Text;

public interface InGameHudTitleAccessor {
   Text getTitle();

   Text getSubtitle();

   void setTitle_(Text var1);

   void setSubtitle_(Text var1);
}
