package me.phantom.mixin.input;

import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.util.mc.KeyBindingTimesPressedAccessor;

import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants.Key;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({KeyMapping.class})
public abstract class KeyMappingIsPressedMixin implements Comparable, KeyBindingTimesPressedAccessor {
   @Shadow
   private int clickCount;
   @Shadow
   private boolean isDown;
   @Shadow
   public abstract String getName();
   @Shadow
   public Key key;

   @Override
   public int getTimesPressed() {
      return this.clickCount;
   }

   @Override
   public void setTimesPressed(int timePressed) {
      this.clickCount = timePressed;
   }

   @Overwrite(
      aliases = {"isPressed"}
   )
   public boolean isDown() {
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(KeyStateEvent.kt(this, this.isDown)).re() : this.isDown;
   }

    @Override
   public Key getBoundKey() {
      return this.key;
   }
}
