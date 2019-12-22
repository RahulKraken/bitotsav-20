package `in`.bitotsav.bitotsav_20.feed.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.feed.data.Feed
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feed.*

/**
 * A simple [Fragment] subclass.
 */
class FeedFragment : Fragment() {

    private val feeds = listOf(
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719),
        Feed(1, "title", "details", "announcement", 76382719)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feed_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FeedAdapter(feeds)
        }
    }
}
