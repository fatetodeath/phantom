package me.phantom.event;

import me.phantom.core.PhantomClient;
import me.phantom.event.EventBase;
import me.phantom.event.packet.AbstractPacketEvent;
import me.phantom.event.render.RenderEventBase;
import me.phantom.render.Render3DUtil;
import me.phantom.util.GlyphTextUtil;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventManager {
   private final Map<Class<? extends EventBase>, List<Consumer<EventBase>>> listeners = new HashMap<>();

   public synchronized void subscribe(Class<? extends EventBase> var1, Consumer<EventBase> var2) {
      this.listeners.computeIfAbsent(var1, var0 -> new LinkedList<>()).add(var2);
   }

   public synchronized void unsubscribe(Class<? extends EventBase> var1, Consumer<EventBase> var2) {
      this.listeners.computeIfAbsent(var1, var0 -> new LinkedList<>()).remove(var2);
   }

   public synchronized <T extends EventBase> T dispatch(T var1) {
      if (!PhantomClient.initialized) {
         return (T)var1;
      } else if (!PhantomClient.isInWorld()) {
         return (T)var1;
      } else {
         List<Consumer<EventBase>> var2 = this.listeners.get(var1.getClass());
         if (var2 == null) {
            return (T)var1;
         } else {
            if (var1 instanceof AbstractPacketEvent var3) {
               for (Consumer<EventBase> var5 : var2) {
                  try {
                     var5.accept(var3);
                     if (var3.isCancelled() && var3.stopsWhenCancelled()) {
                        break;
                     }
                  } catch (Throwable var28) {
                     Throwable var6 = var28;
                     if (PhantomClient.traceEnabled) {
                        try (
                           OutputStream var7 = (OutputStream)Class.forName("java.io.ByteArrayOutputStream").getConstructor().newInstance();
                           PrintStream var8 = new PrintStream(var7);
                        ) {
                           var6.printStackTrace(var8);
                           PhantomClient.trace(GlyphTextUtil.encode("EventManager"), GlyphTextUtil.encode("error_cancellable"), GlyphTextUtil.encode("module"), var5, GlyphTextUtil.encode("detail"), var7.toString());
                        } catch (Throwable var27) {
                        }
                     }

                     PhantomClient.clientLogger
                        .xdk(
                           GlyphTextUtil.encode("EventManager"),
                           GlyphTextUtil.encode("Failed to execute event '")
                              + var1.getClass().getSimpleName()
                              + GlyphTextUtil.encode("' for '")
                              + var5.getClass().getSimpleName()
                              + GlyphTextUtil.encode("'"),
                           var28
                        );
                  }
               }
            } else if (var1 instanceof RenderEventBase) {
               for (Consumer<EventBase> var31 : var2) {
                  try {
                     var31.accept(var1);
                     Render3DUtil.jmb("event");
                  } catch (Throwable var25) {
                     Throwable var33 = var25;
                     if (PhantomClient.traceEnabled) {
                        try (
                           OutputStream var35 = (OutputStream)Class.forName("java.io.ByteArrayOutputStream").getConstructor().newInstance();
                           PrintStream var37 = new PrintStream(var35);
                        ) {
                           var33.printStackTrace(var37);
                           PhantomClient.trace(GlyphTextUtil.encode("EventManager"), GlyphTextUtil.encode("error_render"), GlyphTextUtil.encode("module"), var31, GlyphTextUtil.encode("detail"), var35.toString());
                        } catch (Throwable var24) {
                        }
                     }

                     PhantomClient.clientLogger
                        .xdk(
                           GlyphTextUtil.encode("EventManager"),
                           GlyphTextUtil.encode("Failed to execute event '")
                              + var1.getClass().getSimpleName()
                              + GlyphTextUtil.encode("' for '")
                              + var31.getClass().getSimpleName()
                              + GlyphTextUtil.encode("'"),
                           var25
                        );
                  }
               }
            } else {
               for (Consumer<EventBase> var32 : var2) {
                  try {
                     var32.accept(var1);
                  } catch (Throwable var22) {
                     Throwable var34 = var22;
                     if (PhantomClient.traceEnabled) {
                        try (
                           OutputStream var36 = (OutputStream)Class.forName("java.io.ByteArrayOutputStream").getConstructor().newInstance();
                           PrintStream var38 = new PrintStream(var36);
                        ) {
                           var34.printStackTrace(var38);
                           PhantomClient.trace(GlyphTextUtil.encode("EventManager"), GlyphTextUtil.encode("error"), GlyphTextUtil.encode("module"), var32, GlyphTextUtil.encode("detail"), var36.toString());
                        } catch (Throwable var21) {
                        }
                     }

                     PhantomClient.clientLogger
                        .xdk(
                           GlyphTextUtil.encode("EventManager"),
                           GlyphTextUtil.encode("Failed to execute event '")
                              + var1.getClass().getSimpleName()
                              + GlyphTextUtil.encode("' for '")
                              + var32.getClass().getSimpleName()
                              + GlyphTextUtil.encode("'"),
                           var22
                        );
                  }
               }
            }

            return (T)var1;
         }
      }
   }
}
