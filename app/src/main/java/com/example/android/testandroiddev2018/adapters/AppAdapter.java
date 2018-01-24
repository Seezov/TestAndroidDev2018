package com.example.android.testandroiddev2018.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.testandroiddev2018.R;
import com.example.android.testandroiddev2018.entities.App;
import com.example.android.testandroiddev2018.listeners.RecyclerViewClickListener;

import java.util.List;


public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    private Context mainContext;
    private static RecyclerViewClickListener itemListener;
    private List<App> apps;

    public AppAdapter(Context mainContext, List<App> apps, RecyclerViewClickListener itemListener) {
        this.mainContext = mainContext;
        AppAdapter.itemListener = itemListener;
        this.apps = apps;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainContext);
        View view = inflater.inflate(R.layout.card_layout,null);;
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

    class AppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textViewTitle, textViewDesc;

        AppViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, getLayoutPosition());
        }
    }
}
