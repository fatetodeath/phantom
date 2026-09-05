package me.phantom.mixin;

import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.MinecraftClientBridge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.session.Session;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MinecraftClient.class})
public abstract class MinecraftClientTickMixin extends ReentrantThreadExecutor implements WindowEventHandler, MinecraftClientBridge {
   @Shadow
   private int itemUseCooldown;
   @Shadow
   private int attackCooldown;
   @Shadow
   private ClientPlayerEntity player;
   @Shadow
   private HitResult crosshairTarget;
   @Shadow
   private ClientPlayerInteractionManager interactionManager;
   @Mutable
   @Shadow
   private Session session;

   public MinecraftClientTickMixin(String string) {
      super(string);
   }

   @Override
   public HitResult getCrosshairTarget() {
      return this.crosshairTarget;
   }

   @Override
   public void setAttackCooldown(int i) {
      this.attackCooldown = i;
   }

   @Override
   public void setItemUseCooldown(int i) {
      this.itemUseCooldown = i;
   }

   @Override
   public int getItemUseCooldown() {
      return this.itemUseCooldown;
   }

   @Override
   public ClientPlayerInteractionManager getInteractionManager() {
      return this.interactionManager;
   }

   @Override
   public void setSession(Session session) {
      this.session = session;
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
