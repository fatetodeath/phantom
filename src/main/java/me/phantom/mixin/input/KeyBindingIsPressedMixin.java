package me.phantom.mixin.input;

import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.util.mc.KeyBindingTimesPressedAccessor;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({KeyBinding.class})
public abstract class KeyBindingIsPressedMixin implements Comparable, KeyBindingTimesPressedAccessor {
   @Shadow
   public int timesPressed;
   @Shadow
   public boolean pressed;
   @Shadow
   public String translationKey;
   @Shadow
   public Key boundKey;

   @Override
   public int getTimesPressed() {
      return this.timesPressed;
   }

   @Override
   public void setTimesPressed(int timePressed) {
      this.timesPressed = timePressed;
   }

   @Overwrite(
      aliases = {"isPressed"}
   )
   public boolean isPressed() {
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(KeyStateEvent.kt(this, this.pressed)).re() : this.pressed;
   }

   @Override
   public String getName() {
      return this.translationKey;
   }

   @Override
   public Key getBoundKey() {
      return this.boundKey;
   }
}
