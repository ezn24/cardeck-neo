package io.github.shaonialife.cardeck.notifications

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NotificationBannerPolicyTest {
    @Test
    fun onlyContentNotificationsBecomeBanners() {
        assertTrue(shouldShowNotificationBanner(false, false, false, false, true))
        assertFalse(shouldShowNotificationBanner(true, false, false, false, true))
        assertFalse(shouldShowNotificationBanner(false, true, false, false, true))
        assertFalse(shouldShowNotificationBanner(false, false, true, false, true))
        assertFalse(shouldShowNotificationBanner(false, false, false, true, true))
        assertFalse(shouldShowNotificationBanner(false, false, false, false, false))
    }
}
