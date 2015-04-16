package com.example.prabhendu.connectme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class roledivider extends ActionBarActivity {

    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roledivider);

        header = (TextView) findViewById(R.id.header);

        new HttpAsyncTask().execute("http://128.61.104.114:18081/api/users");
        new HttpAsyncTask2().execute("http://128.61.104.114:18081/api/companies");
        new HttpAsyncTask3().execute("http://128.61.104.114:18081/api/resumes");
    }

    @Override
    protected void onResume() {
        super.onResume();

        new HttpAsyncTask().execute("http://128.61.104.114:18081/api/users");
        new HttpAsyncTask2().execute("http://128.61.104.114:18081/api/companies");
        new HttpAsyncTask3().execute("http://128.61.104.114:18081/api/resumes");
    }

    public static String POST(String url) {
        InputStream inputStream = null;
        String result = "";

        DataStorage data = new DataStorage();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            pairs.add(new BasicNameValuePair("email", data.getEmail()));
            pairs.add(new BasicNameValuePair("firstName", data.getFirstName()));
            pairs.add(new BasicNameValuePair("lastName", data.getLastName()));

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

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpresponse = httpclient.execute(new HttpGet(url));
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
            return POST(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.w("SERVER SENT: ", result);
            Log.v("RESPONSE", result);
            //header2.setText(result);
            //editable.setText(result);

        }

    }

    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> { //for companies

        @Override
        protected String doInBackground(String... urls) {
            Log.w("GETTING", "URL: " + urls[0]);
            return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.w("SERVER SENT: ", result);
            Log.v("RESPONSE", result);

            DataStorage data = new DataStorage();
            data.setCompaniesJSON(result);

        }

    }

    private class HttpAsyncTask3 extends AsyncTask<String, Void, String> { //for resumes

        @Override
        protected String doInBackground(String... urls) {
            Log.w("PUTTING", "URL: " + urls[0]);
            return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.w("SERVER SENT: ", result);
            Log.v("RESPONSE", result);
            //header2.setText(result);
            //editable.setText(result);

            DataStorage data = new DataStorage();
            data.setResumesJSON(result);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_roledivider, menu);
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

    public void studentScreen (View view) {
        Intent intent = new Intent(roledivider.this, FirstScreen.class);
        startActivity(intent);
    }

    public void recruiterScreen (View view) {
        Intent intent = new Intent(roledivider.this, RecruiterChooser.class);
        startActivity(intent);
    }
}
