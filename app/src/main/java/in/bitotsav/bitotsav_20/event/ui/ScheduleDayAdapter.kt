package `in`.bitotsav.bitotsav_20.event.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.event.data.Event
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleDayAdapter(private var events: List<Event>?) : RecyclerView.Adapter<ScheduleDayAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
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

    class DayViewHolder(
        inflater: LayoutInflater, parent: ViewGroup
    ): RecyclerView.ViewHolder(inflater.inflate(R.layout.item_schedule, parent, false)) {
        private var eventName: TextView? = null
        private var eventVenueDuration: TextView? = null

        init {
            eventName = itemView.findViewById(R.id.event_name)
            eventVenueDuration = itemView.findViewById(R.id.event_venue_duration)
        }

        fun bind(event: Event) {
            eventName?.text = event.eventName
            eventVenueDuration?.text = "${event.venue} / ${event.duration}"
        }
    }
}