package gg.essential.universal

import gg.essential.universal.utils.MCSChatPacket
import gg.essential.universal.wrappers.message.UTextComponent

object UPacket {
    @JvmStatic
    fun sendChatMessage(message: UTextComponent) {
        UMinecraft.getNetHandler()!!.handleChat(MCSChatPacket(message, false))
    }

    @JvmStatic
    fun sendActionBarMessage(message: UTextComponent) {}
}
