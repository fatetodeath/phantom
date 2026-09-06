package me.phantom.render;

import org.lwjgl.opengl.*;

public final class GlStateSnapshot {
   private int program;
   private int vao;
   private int arrayBuffer;
   private int activeTexture;
   private int texture2D;
   private boolean blend;
   private int blendSrcRgb;
   private int blendDstRgb;
   private int blendSrcAlpha;
   private int blendDstAlpha;
   private boolean depthTest;
   private boolean depthMask;
   private boolean scissorTest;
   private boolean stencilTest;
   private final int[] viewport = new int[4];
   private final int[] scissorBox = new int[4];

   private GlStateSnapshot() {
   }

   public static GlStateSnapshot capture() {
      GlStateSnapshot s = new GlStateSnapshot();
      s.program = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
      s.vao = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING);
      s.arrayBuffer = GL11.glGetInteger(GL30.GL_ARRAY_BUFFER_BINDING);
      s.activeTexture = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      s.texture2D = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
      s.blend = GL11.glIsEnabled(GL11.GL_BLEND);
      s.blendSrcRgb = GL11.glGetInteger(GL14.GL_BLEND_SRC_RGB);
      s.blendDstRgb = GL11.glGetInteger(GL14.GL_BLEND_DST_RGB);
      s.blendSrcAlpha = GL11.glGetInteger(GL14.GL_BLEND_SRC_ALPHA);
      s.blendDstAlpha = GL11.glGetInteger(GL14.GL_BLEND_DST_ALPHA);
      GL13.glActiveTexture(s.activeTexture);
      s.depthTest = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
      s.depthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
      s.scissorTest = GL11.glIsEnabled(GL11.GL_SCISSOR_TEST);
      s.stencilTest = GL11.glIsEnabled(GL11.GL_STENCIL_TEST);
      GL11.glGetIntegerv(GL11.GL_VIEWPORT, s.viewport);
      GL11.glGetIntegerv(GL11.GL_SCISSOR_BOX, s.scissorBox);
      return s;
   }

   public void restore() {
      GL20.glUseProgram(this.program);
      GL30.glBindVertexArray(this.vao);
      GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.arrayBuffer);
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture2D);
      GL13.glActiveTexture(this.activeTexture);
      setEnabled(GL11.GL_BLEND, this.blend);
      GL14.glBlendFuncSeparate(this.blendSrcRgb, this.blendDstRgb, this.blendSrcAlpha, this.blendDstAlpha);
      setEnabled(GL11.GL_DEPTH_TEST, this.depthTest);
      GL11.glDepthMask(this.depthMask);
      setEnabled(GL11.GL_SCISSOR_TEST, this.scissorTest);
      setEnabled(GL11.GL_STENCIL_TEST, this.stencilTest);
      GL11.glViewport(this.viewport[0], this.viewport[1], this.viewport[2], this.viewport[3]);
      GL11.glScissor(this.scissorBox[0], this.scissorBox[1], this.scissorBox[2], this.scissorBox[3]);
   }

   private static void setEnabled(int cap, boolean enabled) {
      if (enabled) {
         GL11.glEnable(cap);
      } else {
         GL11.glDisable(cap);
      }
   }
}
