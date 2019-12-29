package `in`.bitotsav.bitotsav_20.feed.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.db.AppDatabase
import `in`.bitotsav.bitotsav_20.feed.data.Feed
import `in`.bitotsav.bitotsav_20.feed.data.FeedRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feed.*

/**
 * A simple [Fragment] subclass.
 */
class FeedFragment : Fragment() {

    private lateinit var feeds: LiveData<List<Feed>>
    private lateinit var adapter: FeedAdapter

    companion object {
        fun newInstance() = FeedFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val feedViewModelFactory = FeedViewModelFactory(FeedRepository(AppDatabase(activity!!).feedDao()), activity!!.application)
        val feedViewModel = feedViewModelFactory.create(FeedViewModel::class.java)
        feeds = feedViewModel.getAllFeeds()

        feeds.observe(this,
            Observer {
                println("Feeds: ${feeds.value}")
                updateList()
            })

        adapter = FeedAdapter(feeds.value)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feed_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FeedAdapter(feeds.value)
            addItemDecoration(ItemDividerDecoration(context!!, 64, 48))
        }
    }

    private fun updateList() {
        adapter.setData(feeds.value!!)
        feed_recycler_view.adapter = adapter
    }
}
