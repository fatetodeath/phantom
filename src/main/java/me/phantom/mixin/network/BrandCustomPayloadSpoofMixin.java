package me.phantom.mixin.network;

import me.phantom.module.network.ClientSpooferModule;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.BrandCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({BrandCustomPayload.class})
public class BrandCustomPayloadSpoofMixin {
   @Redirect(
      method = {"write"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/PacketByteBuf;writeString(Ljava/lang/String;)Lnet/minecraft/network/PacketByteBuf;"
      )
   )
   public PacketByteBuf getClientModName(PacketByteBuf buf, String value) {
      return buf.writeString(ClientSpooferModule.vme(value));
   }
}
