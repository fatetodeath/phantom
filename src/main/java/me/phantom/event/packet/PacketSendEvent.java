package me.phantom.event.packet;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.PerThreadObjectPool;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class PacketSendEvent extends AbstractPacketEvent {
   private static PerThreadObjectPool<PacketSendEvent> pool = new PerThreadObjectPool(() -> new PacketSendEvent());
   private Packet<ServerGamePacketListenerImpl> rc;

   public static PacketSendEvent sk(Packet<ServerGamePacketListenerImpl> var0) {
      PacketSendEvent var1 = (PacketSendEvent)pool.get();
      var1.setCancelled(false);
      var1.rc = var0;
      return var1;
   }

   public Packet<ServerGamePacketListenerImpl> wmo() {
      return this.rc;
   }

   @Override
   public boolean stopsWhenCancelled() {
      return false;
   }
}
