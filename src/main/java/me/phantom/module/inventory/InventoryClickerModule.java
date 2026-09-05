package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.UiRenderEvent;
import me.phantom.gui.widget.FocusedSlotProvider;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.IntRangeSliderSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
public class InventoryClickerModule extends BaseModule {
   public final IntRangeSliderSetting ux = new IntRangeSliderSetting(this, GlyphTextUtil.encode("cps"), 1, 5, 10, 100);
   private long bw;

   public InventoryClickerModule() {
      super(ModuleCategory.iua, ClientPlayStyle.or, GlyphTextUtil.encode("InventoryClicker"));
      this.markBeta();
   }

   @Override
   public void onEnable() {
   }

   public void up(UiRenderEvent var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var2.currentScreen instanceof HandledScreen var4 && this.bw <= System.currentTimeMillis()) {
         if (PhantomClient.uiManager.getContext().xfy[0]) {
            FocusedSlotProvider var5 = (FocusedSlotProvider)var4;
            Slot var6 = var5.getFocusedSlot();
            ScreenHandler var7 = var4.getScreenHandler();
            if (var6 != null) {
               var2.interactionManager.clickSlot(var7.syncId, var6.getIndex(), 0, SlotActionType.QUICK_MOVE, var2.player);
            }

            this.bw = System.currentTimeMillis() + (int)(1000.0F / this.ux.koj());
         }
      }
   }
}
