package me.phantom.gui.screen;

import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.widget.SortSlotPosition;
import me.phantom.module.base.InventoryModuleBase;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.setting.InventorySortSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.inventory.ItemScoreCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.lwjgl.nanovg.NanoVG;

public class InventorySortEditorScreen extends AbstractDialog {
   private static final int vcyn = 48;
   public static final int cdu = 4;
   public static final int idc = 52;
   private final InventorySortSetting mqk;
   public List<SortSlotPosition> drx = new ArrayList<>(44);
   public int sxy;
   public int xtl;
   public SortSlotPosition ult;

   public InventorySortEditorScreen(InventorySortSetting var1) {
      super(GlyphTextUtil.encode("InventorySort"), null);
      this.mqk = var1;
      this.drx.clear();

      for (int var2 = 0; var2 < 4; var2++) {
         this.drx.add(new SortSlotPosition(this, 0, var2 * 52, -1));
      }

      for (int var4 = 0; var4 < 2; var4++) {
         for (int var3 = 0; var3 < 2; var3++) {
            this.drx.add(new SortSlotPosition(this, (4 + var4) * 52 + 26, (var3 + 1) * 52, -1));
         }
      }

      this.drx.add(new SortSlotPosition(this, 390, 78, -1));

      for (int var5 = 0; var5 < 9; var5++) {
         for (int var8 = 0; var8 < 3; var8++) {
            this.drx.add(new SortSlotPosition(this, var5 * 52, 208 + var8 * 52 + 26, var8 * 9 + var5 + 9));
         }

         this.drx.add(new SortSlotPosition(this, var5 * 52, 416, 36 + var5));
      }

      for (SortSlotPosition var9 : this.drx) {
         this.sxy = Math.max(this.sxy, var9.x + 52);
         this.xtl = Math.max(this.xtl, var9.y + 52);
      }

      for (SortSlotPosition var10 : this.drx) {
         this.sxy = Math.max(this.sxy, var10.x + 52);
         this.xtl = Math.max(this.xtl, var10.y + 52);
      }
   }

   @Override
   public void fiv() {
      super.fiv();
   }

   @Override
   public void yww() {
   }

   @Override
   public int vgh(int var1, int var2) {
      return this.sxy + 20;
   }

   @Override
   public int zch(int var1, int var2) {
      return this.xtl + 34 + 16;
   }

