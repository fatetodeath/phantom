package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.module.misc.JumpPredictionModule;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.FloatSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
@ModuleMetadata(
   x = -4.5,
   y = -60.0,
   z = -34.5
)
public class ParkourModule extends BaseModule {
   public final ToggleSetting xj = new ToggleSetting(this, GlyphTextUtil.encode("edgejump"), true);
   public final FloatSliderSetting ycb = new FloatSliderSetting(this, GlyphTextUtil.encode("edgeparam"), 0.0F, 0.29F, 1.0F);
   public final ColorSetting opl = new ColorSetting(this, GlyphTextUtil.encode("colornormal"), 0.15F, 1.0F, 1.0F);
   public boolean wud;
   public JumpPredictionModule zft;

   public ParkourModule() {
      super(ModuleCategory.wld, ClientPlayStyle.zfw, GlyphTextUtil.encode("Parkour"));
      this.selectSettings(new String[]{GlyphTextUtil.encode("colornormal")});
      this.markClipboardHint();
      this.ycb.setVisibleWhen(this.xj);
   }

   @Override
   public void onEnable() {
      this.zft.setEnabled(true, true);
   }

   public void sau(KeyStateEvent var1) {
      if (this.wud && var1.by().getName().equals(GlyphTextUtil.plain("key.jump"))) {
         var1.ywz(true);
         this.wud = false;
      }
   }

   public void tjy(TickEvent var1) {
      this.wud = this.mxr();
   }

   private boolean mxr() {
      LocalPlayer var1 = Minecraft.getInstance().player;
      if (!var1.isShiftKeyDown() && var1.onGround() && !(var1.zza <= 0.0F)) {
         Vec3 var2 = var1.position();
         if (var1.yo == var2.y && !(var1.getDeltaMovement().y > 0.0)) {
            return this.xj.getValue() ? this.tpb() : this.xqt();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean xqt() {
      LocalPlayer var1 = Minecraft.getInstance().player;
      Vec3 var2 = var1.position();
      Vec3 var3 = var1.getDeltaMovement();
      AABB var4 = new AABB(var2.x + var3.x, var2.y - 1.0, var2.z + var3.z, var2.x + var3.x, var2.y, var2.z + var3.z);
      return !Minecraft.getInstance().level.getBlockCollisions(var1, var4).iterator().hasNext();
   }

   public boolean tpb() {
      LocalPlayer var1 = Minecraft.getInstance().player;
      Vec3 var2 = var1.position();
      float var3 = (Float)this.ycb.getValue();
      AABB var4 = new AABB(var2.x - var3, var2.y - 1.0, var2.z - var3, var2.x + var3, var2.y, var2.z + var3);
      return !Minecraft.getInstance().level.getBlockCollisions(var1, var4).iterator().hasNext();
   }
}
