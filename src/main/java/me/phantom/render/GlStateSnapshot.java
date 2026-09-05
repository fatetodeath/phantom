package me.phantom.render;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL31;

public class GlStateSnapshot implements AutoCloseable {
   private final int ghvg = GL31.glGetInteger(35725);
   private final int knp = GL31.glGetInteger(32971);
   private final int zji = GL31.glGetInteger(32970);
   private final boolean qx = GL31.glIsEnabled(2884);
   public final int gtt = GL31.glGetInteger(2885);
   private final int pk = GL31.glGetInteger(2886);
   private final boolean aie = GL31.glIsEnabled(3042);
   private final boolean nkw = GL31.glIsEnabled(2929);
   private final boolean eii = GL31.glIsEnabled(3089);
   public final IntBuffer vfq = BufferUtils.createIntBuffer(4);
   public final int hiv;
   private final int rwd;
   private final int bqx;
   private final int mpr;
   private final int tzy;
   public final int oms;
   public final int bb;
   private final int huy;
   private final int upd;
   private final int ukl;
   private final int gzk;
   public final int wxr;

   public GlStateSnapshot() {
      GL31.glGetIntegerv(3107, this.vfq);
      this.hiv = GL31.glGetInteger(2968);
      this.rwd = GL31.glGetInteger(2964);
      this.bqx = GL31.glGetInteger(2965);
      this.mpr = GL31.glGetInteger(2966);
      this.tzy = GL31.glGetInteger(2962);
      this.oms = GL31.glGetInteger(2967);
      this.bb = GL31.glGetInteger(2963);
      this.huy = GL31.glGetInteger(34016);
      this.upd = GL31.glGetInteger(35368);
      this.ukl = GL31.glGetInteger(34229);
      this.gzk = GL31.glGetInteger(34964);
      this.wxr = GL31.glGetInteger(32873);
   }

   @Override
   public void close() {
      GL31.glUseProgram(this.ghvg);
      GL31.glBlendFunc(this.knp, this.zji);
      if (this.qx) {
         GL31.glEnable(2884);
      } else {
         GL31.glDisable(2884);
      }

      GL31.glCullFace(this.gtt);
      GL31.glFrontFace(this.pk);
      if (this.aie) {
         GL31.glEnable(3042);
      } else {
         GL31.glDisable(3042);
      }

      if (this.nkw) {
         GL31.glEnable(2929);
      } else {
         GL31.glDisable(2929);
      }

      if (this.eii) {
         GL31.glEnable(3089);
      } else {
         GL31.glDisable(3089);
      }

      GL31.glColorMask(this.vfq.get(0) != 0, this.vfq.get(1) != 0, this.vfq.get(2) != 0, this.vfq.get(3) != 0);
      GL31.glStencilMask(this.hiv);
      GL31.glStencilOp(this.rwd, this.bqx, this.mpr);
      GL31.glStencilFunc(this.tzy, this.oms, this.bb);
      GL31.glActiveTexture(this.huy);
      GL31.glBindBuffer(35345, this.upd);
      GL31.glBindVertexArray(this.ukl);
      GL31.glBindBuffer(34962, this.gzk);
      GL31.glBindTexture(3553, this.wxr);
   }
}
