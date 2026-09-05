package me.phantom.util.combat;

import me.phantom.util.math.FloatPoint2D;

import me.phantom.module.combat.AimAssistModule;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.entity.LivingEntity;

public class AimTargetSnapshot {
   public LivingEntity target;
   public float minX = Float.MAX_VALUE;
   public float maxX = Float.MIN_VALUE;
   public float minY = Float.MAX_VALUE;
   public float maxY = Float.MIN_VALUE;
   public float yawDiff = Float.MAX_VALUE;
   public float pitchDiff = Float.MAX_VALUE;
   public float maxYawDiff;
   public float maxPitchDiff;
   public List<FloatPoint2D> visiblePoints = new LinkedList<>();

   public AimTargetSnapshot(AimAssistModule var1) {
   }
}
