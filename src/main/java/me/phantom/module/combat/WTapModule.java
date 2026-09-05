package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.packet.PacketSendEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.FloatRangeSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
@ModuleMetadata(
   x = -20.5,
   y = -60.0,
   z = 5.5
)
public class WTapModule extends BaseModule {
   public final FloatRangeSliderSetting aup = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("duration"), 0.0F, 0.05F, 0.1F, 0.2F);
   public final FloatRangeSliderSetting xaz = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("delay"), 0.0F, 0.1F, 0.25F, 1.5F);
   public final FloatRangeSliderSetting dy = new FloatRangeSliderSetting(this, GlyphTextUtil.encode("range"), 0.5F, 2.5F, 3.5F, 4.0F);
   public final ToggleSetting xmy = new ToggleSetting(this, GlyphTextUtil.encode("stap"), true);
   public VeloCritModule ni;
   public AutoClickerModule oqk;
   public int pks;
   private int gio;

   public WTapModule() {
      super(ModuleCategory.vne, ClientPlayStyle.zfw, GlyphTextUtil.encode("WTap"));
   }

   public void wde(PacketSendEvent var1) throws Throwable {
      Packet var2 = var1.wmo();
      if (var2 instanceof HandSwingC2SPacket) {
         if (!PhantomClient.isScreenClosed() || this.pks > 0 || this.gio > 0) {
            return;
         }

         Entity var3 = GameInteractionUtil.getCrosshairEntity();
         if (var3 == null || !(var3 instanceof LivingEntity) || ((LivingEntity)var3).deathTime > 0) {
            return;
         }

         ClientPlayerEntity var4 = MinecraftClient.getInstance().player;
         ItemStack var5 = var4.getMainHandStack();
         if (var5 == null) {
            return;
         }

         Item var6 = var5.getItem();
         if (!GameInteractionUtil.dwx(var6) && !(var6 instanceof AxeItem)) {
            return;
         }

         float var7 = this.dy.fbz();
         if (var3.getPos().squaredDistanceTo(var4.getPos()) > var7 * var7) {
            return;
         }

         this.pks = this.aup.cah();
      }
   }

   public void tjy(TickEvent var1) {
      if (!this.ni.isEnabled() || !this.ni.ma()) {
         if (this.pks > 0) {
            if (this.pks-- <= 0) {
               this.gio = this.xaz.cah();
            }
         } else if (this.gio > 0) {
            this.gio--;
         }
      }
   }

   @Override
   public boolean ma() {
      return this.pks > 0;
   }

   public void sau(KeyStateEvent var1) {
      if (var1.by().getName().equals(GlyphTextUtil.plain("key.forward")) && this.pks > 0) {
         var1.ywz(false);
      }

      if ((Boolean)this.xmy.getValue() && var1.by().getName().equals(GlyphTextUtil.plain("key.back")) && this.pks > 0) {
         var1.ywz(true);
      }
   }
}
