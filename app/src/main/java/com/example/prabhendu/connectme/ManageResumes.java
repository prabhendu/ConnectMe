package com.example.prabhendu.connectme;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ManageResumes extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_resumes);
        TextView mText = (TextView) findViewById(R.id.editName);

        final DataStorage data = new DataStorage();

        final TextView currentRes = (TextView)findViewById(R.id.currentResume);
        if(data.getCurrentResumeFilename() != null) {
            currentRes.setText(data.getCurrentResumeFilename());
        } else {
            currentRes.setText("Select a file below");
        }

        ArrayList<String> resumes = data.getResumesForEmail(data.getEmail());

        final ListView lv = (ListView)findViewById(R.id.resumeListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, resumes);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object at = lv.getItemAtPosition(position);
                String resumeFileName = (String) at;

                data.setCurrentResumeFilename(resumeFileName);
                currentRes.setText(resumeFileName);
            }
        });


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v) {
//                Intent intent = new Intent(SecondScreen.this, ThirdScreen.class);
//                startActivity(intent);
//            }
//        });
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
    public void uploadResumePopup (View view) {
        Intent intent = new Intent(this,UploadResumePopup.class);
        startActivity(intent);
    }

}
