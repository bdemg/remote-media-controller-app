package controller.media.com.jorge.remote_media_controller.controller

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import controller.media.com.jorge.remote_media_controller.R
import controller.media.com.jorge.remote_media_controller.network.NetworkClient
import controller.media.com.jorge.remote_media_controller.utils.TrackPadTouchListener
import kotlin.math.roundToInt

class TrackPadActivity : AppCompatActivity() {

    private val networkClient = NetworkClient(this)
    private var scrollAmount: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_pad)

        this.scrollAmount = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("scrollSteps", "3").toDouble().roundToInt()

        val rightClick = findViewById<Button>(R.id.trackpadRightClick)
        rightClick.setOnClickListener{

            this.networkClient.route = "/rightClick"
            this.networkClient.start()
        }

        val leftClick = findViewById<Button>(R.id.trackpadLeftClick)
        leftClick.setOnClickListener{

            this.networkClient.route = "/leftClick"
            this.networkClient.start()
        }

        val scrollUp = findViewById<ImageButton>(R.id.scrollUp)
        scrollUp.setOnClickListener{

            this.networkClient.route = "/scrollUp/$scrollAmount"
            this.networkClient.start()
        }

        val scrollDown = findViewById<ImageButton>(R.id.scrollDown)
        scrollDown.setOnClickListener{

            this.networkClient.route = "/scrollDown/$scrollAmount"
            this.networkClient.start()
        }

        val trackpadLayout = findViewById<ConstraintLayout>(R.id.trackpadLayout)
        trackpadLayout.setOnTouchListener(TrackPadTouchListener(this, networkClient))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.trackpad_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.back_button -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
