package `in`.bitotsav.bitotsav_20

import `in`.bitotsav.bitotsav_20.event.ui.ScheduleFragment
import `in`.bitotsav.bitotsav_20.feed.ui.FeedFragment
import `in`.bitotsav.bitotsav_20.leaderboard.ui.LeaderboardFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_bitotsav -> {
                    println("Bitotsav selected")
                    setFragment(BitotsavFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_schedule -> {
                    println("Schedule selected")
                    setFragment(ScheduleFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_feed -> {
                    println("Feed selected")
                    setFragment(FeedFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_leaderboard -> {
                    println("Leaderboard selected")
                    setFragment(LeaderboardFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        bottom_navigation.selectedItemId = R.id.action_schedule
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(container.id, fragment).commit()
    }
}
