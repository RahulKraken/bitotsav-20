package `in`.bitotsav.bitotsav_20.feed.ui

import `in`.bitotsav.bitotsav_20.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemDividerDecoration (
    context: Context,
    private val paddingLeft: Int,
    private val paddingRight: Int
) : RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null

    init {
        divider = ContextCompat.getDrawable(context, R.drawable.list_divider)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + paddingLeft
        val right = parent.width - parent.paddingRight - paddingRight

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + (divider?.intrinsicHeight ?: 0)

            divider?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}