package me.phantom.util.mc;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.session.Session;
import net.minecraft.util.hit.HitResult;

public interface MinecraftClientBridge {
   HitResult getCrosshairTarget();

   void setAttackCooldown(int var1);

   void setItemUseCooldown(int var1);

   int getItemUseCooldown();

   ClientPlayerInteractionManager getInteractionManager();

   void setSession(Session var1);
}
