package me.phantom.module.internal;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ModeSetting;
import me.phantom.util.GlyphTextUtil;

public class BlatantFilterModule extends BaseModule {
   public static BlatantFilterModule qn;
   public ModeSetting fus = new ModeSetting(this, GlyphTextUtil.encode("level"), GlyphTextUtil.encode("Full rage"), GlyphTextUtil.encode("Semi rage"), GlyphTextUtil.encode("Semi legit"), GlyphTextUtil.encode("Full legit"));

   public BlatantFilterModule() {
      super(ModuleCategory.yy, ClientPlayStyle.or, GlyphTextUtil.encode("BlatantFilter"));
      qn = this;
   }

   @Override
   public void setEnabled(boolean var1, boolean var2) {
      super.setEnabled(var1, var2);
      if (var1) {
         ClientPlayStyle var3 = getMinimumStyle();

         for (BaseModule var7 : PhantomClient.moduleManager.getModules()) {
            if (var7.playStyle.getLevel() < var3.getLevel()) {
               var7.setEnabled(false, true);
            }
         }
      }
   }

   @Override
   public boolean isEnabled() {
      return this.enabled;
   }

   public static ClientPlayStyle getMinimumStyle() {
      if (qn != null && qn.isEnabled()) {
         if (((String)qn.fus.getValue()).equals(GlyphTextUtil.encode("Full rage"))) {
            return ClientPlayStyle.bq;
         } else if (((String)qn.fus.getValue()).equals(GlyphTextUtil.encode("Semi rage"))) {
            return ClientPlayStyle.ats;
         } else if (((String)qn.fus.getValue()).equals(GlyphTextUtil.encode("Semi legit"))) {
            return ClientPlayStyle.zfw;
         } else {
            return ((String)qn.fus.getValue()).equals(GlyphTextUtil.encode("Full legit")) ? ClientPlayStyle.or : ClientPlayStyle.bq;
         }
      } else {
         return ClientPlayStyle.bq;
      }
   }
}
