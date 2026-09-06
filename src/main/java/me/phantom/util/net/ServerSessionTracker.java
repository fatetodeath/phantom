package me.phantom.util.net;

import me.phantom.core.PhantomClient;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class ServerSessionTracker {
   public String di;
   private String sb;

   public void update() {
      String var1 = null;

      try {
         ServerData var2 = Minecraft.getInstance().getConnection().getServerData();
         var1 = var2.ip;
      } catch (Throwable var5) {
      }

      String var7 = null;

      try {
         var7 = Minecraft.getInstance().getUser().getName();
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
