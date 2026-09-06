package me.phantom.util.mc;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.User;
import net.minecraft.world.phys.HitResult;

public interface MinecraftClientBridge {
   HitResult getCrosshairTarget();

   void setAttackCooldown(int var1);

   void setItemUseCooldown(int var1);

   int getItemUseCooldown();

   MultiPlayerGameMode getInteractionManager();

   void setSession(User var1);
}
