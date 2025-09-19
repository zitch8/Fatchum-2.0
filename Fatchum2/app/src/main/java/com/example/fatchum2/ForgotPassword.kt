package com.example.fatchum2

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
//    private lateinit var timer: CountDownTimer
    private lateinit var etMail: EditText
    private lateinit var btnReset: Button
    private lateinit var tvMinute: TextView
    private lateinit var tvSeconds: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // GO BACK TO LOGIN
//        val loginBack = findViewById<TextView>(R.id.tvFPlogin)
//        loginBack.setOnClickListener {
//            startActivity(Intent(this@ForgotPassword, Login::class.java))
//        }

//        etMail = findViewById(R.id.editFPemail)
//        tvMinute = findViewById(R.id.tvMinute)
//        tvSeconds = findViewById(R.id.tvSeconds)
//        btnReset = findViewById(R.id.btnForgotPassword)
//        btnReset.setOnClickListener {
//            val email: String = etMail.text.toString().trim { it <= ' '}
//            if (email.isEmpty()) {
//                Toast.makeText(applicationContext, "Please enter your email", Toast.LENGTH_SHORT).show()
//            } else {
//                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
//                    .addOnCompleteListener {task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(applicationContext, "Please check your inbox", Toast.LENGTH_LONG).show()
//                            // finish()
//                            btnReset.isEnabled = false
//                            btnReset.isClickable = false
//                            timer.start()
//                        } else {
//                            Toast.makeText(this@ForgotPassword, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
//                        }
//                    }
//            }
//        }

//        var minute: Int = 0
//        var sec: Int = 0
//        timer = object : CountDownTimer(90000, 1000) {
//            override fun onTick(p0: Long) {
//                minute = (p0/60000).toInt()
//                sec = (p0/1000).toInt()
//                if (minute == 1) sec -= 60
//                tvMinute.text = "0" + minute.toString() + ":"
//                if (sec < 10) {
//                    tvSeconds.text = "0" + sec.toString()
//                } else {
//                    tvSeconds.text = sec.toString()
//                }
//
//            }
//
//            override fun onFinish() {
//                btnReset.isEnabled = true
//                btnReset.isClickable = true
//            }
//        }
    }
}