package me.phantom.mixin.entity;

import me.phantom.util.mc.GameProfileAccessor;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PlayerListEntry.class})
public abstract class PlayerListEntryProfileMixin implements GameProfileAccessor {
   @Mutable
   @Accessor("profile")
   @Override
   public abstract void setGameProfile(GameProfile var1);
}
