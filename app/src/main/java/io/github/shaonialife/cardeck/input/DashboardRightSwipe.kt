package io.github.shaonialife.cardeck.input

/** 仪表盘位于应用页左侧，向左滑才前进到应用页。 */
internal fun dashboardLeftSwipe(
    dx: Float,
    dy: Float,
    threshold: Float,
): Boolean = dx <= -threshold && -dx > kotlin.math.abs(dy) * 1.5f

/** HID 的抬手可能回传 0,0；在 MOVE 达标即触发，认领后吞掉剩余手势。 */
internal class DashboardSwipeGesture {
    enum class Result { NONE, TRIGGER, CONSUME }

    private var slot = -1
    private var startX = 0f
    private var startY = 0f
    private var claimed = false

    fun tracks(contactSlot: Int): Boolean = slot == contactSlot

    fun update(
        contactSlot: Int,
        down: Boolean,
        x: Float,
        y: Float,
        canStart: Boolean,
        threshold: Float,
    ): Result {
        if (slot < 0) {
            if (!down || !canStart) return Result.NONE
            slot = contactSlot
            startX = x
            startY = y
            return Result.NONE
        }
        if (contactSlot != slot) return Result.NONE
        if (claimed) {
            if (!down) reset()
            return Result.CONSUME
        }
        val trigger = dashboardLeftSwipe(x - startX, y - startY, threshold)
        if (trigger) {
            claimed = true
            if (!down) reset()
            return Result.TRIGGER
        }
        if (!down) reset()
        return Result.NONE
    }

    fun reset() {
        slot = -1
        claimed = false
    }
}
