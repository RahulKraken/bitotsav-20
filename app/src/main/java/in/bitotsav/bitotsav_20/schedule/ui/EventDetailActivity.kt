package `in`.bitotsav.bitotsav_20.schedule.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}
