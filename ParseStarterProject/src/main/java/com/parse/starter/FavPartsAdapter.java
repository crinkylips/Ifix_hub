package com.parse.starter;

import android.content.Context;

import com.parse.ParseQueryAdapter;

import java.util.Arrays;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;

/*
 * The FavoriteMealAdapter is an extension of ParseQueryAdapter
 * that has a custom layout for favorite meals, including a
 * bigger preview image, the meal's rating, and a "favorite"
 * star.
 */

public class FavPartsAdapter extends ParseQueryAdapter<Parts> {

    public FavPartsAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Parts>() {
            public ParseQuery<Parts> create() {
                // Here we can configure a ParseQuery to display
                // only top-rated meals.
                ParseQuery query = new ParseQuery("Parts");
                query.whereContainedIn("rating", Arrays.asList("5", "4"));
                query.orderByDescending("rating");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Parts parts, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_favorites, null);
        }

        super.getItemView(parts, v, parent);

        ParseImageView partsImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = parts.getParseFile("photo");
        if (photoFile != null) {
            partsImage.setParseFile(photoFile);
            partsImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(parts.getDevice());
        TextView ratingTextView = (TextView) v
                .findViewById(R.id.favorite_parts_rating);
        ratingTextView.setText(parts.getRating());
        return v;
    }

}

