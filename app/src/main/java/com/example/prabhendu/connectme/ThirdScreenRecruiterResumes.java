package com.example.prabhendu.connectme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ThirdScreenRecruiterResumes extends ActionBarActivity {

    String tagName;
    TextView company_name;
    ArrayAdapter<String> adapter;
    DataStorage data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_screen);

        data = new DataStorage();

        company_name = (TextView) findViewById(R.id.company_name);

        tagName = getIntent().getExtras().getString("tagName");

        if(!tagName.equals("") && tagName != null) {

            company_name.setText("Submitted resumes for: " + tagName);

        }

        ArrayList<String> resumes = new ArrayList<String>();
        resumes = data.getResumesForTag(tagName);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, resumes);
        final ListView lv = (ListView) findViewById(R.id.companyTags);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object at = lv.getItemAtPosition(position);
                String resumeFileName = (String) at;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://128.61.104.114:18081/api/resumes/" + data.getResumeID(resumeFileName)));
                startActivity(browserIntent);

            }
        });

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
