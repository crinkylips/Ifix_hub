package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

/**
 * Created by rufflez on 1/5/16.
 */
public class NewPartsActivity extends AppCompatActivity {

    private ParseImageView photo;
    private EditText device;
    private EditText price;
    private EditText network;
    private Spinner rating_spinner;
    private static final int PHOTO_INTENT_REQUEST = 100;
    private Bitmap bitmap;
    private Parts parts;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_parts_activity);

        photo = (ParseImageView)findViewById(R.id.parts_image);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PHOTO_INTENT_REQUEST);
                }
            }
        });
        device = (EditText)findViewById(R.id.device);
        network = (EditText)findViewById(R.id.network);
        price = (EditText)findViewById(R.id.price);
        rating_spinner = (Spinner)findViewById(R.id.rating_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(this, R.array.ratings_array,
                        android.R.layout.simple_spinner_dropdown_item);
        rating_spinner.setAdapter(spinnerAdapter);
        parts = new Parts();
        FloatingActionButton save = (FloatingActionButton)findViewById(R.id.fab);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parts.setDevice(device.getText().toString());
                parts.setPrice(price.getText().toString());
                parts.setNetwork(network.getText().toString());
                parts.setAuthor(ParseUser.getCurrentUser());
                parts.setRating(rating_spinner.getSelectedItem().toString());
                parts.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            finish();
                        } else {
                            Snackbar.make(getCurrentFocus(), "Error saving: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_INTENT_REQUEST){
            if (resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                bitmap = (Bitmap)extras.get("data");
                byte[] bytes = bitmapToByteArray(bitmap);
                final ParseFile photoFile = new ParseFile("partsphoto.jpg", bytes);
                photoFile.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(NewPartsActivity.this, "Error saving: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            parts.setPhotoFile(photoFile);
                        }
                    }
                });
                photo.setParseFile(photoFile);
                photo.loadInBackground();
            }
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
