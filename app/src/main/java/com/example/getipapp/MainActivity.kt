package com.example.getipapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val ipTextView: TextView = findViewById(R.id.ipTextView)
        fetchIpAddress(ipTextView)
    }

    fun extractIpAddress(jsonString: String): String {
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getString("myip")
    }

    private fun fetchIpAddress(textView: TextView) {
        val request = Request.Builder()
            .url("https://functions.yandexcloud.net/d4e2bt6jba6cmiekqmsv")
            .build()

        Thread {
            try {
                val response: Response = client.newCall(request).execute()
                val ip = response.body?.string()
                val ipTest = "My IP: " + extractIpAddress(ip.toString())
                runOnUiThread {
                    textView.text = ipTest
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}
