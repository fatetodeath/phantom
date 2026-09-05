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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket.MoveRelative;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket.RotateAndMoveRelative;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3d;

public class PacketJamModule extends BaseModule {
   private List<Packet<ClientPlayNetworkHandler>> fbi = new LinkedList<>();
   public List<Packet<ServerPlayNetworkHandler>> il = new LinkedList<>();
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
      if (MinecraftClient.getInstance().player != null) {
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

   public static void muz(Collection<EntityGhostState> var0, MatrixStack var1, float var2) {
      MinecraftClient var3 = MinecraftClient.getInstance();
      EntityRenderDispatcher var4 = var3.getEntityRenderDispatcher();
      Immediate var5 = var3.getBufferBuilders().getEntityVertexConsumers();
      ClientPlayerEntity var6 = var3.player;
      Vec3d var7 = GameInteractionUtil.getLerpedEyePos(var6, var2);

      try {
         var4.setRenderShadows(false);
         synchronized (var0) {
            for (EntityGhostState var10 : var0) {
               Vec3d var11 = var10.ssi.lerp(var10.ref, var2);
               if (Render3DUtil.isPointInFrustum(var11.subtract(var7))) {
                  var4.render(var10.kg, var11.x, var11.y, var11.z, var2, var1, var5, var4.getLight(var10.kg, var2));
                  var5.draw();
               }
            }
         }
      } finally {
         GlStateManager._enableDepthTest();
         var4.setRenderShadows(true);
      }
   }

   public static void wpz(Packet<?> var0, Map<Integer, EntityGhostState> var1) {
      if (var0 instanceof MoveRelative var2) {
         ClientWorld var5 = MinecraftClient.getInstance().world;
         Entity var6 = var2.getEntity(var5);
         if (var6 != null) {
            EntityGhostState var7 = (EntityGhostState)var1.get(var6.getId());
            if (var7 != null) {
               var7.zd(var2.getDeltaX() / 4096.0, var2.getDeltaY() / 4096.0, var2.getDeltaZ() / 4096.0);
            }
         }
      } else if (var0 instanceof RotateAndMoveRelative var3) {
         ClientWorld var8 = MinecraftClient.getInstance().world;
         Entity var10 = var3.getEntity(var8);
         if (var10 != null) {
            EntityGhostState var11 = (EntityGhostState)var1.get(var10.getId());
            if (var11 != null) {
               var11.zd(var3.getDeltaX() / 4096.0, var3.getDeltaY() / 4096.0, var3.getDeltaZ() / 4096.0);
            }
         }
      } else if (var0 instanceof EntityPositionS2CPacket var4) {
         EntityGhostState var9 = (EntityGhostState)var1.get(var4.entityId());
         if (var9 != null) {
            var9.vi(var4.change().deltaMovement().getX(), var4.change().deltaMovement().getY(), var4.change().deltaMovement().getZ());
         }
      }
   }

}
