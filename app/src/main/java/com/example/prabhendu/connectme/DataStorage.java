package com.example.prabhendu.connectme;

import android.util.Log;

import org.brickred.socialauth.android.SocialAuthAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ses on 4/10/2015.
 */
public class DataStorage {

    static String id;

    static String email;
    static String firstName;
    static String lastName;
    static String phoneNumber;
    static String title;
    static String lookingFor;

    static String json;
    static ArrayList<JSONObject> users;

    public void setID(String id) { this.id = id; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setTitle(String title) { this.title = title; }

    public void setLookingFor(String lookingFor) { this.lookingFor = lookingFor; }

    public void setJson(String json) { this.json = json;}

    //---

    public String getID() { return this.id; }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNumber() { return this.phoneNumber; }

    public String getTitle() { return this.title; }

    public String getLookingFor() { return this.lookingFor; }

    public String getJson() { return this.json; }

    public void buildArrayList() {

        if(users == null) users = new ArrayList<JSONObject>();

        if(json != null) {

            try {

                JSONArray jsonArray = new JSONArray(json);
                users.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    users.add(obj);
                }

            } catch (JSONException e) {
                //whoops
            }


        }

    }

    public void setData(String email) {

        JSONObject temp = new JSONObject();

        Log.v("SETTING DATA FOR", email);

        for(JSONObject i : users) {

            try {

                Log.v("EMAIL CHECK: ", i.getString("email"));

                if (i.getString("email").equals(email)) {
                    temp = i;
                    Log.v("EMAIL FOUND", " lol ");
                    break;
                }

            } catch(JSONException e) {
                //whoops
            }

        }

        if(!temp.isNull("email")) {

            try {

                setID(temp.getString("_id"));
                setEmail(temp.getString("email"));
                setLastName(temp.getString("lastName"));
                setFirstName(temp.getString("firstName"));
                setPhoneNumber(temp.getString("phoneNumber"));
                setTitle(temp.getString("title"));
                setLookingFor(temp.getString("jobSearchType"));



            } catch (JSONException e) {
                Log.v("JSONERRORDATA", "WUTHAPPENED");
            }

        }

    }

}
