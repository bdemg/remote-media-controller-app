package controller.media.com.jorge.remote_media_controller.utils

import android.content.Context
import android.os.Handler
import android.os.Message
import android.widget.Toast

class ToastHandler(val context:Context) : Handler() {

    override fun handleMessage(message: Message) {
        val stringMessage = message.obj as String
        Toast.makeText(context, stringMessage, Toast.LENGTH_SHORT).show()
    }
}