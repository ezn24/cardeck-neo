package io.github.shaonialife.cardeck.notifications

internal fun shouldShowNotificationBanner(
    self: Boolean,
    ongoing: Boolean,
    summary: Boolean,
    media: Boolean,
    hasText: Boolean,
): Boolean = !self && !ongoing && !summary && !media && hasText
