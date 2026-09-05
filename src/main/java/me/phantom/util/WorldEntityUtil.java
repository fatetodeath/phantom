package me.phantom.util;

import me.phantom.core.PhantomClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class WorldEntityUtil {
   public static List<Entity> getEntities() {
      ArrayList<Entity> var0 = new ArrayList<>(1000);

      for (Entity var2 : MinecraftClient.getInstance().world.getEntities()) {
         var0.add(var2);
      }

      return var0;
   }

   public static List<List<Entity>> getPartitionedEntities() {
      List<Entity> var0 = getEntities();
      return !PhantomClient.targetSelector.cgq.isEnabled()
         ? Collections.singletonList(var0)
         : Arrays.asList(var0.stream().filter(var0x -> PhantomClient.targetSelector.tso(var0x)).toList(), var0.stream().filter(var0x -> !PhantomClient.targetSelector.tso(var0x)).toList());
   }
}
