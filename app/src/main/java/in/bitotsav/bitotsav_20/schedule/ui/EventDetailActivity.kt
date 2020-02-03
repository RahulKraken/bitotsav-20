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

        bindData(event)
    }

    private fun bindData(event: Event) {
        // text details
        event_detail_title.text = event.name
        event_detail_desc.text = event.description
        event_detail_rules.text = event.rulesAndRegulations
        event_detail_day_time.text = "Day ${event.day}, ${event.timing}"
        event_detail_venue.text = event.venue

        // points
        val points = event.points?.split("-")
        if (!points.isNullOrEmpty() && points.size > 1) {
            if (points[0].isNotEmpty()) prize_one.text = points[0]
            if (points[1].isNotEmpty()) prize_two.text = points[1]
            if (points[2].isNotEmpty()) prize_three.text = points[2]
        } else {
            event_detail_prizes_label.visibility = View.GONE
            event_detail_prizes_container.visibility = View.GONE
        }

        // TODO: get winners, format then parse and display
        // for now it's not visible
        event_detail_winner_container.visibility = View.GONE

        // coordinators
        val coordinators = event.contactInformation?.split("\n")
        if (!coordinators.isNullOrEmpty() && coordinators[0].isNotEmpty()) {
            val v = getNameAndPhone(coordinators[0])
            coordinator_one_label.text = v.first
            coordinator_one.text = v.second
        }
        if (!coordinators.isNullOrEmpty() && coordinators[1].isNotEmpty()) {
            val v = getNameAndPhone(coordinators[1])
            coordinator_two_label.text = v.first
            coordinator_two.text = v.second
        }

        populateRecyclerView(event)
    }

    private fun getNameAndPhone(s: String): Pair<String, String> {
        val name = s.split(":")[0].split(".")[1].trim()
        val phone = s.split(":")[1].trim()
        println("name $name, phone $phone")
        return Pair(name, phone)
    }

    private fun populateRecyclerView(event: Event) {
        val categories = event.eventCategory
        if (categories.isNullOrEmpty()) event_detail_category_rv.visibility = View.GONE
        else event_detail_category_rv?.let {
            it.apply {
                adapter = CategoriesListAdapter(context, listOf(categories))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}