   @Override
   public void lrk(GuiRenderContext var1, int var2, int var3, int var4, int var5) {
      this.drx.clear();

      for (int var6 = 0; var6 < 4; var6++) {
         this.drx.add(new SortSlotPosition(this, 0, var6 * 52, -1));
      }

      for (int var22 = 0; var22 < 2; var22++) {
         for (int var7 = 0; var7 < 2; var7++) {
            this.drx.add(new SortSlotPosition(this, (4 + var22) * 52 + 52, var7 * 52 + 26, -1));
         }
      }

      this.drx.add(new SortSlotPosition(this, 390, 52, -1));

      for (int var23 = 0; var23 < 9; var23++) {
         for (int var28 = 0; var28 < 3; var28++) {
            this.drx.add(new SortSlotPosition(this, var23 * 52, 208 + var28 * 52 + 26, var28 * 9 + var23 + 9));
         }

         this.drx.add(new SortSlotPosition(this, var23 * 52, 416, 36 + var23));
      }

      this.drx.add(new SortSlotPosition(this, 208, 156, 45));

      for (SortSlotPosition var29 : this.drx) {
         this.sxy = Math.max(this.sxy, var29.x + 52);
         this.xtl = Math.max(this.xtl, var29.y + 52);
      }

      for (SortSlotPosition var30 : this.drx) {
         this.sxy = Math.max(this.sxy, var30.x + 52);
         this.xtl = Math.max(this.xtl, var30.y + 52);
      }

      ItemScoreCategory[] var26 = (ItemScoreCategory[])this.mqk.getValue();
      ClientPlayerEntity var31 = MinecraftClient.getInstance().player;
      String[] var8 = new String[]{
         GlyphTextUtil.encode("module.inventorysort.copy_current"), GlyphTextUtil.encode("module.inventorysort.clear_all"), GlyphTextUtil.encode("module.inventorysort.reset_default")
      };
      byte var9 = 36;
      short var10 = 200;

      for (int var11 = 0; var11 < var8.length; var11++) {
         if (var31 != null) {
            int var12 = -var10 - 6;
            int var13 = var11 * var9 - var9 / 2;
            var1.translate(var12, var13);
            boolean var14 = var1.isHovered(var4, var5, 0, 0, var10, 28);
            var1.drawButton(var4, var5, PhantomClient.translationManager.ry(var8[var11]), var10, 28, true, var14 ? 1.0F : 0.0F, var14 ? 1.0F : 0.0F);
            var1.translate(-var12, -var13);
            if (var14) {
               var26 = new ItemScoreCategory[var26.length];
               Arrays.fill(var26, ItemScoreCategory.ryh);
               if (var11 == 0) {
                  DefaultedList var15 = var31.getInventory().getMainStacks();

                  for (int var16 = 9; var16 < 36; var16++) {
                     var26[var16] = InventoryModuleBase.getItemCategory((ItemStack)var15.get(var16));
                  }

                  for (int var39 = 0; var39 < 9; var39++) {
                     var26[var39 + 36] = InventoryModuleBase.getItemCategory((ItemStack)var15.get(var39));
                  }

                  var26[45] = InventoryModuleBase.getItemCategory(var31.getOffHandStack());
               } else if (var11 == 1) {
                  Arrays.fill(var26, ItemScoreCategory.um);
               } else if (var11 == 2) {
                  System.arraycopy(this.mqk.getDefaultValue(), 0, var26, 0, var26.length);
               }

               if (var1.doj[0]) {
                  this.mqk.setValue(var26);
               }
            }
         }
      }

      for (SortSlotPosition var34 : this.drx) {
         this.wbd(var34, var34.x, var34.y, var4, var5, var26);
      }

      NanoVG.nvgSave(var1.nvg);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(0, 0, 0, 120, GuiRenderContext.colorScratch));
      NanoVG.nvgBeginPath(var1.nvg);
      NanoVG.nvgRoundedRect(var1.nvg, 77.0F, 22.0F, 130.0F, 204.0F, 5.0F);
      NanoVG.nvgFill(var1.nvg);
      NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(240, 240, 240, 200, GuiRenderContext.colorScratch));
      int var33 = 0;
      int var35 = 0;
      var26 = ItemScoreCategory.qfo();

      for (ItemScoreCategory var40 : var26) {
         int var17 = var33 * var10;
         int var18 = var35 * var9 - var9 / 2;
         if (var40.stack != null) {
            boolean var19 = var1.isHovered(var4, var5, var17 + var2 + 6, var18 + 1, var10 - 2, var9 - 2);
            boolean var20 = false;
            if (this.ult != null) {
               ItemScoreCategory var21 = ((ItemScoreCategory[])this.mqk.getValue())[this.ult.erv];
               if (var21 == null) {
                  var21 = ItemScoreCategory.ryh;
               }

               var20 = var21 == var40;
               NanoVG.nvgFillColor(var1.nvg, var20 ? GuiRenderContext.rgba(30, 170, 40, 220, GuiRenderContext.colorScratch) : GuiRenderContext.rgba(0, 0, 0, 220, GuiRenderContext.colorScratch));
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRoundedRect(var1.nvg, var17 + var2 + 6, var18, var10, var9 - 2, 5.0F);
               NanoVG.nvgFill(var1.nvg);
            }

            NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(15, 15, 15, 220, GuiRenderContext.colorScratch));
            NanoVG.nvgBeginPath(var1.nvg);
            NanoVG.nvgRoundedRect(var1.nvg, var17 + var2 + 6 + 1, var18 + 1, var10 - 2, var9 - 2 - 2, 5.0F);
            NanoVG.nvgFill(var1.nvg);
            if (var19) {
               NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(45, 45, 45, 180, GuiRenderContext.colorScratch));
               NanoVG.nvgBeginPath(var1.nvg);
               NanoVG.nvgRoundedRect(var1.nvg, var17 + var2 + 6 + 1, var18 + 1, var10 - 2, var9 - 2 - 2, 5.0F);
               NanoVG.nvgFill(var1.nvg);
               if (var1.doj[0] && this.ult != null) {
                  ((ItemScoreCategory[])this.mqk.getValue())[this.ult.erv] = var40;
                  var1.doj[0] = false;
               }
            }

            PhantomClient.uiManager.sdd().tzk(var40.stack, var17 + var2 + 10 + 12, var18 + 5, 26.0F, 26.0F, 1.0F);
            GlyphTextUtil.setTextAlign(var1.nvg, 18);
            NanoVG.nvgFillColor(var1.nvg, GuiRenderContext.rgba(215, 215, 215, 220, GuiRenderContext.colorScratch));
            NanoVG.nvgFontSize(var1.nvg, 16.0F);
            NanoVG.nvgFontFace(var1.nvg, InterfaceModule.fontRegular);
            GlyphTextUtil.drawText(var1.nvg, var17 + var2 + 12 + var10 / 2, var18 + 18, PhantomClient.translationManager.ry(GlyphTextUtil.encode("category.name.") + var40.vr()));
            if (++var35 >= 13) {
               var35 = 0;
               var33++;
            }
         }
      }

      if (var1.doj[0]) {
         var1.doj[0] = false;
         this.ult = null;
      }
   }

   public void wbd(SortSlotPosition var1, int var2, int var3, int var4, int var5, ItemScoreCategory[] var6) {
      byte var7 = 10;
      byte var8 = 11;
      GuiRenderContext var9 = PhantomClient.uiManager.getContext();
      boolean var10 = var9.isHovered(var4, var5, var1.x + 2 + var7, var1.y + 2 + var8, 48, 48);
      NanoVG.nvgSave(var9.nvg);
      NanoVG.nvgTranslate(var9.nvg, var1.x + 2 + var7, var1.y + 2 + var8);
      NanoVG.nvgFillColor(
         var9.nvg, Objects.equals(this.ult, var1) ? GuiRenderContext.rgba(40, 190, 30, var10 ? 220 : 120, GuiRenderContext.colorScratch) : GuiRenderContext.rgba(40, 40, 40, var10 ? 220 : 120, GuiRenderContext.colorScratch)
      );
      NanoVG.nvgBeginPath(var9.nvg);
      NanoVG.nvgRoundedRect(var9.nvg, 0.0F, 0.0F, 48.0F, 48.0F, 5.0F);
      NanoVG.nvgFill(var9.nvg);
      NanoVG.nvgRestore(var9.nvg);
      if (var1.erv >= 0) {
         if (var10 && var9.doj[0]) {
            this.ult = var1;
            var9.doj[0] = false;
         }

         ItemScoreCategory var11 = var6[var1.erv];
         if (var11 == null) {
            var11 = ItemScoreCategory.ryh;
         }

         PhantomClient.uiManager.sdd().tzk(var11.stack, var1.x + 2 + var7 + 8 + 1, var1.y + 2 + var8 + 8 + 1, 32.0F, 32.0F, 1.0F);
      }
   }

   @Override
   public void puu(GuiRenderContext var1, float var2) {
   }

   @Override
   public boolean handleKey(GuiRenderContext var1, int var2, int var3, int var4) {
      return super.handleKey(var1, var2, var3, var4);
   }

   @Override
   public void na(char var1, int var2) {
   }

}
