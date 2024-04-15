package com.example.leopold_jacquet

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import java.net.URL

class WebService : IntentService("WebService") {

    override fun onHandleIntent(intent: Intent?) {
        val url = URL("https://ror-next-u.onrender.com/beers.json")
        val connection = url.openConnection()
        connection.getInputStream()
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String?, param2: String?) {
        TODO("Handle action Foo")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String?, param2: String?) {
        TODO("Handle action Baz")
    }

    companion object {
        @JvmStatic
        fun startAction(context: Context) {
            val intent = Intent(context, WebService::class.java)
            context.startService(intent)
        }
    }
}