package me.phantom.event.packet;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;

public class PacketEvent extends AbstractPacketEvent {
   private static PerThreadObjectPool<PacketEvent> pool = new PerThreadObjectPool(() -> new PacketEvent());
   public Packet<ClientPlayNetworkHandler> rc;

   public static PacketEvent hd(Packet<ClientPlayNetworkHandler> var0) {
      PacketEvent var1 = (PacketEvent)pool.get();
      var1.setCancelled(false);
      var1.rc = var0;
      return var1;
   }

   public Packet<ClientPlayNetworkHandler> wmo() {
      return this.rc;
   }

   @Override
   public boolean stopsWhenCancelled() {
      return false;
   }
}
