package io.github.shaonialife.cardeck.launcher.settings

import java.text.Normalizer
import java.util.Locale

internal fun matchesAppSearch(
    query: String,
    label: String,
    packageName: String,
): Boolean {
    fun normalized(value: String) = Normalizer.normalize(value, Normalizer.Form.NFKC).lowercase(Locale.ROOT)
    val terms = normalized(query).trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
    val haystack = normalized("$label $packageName")
    return terms.all { it in haystack }
}
