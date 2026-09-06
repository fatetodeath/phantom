package me.phantom.mixin.screen;

import me.phantom.util.mc.ScoreboardEntryAccessor;

import net.minecraft.world.scores.PlayerScoreEntry;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PlayerScoreEntry.class})
public abstract class PlayerScoreEntryAccessorMixin implements ScoreboardEntryAccessor {
   @Mutable
   @Accessor("display")
   @Override
   public abstract void setDisplay(Component var1);

   @Mutable
   @Accessor("owner")
   @Override
   public abstract void setOwner(String var1);
}
