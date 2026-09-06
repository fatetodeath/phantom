package me.phantom.core.module;

import me.phantom.module.base.BaseModule;
import me.phantom.module.build.AutoExtinguishModule;
import me.phantom.module.build.AutoMlgModule;
import me.phantom.module.build.AutoPlaceModule;
import me.phantom.module.build.AutoToolModule;
import me.phantom.module.build.FastPlaceModule;
import me.phantom.module.build.NoBreakDelayModule;
import me.phantom.module.combat.AimAssistModule;
import me.phantom.module.combat.AntiFireballModule;
import me.phantom.module.combat.AutoClickerModule;
import me.phantom.module.combat.AutoMaceModule;
import me.phantom.module.combat.AutoShieldModule;
import me.phantom.module.combat.BowAimbotModule;
import me.phantom.module.combat.BreachSwapModule;
import me.phantom.module.combat.CrystalAuraModule;
import me.phantom.module.combat.DodgeModule;
import me.phantom.module.combat.HitboxModule;
import me.phantom.module.combat.ReachModule;
import me.phantom.module.combat.ShieldBreakerModule;
import me.phantom.module.combat.VeloCritModule;
import me.phantom.module.combat.WindChargeModule;
import me.phantom.module.combat.WTapModule;
import me.phantom.module.internal.AimHelperModule;
import me.phantom.module.internal.ArrayListHudModule;
import me.phantom.module.internal.BlatantFilterModule;
import me.phantom.module.internal.BugTrackingModule;
import me.phantom.module.internal.ConfigsButtonModule;
import me.phantom.module.internal.DeveloperModule;
import me.phantom.module.internal.FriendModule;
import me.phantom.module.internal.HudModule;
import me.phantom.module.internal.InterfaceModule;
import me.phantom.module.internal.KeybindManagerModule;
import me.phantom.module.internal.PacketJamModule;
import me.phantom.module.internal.QuickConsoleModule;
import me.phantom.module.internal.TeamsModule;
import me.phantom.module.internal.UninjectModule;
import me.phantom.module.inventory.AutoArmorModule;
import me.phantom.module.inventory.AutoCraftModule;
import me.phantom.module.inventory.AutoElytraModule;
import me.phantom.module.inventory.AutoEnchantModule;
import me.phantom.module.inventory.AutoHealModule;
import me.phantom.module.inventory.AutoPearlModule;
import me.phantom.module.inventory.AutoSoupModule;
import me.phantom.module.inventory.ChestStealerModule;
import me.phantom.module.inventory.EnderChestViewerModule;
import me.phantom.module.inventory.InventoryActionModule;
import me.phantom.module.inventory.InventoryClickerModule;
import me.phantom.module.inventory.InventorySortModule;
import me.phantom.module.misc.AntiAfkModule;
import me.phantom.module.misc.AntiBotModule;
import me.phantom.module.misc.AutoEscapeModule;
import me.phantom.module.misc.AutoFishModule;
import me.phantom.module.misc.AutoRespawnModule;
import me.phantom.module.misc.JumpPredictionModule;
import me.phantom.module.misc.NameProtectModule;
import me.phantom.module.misc.NoScoreboardModule;
import me.phantom.module.misc.ScreenMoveModule;
import me.phantom.module.movement.AntiFireModule;
import me.phantom.module.movement.AntiWebModule;
import me.phantom.module.movement.AutoStairsModule;
import me.phantom.module.movement.AutoWalkModule;
import me.phantom.module.movement.FlyModule;
import me.phantom.module.movement.KeepSprintModule;
import me.phantom.module.movement.NoJumpDelayModule;
import me.phantom.module.movement.NoPushModule;
import me.phantom.module.movement.ObstacleSpeedModule;
import me.phantom.module.movement.ParkourModule;
import me.phantom.module.movement.SafeWalkModule;
import me.phantom.module.movement.SprintModule;
import me.phantom.module.movement.VelocityModule;
import me.phantom.module.network.ArrowDodgeModule;
import me.phantom.module.network.BackTrackModule;
import me.phantom.module.network.BlinkModule;
import me.phantom.module.network.ClientSpooferModule;
import me.phantom.module.network.FakeLagModule;
import me.phantom.module.network.PingMeasurementModule;
import me.phantom.module.targets.IgnoreListModule;
import me.phantom.module.targets.PrioListModule;
import me.phantom.module.targets.TargetBotsModule;
import me.phantom.module.targets.TargetFriendsModule;
import me.phantom.module.targets.TargetMobsModule;
import me.phantom.module.targets.TargetPlayersModule;
import me.phantom.module.visuals.AntiVisionModule;
import me.phantom.module.visuals.BedEspModule;
import me.phantom.module.visuals.BlockEspModule;
import me.phantom.module.visuals.BlockOverlayModule;
import me.phantom.module.visuals.ChestEspModule;
import me.phantom.module.visuals.EspModule;
import me.phantom.module.visuals.FreecamModule;
import me.phantom.module.visuals.ItemEspModule;
import me.phantom.module.visuals.NametagModule;
import me.phantom.module.visuals.NearbyWarnModule;
import me.phantom.module.visuals.OutOfFOVArrowModule;
import me.phantom.module.visuals.PlayerHighlightModule;
import me.phantom.module.visuals.TntDetectorModule;
import me.phantom.module.visuals.TrajectoriesModule;
import me.phantom.module.visuals.XRayModule;
import me.phantom.util.GlyphTextUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
   public final BaseModule[] modules;
   public final Map<String, BaseModule> modulesByName = new HashMap<>();

   public ModuleManager() throws Throwable {
      this.modules = new BaseModule[]{
         new TargetFriendsModule(),
         new TargetMobsModule(),
         new TargetPlayersModule(),
         new TargetBotsModule(),
         new IgnoreListModule(),
         new PrioListModule(),
         new BugTrackingModule(),
         new InterfaceModule(),
         new EspModule(),
         new VeloCritModule(),
         new BedEspModule(),
         new FakeLagModule(),
         new ClientSpooferModule(),
         new BlatantFilterModule(),
         new DeveloperModule(),
         new NoPushModule(),
         new AntiBotModule(),
         new AimHelperModule(),
         new FriendModule(),
         new ConfigsButtonModule(),
         new KeybindManagerModule(),
         new NameProtectModule(),
         new InventoryActionModule(),
         new AntiFireballModule(),
         new EnderChestViewerModule(),
         new AutoShieldModule(),
         new AutoEnchantModule(),
         new AutoCraftModule(),
         new AntiWebModule(),
         new AutoFishModule(),
         new AutoMlgModule(),
         new AutoToolModule(),
         new AutoMaceModule(),
         new WindChargeModule(),
         new FastPlaceModule(),
         new AimAssistModule(),
         new AutoClickerModule(),
         new ShieldBreakerModule(),
         new AutoWalkModule(),
         new AutoPlaceModule(),
         new AutoExtinguishModule(),
         new BowAimbotModule(),
         new HitboxModule(),
         new ReachModule(),
         new AutoHealModule(),
         new AutoSoupModule(),
         new AutoArmorModule(),
         new ChestStealerModule(),
         new InventorySortModule(),
         new InventoryClickerModule(),
         new AntiAfkModule(),
         new AutoRespawnModule(),
         new NoScoreboardModule(),
         new ScreenMoveModule(),
         new AntiFireModule(),
         new AutoEscapeModule(),
         new AutoStairsModule(),
         new FlyModule(),
         new ObstacleSpeedModule(),
         new ParkourModule(),
         new SafeWalkModule(),
         new KeepSprintModule(),
         new SprintModule(),
         new VelocityModule(),
         new TeamsModule(),
         new WTapModule(),
         new BackTrackModule(),
         new BlinkModule(),
         new AntiVisionModule(),
         new BlockOverlayModule(),
         new NoBreakDelayModule(),
         new ChestEspModule(),
         new FreecamModule(),
         new ItemEspModule(),
         new JumpPredictionModule(),
         new NametagModule(),
         new NearbyWarnModule(),
         new ArrowDodgeModule(),
         new OutOfFOVArrowModule(),
         new TntDetectorModule(),
         new TrajectoriesModule(),
         new XRayModule(),
         new BlockEspModule(),
         new HudModule(),
         new PingMeasurementModule(),
         new ArrayListHudModule(),
         new CrystalAuraModule(),
         new PlayerHighlightModule(),
         new NoJumpDelayModule(),
         new PacketJamModule(),
         new DodgeModule(),
         new UninjectModule(),
         new BreachSwapModule(),
         new AutoElytraModule(),
         new AutoPearlModule(),
         new QuickConsoleModule()
      };
      Arrays.sort(this.modules, Comparator.comparingInt(var0 -> -var0.getName().length()));

      for (BaseModule var4 : this.modules) {
         this.modulesByName.put(var4.getName(), var4);

         for (Field var8 : var4.getClass().getFields()) {
            if (BaseModule.class.isAssignableFrom(var8.getType())) {
               for (BaseModule var12 : this.modules) {
                  if (var8.getType().equals(var12.getClass())) {
                     var8.set(var4, var12);
                  }
               }
            }
         }
      }
   }

   public void registerModules() {
      for (BaseModule var4 : this.modules) {
         var4.registerModules();
      }
   }

   public BaseModule getModuleByName(String var1) {
      BaseModule var2 = this.modulesByName.get(var1);
      if (var2 == null) {
         throw new IllegalArgumentException(GlyphTextUtil.encode("Module '") + var1 + GlyphTextUtil.encode("' not found."));
      } else {
         return var2;
      }
   }

    public List<BaseModule> getModulesByCategory(ModuleCategory var1) {
       ArrayList<BaseModule> var2 = new ArrayList<>(10);

      for (BaseModule var6 : this.modules) {
         if (var6.getCategory() == var1 && var6.lvr()) {
            var2.add(var6);
         }
      }

      var2.sort(Comparator.comparingInt(var0 -> -var0.getSettings().size()));
      return var2;
   }

   public BaseModule[] getModules() {
      return this.modules;
   }

   public List<BaseModule> getKeyboundModules() {
      ArrayList var1 = new ArrayList(this.modules.length);

      for (BaseModule var5 : this.modules) {
         if (var5.getCategory() != ModuleCategory.yy && var5.getKeyCode() != 0 && var5.isEnabled()) {
            var1.add(var5);
         }
      }

      return var1;
   }
}
