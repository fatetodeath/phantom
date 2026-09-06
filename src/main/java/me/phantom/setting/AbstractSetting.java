package me.phantom.setting;

import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class AbstractSetting<T> {
   public BaseModule module;
   public final String name;
   public final T defaultValue;
   public CollapsibleSettingsGroup group;
   public T value;
   public Supplier<Boolean> visibilitySupplier;
   public boolean lastVisible;
   public String cachedLabel;
   public String cachedDescription;
   public BiConsumer<T, T> changeListener;
   public String translationPrefixOverride;

   public AbstractSetting(BaseModule var1, String var2, T var3) {
      this.module = var1;
      this.name = var2;
      this.defaultValue = (T)var3;
      this.value = (T)var3;
      this.refresh();
      var1.registerSetting(this);
   }

   public void refresh() {
      String var1 = this.translationPrefixOverride == null ? GlyphTextUtil.encode("module.") + this.module.getName() + GlyphTextUtil.encode(".") + this.name : this.translationPrefixOverride;
      this.cachedLabel = PhantomClient.translationManager.ry(var1 + GlyphTextUtil.encode(".name"));
      this.cachedDescription = PhantomClient.translationManager.ry(var1 + GlyphTextUtil.encode(".description"));
   }

   public void setGroup(CollapsibleSettingsGroup var1) {
      this.group = var1;
   }

   public CollapsibleSettingsGroup getGroup() {
      return this.group;
   }

   public final String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return this.translationPrefixOverride != null ? PhantomClient.translationManager.ry(this.translationPrefixOverride + GlyphTextUtil.encode(".name")) : this.cachedLabel.replace(GlyphTextUtil.encode("$1"), this.getValue().toString());
   }

   public String getDescription() {
      return this.translationPrefixOverride != null ? PhantomClient.translationManager.ry(this.translationPrefixOverride + GlyphTextUtil.encode(".description")) : this.cachedDescription.replace(GlyphTextUtil.encode("$1"), this.getValue().toString());
   }

   public final T getDefaultValue() {
      return this.defaultValue;
   }

   public T getValue() {
      return this.value;
   }

   public void setValue(T var1) {
      T var2 = this.value;
      this.value = (T)var1;
      if (this.changeListener != null) {
         this.changeListener.accept(var2, var1);
      }

      this.notifyConfigChanged();
   }

   public void notifyConfigChanged() {
      if (this.module.getCategory() == ModuleCategory.yy) {
         PhantomClient.configManager.fl();
      }
   }

   public void resetToDefault() {
      this.value = this.defaultValue;
   }

   public BaseModule getModule() {
      return this.module;
   }

   public abstract JsonElement toJson();

   public abstract void fromJson(JsonElement var1);

   public abstract int getHeight();

   public abstract void renderSetting(GuiRenderContext var1, int var2, int var3, int var4, boolean[] var5, boolean[] var6);

   public void setVisibleWhen(ToggleSetting var1) {
      if (this.equals(var1)) {
         throw new IllegalArgumentException();
      } else {
         this.setVisibilitySupplier(() -> (Boolean)var1.getValue() && var1.lvr());
      }
   }

   public void setVisibilitySupplier(Supplier<Boolean> var1) {
      this.visibilitySupplier = var1;
   }

   public boolean lvr() {
      return (this.visibilitySupplier == null || this.visibilitySupplier.get()) && this.group == null;
   }

   public boolean wasVisible() {
      return this.lastVisible;
   }

   public void updateWasVisible() {
      this.lastVisible = this.lvr();
   }

   public void dpf(int var1, int var2, int var3, int var4) {
   }

   public void setChangeListener(BiConsumer<T, T> var1) {
      this.changeListener = var1;
   }
}
