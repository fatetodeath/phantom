package me.phantom.event.inventory;

import me.phantom.event.packet.AbstractPacketEvent;

import me.phantom.util.inventory.ContainerPassMode;
import me.phantom.util.inventory.InventoryWrapper;
import me.phantom.util.PerThreadObjectPool;

public class ContainerSwitchEvent extends AbstractPacketEvent {
   private static PerThreadObjectPool<ContainerSwitchEvent> pool = new PerThreadObjectPool(() -> new ContainerSwitchEvent());
   public InventoryWrapper qg;
   private InventoryWrapper ac;
   private ContainerPassMode yr;

   public static ContainerSwitchEvent get(InventoryWrapper var0, InventoryWrapper var1, ContainerPassMode var2) {
      ContainerSwitchEvent var3 = (ContainerSwitchEvent)pool.get();
      var3.setCancelled(false);
      var3.qg = var0;
      var3.ac = var1;
      var3.yr = var2;
      return var3;
   }

   public InventoryWrapper yt() {
      return this.qg;
   }

   public InventoryWrapper eh() {
      return this.ac;
   }

   public ContainerPassMode xy() {
      return this.yr;
   }
}
