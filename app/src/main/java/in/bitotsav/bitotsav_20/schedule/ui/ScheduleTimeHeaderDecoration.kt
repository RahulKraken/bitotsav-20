package `in`.bitotsav.bitotsav_20.schedule.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.os.Build
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDimensionPixelSizeOrThrow
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.graphics.withTranslation
import androidx.core.text.inSpans
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import java.lang.Exception

class ScheduleTimeHeaderDecoration (
    context: Context,
    events: List<Event>
) : ItemDecoration() {

    private val paint: TextPaint

    private val padding: Int
    private val width: Int
    private val textSize: Int
    private val meridiemTextSize: Int

    private val textSizeSpan: AbsoluteSizeSpan
    private val meridiemTextSizeSpan: AbsoluteSizeSpan

    init {
        val attrs = context.obtainStyledAttributes(R.style.time_headers, R.styleable.TimeHeader)
        paint = TextPaint(ANTI_ALIAS_FLAG).apply {
            color = attrs.getColorOrThrow(R.styleable.TimeHeader_android_textColor)
            // might fail, so to avoid silly crashes
            try {
                typeface = ResourcesCompat.getFont(
                    context,
                    attrs.getResourceIdOrThrow(R.styleable.TimeHeader_android_fontFamily)
                )
            } catch (_: Exception) {
                // peacefully ignore
            }
        }

        width = attrs.getDimensionPixelSizeOrThrow(R.styleable.TimeHeader_android_width)
        padding = attrs.getDimensionPixelSizeOrThrow(R.styleable.TimeHeader_android_padding)
        textSize = attrs.getDimensionPixelSizeOrThrow(R.styleable.TimeHeader_timeTextSize)
        meridiemTextSize = attrs.getDimensionPixelSizeOrThrow(R.styleable.TimeHeader_meridiemTextSize)
        // recycle attributes
        attrs.recycle()

        // used to build text with fixed sizes
        textSizeSpan = AbsoluteSizeSpan(textSize)
        meridiemTextSizeSpan = AbsoluteSizeSpan(meridiemTextSize)
    }

    private val distinctTimes =
        events.mapIndexed { index, event ->
            println("event.timing ${event.timing}")
            index to event.timing
        }.distinctBy { it.second }

    private val timeSlots: Map<Int, StaticLayout> =
        distinctTimes.map {
            println("it.second: ${it.second}")
            it.first to createHeader(it.second)
        }.toMap()

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (timeSlots.isEmpty() || parent.isEmpty()) return

        val parentPadding = parent.paddingTop

        var earliestPosition = Int.MAX_VALUE
        var previousHeaderPosition = -1
        var previousHasHeader = false
        var earliestChild: View? = null

        for (i in parent.childCount - 1 downTo 0) {
            val child = parent.getChildAt(i) ?: continue

            // if can't see the child continue
            if (child.y > parent.height || child.y + child.height < 0) continue

            // if you can see then get pos of child
            val position = parent.getChildAdapterPosition(child)
            if (position < 0) continue

            if (position < earliestPosition) {
                earliestPosition = position
                earliestChild = child
            }

            val header = timeSlots[position]
            if (header != null) {
                drawHeader(c, child, parentPadding, header, child.alpha, previousHasHeader)
                previousHeaderPosition = position
                previousHasHeader = true
            } else {
                previousHasHeader = false
            }
        }

        // this child needs a sticky header
        if (earliestChild != null && earliestPosition != previousHeaderPosition) {
            findHeaderBeforePosition(earliestPosition)?.let { stickyHeader ->
                previousHasHeader = previousHeaderPosition - earliestPosition == 1
                drawHeader(c, earliestChild, parentPadding, stickyHeader, 1f, previousHasHeader)
            }
        }
    }

    private fun findHeaderBeforePosition(position: Int): StaticLayout? {
        for (headerPos in timeSlots.keys.reversed()) {
            if (position > headerPos) return timeSlots[headerPos]
        }
        return null
    }

    private fun drawHeader(
        canvas: Canvas,
        child: View,
        parentPadding: Int,
        header: StaticLayout,
        headerAlpha: Float,
        previousHasHeader: Boolean
    ) {
        val childTop = child.y.toInt()
        val childBottom = childTop + child.height
        var top = (childTop + padding).coerceAtLeast(parentPadding)
        if (previousHasHeader) {
            top = top.coerceAtMost(childBottom - header.height - padding)
        }
        paint.alpha = (headerAlpha * 255).toInt()
        canvas.withTranslation(y = top.toFloat()) {
            header.draw(canvas)
        }
    }

    private fun createHeader(timing: String): StaticLayout {
        val t = timing.split(" ")
        val time = t[0].trim()
        val meridiem = t[1].trim()
        println("time: $time, meridiem: $meridiem")
        val text = SpannableStringBuilder().apply {
            inSpans(textSizeSpan) {
                append(time)
            }
            append(System.lineSeparator())
            inSpans(meridiemTextSizeSpan) {
                append(meridiem)
            }
        }
        return newStaticLayout(text, paint, width, Layout.Alignment.ALIGN_CENTER, 1f, 0f, false)
    }

    private fun newStaticLayout(
        source: CharSequence,
        paint: TextPaint,
        width: Int,
        alignment: Layout.Alignment,
        spacingmult: Float,
        spacingadd: Float,
        includepad: Boolean
    ): StaticLayout {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(source, 0, source.length, paint, width).apply {
                setAlignment(alignment)
                setLineSpacing(spacingadd, spacingmult)
                setIncludePad(includepad)
            }.build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(source, paint, width, alignment, spacingmult, spacingadd, includepad)
        }
    }
}