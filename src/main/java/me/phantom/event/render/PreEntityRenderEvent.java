package me.phantom.event.render;

import me.phantom.event.packet.AbstractPacketEvent;
import me.phantom.util.PerThreadObjectPool;

import net.minecraft.world.entity.Entity;

public class PreEntityRenderEvent extends AbstractPacketEvent {
   public static PerThreadObjectPool<PreEntityRenderEvent> pool = new PerThreadObjectPool(() -> new PreEntityRenderEvent());
   public Entity kg;

   public static PreEntityRenderEvent qt(Entity var0) {
      PreEntityRenderEvent var1 = (PreEntityRenderEvent)pool.get();
      var1.setCancelled(false);
      var1.kg = var0;
      return var1;
   }

   public Entity tg() {
      return this.kg;
   }
}
