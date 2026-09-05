package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.gui.screen.BlankClientScreen;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ModeSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
public class InterfaceModule extends BaseModule {
   public static final String[] gfpy = new String[]{
      GlyphTextUtil.encode("Sans"), GlyphTextUtil.encode("Roboto"), GlyphTextUtil.encode("Courier"), GlyphTextUtil.encode("Domine"), GlyphTextUtil.encode("Dosis"), GlyphTextUtil.encode("Kanit"), GlyphTextUtil.encode("Maple")
   };
   public final ColorSetting jfj = new ColorSetting(this, GlyphTextUtil.encode("primary"), new Color(15, 15, 15));
   public final ColorSetting mbw = new ColorSetting(this, GlyphTextUtil.encode("secondary"), new Color(255, 0, 0));
   public final ColorSetting nkj = new ColorSetting(this, GlyphTextUtil.encode("fontcolor"), new Color(240, 240, 240));
   public final ColorSetting qir = new ColorSetting(this, GlyphTextUtil.encode("shadowfontcolor"), new Color(0, 0, 0));
   public final IntegerSliderSetting uxm = new IntegerSliderSetting(this, GlyphTextUtil.encode("shadowfontstrength"), 0, 25, 100);
   public final IntegerSliderSetting rfv = new IntegerSliderSetting(this, GlyphTextUtil.encode("scale"), 80, 100, 150);
   public final ToggleSetting ppr = new ToggleSetting(this, GlyphTextUtil.encode("animations"), true);
   public final FloatSliderSetting wmp = new FloatSliderSetting(this, GlyphTextUtil.encode("animationSpeed"), 0.0F, 1.4F, 2.0F);
   public final FloatSliderSetting ydq = new FloatSliderSetting(this, GlyphTextUtil.encode("glowSpeed"), 0.0F, 1.0F, 2.0F);
   public final ToggleSetting twz = new ToggleSetting(this, GlyphTextUtil.encode("windowVelocity"), true);
   public final ToggleSetting htk = new ToggleSetting(this, GlyphTextUtil.encode("videos"), true);
   public final ToggleSetting owv = new ToggleSetting(this, GlyphTextUtil.encode("gestures"), true);
   public final ToggleSetting pvs = new ToggleSetting(this, GlyphTextUtil.encode("direct_search"), true);
   public final ModeSetting ka = new ModeSetting(this, GlyphTextUtil.encode("language"), GlyphTextUtil.encode("en_US"), GlyphTextUtil.encode("de_DE"));
   public final ToggleSetting wcc = new ToggleSetting(this, GlyphTextUtil.encode("hide_notifications"), false);
   public final IntegerSliderSetting hzx = new IntegerSliderSetting(this, GlyphTextUtil.encode("blur"), 0, 50, 100);
   public final ModeSetting tbw = new ModeSetting(this, GlyphTextUtil.encode("font"), gfpy);
   public final ModeSetting zhn = new ModeSetting(this, GlyphTextUtil.encode("font_notification"), gfpy);
   public static String fontRegular = null;
   public static String fontBold = null;

   public InterfaceModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("UI"));
      this.selectAllSettings();
      this.setKeybind(344, false);
      this.wmp.setVisibleWhen(this.ppr);
      this.ka.setChangeListener((var0, var1) -> {
         try {
            PhantomClient.refresh();
         } catch (Throwable var3) {
            PhantomClient.fatalError(GlyphTextUtil.encode("Reloading languages"), GlyphTextUtil.encode("Failed to reload languages"), var3);
         }
      });
      this.tbw.setChangeListener((var1, var2) -> PhantomClient.uiManager.getContext().loadUiFonts(this));
   }

   @Override
   public boolean supportsHoldMode() {
      return false;
   }

   @Override
   public void setEnabled(boolean var1, boolean var2) {
      if (this.isEnabled() != var1) {
         super.setEnabled(var1, var2);
         if (var1 && var2) {
            PhantomClient.uiManager.enableAnimation.qou(300);
            if (MinecraftClient.getInstance().currentScreen == null) {
               MinecraftClient.getInstance().setScreen(new BlankClientScreen());
            }
         }
      }
   }
}
