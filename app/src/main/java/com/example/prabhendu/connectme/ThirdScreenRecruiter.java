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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ThirdScreenRecruiter extends ActionBarActivity {

    String companyName;
    TextView company_name;
    ArrayAdapter<String> adapter;

    TextView tagToAdd;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_screen_recruiter);

        tagToAdd = (TextView) findViewById(R.id.tagAdd);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tag Added!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();

        DataStorage data = new DataStorage();

        company_name = (TextView) findViewById(R.id.company_name);

        companyName = getIntent().getExtras().getString("companyName");

        if(!companyName.equals("") && companyName != null) {

            company_name.setText(companyName);

        }

        ArrayList<String> companyTags = new ArrayList<String>();
        companyTags = data.getCompanyTags(companyName);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, companyTags);
        final ListView lv = (ListView) findViewById(R.id.companyTags);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object at = lv.getItemAtPosition(position);
                String tagName = (String) at;

                Intent intent = new Intent(ThirdScreenRecruiter.this, ThirdScreenRecruiterResumes.class);
                intent.putExtra("tagName", tagName);
                startActivity(intent);
            }
        });

    }

    public String POST(String url) {
        InputStream inputStream = null;
        String result = "";

        DataStorage data = new DataStorage();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            pairs.add(new BasicNameValuePair("tag", tagToAdd.getText().toString()));

            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse httpresponse = httpclient.execute(post);
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


    private class HttpAsyncTask3 extends AsyncTask<String, Void, String> { //for resumes

        @Override
        protected String doInBackground(String... urls) {
            Log.w("GETTING", "URL: " + urls[0]);
            return POST(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.w("SERVER SENT: ", result);
            Log.v("RESPONSE", result);



        }

    }

    public void newTag(View view) {

        DataStorage data = new DataStorage();

        String url = "http://128.61.104.114:18081/api/companies/" + data.getCompanyID(companyName) + "/tags";
        new HttpAsyncTask3().execute(url);

        alert.show();

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
