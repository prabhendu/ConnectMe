package com.example.prabhendu.connectme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class RecruiterResumeScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_resume_screen);
        //Retrieving Tag value
        Intent intent = getIntent();
        String str = intent.getStringExtra("clickedTag");
        TextView mText = (TextView) findViewById(R.id.tag);
        mText.setText(str);

        //Filling up resumeList - Get from backend an then populate
        String[] resumeArray = {
                "Resume_Software_Engineer",
                "Resume_Security_Analyst",
                "Resume_IT_technical",
        };
        ListView resumeView = (ListView)findViewById(R.id.resumeList);
        ArrayAdapter<String> resumeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,resumeArray);
        resumeView.setAdapter(resumeAdapter);

        resumeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(RecruiterTagScreen.this, RecruiterResumeScreen.class);
                //Extracting tag clicked
                String resume = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Clicked Resume is : " + resume, Toast.LENGTH_SHORT).show();
//                intent.putExtra("clickedTag",tag);
//                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recruiter_resume_screen, menu);
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
