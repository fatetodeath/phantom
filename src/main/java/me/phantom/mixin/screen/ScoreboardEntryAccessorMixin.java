package me.phantom.mixin.screen;

import me.phantom.util.mc.ScoreboardEntryAccessor;

import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ScoreboardEntry.class})
public abstract class ScoreboardEntryAccessorMixin implements ScoreboardEntryAccessor {
   @Mutable
   @Accessor("display")
   @Override
   public abstract void setDisplay(Text var1);

   @Mutable
   @Accessor("owner")
   @Override
   public abstract void setOwner(String var1);
}
