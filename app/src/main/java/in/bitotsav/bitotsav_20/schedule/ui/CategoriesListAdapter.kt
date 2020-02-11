package `in`.bitotsav.bitotsav_20.schedule.ui

import `in`.bitotsav.bitotsav_20.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView

class CategoriesListAdapter(
    private val context: Context,
    private val categories: List<String>
) : RecyclerView.Adapter<CategoriesListAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return CategoryViewHolder(layoutInflater, parent)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    inner class CategoryViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false)) {

        private var text: TextView? = null

        init {
            text = itemView.findViewById(R.id.item_category)
        }

        fun bind(category: String) {
            text?.text = category
            text?.background = context.getDrawable(R.drawable.tv_rounded_corners)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val gradientDrawable = text?.background as GradientDrawable
                gradientDrawable.color = when (category) {
                    "swaang" -> ColorStateList.valueOf(context.getColor(R.color.swaang))
                    "rhetoric" -> ColorStateList.valueOf(context.getColor(R.color.rhetoric))
                    "digitales" -> ColorStateList.valueOf(context.getColor(R.color.digitales))
                    "herald" -> ColorStateList.valueOf(context.getColor(R.color.herald))
                    "taabir" -> ColorStateList.valueOf(context.getColor(R.color.taabir))
                    "meraki" -> ColorStateList.valueOf(context.getColor(R.color.meraki))
                    "euphoria" -> ColorStateList.valueOf(context.getColor(R.color.euphoria))
                    "dansation" -> ColorStateList.valueOf(context.getColor(R.color.dansation))
                    "dhwani" -> ColorStateList.valueOf(context.getColor(R.color.dhwani))
                    "adaa" -> ColorStateList.valueOf(context.getColor(R.color.adaa))
                    else -> ColorStateList.valueOf(context.getColor(R.color.swaang))
                }
            } else {
                val drawable = context.getDrawable(R.drawable.tv_rounded_corners)
                val color = when (category) {
                    "swaang" -> ContextCompat.getColor(context, R.color.swaang)
                    "rhetoric" -> ContextCompat.getColor(context, R.color.rhetoric)
                    "digitales" -> ContextCompat.getColor(context, R.color.digitales)
                    "herald" -> ContextCompat.getColor(context, R.color.herald)
                    "taabir" -> ContextCompat.getColor(context, R.color.taabir)
                    "meraki" -> ContextCompat.getColor(context, R.color.meraki)
                    "euphoria" -> ContextCompat.getColor(context, R.color.euphoria)
                    "dansation" -> ContextCompat.getColor(context, R.color.dansation)
                    "dhwani" -> ContextCompat.getColor(context, R.color.dhwani)
                    "adaa" -> ContextCompat.getColor(context, R.color.adaa)
                    else -> ContextCompat.getColor(context, R.color.swaang)
                }
                DrawableCompat.setTint(drawable!!, color)
                text?.background = drawable
            }
        }
    }
}