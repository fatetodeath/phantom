package me.phantom.gui.widget;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.render.VideoManager;
import me.phantom.util.GlyphTextUtil;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.ByteBufferSeekableByteChannel;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.ColorUtil;
import org.jcodec.scale.Transform;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;

public class VideoPlayerWidget {
   public static final long aoy = 30L;
   public static final long xrq = 33L;
   public final String name;
   public int vcl;
   public FrameGrab dxy;
   public int ixg;
   public int whd;
   public int kizo;
   public int wmh;
   public int lpx;
   public long urw;
   public boolean kl;
   public long zfz;
   public final VideoManager tnd;

   public VideoPlayerWidget(VideoManager var1, String var2) {
      this.tnd = var1;
      this.ixg = -1;
      this.name = var2;
      this.urw = System.currentTimeMillis();
   }

   public void mpj() {
      if (this.wmh != 0) {
         synchronized (this.tnd.zomz) {
            this.tnd.zomz.add(this.lpx);
         }

         synchronized (this.tnd.umlj) {
            this.tnd.umlj.add(this.wmh);
         }
      }

      this.dxy = null;
   }

   public int glp(GuiRenderContext var1) {
      this.urw = System.currentTimeMillis();
      if (this.dxy != null && this.vcl > 0) {
         long var2 = (System.currentTimeMillis() - this.zfz) % (this.vcl * 33L);
         int var4 = (int)Math.min(Math.max(0L, var2 / 33L), (long)(this.vcl - 1));
         if (this.ixg != var4) {
            try {
               this.ixg = var4;
               Picture var5 = this.dxy.getNativeFrame();
               if (var5 == null) {
                  this.dxy.seekToFramePrecise(0);
                  var5 = this.dxy.getNativeFrame();
               }

               if (this.lpx != 0) {
                  GL11.glDeleteTextures(this.lpx);
               }

               if (this.wmh != 0) {
                  NanoVG.nvgDeleteImage(var1.nvg, this.wmh);
               }

               int var6 = var5.getWidth();
               int var7 = var5.getHeight();
               Picture var8 = Picture.createCropped(var6, var7, ColorSpace.RGB, var5.getCrop());
               Transform var9 = ColorUtil.getTransform(var5.getColor(), ColorSpace.RGB);
               var9.transform(var5, var8);
               byte[][] var10 = var8.getData();
               byte[] var11 = var10[0];

               for (int var12 = 0; var12 < var11.length; var12++) {
                  var11[var12] = (byte)(var11[var12] + 128);
               }

               this.lpx = GL11.glGenTextures();
               GL11.glBindTexture(3553, this.lpx);
               GL11.glTexParameterf(3553, 10241, 9729.0F);
               GL11.glTexParameterf(3553, 10240, 9729.0F);
               GL11.glPixelStorei(3315, 0);
               GL11.glTexImage2D(3553, 0, 6407, var6, var7, 0, 6407, 5121, BufferUtils.createByteBuffer(var11.length).put(var11).flip());
               this.wmh = NanoVGGL3.nvglCreateImageFromHandle(var1.nvg, this.lpx, var6, var7, 0);
            } catch (Throwable var13) {
               PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("VideoManager"), GlyphTextUtil.encode("Failed to read frame ") + var4, var13);
            }
         }

         return this.wmh;
      } else {
         return 0;
      }
   }

   public void update() {
      if (!this.kl && System.currentTimeMillis() - this.urw < 15000L) {
         try {
            if (this.dxy == null) {
               ByteBufferSeekableByteChannel var1 = ByteBufferSeekableByteChannel.readFromByteBuffer(NIOUtils.asByteBuffer(PhantomClient.cloudApi.xct(this.name)));
               this.dxy = FrameGrab.createFrameGrab(var1);
               this.vcl = this.dxy.getVideoTrack().getMeta().getTotalFrames();
            }
         } catch (Throwable var2) {
            PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("VideoManager"), GlyphTextUtil.encode("Failed to read video ") + this.name, var2);
            this.kl = true;
         }
      }
   }
}
