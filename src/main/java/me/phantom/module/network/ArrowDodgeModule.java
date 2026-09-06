package me.phantom.module.network;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.PacketJamParticipant;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.combat.ProjectilePathSimulator;
import me.phantom.util.combat.ProjectileType;
import me.phantom.util.WorldEntityUtil;

import java.awt.Color;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

public class ArrowDodgeModule extends BaseModule implements PacketJamParticipant {
   public IntegerSliderSetting hw = new IntegerSliderSetting(this, GlyphTextUtil.encode("prediction"), 1, 10, 20);
   public IntegerSliderSetting izrf = new IntegerSliderSetting(this, GlyphTextUtil.encode("tolerance"), 0, 75, 100);
   public IntegerSliderSetting dmt = new IntegerSliderSetting(this, GlyphTextUtil.encode("box_opacity"), 0, 40, 100);
   public ColorSetting wtg = new ColorSetting(this, GlyphTextUtil.encode("box_color"), Color.yellow);
   private int uhg = 0;
   private Vec3 utw;
   private AABB pne;

   public ArrowDodgeModule() {
      super(ModuleCategory.jq, ClientPlayStyle.ats, GlyphTextUtil.encode("ArrowDodge"));
      this.wtg.setVisibilitySupplier(() -> (Integer)this.dmt.getValue() > 0);
      this.selectSettings(new String[]{GlyphTextUtil.encode("box_color"), GlyphTextUtil.encode("box_opacity")});
      this.markClipboardHint();
   }

   @Override
   public boolean ma() {
      return this.uhg > 0;
   }

   @Override
   public String getValueSuffix() {
      return String.valueOf(this.uhg);
   }

   @Override
   public void onDisable() {
      this.uhg = 0;
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      if (this.uhg > 0) {
         Render3DUtil.ehw(var1.getMatrices(), Shapes.create(this.pne), this.wtg.nqq(), ((Integer)this.dmt.getValue()).intValue() / 100.0F);
      }
   }

   public void tjy(TickEvent var1) {
      if (this.uhg > 0) {
         this.uhg--;
      } else {
         Minecraft var2 = Minecraft.getInstance();
         ClientLevel var3 = var2.level;
         LocalPlayer var4 = var2.player;

         for (Entity var6 : WorldEntityUtil.getEntities()) {
            if (var6 instanceof Arrow var7) {
               ProjectileType var8 = ProjectileType.qju;
               ProjectilePathSimulator var9 = new ProjectilePathSimulator(var3, var6, var6.position(), var6.position().subtract(var6.xo, var6.yo, var6.zo), var8);
               ProjectileType var10 = ProjectileType.sowp;
               ProjectilePathSimulator var11 = new ProjectilePathSimulator(var3, var4, var4.position(), var4.getDeltaMovement(), var10);
               int var12 = 0;

               while (var12++ <= this.hw.getValue()) {
                  Vec3 var13 = var9.swx();
                  EntityHitResult var14 = GameInteractionUtil.raycastEntities(
                     var3,
                     var6,
                     var13,
                     var13.add(var9.udz()),
                     var9.sq().expandTowards(var9.udz()).inflate(1.0),
                     var3x -> {
                        if (Objects.equals(var4, var3x)) {
                           Vec3 var4x = var11.swx();
                           double var5 = ((Integer)this.izrf.getValue()).intValue() / 100.0F;
                           return new AABB(
                              var4x.x - 0.3 - var5, var4x.y - var5, var4x.z - 0.3 - var5, var4x.x + 0.3 + var5, var4x.y + 1.8 + var5, var4x.z + 0.3 + var5
                           );
                        } else {
                           return null;
                        }
                     }
                  );
                  if (var14 != null && var14.getEntity() != null) {
                     this.uhg = var12 + 2;
                     this.utw = var4.position();
                     this.pne = new AABB(this.utw.x - 0.3, this.utw.y, this.utw.z - 0.3, this.utw.x + 0.3, this.utw.y + 1.8, this.utw.z);
                  }

                  var9.tkm();
                  var11.tkm();
               }
            }
         }
      }
   }

   @Override
   public String getDisplayName() {
      return super.getDisplayName() + (this.uhg > 0 ? GlyphTextUtil.encode("[") + PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.arrowdodge.active")) + GlyphTextUtil.encode("]") : GlyphTextUtil.encode(""));
   }

   @Override
   public boolean brz() {
      return false;
   }

   @Override
   public boolean zmb() {
      return this.isEnabled() && this.uhg > 0;
   }

}
