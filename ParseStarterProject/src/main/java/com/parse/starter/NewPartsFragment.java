package com.parse.starter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by hIVE9 on 21/12/2016.
 */

public class NewPartsFragment extends Fragment {

    private ImageButton photoButton;
    private Button saveButton;
    private Button cancelButton;
    private EditText device;
    private TextView devicePrice;
    private TextView  deviceNetwork;
    private Spinner PartsRating;
    private ParseImageView partsPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.new_parts_fragment, parent, false);

        device = ((EditText) v.findViewById(R.id.device_name));
        devicePrice = ((EditText) v.findViewById(R.id.device_price));
        deviceNetwork = ((EditText) v.findViewById(R.id.device_network));

        // The mealRating spinner lets people assign favorites of meals they've
        // eaten.
        // Meals with 4 or 5 ratings will appear in the Favorites view.
        PartsRating = ((Spinner) v.findViewById(R.id.rating_spinner));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.ratings_array,
                        android.R.layout.simple_spinner_dropdown_item);
        PartsRating.setAdapter(spinnerAdapter);

        photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(device.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(devicePrice.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(deviceNetwork.getWindowToken(), 0);
                startCamera();
            }
        });

        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Parts parts = ((NewPartsActivity) getActivity()).getCurrentParts();

                // When the user clicks "Save," upload the meal to Parse
                // Add data to the meal object:
                parts.setDevice(device.getText().toString());
                parts.setPrice(devicePrice.getText().toString());
                parts.setNetwork(deviceNetwork.getText().toString());

                // Associate the meal with the current user
                parts.setAuthor(ParseUser.getCurrentUser());

                // Add the rating
                parts.setRating(PartsRating.getSelectedItem().toString());

                // If the user added a photo, that data will be
                // added in the CameraFragment

                // Save the meal and return
                parts.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
        });

        cancelButton = ((Button) v.findViewById(R.id.cancel_button));
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });

        // Until the user has taken a photo, hide the preview
        partsPreview = (ParseImageView) v.findViewById(R.id.parts_preview);
        partsPreview.setVisibility(View.INVISIBLE);

        return v;
    }

    /*
     * All data entry about a Meal object is managed from the NewMealActivity.
     * When the user wants to add a photo, we'll start up a custom
     * CameraFragment that will let them take the photo and save it to the Meal
     * object owned by the NewMealActivity. Create a new CameraFragment, swap
     * the contents of the fragmentContainer (see activity_new_meal.xml), then
     * add the NewMealFragment to the back stack so we can return to it when the
     * camera is finished.
     */
    public void startCamera() {
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, cameraFragment);
        transaction.addToBackStack("NewMealFragment");
        transaction.commit();
    }

    /*
     * On resume, check and see if a meal photo has been set from the
     * CameraFragment. If it has, load the image in this fragment and make the
     * preview image visible.
     */
    @Override
    public void onResume() {
        super.onResume();
        ParseFile photoFile = ((NewPartsActivity) getActivity())
                .getCurrentParts().getPhotoFile();
        if (photoFile != null) {
            partsPreview.setParseFile(photoFile);
            partsPreview.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    partsPreview.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}
