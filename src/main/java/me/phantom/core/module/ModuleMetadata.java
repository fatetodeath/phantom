package me.phantom.core.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ModuleMetadata {
   	double x() default 0.0;

   double y() default 0.0;

   double z() default 0.0;

   double fx() default 0.0;

   double pkm() default 0.0;

   double nsm() default 0.0;
}
