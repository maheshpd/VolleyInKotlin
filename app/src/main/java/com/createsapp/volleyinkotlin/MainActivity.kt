package com.createsapp.volleyinkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUser()
    }

    //What is Singleton
    //function for network call
    private fun getUser() {
        //Instantiate the
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://api.github.com/search/users?q=eyehunt"

        //Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            var strResp = response.toString()
            val jsonObject: JSONObject = JSONObject(strResp)
            val jsonArray: JSONArray = jsonObject.getJSONArray("items")
            var str_user: String = ""
            for (i in 0 until jsonArray.length()) {
                var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                str_user = str_user  + "\n" + jsonInner.get("login")
            }

            tv_user.text = str_user

        }, Response.ErrorListener {
            tv_user.text = "That didn't work!"
        })
    }


}