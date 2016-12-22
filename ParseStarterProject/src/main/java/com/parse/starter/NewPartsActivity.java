package com.parse.starter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by hIVE9 on 21/12/2016.
 */

public class NewPartsActivity extends Activity
{
    private Parts parts;

    @Override
    public void onCreate(Bundle savedInstanceState){
        parts = new Parts();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        // Begin with main data entry view,
        // NewMealFragment
        setContentView(R.layout.activity_new_parts);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewPartsFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public Parts getCurrentParts() {
        return parts;
    }

}