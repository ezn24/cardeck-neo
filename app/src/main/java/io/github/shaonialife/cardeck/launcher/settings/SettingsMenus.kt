package io.github.shaonialife.cardeck.launcher.settings

import androidx.fragment.app.Fragment
import io.github.shaonialife.cardeck.R
import io.github.shaonialife.cardeck.ui.UiStrings

/** 车机设置菜单注册表：车机双栏与手机二级菜单共用（菜单项/标题/Fragment 工厂）。 */
object SettingsMenus {
    const val DESKTOP = 0
    const val SUPERSAMPLE = 1
    const val ORIENTATION = 2
    const val DOCK = 3
    const val DEFAULT_MAP = 4
    const val DISPLAY = 5

    data class Item(
        val id: Int,
        val title: String,
        val desc: String,
        val iconRes: Int,
    )

    val ALL get() =
        listOf(
            Item(
                DESKTOP,
                UiStrings
                    .text(R.string.ui_066),
                UiStrings
                    .text(R.string.ui_139),
                R.drawable.ic_menu_grid,
            ),
            Item(
                SUPERSAMPLE,
                UiStrings
                    .text(R.string.ui_067),
                UiStrings
                    .text(R.string.ui_140),
                R.drawable.ic_menu_zoom,
            ),
            Item(
                ORIENTATION,
                UiStrings
                    .text(R.string.ui_068),
                UiStrings
                    .text(R.string.ui_141),
                R.drawable.ic_screen_rotation,
            ),
            Item(
                DOCK,
                UiStrings
                    .text(R.string.ui_069),
                UiStrings
                    .text(R.string.ui_142),
                R.drawable.ic_menu_dock,
            ),
            Item(
                DEFAULT_MAP,
                UiStrings
                    .text(R.string.ui_070),
                UiStrings
                    .text(R.string.ui_143),
                R.drawable.ic_menu_map,
            ),
            Item(
                DISPLAY,
                UiStrings
                    .text(R.string.ui_071),
                UiStrings
                    .text(R.string.ui_144),
                R.drawable.ic_menu_display,
            ),
        )

    fun titleOf(id: Int): String = ALL.first { it.id == id }.title

    fun createFragment(id: Int): Fragment =
        when (id) {
            DESKTOP -> DesktopPanelFragment()
            SUPERSAMPLE -> SupersamplePanelFragment()
            ORIENTATION -> OrientationPanelFragment()
            DOCK -> DockPanelFragment()
            DISPLAY -> DisplayPanelFragment()
            else -> DefaultMapPanelFragment()
        }
}
