package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import java.util.Arrays;
import java.util.List;


public class MainPartsActivity extends AppCompatActivity {
    private AllPartsAdapter mAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_parts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        ParseQueryAdapter.QueryFactory<Parts> factory = new ParseQueryAdapter.QueryFactory<Parts>() {
            @Override
            public ParseQuery<Parts> create() {
                ParseQuery query = new ParseQuery("Parts");
                query.orderByDescending("device");
                return query;
            }
        };
        mAdapter = new AllPartsAdapter(factory, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainPartsActivity.this));
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        mAdapter.addOnQueryLoadListener(new ParseRecyclerQueryAdapter.OnQueryLoadListener<Parts>() {
            @Override
            public void onLoaded(List<Parts> objects, Exception e) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onLoading() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.loadObjects();
            }
        });
        recyclerView.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPartsActivity.this, NewPartsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        mAdapter.loadObjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_parts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_favorites:
                ParseQueryAdapter.QueryFactory<Parts> favoritefactory = new ParseQueryAdapter.QueryFactory<Parts>() {
                    @Override
                    public ParseQuery<Parts> create() {
                        ParseQuery query = new ParseQuery("Parts");
                        query.whereContainedIn("rating", Arrays.asList("5", "4"));
                        query.orderByDescending("rating");
                        return query;
                    }
                };
                mAdapter = new AllPartsAdapter(favoritefactory, true);
                recyclerView.setAdapter(mAdapter);
                break;
            case R.id.myparts:
                ParseQueryAdapter.QueryFactory<Parts> allFactory = new ParseQueryAdapter.QueryFactory<Parts>() {
                    @Override
                    public ParseQuery<Parts> create() {
                        ParseQuery query = new ParseQuery("Parts");
                        query.orderByDescending("device");
                        return query;
                    }
                };
                mAdapter = new AllPartsAdapter(allFactory, true);
                recyclerView.setAdapter(mAdapter);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
