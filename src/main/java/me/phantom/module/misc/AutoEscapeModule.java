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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

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
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (this.ip) {
         this.ip = false;
         if (var2.currentScreen instanceof GameMenuScreen) {
            var2.getAbuseReportContext().tryShowDraftScreen(var2, var2.currentScreen, () -> {
               boolean var1x = var2.isInSingleplayer();
               ServerInfo var2x = var2.getCurrentServerEntry();
               var2.world.disconnect();
               if (var1x) {
                  var2.disconnect(new MessageScreen(Text.translatable(GlyphTextUtil.encode("menu.savingLevel"))));
               } else {
                  var2.disconnect();
               }

               TitleScreen var3x = new TitleScreen();
               if (var1x) {
                  var2.setScreen(var3x);
               } else if (var2x != null && var2x.isRealm()) {
                  var2.setScreen(new RealmsMainScreen(var3x));
               } else {
                  var2.setScreen(new MultiplayerScreen(var3x));
               }
            }, true);
         }
      } else {
         ClientWorld var3 = MinecraftClient.getInstance().world;
         ClientPlayerEntity var4 = MinecraftClient.getInstance().player;
         if (var4.getHealth() > (Float)this.uy.getValue()) {
            this.wjg = false;
         } else {
            Vec3d var5 = var4.getPos();
            if (!(Boolean)this.jfz.getValue() || this.xtr(var3, var5)) {
               if (!this.wjg) {
                  this.wjg = true;
                  if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Disconnect"))) {
                     GameMenuScreen var6 = new GameMenuScreen(true);
                     var2.setScreen(var6);
                     this.ip = true;
                  } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("/hub"))) {
                     MinecraftClient.getInstance().player.networkHandler.sendChatCommand(GlyphTextUtil.encode("/hub"));
                  } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("NaN Move packet"))) {
                     MinecraftClient.getInstance().player.networkHandler.sendPacket(new PositionAndOnGround(Double.NaN, Double.NaN, Double.NaN, false, false));
                  } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Invalid held item"))) {
                     MinecraftClient.getInstance().player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(new Random().nextBoolean() ? 9 : -1));
                  }
               }
            }
         }
      }
   }

   private boolean xtr(ClientWorld var1, Vec3d var2) {
      double var3 = RotationMathUtil.square(((Float)this.jio.getValue()).floatValue());

      for (Entity var6 : WorldEntityUtil.getEntities()) {
         if (var6 instanceof PlayerEntity && !(var6 instanceof ClientPlayerEntity) && var6.getPos().squaredDistanceTo(var2) <= var3) {
            return true;
         }
      }

      return false;
   }
}
