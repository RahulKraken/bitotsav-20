package `in`.bitotsav.bitotsav_20.schedule.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class ScheduleDayAdapter(
    private var events: List<Event>?,
    private var context: Context) : RecyclerView.Adapter<ScheduleDayAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(context)
        return DayViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return if (events.isNullOrEmpty()) 0 else events!!.size
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val event = events?.get(position)
        holder.bind(event!!)
    }

    fun setData(events: List<Event>) {
        this.events = events
    }

    inner class DayViewHolder(
        inflater: LayoutInflater, val parent: ViewGroup
    ): RecyclerView.ViewHolder(inflater.inflate(R.layout.item_schedule, parent, false)), View.OnClickListener {
        private var eventName: TextView? = null
        private var eventVenueDuration: TextView? = null
        private var registerLabelIcon: ImageView? = null
        private var registerLabel: TextView? = null
        private var categoryList: RecyclerView? = null

        init {
            eventName = itemView.findViewById(R.id.event_name)
            eventVenueDuration = itemView.findViewById(R.id.event_venue_duration)
            registerLabelIcon = itemView.findViewById(R.id.register_icon)
            registerLabel = itemView.findViewById(R.id.register_label)
            categoryList = itemView.findViewById(R.id.rv_categories)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val event = events?.get(adapterPosition)
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtra(context.getString(R.string.event_intent_pass_key), event)
            context.startActivity(intent)
        }

        fun bind(event: Event) {
            // format event name format: All caps to first letter caps
            val ename = event.name[0].toUpperCase() + event.name.substring(1, event.name.length).toLowerCase()
            eventName?.text = ename
            // format datetime and venue
            val evd = "${event.venue} - ${event.duration!!.split(":", limit = 2)[1].trim()}"
            eventVenueDuration?.text = evd
            if (event.category != "Formal") {
                registerLabel?.visibility = View.GONE
                registerLabelIcon?.visibility = View.GONE
            } else {
                registerLabel?.visibility = View.VISIBLE
                registerLabelIcon?.visibility = View.VISIBLE
            }

            // populate categories list
            val categories = event.eventCategory
            if (categories!!.isNotEmpty()) {
                categoryList?.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = CategoriesListAdapter(context, listOf(categories))
                }
            } else {
                categoryList?.visibility = View.GONE
            }
        }
    }
}