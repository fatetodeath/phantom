package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.FriendModule;
import me.phantom.setting.NameProtectEntry;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.ChatHudMessagesAccessor;
import me.phantom.util.mc.InGameHudTitleAccessor;
import me.phantom.util.mc.ScoreboardEntryAccessor;
import me.phantom.util.ResourceLoader;
import me.phantom.util.WorldEntityUtil;

import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.client.multiplayer.chat.GuiMessage.Line;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.PlayerScoreEntry;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.chat.contents.PlainTextContents.LiteralContents;
import net.minecraft.util.Mth;
public class NameProtectModule extends BaseModule {
   public static List<String> le = new ArrayList<>(20000);
   public static Set<String> li = new HashSet<>();
   public static NameProtectModule zj;
   public final ToggleSetting ff = new ToggleSetting(this, GlyphTextUtil.encode("protect_mc"), false);
   public StringBuilder mfg = new StringBuilder(4096);
   private char[] fra = new char[4096];
   private boolean ilm;
   private char[][] uwp;
   private char[][] jx;
   private char[][] qgx;
   private char[][] mqo;
   private int imn = 0;
   private int zbd = 0;
   public FriendModule bd;

   public NameProtectModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.or, GlyphTextUtil.encode("Nameprotect"));
      this.hiddenFromList = true;
      this.setEnabled(true, true);
      this.markClipboardHint();
      zj = this;
   }

   @Override
   public boolean supportsHoldMode() {
      return false;
   }

   @Override
   public void onEnable() {
      this.hhk();
   }

   @Override
   public void onDisable() {
      this.uwp = new char[0][];
      this.qgx = new char[0][];
   }

   public void tjy(TickEvent var1) {
      Minecraft var2 = Minecraft.getInstance();
      ClientPacketListener var3 = var2.getConnection();

      for (PlayerInfo var6 : var3.getOnlinePlayers()) {
         jue(var6);
      }
   }

   @Override
   public void loadConfig(JsonObject var1, boolean var2, boolean var3, boolean var4) {
      super.loadConfig(var1, var2, var3, var4);
      if (this.isEnabled()) {
         this.hhk();
      }
   }

   public void hhk() {
      Map<String, NameProtectEntry> var1 = this.bd.pd.getValue();
      ArrayList<char[]> var2 = new ArrayList<>(var1.size() + 1);
      char[] var3 = Minecraft.getInstance().getGameProfile().name().toCharArray();
      if (var3.length >= 2) {
         var2.add(var3);
      }

      for (NameProtectEntry var5 : var1.values()) {
         if (var5.rxs.length() >= 2 && var5.iwg.length() >= 2) {
            var2.add(GlyphTextUtil.decode(var5.rxs).toCharArray());
         }
      }

      int var20 = 0;

      for (int var21 = 0; var21 < var2.size(); var21++) {
         int var6 = ((char[])var2.get(var21)).length;
         if (var21 == 0 || var20 > var6) {
            var20 = var6;
         }
      }

      this.imn = var20;
      this.jx = var2.toArray(char[][]::new);
      this.uwp = new char[this.jx.length][];
      this.qgx = new char[this.jx.length][];
      this.mqo = new char[this.jx.length][];
      int var22 = 0;

      for (int var23 = 0; var23 < this.jx.length; var23++) {
         String var7 = new String(this.jx[var23]);
         this.uwp[var23] = var7.toLowerCase().toCharArray();
         NameProtectEntry var8 = (NameProtectEntry)var1.get(var7.toLowerCase());
         if (var8 == null) {
            this.bd.pd.swh(var7);
            var8 = (NameProtectEntry)var1.get(var7.toLowerCase());
         }

         String var9 = GlyphTextUtil.decode(var8.iwg);
         this.qgx[var23] = var9.toCharArray();
         this.mqo[var23] = var9.toLowerCase().toCharArray();
         int var10 = var9.length();
         if (var23 == 0 || var22 > var10) {
            var22 = var10;
         }
      }

      this.zbd = var22;
      Minecraft var24 = Minecraft.getInstance();
      ClientPacketListener var25 = var24.getConnection();
      if (var25 != null) {
         ChatHudMessagesAccessor var26 = (ChatHudMessagesAccessor)var24.gui.getChat();
         ClientLevel var27 = var24.level;
         InGameHudTitleAccessor var28 = (InGameHudTitleAccessor)var24.gui;
         List var11 = var26.getMessages();
         List var12 = var26.getVisibleMessages();

         for (Entity var14 : WorldEntityUtil.getEntities()) {
            if (var14.getCustomName() != null) {
               var14.setCustomName((Component)jue(var14.getCustomName()));
            }
         }

         ArrayList var29 = new ArrayList(var12.size());

         for (int var30 = var11.size() - 1; var30 >= 0; var30--) {
            GuiMessage var15 = (GuiMessage)jue(var11.get(var30));
            var11.set(var30, var15);
            int var16 = Mth.floor(ChatComponent.getWidth(Minecraft.getInstance().options.chatScale().get()));
            if (var15.tag() != null && var15.tag().icon() != null) {
               var16 -= var15.tag().icon().width + 4 + 2;
            }

            List var17 = ComponentRenderUtils.wrapComponents(var15.content(), var16, var24.font);

            for (int var18 = 0; var18 < var17.size(); var18++) {
               FormattedCharSequence var19 = (FormattedCharSequence)var17.get(var18);
               var29.add(0, new Line(var15, var19, var18 == var17.size() - 1));
            }

            while (var29.size() > 100) {
               var29.remove(var29.size() - 1);
            }
         }

         var12.clear();
         var12.addAll(var29);
         var28.setTitle_((Component)jue(var28.getTitle()));
         var28.setSubtitle_((Component)jue(var28.getSubtitle()));

         for (PlayerInfo var33 : var25.getOnlinePlayers()) {
            jue(var33);
         }

         jue(var27.getScoreboard());
      }
   }

   public String ssv(String var1) {
      return this.tbg(this.mqo, this.jx, this.zbd, var1);
   }

   public String tsp(String var1) {
      return this.tbg(this.uwp, this.qgx, this.imn, var1);
   }

   private synchronized String tbg(char[][] var1, char[][] var2, int var3, String var4) {
      try {
         char[] var19 = var4.toCharArray();
         int var20 = var19.length;
         if (var20 < var3 - 1) {
            return var4;
         } else {
            int var22 = Math.min(var20, var20 - var3 + 1);
            int var8 = var1.length;
            char[] var9 = this.fra;
            if (var9.length < var19.length) {
               this.fra = var9 = new char[var19.length + 64];
            }

            for (int var10 = 0; var10 < var19.length; var10++) {
               char var11 = var19[var10];
               var9[var10] = (char)(var11 >= 'A' && var11 <= 'Z' ? var11 - 'A' + 97 : var11);
            }

            StringBuilder var23 = this.mfg;
            var23.ensureCapacity(var22 + 64);

            int var24;
            label97:
            for (var24 = 0; var24 < var22; var24++) {
               char var12 = var9[var24];

               label94:
               for (int var13 = 0; var13 < var8; var13++) {
                  char[] var14 = var1[var13];
                  if (var14[0] == var12) {
                     int var15 = var14.length;

                     for (int var16 = 1; var16 < var15; var16++) {
                        if (var9[var24 + var16] != var14[var16]) {
                           continue label94;
                        }
                     }

                     var23.append(var2[var13]);
                     var24 += var15 - 1;
                     continue label97;
                  }
               }

               var23.append(var19[var24]);
            }

            while (var24 < var19.length) {
               var23.append(var19[var24]);
               var24++;
            }

            String var25 = var23.toString();
            var23.setLength(0);
            return var25;
         }
      } catch (Throwable var17) {
         StringBuilder var6 = new StringBuilder(GlyphTextUtil.encode("Failed. Switching to alternate mode\n"));
         var6.append(GlyphTextUtil.encode("Input text: '")).append(var4).append(GlyphTextUtil.encode("' (")).append(Arrays.toString(var4.getBytes())).append(GlyphTextUtil.encode(")\n"));
         var6.append(GlyphTextUtil.encode("smallestName = ")).append(var3).append(GlyphTextUtil.encode("\n"));
         var6.append(GlyphTextUtil.encode("nameprotect.length = ")).append(var1.length).append(GlyphTextUtil.encode("\n"));

         for (int var7 = 0; var7 < this.uwp.length; var7++) {
            var6.append(GlyphTextUtil.encode("nameprotect["))
               .append(var7)
               .append(GlyphTextUtil.encode("] = "))
               .append(var1.length)
               .append(GlyphTextUtil.encode(" ("))
               .append(Arrays.toString(var1[var7]))
               .append(GlyphTextUtil.encode(")\n"));
         }

         var6.append(GlyphTextUtil.encode("replacement.length = ")).append(var2.length).append(GlyphTextUtil.encode("\n"));

         for (int var21 = 0; var21 < this.qgx.length; var21++) {
            var6.append(GlyphTextUtil.encode("replacement["))
               .append(var21)
               .append(GlyphTextUtil.encode("] = "))
               .append(var2.length)
               .append(GlyphTextUtil.encode(" ("))
               .append(Arrays.toString(var2[var21]))
               .append(GlyphTextUtil.encode(")\n"));
         }

         PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("Nameprotect"), var6.toString(), var17);
         if (!this.ilm) {
            PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("nametag.error"));
            this.ilm = true;
         }

         var4 = var4.toLowerCase();

         for (int var5 = 0; var5 < var1.length; var5++) {
            var4 = var4.replace(new String(var1[var5]), new String(var2[var5]));
         }

         return var4;
      }
   }

   public static Object jue(Object var0) {
      try {
         if (zj != null && zj.isEnabled()) {
            if (zj.uwp == null) {
               zj.hhk();
            }

            if (var0 == null) {
               return null;
            } else if (var0 instanceof String var1) {
               return zj.tsp(var1);
            } else if (var0 instanceof LiteralContents var2) {
               return PlainTextContents.create((String)jue(var2.text()));
            } else if (var0 instanceof PlayerInfo var3) {
               var3.setTabListDisplayName((Component)jue(var3.getTabListDisplayName()));
               if (var3.getTabListDisplayName() == null) {
                  var3.setTabListDisplayName(Component.literal((String)jue(var3.getProfile().name())));
               }

               return var3;
            } else if (var0 instanceof Scoreboard var4) {
               for (Objective var19 : var4.getObjectives()) {
                  var19.setDisplayName((Component)jue(var19.getDisplayName()));

                   for (PlayerScoreEntry var23 : var4.listPlayerScores(var19)) {
                      ((ScoreboardEntryAccessor)(Object)var23).setOwner((String)jue(var23.owner()));
                      ((ScoreboardEntryAccessor)(Object)var23).setDisplay((Component)jue(var23.display()));
                   }
               }

               for (PlayerTeam var20 : var4.getPlayerTeams()) {
                  var20.setPlayerPrefix((Component)jue(var20.getPlayerPrefix()));
                  var20.setPlayerSuffix((Component)jue(var20.getPlayerSuffix()));
                  var20.setDisplayName((Component)jue(var20.getDisplayName()));
               }

               return var4;
            } else if (var0 instanceof TranslatableContents var5) {
               Object[] var15 = var5.getArgs();
               Object[] var18 = new Object[var15.length];

               for (int var21 = 0; var21 < var15.length; var21++) {
                  var18[var21] = jue(var15[var21]);
               }

               return new TranslatableContents(var5.getKey(), var5.getFallback(), var18);
            } else if (var0 instanceof Line var6) {
               return var0;
            } else if (var0 instanceof MutableComponent var7) {
               MutableComponent var14 = MutableComponent.create((ComponentContents)jue(var7.getContents()));
               Style var10 = var7.getStyle();
               if (var10.getInsertion() != null) {
                  var10 = var10.withInsertion((String)jue(var10.getInsertion()));
               }

               var14.setStyle(var10);

               for (Component var12 : var7.getSiblings()) {
                  var14.getSiblings().add((Component)jue(var12));
               }

               return var14;
            } else if (var0 instanceof GuiMessage var8) {
               return new GuiMessage(var8.addedTime(), (Component)jue(var8.content()), var8.signature(), var8.source(), var8.tag());
            } else {
               String var9 = var0.getClass().getName();
               if (li.add(var9)) {
                  PhantomClient.clientLogger.sdj(GlyphTextUtil.encode("Nameprotect"), GlyphTextUtil.encode("Unknown type: ") + var9);
               }

               return var0;
            }
         } else {
            return var0;
         }
      } catch (Throwable var13) {
         PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("Nameprotect"), GlyphTextUtil.encode("Failed to protect ") + var0, var13);
         return var0;
      }
   }

   static {
      try {
         for (String var3 : new String(ResourceLoader.ogt(GlyphTextUtil.encode("names/namelist.txt")), StandardCharsets.UTF_8).replace(GlyphTextUtil.encode("\r\n"), GlyphTextUtil.encode("\n")).split(GlyphTextUtil.encode("\n"))) {
            le.add(var3);
         }
      } catch (Throwable var4) {
         PhantomClient.fatalError(GlyphTextUtil.encode("Nameprotect"), GlyphTextUtil.encode("Failed to read namelist"), var4);
      }
   }
}
