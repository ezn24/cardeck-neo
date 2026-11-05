package io.github.shaonialife.cardeck.render

/** 不依赖 Android 的时间曲线；中途反向从当前值起步，避免快速切屏跳变。 */
internal data class LayoutMotion(
    val from: Float,
    val to: Float,
    val startedAt: Long,
) {
    fun valueAt(now: Long): Float = from + (to - from) * progress(now - startedAt)

    fun isRunning(now: Long): Boolean = from != to && now - startedAt < DURATION_MS

    companion object {
        const val DURATION_MS = 320L

        fun progress(elapsed: Long): Float {
            val t = (elapsed.toFloat() / DURATION_MS).coerceIn(0f, 1f)
            return t * t * (3f - 2f * t)
        }
    }
}
