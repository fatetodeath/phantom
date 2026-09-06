package me.phantom.util.mc;

import com.mojang.blaze3d.platform.InputConstants.Key;

public interface KeyBindingTimesPressedAccessor {
   int getTimesPressed();

   void setTimesPressed(int var1);

   String getName();

   Key getBoundKey();
}
