package me.phantom.gui.hud;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public class NotificationAction {
   public String label;
   public ByteBuffer iconImage;
   public Supplier<Boolean> dismissCondition;

   public NotificationAction(String var1, ByteBuffer var2, Supplier<Boolean> var3) {
      this.label = var1;
      this.iconImage = var2;
      this.dismissCondition = var3;
   }
}
