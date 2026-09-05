package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.build.AutoToolModule;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
public class AutoElytraModule extends InventoryModuleBase {
   public AutoArmorModule gzu;
   private final ToggleSetting lvc = new ToggleSetting(this, "disable_on_ground", true);

   public AutoElytraModule() {
      super(ModuleCategory.iua, ClientPlayStyle.zfw, GlyphTextUtil.encode("AutoElytra"));
      this.markBeta();
   }

   @Override
   public void onEnable() {
      ClientPlayerEntity var1 = MinecraftClient.getInstance().player;
      if (var1.getEquippedStack(EquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
         int var2 = var1.getInventory().getSelectedSlot();
         int var3 = AutoToolModule.nn(var0 -> var0.getItem() == Items.ELYTRA ? 1.0F : 0.0F);
         if (var2 == var3 && var1.getMainHandStack().getItem() != Items.ELYTRA) {
            this.setEnabled(false, false);
            PhantomClient.uiManager.notifications().tul("module.autoelytra.no_elytra");
         } else {
            MinecraftClient.getInstance().player.getInventory().setSelectedSlot(var3);
            GameInteractionUtil.zgx();
            PhantomClient.scheduleTask(() -> MinecraftClient.getInstance().player.getInventory().setSelectedSlot(var2), 1);
         }
      }
   }

   @Override
   public void onDisable() {
      this.gzu.ctc();
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      if ((Boolean)this.lvc.getValue() && var2.isOnGround() && var2.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
         this.setEnabled(false, false);
      }
   }
}
