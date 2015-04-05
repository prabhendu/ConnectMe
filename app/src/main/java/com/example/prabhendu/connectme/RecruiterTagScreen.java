package com.example.prabhendu.connectme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class RecruiterTagScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_tag_screen);

             //Filling up tagList - Get from backend
        String[] tagArray = {
                "APPLE_GT",
                "APPLE_GSU",
                "APPLE_UCSD",
                "APPLE_UCLA"
        };
        final ListView tagView = (ListView)findViewById(R.id.tagList);
        ArrayAdapter<String> tagAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tagArray);
        tagView.setAdapter(tagAdapter);

        tagView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecruiterTagScreen.this, RecruiterResumeScreen.class);
                //Extracting tag clicked
                String tag = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Tag is : " +tag, Toast.LENGTH_SHORT).show();
                intent.putExtra("clickedTag",tag);
                startActivity(intent);
            }
        });

    }

    public void createTag (View view) {
        Intent intent = new Intent (this,TagCreationScreen.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recruiter_first_screen, menu);
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
