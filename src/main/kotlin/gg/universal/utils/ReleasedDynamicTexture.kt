package gg.essential.universal.utils

import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.TextureUtil
import net.minecraft.client.resources.IResourceManager

import java.awt.image.BufferedImage
import java.io.IOException


class ReleasedDynamicTexture(
    /** width of this icon in pixels  */
    private val width: Int,
    /** height of this icon in pixels  */
    private val height: Int
) : AbstractTexture() {
    var textureData: IntArray = IntArray(width * height)

    constructor(bufferedImage: BufferedImage) : this(bufferedImage.width, bufferedImage.height) {
        bufferedImage.getRGB(0, 0, bufferedImage.width, bufferedImage.height, textureData, 0, bufferedImage.width)
        updateDynamicTexture()
    }

    @Throws(IOException::class)
    override fun loadTexture(resourceManager: IResourceManager) {
    }

    fun updateDynamicTexture() {
        TextureUtil.uploadTexture(getGlTextureId(), textureData, width, height)
        textureData = IntArray(0)
    }

    init {
        TextureUtil.allocateTexture(getGlTextureId(), width, height)
    }
}