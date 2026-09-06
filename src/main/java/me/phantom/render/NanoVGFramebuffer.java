package me.phantom.render;

import me.phantom.core.PhantomClient;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.opengl.GlTexture;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class NanoVGFramebuffer {
   public int gsd = 0;
   private int lsb = 0;
   private int con = 0;
   public int nkv = 0;
   private int lnj = 0;
   private int xxb = 0;
   public int rlj = 0;
   private int texWidth;
   private int texHeight;

   public void ysf(int var1, int var2) {
      this.rlj = GL11.glGetInteger(36006);
      if (var1 != this.lnj || var2 != this.xxb) {
         this.qrs();
         this.keg(var1, var2);
         this.lnj = var1;
         this.xxb = var2;
      }

      GL30.glBindFramebuffer(36160, this.gsd);
      GL11.glViewport(0, 0, var1, var2);
   }

   public void ipg() {
      GL30.glBindFramebuffer(36160, this.rlj);
   }

   private void keg(int var1, int var2) {
      this.texWidth = var1;
      this.texHeight = var2;

      Minecraft mc = Minecraft.getInstance();
      RenderTarget target = mc.getMainRenderTarget();
      int mcColorTexId = -1;
      int mcDepthTexId = -1;
      if (target != null) {
         if (target.getColorTexture() instanceof GlTexture colorTexture) {
            mcColorTexId = colorTexture.glId();
         }
         if (target.getDepthTexture() instanceof GlTexture depthTexture) {
            mcDepthTexId = depthTexture.glId();
         }
      }
      if (mcColorTexId == -1) return;

      this.gsd = GL30.glGenFramebuffers();
      GL30.glBindFramebuffer(36160, this.gsd);
      GL30.glFramebufferTexture2D(36160, 36064, 3553, mcColorTexId, 0);
      if (mcDepthTexId != -1) {
         GL30.glFramebufferTexture2D(36160, 36096, 3553, mcDepthTexId, 0);
      }
      this.lsb = mcColorTexId;
   }

   public void qrs() {
      if (this.nkv != 0) {
         NanoVG.nvgDeleteImage(PhantomClient.uiManager.getContext().nvg, this.nkv);
         this.nkv = 0;
      }

      if (this.con != 0) {
         GL30.glDeleteRenderbuffers(this.con);
         this.con = 0;
      }

      if (this.gsd != 0) {
         GL30.glDeleteFramebuffers(this.gsd);
         this.gsd = 0;
      }

      this.lsb = 0;
   }

   public int loz() {
      return this.lsb;
   }

   public int lwe() {
      return this.nkv;
   }

   public int ufs() {
      return this.gsd;
   }

   public void drm(int x1, int y1, int x2, int y2) {
   }
}
