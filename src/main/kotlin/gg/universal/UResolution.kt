package gg.essential.universal

import net.minecraft.client.gui.ScaledResolution

object UResolution {
    private var scaledResolution: ScaledResolution? = null
    private var cachedWidth: Int? = null
    private var cachedHeight: Int? = null

    @JvmStatic
    val windowWidth: Int
        get() {
            return UMinecraft.getMinecraft().displayWidth
        }

    @JvmStatic
    val windowHeight: Int
        get() {
            return UMinecraft.getMinecraft().displayHeight
        }

    private fun get(): ScaledResolution {
        if (cachedHeight != windowHeight || cachedWidth != windowWidth || scaledResolution == null) scaledResolution = ScaledResolution(UMinecraft.getMinecraft(), UMinecraft.getMinecraft().displayWidth, UMinecraft.getMinecraft().displayHeight)
        return scaledResolution!!
    }

    @JvmStatic
    val scaledWidth: Int
        get() {
            return get().scaledWidth
        }

    @JvmStatic
    val scaledHeight: Int
        get() {
            return get().scaledHeight
        }

    @JvmStatic
    val scaleFactor: Double
        get() {
            return get().scaleFactor.toDouble()
        }
}
