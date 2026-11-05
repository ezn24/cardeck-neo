package io.github.shaonialife.cardeck.ui

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

/** 偏好由应用持久化，AppCompat 同步所有 Activity；服务/应用 Context 用相同语言。 */
object AppLanguage {
    fun current(context: Context): String {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            return context
                .getSystemService(android.app.LocaleManager::class.java)
                .applicationLocales
                .toLanguageTags()
        }
        return context
            .getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("appLanguage", "")
            .orEmpty()
    }

    fun set(
        context: Context,
        tag: String,
    ) {
        context
            .getSharedPreferences("settings", Context.MODE_PRIVATE)
            .edit()
            .putString("appLanguage", tag)
            .apply()
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }

    fun restore(context: Context) {
        if (android.os.Build.VERSION.SDK_INT < 33) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(current(context)))
        }
    }

    fun localized(context: Context): Context {
        val tag = current(context)
        if (tag.isEmpty()) return context
        val config = Configuration(context.resources.configuration)
        config.setLocales(LocaleList.forLanguageTags(tag))
        return context.createConfigurationContext(config)
    }
}
