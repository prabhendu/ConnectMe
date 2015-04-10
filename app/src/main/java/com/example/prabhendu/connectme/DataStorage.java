package com.example.prabhendu.connectme;

import org.brickred.socialauth.android.SocialAuthAdapter;

import java.util.Map;

/**
 * Created by Ses on 4/10/2015.
 */
public class DataStorage {

    static String email;
    static String firstName;
    static String lastName;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

}
