package com.example.prabhendu.connectme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;


public class SelfProfile extends ActionBarActivity {

    DataStorage data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_profile);

        data = new DataStorage();

        //Name text is  editText2
        //Phone text is editText3
        //Email text is editText4
        //Title text is editText5
        TextView name = (TextView) findViewById(R.id.editText2);
        try{
            name.setText(data.getFirstName() + " " + data.getLastName());
        } catch(NullPointerException e) {
            name.setHint("First Name");
        }

        TextView phone = (TextView) findViewById(R.id.editText3);
        phone.setHint("123-456-7890");

        TextView email = (TextView) findViewById(R.id.editText4);
        try {
            email.setText(data.getEmail());
        } catch(NullPointerException e) {
            email.setHint("example@example.com");
        }

        TextView title = (TextView) findViewById(R.id.editText5);

        android.widget.Button newResume = (Button) findViewById(R.id.newResumeButton);

        newResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newResume = new Intent(SelfProfile.this, UploadResumePopup.class);
                startActivity(newResume);
                /*Intent filePickerIntent = new Intent(SelfProfile.this, FilePickerActivity.class);
                filePickerIntent.putExtra(FilePickerActivity.REQUEST_CODE, FilePickerActivity.REQUEST_FILE);
                startActivityForResult(filePickerIntent, FilePickerActivity.REQUEST_FILE);*/
            }
        });

        title.setHint("Undergrad");

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

  /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("File activity returned", "Activity Returned");
        if(requestCode == FilePickerActivity.REQUEST_FILE
                && resultCode == RESULT_OK) {

            String filePath = data.
                    getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH);
            if(filePath != null) {
                Log.i("File Selected", filePath);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

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
