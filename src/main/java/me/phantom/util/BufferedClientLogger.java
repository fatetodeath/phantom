package me.phantom.util;

import me.phantom.core.PhantomClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BufferedClientLogger {
   private final boolean oxn = PhantomClient.isDebugBuild || GlyphTextUtil.encodedEquals(PhantomClient.branch, GlyphTextUtil.encode("dev"));
   public final ByteArrayOutputStream joc;
   public final PrintStream cbi;

   public BufferedClientLogger() {
      try {
         this.joc = (ByteArrayOutputStream)Class.forName("java.io.ByteArrayOutputStream").getConstructor(int.class).newInstance(4194304);
         this.cbi = new PrintStream(this.joc);
      } catch (Throwable var2) {
         throw new RuntimeException(var2);
      }
   }

   public String myaq() {
      return new SimpleDateFormat(GlyphTextUtil.plain("dd.MM.YYYY - HH:mm:ss")).format(new Date());
   }

   public void sdj(String var1, Object var2) {
      String var3 = GlyphTextUtil.plain("%s | [%s:%s] | %s").formatted(this.myaq(), GlyphTextUtil.encode("INFO"), var1, String.valueOf(var2));
      if (this.oxn) {
         System.out.println(GlyphTextUtil.decode(var3));
      } else {
         try {
            this.wfu();
            this.joc.write(var3.getBytes(GlyphTextUtil.plain("UTF-8")));
            this.joc.write(10);
         } catch (IOException var5) {
         }
      }
   }

   public void cim(String var1, Object var2) {
      this.xdk(var1, var2, null);
   }

   public void xdk(String var1, Object var2, Throwable var3) {
      try {
         String var4 = GlyphTextUtil.plain("%s | [%s:%s] | %s").formatted(this.myaq(), GlyphTextUtil.encode("ERROR"), var1, String.valueOf(var2));
         if (this.oxn) {
            System.err.println(GlyphTextUtil.decode(var4));
            if (var3 != null) {
               var3.printStackTrace();
            }
         } else if (this.joc != null) {
            this.wfu();

            try {
               this.joc.write(var4.getBytes(GlyphTextUtil.plain("UTF-8")));
            } catch (IOException var7) {
            }

            try {
               this.joc.write(10);
            } catch (Exception var6) {
            }

            if (var3 != null) {
               var3.printStackTrace(this.cbi);
            }
         }
      } catch (Throwable var8) {
         if (var3 != null) {
            var3.printStackTrace();
         }
      }
   }

   private void wfu() {
      if (this.joc.size() > 4194304) {
         this.joc.reset();
      }
   }

   public void pvp(StringBuilder var1) {
      try {
         var1.append(this.joc.toString());
      } catch (Throwable var3) {
      }
   }

   public void mp(String var1) {
      this.sdj(GlyphTextUtil.encode("Tracepoint"), var1);
   }
}
