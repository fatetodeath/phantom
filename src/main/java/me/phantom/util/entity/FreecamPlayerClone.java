package me.phantom.util.entity;

import me.phantom.module.visuals.FreecamModule;

import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class FreecamPlayerClone extends OtherClientPlayerEntity {
   private final ClientPlayerEntity player = MinecraftClient.getInstance().player;

   public FreecamPlayerClone(FreecamModule var1) {
      super(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getGameProfile());
      this.uuid = UUID.randomUUID();
      this.uuidString = this.uuid.toString();
      this.copyPositionAndRotation(this.player);
      this.headYaw = this.player.headYaw;
      this.bodyYaw = this.player.bodyYaw;
      this.getInventory().clone(this.player.getInventory());
      this.getDataTracker().set(PlayerEntity.PLAYER_MODEL_PARTS, (Byte)this.player.getDataTracker().get(PlayerEntity.PLAYER_MODEL_PARTS));
   }
}
