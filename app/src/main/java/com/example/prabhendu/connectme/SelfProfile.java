package com.example.prabhendu.connectme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SelfProfile extends ActionBarActivity {

    DataStorage data;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_profile);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("User information updated!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();


        data = new DataStorage();

        data.buildArrayList();
        data.setData(data.getEmail());

        //Name text is  editText2
        //Phone text is editText3
        //Email text is editText4
        //Title text is editText5
        final TextView nameTextView = (TextView) findViewById(R.id.editText2);
        try{
            nameTextView.setText(data.getFirstName() + " " + data.getLastName());
        } catch(NullPointerException e) {
            nameTextView.setHint("Full Name");
        }

        final TextView phoneTextView = (TextView) findViewById(R.id.editText3);
        try{
            if(data.getPhoneNumber().equals("")) phoneTextView.setHint("1234567890");
            else phoneTextView.setText(data.getPhoneNumber());
        } catch(NullPointerException e) {
            phoneTextView.setHint("123-456-7890");
        }

        final TextView emailTextView = (TextView) findViewById(R.id.editText4);
        try {
            emailTextView.setText(data.getEmail());
        } catch(NullPointerException e) {
            emailTextView.setHint("example@example.com");
        }

        final TextView titleTextView = (TextView) findViewById(R.id.editText5);
        try {

            if(data.getTitle().equals("")) titleTextView.setHint("Undergraduate");
            else titleTextView.setText(data.getTitle());
        } catch (NullPointerException e) {
            titleTextView.setHint("Undergraduate");
        }

        final TextView lookingForTextView = (TextView) findViewById(R.id.lookingfor);
        try {
            if(data.getLookingFor().equals("")) lookingForTextView.setHint("Internship");
            else lookingForTextView.setText(data.getLookingFor());
        } catch(NullPointerException e) {
            lookingForTextView.setHint("Internship");
        }


        Button saveChanges = (Button) findViewById(R.id.savebutton);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setEmail(emailTextView.getText().toString());

                String fullName = nameTextView.getText().toString();
                data.setFirstName(fullName.split(" ")[0]);
                data.setLastName(fullName.split(" ")[1]);

                data.setPhoneNumber(phoneTextView.getText().toString());
                data.setTitle(titleTextView.getText().toString());
                data.setLookingFor(lookingForTextView.getText().toString());

                String url = "http://128.61.104.114:18081/api/users/" + data.getID();

                new HttpAsyncTask().execute(url);

                alert.show();

            }
        });


    }

    public static String PUT(String url) {
        InputStream inputStream = null;
        String result = "";

        DataStorage data = new DataStorage();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut put = new HttpPut(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            pairs.add(new BasicNameValuePair("email", data.getEmail()));
            pairs.add(new BasicNameValuePair("firstName", data.getFirstName()));
            pairs.add(new BasicNameValuePair("lastName", data.getLastName()));
            pairs.add(new BasicNameValuePair("phoneNumber", data.getPhoneNumber()));
            pairs.add(new BasicNameValuePair("title", data.getTitle()));
            pairs.add(new BasicNameValuePair("jobSearchType", data.getLookingFor()));

            put.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse httpresponse = httpclient.execute(put);
            inputStream = httpresponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "DID NOT WORK";



        } catch(Exception e) {
            //
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.w("PUTTING", "URL: " + urls[0]);
            return PUT(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.w("SERVER SENT: ", result);
            Log.v("RESPONSE", result);
            //header2.setText(result);
            //editable.setText(result);

        }

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
