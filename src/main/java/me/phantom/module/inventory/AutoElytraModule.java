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

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
public class AutoElytraModule extends InventoryModuleBase {
   public AutoArmorModule gzu;
   private final ToggleSetting lvc = new ToggleSetting(this, "disable_on_ground", true);

   public AutoElytraModule() {
      super(ModuleCategory.iua, ClientPlayStyle.zfw, GlyphTextUtil.encode("AutoElytra"));
      this.markBeta();
   }

   @Override
   public void onEnable() {
      LocalPlayer var1 = Minecraft.getInstance().player;
      if (var1.getItemBySlot(EquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
         int var2 = var1.getInventory().getSelectedSlot();
         int var3 = AutoToolModule.nn(var0 -> var0.getItem() == Items.ELYTRA ? 1.0F : 0.0F);
         if (var2 == var3 && var1.getMainHandItem().getItem() != Items.ELYTRA) {
            this.setEnabled(false, false);
            PhantomClient.uiManager.notifications().tul("module.autoelytra.no_elytra");
         } else {
            Minecraft.getInstance().player.getInventory().setSelectedSlot(var3);
            GameInteractionUtil.zgx();
            PhantomClient.scheduleTask(() -> Minecraft.getInstance().player.getInventory().setSelectedSlot(var2), 1);
         }
      }
   }

   @Override
   public void onDisable() {
      this.gzu.ctc();
   }

   public void tjy(TickEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      if ((Boolean)this.lvc.getValue() && var2.onGround() && var2.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
         this.setEnabled(false, false);
      }
   }
}
