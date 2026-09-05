package me.phantom.util.dev;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Stream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ObfuscatedStringScanner {
   public static void ck(String[] var0) throws Throwable {
      LinkedList var1 = new LinkedList();
      var1.add(new File("bin/main"));
      int var2 = 0;
      int var3 = 0;
      System.out.println("------");
      HashSet var4 = new HashSet();

      try (DataOutputStream var5 = new DataOutputStream(new FileOutputStream(new File("strings.bin")))) {
         while (!var1.isEmpty()) {
            File var6 = (File)var1.remove();
            if (var6.isDirectory()) {
               if (!var6.getName().equals("helper")) {
                  Stream.of(var6.listFiles()).forEach(var1::add);
               }
            } else if (var6.getName().endsWith(".class")) {
               ClassNode var7 = new ClassNode();
               new ClassReader(Files.readAllBytes(var6.toPath())).accept(var7, 0);

               for (MethodNode var9 : var7.methods) {
                  AbstractInsnNode var10 = var9.instructions.getFirst();
                  if (var10 != null) {
                     while (true) {
                        if (var10 instanceof LdcInsnNode var11 && var11.cst instanceof String var12) {
                           var2++;
                           if (!(
                                 var10.getNext() instanceof MethodInsnNode var18
                                    && var18.owner.equals("god/buddy/phantom/Stringer")
                                    && var18.desc.equals("(Ljava/lang/String;)Ljava/lang/String;")
                              )
                              && !(
                                 var10.getNext() instanceof MethodInsnNode var19
                                    && var19.owner.equals("god/buddy/phantom/Stringer")
                                    && var19.desc.equals("(Ljava/lang/String;Ljava/lang/String;)Z")
                              )) {
                              var3++;
                              byte[] var20 = var12.getBytes();
                              System.out.println(var7.name + " = " + var12);
                              var5.writeInt(var20.length);
                              var5.write(var20);
                              var4.add(var12.replace("\r", "").replace("\n", "\\n").trim());
                           }
                        }

                        if ((var10 = var10.getNext()) == null) {
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

      ArrayList var17 = new ArrayList(var4);
      Collections.sort(var17);
      Files.write(new File("excluded_strings.txt").toPath(), var17);
      System.out.println(var3 + " / " + var2);
      System.out.println((int)(100.0F * (var3 / (var2 * 1.0F))));
   }
}
