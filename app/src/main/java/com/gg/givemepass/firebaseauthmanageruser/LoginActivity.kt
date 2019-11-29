package com.gg.givemepass.firebaseauthmanageruser

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        mAuth = FirebaseAuth.getInstance()
        password_layout.isErrorEnabled = true
        account_layout.isErrorEnabled = true
        login_button.setOnClickListener(View.OnClickListener {
            val account = account_edit.text.toString()
            val password = password_edit.text.toString()
            if (account.isNotEmpty()) {
                account_layout.error = getString(R.string.plz_input_accout)
                return@OnClickListener
            }
            if (password.isNotEmpty()) {
                password_layout.error = getString(R.string.plz_input_pw)
                return@OnClickListener
            }
            account_layout.error = ""
            password_layout.error = ""
            mAuth.signInWithEmailAndPassword(account, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@LoginActivity, R.string.login_success, Toast.LENGTH_SHORT).show()
                            val intent = Intent()
                            intent.setClass(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
        })
    }
}
