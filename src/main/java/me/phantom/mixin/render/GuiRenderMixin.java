package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.ScoreboardSidebarRenderEvent;
import me.phantom.util.mc.InGameHudTitleAccessor;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.scores.Objective;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Gui.class})
public abstract class GuiRenderMixin implements InGameHudTitleAccessor {
   @Accessor("title")
   @Override
   public abstract Component getTitle();

   @Accessor("subtitle")
   @Override
   public abstract Component getSubtitle();

   @Accessor("title")
   @Override
   public abstract void setTitle_(Component var1);

   @Accessor("subtitle")
   @Override
   public abstract void setSubtitle_(Component var1);

   @Inject(
      method = {"displayScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/scores/Objective;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderScoreboardSidebar(GuiGraphicsExtractor context, Objective objective, CallbackInfo callback) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(ScoreboardSidebarRenderEvent.nq()).isCancelled()) {
         callback.cancel();
      }
   }

   @Inject(
      method = {"extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V"},
      at = {@At("TAIL")}
   )
   public void extractRenderState(GuiGraphicsExtractor context, DeltaTracker tickCounter, CallbackInfo callback) {
   }
}
