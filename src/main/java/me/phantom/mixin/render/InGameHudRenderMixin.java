package me.phantom.mixin.render;

import me.phantom.core.PhantomClient;
import me.phantom.event.render.ScoreboardSidebarRenderEvent;
import me.phantom.util.mc.InGameHudTitleAccessor;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InGameHud.class})
public abstract class InGameHudRenderMixin implements InGameHudTitleAccessor {
   @Accessor("title")
   @Override
   public abstract Text getTitle();

   @Accessor("subtitle")
   @Override
   public abstract Text getSubtitle();

   @Accessor("title")
   @Override
   public abstract void setTitle_(Text var1);

   @Accessor("subtitle")
   @Override
   public abstract void setSubtitle_(Text var1);

   @Inject(
      method = {"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderScoreboardSidebar(DrawContext context, ScoreboardObjective objective, CallbackInfo callback) {
      if (PhantomClient.initialized && PhantomClient.eventManager.dispatch(ScoreboardSidebarRenderEvent.nq()).isCancelled()) {
         callback.cancel();
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("TAIL")}
   )
   public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo callback) {
      if (PhantomClient.initialized) {
         PhantomClient.uiManager.upfx(context, tickCounter.getTickProgress(false));
      }
   }
}
