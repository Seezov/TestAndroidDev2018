package com.example.android.testandroiddev2018;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.testandroiddev2018.entities.App;

import java.util.List;


public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    private Context mainContext;
    private List<App> apps;

    public AppAdapter(Context mainContext, List<App> apps) {
        this.mainContext = mainContext;
        this.apps = apps;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainContext);
        View view = inflater.inflate(R.layout.card_layout,null);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        App app = apps.get(position);
        holder.textViewTitle.setText(app.getName());
        holder.textViewDesc.setText(app.getLastTimeStamp());
        holder.imageView.setImageDrawable(app.getImage());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    class AppViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle, textViewDesc;

        public AppViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
        }
    }
}
