package me.phantom.render;

import me.phantom.core.PhantomClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class NanoVGFramebuffer {
   public int gsd = 0;
   private int lsb = 0;
   private int con = 0;
   public int nkv = 0;
   private int lnj = 0;
   private int xxb = 0;
   public int rlj = 0;

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
      GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      GL11.glClear(17664);
   }

   public void ipg() {
      GL30.glBindFramebuffer(36160, this.rlj);
   }

   private void keg(int var1, int var2) {
      GlfwContextGuard var3 = new GlfwContextGuard();

      try {
         GLFW.glfwMakeContextCurrent(PhantomClient.uiManager.getContext().contextWindow);
         this.gsd = GL30.glGenFramebuffers();
         GL30.glBindFramebuffer(36160, this.gsd);
         GL13.glActiveTexture(33984);
         this.lsb = GL11.glGenTextures();
         GL11.glBindTexture(3553, this.lsb);
         GL11.glTexImage2D(3553, 0, 32856, var1, var2, 0, 6408, 5121, 0L);
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL30.glFramebufferTexture2D(36160, 36064, 3553, this.lsb, 0);
         this.con = GL30.glGenRenderbuffers();
         GL30.glBindRenderbuffer(36161, this.con);
         GL30.glRenderbufferStorage(36161, 33190, var1, var2);
         GL30.glFramebufferRenderbuffer(36160, 36096, 36161, this.con);
         this.nkv = NanoVGGL3.nvglCreateImageFromHandle(PhantomClient.uiManager.getContext().nvg, this.lsb, var1, var2, 0);
      } catch (Throwable var7) {
         try {
            var3.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
         }

         throw var7;
      }

      var3.close();
   }

   public void qrs() {
      if (this.nkv != 0) {
         NanoVG.nvgDeleteImage(PhantomClient.uiManager.getContext().nvg, this.nkv);
         this.nkv = 0;
      }

      if (this.lsb != 0) {
         GL11.glDeleteTextures(this.lsb);
         this.lsb = 0;
      }

      if (this.con != 0) {
         GL30.glDeleteRenderbuffers(this.con);
         this.con = 0;
      }

      if (this.gsd != 0) {
         GL30.glDeleteFramebuffers(this.gsd);
         this.gsd = 0;
      }
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

   public void drm(DrawContext var1, int var2, int var3, int var4, int var5) {
      Matrix4f var6 = var1.getMatrices().peek().getPositionMatrix();
      RenderLayer var7 = RenderLayer.getGuiTextured(Identifier.of("static", String.valueOf(this.loz())));
      VertexConsumer var8 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers().getBuffer(var7);
      float var9 = 5000.0F;
      var8.vertex(var6, var2, var3, var9).texture(0.0F, 1.0F).color(-1);
      var8.vertex(var6, var2, var5, var9).texture(0.0F, 0.0F).color(-1);
      var8.vertex(var6, var4, var5, var9).texture(1.0F, 0.0F).color(-1);
      var8.vertex(var6, var4, var3, var9).texture(1.0F, 1.0F).color(-1);
   }
}
