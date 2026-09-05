package me.phantom.mixin.world;

import me.phantom.core.PhantomClient;
import me.phantom.event.world.TimeOfDayEvent;

import net.minecraft.client.world.ClientWorld.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Properties.class})
public class ClientWorldPropertiesTimeMixin {
   @Shadow
   private long timeOfDay;

   @Overwrite(
      aliases = {"getTimeOfDay"}
   )
   public long getTimeOfDay() {
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(TimeOfDayEvent.mj(this.timeOfDay)).ou() : this.timeOfDay;
   }
}
