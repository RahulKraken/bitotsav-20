package `in`.bitotsav.bitotsav_20.feed.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.VolleyService
import `in`.bitotsav.bitotsav_20.db.AppDatabase
import `in`.bitotsav.bitotsav_20.feed.data.Feed
import `in`.bitotsav.bitotsav_20.feed.data.FeedRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_feed.*
import org.json.JSONObject
import java.lang.Exception

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
//        val feedViewModelFactory = FeedViewModelFactory(FeedRepository(AppDatabase(activity!!).feedDao()), activity!!.application)
//        val feedViewModel = feedViewModelFactory.create(FeedViewModel::class.java)
//        feeds = feedViewModel.getAllFeeds()
//
//        feeds.observe(this,
//            Observer {
//                println("Feeds: ${feeds.value}")
//                updateList()
//            })
//
//        adapter = FeedAdapter(feeds)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchNotification()
//        feed_recycler_view.apply {
//            layoutManager = LinearLayoutManager(activity)
//            adapter = FeedAdapter(feeds.value)
//            addItemDecoration(ItemDividerDecoration(context!!, 64, 48))
//        }
    }

    private fun fetchNotification() {
        feed_progress_bar.visibility = View.VISIBLE
        val req = StringRequest(Request.Method.POST, "https://bitotsav.in/api/admin/getNotifications",
            Response.Listener { res ->
                try {
                    val obj = JSONObject(res)
                    val array = obj.getJSONArray("notifications")
                    val notifications = ArrayList<Feed>()
                    for (x in 0 until array.length()) {
                        val not = array.getJSONObject(x)
                        notifications.add(Feed(-1, not.getString("title"), not.getString("message"), "type", 0L))
                    }
                    feed_progress_bar.visibility = View.GONE
                    feed_recycler_view.apply {
                        adapter = FeedAdapter(notifications)
                        layoutManager = LinearLayoutManager(activity)
                        addItemDecoration(ItemDividerDecoration(activity!!, 32, 32))
                    }
                } catch (e: Exception) {
                    Snackbar.make(feed_recycler_view, "Could not fetch notifications", Snackbar.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {
                Snackbar.make(feed_recycler_view, "Could not fetch notifications", Snackbar.LENGTH_SHORT).show()
            })
        VolleyService.getRequestQueue(activity!!).add(req)
    }

//    private fun updateList() {
//        adapter.setData(feeds.value!!)
//        feed_recycler_view.adapter = adapter
//    }
}
