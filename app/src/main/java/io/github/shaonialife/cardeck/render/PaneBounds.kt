package io.github.shaonialife.cardeck.render

/** 流像素坐标唯一来源：建屏、合成与触摸使用相同的整数边界。 */
data class PaneBounds(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
) {
    val width: Int get() = (right - left).toInt().coerceAtLeast(1)
    val height: Int get() = (bottom - top).toInt().coerceAtLeast(1)

    fun contains(
        x: Float,
        y: Float,
    ): Boolean = x >= left && x < right && y >= top && y < bottom
}

fun mediaPaneBounds(
    streamWidth: Int,
    appHeight: Int,
    dockWidth: Int,
    splitCenter: Float,
    portrait: Boolean,
    swapped: Boolean = false,
): PaneBounds {
    val start = (splitCenter + DASH_GAP_PX / 2f).toInt().toFloat()
    val margin = CARD_MARGIN_PX.toFloat()
    if (swapped) {
        val regular = mediaPaneBounds(streamWidth, appHeight, dockWidth, splitCenter, portrait, false)
        return if (portrait) {
            PaneBounds(margin, margin, streamWidth - margin, margin + regular.height)
        } else {
            PaneBounds(dockWidth + margin, margin, dockWidth + margin + regular.width, appHeight - margin)
        }
    }
    return if (portrait) {
        PaneBounds(margin, start, streamWidth - margin, appHeight - margin)
    } else {
        PaneBounds(start.coerceAtLeast(dockWidth + margin), margin, streamWidth - margin, appHeight - margin)
    }
}

fun mapPaneBounds(
    streamWidth: Int,
    appHeight: Int,
    dockWidth: Int,
    splitCenter: Float,
    portrait: Boolean,
    swapped: Boolean,
): PaneBounds {
    val margin = CARD_MARGIN_PX.toFloat()
    val media = mediaPaneBounds(streamWidth, appHeight, dockWidth, splitCenter, portrait, swapped)
    return if (portrait) {
        if (swapped) {
            PaneBounds(margin, media.bottom + DASH_GAP_PX, streamWidth - margin, appHeight - margin)
        } else {
            PaneBounds(margin, margin, streamWidth - margin, media.top - DASH_GAP_PX)
        }
    } else {
        if (swapped) {
            PaneBounds(media.right + DASH_GAP_PX, margin, streamWidth - margin, appHeight - margin)
        } else {
            PaneBounds(dockWidth + margin, margin, media.left - DASH_GAP_PX, appHeight - margin)
        }
    }
}
