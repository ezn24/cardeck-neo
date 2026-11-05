package io.github.shaonialife.cardeck.ui

import android.content.Context

/** 设置菜单等非 Activity 对象也从同一份 Android 语言资源取文案。 */
object UiStrings {
    private lateinit var app: Context

    fun initialize(context: Context) {
        app = context.applicationContext
    }

    fun text(id: Int): String = AppLanguage.localized(app).getString(id)

    fun format(
        id: Int,
        vararg args: Any,
    ): String = AppLanguage.localized(app).getString(id, *args)
}
