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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_day.*
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 */
class ScheduleDayFragment : Fragment() {

    companion object {
        fun newInstance(day: String) = ScheduleDayFragment().apply {
            arguments = Bundle().apply {
                putString("day", day)
            }
        }
    }

    private val day: String by lazy {
        arguments?.getString("day") ?: throw IllegalStateException("No day argument")
    }

    private lateinit var events: LiveData<List<Event>>
    private lateinit var adapter: ScheduleDayAdapter
    private lateinit var decoration: ScheduleTimeHeaderDecoration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = ScheduleViewModelFactory(EventRepository(AppDatabase(activity!!).eventDao()), activity!!.application)
        val viewModel = viewModelFactory.create(ScheduleViewModel::class.java)
        events = when(day) {
            "1" -> viewModel.getAllEventsForDay()[0]
            "2" -> viewModel.getAllEventsForDay()[1]
            "3" -> viewModel.getAllEventsForDay()[2]
            else -> viewModel.getAllEventsForDay()[0]
        }
        events.observe(this,
            Observer {
                println("dayFragment $day: ${events.value}")
                updateRecyclerView()
            })

        adapter = ScheduleDayAdapter(events.value, activity!!)

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
