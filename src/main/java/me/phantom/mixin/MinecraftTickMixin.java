package me.phantom.mixin;

import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.MinecraftClientBridge;

import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.WindowEventHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.User;
import net.minecraft.world.phys.HitResult;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public abstract class MinecraftTickMixin extends ReentrantBlockableEventLoop implements WindowEventHandler, MinecraftClientBridge {
   @Shadow
   private int rightClickDelay;
   @Shadow
   private int missTime;
   @Shadow
   private LocalPlayer player;
   @Shadow
   private HitResult hitResult;
   @Shadow
   private MultiPlayerGameMode gameMode;
   @Mutable
   @Shadow
   private User user;

   public MinecraftTickMixin(String string) {
      super(string, false);
   }

   @Override
   public HitResult getCrosshairTarget() {
      return this.hitResult;
   }

   @Override
   public void setAttackCooldown(int i) {
      this.missTime = i;
   }

   @Override
   public void setItemUseCooldown(int i) {
      this.rightClickDelay = i;
   }

   @Override
   public int getItemUseCooldown() {
      return this.rightClickDelay;
   }

   @Override
   public MultiPlayerGameMode getInteractionManager() {
      return this.gameMode;
   }

   @Override
   public void setSession(User session) {
      this.user = session;
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("HEAD")}
   )
   public void tick(CallbackInfo info) {
      try {
         if (PhantomClient.initialized) {
            PhantomClient.update();
            if (this.player != null && PhantomClient.initialized) {
               PhantomClient.eventManager.dispatch(TickEvent.get());
            }
         } else {
            PhantomClient.initialize();
         }
      } catch (Throwable var3) {
         PhantomClient.fatalError(GlyphTextUtil.encode("Minecraft"), GlyphTextUtil.encode("General tick failure"), var3);
      }
   }
}
