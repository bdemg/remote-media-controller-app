package controller.media.com.jorge.remote_media_controller.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import controller.media.com.jorge.remote_media_controller.R
import controller.media.com.jorge.remote_media_controller.network.NetworkClient
import controller.media.com.jorge.remote_media_controller.utils.MediaScreenSwipeListener


class MediaControllerActivity : AppCompatActivity() {

    private val networkClient = NetworkClient(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_controller)

        val volumeUp = findViewById<Button>(R.id.volumeUp)
        volumeUp.setOnClickListener {

            this.networkClient.route = "/volumeUp"
            this.networkClient.start()
        }

        val volumeDown = findViewById<Button>(R.id.volumeDown)
        volumeDown.setOnClickListener {

            this.networkClient.route = "/volumeDown"
            this.networkClient.start()
        }

        val mute = findViewById<Button>(R.id.mute)
        mute.setOnClickListener {

            this.networkClient.route = "/mute"
            this.networkClient.start()
        }

        val nextTrack = findViewById<Button>(R.id.nextTrack)
        nextTrack.setOnClickListener {

            this.networkClient.route = "/nextTrack"
            this.networkClient.start()
        }

        val previousTrack = findViewById<Button>(R.id.previousTrack)
        previousTrack.setOnClickListener {

            this.networkClient.route = "/previousTrack"
            this.networkClient.start()
        }

        val playPause = findViewById<Button>(R.id.playPause)
        playPause.setOnClickListener {

            this.networkClient.route = "/playPause/native"
            this.networkClient.start()
        }

        val spacebarPlayPause = findViewById<Button>(R.id.spacebarPlayPause)
        spacebarPlayPause.setOnClickListener{

            this.networkClient.route = "/playPause/spacebar"
            this.networkClient.start()
        }

        val fastForward = findViewById<Button>(R.id.forward)
        fastForward.setOnClickListener{

            this.networkClient.route = "/mediaForward"
            this.networkClient.start()
        }

        val fastRewind = findViewById<Button>(R.id.rewind)
        fastRewind.setOnClickListener{

            this.networkClient.route = "/mediaBackward"
            this.networkClient.start()
        }

        val layout = findViewById<ConstraintLayout>(R.id.mediaControllerLayout)
        layout.setOnTouchListener(MediaScreenSwipeListener(this))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.settings_button -> openSettings()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openSettings(): Boolean{
        val intent = Intent(this, ApplicationSettingsActivity::class.java)
        startActivity(intent)
        return true
    }
}
