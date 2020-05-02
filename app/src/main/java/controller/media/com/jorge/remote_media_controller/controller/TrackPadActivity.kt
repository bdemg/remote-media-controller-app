package controller.media.com.jorge.remote_media_controller.controller

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import controller.media.com.jorge.remote_media_controller.R
import controller.media.com.jorge.remote_media_controller.network.NetworkClient
import controller.media.com.jorge.remote_media_controller.utils.TrackPadTouchListener

class TrackPadActivity : AppCompatActivity() {

    private val networkClient = NetworkClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_pad)

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

        val trackpadLayout = findViewById<ConstraintLayout>(R.id.trackpadLayout)
        trackpadLayout.setOnTouchListener(TrackPadTouchListener(this, networkClient))
    }
}
