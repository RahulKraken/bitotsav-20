package `in`.bitotsav.bitotsav_20.feed.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.feed.data.Feed
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeedAdapter (private val feeds: List<Feed>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FeedViewHolder(layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = feeds[position]
        holder.bind(feed)
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    class FeedViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_feed, parent, false)) {
        private var title: TextView? = null
        private var detail: TextView? = null
        private var type: TextView? = null
        private var timestamp: TextView? = null

        init {
            title = itemView.findViewById(R.id.announcement_title)
            detail = itemView.findViewById(R.id.announcement_details)
            type = itemView.findViewById(R.id.announcement_type)
            timestamp = itemView.findViewById(R.id.announcement_timestamp)
        }

        fun bind(feed: Feed) {
            title?.text = feed.title
            detail?.text = feed.detail
            type?.text = feed.type
            timestamp?.text = feed.timestamp.toString()
        }
    }
}