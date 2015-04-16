package com.example.prabhendu.connectme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Ses on 3/24/2015.
 */
public class SearchCompanies extends ActionBarActivity {

    TextView header2;
    EditText editable;
    ArrayList<String> companiesArrayList;
    ArrayAdapter<String> adapter;
    DataStorage data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_companies);
        data = new DataStorage();

        header2 = (TextView) findViewById(R.id.textView2);
        editable = (EditText) findViewById(R.id.editName);

        companiesArrayList = new ArrayList<String>();
        try {

            JSONArray jsonArray = new JSONArray(data.getCompaniesJSON());
            this.companiesArrayList.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String companyName = obj.getString("name");

                if(companyName.toUpperCase().contains(editable.getText().toString().toUpperCase())) {
                    this.companiesArrayList.add(companyName);
                }

            }

        } catch (JSONException e) {
            //whoops
        }


        final ListView lv = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.companiesArrayList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object at = lv.getItemAtPosition(position);
                String companyName = (String) at;

                Intent intent = new Intent(SearchCompanies.this, ThirdScreen.class);
                intent.putExtra("companyName", companyName);
                startActivity(intent);
            }
        });

    }

    public void refresh(View view) {

        data = new DataStorage();

        try {

            JSONArray jsonArray = new JSONArray(data.getCompaniesJSON());
            this.companiesArrayList.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String companyName = obj.getString("name");

                if(companyName.toUpperCase().contains(editable.getText().toString().toUpperCase())) {
                    this.companiesArrayList.add(companyName);
                }

            }

        } catch (JSONException e) {
            //whoops
        }

        adapter.notifyDataSetChanged();
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

}
