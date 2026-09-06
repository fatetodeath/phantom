package me.phantom.event.packet;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;

public class PacketEvent extends AbstractPacketEvent {
   private static PerThreadObjectPool<PacketEvent> pool = new PerThreadObjectPool(() -> new PacketEvent());
   public Packet<ClientPacketListener> rc;

   public static PacketEvent hd(Packet<ClientPacketListener> var0) {
      PacketEvent var1 = (PacketEvent)pool.get();
      var1.setCancelled(false);
      var1.rc = var0;
      return var1;
   }

   public Packet<ClientPacketListener> wmo() {
      return this.rc;
   }

   @Override
   public boolean stopsWhenCancelled() {
      return false;
   }
}
