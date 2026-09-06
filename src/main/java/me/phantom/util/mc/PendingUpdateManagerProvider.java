package me.phantom.util.mc;

import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;

public interface PendingUpdateManagerProvider {
   BlockStatePredictionHandler getPendingUpdateManager();
}
