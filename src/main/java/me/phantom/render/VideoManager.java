package me.phantom.render;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.VideoPlayerWidget;
import me.phantom.util.GlyphTextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

public class VideoManager implements Runnable {
   public final Map<String, VideoPlayerWidget> gxw = new HashMap<>();
   public final List<VideoPlayerWidget> mqy = new ArrayList<>();
   public final List<Integer> zomz = new LinkedList<>();
   public final List<Integer> umlj = new LinkedList<>();

   public VideoManager() {
      Thread var1 = new Thread(this);
      var1.setName(GlyphTextUtil.encode("VideoManager"));
      var1.start();
   }

   public VideoPlayerWidget okh(GuiRenderContext var1, String var2) {
      synchronized (this.gxw) {
         VideoPlayerWidget var4 = this.gxw.computeIfAbsent(var2, var1x -> new VideoPlayerWidget(this, var1x));
         return var4.kl ? null : var4;
      }
   }

   public void jyu(GuiRenderContext var1) {
      synchronized (this.zomz) {
         this.zomz.forEach(GL11::glDeleteTextures);
         this.zomz.clear();
      }

      synchronized (this.umlj) {
         this.umlj.forEach(var1x -> NanoVG.nvgDeleteImage(var1.nvg, var1x));
         this.umlj.clear();
      }
   }

   @Override
   public void run() {
      while (MinecraftClient.getInstance().isRunning()) {
         try {
            Thread.sleep(200L);
         } catch (InterruptedException var4) {
         }

         synchronized (this.gxw) {
            this.gxw.values().removeIf(var0 -> {
               if (System.currentTimeMillis() - var0.urw >= 30000L) {
                  var0.mpj();
                  return true;
               } else {
                  return false;
               }
            });
            this.mqy.clear();
            this.mqy.addAll(this.gxw.values());
         }

         for (VideoPlayerWidget var2 : this.mqy) {
            var2.update();
         }
      }
   }
}
