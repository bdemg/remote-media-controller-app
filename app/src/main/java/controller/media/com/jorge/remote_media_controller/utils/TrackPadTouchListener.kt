package controller.media.com.jorge.remote_media_controller.utils

import android.app.Activity
import android.preference.PreferenceManager
import android.view.MotionEvent
import android.view.View
import controller.media.com.jorge.remote_media_controller.network.NetworkClient
import kotlin.math.roundToInt


class TrackPadTouchListener : View.OnTouchListener {

    private val activity: Activity
    private var Xpos = 0
    private var Ypos = 0
    private var sensitivity: Double
    private val tapThreshold = 3
    private var isTap = false

    constructor(activity: Activity) {
        this.activity = activity
        sensitivity = PreferenceManager.getDefaultSharedPreferences(activity).getString("trackpadSensitivity", "0.8").toDouble()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val eventAction = event.action
        val networkClient = NetworkClient(this.activity)

        when (eventAction) {
            MotionEvent.ACTION_DOWN -> {
                this.Xpos = event.x.toInt()
                this.Ypos = event.y.toInt()
                this.isTap=true
            }

            MotionEvent.ACTION_MOVE -> {
                val newXpos = event.x.toInt()
                val newYpos = event.y.toInt()

                var Xdelta = (newXpos - this.Xpos)
                var Ydelta = (newYpos - this.Ypos)

                if(Math.abs(Xdelta)>this.tapThreshold || Math.abs(Ydelta)>this.tapThreshold){
                    this.Xpos = newXpos
                    this.Ypos = newYpos

                    this.isTap=false

                    val Xmovement = Xdelta*this.sensitivity
                    val Ymovement = Ydelta*this.sensitivity
                    networkClient.route = "/moveMouse/${Xmovement.roundToInt()}/${Ymovement.roundToInt()}"
                    networkClient.start()
                }

            }

            MotionEvent.ACTION_UP -> {
                Xpos = 0
                Ypos = 0

                if(isTap){
                    networkClient.route="/leftClick"
                    networkClient.start()
                }
            }
        }
        return true
    }

}