package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.WorldEntityUtil;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ServerData;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class AutoEscapeModule extends BaseModule {
   public final ModeSetting modeSetting = new ModeSetting(this, GlyphTextUtil.encode("mode"), GlyphTextUtil.encode("/hub"), GlyphTextUtil.encode("NaN Move packet"), GlyphTextUtil.encode("Invalid held item"));
   public final ToggleSetting jfz = new ToggleSetting(this, GlyphTextUtil.encode("playernear"), true);
   public final FloatSliderSetting jio = new FloatSliderSetting(this, GlyphTextUtil.encode("playernear_dist"), 3.5F, 8.0F, 64.0F);
   public final FloatSliderSetting uy = new FloatSliderSetting(this, GlyphTextUtil.encode("minhealth"), 0.0F, 5.0F, 9.0F);
   public AimHelperModule ia;
   public boolean wjg = false;
   private boolean ip;

   public AutoEscapeModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.bq, GlyphTextUtil.encode("AutoEscape"));
      this.jio.setVisibleWhen(this.jfz);
   }

   @Override
   public void onEnable() {
      this.wjg = false;
   }

   public void tjy(TickEvent var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (this.ip) {
         this.ip = false;
         if (var2.screen instanceof PauseScreen) {
            var2.getReportingContext().draftReportHandled(var2, var2.screen, () -> {
                boolean var1x = var2.isLocalServer();
                ServerData var2x = var2.getCurrentServer();
                var2.disconnectFromWorld(Component.translatable("menu.savingLevel"));

               TitleScreen var3x = new TitleScreen();
               if (var1x) {
                  var2.setScreen(var3x);
               } else if (var2x != null && var2x.isRealm()) {
                  var2.setScreen(new RealmsMainScreen(var3x));
               } else {
                  var2.setScreen(new JoinMultiplayerScreen(var3x));
               }
            }, true);
         }
      } else {
         ClientLevel var3 = Minecraft.getInstance().level;
         LocalPlayer var4 = Minecraft.getInstance().player;
         if (var4.getHealth() > (Float)this.uy.getValue()) {
            this.wjg = false;
         } else {
            Vec3 var5 = var4.position();
            if (!(Boolean)this.jfz.getValue() || this.xtr(var3, var5)) {
               if (!this.wjg) {
                  this.wjg = true;
                  if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Disconnect"))) {
                     PauseScreen var6 = new PauseScreen(true);
                     var2.setScreen(var6);
                     this.ip = true;
                  } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("/hub"))) {
                     Minecraft.getInstance().player.connection.sendCommand(GlyphTextUtil.encode("/hub"));
                  } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("NaN Move packet"))) {
                     Minecraft.getInstance().player.connection.send(new Pos(Double.NaN, Double.NaN, Double.NaN, false, false));
                  } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Invalid held item"))) {
                     Minecraft.getInstance().player.connection.send(new ServerboundSetCarriedItemPacket(new Random().nextBoolean() ? 9 : -1));
                  }
               }
            }
         }
      }
   }

   private boolean xtr(ClientLevel var1, Vec3 var2) {
      double var3 = RotationMathUtil.square(((Float)this.jio.getValue()).floatValue());

      for (Entity var6 : WorldEntityUtil.getEntities()) {
         if (var6 instanceof Player && !(var6 instanceof LocalPlayer) && var6.position().distanceToSqr(var2) <= var3) {
            return true;
         }
      }

      return false;
   }
}
