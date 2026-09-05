package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.build.AutoToolModule;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
public class AutoPearlModule extends InventoryModuleBase {
   public AutoPearlModule() {
      super(ModuleCategory.iua, ClientPlayStyle.zfw, GlyphTextUtil.encode("AutoPearl"));
      this.markBeta();
   }

   @Override
   public void onEnable() {
      ClientPlayerEntity var1 = MinecraftClient.getInstance().player;
      int var2 = var1.getInventory().getSelectedSlot();
      int var3 = AutoToolModule.nn(var0 -> var0.getItem() == Items.ENDER_PEARL ? 1.0F : 0.0F);
      if (var2 == var3 && var1.getMainHandStack().getItem() != Items.ENDER_PEARL) {
         this.setEnabled(false, false);
         PhantomClient.uiManager.notifications().tul("module.autopearl.no_pearl");
      } else {
         MinecraftClient.getInstance().player.getInventory().setSelectedSlot(var3);
         GameInteractionUtil.zgx();
         PhantomClient.scheduleTask(() -> {
            MinecraftClient.getInstance().player.getInventory().setSelectedSlot(var2);
            this.setEnabled(false, false);
         }, 2);
      }
   }

   @Override
   public void onDisable() {
   }
}
