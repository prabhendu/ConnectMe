package com.example.prabhendu.connectme;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthError;

import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.w3c.dom.Text;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SocialAuthAdapter adapter = new SocialAuthAdapter(new ResponseListener());
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
            }

                        public void onCancel() {
                   }

                     public void onBack() {

                           }

                        public void onError(SocialAuthError err) {
                        Log.i("Log Error", "logged in");
                   }
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


}