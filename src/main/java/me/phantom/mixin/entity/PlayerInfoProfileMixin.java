package me.phantom.mixin.entity;

import me.phantom.util.mc.GameProfileAccessor;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PlayerInfo.class})
public abstract class PlayerInfoProfileMixin implements GameProfileAccessor {
   @Mutable
   @Accessor("profile")
   @Override
   public abstract void setGameProfile(GameProfile var1);
}
