package me.phantom.module.base;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.EventBase;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.gui.core.UiManager;
import me.phantom.gui.widget.EasedAnimation;
import me.phantom.gui.widget.SearchHighlightRect;
import me.phantom.module.internal.ArrayListHudModule;
import me.phantom.module.internal.BlatantFilterModule;
import me.phantom.module.internal.ConfigsButtonModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.setting.AbstractSetting;
import me.phantom.setting.CollapsibleSettingsGroup;
import me.phantom.setting.KeyBindable;
import me.phantom.setting.KeybindSetting;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.JsonUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import org.lwjgl.nanovg.NanoVG;

public class BaseModule implements KeyBindable {
   public final ClientPlayStyle playStyle;
   public final ModuleCategory category;
   public final String name;
   public final Map<String, AbstractSetting<?>> settings = new LinkedHashMap<>();
   public final Map<String, AbstractSetting<?>> selectedSettings = new LinkedHashMap<>();
   public final Map<Class<? extends EventBase>, Consumer<EventBase>> eventListeners = new HashMap<>();
   public int keyCode;
   public boolean holdMode;
   public boolean expanded;
   public EasedAnimation expandAnimation;
   public EasedAnimation collapseAnimation;
   public EasedAnimation errorShakeAnimation;
   public EasedAnimation labelShowAnimation;
   public EasedAnimation labelHideAnimation;
   public boolean labelHovered;
   public boolean hiddenFromHud;
   public boolean clipboardHint;
   public boolean beta;
   public boolean enabled;
   public boolean hiddenFromList;
   public EasedAnimation enableAnimation;
   public EasedAnimation disableAnimation;
   public SearchHighlightRect searchHighlight;
   public long noConfigNoticeUntil;

   public BaseModule(ModuleCategory var1, ClientPlayStyle var2, String var3) {
      this.playStyle = var2;
      this.category = var1;
      this.name = var3;
      this.expandAnimation = new EasedAnimation(false);
      this.collapseAnimation = new EasedAnimation(false);
      this.errorShakeAnimation = new EasedAnimation(false);
      this.enableAnimation = new EasedAnimation(false);
      this.disableAnimation = new EasedAnimation(false);
      this.labelShowAnimation = new EasedAnimation(false);
      this.labelHideAnimation = new EasedAnimation(false);

      for (Method var7 : this.getClass().getMethods()) {
         if (var7.getParameterTypes().length == 1 && EventBase.class.isAssignableFrom(var7.getParameterTypes()[0])) {
            this.eventListeners.put((Class<? extends EventBase>)var7.getParameterTypes()[0], var2x -> {
               try {
                  var7.invoke(this, var2x);
               } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var4) {
                  throw new RuntimeException(var4);
               }
            });
         }
      }

