package me.phantom.render.effect;

import me.phantom.core.PhantomClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class BlurShaderProgram {
   public int gyfk;
   private int rdl;
   private int gu;
   private final float[] dcy = new float[]{
      -1.0F,
      -1.0F,
      0.0F,
      0.0F,
      0.0F,
      1.0F,
      -1.0F,
      0.0F,
      1.0F,
      0.0F,
      1.0F,
      1.0F,
      0.0F,
      1.0F,
      1.0F,
      1.0F,
      1.0F,
      0.0F,
      1.0F,
      1.0F,
      -1.0F,
      1.0F,
      0.0F,
      0.0F,
      1.0F,
      -1.0F,
      -1.0F,
      0.0F,
      0.0F,
      0.0F
   };

   public BlurShaderProgram() {
      this.wnl();
   }

   public void wnl() {
      if (this.gyfk != 0) {
         GL30.glDeleteProgram(this.gyfk);
      }

      int var1 = GL20.glCreateShader(35633);
      GL20.glShaderSource(
         var1,
         "#version 330 core\nlayout (location = 0) in vec3 aPos;\nlayout (location = 1) in vec2 aTexCoord;\n\nout vec2 TexCoord;\n\nvoid main() {\n    gl_Position = vec4(aPos, 1.0);\n    TexCoord = aTexCoord;\n}\n"
      );
      GL20.glCompileShader(var1);
      this.qsa(var1, "VERTEX");
      int var2 = GL20.glCreateShader(35632);
      GL20.glShaderSource(
         var2,
         "         #version 330 core\n         out vec4 FragColor;\n\n         in vec2 TexCoord;\n         uniform sampler2D tex;\n         uniform float blurRadius;\n         uniform vec2 singleStepOffset;\n\n         void main() {\nvec4 sum = vec4(0.0);\nsum += texture(tex, TexCoord) * 0.109379;\nsum += texture(tex, TexCoord + singleStepOffset * 1.485004) * 0.208183;\nsum += texture(tex, TexCoord - singleStepOffset * 1.485004) * 0.208183;\nsum += texture(tex, TexCoord + singleStepOffset * 3.465057) * 0.170786;\nsum += texture(tex, TexCoord - singleStepOffset * 3.465057) * 0.170786;\nsum += texture(tex, TexCoord + singleStepOffset * 5.000000) * 0.066342;\nsum += texture(tex, TexCoord - singleStepOffset * 5.000000) * 0.066342;\nfloat l = length(sum);\n \t\t  \tFragColor = vec4(sum.r / l, sum.g / l, sum.b / l, sum.a);\n         }\n"
      );
      GL20.glCompileShader(var2);
      this.qsa(var2, "FRAGMENT");
      this.gyfk = GL20.glCreateProgram();
      GL20.glAttachShader(this.gyfk, var1);
      GL20.glAttachShader(this.gyfk, var2);
      GL20.glLinkProgram(this.gyfk);
      this.qsa(this.gyfk, "PROGRAM");
      GL20.glDeleteShader(var1);
      GL20.glDeleteShader(var2);
      this.rdl = GL30.glGenVertexArrays();
      this.gu = GL15.glGenBuffers();
      GL30.glBindVertexArray(this.rdl);
      GL15.glBindBuffer(34962, this.gu);
      GL15.glBufferData(34962, this.dcy, 35044);
      GL20.glVertexAttribPointer(0, 3, 5126, false, 20, 0L);
      GL20.glEnableVertexAttribArray(0);
      GL20.glVertexAttribPointer(1, 2, 5126, false, 20, 12L);
      GL20.glEnableVertexAttribArray(1);
      GL15.glBindBuffer(34962, 0);
      GL30.glBindVertexArray(0);
   }

   public void rg(int var1, int var2, float var3, int var4) {
      int var5 = GL30.glGetInteger(35725);
      int var6 = GL30.glGetInteger(34016);
      int var7 = GL30.glGetInteger(32873);
      int var8 = GL30.glGetInteger(34229);
      GL20.glUseProgram(this.gyfk);
      GL13.glActiveTexture(33984);
      GL11.glBindTexture(3553, var4);
      GL20.glUniform1i(GL20.glGetUniformLocation(this.gyfk, "tex"), 0);
      GL30.glBindVertexArray(this.rdl);
      GL20.glUniform2f(GL20.glGetUniformLocation(this.gyfk, "singleStepOffset"), var1 == 0 ? 0.0F : 1.0F / var1, var2 == 0 ? 0.0F : 1.0F / var2);
      GL11.glDrawArrays(4, 0, 6);
      GL30.glBindVertexArray(var8);
      GL13.glActiveTexture(var6);
      GL11.glBindTexture(3553, var7);
      GL20.glUseProgram(var5);
   }

   private void qsa(int var1, String var2) {
      if (!var2.equals("PROGRAM")) {
         if (GL20.glGetShaderi(var1, 35713) == 0) {
            PhantomClient.clientLogger.cim("BlurRenderer", "Shader compiler error (" + var2 + "): " + GL20.glGetShaderInfoLog(var1));
         }
      } else if (GL20.glGetProgrami(var1, 35714) == 0) {
         PhantomClient.clientLogger.cim("BlurRenderer", "Shader link error: " + GL20.glGetProgramInfoLog(var1));
      }
   }
}
