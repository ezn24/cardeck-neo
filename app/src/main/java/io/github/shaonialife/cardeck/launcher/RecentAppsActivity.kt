package io.github.shaonialife.cardeck.launcher

import android.content.ComponentName
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import io.github.shaonialife.cardeck.R
import io.github.shaonialife.cardeck.util.Bg

/** 仅展示当前车机任务，图标/标签和 shell 查询全在后台。 */
class RecentAppsActivity : AppCompatActivity() {
    private data class Entry(
        val component: ComponentName,
        val title: String,
        val icon: Bitmap,
    )

    private lateinit var rows: LinearLayout
    private lateinit var status: TextView
    private lateinit var closeAll: TextView
    private lateinit var refresh: TextView
    private var entries = emptyList<Entry>()
    private var busy = false

    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

    private fun text(
        value: String,
        size: Float = 16f,
    ): TextView =
        TextView(this).apply {
            text = value
            textSize = size
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER_VERTICAL
        }

    private fun button(
        label: String,
        action: () -> Unit,
    ): TextView =
        text(label).apply {
            gravity = Gravity.CENTER
            minHeight = dp(48)
            setPadding(dp(14), 0, dp(14), 0)
            background = getDrawable(R.drawable.bg_app_row)
            isFocusable = true
            setOnClickListener { if (!busy) action() }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root =
            LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(18), dp(12), dp(18), dp(12))
                setBackgroundColor(0xFF090B12.toInt())
            }
        val header = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        header.addView(button(getString(R.string.recent_back)) { leave() })
        header.addView(
            text(getString(R.string.recent_apps), 21f).apply {
                setTypeface(typeface, Typeface.BOLD)
                setPadding(dp(16), 0, 0, 0)
            },
            LinearLayout.LayoutParams(0, dp(52), 1f),
        )
        refresh = button(getString(R.string.recent_refresh)) { load() }
        closeAll = button(getString(R.string.recent_close_all)) { close(entries.map { it.component.packageName }.toSet()) }
        header.addView(refresh)
        header.addView(closeAll, LinearLayout.LayoutParams(-2, dp(48)).apply { leftMargin = dp(10) })
        root.addView(header)
        root.addView(
            text(getString(R.string.recent_help), 13f).apply {
                setTextColor(0xFFADB4C6.toInt())
                setPadding(0, dp(8), 0, dp(8))
            },
        )
        status = text("", 15f).apply { setPadding(0, dp(8), 0, dp(8)) }
        root.addView(status)
        rows = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        root.addView(ScrollView(this).apply { addView(rows) }, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)
        onBackPressedDispatcher.addCallback(this) { leave() }
    }

    override fun onResume() {
        super.onResume()
        load()
    }

    override fun onStop() {
        super.onStop()
        if (!isFinishing) finishAndRemoveTask()
    }

    private fun leave() {
        VirtualLauncher.goGrid()
        finishAndRemoveTask()
    }

    private fun busy(value: Boolean) {
        busy = value
        refresh.isEnabled = !value
        closeAll.isEnabled = !value && entries.isNotEmpty()
        rows.alpha = if (value) 0.5f else 1f
    }

    private fun load(message: String? = null) {
        if (busy) return
        busy(true)
        status.text = message ?: getString(R.string.recent_loading)
        Bg.run("recent-apps") {
            val result =
                runCatching {
                    val cached = VirtualLauncher.peekCachedApps().orEmpty().associateBy { it.component.packageName }
                    VirtualLauncher.sessionApps().map { component ->
                        val pkg = component.packageName
                        val app = cached[pkg]
                        val info = runCatching { packageManager.getApplicationInfo(pkg, 0) }.getOrNull()
                        val title = app?.label ?: info?.loadLabel(packageManager)?.toString() ?: pkg
                        val icon = app?.icon ?: info?.loadIcon(packageManager) ?: packageManager.defaultActivityIcon
                        Entry(component, title, AppIcons.rounded(icon, 112))
                    }
                }
            runOnUiThread {
                if (isFinishing || isDestroyed) return@runOnUiThread
                result
                    .onSuccess {
                        entries = it
                        render()
                        status.text =
                            message ?: if (it.isEmpty()) getString(R.string.recent_empty) else getString(R.string.recent_count, it.size)
                    }.onFailure { status.setText(R.string.recent_read_failed) }
                busy(false)
            }
        }
    }

    private fun render() {
        rows.removeAllViews()
        entries.forEach { entry ->
            val row =
                LinearLayout(this).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    minimumHeight = dp(76)
                    setPadding(dp(14), dp(10), dp(10), dp(10))
                    background = getDrawable(R.drawable.bg_app_row)
                }
            val open =
                LinearLayout(this).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    isFocusable = true
                    contentDescription = getString(R.string.recent_open_app, entry.title)
                    setOnClickListener { if (!busy) open(entry) }
                }
            open.addView(
                ImageView(this).apply {
                    setImageBitmap(entry.icon)
                    importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                },
                LinearLayout.LayoutParams(dp(44), dp(44)),
            )
            open.addView(
                text(entry.title, 18f).apply {
                    maxLines = 1
                    ellipsize = android.text.TextUtils.TruncateAt.END
                    setPadding(dp(14), 0, dp(14), 0)
                },
                LinearLayout.LayoutParams(0, dp(52), 1f),
            )
            row.addView(open, LinearLayout.LayoutParams(0, -2, 1f))
            row.addView(
                button(getString(R.string.recent_close)) { close(setOf(entry.component.packageName)) }.apply {
                    contentDescription = getString(R.string.recent_close_app, entry.title)
                },
            )
            rows.addView(row, LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = dp(10) })
        }
    }

    private fun open(entry: Entry) {
        busy(true)
        Bg.run("recent-open") {
            val ok = runCatching { VirtualLauncher.launchApp(entry.component) }.getOrDefault(false)
            runOnUiThread {
                if (isFinishing || isDestroyed) return@runOnUiThread
                if (ok) {
                    finishAndRemoveTask()
                } else {
                    busy(false)
                    status.setText(R.string.recent_open_failed)
                }
            }
        }
    }

    private fun close(packages: Set<String>) {
        if (packages.isEmpty()) return
        busy(true)
        Bg.run("recent-close") {
            val ok = runCatching { VirtualLauncher.closeVehicleApps(packages) }.getOrDefault(false)
            runOnUiThread {
                if (isFinishing || isDestroyed) return@runOnUiThread
                busy(false)
                load(if (ok) null else getString(R.string.recent_close_failed))
            }
        }
    }
}
