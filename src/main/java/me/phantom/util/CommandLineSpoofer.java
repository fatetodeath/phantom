package me.phantom.util;

import me.phantom.core.PhantomClient;

import com.sun.jna.Memory;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
import java.util.Arrays;
import java.util.stream.Collectors;
import oshi.jna.platform.windows.NtDll;

public class CommandLineSpoofer {
   public static void cyx() {
      if (!Platform.isMac()) {
         try {
            HANDLE var0 = Kernel32.INSTANCE.GetCurrentProcess();
            Memory var1 = new Memory(48L);
            NtDll.INSTANCE.NtQueryInformationProcess(var0, 0, var1, 48, new IntByReference());
            Pointer var2 = var1.getPointer(8L);
            Pointer var3 = var2.getPointer(32L);
            String var4 = Arrays.stream(var3.getPointer(120L).getWideString(0L).split(GlyphTextUtil.encode(" ")))
               .filter(var0x -> !var0x.contains(GlyphTextUtil.encode("-agentlib:jdwp")) && !var0x.contains(GlyphTextUtil.encode("clientlauncher")))
               .collect(Collectors.joining(GlyphTextUtil.encode(" ")));
            var3.setShort(112L, (short)(var4.length() * 2));
            var3.getPointer(120L).setWideString(0L, var4);
         } catch (Throwable var5) {
            PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("CommandLineSpoofer"), GlyphTextUtil.encode("Failed to spoof command line"), var5);
            PhantomClient.uiManager.notifications().klr(GlyphTextUtil.encode("commandlinespoofer.failed"));
         }
      }
   }
}
