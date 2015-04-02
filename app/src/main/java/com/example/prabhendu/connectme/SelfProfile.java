package com.example.prabhendu.connectme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class SelfProfile extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_profile);
        //Name text is  editText2
        //Phone text is editText3
        //Email text is editText4
        //Title text is editText5
        TextView name = (TextView) findViewById(R.id.editText2);
        name.setText("Prabhendu Pandey");

        TextView phone = (TextView) findViewById(R.id.editText3);
        phone.setText("555-678-1234");

        TextView email = (TextView) findViewById(R.id.editText4);
        email.setText("prabhendu@gmail.com");

        TextView title = (TextView) findViewById(R.id.editText5);
        title.setText("Graduate Student");

        String[] resumeArray = {
                "Resume_Software_Engineer",
                "Resume_Security_Analyst",
                "Resume_IT_technical",
        };

        ListView lv = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,resumeArray);
        lv.setAdapter(adapter);


    }

    public void selfProfile (View view) {
        Intent intent = new Intent(this,SelfProfile.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_self_profile, menu);
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
