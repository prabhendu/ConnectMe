package com.example.prabhendu.connectme;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FirstScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        new HttpAsyncTask().execute("http://128.61.104.114:18081/api/users");
    }

    @Override
    protected void onResume() {
        super.onResume();
        new HttpAsyncTask().execute("http://128.61.104.114:18081/api/users");
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
            Log.w("GETTING", "URL: " + urls[0]);
            return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.w("SERVER SENT: ", result);
            Log.v("RESPONSE", result);
            //header2.setText(result);
            //editable.setText(result);
            DataStorage data = new DataStorage();
            data.setJson(result);

            Log.v("JSONSTR", "is now" + data.getJson());

        }

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

    public void resumeSubmit (View view) {
        Intent intent = new Intent(this,SecondScreen.class);

        EditText editText1 = (EditText) findViewById(R.id.editText);
        String str = editText1.getText().toString();
        intent.putExtra("firstTag",str);
        startActivity(intent);
    }

    public void selfProfile (View view) {
        Intent intent = new Intent(this,SelfProfile.class);
        startActivity(intent);
    }

    public void searchCompanies(View view) {
        Intent intent = new Intent(this, SearchCompanies.class);
        startActivity(intent);
    }

}
