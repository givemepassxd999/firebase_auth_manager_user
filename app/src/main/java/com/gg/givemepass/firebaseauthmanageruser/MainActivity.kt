package com.gg.givemepass.firebaseauthmanageruser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var user: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initData() {
        user = FirebaseAuth.getInstance().currentUser
    }

    private fun initView() {
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent()
            intent.setClass(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        get_profile.setOnClickListener {
            if (user != null) {
                val sb = StringBuilder()
                sb.append("display name:")
                sb.append(user?.displayName)
                sb.append("\n")
                sb.append("email:")
                sb.append(user?.email)
                sb.append("\n")
                sb.append("photo url:")
                sb.append(user?.photoUrl)
                sb.append("\n")
                sb.append("is email verified:")
                sb.append(user?.isEmailVerified)
                sb.append("\n")
                sb.append("uid:")
                sb.append(user?.uid)
                profile_info.text = sb.toString()
            }
        }
        update_data.setOnClickListener {
            val item = LayoutInflater.from(this@MainActivity).inflate(R.layout.update_data_layout, null)
            AlertDialog.Builder(this@MainActivity)
                    .setView(item)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        val displayName = item.findViewById<View>(R.id.display_name_edit) as EditText
                        val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName.text.toString())
                                .setPhotoUri(Uri.parse("https://github.com/givemepassxd999/free_img/blob/master/01.jpg?raw=true"))
                                .build()

                        user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this@MainActivity, "Profile 修改成功", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this@MainActivity, "Profile 修改:" + task.exception?.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                    }
                    .show()
        }
        change_email.setOnClickListener {
            val credential = EmailAuthProvider.getCredential("abc@gamil.com", "qazxsw")
            user?.reauthenticate(credential)?.addOnCompleteListener {
                user?.updateEmail("abcde@gmail.com")?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Email 修改成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "修改email:" + task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        verification_email.setOnClickListener {
            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Email 驗證成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Email 驗證:" + task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        change_pw.setOnClickListener {
            val item = LayoutInflater.from(this@MainActivity).inflate(R.layout.change_pw_layout, null)
            AlertDialog.Builder(this@MainActivity)
                    .setView(item)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        val displayName = item.findViewById<View>(R.id.new_pw) as EditText
                        val newPassword = displayName.text.toString()
                        user?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@MainActivity, "Password 修改成功", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@MainActivity, "password 修改:" + task.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .show()
        }
        re_auth.setOnClickListener {
            val credential = EmailAuthProvider.getCredential("def@gamil.com", "wsxzaq")
            user?.reauthenticate(credential)?.addOnCompleteListener {
                Toast.makeText(this@MainActivity, "重新認證成功", Toast.LENGTH_SHORT).show()
            }
        }
        del_user.setOnClickListener {
            val credential = EmailAuthProvider.getCredential("def@gamil.com", "wsxzaq")
            user?.reauthenticate(credential)?.addOnCompleteListener {
                user?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@MainActivity, "User刪除成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "User刪除:" + task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
