package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.render.Render3DUtil;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ItemCategorySetSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.render.ItemEspMarker;
import me.phantom.util.inventory.ItemScoreCategory;
import me.phantom.util.math.FloatPoint2D;
import me.phantom.util.WorldEntityUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
public class ItemEspModule extends BaseModule {
   public final IntegerSliderSetting rgu = new IntegerSliderSetting(this, GlyphTextUtil.encode("size"), 16, 32, 64);
   public final ColorSetting pmo = new ColorSetting(this, GlyphTextUtil.encode("marknormal"), 0.0F, 1.0F, 1.0F);
   public final ToggleSetting vnx = new ToggleSetting(this, GlyphTextUtil.encode("markbetter"), true);
   public final ColorSetting fkl = new ColorSetting(this, GlyphTextUtil.encode("markcolor"), 0.33F, 1.0F, 1.0F);
   public final ToggleSetting ght = new ToggleSetting(this, GlyphTextUtil.encode("onlybetter"), true);
   public final ToggleSetting lam = new ToggleSetting(this, GlyphTextUtil.encode("showamount"), true);
   public final IntegerSliderSetting ox = new IntegerSliderSetting(this, GlyphTextUtil.encode("glowness"), 0, 60, 100);
   public final IntegerSliderSetting ndn = new IntegerSliderSetting(this, GlyphTextUtil.encode("roundness"), 0, 100, 100);
   public final IntegerSliderSetting ep = new IntegerSliderSetting(this, GlyphTextUtil.encode("opacity"), 0, 75, 100);
   public final ItemCategorySetSetting ige = new ItemCategorySetSetting(this, GlyphTextUtil.encode("items"));
   public final ToggleSetting jsk = new ToggleSetting(this, GlyphTextUtil.encode("group"), true);
   public final FloatSliderSetting mlp = new FloatSliderSetting(this, GlyphTextUtil.encode("cluster_size"), 0.5F, 1.5F, 5.0F);
   public final ToggleSetting ru = new ToggleSetting(this, GlyphTextUtil.encode("cap_items"), true);
   public final IntegerSliderSetting it = new IntegerSliderSetting(this, GlyphTextUtil.encode("max_items"), 0, 200, 1000);
   private final FloatPoint2D utv = new FloatPoint2D();

