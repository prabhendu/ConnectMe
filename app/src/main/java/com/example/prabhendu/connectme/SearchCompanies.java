package com.example.prabhendu.connectme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    String json;
    ArrayList<String> companiesArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_companies);

        header2 = (TextView) findViewById(R.id.textView2);
        editable = (EditText) findViewById(R.id.editName);

        new HttpAsyncTask().execute("http://128.61.104.114:18081/api/companies");
        companiesArrayList = new ArrayList<String>();
        companiesArrayList.add("Loading companies");

        ListView lv = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.companiesArrayList);
        lv.setAdapter(adapter);

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

    public void refresh(View view) {
        new HttpAsyncTask().execute("http://128.61.104.114:18081/api/companies");

        if(json != null) {
            Log.i("REFRESH", "REFRESH CALLED");
            try {

                JSONArray jsonArray = new JSONArray(json);
                Log.i("ARRAY INFO", "Length: " + jsonArray.length());
                this.companiesArrayList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String companyName = obj.getString("name");
                    Log.i("COMPANY NAME: ", companyName);
                   this.companiesArrayList.add(companyName);

                }

            } catch (JSONException e) {
                //whoops
            }
        }

        adapter.notifyDataSetChanged();
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
            json = result;
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

}
