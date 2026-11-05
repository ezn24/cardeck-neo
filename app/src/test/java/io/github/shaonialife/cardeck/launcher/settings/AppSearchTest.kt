package io.github.shaonialife.cardeck.launcher.settings

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppSearchTest {
    @Test
    fun searchAcceptsBlankCaseInsensitiveAndFullWidthInput() {
        assertTrue(matchesAppSearch("  ", "Spotify", "com.spotify.music"))
        assertTrue(matchesAppSearch("ＳＰＯＴＩＦＹ", "Spotify", "com.spotify.music"))
        assertTrue(matchesAppSearch("spotify MUSIC", "Spotify", "com.spotify.music"))
    }

    @Test
    fun searchMatchesChineseOrPackageAndRequiresEveryTerm() {
        assertTrue(matchesAppSearch("地圖", "Google 地圖", "com.google.android.apps.maps"))
        assertTrue(matchesAppSearch("android.apps.maps", "Google 地圖", "com.google.android.apps.maps"))
        assertFalse(matchesAppSearch("Google spotify", "Google 地圖", "com.google.android.apps.maps"))
    }
}
