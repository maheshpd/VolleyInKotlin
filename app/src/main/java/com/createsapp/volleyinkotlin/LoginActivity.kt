package com.createsapp.volleyinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            val emails = email.text
            val passwords = password.text

            when {
                TextUtils.isEmpty(emails) -> {
                    Toast.makeText(this, "Please enter emailId", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(passwords) -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    ApiCalling(emails.toString(), passwords.toString())
                }
            }

        }

    }

    private fun ApiCalling(emails: String, passwords: String) {

        val urls = "http://auralearning.xyz/teacher_login1.php"

        val request = object : StringRequest(
            Method.POST,
            urls,
            Response.Listener { response ->
                val obj = JSONObject(response)

                val code = obj.getString("code")
                val message = obj.getString("message")

                if (code.equals("1"))
                {
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val param = HashMap<String,String>()
                param["emailid"] = emails
                param["password"] = passwords
                return param
            }
        }
        requestQueue!!.add(request)
    }
}