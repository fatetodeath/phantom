package me.phantom.mixin.world;

import me.phantom.core.PhantomClient;
import me.phantom.event.world.TimeOfDayEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ClientLevel.ClientLevelData.class})
public class ClientWorldPropertiesTimeMixin {
   @Shadow
   private long gameTime;

   @Overwrite
   public long getGameTime() {
      return PhantomClient.initialized ? PhantomClient.eventManager.dispatch(TimeOfDayEvent.mj(this.gameTime)).ou() : this.gameTime;
   }
}
