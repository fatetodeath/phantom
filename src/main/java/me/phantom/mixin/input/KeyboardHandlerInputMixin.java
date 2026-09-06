package me.phantom.mixin.input;

import me.phantom.core.PhantomClient;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.CharacterEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({KeyboardHandler.class})
public class KeyboardHandlerInputMixin {
   @Inject(
      method = {"keyPress(JILnet/minecraft/client/input/KeyEvent;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onKey(long window, int action, KeyEvent keyInput, CallbackInfo info) {
      if (PhantomClient.initialized && PhantomClient.uiManager.hcj(keyInput.key(), keyInput.scancode(), action, keyInput.modifiers())) {
         info.cancel();
      }
   }

   @Inject(
      method = {"charTyped(JLnet/minecraft/client/input/CharacterEvent;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onChar(long window, CharacterEvent charInput, CallbackInfo info) {
      if (PhantomClient.initialized && PhantomClient.uiManager.okr((char)charInput.codepoint(), 0)) {
         info.cancel();
      }
   }
}
