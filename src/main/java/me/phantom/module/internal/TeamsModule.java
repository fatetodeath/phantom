package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;

public class TeamsModule extends BaseModule {
   public static TeamsModule iue;

   public TeamsModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("Teams"));
      iue = this;
   }

   public static boolean sameTeam(Player var0) {
      if (iue != null && iue.isEnabled()) {
         try {
            Color var1 = getTeamColor(Minecraft.getInstance().player.getDisplayName());
            Color var2 = getTeamColor(var0.getDisplayName());
            return var1 != null && var2 != null && var1.equals(var2);
         } catch (Throwable var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static Color getTeamColor(Component var0) {
      HashMap<Color, Integer> var1 = new HashMap<>();
      var0.visit(
         (var1x, var2x) -> {
            if (var1x.getColor() != null) {
               Color var3 = new Color(var1x.getColor().getValue());
               Color var4 = new Color(
                  (int)(Math.ceil(var3.getRed() / 10) * 10.0), (int)(Math.ceil(var3.getGreen() / 10) * 10.0), (int)(Math.ceil(var3.getBlue() / 10) * 10.0)
               );
               var1.put(var4, var1.getOrDefault(var4, 0) + 1);
            }

            return Optional.empty();
         },
         Style.EMPTY
      );
      Color var2 = var1.entrySet().stream().max(Entry.comparingByValue()).get().getKey();
      return var2 == Color.WHITE ? null : var2;
   }
}
