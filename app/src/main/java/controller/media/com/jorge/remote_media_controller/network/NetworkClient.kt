package controller.media.com.jorge.remote_media_controller.network

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.preference.PreferenceManager
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import controller.media.com.jorge.remote_media_controller.utils.ToastHandler
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class NetworkClient(activity: Activity) : Thread() {

    private val callingActivity: Activity = activity
    var route: String? = null
    private val restClient = OkHttpClient()
    private val toastHandler = ToastHandler(callingActivity)


    override fun run() {

        val host = PreferenceManager.getDefaultSharedPreferences(callingActivity).getString("host", "https://localhost:8080")
        val timeout = PreferenceManager.getDefaultSharedPreferences(callingActivity).getString("timeoutMillis", "500").toLong()
        this.restClient.setConnectTimeout(
                timeout,
                TimeUnit.MILLISECONDS
        )
        val request = Request.Builder().url(host + route).build()

        try {
            val response = this.restClient.newCall(request).execute()

            if (!response.isSuccessful) {

                val message = Message()
                message.obj = "Network error, check the server state"
                toastHandler.sendMessage(message)
            }

        } catch (exception: SocketTimeoutException) {

            val message = Message()
            message.obj = "Unable to connect to the server: Connection timed out."
            toastHandler.sendMessage(message)
        }
    }
}