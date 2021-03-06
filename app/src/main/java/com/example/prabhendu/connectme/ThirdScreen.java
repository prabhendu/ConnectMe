package com.example.prabhendu.connectme;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ThirdScreen extends ActionBarActivity {

    String companyName;
    TextView company_name;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_screen);

        DataStorage data = new DataStorage();

        company_name = (TextView) findViewById(R.id.company_name);

        companyName = getIntent().getExtras().getString("companyName");

        if(!companyName.equals("") && companyName != null) {

            company_name.setText(companyName + " tags:");

        }

        ArrayList<String> companyTags = new ArrayList<String>();
        companyTags = data.getCompanyTags(companyName);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, companyTags);
        ListView lv = (ListView) findViewById(R.id.companyTags);

        lv.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_third_screen, menu);
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
