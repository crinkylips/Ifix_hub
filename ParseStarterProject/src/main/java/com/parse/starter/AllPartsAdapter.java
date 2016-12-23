package com.parse.starter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseImageView;
import com.parse.ParseQueryAdapter;

/**
 * Created by rufflez on 1/9/16.
 */
public class AllPartsAdapter extends ParseRecyclerQueryAdapter<Parts, AllPartsAdapter.ViewHolder> {

    public AllPartsAdapter(ParseQueryAdapter.QueryFactory<Parts> factory, boolean hasStableIds) {
        super(factory, hasStableIds);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.parts_all_items_list_view, parent, false);
        ViewHolder myViewHolder = new ViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Parts parts = getItem(position);
        holder.device.setText(parts.getDevice());
        holder.network.setText(parts.getNetwork());
        holder.price.setText(parts.getPrice());
        holder.rating.setText(parts.getRating());
        holder.photo.setParseFile(parts.getPhotoFile());
        holder.photo.loadInBackground();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView device;
        TextView network;
        TextView price;
        TextView rating;
        ParseImageView photo;

        ViewHolder(View itemView) {
            super(itemView);
            device = (TextView)itemView.findViewById(R.id.device);
            network = (TextView)itemView.findViewById(R.id.network);
            device = (TextView)itemView.findViewById(R.id.device);
            rating = (TextView)itemView.findViewById(R.id.rating);
            photo = (ParseImageView)itemView.findViewById(android.R.id.icon);
        }
    }
}
