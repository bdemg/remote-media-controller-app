package controller.media.com.jorge.remote_media_controller.utils

import android.app.Activity
import android.preference.PreferenceManager
import android.view.MotionEvent
import android.view.View
import controller.media.com.jorge.remote_media_controller.network.NetworkClient
import kotlin.math.roundToInt


class TrackPadTouchListener : View.OnTouchListener {

    private val networkClient: NetworkClient
    private val activity: Activity
    private var Xpos = 0
    private var Ypos = 0
    private var sensitivity: Double

    constructor(activity: Activity, networkClient: NetworkClient) {
        this.networkClient = networkClient
        this.activity = activity
        sensitivity = PreferenceManager.getDefaultSharedPreferences(activity).getString("trackpadSensitivity", "0.8").toDouble()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val eventAction = event.action

        when (eventAction) {
            MotionEvent.ACTION_DOWN -> {
                this.Xpos = event.x.toInt()
                this.Ypos = event.y.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                val newXpos = event.x.toInt()
                val newYpos = event.y.toInt()

                val Xdelta = (newXpos - this.Xpos)*this.sensitivity
                val Ydelta = (newYpos - this.Ypos)*this.sensitivity

                this.Xpos = newXpos
                this.Ypos = newYpos

                this.networkClient.route = "/moveMouse/${Xdelta.roundToInt()}/${Ydelta.roundToInt()}"
                this.networkClient.start()
            }

            MotionEvent.ACTION_UP -> {
                Xpos = 0
                Ypos = 0
            }
        }
        return true
    }

}