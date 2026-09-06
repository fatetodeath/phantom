package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
public class AutoSoupModule extends BaseModule {
   public final FloatSliderSetting uy = new FloatSliderSetting(this, GlyphTextUtil.encode("minhealth"), 0.0F, 5.0F, 9.0F);
   public final ToggleSetting nv = new ToggleSetting(this, GlyphTextUtil.encode("autosoup"), false);
   public final ToggleSetting dt = new ToggleSetting(this, GlyphTextUtil.encode("soupdrop"), true);
   public AimHelperModule ia;
   public int xwb;
   public int rdp;

   public AutoSoupModule() {
      super(ModuleCategory.iua, ClientPlayStyle.zfw, GlyphTextUtil.encode("AutoSoup"));
      this.dt.setVisibleWhen(this.nv);
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
      this.xwb = 0;
   }

   public void tjy(TickEvent var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      if (PhantomClient.isScreenClosed()) {
         switch (this.xwb) {
            case 0:
               if (var2.getHealth() / 2.0F > (Float)this.uy.getValue()) {
                  return;
               }

               int var3 = var2.getInventory().getSelectedSlot();

               for (int var7 : InventoryModuleBase.fzs) {
                  int var8 = var3 + var7;
                  if (var8 < 9 && var8 >= 0) {
                     ItemStack var9 = var2.getInventory().getItem(var8);
                     if (var9 != null) {
                        Item var10 = var9.getItem();
                        if (var10 != null && var10.equals(Items.MUSHROOM_STEW)) {
                           this.rdp = var2.getInventory().getSelectedSlot();
                           this.xwb = 1;
                           var2.getInventory().setSelectedSlot(var8);
                           return;
                        }
                     }
                  }
               }
               break;
            case 1:
               GameInteractionUtil.zgx();
               this.xwb = this.dt.getValue() ? 2 : 3;
               break;
            case 2:
               if ((Boolean)this.dt.getValue()) {
                  GameInteractionUtil.xzn();
               }

               this.xwb = 3;
               break;
            case 3:
               var2.getInventory().setSelectedSlot(this.rdp);
               this.xwb = 0;
         }
      }
   }

}
