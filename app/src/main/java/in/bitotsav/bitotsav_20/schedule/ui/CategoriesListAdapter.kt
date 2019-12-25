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
                    "Music" -> ColorStateList.valueOf(context.getColor(R.color.music))
                    "Dance" -> ColorStateList.valueOf(context.getColor(R.color.dance))
                    "Sports" -> ColorStateList.valueOf(context.getColor(R.color.sports))
                    "Action" -> ColorStateList.valueOf(context.getColor(R.color.action))
                    else -> ColorStateList.valueOf(context.getColor(R.color.music))
                }
            }
        }
    }
}