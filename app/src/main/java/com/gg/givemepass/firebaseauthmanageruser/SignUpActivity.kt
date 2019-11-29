package com.gg.givemepass.firebaseauthmanageruser

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initView()
    }

    private fun initView() {
        mAuth = FirebaseAuth.getInstance()
        password_layout.isErrorEnabled = true
        account_layout.isErrorEnabled = true
        signup_button.setOnClickListener(View.OnClickListener {
            val account = account_edit.text.toString()
            val password = password_edit.text.toString()
            if (TextUtils.isEmpty(account)) {
                account_layout.error = getString(R.string.plz_input_accout)
                password_layout.error = ""
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                account_layout.error = ""
                password_layout.error = getString(R.string.plz_input_pw)
                return@OnClickListener
            }
            account_layout.error = ""
            password_layout.error = ""
            mAuth?.createUserWithEmailAndPassword(account, password)?.addOnCompleteListener(this@SignUpActivity) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@SignUpActivity, R.string.register_success, Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.setClass(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@SignUpActivity, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
