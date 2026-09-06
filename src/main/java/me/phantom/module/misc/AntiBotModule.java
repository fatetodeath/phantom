package me.phantom.module.misc;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
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
      } else if (!(var1 instanceof Player)) {
         return false;
      } else if (var1 instanceof LocalPlayer) {
         return false;
      } else {
         Player var2 = (Player)var1;
         if (!(Boolean)this.ezf.getValue() || !Float.isNaN(var2.getHealth()) && !(var2.getHealth() <= 0.0F)) {
            if ((Boolean)this.xjg.getValue() && Minecraft.getInstance().getConnection().getPlayerInfo(var2.getUUID()) == null) {
               return true;
            } else if ((Boolean)this.abw.getValue() && var2.isInvisible()) {
               return true;
            } else {
               if ((Boolean)this.ccw.getValue()) {
                  AABB var3 = var1.getBoundingBox();
                  boolean var4 = Minecraft.getInstance().level.getBlockCollisions(var2, var3.move(0.0, -0.4, 0.0)).iterator().hasNext();
                  if (var4 != var2.onGround()) {
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
