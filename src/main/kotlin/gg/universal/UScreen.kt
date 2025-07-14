package gg.essential.universal

import gg.essential.universal.utils.MCScreen


import org.lwjgl.input.Mouse
import java.io.IOException

abstract class UScreen @JvmOverloads constructor(
    val restoreCurrentGuiOnClose: Boolean = false,
    val newGuiScale: Int = -1
) :
    MCScreen()
{
    private var guiScaleToRestore = -1
    private val screenToRestore: MCScreen? = if (restoreCurrentGuiOnClose) currentScreen else null

    final override fun initGui() {
        if (newGuiScale != -1) {
            guiScaleToRestore = UMinecraft.guiScale
            UMinecraft.guiScale = newGuiScale
            width = UResolution.scaledWidth
            height = UResolution.scaledHeight
        }
        initScreen(width, height)
    }

    final override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        onDrawScreen(mouseX, mouseY, partialTicks)
    }

    final override fun keyTyped(typedChar: Char, keyCode: Int) {
        onKeyPressed(keyCode, typedChar, UKeyboard.getModifiers())
    }

    final override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        onMouseClicked(mouseX.toDouble(), mouseY.toDouble(), mouseButton)
    }

    final override fun mouseMovedOrUp(mouseX: Int, mouseY: Int, state: Int) {
        onMouseReleased(mouseX.toDouble(), mouseY.toDouble(), state)
    }

    final override fun mouseClickMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) {
        onMouseDragged(mouseX.toDouble(), mouseY.toDouble(), clickedMouseButton, timeSinceLastClick)
    }

    final override fun handleMouseInput() {
        super.handleMouseInput()
        val scrollDelta = Mouse.getEventDWheel()
        if (scrollDelta != 0)
            onMouseScrolled(scrollDelta.toDouble())
    }

    final override fun updateScreen() {
        onTick()
    }

    final override fun onGuiClosed() {
        onScreenClose()
        if (screenToRestore != null) {
            //#if FORGE
            val event = net.minecraftforge.client.event.GuiOpenEvent(screenToRestore)
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                return
            //#endif
            UMinecraft.getMinecraft().currentScreen = screenToRestore
        }
        if (guiScaleToRestore != -1)
            UMinecraft.guiScale = guiScaleToRestore
    }

    final override fun drawWorldBackground(tint: Int) {
        onDrawBackground(tint)
    }

    constructor(restoreCurrentGuiOnClose: Boolean, newGuiScale: GuiScale) : this(restoreCurrentGuiOnClose, newGuiScale.ordinal)

    open fun initScreen(width: Int, height: Int) {
        super.initGui()
    }

    open fun onDrawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    open fun onKeyPressed(keyCode: Int, typedChar: Char, modifiers: UKeyboard.Modifiers?) {
        super.keyTyped(typedChar, keyCode)
    }

    open fun onKeyReleased(keyCode: Int, typedChar: Char, modifiers: UKeyboard.Modifiers?) {
    }

    open fun onMouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        super.mouseClicked(mouseX.toInt(), mouseY.toInt(), mouseButton)
    }

    open fun onMouseReleased(mouseX: Double, mouseY: Double, state: Int) {
        super.mouseMovedOrUp(mouseX.toInt(), mouseY.toInt(), state)
    }

    open fun onMouseDragged(x: Double, y: Double, clickedButton: Int, timeSinceLastClick: Long) {
        super.mouseClickMove(x.toInt(), y.toInt(), clickedButton, timeSinceLastClick)
    }

    open fun onMouseScrolled(delta: Double) {}

    open fun onTick() {
        super.updateScreen()
    }

    open fun onScreenClose() {
        super.onGuiClosed()
    }

    open fun onDrawBackground(tint: Int) {
        super.drawWorldBackground(tint)
    }

    companion object {
        @JvmStatic
        val currentScreen: MCScreen?
            get() = UMinecraft.getMinecraft().currentScreen

        @JvmStatic
        fun displayScreen(screen: MCScreen?) {
            UMinecraft.getMinecraft().displayGuiScreen(screen)
        }
    }
}
