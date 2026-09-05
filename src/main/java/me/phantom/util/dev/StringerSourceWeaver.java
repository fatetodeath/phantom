package me.phantom.util.dev;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Stream;

public class StringerSourceWeaver {
   public static void ck(String[] var0) throws Throwable {
      LinkedList var1 = new LinkedList();

      while (!var1.isEmpty()) {
         File var2 = (File)var1.remove();
         if (var2.isDirectory()) {
            if (!var2.getName().equals("helper")) {
               Stream.of(var2.listFiles()).forEach(var1::add);
            }
         } else if (var2.getName().endsWith(".java")) {
            ArrayList var3 = new ArrayList<>(Files.readAllLines(var2.toPath()));

            for (int var4 = 0; var4 < var3.size(); var4++) {
               StringBuilder var5 = new StringBuilder();
               char[] var6 = ((String)var3.get(var4)).toCharArray();
               boolean var7 = false;
               boolean var8 = false;

               for (int var9 = 0; var9 < var6.length; var9++) {
                  char var10 = var6[var9];
                  if (var10 == '"') {
                     if (var7) {
                        if (var6[var9 - 1] != '\\') {
                           var5.append("\")");
                           var7 = false;
                           continue;
                        }
                     } else if (var8) {
                        var8 = false;
                     } else if (var6[var9 - 1] == '(' && (var6[var9 - 1] == 'k' && var6[var9 - 2] == 'o' || var6[var9 - 1] == '$')) {
                        var8 = true;
                     } else {
                        var5.append("Stringer.$(");
                        var7 = true;
                     }
                  }

                  var5.append(var10);
               }

               var3.set(var4, var5.toString());
            }

            Files.write(var2.toPath(), var3);
         }
      }
   }
}
