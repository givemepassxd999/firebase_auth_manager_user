package com.gg.givemepass.firebaseauthmanageruser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button getProfile;
    private TextView profileInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        getProfile = (Button) findViewById(R.id.get_profile);
        profileInfo = (TextView) findViewById(R.id.profile_info);
        getProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
    }
}
