package io.github.shaonialife.cardeck.render

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LayoutMotionTest {
    @Test
    fun visibilityOnlyChangesDoNotBlockTouchesForAnExtraAnimation() {
        assertFalse(LayoutMotion(0f, 0f, 1000L).isRunning(1001L))
    }

    @Test
    fun animationHasStableEndpointsAndNeverOvershoots() {
        val motion = LayoutMotion(0f, 1f, 1000L)
        assertEquals(0f, motion.valueAt(900L), 0f)
        assertEquals(1f, motion.valueAt(1500L), 0f)
        var previous = 0f
        for (elapsed in 0L..LayoutMotion.DURATION_MS) {
            val value = motion.valueAt(1000L + elapsed)
            assertTrue(value in previous..1f)
            previous = value
        }
        assertFalse(motion.isRunning(1320L))
    }

    @Test
    fun reversingHalfwayContinuesFromCurrentPosition() {
        val expanding = LayoutMotion(0f, 1f, 1000L)
        val current = expanding.valueAt(1160L)
        val shrinking = LayoutMotion(current, 0f, 1160L)
        assertEquals(current, shrinking.valueAt(1160L), 0f)
        assertTrue(shrinking.valueAt(1240L) < current)
        assertEquals(0f, shrinking.valueAt(1480L), 0f)
    }
}
