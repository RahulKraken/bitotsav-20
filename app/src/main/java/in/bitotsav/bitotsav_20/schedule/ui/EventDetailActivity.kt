package `in`.bitotsav.bitotsav_20.schedule.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_event_detail.*

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val event = intent.getSerializableExtra(resources.getString(R.string.event_intent_pass_key)) as Event
        println(event)

        bindData(event)
    }

    // TODO: bind more views
    private fun bindData(event: Event) {
        event_detail_title.text = event.eventName
        event_detail_rules.text = event.description
        event_detail_day_time.text = "Day ${event.day}, ${event.timing}"
        event_detail_venue.text = event.venue

        populateRecyclerView(event)
    }

    private fun populateRecyclerView(event: Event) {
        val categories = event.categories.split(",")
        if (categories.isNullOrEmpty()) event_detail_category_rv.visibility = View.GONE
        else event_detail_category_rv?.let {
            it.apply {
                adapter = CategoriesListAdapter(context, categories)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}