   public ItemEspModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("ItemESP"));
      this.selectAllSettings();
      this.markClipboardHint();
      this.it.setVisibleWhen(this.ru);
      this.ght.setVisibleWhen(this.vnx);
      this.fkl.setVisibleWhen(this.vnx);
   }

   public void wj(WorldRenderOverlayEvent var1) {
      GuiRenderContext var2 = var1.getContext();
      float var3 = var2.tickDelta;
      LocalPlayer var4 = Minecraft.getInstance().player;
      Vec3 var5 = GameInteractionUtil.getLerpedEyePos(var4, var3);
      float[] var6 = new float[ItemScoreCategory.qfo().length];
      if ((Boolean)this.vnx.getValue()) {
         NonNullList var7 = var4.getInventory().getNonEquipmentItems();
         List var8 = GameInteractionUtil.getArmorAndOffHandStacks(var4);

         for (int var9 = 0; var9 < var7.size() + var8.size(); var9++) {
            ItemStack var10 = var9 >= var7.size() ? (ItemStack)var8.get(var9 - var7.size()) : (ItemStack)var7.get(var9);
            ItemScoreCategory var11 = InventoryModuleBase.getItemCategory(var10);
            var6[var11.getOrderIndex()] = Math.max(var6[var11.getOrderIndex()], var11.jgy.apply(var10));
         }
      }

      Integer var17 = (Integer)this.rgu.getValue();
      LinkedList<ItemEspMarker> var18 = new LinkedList<>();
      Boolean var19 = (Boolean)this.jsk.getValue();
      int var20 = this.ru.getValue() ? (Integer)this.it.getValue() : Integer.MAX_VALUE;

      for (Entity var12 : WorldEntityUtil.getEntities()) {
         if (var12 instanceof ItemEntity var13) {
            ItemStack var14 = var13.getItem();
            if (var14 != null && !var14.isEmpty()) {
               ItemScoreCategory var15 = InventoryModuleBase.getItemCategory(var14);
               if (((Set)this.ige.getValue()).contains(var15)) {
                  boolean var16 = false;
                  if ((Boolean)this.vnx.getValue()) {
                     var16 = var15.jgy.apply(var14) > var6[var15.getOrderIndex()];
                  }

                  if (!(Boolean)this.ght.getValue() || var16) {
                     var18.add(new ItemEspMarker(var14, var14.getCount(), GameInteractionUtil.getLerpedPos(var13, var2.tickDelta), var16));
                     if (var18.size() > var20) {
                        break;
                     }
                  }
               }
            }
         }
      }

      if (var19) {
         var18 = this.udw(var18, ((Float)this.mlp.getValue()).floatValue());
      }

      for (ItemEspMarker var23 : var18) {
         if (Render3DUtil.worldToScreen(var23.getPos().add(0.0, 0.25, 0.0).subtract(var5), this.utv)) {
            this.utv.axn = this.utv.axn - var17 / 2;
            this.utv.oaz = this.utv.oaz - var17 / 2;
            this.yvi(var2, var23.getStack(), var23.getCount(), this.utv, var23.isBetter(), var17);
         }
      }
   }

   public LinkedList<ItemEspMarker> udw(List<ItemEspMarker> var1, double var2) {
      var2 *= var2;
      LinkedList<ItemEspMarker> var4 = new LinkedList<>();
      LinkedList<ItemEspMarker> var5 = new LinkedList<>(var1);

      while (!var5.isEmpty()) {
         ItemEspMarker var6 = (ItemEspMarker)var5.remove(0);
         LinkedList<ItemEspMarker> var7 = new LinkedList<>();
         var7.add(var6);
         Iterator<ItemEspMarker> var8 = var5.iterator();

         while (var8.hasNext()) {
            ItemEspMarker var9 = (ItemEspMarker)var8.next();
            if (ItemStack.isSameItem(var6.getStack(), var9.getStack())
               && Math.pow(var6.getPos().x - var9.getPos().x, 2.0) + Math.pow(var6.getPos().y - var9.getPos().y, 2.0) + Math.pow(var6.getPos().y - var9.getPos().y, 2.0) <= var2) {
               var7.add(var9);
               var8.remove();
            }
         }

         if (var7.size() > 1) {
            float var15 = (float)var7.stream().mapToDouble(var0 -> var0.getPos().x).average().orElse(0.0);
            float var10 = (float)var7.stream().mapToDouble(var0 -> var0.getPos().y).average().orElse(0.0);
            float var11 = (float)var7.stream().mapToDouble(var0 -> var0.getPos().z).average().orElse(0.0);
            int var12 = var7.stream().mapToInt(ItemEspMarker::getCount).sum();
            boolean var13 = var7.stream().anyMatch(ItemEspMarker::isBetter);
            var4.add(new ItemEspMarker(var6.getStack().copy(), var12, new Vec3(var15, var10, var11), var13));
         } else {
            var4.add(var6);
         }
      }

      return var4;
   }

   public void yvi(GuiRenderContext var1, ItemStack var2, int var3, FloatPoint2D var4, boolean var5, int var6) {
      if (var5) {
         NanoVG.nvgFillColor(var1.nvg, this.fkl.xam(((Integer)this.ep.getValue()).intValue() / 100.0F, GuiRenderContext.shadowColor));
      } else {
         NanoVG.nvgFillColor(var1.nvg, this.pmo.xam(((Integer)this.ep.getValue()).intValue() / 100.0F, GuiRenderContext.shadowColor));
      }

      int var7 = (int)(((Integer)this.ox.getValue()).intValue() / 100.0F * 25.0F);
      NVGPaint var8 = NanoVG.nvgBoxGradient(
         var1.nvg,
         var4.axn,
         var4.oaz,
         var6,
         var6,
         (var6 / 2 - 2) * (((Integer)this.ndn.getValue()).intValue() / 100.0F),
         var7,
         GuiRenderContext.shadowColor,
         GuiRenderContext.rgba(0, 0, 0, 0, GuiRenderContext.colorScratch2),
         GuiRenderContext.paintScratch
      );
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRect(var1.nvg, var4.axn - var7, var4.oaz - var7, var6 + var7 * 2, var6 + var7 * 2);
      NanoVG.nvgFillPaint(var1.nvg, var8);
      NanoVG.nvgFill(var1.nvg);
      PhantomClient.uiManager.sdd().tzk(var2, var4.axn, var4.oaz, var6, var6, 1.0F);
      if (var3 > 1) {
         var1.drawTextAuto(var4.axn + var6 / 2, var4.oaz + var6 / 2, String.valueOf(var3), var6 / 2, true, 18, 0.5F, 1.0F);
      }
   }
}
