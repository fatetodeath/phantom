package me.phantom.render;

import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;

public class GpuTextureWrapper extends AbstractTexture {
   public GpuTextureWrapper(GpuTexture var1) {
      this.texture = var1;
   }
}
