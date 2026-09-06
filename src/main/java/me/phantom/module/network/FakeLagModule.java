package me.phantom.module.network;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.PacketJamParticipant;
import me.phantom.event.packet.PacketEvent;
import me.phantom.event.render.RenderAlphaEvent;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.internal.PacketJamModule;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.entity.EntityGhostState;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.WorldEntityUtil;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
public class FakeLagModule extends BaseModule implements PacketJamParticipant {
   public IntegerSliderSetting gfg = new IntegerSliderSetting(this, GlyphTextUtil.encode("strength"), 0, 40, 100);
   public ToggleSetting uvgl = new ToggleSetting(this, GlyphTextUtil.encode("entity_renderer"), true);
   public IntegerSliderSetting yzl = new IntegerSliderSetting(this, GlyphTextUtil.encode("entity_opacity"), 5, 40, 100);
   private boolean lne;
   public Map<Integer, EntityGhostState> ghostStates = new HashMap<>();
   public int uxr = 0;
   public boolean ysjd;

   public FakeLagModule() {
      super(ModuleCategory.jq, ClientPlayStyle.bq, GlyphTextUtil.encode("FakeLag"));
      this.yzl.setVisibleWhen(this.uvgl);
   }

   @Override
   public boolean ma() {
      return this.ysjd;
   }

   @Override
   public boolean brz() {
      return this.isEnabled() && this.ysjd;
   }

   @Override
   public boolean zmb() {
      return this.isEnabled() && this.ysjd;
   }

   @Override
   public void onEnable() {
      this.ghostStates.clear();
      this.uxr = 0;
   }

   @Override
   public void onDisable() {
      this.ghostStates.clear();
   }

   public void igl(PacketEvent var1) {
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

      if (this.uxr-- <= 0) {
         this.ysjd = !this.ysjd;
         this.ghostStates.clear();
         float var8 = ((Integer)this.gfg.getValue()).intValue() / 100.0F * 0.8F + 0.2F;
         this.uxr = (int)(Math.random() * (this.ysjd ? var8 : 1.0F - var8) * (this.ysjd ? 10 : 30));
      }
   }

   public void xsb(RenderAlphaEvent var1) {
      if (this.lne) {
         var1.tj(((Integer)this.yzl.getValue()).intValue() / 100.0F);
         var1.fo(true);
      }
   }

   public void vbi(WorldRenderMatricesEvent var1) {
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
