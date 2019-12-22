package `in`.bitotsav.bitotsav_20.event.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.event.data.Event
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_day.*

/**
 * A simple [Fragment] subclass.
 */
class ScheduleDayFragment(private val day: Int) : Fragment() {

    private var events = listOf(
        Event(1, "event", "desc", "status", "10:00 AM", "venue", "1", "2", "coordinators", "category", "tag", "duration"),
        Event(2, "event", "desc", "status", "10:00 AM", "venue", "1", "2", "coordinators", "category", "tag", "duration"),
        Event(3, "event", "desc", "status", "10:15 AM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "10:15 AM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(5, "event", "desc", "status", "10:15 AM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(6, "event", "desc", "status", "11:15 AM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(7, "event", "desc", "status", "11:15 AM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(8, "event", "desc", "status", "11:15 AM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "11:45 AM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "12:00 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "12:00 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "12:00 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "12:30 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "12:30 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "12:30 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "12:30 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "3:00 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "3:00 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "3:30 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "3:45 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "3:45 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "3:45 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "4:00 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "4:15 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "4:30 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration"),
        Event(4, "event", "desc", "status", "4:30 PM", "venue", "2", "2", "coordinators", "category", "tag", "duration")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        schedule_rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ScheduleDayAdapter(events)
            addItemDecoration(ScheduleTimeHeaderDecoration(activity!!, events))
        }
    }
}
