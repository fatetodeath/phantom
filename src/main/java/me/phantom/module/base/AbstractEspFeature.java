package me.phantom.module.base;

import me.phantom.event.render.PreEntityRenderEvent;
import me.phantom.event.render.RenderAlphaEvent;
import me.phantom.event.render.WorldRenderSceneEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.internal.TeamsModule;
import me.phantom.module.visuals.EspModule;
import me.phantom.setting.CollapsibleSettingsGroup;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.render.ColorSource;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractEspFeature {
   public final String name;
   public final EspModule vsr;
   public CollapsibleSettingsGroup wqp;
   public final ToggleSetting cgw;
   public final ToggleSetting nqd;
   public final ColorSetting qaj;
   public final ColorSetting jjk;
   public final ColorSetting hzq;
   public final ColorSetting mtg;
   public final ToggleSetting epw;
   public final ColorSetting gi;
   public final IntegerSliderSetting ep;

   public AbstractEspFeature(String var1, EspModule var2) {
      this.name = var1;
      this.vsr = var2;
      this.ep = new IntegerSliderSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".opacity"), 0, 60, 100);
      this.ep.translationPrefixOverride = GlyphTextUtil.encode("module.esp.opacity");
      this.qaj = new ColorSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".color"), 0.5F, 1.0F, 1.0F);
      this.qaj.translationPrefixOverride = GlyphTextUtil.encode("module.esp.color");
      this.cgw = new ToggleSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".team_color"), true);
      this.cgw.translationPrefixOverride = GlyphTextUtil.encode("module.esp.team_color");
      this.nqd = new ToggleSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".same_color"), false);
      this.nqd.translationPrefixOverride = GlyphTextUtil.encode("module.esp.same_color");
      this.jjk = new ColorSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".color_mobs"), 0.6F, 1.0F, 1.0F);
      this.jjk.translationPrefixOverride = GlyphTextUtil.encode("module.esp.color_mobs");
      this.hzq = new ColorSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".color_players"), 0.15F, 0.8F, 0.8F);
      this.hzq.translationPrefixOverride = GlyphTextUtil.encode("module.esp.color_players");
      this.mtg = new ColorSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".color_friends"), 0.3F, 1.0F, 1.0F);
      this.mtg.translationPrefixOverride = GlyphTextUtil.encode("module.esp.color_friends");
      this.epw = new ToggleSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".hurt_indicator"), false);
      this.epw.translationPrefixOverride = GlyphTextUtil.encode("module.esp.hurt_indicator");
      this.gi = new ColorSetting(var2, GlyphTextUtil.lowercase(this.name) + GlyphTextUtil.encode(".hurt_color"), 0.0F, 1.0F, 1.0F);
      this.gi.translationPrefixOverride = GlyphTextUtil.encode("module.esp.hurt_color");
      this.jjk.setVisibilitySupplier(() -> !(Boolean)this.nqd.getValue());
      this.hzq.setVisibilitySupplier(() -> !(Boolean)this.nqd.getValue());
      this.mtg.setVisibilitySupplier(() -> !(Boolean)this.nqd.getValue());
      this.gi.setVisibilitySupplier(this.epw::getValue);
   }

   public ColorSource ypg(Entity var1) {
      if ((Boolean)this.epw.getValue() && var1 instanceof LivingEntity var2 && var2.hurtTime > 0) {
         return this.gi;
      } else {
         if ((Boolean)this.cgw.getValue() && this.vsr.onn.isEnabled()) {
            try {
               Color var4 = TeamsModule.getTeamColor(null);
               return ColorSource.zto(var4);
            } catch (Throwable var3) {
            }
         }

         if (this.vsr.gfyy.isEnabled() && this.vsr.gfyy.dll(var1)) {
            return ColorSource.zto(this.vsr.gfyy.imc.nqq());
         } else if ((Boolean)this.nqd.getValue()) {
            return this.qaj;
         } else if (this.vsr.bd.tdv(var1)) {
            return this.mtg;
         } else if (var1 instanceof Player) {
            return this.hzq;
         } else {
            return var1 instanceof LivingEntity ? this.jjk : this.qaj;
         }
      }
   }

   public void cgi(CollapsibleSettingsGroup var1) {
      this.wqp = var1;
   }

   public boolean isEnabled() {
      return (Boolean)this.wqp.getValue();
   }

   public void hvi(GuiRenderContext var1) {
   }

   public void lfc(GuiRenderContext var1) {
   }

   public void uu(GuiRenderContext var1, PoseStack var2) {
   }

   public void je(RenderAlphaEvent var1) {
   }

   public void kow(WorldRenderSceneEvent var1) {
   }

   public boolean nsq() {
      return false;
   }

   public void wji(PreEntityRenderEvent var1) {
   }
}