      if (PhantomClient.isDebugBuild) {
         this.getDescription();
         this.getDisplayName();

         for (AbstractSetting var9 : this.settings.values()) {
            var9.getDisplayName();
            var9.getDescription();
         }
      }
   }

   public void selectAllSettings() {
      this.selectedSettings.putAll(this.settings);
   }

   public void selectSettings(String... var1) {
      for (String var5 : var1) {
         AbstractSetting var6 = this.settings.get(var5);
         if (var6 == null) {
            throw new IllegalArgumentException(GlyphTextUtil.encode("Unknown key ") + var5);
         }

         this.selectedSettings.put(var5, var6);
      }
   }

   public void markBeta() {
      this.beta = true;
   }

   public void markClipboardHint() {
      this.clipboardHint = true;
   }

   public boolean lvr() {
      return true;
   }

   public final void registerSetting(AbstractSetting<?> var1) {
      this.settings.put(var1.getName(), var1);
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void registerModules() {
   }

   public void setEnabled(boolean var1, boolean var2) {
      if (this.playStyle.getLevel() < BlatantFilterModule.getMinimumStyle().getLevel()) {
         var1 = false;
      }

      if (this.enabled != var1) {
         this.enabled = var1;
         if (var1) {
            this.enableAnimation.qou(420);
            this.subscribeEvents();
            if (PhantomClient.isInWorld()) {
               try {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(GlyphTextUtil.encode("PhantomClient"), GlyphTextUtil.encode("module_onEnable"), this.getName());
                  }

                  this.onEnable();
               } catch (Throwable var5) {
                  this.enabled = false;
                  PhantomClient.clientLogger.xdk(this.getName(), GlyphTextUtil.encode("Failed to enable module"), var5);
               }
            }
         } else {
            this.disableAnimation.qou(420);
            this.unsubscribeEvents();
            if (PhantomClient.isInWorld()) {
               try {
                  if (PhantomClient.traceEnabled) {
                     PhantomClient.trace(GlyphTextUtil.encode("PhantomClient"), GlyphTextUtil.encode("module_onDisable"), this.getName());
                  }

                  this.onDisable();
               } catch (Throwable var4) {
                  PhantomClient.clientLogger.xdk(this.getName(), GlyphTextUtil.encode("Failed to disable module"), var4);
               }
            }
         }
      }
   }

   public boolean ma() {
      return this.isEnabled();
   }

   public boolean isEnabled() {
      return this.enabled && this.playStyle.getLevel() >= BlatantFilterModule.getMinimumStyle().getLevel();
   }

   public final String getName() {
      return this.name;
   }

   public final String getDescription() {
      return PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.") + this.name + GlyphTextUtil.encode(".description")) + (this.clipboardHint ? GlyphTextUtil.encode("\n\nclip:") + this.name : GlyphTextUtil.encode(""));
   }

   public String getDisplayName() {
      return this.buildDisplayName(true, false);
   }

   public String buildDisplayName(boolean var1, boolean var2) {
      StringBuilder var3 = new StringBuilder(this.getTranslatedName());
      if (var2) {
         String var4 = this.getValueSuffix();
         if (!var4.isEmpty()) {
            String var5 = (String)((ArrayListHudModule)PhantomClient.moduleManager.getModuleByName(GlyphTextUtil.encode("List"))).mau.getValue();
            if (var5.equals(GlyphTextUtil.encode("Name [*]"))) {
               var3.append(GlyphTextUtil.encode(" [")).append(var4).append(GlyphTextUtil.encode("]"));
            } else if (var5.equals(GlyphTextUtil.encode("Name <*>"))) {
               var3.append(GlyphTextUtil.encode(" <")).append(var4).append(GlyphTextUtil.encode(">"));
            } else if (var5.equals(GlyphTextUtil.encode("Name *"))) {
               var3.append(GlyphTextUtil.encode(" ")).append(var4);
            } else if (var5.equals(GlyphTextUtil.encode("Name - *"))) {
               var3.append(GlyphTextUtil.encode(" - ")).append(var4);
            }
         }
      }

      if (var1 && this.keyCode != 0) {
         var3.append(GlyphTextUtil.encode(" [")).append(UiManager.getKeyName(this.keyCode).toUpperCase()).append(GlyphTextUtil.encode("]"));
      }

      return var3.toString();
   }

   public String getValueSuffix() {
      return GlyphTextUtil.encode("");
   }

   public String getTranslatedName() {
      return PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.") + this.name + GlyphTextUtil.encode(".name"));
   }

   public final ModuleCategory getCategory() {
      return this.category;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public void setExpanded(boolean var1) {
      if (this.expanded != var1) {
         this.expanded = var1;
         if (this.settings.isEmpty()) {
            this.errorShakeAnimation.qou(600);
            this.expanded = false;
            PhantomClient.uiManager.notifications().klr(GlyphTextUtil.encode("modules.no_configuration"), this.getDisplayName());
            this.noConfigNoticeUntil = System.currentTimeMillis() + 15000L;
         } else if (var1) {
            this.expandAnimation.qou(150);
         } else {
            this.collapseAnimation.qou(150);
         }

         if (var1) {
            for (BaseModule var5 : PhantomClient.moduleManager.getModules()) {
               if (!var5.equals(this)) {
                  var5.setExpanded(false);
               }
            }
         }
      }
   }

   public Collection<AbstractSetting<?>> getSettings() {
      return this.settings.values();
   }

   public int getHeight() {
      int var1 = 25;
      if (this.isExpanded() || this.expandAnimation.sdx() || this.collapseAnimation.sdx()) {
         var1 += 4;

         for (AbstractSetting var3 : this.getSettings()) {
            if (var3.lvr()) {
               var1 += var3.getHeight();
            }
         }

         var1 += 4;
      }

      return var1;
   }

   public int renderModuleEntry(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (PhantomClient.uiManager.searchQuery.length() > 0) {
         if (GlyphTextUtil.lowercase(this.name).contains(GlyphTextUtil.lowercase(GlyphTextUtil.trim(PhantomClient.uiManager.searchQuery)))) {
            this.searchHighlight = new SearchHighlightRect();
            this.searchHighlight.x = var1.mouseX;
            this.searchHighlight.y = var1.mouseY;
            this.searchHighlight.width = var2;
         } else {
            var3 = -1;
            var4 = -1;
            this.searchHighlight = null;
         }
      } else {
         this.searchHighlight = null;
      }

      if (var1.isHovered(var3, var4, var2 - 18, 0, 20, 24) && var1.doj[0]) {
         this.hiddenFromList = !this.hiddenFromList;
         var1.doj[0] = false;
      }

      if (this.supportsHoldMode() && this.getKeyCode() != 0 && var1.isHovered(var3, var4, var2 - 39, 0, 20, 24)) {
         if (this.isHoldMode()) {
            if (var1.doj[0]) {
               this.holdMode = false;
               var1.doj[0] = false;
            }
         } else if (var1.doj[0]) {
            this.holdMode = true;
            var1.doj[0] = false;
         }
      }

      if (var1.isClicked(var3, var4, 0, 0, var2, 24, 0)) {
         if (!(this instanceof InterfaceModule)) {
            this.setEnabled(!this.isEnabled(), true);
            this.afterToggle();
         }
      } else if (var1.isClicked(var3, var4, 0, 0, var2, 24, 1)) {
         this.setExpanded(!this.isExpanded());
      } else if (var1.isClicked(var3, var4, 0, 0, var2, 24, 2)) {
         var1.keybindCaptureTarget = this;
      }

      float var7 = this.errorShakeAnimation.hee();
      if (var7 > 0.0F) {
         NanoVG.nvgSave(var1.nvg);
         NanoVG.nvgTranslate(var1.nvg, var2 / 2, 12.0F);
         NanoVG.nvgRotate(var1.nvg, (float)Math.toRadians(5.0 * Math.sin(var7 * Math.PI * 4.0) * Math.pow(1.0F - var7, 2.0)));
         NanoVG.nvgTranslate(var1.nvg, -var2 / 2, -12.0F);
      }

      int var8 = 0;
      float var9 = !(this instanceof InterfaceModule) && !this.isEnabled() ? 1.0F - this.disableAnimation.hee() : this.enableAnimation.hee();
      float var10 = this.labelHovered ? this.labelShowAnimation.hee() : 1.0F - this.labelHideAnimation.hee();
      if (var1.drawButton(
         var3,
         var4,
         Objects.equals(var1.keybindCaptureTarget, this)
            ? (System.currentTimeMillis() % 1000L <= 500L ? GlyphTextUtil.encode("- ") + PhantomClient.translationManager.ry(GlyphTextUtil.encode("keybind.msg")) + GlyphTextUtil.encode(" -") : GlyphTextUtil.encode("-  -"))
            : this.getDisplayName(),
         var2,
         24,
         true,
         var9,
         var10
      )) {
         if (!this.labelHovered) {
            this.labelShowAnimation.qou(150);
            this.labelHovered = true;
         }

         var1.setTooltip(this.getDescription());
      } else if (this.labelHovered) {
         this.labelHideAnimation.qou(150);
         this.labelHovered = false;
      }

      if (this.beta) {
         NanoVG.nvgSave(var1.nvg);
         NanoVG.nvgTranslate(var1.nvg, var2 / 4 * 3, 6.0F);
         NanoVG.nvgRotate(var1.nvg, (float) (Math.PI / 10));
         var1.drawTextAuto(0.0F, 0.0F, GlyphTextUtil.encode("Beta"), 12.0F, false, 20, 1.0F, 1.0F);
         NanoVG.nvgRestore(var1.nvg);
      }

      int var11 = 4;
      if (this.isExpanded() || System.currentTimeMillis() < this.noConfigNoticeUntil) {
         boolean var12 = var1.isHovered(var3, var4, var2 - var11 - 16, 0, 20, 24);
         var1.drawIconText(var2 - var11, 12, this.hiddenFromList ? 128263 : 128266, 14.0F, false, 20, (var12 ? 0.5F : 0.0F) + (this.hiddenFromList ? 0.0F : 0.5F), this.hiddenFromList ? 0.6F : 1.0F);
         var11 += 21;
         if (var12) {
            if (this.hiddenFromList) {
               var1.setTooltip(PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.showlist")));
            } else {
               var1.setTooltip(PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.hidelist")));
            }
         }

         if (this.supportsHoldMode() && this.getKeyCode() != 0) {
            var12 = var1.isHovered(var3, var4, var2 - var11 - 16, 0, 20, 24);
            var1.drawIconText(var2 - var11, 12, 59297, 14.0F, false, 20, (var12 ? 0.5F : 0.0F) + (this.holdMode ? 0.5F : 0.0F), this.holdMode ? 1.0F : 0.6F);
            var11 += 21;
            if (var12) {
               if (this.isHoldMode()) {
                  var1.setTooltip(PhantomClient.translationManager.ry(GlyphTextUtil.encode("keybind.hold.disable")));
               } else {
                  var1.setTooltip(PhantomClient.translationManager.ry(GlyphTextUtil.encode("keybind.hold.enable")));
               }
            }
         }
      }

      if (this.keyCode != 0) {
         StringBuilder var22 = new StringBuilder();

         for (BaseModule var16 : PhantomClient.moduleManager.getModules()) {
            if (!this.equals(var16) && var16.getKeyCode() == this.keyCode) {
               var22.append(GlyphTextUtil.encode("- Phantom ")).append(var16.getName()).append(GlyphTextUtil.encode("\n"));
            }
         }

         for (KeyBinding var30 : MinecraftClient.getInstance().options.allKeys) {
            if (var30.matchesKey(this.keyCode, 0)) {
               var22.append(GlyphTextUtil.encode("- Minecraft ")).append(I18n.translate(var30.getTranslationKey(), new Object[0])).append(GlyphTextUtil.encode("\n"));
            }
         }

         if (!var22.isEmpty()) {
            boolean var25 = var1.isHovered(var3, var4, var2 - var11 - 16, 0, 20, 24);
            var1.drawIconText(var2 - var11, 12, 9888, 12.0F, false, 20, var25 ? 0.5F : 0.0F, 1.0F);
            var11 += 21;
            if (var25) {
               var1.setTooltip(PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.ui.keybind.collision"), var22.toString()));
            }
         }
      }

      if (!this.settings.isEmpty()) {
         float var23 = this.isExpanded() ? this.expandAnimation.hee() : 1.0F - this.collapseAnimation.hee();
         long var26 = var1.nvg;
         byte var29 = 12;
         byte var31 = 13;
         int var17 = Math.round(5.0F - var23 * 5.0F);
         NanoVG.nvgBeginPath(var26);
         NanoVG.nvgTranslate(var26, var29, var31);
         NanoVG.nvgRotate(var26, var23 * (float) (Math.PI / 2));
         NanoVG.nvgMoveTo(var26, -5.0F, var17 - 2);
         NanoVG.nvgLineTo(var26, 0.0F, 5 - var17 - 2);
         NanoVG.nvgLineTo(var26, 5.0F, var17 - 2);
         NanoVG.nvgTranslate(var26, -var29, -var31);
         int var18 = (int)(var23 * 60.0F + 180.0F);
         NanoVG.nvgStrokeColor(var26, GuiRenderContext.rgba(var18, var18, var18, var18, GuiRenderContext.colorScratch));
         NanoVG.nvgStroke(var26);
      }

      if (var7 > 0.0F) {
         NanoVG.nvgRestore(var1.nvg);
      }

      var1.translate(0, 25);
      var8 += 25;
      if (this.searchHighlight != null) {
         this.searchHighlight.height = var8;
      }

      return var8;
   }

   public void afterToggle() {
   }

   public void subscribeEvents() {
      this.eventListeners.forEach((var0, var1) -> PhantomClient.eventManager.subscribe((Class<? extends EventBase>)var0, (Consumer<EventBase>)var1));
   }

   public int renderSettings(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6) {
      if (var1.doj[3]) {
         this.setExpanded(false);
         var1.doj[3] = false;
      }

      int var7 = 0;
      byte var8 = 8;
      var1.translate(var8, 4);
      var7 += 4;

      for (AbstractSetting var10 : this.getSettings()) {
         var10.updateWasVisible();
      }

      for (AbstractSetting var15 : this.getSettings()) {
         if (var15.lvr()) {
            int var11 = var15.getHeight();
            if (var1.xwl != null && !var1.xwl.equals(var15) && !(var15 instanceof CollapsibleSettingsGroup)) {
               var15.renderSetting(var1, var2 - var8 * 2, -1, -1, var1.mzx, var1.mzx);
            } else {
               var15.renderSetting(var1, var2 - var8 * 2, var3, var4, var5, var6);
            }

            var7 += var11;
            var1.translate(0, var11);
         }
      }

      var7 += 4;
      var1.translate(-var8, 4);
      return var7;
   }

   private void unsubscribeEvents() {
      this.eventListeners.forEach((var0, var1) -> PhantomClient.eventManager.unsubscribe((Class<? extends EventBase>)var0, (Consumer<EventBase>)var1));
   }

   public void loadConfig(JsonObject var1, boolean var2, boolean var3, boolean var4) {
      if (!var2 && !(this instanceof InterfaceModule) && !(this instanceof ConfigsButtonModule)) {
         this.setEnabled(JsonUtils.uzc(var1.get(GlyphTextUtil.encode("enabled"))), false);
      }

      this.hiddenFromList = JsonUtils.itd(var1, GlyphTextUtil.encode("list_hidden"), false);
      if (var4 && this.category != ModuleCategory.yy) {
         this.setKeybind(JsonUtils.jwk(var1, GlyphTextUtil.encode("keybind"), 0), JsonUtils.itd(var1, GlyphTextUtil.encode("keybind_hold"), false));
      }

      JsonObject var5 = var1.get(GlyphTextUtil.encode("values")).getAsJsonObject();

      for (String var7 : var5.keySet()) {
         AbstractSetting var8 = var3 ? this.selectedSettings.get(var7) : this.settings.get(var7);
         if (var8 != null && var2 == (var8 instanceof KeybindSetting)) {
            try {
               var8.fromJson(var5.get(var7));
            } catch (Throwable var10) {
               PhantomClient.clientLogger
                  .xdk(
                     GlyphTextUtil.encode("ConfigManager"),
                     GlyphTextUtil.encode("Failed to read value ") + var7 + GlyphTextUtil.encode(" in module ") + this.name + GlyphTextUtil.encode(" (val=") + var5.get(var7) + GlyphTextUtil.encode(")"),
                     var10
                  );
            }
         }
      }
   }

   public JsonObject saveConfig(boolean var1, boolean var2) {
      JsonObject var3 = new JsonObject();

      for (Entry var5 : (var2 ? this.selectedSettings : this.settings).entrySet()) {
         if (var1 == (var5.getValue() instanceof KeybindSetting)) {
            try {
               JsonElement var6 = ((AbstractSetting)var5.getValue()).toJson();
               if (var6 != null) {
                  var3.add((String)var5.getKey(), var6);
               }
            } catch (Throwable var7) {
               PhantomClient.clientLogger.xdk(GlyphTextUtil.encode("ConfigManager"), GlyphTextUtil.encode("Failed to write value ") + (String)var5.getKey() + GlyphTextUtil.encode(" in module ") + this.name, var7);
            }
         }
      }

      JsonObject var8 = new JsonObject();
      var8.addProperty(GlyphTextUtil.encode("enabled"), this.isEnabled());
      var8.addProperty(GlyphTextUtil.encode("list_hidden"), this.hiddenFromList);
      var8.addProperty(GlyphTextUtil.encode("keybind"), this.getKeyCode());
      var8.addProperty(GlyphTextUtil.encode("keybind_hold"), this.isHoldMode());
      var8.add(GlyphTextUtil.encode("values"), var3);
      return var8;
   }

   public boolean isHiddenFromList() {
      return this.hiddenFromList;
   }

   @Override
   public int getKeyCode() {
      return this.keyCode;
   }

   public boolean supportsHoldMode() {
      return true;
   }

   @Override
   public boolean isHoldMode() {
      return this.holdMode;
   }

   @Override
   public void setKeybind(int var1, boolean var2) {
      if (var1 != 0 && UiManager.getKeyName(var1) == null) {
         PhantomClient.uiManager.notifications().tul(GlyphTextUtil.encode("keybind.unknown"), var1);
      } else {
         this.keyCode = var1;
         this.holdMode = var2 && this.supportsHoldMode();
         if (PhantomClient.configManager != null) {
            PhantomClient.configManager.fl();
         }
      }
   }

   public void setHiddenFromHud(boolean var1) {
      this.hiddenFromHud = var1;
   }

   public boolean isHiddenFromHud() {
      return this.hiddenFromHud;
   }

   public void refresh() {
      for (AbstractSetting var2 : this.settings.values()) {
         var2.refresh();
      }
   }
}
