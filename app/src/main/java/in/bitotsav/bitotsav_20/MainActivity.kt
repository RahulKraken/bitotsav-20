package `in`.bitotsav.bitotsav_20

import `in`.bitotsav.bitotsav_20.event.ui.ScheduleFragment
import `in`.bitotsav.bitotsav_20.feed.ui.FeedFragment
import `in`.bitotsav.bitotsav_20.leaderboard.ui.LeaderboardFragment
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_generic.*

class MainActivity : AppCompatActivity(), DrawerListener {

    lateinit var drawerLayout: DrawerLayout

    var lastSelectedNavigationItem: Int = R.id.action_schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        setSupportActionBar(app_bar)
        setupBottomNavigation()

        drawerLayout.addDrawerListener(this)
    }

    private fun setupBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_bitotsav -> {
                    println("Bitotsav selected")
                    drawerLayout.openDrawer(GravityCompat.START, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_schedule -> {
                    println("Schedule selected")
                    lastSelectedNavigationItem = R.id.action_schedule
                    setFragment(ScheduleFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_feed -> {
                    println("Feed selected")
                    lastSelectedNavigationItem = R.id.action_feed
                    setFragment(FeedFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_leaderboard -> {
                    println("Leaderboard selected")
                    lastSelectedNavigationItem = R.id.action_leaderboard
                    setFragment(LeaderboardFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        bottom_navigation.selectedItemId = lastSelectedNavigationItem
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.END, true)
        else super.onBackPressed()
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(container.id, fragment).commit()
    }

    override fun onDrawerStateChanged(newState: Int) {
        // do nothing
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        // do nothing
    }

    override fun onDrawerClosed(drawerView: View) {
        bottom_navigation.selectedItemId = lastSelectedNavigationItem
    }

    override fun onDrawerOpened(drawerView: View) {
        // do nothing
    }
}
