package me.phantom.event.render;

import me.phantom.event.packet.AbstractPacketEvent;
import me.phantom.util.PerThreadObjectPool;

public class WeatherGradientEvent extends AbstractPacketEvent {
   private static PerThreadObjectPool<WeatherGradientEvent> pool = new PerThreadObjectPool(() -> new WeatherGradientEvent());

   public static WeatherGradientEvent cz() {
      WeatherGradientEvent var0 = (WeatherGradientEvent)pool.get();
      var0.setCancelled(false);
      return var0;
   }
}
