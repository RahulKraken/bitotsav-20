package `in`.bitotsav.bitotsav_20

import `in`.bitotsav.bitotsav_20.profile.ui.ProfileActivity
import `in`.bitotsav.bitotsav_20.schedule.ui.ScheduleFragment
import `in`.bitotsav.bitotsav_20.feed.ui.FeedFragment
import `in`.bitotsav.bitotsav_20.leaderboard.ui.LeaderboardFragment
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_generic.*


class MainActivity : AppCompatActivity(), DrawerListener, View.OnClickListener {

    private lateinit var drawerLayout: DrawerLayout

    private var activeFragment = Fragment()
    private val scheduleFragment = ScheduleFragment.newInstance()
    private val feedFragment = FeedFragment.newInstance()
    private val leaderboardFragment = LeaderboardFragment.newInstance()

    private var lastSelectedNavigationItem: Int = R.id.action_schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        // --------------------------Experiment---------------------------



        startActivity(Intent(this, ProfileActivity::class.java))



        // ---------------------------------------------------------------

        drawerLayout = findViewById(R.id.drawer_layout)

        setSupportActionBar(app_bar)
        setupBottomNavigation()

        supportFragmentManager.beginTransaction().add(R.id.container, feedFragment, "Feed").hide(feedFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, leaderboardFragment, "Leaderboard").hide(leaderboardFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, scheduleFragment, "Schedule").commit()
        supportActionBar?.let { app_bar_title.text = "Schedule" }

        app_bar_search_btn.setOnClickListener(this)
        app_bar_back_arrow.setOnClickListener(this)

        setKeyboardModeOnSearch()

        drawerLayout.addDrawerListener(this)
    }

    private fun setKeyboardModeOnSearch() {
        app_bar_search_box.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                println("Action Search: Triggered")
                return@OnEditorActionListener true
            }
            false
        })
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
                    setFragment(scheduleFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_feed -> {
                    println("Feed selected")
                    lastSelectedNavigationItem = R.id.action_feed
                    setFragment(feedFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_leaderboard -> {
                    println("Leaderboard selected")
                    lastSelectedNavigationItem = R.id.action_leaderboard
                    setFragment(leaderboardFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        bottom_navigation.selectedItemId = lastSelectedNavigationItem
    }

    override fun onClick(v: View?) {
        return when(v?.id) {
            R.id.app_bar_search_btn -> {
                app_bar_search_btn.visibility = View.GONE
                app_bar_title.visibility = View.GONE
                app_bar_search_box.visibility = View.VISIBLE
                app_bar_back_arrow.visibility = View.VISIBLE
            }
            R.id.app_bar_back_arrow -> {
                app_bar_back_arrow.visibility = View.GONE
                app_bar_search_box.visibility = View.GONE
                app_bar_title.visibility = View.VISIBLE
                app_bar_search_btn.visibility = View.VISIBLE
            }
            else -> Unit
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.END, true)
        else super.onBackPressed()
    }

    private fun setFragment(fragment: Fragment) {
        supportActionBar?.let {
            app_bar_title.text = fragment.tag
        }
        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit()
        activeFragment = fragment
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
