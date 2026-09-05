package me.phantom.module.network;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.PacketJamParticipant;
import me.phantom.event.packet.PacketEvent;
import me.phantom.event.render.RenderAlphaEvent;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.EntityTargetFilterModule;
import me.phantom.module.internal.PacketJamModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.IntRangeSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.entity.EntityGhostState;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.math.RotationMathUtil;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.opengl.GL11;
public class BackTrackModule extends EntityTargetFilterModule implements PacketJamParticipant {
   public IntRangeSliderSetting svi = new IntRangeSliderSetting(this, GlyphTextUtil.encode("ping"), 1, 250, 500, 1000);
   public IntegerSliderSetting uhw = new IntegerSliderSetting(this, GlyphTextUtil.encode("entity_opacity"), 0, 40, 100);
   public IntegerSliderSetting dmt = new IntegerSliderSetting(this, GlyphTextUtil.encode("box_opacity"), 0, 40, 100);
   public ColorSetting wtg = new ColorSetting(this, GlyphTextUtil.encode("box_color"), Color.red);
   public ToggleSetting hxy = new ToggleSetting(this, GlyphTextUtil.encode("outgoing"), false);
   public Map<Integer, EntityGhostState> ghostStates = new HashMap<>();
   public long jkq;
   public boolean lne = false;

   public BackTrackModule() {
      super(ModuleCategory.jq, ClientPlayStyle.ats, GlyphTextUtil.encode("BackTrack"));
      this.wtg.setVisibilitySupplier(() -> (Integer)this.dmt.getValue() > 0);
      this.selectSettings(new String[]{GlyphTextUtil.encode("entity_opacity"), GlyphTextUtil.encode("box_opacity"), GlyphTextUtil.encode("box_color")});
   }

   @Override
   public boolean ma() {
      return this.jkq != 0L;
   }

   @Override
   public void onDisable() {
      synchronized (this.ghostStates) {
         this.ghostStates.clear();
      }
   }

   @Override
   public boolean brz() {
      return this.isEnabled() && this.jkq != 0L;
   }

   @Override
   public boolean zmb() {
      return this.isEnabled() && this.jkq != 0L && (Boolean)this.hxy.getValue();
   }

   public void igl(PacketEvent var1) {
      if (this.jkq != 0L) {
         synchronized (this.ghostStates) {
            PacketJamModule.wpz(var1.wmo(), this.ghostStates);
         }
      }
   }

   public void xsb(RenderAlphaEvent var1) {
      if (this.lne) {
         var1.tj(((Integer)this.uhw.getValue()).intValue() / 100.0F);
         var1.fo(true);
      }
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      EntityRenderDispatcher var2 = MinecraftClient.getInstance().getEntityRenderDispatcher();
      Immediate var3 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
      float var4 = var1.tickDelta();

      try {
         this.lne = true;
         var2.setRenderShadows(false);
         synchronized (this.ghostStates) {
            for (EntityGhostState var7 : this.ghostStates.values()) {
               if ((Integer)this.uhw.getValue() > 0) {
                  Vec3d var8 = var7.ssi.lerp(var7.ref, var4);
                  var2.render(var7.kg, var8.x, var8.y, var8.z, var4, var1.getMatrices(), var3, var2.getLight(var7.kg, var4));
                  var3.draw();
               }

               if ((Integer)this.dmt.getValue() > 0) {
                  GL11.glDisable(2929);
                  Render3DUtil.ehw(var1.getMatrices(), VoxelShapes.cuboid(var7.epx(var4)), this.wtg.nqq(), ((Integer)this.dmt.getValue()).intValue() / 100.0F);
                  GL11.glEnable(2929);
               }
            }
         }
      } finally {
         this.lne = false;
         var2.setRenderShadows(true);
      }
   }

   public void tjy(TickEvent var1) {
      Vec3d var2 = MinecraftClient.getInstance().player.getPos();
      double var3 = RotationMathUtil.square(3.5);
      if (this.jkq != 0L) {
         synchronized (this.ghostStates) {
            this.ghostStates.values().forEach(var4 -> {
               var4.ssi = var4.ref;
               var4.ref = var4.ti;
               var4.kr = var4.hln;
               if (var4.ref.squaredDistanceTo(var2) < var3) {
                  this.jkq = System.currentTimeMillis() - 1L;
               }
            });
         }

         if (System.currentTimeMillis() > this.jkq) {
            this.jkq = 0L;
            this.xmw();
            synchronized (this.ghostStates) {
               this.ghostStates.clear();
            }
         }
      } else {
         synchronized (this.ghostStates) {
            for (Entity var7 : WorldEntityUtil.getEntities()) {
               if (!(var7 instanceof ClientPlayerEntity) && var7 instanceof LivingEntity && this.csu(var7)) {
                  int var8 = var7.getId();
                  Vec3d var9 = new Vec3d(var7.lastX, var7.lastY, var7.lastZ);
                  Vec3d var10 = var7.getPos();
                  if (var9.squaredDistanceTo(var2) < var3 && var10.squaredDistanceTo(var2) > var3 && !this.ghostStates.containsKey(var8)) {
                     EntityGhostState var11 = new EntityGhostState(var7);
                     this.ghostStates.put(var8, var11);
                  }
               }
            }

            if (!this.ghostStates.isEmpty()) {
               this.jkq = System.currentTimeMillis() + this.svi.koj();
            }
         }
      }
   }
}
