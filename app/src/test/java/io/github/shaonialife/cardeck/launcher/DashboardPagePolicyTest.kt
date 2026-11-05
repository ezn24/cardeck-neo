package io.github.shaonialife.cardeck.launcher

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DashboardPagePolicyTest {
    @Test
    fun transitionKeepsExactStartMidpointAndEndForOverlayLayers() {
        assertEquals(0f, dashboardPageFraction(0, 0f), 0f)
        assertEquals(0.5f, dashboardPageFraction(0, 0.5f), 0f)
        assertEquals(1f, dashboardPageFraction(0, 1f), 0f)
        assertEquals(1f, dashboardPageFraction(3, 0.5f), 0f)
    }

    @Test fun appToDashboardDragKeepsMoveAndUpFlowingThroughLayoutPreview() {
        assertFalse(suppressTouchDuringLayout(true, true, true))
        assertFalse(suppressTouchDuringLayout(true, true, false))
        assertFalse(suppressTouchDuringLayout(false, true, true))
        assertTrue(suppressTouchDuringLayout(true, false, false))
        assertTrue(suppressTouchDuringLayout(false, false, true))
        assertFalse(suppressTouchDuringLayout(false, false, false))
    }
}
