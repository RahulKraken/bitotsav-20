package `in`.bitotsav.bitotsav_20.feed.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.feed.data.Feed
import `in`.bitotsav.bitotsav_20.utils.share
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeedAdapter (
    private var feeds: List<Feed>?
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FeedViewHolder(layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = feeds?.get(position)
        feed?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return if (feeds.isNullOrEmpty()) 0 else return feeds?.size!!
    }

    fun setData(feeds: List<Feed>) {
        this.feeds = feeds
    }

    class FeedViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ) : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_feed, parent, false)) {
        private var title: TextView? = null
        private var detail: TextView? = null
//        private var type: TextView? = null
//        private var timestamp: TextView? = null
//        private var star: ImageView? = null

        init {
            title = itemView.findViewById(R.id.announcement_title)
            detail = itemView.findViewById(R.id.announcement_details)
//            star = itemView.findViewById(R.id.share_btn)
//            type = itemView.findViewById(R.id.announcement_type)
//            timestamp = itemView.findViewById(R.id.announcement_timestamp)
        }

        fun bind(feed: Feed) {
            title?.text = feed.title
            detail?.text = feed.message
//            type?.text = feed.type
//            timestamp?.text = feed.timestamp.toString()
        }
    }
}