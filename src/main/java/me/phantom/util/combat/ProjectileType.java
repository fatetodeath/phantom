package me.phantom.util.combat;

import me.phantom.util.GlyphTextUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.phys.Vec3;

public class ProjectileType {
   public static final ProjectileType za = new ProjectileType(0.25F, 0.25F, GlyphTextUtil.encode("Fishing bobber"));
   public static final ProjectileType xxo = new ProjectileType(0.25F, 0.25F, GlyphTextUtil.encode("Potion"));
   public static final ProjectileType wih = new ProjectileType(0.25F, 0.25F, GlyphTextUtil.encode("Exp bottle"));
   public static final ProjectileType olm = new ProjectileType(0.25F, 0.25F, GlyphTextUtil.encode("Ender Pearl"));
   public static final ProjectileType xtu = new ProjectileType(0.25F, 0.25F, GlyphTextUtil.encode("Egg"));
   public static final ProjectileType rci = new ProjectileType(0.25F, 0.25F, GlyphTextUtil.encode("Snowball"));
   public static final ProjectileType voe = new ProjectileType(0.25F, 0.25F, GlyphTextUtil.encode("Fireball"));
   public static final ProjectileType luu = new ProjectileType(0.5F, 0.5F, GlyphTextUtil.encode("Trident"));
   public static final ProjectileType zds = new ProjectileType(0.5F, 0.5F, GlyphTextUtil.encode("Crossbow"));
   public static final ProjectileType qju = new ProjectileType(0.5F, 0.5F, GlyphTextUtil.encode("Arrow"));
   public static final ProjectileType sowp = new ProjectileType(0.6F, 1.8F, GlyphTextUtil.encode("Player"));
   public final float hed;
   public final float hwfy;
   public final String name;

   private ProjectileType(float var1, float var2, String var3) {
      this.hed = var1;
      this.hwfy = var2;
      this.name = var3;
   }

   public float ayy(Player var1) {
      if (this.equals(za)) {
         return 0.7F;
      } else if (this.equals(xxo)) {
         return 0.5F;
      } else if (this.equals(wih)) {
         return 0.7F;
      } else if (this.equals(olm)) {
         return 1.5F;
      } else if (this.equals(xtu)) {
         return 1.5F;
      } else if (this.equals(rci)) {
         return 1.5F;
      } else if (this.equals(voe)) {
         return 0.1F;
      } else if (this.equals(qju)) {
         int var2 = 72000 - var1.getUseItemRemainingTicks();
         return BowItem.getPowerForTime(var2) * 3.0F;
      } else if (this.equals(luu)) {
         return 2.5F + EnchantmentHelper.getTridentSpinAttackStrength(var1.getMainHandItem(), var1) * 0.5F;
      } else if (this.equals(zds)) {
         return 3.15F;
      } else {
         return this.equals(sowp) ? 0.0F : 0.0F;
      }
   }

   public Vec3 kyx(Vec3 var1, Vec3 var2) {
      if (this.equals(za)) {
         return var1.scale(0.99F).add(0.0, -0.03F, 0.0);
      } else if (this.equals(xxo)) {
         return var1.scale(0.99F).add(0.0, -0.03F, 0.0);
      } else if (this.equals(wih)) {
         return var1.scale(0.99F).add(0.0, -0.03F, 0.0);
      } else if (this.equals(olm)) {
         return var1.scale(0.99F).add(0.0, -0.03F, 0.0);
      } else if (this.equals(xtu)) {
         return var1.scale(0.99F).add(0.0, -0.03F, 0.0);
      } else if (this.equals(rci)) {
         return var1.scale(0.99F).add(0.0, -0.03F, 0.0);
      } else if (this.equals(voe)) {
         return var1.add(var2).scale(0.95);
      } else if (this.equals(zds) || this.equals(qju)) {
         return var1.scale(0.99F).add(0.0, -0.05F, 0.0);
      } else if (this.equals(sowp)) {
         return var1.add(var2.multiply(1.0, 0.0, 1.0)).scale(0.98).add(0.92, -0.08, 0.92);
      } else {
         return this.equals(luu) ? var1.scale(0.99F).add(0.0, -0.05F, 0.0) : null;
      }
   }
}
