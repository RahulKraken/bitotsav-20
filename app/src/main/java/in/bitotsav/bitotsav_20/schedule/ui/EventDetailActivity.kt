package `in`.bitotsav.bitotsav_20.schedule.ui

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import `in`.bitotsav.bitotsav_20.utils.share
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_event_detail.*

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val event = intent.getSerializableExtra(resources.getString(R.string.event_intent_pass_key)) as Event
        bindData(event)
        share_btn.setOnClickListener {
            share(this, "Checkout ${event.name} at Bitotsav BIT Mesra.", "Venue: ${event.venue}, time: ${event.timing} on day ${event.day}")
        }
    }

    private fun bindData(event: Event) {
        // event image
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        event_detail_image.setImageResource(when(event.eventCategory?.toLowerCase()) {
            "swaang" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_purple_300)
                R.drawable.swaang
            }
            "rhetoric" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_light_blue_400)
                R.drawable.rhetoric
            }
            "digitales" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_orange_400)
                R.drawable.digitales
            }
            "meraki" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_deep_purple_400)
                R.drawable.meraki
            }
            "euphoria" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_deep_purple_800)
                R.drawable.euphoriia
            }
            "dansation" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_pink_400)
                R.drawable.dansation
            }
            "dhwani" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_teal_400)
                R.drawable.dhwani
            }
            "adaa" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_pink_700)
                R.drawable.adaa
            }
            else -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.md_amber_400)
                R.drawable.detail_placeholder
            }
        })

        // text details
        // format event name format: All caps to first letter caps
        val ename = event.name[0].toUpperCase() + event.name.substring(1, event.name.length).toLowerCase()
        println("ename: $ename")
        event_detail_title.text = ename
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

        // winners
        if (event.dummy1.isNullOrEmpty()) {
            event_detail_winner_container.visibility = View.GONE
            event_detail_winner_label.visibility = View.GONE
        } else {
            val winners = event.dummy1.split("!@#$%")
            if (winners[0].isNotEmpty()) winner_one.text = winners[0]
            if (winners[1].isNotEmpty()) winner_two.text = winners[1]
            if (winners[2].isNotEmpty()) winner_three.text = winners[2]
        }

        // coordinators
        val coordinators = event.contactInformation?.split("\n")
        try {
            if (!coordinators.isNullOrEmpty() && coordinators[0].isNotEmpty()) {
                val v = getNameAndPhone(coordinators[0])
                coordinator_one_label.text = v.first
                coordinator_one.text = v.second
            }
        } catch (e: Exception) {
            coordinator_one.visibility = View.GONE
            coordinator_one_label.visibility = View.GONE
        }
        try {
            if (!coordinators.isNullOrEmpty() && coordinators[1].isNotEmpty()) {
                val v = getNameAndPhone(coordinators[1])
                coordinator_two_label.text = v.first
                coordinator_two.text = v.second
            }
        } catch (e: Exception) {
            coordinator_two.visibility = View.GONE
            coordinator_two_label.visibility = View.GONE
        }

        populateRecyclerView(event)
    }

    private fun getNameAndPhone(s: String): Pair<String, String> {
        val name = s.split(":")[0].split(".")[1].trim()
        val phone = s.split(":")[1].trim()
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
