package com.falcon.anubhav.githubclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.falcon.anubhav.githubclient.R;
import com.falcon.anubhav.githubclient.model.GithubUser;
import com.falcon.anubhav.githubclient.rest.APIClient;
import com.falcon.anubhav.githubclient.rest.GithubUserEndPoints;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private TextView txtUserName, txtFollowers, txtFollowing, txtEmail, txtLogin, txtRepos;
    private Button btnRepos;
    private Bundle bundle;
    private String username;
    private ProgressBar progressBar;
    private ImageView imgUser;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtFollowers = (TextView) findViewById(R.id.txtFollowers);
        txtFollowing = (TextView) findViewById(R.id.txtFollowing);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtRepos = (TextView) findViewById(R.id.txtRepos);
        btnRepos = (Button) findViewById(R.id.btnRepo);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        imgUser = (ImageView) findViewById(R.id.imgUser);

        bundle = getIntent().getExtras();
        username = bundle.getString("user_name");
        loadDate();

        btnRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ReposActivity.class);
                intent.putExtra("user_name", username);
                startActivity(intent);
            }
        });
    }

    private void loadDate() {
        GithubUserEndPoints apiService = APIClient.
                getClient().create(GithubUserEndPoints.class);

        Call<GithubUser> call = apiService.getUser(username);
        call.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if (response.body() == null) {
                    linearLayout.setVisibility(View.GONE);
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "Please enter a valid username", Snackbar.LENGTH_INDEFINITE).show();
                } else {
                    Glide.with(UserActivity.this).load(response.body()
                            .getAvatarUrl())
                            .into(imgUser);

                    if (response.body().getName() == null) {
                        txtUserName.setText("No name provided.");
                    } else {
                        txtUserName.setText("Username : " + response.body().getName());
                    }
                    txtFollowers.setText("Followers : " + response.body().getFollowers());
                    txtFollowing.setText("Following : " + response.body().getFollowing());
                    txtRepos.setText("Repositories Count : " + response.body().getPublicRepos());
                    if (response.body().getEmail() == null) {
                        txtEmail.setText("No email provided.");
                    } else {
                        txtEmail.setText("Email : " + response.body().getEmail());
                    }
                    txtLogin.setText("Login : " + response.body().getLogin());
                }
            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.GONE);
                Snackbar.make(getWindow().getDecorView().getRootView(), "Error occured while fetching the information !", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }
}
