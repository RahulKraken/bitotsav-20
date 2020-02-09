package `in`.bitotsav.bitotsav_20.activities

import `in`.bitotsav.bitotsav_20.R
import `in`.bitotsav.bitotsav_20.feed.ui.FeedFragment
import `in`.bitotsav.bitotsav_20.leaderboard.ui.LeaderboardFragment
import `in`.bitotsav.bitotsav_20.profile.ui.ProfileActivity
import `in`.bitotsav.bitotsav_20.schedule.api.getAllEvents
import `in`.bitotsav.bitotsav_20.schedule.ui.ScheduleFragment
import `in`.bitotsav.bitotsav_20.utils.SharedPrefUtils
import `in`.bitotsav.bitotsav_20.utils.openCustomTab
import `in`.bitotsav.bitotsav_20.utils.share
import `in`.bitotsav.bitotsav_20.OneSignal.MyApplication;
import android.animation.ObjectAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_generic.*
import kotlinx.android.synthetic.main.layout_bitotsav.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var myApplication: MyApplication

    private var activeFragment = Fragment()
    private val scheduleFragment = ScheduleFragment.newInstance()
    private val feedFragment = FeedFragment.newInstance()
    private val leaderboardFragment = LeaderboardFragment.newInstance()

    private var isBitotsavMenuOpen = true

    private var lastSelectedNavigationItem: Int =
        R.id.action_schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myApplication = MyApplication.getInstance()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }

        // create notification channel for BUILD_VERSION >= ANDROID.O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("Bitotsav20", "Bitotsav20", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // set status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.md_red_800)

        // set bitotsav menu contents
        // TODO: on logout replace text
        menu_team_name.visibility = View.GONE
        if (SharedPrefUtils(this).getUser() != null) {
            menu_username.text = SharedPrefUtils(this).getUser()?.name
        } else {
            menu_username.text = "Login/Register"
            menu_user_avatar.visibility = View.GONE
        }

        setSupportActionBar(app_bar)
        setupBottomNavigation()

        hideBitotsavMenu()

        fetchEvents()

        supportFragmentManager.beginTransaction().add(R.id.container, feedFragment, "Feed").hide(feedFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, leaderboardFragment, "Leaderboard").hide(leaderboardFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, scheduleFragment, "Schedule").commit()
        supportActionBar?.let { app_bar_title.text = "Schedule" }

//        app_bar_search_btn.setOnClickListener(this)
//        app_bar_back_arrow.setOnClickListener(this)
        user_detail_container.setOnClickListener(this)
        bitotsav_menu_container.setOnClickListener(this)
        bitotsav_menu_large_container.setOnClickListener(this)
        rate_card.setOnClickListener(this)
        contact_card.setOnClickListener(this)
        info_card.setOnClickListener(this)
        sponsor_card.setOnClickListener(this)
        faq_card.setOnClickListener(this)
        night_card.setOnClickListener(this)
        bitotsav_card.setOnClickListener(this)
        web_card.setOnClickListener(this)
        share_card.setOnClickListener(this)
        ic_facebook.setOnClickListener(this)
        ic_twitter.setOnClickListener(this)
        ic_instagram.setOnClickListener(this)
        ic_youtube.setOnClickListener(this)

//        setKeyboardModeOnSearch()

        // retrieve firbase registration token
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("token", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result?.token
                println("FIRE BASE TOKEN: $token")
            })
    }

    private fun fetchEvents() {
        getAllEvents(this)
    }

/*
    private fun setKeyboardModeOnSearch() {
        app_bar_search_box.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                println("Action Search: Triggered")
                return@OnEditorActionListener true
            }
            false
        })
    }
*/

    private fun setupBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_bitotsav -> {
                    println("Bitotsav selected")
                    // TODO: Add the transition of fragment
                    showBitotsavMenu()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_schedule -> {
                    println("Schedule selected")
                    lastSelectedNavigationItem =
                        R.id.action_schedule
                    setFragment(scheduleFragment)
                    return@OnNavigationItemSelectedListener true
                }
//                R.id.action_feed -> {
//                    println("Feed selected")
//                    lastSelectedNavigationItem =
//                        R.id.action_feed
//                    setFragment(feedFragment)
//                    return@OnNavigationItemSelectedListener true
//                }
                R.id.action_leaderboard -> {
                    println("Leaderboard selected")
                    lastSelectedNavigationItem =
                        R.id.action_leaderboard
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
            /*R.id.app_bar_search_btn -> {
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
            }*/
            R.id.user_detail_container -> {
                println("profile btn clicked")
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.rate_card -> println("rating btn selected")
            R.id.contact_card -> {
                println("contact btn selected")
                startActivity(Intent(this, ContactUsActivity::class.java))
            }
            R.id.info_card -> {
                println("info btn selected")
                startActivity(Intent(this, InfoActivity::class.java))
            }
            R.id.sponsor_card -> {
                println("sponsor btn selected")
                startActivity(Intent(this, SponsorActivity::class.java))
            }
            R.id.faq_card -> {
                println("faq btn selected")
                startActivity(Intent(this, FaqActivity::class.java))
            }
            R.id.night_card -> {
                println("night btn selected")
                startActivity(Intent(this, NightsActivity::class.java))
            }
            R.id.bitotsav_card -> {
                println("bitotsav btn selected")
                startActivity(Intent(this, BitotsavActivity::class.java))
            }
            R.id.web_card -> {
                println("web btn selected")
                openCustomTab(this, getString(R.string.website_url))
            }
            R.id.share_card -> {
                println("share btn selected")
                share(this, "Check out the app for Bitotsav 20 on google play!", "Bitotsav 20 at Birla Institute of Technology from 14th to 16th of February, 2020.")
            }
            R.id.bitotsav_menu_container -> println("bitotsav card clicked")
            R.id.bitotsav_menu_large_container -> onBackPressed()
            R.id.ic_facebook -> openCustomTab(this, getString(R.string.facebook_url))
            R.id.ic_twitter -> openCustomTab(this, getString(R.string.twitter_url))
            R.id.ic_instagram -> openCustomTab(this, getString(R.string.instagram_url))
            R.id.ic_youtube -> openCustomTab(this, getString(R.string.youtube_url))
            else -> Unit
        }
    }

    override fun onBackPressed() {
        if (isBitotsavMenuOpen) {
            hideBitotsavMenu()
            bottom_navigation.selectedItemId = lastSelectedNavigationItem
        } else super.onBackPressed()
    }

    private fun hideBitotsavMenu() {
        if (isBitotsavMenuOpen)
            ObjectAnimator.ofFloat(bitotsav_menu_large_container, "translationX", -1200f).apply {
                duration = 300
                start()
            }
        isBitotsavMenuOpen = false
    }

    private fun showBitotsavMenu() {
        if (!isBitotsavMenuOpen)
            ObjectAnimator.ofFloat(bitotsav_menu_large_container, "translationX", 0f).apply {
                duration = 300
                start()
            }
        isBitotsavMenuOpen = true
    }

    private fun setFragment(fragment: Fragment) {
        hideBitotsavMenu()
        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit()
        activeFragment = fragment
        supportActionBar?.let {
            app_bar_title.text = fragment.tag
        }
    }
}
