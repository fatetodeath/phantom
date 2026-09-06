package me.phantom.util.entity;

import me.phantom.module.visuals.FreecamModule;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.player.Player;

public class FreecamPlayerClone extends RemotePlayer {
   private final LocalPlayer player = Minecraft.getInstance().player;

   public FreecamPlayerClone(FreecamModule var1) {
      super(Minecraft.getInstance().level, Minecraft.getInstance().player.getGameProfile());
      this.uuid = UUID.randomUUID();
      this.stringUUID = this.uuid.toString();
      this.copyPosition(this.player);
      this.yHeadRot = this.player.yHeadRot;
      this.yBodyRot = this.player.yBodyRot;
      this.getInventory().replaceWith(this.player.getInventory());
       // PLAYER_MODEL_PARTS removed in 1.21.11
   }
}
