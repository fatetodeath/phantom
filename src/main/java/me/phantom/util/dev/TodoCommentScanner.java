package me.phantom.util.dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoCommentScanner {
   public static String[] prk = new String[]{"TEST", "IMPORTANT / PROTECTION", "HIGH", "NORMAL", "LOW"};
   public static Map<String, List<String>> lvo = new HashMap<>();

   private static void zx(File var0) throws Throwable {
      if (var0.isDirectory()) {
         for (File var4 : var0.listFiles()) {
            zx(var4);
         }
      } else if (var0.getName().endsWith(".java") && !var0.getName().equals("Helper.java")) {
         String var8;
         try (BufferedReader var7 = new BufferedReader(new InputStreamReader(new FileInputStream(var0), "utf-8"))) {
            while ((var8 = var7.readLine()) != null) {
               if (var8.contains("TODO") || var8.contains("FIXME")) {
                  String var9 = var8.trim().replace("//", "").replace("\t", "");
                  var9 = var9.substring(var9.indexOf("TODO ") + 5);
                  lvo.computeIfAbsent("NORMAL", var0x -> new ArrayList<>()).add(var0.getName().substring(0, var0.getName().length() - 5) + " - " + var9);
               }
            }
         }
      }
   }

   public static void ck(String[] var0) throws Throwable {
      zx(new File("src"));

      try (PrintStream var1 = new PrintStream("res/todo.txt")) {
         for (String var5 : prk) {
            var1.println();
            var1.println("- " + var5 + " -");
            if (lvo.containsKey(var5)) {
               for (String var7 : lvo.get(var5)) {
                  var1.println(var7);
               }
            }
         }
      }
   }

}
