package me.phantom.module.network;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.packet.PacketEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.net.PingSample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket.Action;
import net.minecraft.network.protocol.ping.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.ping.ClientboundPongResponsePacket;
public class PingMeasurementModule extends BaseModule {
   public static PingMeasurementModule bsd;
   public ModeSetting modeSetting = new ModeSetting(this, GlyphTextUtil.encode("mode"), GlyphTextUtil.encode("Trusted"), GlyphTextUtil.encode("Untrusted"), GlyphTextUtil.encode("Legacy"));
   public IntegerSliderSetting gmr = new IntegerSliderSetting(this, GlyphTextUtil.encode("newfrequency"), 50, 50, 30000);
   private ToggleSetting egm = new ToggleSetting(this, GlyphTextUtil.encode("showhistory"), true);
   public FloatSliderSetting ixj = new FloatSliderSetting(this, GlyphTextUtil.encode("historyduration"), 0.0F, 10.0F, 30.0F);
   public long wwyy;
   private long knh;
   private long jvp;
   public int aiq;
   private List<PingSample> coe = new ArrayList<>(100);

   public PingMeasurementModule() {
      super(ModuleCategory.jq, ClientPlayStyle.ats, GlyphTextUtil.encode("PingMeasurement"));
      this.ixj.setVisibleWhen(this.egm);
      bsd = this;
   }

   @Override
   public void onEnable() {
      this.coe.clear();
      this.yxj();
   }

   public void tjy(TickEvent var1) {
      this.coe.removeIf(var1x -> (float)(System.currentTimeMillis() - var1x.getTime()) >= (Float)this.ixj.getValue() * 1000.0F);
      if (System.currentTimeMillis() - this.wwyy > ((Integer)this.gmr.getValue()).intValue()) {
         this.yxj();
         this.wwyy = System.currentTimeMillis();
      }
   }

   public void yxj() {
      if (!Minecraft.getInstance().getDebugOverlay().showNetworkCharts()) {
         ClientPacketListener var1 = Minecraft.getInstance().getConnection();
         if (var1 != null) {
            this.jvp = System.currentTimeMillis();
            if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Trusted")) || ((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Untrusted"))) {
               var1.send(new ServerboundPingRequestPacket(System.currentTimeMillis()));
            } else if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Legacy"))) {
               var1.send(new ServerboundClientCommandPacket(Action.REQUEST_STATS));
            }
         }
      }
   }

   @Override
   public String getValueSuffix() {
      return wxa();
   }

   public void igl(PacketEvent var1) {
      if (!Minecraft.getInstance().getDebugOverlay().showNetworkCharts()) {
         Packet var2 = var1.wmo();
         if (!((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Legacy")) && !((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Untrusted"))) {
            if (((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Trusted")) && var2 instanceof ClientboundPongResponsePacket var3) {
               this.pux((int)(System.currentTimeMillis() - var3.time()));
               var1.setCancelled(true);
            }
         } else if ((((String)this.modeSetting.getValue()).equals(GlyphTextUtil.encode("Untrusted")) ? var2 instanceof ClientboundPongResponsePacket : var2 instanceof ClientboundAwardStatsPacket)
            && this.jvp != 0L) {
            this.pux((int)(System.currentTimeMillis() - this.jvp));
            var1.setCancelled(true);
         }
      }
   }

   public void pux(int var1) {
      this.aiq = var1;
      this.knh = System.currentTimeMillis();
      this.jvp = 0L;
      if ((Boolean)this.egm.getValue()) {
         this.coe.add(new PingSample(var1, this.knh));
      } else {
         this.coe.clear();
      }
   }

   public static List<PingSample> xhh() {
      return bsd != null && bsd.isEnabled() ? bsd.coe : Collections.emptyList();
   }

   public static String wxa() {
      try {
         if (bsd != null && bsd.isEnabled() && System.currentTimeMillis() - bsd.knh <= (Integer)bsd.gmr.getValue() * 2) {
            return GlyphTextUtil.encode("") + bsd.aiq;
         }
      } catch (Throwable var2) {
      }

      try {
         return GlyphTextUtil.encode("")
            + Minecraft.getInstance().getConnection().getPlayerInfo(Minecraft.getInstance().getGameProfile().id()).getLatency();
      } catch (Throwable var1) {
         return GlyphTextUtil.encode("-");
      }
   }
}
