package com.example.prabhendu.connectme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;

public class MainActivity extends ActionBarActivity {

    static SocialAuthAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SocialAuthAdapter(new ResponseListener());
        ImageButton loginButton = (ImageButton) findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.authorize(MainActivity.this, Provider.LINKEDIN);
            }
        });
    }

    // To receive the response after authentication
    private final class ResponseListener implements DialogListener {
        public void onComplete(Bundle values) {
            Log.i("Logged In", "logged in");
            DataStorage data = new DataStorage();

            data.setEmail(adapter.getUserProfile().getEmail());
            data.setFirstName(adapter.getUserProfile().getFirstName());
            data.setLastName(adapter.getUserProfile().getLastName());

            Intent loggedIn = new Intent(MainActivity.this, roledivider.class);
            startActivity(loggedIn);
            }

        public void onCancel() {
        }

        public void onBack() {

        }

        public void onError(SocialAuthError err) {
            Log.i("Log Error", "AuthError");
            //Temporary
            //Intent loggedIn = new Intent(MainActivity.this, FirstScreen.class);
//            Intent loggedIn = new Intent(MainActivity.this, roledivider.class);
//            startActivity(loggedIn);
        }
    }

    public static SocialAuthAdapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop(){
        adapter.signOut(MainActivity.this, Provider.LINKEDIN.toString());
        super.onStop();
    }
}