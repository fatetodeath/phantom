package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.packet.PacketSendEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.hud.HudAnchor;
import me.phantom.gui.hud.HudElementBase;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.HudElementsSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import org.lwjgl.nanovg.NanoVG;
public class HudModule extends BaseModule {
   public static List<Long> sqy = new LinkedList<>();
   public static List<Long> cbp = new LinkedList<>();
   public static List<Long> yxu = new LinkedList<>();
   public static int tjj;
   public static ByteBuf ys = Unpooled.buffer(128);
   public HudElementsSetting dg = new HudElementsSetting(this, GlyphTextUtil.encode("hud"), new JsonObject());
   public IntegerSliderSetting rfv = new IntegerSliderSetting(this, GlyphTextUtil.encode("scale"), 50, 100, 150);
   public InterfaceModule utt;
   public static final float qwj = 15.0F;
   public static final float xwd = 22.0F;
   public static final float oxbp = 5.0F;

   public HudModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("Hud"));
      this.selectAllSettings();
   }

   public void wde(PacketSendEvent var1) throws Throwable {
      Packet var2 = var1.wmo();
      if (var2 instanceof ServerboundInteractPacket var3) {
         synchronized (ys) {
            ys.clear();
            FriendlyByteBuf var5 = new FriendlyByteBuf(ys);
            ServerboundInteractPacket.STREAM_CODEC.encode(var5, var3);
            var5.readVarInt();
            int var6 = var5.readVarInt();
            if (var6 != 1 && var6 == 0) {
               cbp.add(System.nanoTime());
            }
         }
      } else if (var2 instanceof ServerboundSwingPacket) {
         yxu.add(System.nanoTime());
      } else if (var2 instanceof ServerboundUseItemOnPacket || var2 instanceof ServerboundUseItemPacket) {
         cbp.add(System.nanoTime());
      }
   }

   public void lfc(GuiRenderContext var1) throws Throwable {
      if (this.isEnabled()) {
         if (tjj == 0) {
            tjj = NanoVG.nvgCreateImageMem(var1.nvg, 6, var1.loadResource(GlyphTextUtil.encode("textures/icon.png")));
         }

         sqy.add(System.nanoTime());
         long var2 = System.nanoTime() - 1000000000L;

         while (!sqy.isEmpty() && sqy.get(0) < var2) {
            sqy.remove(0);
         }

         while (!cbp.isEmpty() && cbp.get(0) < var2) {
            cbp.remove(0);
         }

         while (!yxu.isEmpty() && yxu.get(0) < var2) {
            yxu.remove(0);
         }

         long var4 = var1.nvg;
         float var6 = 1.4F * Math.round(((Integer)this.rfv.getValue()).intValue() / 100.0F * 5.0F) / 5.0F;

         for (HudAnchor var10 : HudAnchor.vwu()) {
            float var11 = var10.dvr(var1, var6);
            float var12 = var10.xtp(var1, var6);
            float var13 = var10.ktw();
            boolean var14 = var10 == HudAnchor.pwh || var10 == HudAnchor.qrx;

            for (HudElementBase var16 : var10.sm) {
               float var17 = var11;
               float var18 = var16.icg(var1) * var6;
               float var19 = var16.yxe(var1) * var6;
               if (var10 == HudAnchor.qrx) {
                  var17 = var11 - var18;
               }

               NanoVG.nvgSave(var4);
               NanoVG.nvgTranslate(var4, var17, var12 + var13 * var19);
               NanoVG.nvgScale(var4, var6, var6);
               var16.jjc(var1);
               NanoVG.nvgRestore(var4);
               if (var14) {
                  var12 += var19 + 5.0F;
               } else {
                  var11 += var18 + 5.0F;
               }
            }
         }
      }
   }

   public static void bvg(GuiRenderContext var0, int var1, float var2, float var3, ItemStack var4, boolean var5) {
      if (PhantomClient.traceEnabled) {
         PhantomClient.trace(GlyphTextUtil.encode("Hud"), GlyphTextUtil.encode("renderArmor"), var4.toString());
      }

      float var6 = 1.0F;
      if (var4 == null || var4.getItem().equals(Items.AIR)) {
         var4 = switch (var1) {
            case 0 -> new ItemStack(Items.LEATHER_HELMET);
            case 1 -> new ItemStack(Items.LEATHER_CHESTPLATE);
            case 2 -> new ItemStack(Items.LEATHER_LEGGINGS);
            case 3 -> new ItemStack(Items.LEATHER_BOOTS);
            default -> var4;
         };
         var6 = -1.0F;
      }

      PhantomClient.uiManager.sdd().tzk(var4, var2, var3, 22.0F, 22.0F, var6);
      float var7 = 2.0F;
      if (var6 > 0.0F) {
         NanoVG.nvgBeginPath(var0.nvg);
         NanoVG.nvgRoundedRect(var0.nvg, var2 + 2.5F, var3 + 22.0F - 2.0F + 1.0F, 17.0F, 2.0F, 1.0F);
         NanoVG.nvgFillColor(var0.nvg, GuiRenderContext.rgba(255, 20, 20, 220, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var0.nvg);
         NanoVG.nvgBeginPath(var0.nvg);
         NanoVG.nvgRoundedRect(
            var0.nvg,
            var2 + 2.5F,
            var3 + 22.0F - 2.0F + 1.0F,
            17.0F * Math.min(1.0F, Math.max(0.0F, 1.0F - var4.getDamageValue() / (var4.getMaxDamage() * 1.0F))),
            2.0F,
            1.0F
         );
         NanoVG.nvgFillColor(var0.nvg, GuiRenderContext.rgba(20, 255, 20, 220, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var0.nvg);
      } else {
         NanoVG.nvgBeginPath(var0.nvg);
         NanoVG.nvgRoundedRect(var0.nvg, var2 + 2.5F, var3 + 22.0F - 2.0F + 1.0F, 17.0F, 2.0F, 1.0F);
         NanoVG.nvgFillColor(var0.nvg, GuiRenderContext.rgba(90, 90, 90, 220, GuiRenderContext.colorScratch));
         NanoVG.nvgFill(var0.nvg);
      }
   }
}
