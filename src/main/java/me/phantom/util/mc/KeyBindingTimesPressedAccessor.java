package me.phantom.util.mc;

import net.minecraft.client.util.InputUtil.Key;

public interface KeyBindingTimesPressedAccessor {
   int getTimesPressed();

   void setTimesPressed(int var1);

   String getName();

   Key getBoundKey();
}
