package me.phantom.render;

import org.lwjgl.glfw.GLFW;

public class GlfwContextGuard implements AutoCloseable {
   public final long windowHandle = GLFW.glfwGetCurrentContext();

   public void close() {
      GLFW.glfwMakeContextCurrent(this.windowHandle);
   }
}
