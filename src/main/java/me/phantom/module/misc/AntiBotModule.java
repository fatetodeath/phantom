package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
public class AntiBotModule extends BaseModule {
   public final ToggleSetting ccw = new ToggleSetting(this, GlyphTextUtil.encode("check_groundstate"), false);
   public final ToggleSetting abw = new ToggleSetting(this, GlyphTextUtil.encode("check_invisble"), true);
   public final ToggleSetting xjg = new ToggleSetting(this, GlyphTextUtil.encode("check_tablist"), true);
   public final ToggleSetting ezf = new ToggleSetting(this, GlyphTextUtil.encode("check_nan"), false);

   public AntiBotModule() {
      super(ModuleCategory.hpi, ClientPlayStyle.or, GlyphTextUtil.encode("AntiBot"));
      this.hiddenFromList = true;
      this.setEnabled(true, false);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.setEnabled(true, false);
   }

   public boolean uei(Entity var1) {
      if (!this.isEnabled()) {
         return false;
      } else if (!(var1 instanceof PlayerEntity)) {
         return false;
      } else if (var1 instanceof ClientPlayerEntity) {
         return false;
      } else {
         PlayerEntity var2 = (PlayerEntity)var1;
         if (!(Boolean)this.ezf.getValue() || !Float.isNaN(var2.getHealth()) && !(var2.getHealth() <= 0.0F)) {
            if ((Boolean)this.xjg.getValue() && MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(var2.getUuid()) == null) {
               return true;
            } else if ((Boolean)this.abw.getValue() && var2.isInvisible()) {
               return true;
            } else {
               if ((Boolean)this.ccw.getValue()) {
                  Box var3 = var1.getBoundingBox();
                  boolean var4 = MinecraftClient.getInstance().world.getBlockCollisions(var2, var3.offset(0.0, -0.4, 0.0)).iterator().hasNext();
                  if (var4 != var2.isOnGround()) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            return true;
         }
      }
   }
}
