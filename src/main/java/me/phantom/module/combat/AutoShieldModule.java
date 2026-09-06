package me.phantom.module.combat;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.WorldEntityUtil;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
public class AutoShieldModule extends BaseModule {
   public AutoClickerModule oqk;
   private boolean spa;
   public ToggleSetting pt = new ToggleSetting(this, GlyphTextUtil.encode("enemy_block"), true);
   private ToggleSetting zo = new ToggleSetting(this, GlyphTextUtil.encode("enemy_axe"), true);
   private ToggleSetting tdm = new ToggleSetting(this, GlyphTextUtil.encode("frustum_check"), false);

   public AutoShieldModule() {
      super(ModuleCategory.vne, ClientPlayStyle.ats, GlyphTextUtil.encode("AutoBlock"));
   }

   public boolean hbb(boolean var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      ItemStack var3 = var2.getOffhandItem();
      if (!PhantomClient.uiManager.getContext().xfy[0] && !this.oqk.pxy && !(Boolean)this.pt.getValue()) {
         return false;
      } else if (var3 == null || var3.getItem() != Items.SHIELD) {
         return false;
      } else if (!var1) {
         return true;
      } else if ((Boolean)this.tdm.getValue() && !(GameInteractionUtil.getCrosshairEntity() instanceof LivingEntity)) {
         return false;
      } else {
         Vec3 var4 = var2.position();
         double var5 = 3.5;
         Iterator var7 = WorldEntityUtil.getEntities().iterator();

         while (true) {
            if (!var7.hasNext()) {
               return false;
            }

            Entity var8 = (Entity)var7.next();
            if (!(var8 instanceof LocalPlayer) && PhantomClient.targetSelector.csu(var8) && var8 instanceof LivingEntity var9 && ((LivingEntity)var8).deathTime <= 0) {
               Vec3 var10 = var8.position();
               if (!(var4.distanceToSqr(var10) >= Math.pow(var5, 2.0))) {
                  if (!(var9 instanceof Player var11)) {
                     break;
                  }

                  if ((Boolean)this.zo.getValue()) {
                     for (ItemStack var13 : GameInteractionUtil.lqy(var11)) {
                        if (var13 != null && var13.getItem() instanceof AxeItem) {
                           return false;
                        }
                     }
                  }

                  if (var11.isBlocking() || !(Boolean)this.pt.getValue()) {
                     break;
                  }
               }
            }
         }

         return true;
      }
   }

   public void tjy(TickEvent var1) {
      this.spa = this.hbb(true);
   }

   public void sau(KeyStateEvent var1) {
      if (this.spa && (!this.oqk.isEnabled() || !this.oqk.ewe) && var1.by().getName().equals(GlyphTextUtil.plain("key.use"))) {
         var1.ywz(true);
      }
   }
}
