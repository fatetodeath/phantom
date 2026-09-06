package me.phantom.mixin.render;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Projection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({GameRenderer.class})
public interface GameRendererProjectionAccessor {
    @Accessor("hudProjection")
    Projection phantom$getHudProjection();
}
