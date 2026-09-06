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
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
            LocalPlayer var2 = Minecraft.getInstance().player;
            Vec3 var3 = var2.getEyePosition();
            Vec3 var4 = var2.getLookAngle().scale(64.0);
            Vec3 var5 = var3.add(var4);
            AABB var6 = var2.getBoundingBox().expandTowards(var4).inflate(1.0, 1.0, 1.0);
            EntityHitResult var7 = GameInteractionUtil.raycastEntity(var2, var3, var5, var6, var0 -> !var0.isSpectator() && var0.isPickable(), 64.0, Entity::getBoundingBox);
            if (var7 != null && var7.getEntity() instanceof Player var8) {
               GameProfile var10 = ((OriginalProfileProvider)var8).getOrginalProfile();
               if (this.req.add(var10.id())) {
                  PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("module.playerhighlight.added"), var10.name());
               } else {
                  PhantomClient.uiManager.notifications().mfs(GlyphTextUtil.encode("module.playerhighlight.removed"), var10.name());
                  this.req.remove(var10.id());
               }
            }
         }
      } else {
         this.awr = false;
      }
   }

   public boolean dll(Entity var1) {
      if (var1 instanceof Player var2) {
         UUID var3 = ((OriginalProfileProvider)var2).getOrginalProfile().id();
         return this.req.contains(var3);
      } else {
         return false;
      }
   }
}
