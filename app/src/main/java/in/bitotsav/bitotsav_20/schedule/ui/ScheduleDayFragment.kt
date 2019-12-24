package `in`.bitotsav.bitotsav_20.schedule.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.db.AppDatabase
import `in`.bitotsav.bitotsav_20.schedule.data.Event
import `in`.bitotsav.bitotsav_20.schedule.data.EventRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_day.*

/**
 * A simple [Fragment] subclass.
 */
class ScheduleDayFragment(private val day: String) : Fragment() {

    private lateinit var events: LiveData<List<Event>>
    private lateinit var adapter: ScheduleDayAdapter
    private lateinit var decoration: ScheduleTimeHeaderDecoration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = ScheduleViewModelFactory(EventRepository(AppDatabase(activity!!).eventDao()), activity!!.application)
        val viewModel = viewModelFactory.create(ScheduleViewModel::class.java)
        events = viewModel.getAllEventsForDay(day)
        events.observe(this,
            Observer {
                println("dayFragment: ${events.value}")
//                schedule_rv.adapter?.notifyDataSetChanged()
                updateRecyclerView()
            })

        adapter = ScheduleDayAdapter(events.value, activity!!)

        println("dayFragment: ${events.value}")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        schedule_rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapter
        }
    }

    private fun updateRecyclerView() {
        adapter.setData(events.value!!)
        schedule_rv.adapter = adapter
        if (!events.value.isNullOrEmpty()) {
            decoration = ScheduleTimeHeaderDecoration(context!!, events = events.value!!)
            schedule_rv.addItemDecoration(decoration)
        }
    }
}
