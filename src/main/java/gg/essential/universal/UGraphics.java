package gg.essential.universal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import gg.essential.universal.utils.ReleasedDynamicTexture;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;


import org.lwjgl.opengl.GL11;

public class UGraphics {
    private static final Pattern formattingCodePattern = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");


    public static int ZERO_TEXT_ALPHA = 10;


    public UGraphics(){}

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static UGraphics getFromTessellator() {
        return new UGraphics();
    }

    public static void translate(float x, float y, float z) {
        translate(x, y, (double) z);
    }

    public static void translate(double x, double y, double z) {
        GL11.glTranslated(x, y, z);
    }

    public static void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    public static void scale(float x, float y, float z) {
        scale(x, y, (double) z);
    }

    public static void scale(double x, double y, double z) {
        GL11.glScaled(x, y, z);
    }

    public static Tessellator getTessellator() {
        return Tessellator.instance;
    }

    public static void draw() {
        getTessellator().draw();
    }

    public static void cullFace(int mode) {
        GL11.glCullFace(mode);
    }

    public static void enableLighting() {
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    public static void disableLighting() {
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    public static void disableLight(int mode) {
        GL11.glDisable(GL11.GL_LIGHT0 + mode);
    }

    public static void enableLight(int mode) {
        GL11.glEnable(GL11.GL_LIGHT0 + mode);
    }

    public static void enableBlend() {
        GL11.glEnable(GL11.GL_BLEND);
    }

    public static void disableTexture2D() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public static void disableAlpha() {
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    public static void shadeModel(int mode) {
        GL11.glShadeModel(mode);
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        OpenGlHelper.glBlendFunc(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
    }

    public static void enableTexture2D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void disableBlend() {
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void deleteTexture(int glTextureId) {
        GL11.glDeleteTextures(glTextureId);
    }

    public static void enableAlpha() {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    public static void bindTexture(int glTextureId) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTextureId);
    }

    public static int getStringWidth(String in) {
        return UMinecraft.getFontRenderer().getStringWidth(in);
    }

    public static void drawString(String text, float x, float y, int color, boolean shadow) {
        if ((color >> 24 & 255) <= 10) return;
        UMinecraft.getFontRenderer().drawString(text, (int)x, (int)y, color, shadow);
    }

    public static void drawString(String text, float x, float y, int color, int shadowColor) {
        if ((color >> 24 & 255) <= 10) return;
        UMinecraft.getFontRenderer().drawString(text, (int)x + 1, (int)y + 1, shadowColor, false);
        UMinecraft.getFontRenderer().drawString(text, (int)x, (int)y, color, false);
    }

    public static List<String> listFormattedStringToWidth(String str, int wrapWidth) {
        return listFormattedStringToWidth(str, wrapWidth, true);
    }

    public static List<String> listFormattedStringToWidth(String str, int wrapWidth, boolean safe) {
        if (safe) {
            String tmp = formattingCodePattern.matcher(str).replaceAll("");
            int max = 0;
            for (String s : tmp.split(" "))
                max = Math.max(max, getStringWidth(s));
            wrapWidth = Math.max(max, wrapWidth);
        }

        return UMinecraft.getFontRenderer().listFormattedStringToWidth(str, wrapWidth);
    }

    public static float getCharWidth(char character) {
        return UMinecraft.getFontRenderer().getCharWidth(character);
    }

    public static void glClear(int mode) {
        GL11.glClear(mode);
    }

    public static void glClearStencil(int mode) {
        GL11.glClearStencil(mode);
    }

    public static ReleasedDynamicTexture getTexture(InputStream stream) {
        try {
            return new ReleasedDynamicTexture(ImageIO.read(stream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Failed to read image");
    }

    public static ReleasedDynamicTexture getTexture(BufferedImage img) {
        return new ReleasedDynamicTexture(img);
    }

    public static ReleasedDynamicTexture getEmptyTexture() {
        return new ReleasedDynamicTexture(0, 0);
    }

    public static void glUseProgram(int program) {
        OpenGlHelper.func_153161_d(program);
    }

    public static boolean areShadersSupported() {
        return OpenGlHelper.func_153193_b();
    }

    public static int glCreateProgram() {
        return OpenGlHelper.func_153183_d();
    }

    public static int glCreateShader(int type) {
        return OpenGlHelper.func_153195_b(type);
    }

    public static void glCompileShader(int shaderIn) {
        OpenGlHelper.func_153170_c(shaderIn);
    }

    public static int glGetShaderi(int shaderIn, int pname) {
        return OpenGlHelper.func_153157_c(shaderIn, pname);
    }

    public static String glGetShaderInfoLog(int shader, int maxLen) {
        return OpenGlHelper.func_153158_d(shader, maxLen);
    }

    public static void glAttachShader(int program, int shaderIn) {
        OpenGlHelper.func_153178_b(program, shaderIn);
    }

    public static void glLinkProgram(int program) {
        OpenGlHelper.func_153179_f(program);
    }

    public static int glGetProgrami(int program, int pname) {
        return OpenGlHelper.func_153175_a(program, pname);
    }

    public static String glGetProgramInfoLog(int program, int maxLen) {
        return OpenGlHelper.func_153166_e(program, maxLen);
    }

    public static void color4f(float red, float green, float blue, float alpha) {
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void directColor3f(float red, float green, float blue) {
        GL11.glColor3f(red, green, blue);
    }

    public static void enableDepth() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void depthFunc(int mode) {
        GL11.glDepthFunc(mode);
    }

    public static void depthMask(boolean flag) {
        GL11.glDepthMask(flag);
    }

    public static void disableDepth() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public UGraphics begin(int glMode) {
        getTessellator().startDrawing(glMode);
        return this;
    }

    public UGraphics pos(double x, double y, double z) {
        getTessellator().addVertex(x, y, z);
        return this;
    }

    public UGraphics color(int red, int green, int blue, int alpha) {
        return color(red / 255f, green / 255f, blue / 255f, alpha / 255f);
    }

    public UGraphics color(float red, float green, float blue, float alpha) {
        getTessellator().setColorRGBA_F(red, green, blue, alpha);
        return this;
    }

    public UGraphics color(Color color) {
        return color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public UGraphics endVertex() {
        return this;
    }

    public UGraphics tex(double u, double v) {
        getTessellator().setTextureUV(u, v);
        return this;
    }

    // A collection of methods for always calling the OpenGL transformations rather than
    // delegating to the MatrixStack. In versions less than 1.15.2, these methods are no
    // different than transformation methods in the UGraphics class.
    //
    // The other transformation methods should be preferred.
    public static class GL {
        public static void pushMatrix() {
            GL11.glPushMatrix();
        }

        public static void popMatrix() {
            GL11.glPopMatrix();
        }

        public static void translate(float x, float y, float z) {
            translate(x, y, (double) z);
        }

        public static void translate(double x, double y, double z) {
            GL11.glTranslated(x, y, z);
        }

        public static void rotate(float angle, float x, float y, float z) {
            GL11.glRotatef(angle, x, y, z);
        }

        public static void scale(float x, float y, float z) {
            scale(x, y, (double) z);
        }

        public static void scale(double x, double y, double z) {
            GL11.glScaled(x, y, z);
        }
    }
}
