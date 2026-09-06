package me.phantom.render;

import java.util.Objects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public final class ItemEspMarker {
   public final ItemStack stack;
   private final int count;
   public final Vec3 pos;
   private final boolean better;

   public ItemEspMarker(ItemStack stack, int count, Vec3 pos, boolean better) {
      this.stack = stack;
      this.count = count;
      this.pos = pos;
      this.better = better;
   }

   public final String toString() {
      return "ItemEspMarker[stack=" + this.stack + ", count=" + this.count + ", pos=" + this.pos + ", better=" + this.better + "]";
   }

   public final int hashCode() {
      return Objects.hash(this.stack, this.count, this.pos, this.better);
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ItemEspMarker)) {
         return false;
      } else {
         ItemEspMarker var2 = (ItemEspMarker)var1;
         return this.count == var2.count && this.better == var2.better && Objects.equals(this.stack, var2.stack) && Objects.equals(this.pos, var2.pos);
      }
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public int getCount() {
      return this.count;
   }

   public Vec3 getPos() {
      return this.pos;
   }

   public boolean isBetter() {
      return this.better;
   }
}
