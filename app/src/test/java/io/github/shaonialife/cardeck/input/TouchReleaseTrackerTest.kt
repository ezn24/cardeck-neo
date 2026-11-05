package io.github.shaonialife.cardeck.input

import io.github.shaonialife.cardeck.input.TouchReports.Contact
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TouchReleaseTrackerTest {
    @Test
    fun releaseWithClearedCoordinatesFinishesAppGridTap() {
        val tracker = TouchReleaseTracker()
        val down = Contact(0, 421, 130, true)
        assertEquals(down, tracker.accept(down))
        assertNull(tracker.accept(Contact(1, 0, 0, false)))
        assertEquals(down.copy(down = false), tracker.accept(Contact(0, 0, 0, false)))
        assertNull(tracker.accept(Contact(0, 0, 0, false)))
    }

    @Test
    fun releaseUsesLatestPositionAndPreservesExplicitCoordinates() {
        val tracker = TouchReleaseTracker()
        tracker.accept(Contact(0, 100, 100, true))
        tracker.accept(Contact(0, 180, 150, true))
        assertEquals(Contact(0, 180, 150, false), tracker.accept(Contact(0, 0, 0, false)))
        tracker.accept(Contact(0, 100, 100, true))
        val up = Contact(0, 101, 102, false)
        assertEquals(up, tracker.accept(up))
    }
}
