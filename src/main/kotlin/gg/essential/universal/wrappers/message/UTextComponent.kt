
package gg.essential.universal.wrappers.message

import gg.essential.universal.UChat
import gg.essential.universal.utils.*

import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent
import net.minecraft.util.ChatStyle

@Suppress("MemberVisibilityCanBePrivate")
class UTextComponent : MCITextComponent {
    lateinit var component: MCITextComponent
        private set
    var text: String
        set(value) {
            field = value
            reInstance()
        }
    var formatted = true
        set(value) {
            field = value
            reInstance()
        }

    var clickAction: MCClickEventAction? = null
        set(value) {
            field = value
            reInstance()
        }
    var clickValue: String? = null
        set(value) {
            field = value
            reInstance()
        }
    var hoverAction: MCHoverEventAction? = null
        set(value) {
            field = value
            reInstance()
        }
    var hoverValue: Any? = null
        set(value) {
            field = value
            reInstance()
        }

    constructor(text: String) {
        this.text = text
        reInstance()
    }

    constructor(component: MCITextComponent) {
        this.component = component

        text = component.formattedText

        val clickEvent = component.chatStyle.chatClickEvent

        if (clickEvent != null) {
            clickAction = clickEvent.action
            clickValue = clickEvent.value
        }

        val hoverEvent = component.chatStyle.chatHoverEvent

        if (hoverEvent != null) {
            hoverAction = hoverEvent.action
            hoverValue = hoverEvent.value
        }
    }

    fun setClick(action: MCClickEventAction, value: String) = apply {
        clickAction = action
        clickValue = value
        reInstance()
    }

    fun setHover(action: MCHoverEventAction, value: Any) = apply {
        hoverAction = action
        hoverValue = value
        reInstance()
    }

    fun chat() {
        TODO()
    }

    fun actionBar() {
        TODO()
    }

    private fun reInstance() {
        component = MCStringTextComponent(text.formatIf(formatted))

        reInstanceClick()
        reInstanceHover()
    }

    private fun reInstanceClick() {
        if (clickAction == null || clickValue == null)
            return

        val event = ClickEvent(clickAction, clickValue!!.formatIf(formatted))
        component.chatStyle.chatClickEvent = event
    }

    private fun reInstanceHover() {
        if (hoverAction == null || hoverValue == null)
            return

        setHoverEventHelper(HoverEvent(
            hoverAction,
            MCStringTextComponent(hoverValue!! as String)
        ))
    }

    private fun setHoverEventHelper(event: HoverEvent) {
        component.chatStyle.chatHoverEvent = event
    }

    private fun String.formatIf(predicate: Boolean) = if (predicate) UChat.addColor(this) else this


    override fun setChatStyle(style: ChatStyle): IChatComponent = component.setChatStyle(style)

    override fun getChatStyle(): ChatStyle = component.chatStyle

    override fun appendText(text: String): IChatComponent = component.appendText(text)

    override fun appendSibling(other: IChatComponent): IChatComponent = component.appendSibling(other)

    override fun getUnformattedTextForChat(): String = component.unformattedTextForChat

    override fun getUnformattedText(): String = component.unformattedText

    override fun getFormattedText(): String = component.formattedText

    override fun createCopy(): IChatComponent = component.createCopy()

    override fun getSiblings(): MutableList<IChatComponent> = component.siblings as MutableList<IChatComponent>
    override fun iterator(): MutableIterator<IChatComponent> = component.iterator() as MutableIterator<IChatComponent>

    companion object {
        fun from(obj: Any): UTextComponent? {
            return when (obj) {
                is UTextComponent -> obj
                is String -> UTextComponent(obj)
                is MCITextComponent -> UTextComponent(obj)
                else -> null
            }
        }

        fun stripFormatting(string: String): String {
            return EnumChatFormatting.getTextWithoutFormattingCodes(string)
        }
    }
}
