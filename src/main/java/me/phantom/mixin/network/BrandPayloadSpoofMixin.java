package me.phantom.mixin.network;

import me.phantom.module.network.ClientSpooferModule;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.BrandPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({BrandPayload.class})
public class BrandPayloadSpoofMixin {
   @Redirect(
      method = {"write"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/FriendlyByteBuf;writeUtf(Ljava/lang/String;)Lnet/minecraft/network/FriendlyByteBuf;"
      )
   )
   public FriendlyByteBuf getClientModName(FriendlyByteBuf buf, String value) {
      return buf.writeUtf(ClientSpooferModule.vme(value));
   }
}
