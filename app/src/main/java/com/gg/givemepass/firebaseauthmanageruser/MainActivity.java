package com.gg.givemepass.firebaseauthmanageruser;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
    private Button getProfile;
    private TextView profileInfo;
    private Button logout;
    private Button updateData;
    private FirebaseUser user;
    private Button changeEmail;
    private Button verificationEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void initView() {
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getProfile = (Button) findViewById(R.id.get_profile);
        profileInfo = (TextView) findViewById(R.id.profile_info);
        getProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("display name:");
                    sb.append(user.getDisplayName());
                    sb.append("\n");
                    sb.append("email:");
                    sb.append(user.getEmail());
                    sb.append("\n");
                    sb.append("photo url:");
                    sb.append(user.getPhotoUrl());
                    sb.append("\n");
                    sb.append("is email verified:");
                    sb.append(user.isEmailVerified());
                    sb.append("\n");
                    sb.append("uid:");
                    sb.append(user.getUid());
                    profileInfo.setText(sb.toString());
                }
            }
        });
        updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View item = LayoutInflater.from(MainActivity.this).inflate(R.layout.update_data_layout, null);
                new AlertDialog.Builder(MainActivity.this)
                    .setView(item)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText displayName = (EditText) item.findViewById(R.id.display_name_edit);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName.getText().toString())
                                .setPhotoUri(Uri.parse("https://github.com/givemepassxd999/free_img/blob/master/01.jpg?raw=true"))
                                .build();

                            user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Profile 修改成功", Toast.LENGTH_SHORT).show();
                                        } else{
                                            Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        }
                    })

                    .show();
            }
        });
        changeEmail = (Button) findViewById(R.id.change_email);
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthCredential credential = EmailAuthProvider.getCredential("abc@gamil.com", "qazxsw");
                user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.updateEmail("def@gmail.com")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Email 修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "修改email:"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }
        });
        verificationEmail = (Button) findViewById(R.id.verification_email);
        verificationEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Email 驗證成功", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(MainActivity.this, "Email 驗證"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }
}
