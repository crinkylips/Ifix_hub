package com.parse.starter;

/**
 * Created by hIVE9 on 22/12/2016.
 */

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by rufflez on 1/5/16.
 */
@ParseClassName("Parts")
public class Parts extends ParseObject{
    public Parts() {
        // A default constructor is required.
    }

    public String getDevice() {
        return getString("device");
    }

    public void setDevice(String title) {
        put("device", title);
    }

    public String getPrice() {
        return getString("price");
    }

    public void setPrice(String title) {
        put("price", title);
    }

    public String getNetwork() {
        return getString("network");
    }

    public void setNetwork(String title) {
        put("network", title);
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser user) {
        put("author", user);
    }

    public String getRating() {
        return getString("rating");
    }

    public void setRating(String rating) {
        put("rating", rating);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }
}