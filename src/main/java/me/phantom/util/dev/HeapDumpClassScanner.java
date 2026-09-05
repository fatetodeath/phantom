package me.phantom.util.dev;

import java.io.File;
import java.io.FileInputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class HeapDumpClassScanner {
   public static int ipf = 0;

   public static void ck(String[] var0) throws Throwable {
      byte[] var1 = new byte[8388608];

      try (FileInputStream var2 = new FileInputStream(new File("C:\\Users\\Buddy\\AppData\\Local\\Temp\\javaw (2).DMP"))) {
         while (var2.read(var1) != -1) {
            gb(var1);
         }
      }

      System.out.println(ipf);
   }

   public static void gb(byte[] var0) {
      byte[] var1 = new byte[]{-54, -2, -70, -66};

      label33:
      for (int var2 = 0; var2 < var0.length - var1.length; var2++) {
         for (int var3 = 0; var3 < var1.length; var3++) {
            if (var0[var2 + var3] != var1[var3]) {
               continue label33;
            }
         }

         try {
            ClassNode var5 = new ClassNode();
            new ClassReader(var0, var2, var0.length - var2).accept(var5, 0);
            if (var5.name.contains("_")) {
               System.out.println(var5.name);
            }

            ipf++;
         } catch (Throwable var4) {
         }
      }
   }
}
