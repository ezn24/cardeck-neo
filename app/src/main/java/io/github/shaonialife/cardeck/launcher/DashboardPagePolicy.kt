package io.github.shaonialife.cardeck.launcher

internal fun dashboardPageFraction(
    position: Int,
    offset: Float,
): Float = (position + offset).coerceIn(0f, 1f)

/** 手指驱动 pager 时必须继续注入 MOVE/UP；只有自动动画才屏蔽静态坐标。 */
internal fun suppressTouchDuringLayout(
    pageAnimating: Boolean,
    userDragging: Boolean,
    composerAnimating: Boolean,
): Boolean = !userDragging && (pageAnimating || composerAnimating)
