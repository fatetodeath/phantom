package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.core.PhantomClient;
import me.phantom.event.misc.ClipAtLedgeEvent;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.ItemScoreCategory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
@ModuleMetadata(
   x = 21.5,
   y = -60.0,
   z = -4.5
)
public class SafeWalkModule extends BaseModule {
   public final ToggleSetting xut = new ToggleSetting(this, GlyphTextUtil.encode("preventjump"), false);
   public final ToggleSetting ruv = new ToggleSetting(this, GlyphTextUtil.encode("sneakmode"), true);
   public final FloatSliderSetting vhz = new FloatSliderSetting(this, GlyphTextUtil.encode("boundoffset"), 0.1F, 0.6F, 0.8F);
   public final ToggleSetting rkb = new ToggleSetting(this, GlyphTextUtil.encode("autoblock"), true);
   public final ToggleSetting vkz = new ToggleSetting(this, GlyphTextUtil.encode("checksneak"), false);
   public final FloatSliderSetting shw = new FloatSliderSetting(this, GlyphTextUtil.encode("checksneak_treshold"), 0.0F, 0.5F, 1.0F);
   public final ToggleSetting vek = new ToggleSetting(this, GlyphTextUtil.encode("checkblock"), false);
   public final ToggleSetting rww = new ToggleSetting(this, GlyphTextUtil.encode("checkpitch"), false);
   public final IntegerSliderSetting gmq = new IntegerSliderSetting(this, GlyphTextUtil.encode("minpitch"), 30, 40, 80);
   public int bpf = -1;
   public int cwde = 0;
   public boolean rv;

   public SafeWalkModule() {
      super(ModuleCategory.wld, ClientPlayStyle.zfw, GlyphTextUtil.encode("SafeWalk"));
      this.markClipboardHint();
      this.vhz.setVisibilitySupplier(() -> (Boolean)this.ruv.getValue() || (Boolean)this.xut.getValue());
      this.gmq.setVisibleWhen(this.rww);
      this.shw.setVisibleWhen(this.vkz);
   }

   @Override
   public void onEnable() {
      if ((Boolean)this.rkb.getValue()) {
         ClientPlayerEntity var1 = MinecraftClient.getInstance().player;
         int var2 = var1.getInventory().getSelectedSlot();

         for (int var6 : InventoryModuleBase.fzs) {
            int var7 = var2 + var6;
            if (var7 < 9 && var7 >= 0) {
               ItemStack var8 = var1.getInventory().getStack(var7);
               if (var8 != null && var8.getItem() != null && InventoryModuleBase.getItemCategory(var8) == ItemScoreCategory.fom) {
                  this.bpf = var1.getInventory().getSelectedSlot();
                  var1.getInventory().setSelectedSlot(var7);
                  break;
               }
            }
         }
      }
   }

   @Override
   public void onDisable() {
      if (this.bpf != -1 && (Boolean)this.rkb.getValue()) {
         ClientPlayerEntity var1 = MinecraftClient.getInstance().player;
         if (var1 != null) {
            ItemStack var2 = var1.getMainHandStack();
            if (InventoryModuleBase.getItemCategory(var2) == ItemScoreCategory.fom) {
               var1.getInventory().setSelectedSlot(this.bpf);
            }
         }

         this.bpf = -1;
      }
   }

   @Override
   public boolean ma() {
      return this.rv;
   }

   public void tjy(TickEvent var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if ((Boolean)this.vkz.getValue()) {
         boolean var3 = var2.options.sneakKey.isPressed();
         if (var3) {
            this.cwde = (int)((Float)this.shw.getValue() * 20.0F);
         } else {
            if (this.cwde <= 0) {
               this.rv = false;
               return;
            }

            this.cwde--;
         }
      }

      ClientPlayerEntity var7 = var2.player;
      if ((Boolean)this.rww.getValue() && var7.getPitch() < ((Integer)this.gmq.getValue()).intValue()) {
         this.rv = false;
      } else if ((Boolean)this.vek.getValue() && ItemScoreCategory.fom != InventoryModuleBase.getItemCategory(var7.getMainHandStack()) && ItemScoreCategory.fom != InventoryModuleBase.getItemCategory(var7.getOffHandStack())) {
         this.rv = false;
         if (PhantomClient.traceEnabled) {
            PhantomClient.trace(
               GlyphTextUtil.encode("SafeWalk"),
               GlyphTextUtil.encode("CheckBlock failed"),
               var7.getMainHandStack(),
               InventoryModuleBase.getItemCategory(var7.getMainHandStack()),
               var7.getOffHandStack(),
               InventoryModuleBase.getItemCategory(var7.getOffHandStack())
            );
         }
      } else {
         ClientWorld var4 = var2.world;
         float var5 = (Float)this.vhz.getValue();
         Box var6 = var7.getBoundingBox();
         this.rv = var7.isOnGround()
            && (
               !var4.getBlockCollisions(var7, var6.offset(var5, -1.0, 0.0)).iterator().hasNext()
                  || !var4.getBlockCollisions(var7, var6.offset(-var5, -1.0, 0.0)).iterator().hasNext()
                  || !var4.getBlockCollisions(var7, var6.offset(0.0, -1.0, var5)).iterator().hasNext()
                  || !var4.getBlockCollisions(var7, var6.offset(0.0, -1.0, -var5)).iterator().hasNext()
            );
      }
   }

   public void sau(KeyStateEvent var1) {
      if (var1.by().getName().equals(GlyphTextUtil.plain("key.sneak")) && (Boolean)this.ruv.getValue() && this.rv) {
         var1.ywz(true);
      } else if (var1.by().getName().equals(GlyphTextUtil.plain("key.jump")) && (Boolean)this.xut.getValue() && this.rv) {
         var1.ywz(false);
      }
   }

   public void ae(ClipAtLedgeEvent var1) {
      if (this.rv) {
         var1.eq(true);
      }
   }

}
