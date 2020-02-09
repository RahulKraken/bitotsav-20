package `in`.bitotsav.bitotsav_20.activities

import `in`.bitotsav.bitotsav_20.R
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_contact_us.*

class ContactUsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

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
