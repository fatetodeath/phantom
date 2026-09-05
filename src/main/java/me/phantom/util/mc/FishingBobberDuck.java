package me.phantom.util.mc;

import net.minecraft.entity.player.PlayerEntity;

public interface FishingBobberDuck {
   boolean hasCaughtFish();

   PlayerEntity getPlayerOwner();
}
