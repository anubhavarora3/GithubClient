package com.falcon.anubhav.githubclient.rest;

import com.falcon.anubhav.githubclient.model.GithubRepos;
import com.falcon.anubhav.githubclient.model.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by anubhav on 28/08/17.
 */

public interface GithubUserEndPoints {

    @GET("/users/{user}")
    Call<GithubUser> getUser(@Path("user") String user);

    @GET("/users/{user}/repos")
    Call<List<GithubRepos>> getRepos(@Path("user") String user);

}
