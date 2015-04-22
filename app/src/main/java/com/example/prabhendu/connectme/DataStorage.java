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

    static String currentResumeFilename;

    static String json;
    static ArrayList<JSONObject> users;

    static String companiesJSON;

    static String resumesJSON;

    static String sentJSON;

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

    public void setCurrentResumeFilename(String name) { this.currentResumeFilename = name;}

    public void setJson(String json) { this.json = json;}

    public void setCompaniesJSON(String companiesJSON) { this.companiesJSON = companiesJSON; }

    public void setResumesJSON(String resumesJSON) { this.resumesJSON = resumesJSON; }

    public void setSentJSON(String sentJSON) { this.sentJSON = sentJSON; }

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

    public String getCurrentResumeFilename() { return this.currentResumeFilename; }

    public String getJson() { return this.json; }

    public String getCompaniesJSON() { return this.companiesJSON; }

    public String getResumesJSON() { return this.resumesJSON; }

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

    public ArrayList<String> getCompanyTags(String companyName) {

        ArrayList<String> temp = new ArrayList<String>();


        try {

            JSONArray jsonarr = new JSONArray(companiesJSON);

            for(int i=0; i<jsonarr.length(); i++) {

                JSONObject obj = jsonarr.getJSONObject(i);

                if(obj.getString("name").equals(companyName)) {

                    JSONArray tags = obj.getJSONArray("tags");

                    for(int j=0; j<tags.length(); j++) {

                        String tag = (String) tags.get(j);
                        temp.add(tag);

                    }

                    break;

                }


            }


        } catch (JSONException e) {
            //whoopsie
        }

        return temp;


    }

    public ArrayList<String> getResumesForEmail(String email) {

        ArrayList<String> temp = new ArrayList<String>();

        try {

            JSONArray jsonarr = new JSONArray(resumesJSON);

            for(int i=0; i<jsonarr.length(); i++) {

                JSONObject obj = jsonarr.getJSONObject(i);

                if(obj.getString("email").equals(email)) {

                    temp.add(obj.getString("fileName"));

                }

            }


        } catch(JSONException e) {
            //oops
        }

        return temp;


    }

    public ArrayList<String> getResumesForTag(String tag) {

        ArrayList<String> temp = new ArrayList<String>();


        try {

            JSONArray jsonarr = new JSONArray(sentJSON);

            for(int i=0; i<jsonarr.length(); i++) {

                JSONObject obj = jsonarr.getJSONObject(i);

                if(obj.getString("tag").equals(tag)) {

                    temp.add(obj.getString("fileName"));
                }


            }


        } catch (JSONException e) {
            //whoopsie
        }

        return temp;


    }

    public String getCompanyID(String companyName) {

        ArrayList<String> temp = new ArrayList<String>();


        try {

            JSONArray jsonarr = new JSONArray(companiesJSON);

            for(int i=0; i<jsonarr.length(); i++) {

                JSONObject obj = jsonarr.getJSONObject(i);

                if(obj.getString("name").equals(companyName)) {

                    return obj.getString("_id");

                }


            }


        } catch (JSONException e) {
            //whoopsie
        }

        return "";

    }

    public String getResumeID(String filename) {

        ArrayList<String> temp = new ArrayList<String>();

        try {

            JSONArray jsonarr = new JSONArray(resumesJSON);

            for(int i=0; i<jsonarr.length(); i++) {

                JSONObject obj = jsonarr.getJSONObject(i);

                if(obj.getString("fileName").equals(filename)) {

                    return obj.getString("_id");

                }

            }

        } catch(JSONException e) {
            //oops
        }

        return "";

    }


}
