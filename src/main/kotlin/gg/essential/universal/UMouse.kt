package gg.essential.universal

import org.lwjgl.input.Mouse
import kotlin.math.max

object UMouse {
    @JvmStatic
    fun getTrueX(): Double {
        return Mouse.getX().toDouble()
    }

    @JvmStatic
    fun getScaledX(): Double {
        return getTrueX() * UResolution.scaledWidth / max(1, UResolution.windowWidth)
    }

    @JvmStatic
    fun getTrueY(): Double {
        return Mouse.getY().toDouble()
    }

    @JvmStatic
    fun getScaledY(): Double {
        return getTrueY() * UResolution.scaledHeight / max(1, UResolution.windowHeight)
    }
}
