package com.falcon.anubhav.githubclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.falcon.anubhav.githubclient.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName;
    private Button btnLogin;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserName = (EditText) findViewById(R.id.edtLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUserName.getText().toString().trim();
                if (!username.equals("")) {
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    intent.putExtra("user_name", username);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
