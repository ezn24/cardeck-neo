package io.github.shaonialife.cardeck.input

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DashboardRightSwipeTest {
    @Test fun dashboardOnlyAdvancesToAppsOnLeftSwipe() {
        assertTrue(dashboardLeftSwipe(-100f, 2f, 72f))
        assertFalse(dashboardLeftSwipe(100f, 2f, 72f))
        assertFalse(dashboardLeftSwipe(-30f, 2f, 72f))
        assertFalse(dashboardLeftSwipe(-100f, 200f, 72f))
    }

    @Test fun leftMoveTriggersBeforeZeroCoordinateReleaseAndConsumesRemainder() {
        val swipe = DashboardSwipeGesture()
        assertEquals(DashboardSwipeGesture.Result.NONE, swipe.update(0, true, 1401f, 240f, true, 72f))
        assertEquals(DashboardSwipeGesture.Result.TRIGGER, swipe.update(0, true, 1200f, 250f, false, 72f))
        assertEquals(DashboardSwipeGesture.Result.CONSUME, swipe.update(0, true, 1100f, 250f, false, 72f))
        assertEquals(DashboardSwipeGesture.Result.CONSUME, swipe.update(0, false, 0f, 0f, false, 72f))
        assertFalse(swipe.tracks(0))
    }

    @Test fun inactiveSlotCannotEndGestureAndCrossingPanesDoesNotResetIt() {
        val swipe = DashboardSwipeGesture()
        swipe.update(0, true, 900f, 200f, true, 72f)
        assertEquals(DashboardSwipeGesture.Result.NONE, swipe.update(1, false, 0f, 0f, false, 72f))
        assertTrue(swipe.tracks(0))
        assertEquals(DashboardSwipeGesture.Result.TRIGGER, swipe.update(0, true, 700f, 210f, false, 72f))
    }

    @Test fun rightSwipeAndVerticalSwipeDoNotLeaveDashboard() {
        val swipe = DashboardSwipeGesture()
        swipe.update(0, true, 100f, 200f, true, 72f)
        assertEquals(DashboardSwipeGesture.Result.NONE, swipe.update(0, true, 400f, 210f, false, 72f))
        assertEquals(DashboardSwipeGesture.Result.NONE, swipe.update(0, false, 400f, 210f, false, 72f))
        swipe.update(0, true, 900f, 200f, true, 72f)
        assertEquals(DashboardSwipeGesture.Result.NONE, swipe.update(0, true, 800f, 400f, false, 72f))
        assertEquals(DashboardSwipeGesture.Result.NONE, swipe.update(0, false, 800f, 400f, false, 72f))
        assertFalse(swipe.tracks(0))
    }
}
