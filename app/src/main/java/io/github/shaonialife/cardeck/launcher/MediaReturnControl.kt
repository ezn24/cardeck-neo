package io.github.shaonialife.cardeck.launcher

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import io.github.shaonialife.cardeck.render.OverlayCard

/** 浮动返回键覆盖在完整媒体画面上，不占用虚拟屏高度。 */
object MediaReturnControl {
    private val bitmap by lazy {
        Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888).also {
            val canvas = Canvas(it)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = 0xE6222430.toInt()
            canvas.drawCircle(32f, 32f, 31f, paint)
            paint.color = 0xFFF5F5F7.toInt()
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 4f
            paint.strokeCap = Paint.Cap.ROUND
            paint.strokeJoin = Paint.Join.ROUND
            canvas.drawPath(
                Path().apply {
                    moveTo(29f, 18f)
                    lineTo(17f, 30f)
                    lineTo(29f, 42f)
                    moveTo(18f, 30f)
                    lineTo(37f, 30f)
                    quadTo(48f, 30f, 46f, 44f)
                },
                paint,
            )
        }
    }
    private var current: OverlayCard? = null
    private var pressed = false

    fun card(): OverlayCard? {
        if (!VirtualLauncher.isMediaReturnVisible()) return null
        val pane = VirtualLauncher.mediaPaneBounds()
        val size =
            (minOf(VirtualLauncher.streamWidthPx, VirtualLauncher.streamHeightPx) * 40f / 540f)
                .coerceAtLeast(32f)
                .coerceAtMost(pane.width / 4f)
        val inset = size / 5f
        val right = pane.right - inset
        val bottom = pane.bottom - inset
        return current?.takeIf { it.right == right && it.bottom == bottom && it.left == right - size }
            ?: OverlayCard(bitmap, right - size, bottom - size, right, bottom).also { current = it }
    }

    fun onContact(
        down: Boolean,
        x: Float,
        y: Float,
        appGestureActive: Boolean,
    ): Boolean {
        val hit = card()?.contains(x, y) == true
        if (down) {
            if (pressed || (hit && !appGestureActive)) {
                pressed = true
                return true
            }
            return false
        }
        if (!pressed) return false
        pressed = false
        if (hit) VirtualLauncher.closeMediaPane()
        return true
    }

    fun reset() {
        pressed = false
        current = null
    }
}
