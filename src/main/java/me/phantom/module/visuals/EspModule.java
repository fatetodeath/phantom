package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.entity.AttackEntityEvent;
import me.phantom.event.render.FramebufferBindEvent;
import me.phantom.event.render.PreEntityRenderEvent;
import me.phantom.event.render.RenderAlphaEvent;
import me.phantom.event.render.WorldRenderMatricesEvent;
import me.phantom.event.render.WorldRenderOverlayEvent;
import me.phantom.event.render.WorldRenderSceneEvent;
import me.phantom.gui.core.GuiRenderContext;
import me.phantom.module.base.AbstractEspFeature;
import me.phantom.module.base.EntityTargetFilterModule;
import me.phantom.module.combat.AimAssistModule;
import me.phantom.module.combat.HitboxModule;
import me.phantom.module.internal.FriendModule;
import me.phantom.module.internal.TeamsModule;
import me.phantom.module.misc.BoxEspModule;
import me.phantom.module.misc.OrientedBoxModule;
import me.phantom.module.misc.TwoDEspModule;
import me.phantom.module.misc.WallhackModule;
import me.phantom.render.effect.BoneEspRenderer;
import me.phantom.render.effect.EntityOutlineEffect;
import me.phantom.setting.AbstractSetting;
import me.phantom.setting.CollapsibleSettingsGroup;
import me.phantom.setting.IntegerSliderSetting;
import me.phantom.setting.ToggleSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.WorldRendererDuck;
import me.phantom.util.WorldEntityUtil;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.CameraType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
public class EspModule extends EntityTargetFilterModule {
   public final AbstractEspFeature[] et = new AbstractEspFeature[]{new EntityOutlineEffect(this), new TwoDEspModule(this), new OrientedBoxModule(this), new BoxEspModule(this), new WallhackModule(this), new BoneEspRenderer(this)};
   public final ToggleSetting wco = new ToggleSetting(this, GlyphTextUtil.encode("self_render"), false);
   public final ToggleSetting abw = new ToggleSetting(this, GlyphTextUtil.encode("invisible"), false);
   public final ToggleSetting qxz = new ToggleSetting(this, GlyphTextUtil.encode("show_nametag"), false);
   public final IntegerSliderSetting sxo = new IntegerSliderSetting(this, GlyphTextUtil.encode("max_fps"), 30, 144, 250).kyc(var0 -> var0 >= 250 ? GlyphTextUtil.encode("Off") : String.valueOf(var0));
   public float jet = 0.0F;
   public HitboxModule zoc;
   public AimAssistModule ycl;
   public FriendModule bd;
   public TeamsModule onn;
   public PlayerHighlightModule gfyy;
   public boolean kor;

   public EspModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("ESP"));
      this.dcv.bdl(true);
      this.dcv.setVisibilitySupplier(() -> false);
      this.selectAllSettings();
      this.markClipboardHint();

      for (AbstractEspFeature var4 : this.et) {
         ArrayList var5 = new ArrayList();

         for (Field var9 : var4.getClass().getFields()) {
            if (AbstractSetting.class.isAssignableFrom(var9.getType())) {
               try {
                  AbstractSetting var10 = (AbstractSetting)var9.get(var4);
                  if (var10 != null) {
                     var5.add(var10);
                  }
               } catch (Throwable var11) {
                  PhantomClient.fatalError(GlyphTextUtil.encode("ESPMode"), GlyphTextUtil.encode("Failed to get esp mode value ") + var9.getName(), var11);
               }
            }
         }

         var4.cgi(new CollapsibleSettingsGroup(this, var4.name, var5, false));
      }

      this.et[0].wqp.bdl(true);
   }

   public void imex(AttackEntityEvent var1) {
      if ((Boolean)this.abw.getValue()) {
         var1.setCancelled(true);
         var1.ywz(false);
      }
   }

   @Override
   public String getValueSuffix() {
      StringBuilder var1 = new StringBuilder();

      for (int var2 = 0; var2 < this.et.length; var2++) {
         if (this.et[var2].isEnabled()) {
            var1.append(PhantomClient.translationManager.ry(GlyphTextUtil.encode("module.esp.") + this.et[var2].name + GlyphTextUtil.encode(".name")));
            var1.append(GlyphTextUtil.encode(", "));
         }
      }

      if (var1.length() > 0) {
         var1.setLength(var1.length() - 2);
      }

      return var1.toString();
   }

   public void xsb(RenderAlphaEvent var1) {
      for (AbstractEspFeature var5 : this.et) {
         if (var5.isEnabled()) {
            var5.je(var1);
         }
      }
   }

   public void lud(PreEntityRenderEvent var1) {
      for (AbstractEspFeature var5 : this.et) {
         if (var5.isEnabled()) {
            var5.wji(var1);
         }
      }
   }

   public void wj(WorldRenderOverlayEvent var1) {
      GuiRenderContext var2 = var1.getContext();
      if (!this.zoc.isEnabled() || !(Boolean)this.zoc.vpp.getValue()) {
         for (AbstractEspFeature var6 : this.et) {
            if (var6.isEnabled()) {
               var6.lfc(var2);
            }
         }
      }
   }

   public void vbi(WorldRenderMatricesEvent var1) {
      if (!this.zoc.isEnabled() || !(Boolean)this.zoc.vpp.getValue()) {
         this.jet %= 1.0F;
         GuiRenderContext var2 = PhantomClient.uiManager.getContext();
         PoseStack var3 = var1.getMatrices();
         // TODO: setRenderShadows() removed in 1.21.11
         this.kor = true;

         for (AbstractEspFeature var7 : this.et) {
            if (var7.isEnabled()) {
               var7.uu(var2, var3);
            }
         }

         // TODO: setRenderShadows() removed in 1.21.11
         this.kor = false;
      }
   }

   public List<Entity> ffs(GuiRenderContext var1) {
      LinkedList var2 = new LinkedList();
      this.bot(var1, var2::add);
      return var2;
   }

   public void bot(GuiRenderContext var1, Consumer<Entity> var2) {
      Minecraft var3 = Minecraft.getInstance();
      LocalPlayer var4 = var3.player;
      float var5 = var1.tickDelta;
      Vec3 var6 = GameInteractionUtil.getLerpedEyePos(var4, var5);
      Frustum var7 = ((WorldRendererDuck)var3.levelRenderer).getFrustum();
      EntityRenderDispatcher var8 = var3.getEntityRenderDispatcher();

      for (Entity var10 : WorldEntityUtil.getEntities()) {
         if ((
               !(var10 instanceof LocalPlayer)
                  || (Boolean)this.wco.getValue() && Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON
            )
            && this.csu(var10)
            && var3.levelRenderer.isSectionCompiledAndVisible(var10.blockPosition())
            && var8.shouldRender(var10, var7, var6.x, var6.y, var6.z)) {
            var2.accept(var10);
         }
      }
   }

   public void yna(FramebufferBindEvent var1) {
      for (AbstractEspFeature var5 : this.et) {
         if (var5.isEnabled() && var5.nsq()) {
            var1.setCancelled(true);
            break;
         }
      }
   }

   public void xli(WorldRenderSceneEvent var1) {
      for (AbstractEspFeature var5 : this.et) {
         if (var5.isEnabled()) {
            var5.kow(var1);
         }
      }
   }
}
