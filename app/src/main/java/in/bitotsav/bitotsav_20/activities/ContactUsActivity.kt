package `in`.bitotsav.bitotsav_20.activities

import `in`.bitotsav.bitotsav_20.R
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.app_bar_generic.*

class ContactUsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        val toolbar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)
        app_bar_title.text = "Contact Us"

        // set status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.md_red_800)

        email.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.email -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "bitotsav@bitmesra.ac.in", null))
                startActivity(Intent.createChooser(intent, "Send email..."))
            }
            R.id.container_contact_one -> {}
            R.id.container_contact_two -> {}
            else -> println("not clicked anything!")
        }
    }
}
