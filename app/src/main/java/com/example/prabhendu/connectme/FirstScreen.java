package com.example.prabhendu.connectme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class FirstScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_screen, menu);
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

    public void resumeSubmit (View view) {
        Intent intent = new Intent(this,SecondScreen.class);

        EditText editText1 = (EditText) findViewById(R.id.editText);
        String str = editText1.getText().toString();
        intent.putExtra("firstTag",str);
        startActivity(intent);
    }

    public void selfProfile (View view) {
        Intent intent = new Intent(this,SelfProfile.class);
        startActivity(intent);
    }

    public void searchCompanies(View view) {
        Intent intent = new Intent(this, SearchCompanies.class);
        startActivity(intent);
    }

}
