package io.github.shaonialife.cardeck.render

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MediaPaneTest {
    @Test
    fun swappingPreservesDisplaySizesAndLeavesExactlyOneGap() {
        for (portrait in listOf(false, true)) {
            val normal = mediaPaneBounds(1344, 756, if (portrait) 0 else 88, 500f, portrait)
            val swapped = mediaPaneBounds(1344, 756, if (portrait) 0 else 88, 500f, portrait, true)
            val map = mapPaneBounds(1344, 756, if (portrait) 0 else 88, 500f, portrait, true)
            assertEquals(normal.width, swapped.width)
            assertEquals(normal.height, swapped.height)
            assertEquals(DASH_GAP_PX, if (portrait) map.top - swapped.bottom else map.left - swapped.right, 0f)
            assertFalse(map.contains(swapped.left + 1, swapped.top + 1))
        }
    }

    @Test
    fun landscapePaneMatchesRightCardAndExcludesDockAndMap() {
        val pane = mediaPaneBounds(1344, 756, 88, 900f, false)
        assertEquals(912f, pane.left, 0f)
        assertEquals(416, pane.width)
        assertEquals(724, pane.height)
        assertFalse(pane.contains(80f, 100f))
        assertFalse(pane.contains(899f, 100f))
        assertTrue(pane.contains(1000f, 100f))
        assertFalse(pane.contains(1328f, 100f))
    }

    @Test
    fun portraitPaneFitsBelowMapAndAboveBottomDock() {
        val pane = mediaPaneBounds(756, 1256, 0, 830f, true)
        assertEquals(16f, pane.left, 0f)
        assertEquals(842f, pane.top, 0f)
        assertEquals(724, pane.width)
        assertEquals(398, pane.height)
        assertFalse(pane.contains(100f, 800f))
        assertFalse(pane.contains(100f, 1256f))
        assertTrue(pane.contains(100f, 1000f))
    }
}
