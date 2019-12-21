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
class DayFragment(private val day: Int) : Fragment() {

    private val events = listOf(
        Event(
            1,
            "event",
            "desc",
            "status",
            "timing",
            "venue",
            "1",
            "2",
            "coordinators",
            "category",
            "tag",
            "duration"
        ),
        Event(
            2,
            "event",
            "desc",
            "status",
            "timing",
            "venue",
            "1",
            "2",
            "coordinators",
            "category",
            "tag",
            "duration"
        ),
        Event(
            3,
            "event",
            "desc",
            "status",
            "timing",
            "venue",
            "2",
            "2",
            "coordinators",
            "category",
            "tag",
            "duration"
        ),
        Event(
            4,
            "event",
            "desc",
            "status",
            "timing",
            "venue",
            "2",
            "2",
            "coordinators",
            "category",
            "tag",
            "duration"
        )
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
            adapter = DayAdapter(events)
        }
    }
}
