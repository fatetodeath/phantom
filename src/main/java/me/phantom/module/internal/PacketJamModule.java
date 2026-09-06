package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.PacketJamParticipant;
import me.phantom.core.PhantomClient;
import me.phantom.event.packet.PacketEvent;
import me.phantom.event.packet.PacketSendEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.render.Render3DUtil;
import me.phantom.util.entity.EntityGhostState;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket.Pos;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket.PosRot;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.Vec3;

public class PacketJamModule extends BaseModule {
   private List<Packet<ClientPacketListener>> fbi = new LinkedList<>();
   public List<Packet<ServerGamePacketListenerImpl>> il = new LinkedList<>();
   public List<PacketJamParticipant> tzle = new ArrayList<>();

   public PacketJamModule() {
      super(ModuleCategory.yy, ClientPlayStyle.zfw, GlyphTextUtil.encode("PacketJam"));
      this.setEnabled(true, true);
   }

   @Override
   public boolean lvr() {
      return false;
   }

   @Override
   public void registerModules() {
      for (BaseModule var4 : PhantomClient.moduleManager.getModules()) {
         if (var4 instanceof PacketJamParticipant var5) {
            this.tzle.add(var5);
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.setEnabled(true, true);
   }

   public void igl(PacketEvent var1) {
      if (this.tzle.stream().anyMatch(var0 -> var0.brz())) {
         this.fbi.add(var1.wmo());
         var1.setCancelled(true);
      }
   }

   public void pe(PacketSendEvent var1) {
      if (this.tzle.stream().anyMatch(var0 -> var0.zmb())) {
         this.il.add(var1.wmo());
         var1.setCancelled(true);
      }
   }

   public void tjy(TickEvent var1) {
      if (Minecraft.getInstance().player != null) {
         if (!this.tzle.stream().anyMatch(var0 -> var0.zmb())) {
            this.col();
         }

         if (!this.tzle.stream().anyMatch(var0 -> var0.brz())) {
            this.feq();
         }
      }
   }

   public void feq() {
      synchronized (this.fbi) {
         this.fbi.forEach(GameInteractionUtil::handlePacketUnsafe);
         this.fbi.clear();
      }
   }

   public void col() {
      synchronized (this.il) {
         this.il.forEach(GameInteractionUtil::sendPacketUnsafe);
         this.il.clear();
      }
   }

   public static void muz(Collection<EntityGhostState> var0, PoseStack var1, float var2) {
      Minecraft var3 = Minecraft.getInstance();
      EntityRenderDispatcher var4 = var3.getEntityRenderDispatcher();
      BufferSource var5 = var3.renderBuffers().bufferSource();
      LocalPlayer var6 = var3.player;
      Vec3 var7 = GameInteractionUtil.getLerpedEyePos(var6, var2);

      try {

synchronized (var0) {
            for (EntityGhostState var10 : var0) {
               Vec3 var11 = var10.ssi.lerp(var10.ref, var2);
               if (Render3DUtil.isPointInFrustum(var11.subtract(var7))) {
                  // TODO: EntityRenderManager.render() API changed in 1.21.11
                  var5.endBatch();
               }
            }
         }
      } finally {
         GlStateManager._enableDepthTest();

}
   }

   public static void wpz(Packet<?> var0, Map<Integer, EntityGhostState> var1) {
      if (var0 instanceof Pos var2) {
         ClientLevel var5 = Minecraft.getInstance().level;
         Entity var6 = var2.getEntity(var5);
         if (var6 != null) {
            EntityGhostState var7 = (EntityGhostState)var1.get(var6.getId());
            if (var7 != null) {
               var7.zd(var2.getXa() / 4096.0, var2.getYa() / 4096.0, var2.getZa() / 4096.0);
            }
         }
      } else if (var0 instanceof PosRot var3) {
         ClientLevel var8 = Minecraft.getInstance().level;
         Entity var10 = var3.getEntity(var8);
         if (var10 != null) {
            EntityGhostState var11 = (EntityGhostState)var1.get(var10.getId());
            if (var11 != null) {
               var11.zd(var3.getXa() / 4096.0, var3.getYa() / 4096.0, var3.getZa() / 4096.0);
            }
         }
      } else if (var0 instanceof ClientboundTeleportEntityPacket var4) {
         EntityGhostState var9 = (EntityGhostState)var1.get(var4.id());
         if (var9 != null) {
            var9.vi(var4.change().deltaMovement().x(), var4.change().deltaMovement().y(), var4.change().deltaMovement().z());
         }
      }
   }

}
