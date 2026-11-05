package io.github.shaonialife.cardeck.notifications

import android.app.Notification
import android.content.ComponentName
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.service.notification.StatusBarNotification
import android.text.TextPaint
import android.text.TextUtils
import io.github.shaonialife.cardeck.R
import io.github.shaonialife.cardeck.launcher.VirtualLauncher
import io.github.shaonialife.cardeck.launcher.settings.DisplayPrefs
import io.github.shaonialife.cardeck.render.OverlayCard
import io.github.shaonialife.cardeck.ui.UiStrings
import io.github.shaonialife.cardeck.util.Bg
import java.util.concurrent.atomic.AtomicInteger

/** 一张有时限的车机通知；绘字和读取其他 APK 资源都在后台，不保存通知内容。 */
object NotificationBannerController {
    private val handler = Handler(Looper.getMainLooper())
    private val generation = AtomicInteger()
    private var expiry: Runnable? = null

    @Volatile private var card: OverlayCard? = null

    @Volatile private var key: String? = null

    @Volatile private var component: ComponentName? = null
    private var held: OverlayCard? = null
    private var downX = 0f
    private var downY = 0f
    private var signature = ""
    private var lastPost = 0L

    fun post(
        context: Context,
        sbn: StatusBarNotification,
    ) {
        if (VirtualLauncher.appContext() == null || !DisplayPrefs.showCarNotifications(context)) return
        val notification = sbn.notification
        val extras = notification.extras
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString().orEmpty()
        val text =
            (
                extras.getCharSequence(Notification.EXTRA_BIG_TEXT)
                    ?: extras.getCharSequence(Notification.EXTRA_TEXT)
            )?.toString().orEmpty()
        if (!shouldShowNotificationBanner(
                sbn.packageName == context.packageName,
                sbn.isOngoing,
                notification.flags and Notification.FLAG_GROUP_SUMMARY != 0,
                notification.category == Notification.CATEGORY_TRANSPORT,
                title.isNotBlank() || text.isNotBlank(),
            )
        ) {
            return
        }
        if (notification.visibility == Notification.VISIBILITY_SECRET) return
        val now = android.os.SystemClock.elapsedRealtime()
        val nextSignature = "${sbn.key}|$title|$text"
        if (signature == nextSignature && now - lastPost < 5000) return
        signature = nextSignature
        lastPost = now
        val token = generation.incrementAndGet()
        val app = context.applicationContext
        Bg.run("notification-banner") {
            if (token != generation.get()) return@run
            val area = VirtualLauncher.appAreaRect() ?: return@run
            val scale = (minOf(VirtualLauncher.streamWidthPx, VirtualLauncher.streamHeightPx) / 540f).coerceIn(0.5f, 3f)
            val width = minOf(area.width() * 0.88f, 740f * scale).toInt().coerceAtLeast(1)
            val height = minOf(112f * scale, area.height() / 3f).toInt().coerceAtLeast(1)
            val locked = app.getSystemService(android.app.KeyguardManager::class.java).isKeyguardLocked
            val label =
                runCatching {
                    app.packageManager
                        .getApplicationInfo(sbn.packageName, 0)
                        .loadLabel(app.packageManager)
                        .toString()
                }.getOrDefault(sbn.packageName)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
            paint.color = 0xF2121215.toInt()
            canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 18f * scale, 18f * scale, paint)

            fun line(
                value: String,
                size: Float,
                y: Float,
                color: Int,
            ) {
                paint.color = color
                paint.textSize = size * scale
                val clipped =
                    TextUtils
                        .ellipsize(
                            value.replace('\n', ' '),
                            paint,
                            (width - 62f * scale).coerceAtLeast(1f),
                            TextUtils.TruncateAt.END,
                        ).toString()
                canvas.drawText(clipped, 16f * scale, y * scale, paint)
            }
            line(label, 13f, 25f, 0xFF8E8E93.toInt())
            line(if (locked) UiStrings.text(R.string.new_notification) else title, 19f, 55f, 0xFFF5F5F7.toInt())
            line(if (locked) "" else text, 15f, 84f, 0xFFE0E0E5.toInt())
            paint.color = 0xFF8E8E93.toInt()
            paint.strokeWidth = 2f * scale
            val closeX = width - 22f * scale
            canvas.drawLine(closeX - 5f * scale, 18f * scale, closeX + 5f * scale, 28f * scale, paint)
            canvas.drawLine(closeX + 5f * scale, 18f * scale, closeX - 5f * scale, 28f * scale, paint)
            val left = area.left + (area.width() - width) / 2f
            val top = area.top + 12f * scale
            val next = OverlayCard(bitmap, left, top, left + width, top + height)
            val launch = app.packageManager.getLaunchIntentForPackage(sbn.packageName)?.component
            handler.post {
                if (token != generation.get() || VirtualLauncher.appContext() == null) return@post
                expiry?.let { handler.removeCallbacks(it) }
                key = sbn.key
                component = launch
                card = next
                VirtualLauncher.setNotificationOverlay(next)
                expiry = Runnable { if (token == generation.get()) clear() }
                handler.postDelayed(expiry!!, 5000)
            }
        }
    }

    fun remove(notificationKey: String) {
        if (key == notificationKey) clear()
    }

    fun clear() {
        generation.incrementAndGet()
        expiry?.let { handler.removeCallbacks(it) }
        expiry = null
        card = null
        key = null
        component = null
        VirtualLauncher.setNotificationOverlay(null)
    }

    fun onContact(
        down: Boolean,
        x: Float,
        y: Float,
    ): Boolean {
        val visible = card
        if (held == null && (visible == null || !VirtualLauncher.isNotificationOverlayVisible() || !visible.contains(x, y))) return false
        if (down) {
            if (held == null) {
                held = visible
                downX = x
                downY = y
            }
            return true
        }
        val pressed = held
        held = null
        if (pressed != null &&
            pressed === card &&
            pressed.contains(x, y) &&
            kotlin.math.abs(x - downX) + kotlin.math.abs(y - downY) < 30f
        ) {
            val launch = component
            val close = x > pressed.right - minOf(pressed.bitmap.height * 0.6f, 64f)
            clear()
            if (!close && launch != null) Bg.run("notification-open-app") { VirtualLauncher.launchApp(launch) }
        }
        return true
    }
}
