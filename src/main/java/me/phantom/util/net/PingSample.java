package me.phantom.util.net;

import java.util.Objects;

public final class PingSample {
   public final int ping;
   private final long time;

   public PingSample(int ping, long time) {
      this.ping = ping;
      this.time = time;
   }

   public final String toString() {
      return "PingSample[ping=" + this.ping + ", time=" + this.time + "]";
   }

   public final int hashCode() {
      return Objects.hash(this.ping, this.time);
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof PingSample)) {
         return false;
      } else {
         PingSample var2 = (PingSample)var1;
         return this.ping == var2.ping && this.time == var2.time;
      }
   }

   public int getPing() {
      return this.ping;
   }

   public long getTime() {
      return this.time;
   }
}
