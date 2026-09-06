package me.phantom.module.network;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.PacketJamParticipant;
import me.phantom.core.PhantomClient;
import me.phantom.event.packet.PacketEvent;
import me.phantom.event.packet.PacketSendEvent;
import me.phantom.event.render.RenderAlphaEvent;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.PacketJamModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.entity.EntityGhostState;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.WorldEntityUtil;

import io.netty.buffer.Unpooled;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.lwjgl.opengl.GL11;
public class BlinkModule extends BaseModule implements PacketJamParticipant {
   public int duh = 0;
   public Vec3 sfyh;
   public ColorSetting owm = new ColorSetting(this, GlyphTextUtil.encode("marker_color"), Color.blue);
   public ToggleSetting whx = new ToggleSetting(this, GlyphTextUtil.encode("stopOnDamage"), false);
   public ToggleSetting rgh = new ToggleSetting(this, GlyphTextUtil.encode("stopOnAttack"), false);
   public ToggleSetting uvgl = new ToggleSetting(this, GlyphTextUtil.encode("entity_renderer"), true);
   public IntegerSliderSetting yzl = new IntegerSliderSetting(this, GlyphTextUtil.encode("entity_opacity"), 5, 40, 100);
   private Map<Integer, EntityGhostState> ghostStates = new HashMap<>();
   public boolean lne;

   public BlinkModule() {
      super(ModuleCategory.jq, ClientPlayStyle.ats, GlyphTextUtil.encode("Blink"));
      this.yzl.setVisibleWhen(this.uvgl);
      this.markClipboardHint();
   }

   @Override
   public boolean brz() {
      return this.isEnabled();
   }

   @Override
   public boolean zmb() {
      return this.isEnabled();
   }

   @Override
   public void onEnable() {
      this.ghostStates.clear();
      this.duh = 0;
      this.sfyh = Minecraft.getInstance().player.position();
   }

   @Override
   public void onDisable() {
      this.ghostStates.clear();
   }

   public void pe(PacketSendEvent var1) {
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this, GlyphTextUtil.encode("send"), var1.wmo());
      }

      if ((Boolean)this.rgh.getValue() && ((Packet<?>)var1.wmo()) instanceof ServerboundInteractPacket var2) {
         FriendlyByteBuf var6 = new FriendlyByteBuf(Unpooled.buffer());
         ServerboundInteractPacket.STREAM_CODEC.encode(var6, var2);
         int var4 = var6.readVarInt();
         int var5 = var6.readVarInt();
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(this, GlyphTextUtil.encode("send_interact"), var4, var5);
         }

         if (var5 == 1) {
            this.setEnabled(false, false);
         }
      }
   }

   public void igl(PacketEvent var1) {
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(this, GlyphTextUtil.encode("send"), var1.wmo());
      }

      if ((Boolean)this.whx.getValue()) {
         LocalPlayer var2 = Minecraft.getInstance().player;
         if (((Packet<?>)var1.wmo()) instanceof ClientboundSetHealthPacket var3 && var3.getHealth() < var2.getHealth()) {
            this.setEnabled(false, false);
         }
      }

      if ((Boolean)this.uvgl.getValue()) {
         synchronized (this.ghostStates) {
            PacketJamModule.wpz(var1.wmo(), this.ghostStates);
         }
      }
   }

   public void tjy(TickEvent var1) {
      if ((Boolean)this.uvgl.getValue()) {
         synchronized (this.ghostStates) {
            this.ghostStates.values().forEach(var0 -> {
               var0.ssi = var0.ref;
               var0.ref = var0.ti;
               var0.kr = var0.hln;
            });

            for (Entity var4 : WorldEntityUtil.getEntities()) {
               int var5 = var4.getId();
               if (!this.ghostStates.containsKey(var5) && !(var4 instanceof LocalPlayer) && var4 instanceof LivingEntity) {
                  this.ghostStates.put(var5, new EntityGhostState(var4));
               }
            }
         }
      }

      if (this.duh++ >= 450) {
         this.setEnabled(false, false);
      }
   }

   public void xsb(RenderAlphaEvent var1) {
      if (this.lne) {
         var1.tj(((Integer)this.yzl.getValue()).intValue() / 100.0F);
         var1.fo(true);
      }
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      try {
         GL11.glDisable(2929);
         Render3DUtil.ehw(var1.getMatrices(), Shapes.create(GameInteractionUtil.createBox(this.sfyh, 0.6F, 1.8F)), this.owm.nqq(), 0.6F);
      } finally {
         GL11.glEnable(2929);
      }

      if ((Boolean)this.uvgl.getValue()) {
         try {
            this.lne = true;
            synchronized (this.ghostStates) {
               PacketJamModule.muz(this.ghostStates.values(), var1.getMatrices(), var1.tickDelta());
            }
         } finally {
            this.lne = false;
         }
      }
   }
}
