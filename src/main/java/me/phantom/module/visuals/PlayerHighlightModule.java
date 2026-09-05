package me.phantom.module.visuals;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.PhantomClient;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.setting.ColorSetting;
import me.phantom.setting.KeybindSetting;
import me.phantom.util.GameInteractionUtil;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.OriginalProfileProvider;

import com.mojang.authlib.GameProfile;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
public class PlayerHighlightModule extends BaseModule {
   public final Set<UUID> req = new HashSet<>();
   public final KeybindSetting xqf = new KeybindSetting(this, GlyphTextUtil.encode("keybind"), 64);
   public final ColorSetting imc = new ColorSetting(this, GlyphTextUtil.encode("color"), Color.ORANGE);
   public boolean awr = false;

   public PlayerHighlightModule() {
      super(ModuleCategory.yxw, ClientPlayStyle.or, GlyphTextUtil.encode("PlayerHighlight"));
   }

   public void tjy(TickEvent var1) {
      if (this.xqf.rrq()) {
         if (!this.awr) {
            this.awr = true;
            ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
            Vec3d var3 = var2.getEyePos();
            Vec3d var4 = var2.getRotationVector().multiply(64.0);
            Vec3d var5 = var3.add(var4);
            Box var6 = var2.getBoundingBox().stretch(var4).expand(1.0, 1.0, 1.0);
            EntityHitResult var7 = GameInteractionUtil.raycastEntity(var2, var3, var5, var6, var0 -> !var0.isSpectator() && var0.canHit(), 64.0, Entity::getBoundingBox);
            if (var7 != null && var7.getEntity() instanceof PlayerEntity var8) {
               GameProfile var10 = ((OriginalProfileProvider)var8).getOrginalProfile();
               if (this.req.add(var10.getId())) {
                  PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("module.playerhighlight.added"), var10.getName());
               } else {
                  PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("module.playerhighlight.removed"), var10.getName());
                  this.req.remove(var10.getId());
               }
            }
         }
      } else {
         this.awr = false;
      }
   }

   public boolean dll(Entity var1) {
      if (var1 instanceof PlayerEntity var2) {
         UUID var3 = ((OriginalProfileProvider)var2).getOrginalProfile().getId();
         return this.req.contains(var3);
      } else {
         return false;
      }
   }
}
