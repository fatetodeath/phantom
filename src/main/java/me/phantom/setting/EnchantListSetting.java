package me.phantom.setting;

import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.screen.EnchantPickerDialog;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Holder;

public class EnchantListSetting extends AbstractSetting<Set<String>> {
   private final Supplier<List<ItemStack>> xcvSupplier;
   private List<ItemStack> xcv;

   public EnchantListSetting(BaseModule var1, String var2, Supplier<List<ItemStack>> var3) {
      super(var1, var2, new HashSet());
      this.setValue(new HashSet());
      this.xcvSupplier = var3;
   }

   private List<ItemStack> getStacks() {
      if (this.xcv == null) {
         this.xcv = this.xcvSupplier.get();
      }
      return this.xcv;
   }

   @Override
   public int getHeight() {
      return 26;
   }

   @Override
   public void fromJson(JsonElement var1) {
      this.getValue().clear();
      JsonArray var2 = (JsonArray)var1;

      for (int var3 = 0; var3 < var2.size(); var3++) {
         this.getValue().add(var2.get(var3).getAsString());
      }
   }

   @Override
   public String getDisplayName() {
      return this.cachedLabel.replace(GlyphTextUtil.encode("$1"), this.getValue().size() + GlyphTextUtil.encode(""));
   }

   @Override
   public JsonElement toJson() {
      JsonArray var1 = new JsonArray();
      this.getValue().forEach(var1::add);
      return var1;
   }

   @Override
   public void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.iut(var3, var4, var2, this.getHeight(), this.getDisplayName(), this.getDescription()) && var1.doj[0]) {
         var1.activeDialog = new EnchantPickerDialog(this, this.getStacks());
         var1.doj[0] = false;
      }
   }

   public boolean oa(ItemStack var1) {
      return var1 == null
         ? false
         : var1.getEnchantments()
            .entrySet()
            .stream()
            .anyMatch(var1x -> this.getValue().contains(((Holder)var1x.getKey()).getRegisteredName()));
   }
}
