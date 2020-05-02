package controller.media.com.jorge.remote_media_controller.utils

import android.app.Activity
import android.content.Intent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import controller.media.com.jorge.remote_media_controller.R
import controller.media.com.jorge.remote_media_controller.controller.TrackPadActivity


class MediaScreenSwipeListener : View.OnTouchListener {

    private var gestureDetector: GestureDetector
    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100
    private val activity: Activity

    constructor(activity: Activity) {
        gestureDetector = GestureDetector(activity, GestureListener())
        this.activity=activity
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
    }

    fun onSwipeRight() {

        val intent = Intent(this.activity, TrackPadActivity::class.java)
        this.activity.startActivity(intent)
        this.activity.overridePendingTransition(R.xml.slide_from_left, R.xml.slide_to_left)
    }

    fun onSwipeLeft() {}

    fun onSwipeTop() {}

    fun onSwipeBottom() {}
}