package me.phantom.module.inventory;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ContainerKind;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.inventory.ContainerSwitchEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.InventoryWrapper;

import com.google.common.base.Suppliers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class EnderChestViewerModule extends InventoryModuleBase {
   public ToggleSetting bqw = new ToggleSetting(this, GlyphTextUtil.encode("clearworldchange"), true);
   public ItemStack[] lvs = new ItemStack[27];
   private int itc;

   public EnderChestViewerModule() {
      super(ModuleCategory.iua, ClientPlayStyle.ats, GlyphTextUtil.encode("EnderChestViewer"));
      this.xaz.setVisibilitySupplier(Suppliers.ofInstance(false));
      this.las.setVisibilitySupplier(Suppliers.ofInstance(false));
      this.markClipboardHint();
   }

   public final Map<Item, Integer> qpc() {
      if (!this.isEnabled()) {
         return null;
      } else {
         HashMap<Item, Integer> var1 = new HashMap<>();

         for (int var2 = 0; var2 < this.lvs.length; var2++) {
            ItemStack var3 = this.lvs[var2];
            if (var3 != null && var3.getItem() != null && var3.getItem() != Items.AIR) {
               Item var4 = var3.getItem();
               if (var4.getComponents().contains(DataComponentTypes.FOOD)) {
                  var4 = Items.APPLE;
               } else if (var4 instanceof BlockItem) {
                  var4 = Items.STONE;
               }

               var1.put(var4, var1.getOrDefault(var4, 0) + var3.getCount());
            }
         }

         return var1;
      }
   }

   @Override
   public void onEnable() {
      Arrays.fill(this.lvs, null);
   }

   @Override
   public void onDisable() {
      Arrays.fill(this.lvs, null);
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      if (this.itc != var2.getId()) {
         if ((Boolean)this.bqw.getValue()) {
            Arrays.fill(this.lvs, null);
         }

         this.itc = var2.getId();
      }
   }

   public void ee(ContainerSwitchEvent var1) {
      if (var1.yt() != null) {
         InventoryWrapper var2 = var1.yt();
         if (var2.getContainerKind() == ContainerKind.uyr) {
            Arrays.fill(this.lvs, null);

            for (int var3 = 0; var3 < var2.getSize(); var3++) {
               ItemStack var4 = var2.getStack(var3);
               if (var4 != null && !Objects.equals(var4.getItem(), Items.AIR)) {
                  this.lvs[var3] = var4;
               }
            }
         }
      }
   }
}
