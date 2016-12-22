package com.parse.starter;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by hIVE9 on 19/12/2016.
 */

@ParseClassName("Parts")

public class Parts extends ParseObject {

    public Parts()
    {

    }
    public String getDevice(){
        return getString("device");
    }
    public void setDevice(String device) {
        put("device", device);
    }
    public String getPrice(){
        return getString("price");
    }
    public void setPrice(String price) {
        put("price", price);
    }
    public String getNetwork(){
        return getString("network");
    }
    public void setNetwork(String network) {put("network", network);
    }
    public ParseUser getAuthor(){
        return getParseUser("author");
    }
    public void setAuthor(ParseUser user){
        put("author", user);
    }
    public ParseFile getPhotoFile(){
        return getParseFile("partsImage");
    }
    public void setPhotoFile(ParseFile file){
        put("partsImage", file);
    }
    public String getRating() {
        return getString("rating");
    }

    public void setRating(String rating) {
        put("rating", rating);
    }

}
