package me.phantom.util.net;

import me.phantom.core.PhantomClient;

import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

public class ServerSessionTracker {
   public String di;
   private String sb;

   public void update() {
      String var1 = null;

      try {
         ServerInfo var2 = MinecraftClient.getInstance().getNetworkHandler().getServerInfo();
         var1 = var2.address;
      } catch (Throwable var5) {
      }

      String var7 = null;

      try {
         var7 = MinecraftClient.getInstance().getSession().getUsername();
      } catch (Throwable var4) {
      }

      try {
         if (!Objects.equals(this.di, var1) || !Objects.equals(this.sb, var7)) {
            this.di = var1;
            this.sb = var7;
            PhantomClient.cloudApi.fb(var7, var1);
         }
      } catch (Throwable var6) {
      }
   }

}
