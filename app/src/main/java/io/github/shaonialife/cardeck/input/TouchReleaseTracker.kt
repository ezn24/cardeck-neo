package io.github.shaonialife.cardeck.input

import io.github.shaonialife.cardeck.input.TouchReports.Contact

/** 空槽和清零的抬手报文相同，以该槽是否有有效按下区分。 */
internal class TouchReleaseTracker {
    private val active = mutableMapOf<Int, Contact>()

    fun accept(report: Contact): Contact? {
        if (report.down) {
            active[report.slot] = report
            return report
        }
        val previous = active.remove(report.slot) ?: return null
        return if (report.xPixels == 0 && report.yPixels == 0) previous.copy(down = false) else report
    }

    fun reset() {
        active.clear()
    }
}
