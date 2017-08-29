package com.falcon.anubhav.githubclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.falcon.anubhav.githubclient.R;
import com.falcon.anubhav.githubclient.adapter.ReposAdapter;
import com.falcon.anubhav.githubclient.model.GithubRepos;
import com.falcon.anubhav.githubclient.rest.APIClient;
import com.falcon.anubhav.githubclient.rest.GithubUserEndPoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposActivity extends AppCompatActivity {

    private RecyclerView reposRecycler;
    private ReposAdapter reposAdapter;
    private Bundle bundle;
    private String username;
    private List<GithubRepos> reposList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        reposRecycler = (RecyclerView) findViewById(R.id.reposRecycler);
        reposRecycler.setLayoutManager(new LinearLayoutManager(this));

        reposList = new ArrayList<>();

        bundle = getIntent().getExtras();
        username = bundle.getString("user_name");

        loadRepos();
    }

    private void loadRepos() {

        GithubUserEndPoints apiService = APIClient.
                getClient().create(GithubUserEndPoints.class);

        Call<List<GithubRepos>> call = apiService.getRepos(username);
        call.enqueue(new Callback<List<GithubRepos>>() {
            @Override
            public void onResponse(Call<List<GithubRepos>> call, Response<List<GithubRepos>> response) {
                progressBar.setVisibility(View.GONE);
                for (int i = 0; i < response.body().size(); i++) {
                    GithubRepos currentRepo = response.body().get(i);
                    reposList.add(currentRepo);

                }
                Log.d("TAG", reposList.size()+"");
                reposAdapter = new ReposAdapter(ReposActivity.this, reposList);
                reposRecycler.setAdapter(reposAdapter);
            }

            @Override
            public void onFailure(Call<List<GithubRepos>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG", t.toString());
            }
        });
    }
}
