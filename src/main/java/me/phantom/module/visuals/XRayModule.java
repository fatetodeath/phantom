package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.event.world.ChunkOcclusionBlockEvent;
import me.phantom.event.world.ShouldDrawBlockSideEvent;
import me.phantom.event.misc.ValueOverrideEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ChoiceListSetting;
import me.phantom.util.GlyphTextUtil;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
public class XRayModule extends BaseModule {
   public static final List<String> ho = new ArrayList<>();
   public final ChoiceListSetting esv = new ChoiceListSetting(this, GlyphTextUtil.encode("blocks"), ho);

   public XRayModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("XRay"));
      this.selectAllSettings();
      this.markClipboardHint();
   }

   @Override
   public void onEnable() {
      MinecraftClient.getInstance().worldRenderer.reload();
   }

   @Override
   public void onDisable() {
      if (MinecraftClient.getInstance().player != null) {
         MinecraftClient.getInstance().worldRenderer.reload();
      }
   }

   public void ihs(ChunkOcclusionBlockEvent var1) {
      Block var2 = MinecraftClient.getInstance().world.getBlockState(var1.fgg()).getBlock();
      String var3 = Registries.BLOCK.getId(var2).toString();
      var1.setCancelled(!((List)this.esv.getValue()).contains(var3));
   }

   public void vys(ShouldDrawBlockSideEvent var1) {
      String var2 = Registries.BLOCK.getId(var1.tf().getBlock()).toString();
      var1.rh(((List)this.esv.getValue()).contains(GlyphTextUtil.encodeChars(var2)));
      var1.setCancelled(true);
   }

   public void xzg(ValueOverrideEvent var1) {
      var1.setValue(2000.0);
   }

   static {
      ho.add(GlyphTextUtil.encode("minecraft:diamond_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:diamond_block"));
      ho.add(GlyphTextUtil.encode("minecraft:deepslate_diamond_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:ancient_debris"));
      ho.add(GlyphTextUtil.encode("minecraft:netherite_block"));
      ho.add(GlyphTextUtil.encode("minecraft:gold_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:gold_block"));
      ho.add(GlyphTextUtil.encode("minecraft:deepslate_gold_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:coal_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:coal_block"));
      ho.add(GlyphTextUtil.encode("minecraft:deepslate_coal_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:iron_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:iron_block"));
      ho.add(GlyphTextUtil.encode("minecraft:deepslate_iron_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:redstone_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:redstone_block"));
      ho.add(GlyphTextUtil.encode("minecraft:deepslate_redstone_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:emerald_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:emerald_block"));
      ho.add(GlyphTextUtil.encode("minecraft:deepslate_emerald_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:lapis_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:lapis_block"));
      ho.add(GlyphTextUtil.encode("minecraft:deepslate_lapis_ore"));
      ho.add(GlyphTextUtil.encode("minecraft:ender_chest"));
      ho.add(GlyphTextUtil.encode("minecraft:chest"));
      ho.add(GlyphTextUtil.encode("minecraft:trapped_chest"));
      ho.add(GlyphTextUtil.encode("minecraft:furnace"));
      ho.add(GlyphTextUtil.encode("minecraft:obsidian"));
      ho.add(GlyphTextUtil.encode("minecraft:tnt"));
   }
}
