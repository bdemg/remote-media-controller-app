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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_controller)

        val volumeUp = findViewById<Button>(R.id.volumeUp)
        volumeUp.setOnClickListener {

            this.sendMediaControlMessage("/volumeUp")
        }

        val volumeDown = findViewById<Button>(R.id.volumeDown)
        volumeDown.setOnClickListener {

            this.sendMediaControlMessage("/volumeDown")
        }

        val mute = findViewById<Button>(R.id.mute)
        mute.setOnClickListener {

            this.sendMediaControlMessage("/mute")
        }

        val nextTrack = findViewById<Button>(R.id.nextTrack)
        nextTrack.setOnClickListener {

            this.sendMediaControlMessage("/nextTrack")
        }

        val previousTrack = findViewById<Button>(R.id.previousTrack)
        previousTrack.setOnClickListener {

            this.sendMediaControlMessage("/previousTrack")
        }

        val playPause = findViewById<Button>(R.id.playPause)
        playPause.setOnClickListener {

            this.sendMediaControlMessage("/playPause/native")
        }

        val spacebarPlayPause = findViewById<Button>(R.id.spacebarPlayPause)
        spacebarPlayPause.setOnClickListener{

            this.sendMediaControlMessage("/playPause/spacebar")
        }

        val fastForward = findViewById<Button>(R.id.forward)
        fastForward.setOnClickListener{

            this.sendMediaControlMessage("/mediaForward")
        }

        val fastRewind = findViewById<Button>(R.id.rewind)
        fastRewind.setOnClickListener{

            this.sendMediaControlMessage("/mediaBackward")
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

    private fun sendMediaControlMessage(route: String){
        val networkClient = NetworkClient(this)
        networkClient.route = route
        networkClient.start()
    }
}
