package com.parse.starter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseQueryAdapter;


public class PartsListActivity extends ListActivity {

    private ParseQueryAdapter<Parts> mainAdapter;
    private FavPartsAdapter favPartsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setClickable(false);

        mainAdapter = new ParseQueryAdapter<Parts>(this, Parts.class);
        mainAdapter.setTextKey("Parts");
        mainAdapter.setTextKey("Price");
        mainAdapter.setTextKey("Network");
        mainAdapter.setImageKey("photo");

        // Subclass of ParseQueryAdapter
        favPartsAdapter = new FavPartsAdapter(this);

        // Default view is all meals
        setListAdapter(mainAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_part_list, menu);
        return true;
    }

    /*
     * Posting meals and refreshing the list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateMealList();
                break;
            }

            case R.id.action_favorites: {
                showFavorites();
                break;
            }

            case R.id.action_new: {
                newMeal();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateMealList() {
        mainAdapter.loadObjects();
        setListAdapter(mainAdapter);
    }

    private void showFavorites() {
        favPartsAdapter.loadObjects();
        setListAdapter(favPartsAdapter);
    }

    private void newMeal() {
        Intent i = new Intent(this, NewPartsActivity.class);
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateMealList();
        }
    }

}