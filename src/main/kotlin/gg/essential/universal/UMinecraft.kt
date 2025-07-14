package gg.essential.universal

import gg.essential.universal.utils.*

//#if FABRIC
//$$ import org.lwjgl.glfw.GLFW
//#endif

//#if FORGE && MC>=11502
//$$ import net.minecraft.client.util.NativeUtil
//#endif

object UMinecraft {
    @JvmStatic
    var guiScale: Int
        get() = getSettings().guiScale
        set(value) {
            getSettings().guiScale = value
        }

    @JvmField
    val isRunningOnMac: Boolean = MCMinecraft.isRunningOnMac

    @JvmStatic
    fun getMinecraft(): MCMinecraft {
        return MCMinecraft.getMinecraft()
    }

    @JvmStatic
    fun getWorld(): MCWorld? {
        return getMinecraft().theWorld
    }

    @JvmStatic
    fun getNetHandler(): MCClientNetworkHandler? {
        return getMinecraft().netHandler
    }

    @JvmStatic
    fun getPlayer(): MCEntityPlayerSP? {
        return getMinecraft().thePlayer
    }

    @JvmStatic
    fun getFontRenderer(): MCFontRenderer {
        return getMinecraft().fontRenderer
    }

    @JvmStatic
    fun getTime(): Long {
        return MCMinecraft.getSystemTime()
    }

    @JvmStatic
    fun getChatGUI(): MCChatScreen? = getMinecraft().ingameGUI?.chatGUI

    @JvmStatic
    fun getSettings(): MCSettings = getMinecraft().gameSettings
}
