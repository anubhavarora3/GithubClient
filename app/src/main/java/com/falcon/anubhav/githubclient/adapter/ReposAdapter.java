package com.falcon.anubhav.githubclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.falcon.anubhav.githubclient.R;
import com.falcon.anubhav.githubclient.model.GithubRepos;

import java.util.List;

/**
 * Created by anubhav on 29/08/17.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    private List<GithubRepos> reposList;
    private Context context;

    public ReposAdapter(Context context, List<GithubRepos> reposList) {
        this.context = context;
        this.reposList = reposList;
    }

    public static class ReposViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtLanguage, txtWactcher, txtClone, txtCreatedAt, txtDescription;

        public ReposViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtClone = (TextView) itemView.findViewById(R.id.txtClone);
            txtCreatedAt = (TextView) itemView.findViewById(R.id.txtCreatedAt);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtWactcher = (TextView) itemView.findViewById(R.id.txtWatcher);
            txtLanguage = (TextView) itemView.findViewById(R.id.txtLanguage);
        }
    }

    @Override
    public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReposViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.repos_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ReposViewHolder holder, int position) {
        final GithubRepos currentRepo = reposList.get(position);
        holder.txtName.setText(context.getResources().getString(R.string.repo_name) + currentRepo.getName());
        holder.txtClone.setText(currentRepo.getCloneUrl());
        holder.txtCreatedAt.setText(context.getResources().getString(R.string.created_at) + currentRepo.getCreatedAt());
        if (currentRepo.getDescription() == null) {
            holder.txtDescription.setText(context.getResources().getString(R.string.no_description));
        } else {
            holder.txtDescription.setText(currentRepo.getDescription() + "");
        }
        holder.txtWactcher.setText(context.getResources().getString(R.string.watchers) + currentRepo.getWatchers().toString());
        holder.txtLanguage.setText(context.getResources().getString(R.string.language) + currentRepo.getLanguage());
        holder.txtClone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentRepo.getCloneUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }
}
