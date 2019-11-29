package com.gg.givemepass.firebaseauthmanageruser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        if (user == null) {
            setContentView(R.layout.activity_home)
            login.setOnClickListener {
                val intent = Intent()
                intent.setClass(this@HomeActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            sign_up.setOnClickListener {
                val intent = Intent()
                intent.setClass(this@HomeActivity, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = Intent()
            intent.setClass(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}


