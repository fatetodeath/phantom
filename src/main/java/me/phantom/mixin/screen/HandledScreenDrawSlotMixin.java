package me.phantom.mixin.screen;

import me.phantom.core.PhantomClient;
import me.phantom.gui.widget.FocusedSlotProvider;
import me.phantom.module.inventory.InventoryActionModule;
import me.phantom.util.GlyphTextUtil;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HandledScreen.class})
public abstract class HandledScreenDrawSlotMixin implements FocusedSlotProvider {
   @Inject(
      method = {"drawSlot(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/screen/slot/Slot;II)V"},
      at = {@At("HEAD")}
   )
   protected void drawSlot(CallbackInfo callback, @Local(ordinal = 0) DrawContext context, @Local(ordinal = 0) Slot slot) {
      try {
         if (InventoryActionModule.acr != null) {
            InventoryActionModule.acr.byc(context, slot);
         }
      } catch (Throwable var5) {
         PhantomClient.fatalError(GlyphTextUtil.encode("drawSlot"), GlyphTextUtil.encode("Failed to draw indicator slots"), var5);
      }
   }

   @Accessor("focusedSlot")
   @Override
   public abstract Slot getFocusedSlot();
}
