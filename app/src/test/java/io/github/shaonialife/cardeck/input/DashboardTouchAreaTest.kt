package io.github.shaonialife.cardeck.input

import io.github.shaonialife.cardeck.launcher.VirtualLauncher
import io.github.shaonialife.cardeck.render.mapPaneBounds
import io.github.shaonialife.cardeck.render.mediaPaneBounds
import org.junit.Assert.assertEquals
import org.junit.Test

class DashboardTouchAreaTest {
    @Test
    fun mediaAndMapTargetsFollowBothOrientationAndSwappedPositions() {
        for (portrait in listOf(false, true)) {
            for (swapped in listOf(false, true)) {
                val width = if (portrait) 756 else 1344
                val height = if (portrait) 1256 else 756
                val dock = if (portrait) 0 else 88
                val mainLength = if (portrait) height else width - dock
                val center =
                    VirtualLauncher
                        .dashSplitGeom(mainLength, VirtualLauncher.DASH_SPLIT_DEFAULT_FRACTION)
                        .splitCenter + dock
                val map = mapPaneBounds(width, height, dock, center, portrait, swapped)
                val media = mediaPaneBounds(width, height, dock, center, portrait, swapped)
                assertEquals(
                    DashboardTouchArea.MEDIA,
                    dashboardTouchArea(map, media, (media.left + media.right) / 2, (media.top + media.bottom) / 2),
                )
                assertEquals(
                    DashboardTouchArea.MAP,
                    dashboardTouchArea(map, media, (map.left + map.right) / 2, (map.top + map.bottom) / 2),
                )
                assertEquals(DashboardTouchArea.GAP, dashboardTouchArea(map, media, 0f, 0f))
            }
        }
    }
}
