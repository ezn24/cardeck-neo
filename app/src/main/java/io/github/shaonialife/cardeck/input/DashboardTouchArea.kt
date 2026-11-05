package io.github.shaonialife.cardeck.input

import io.github.shaonialife.cardeck.render.PaneBounds

internal enum class DashboardTouchArea { MAP, MEDIA, GAP }

internal fun dashboardTouchArea(
    map: PaneBounds,
    media: PaneBounds,
    x: Float,
    y: Float,
): DashboardTouchArea =
    when {
        map.contains(x, y) -> DashboardTouchArea.MAP
        media.contains(x, y) -> DashboardTouchArea.MEDIA
        else -> DashboardTouchArea.GAP
    }
