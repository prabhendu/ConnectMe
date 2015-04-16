package com.example.prabhendu.connectme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class RecruiterChooser extends ActionBarActivity {

    ArrayList<String> companiesArrayList;
    ArrayAdapter<String> adapter;
    DataStorage data;

    TextView companyNameToAdd;
    AlertDialog alert;
    AlertDialog del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_chooser);
        final ListView companyListView = (ListView)findViewById(R.id.companyList);
        companyNameToAdd = (TextView) findViewById(R.id.addCompanyText);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Company Added!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("Company Deleted!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        del = builder2.create();

        data = new DataStorage();

        companiesArrayList = new ArrayList<String>();
        try {

            JSONArray jsonArray = new JSONArray(data.getCompaniesJSON());
            this.companiesArrayList.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String companyName = obj.getString("name");
                this.companiesArrayList.add(companyName);

            }

        } catch (JSONException e) {
            //whoops
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.companiesArrayList);
        companyListView.setAdapter(adapter);

        companyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object at = companyListView.getItemAtPosition(position);
                String companyName = (String) at;

                Intent intent = new Intent(RecruiterChooser.this, ThirdScreenRecruiter.class);
                intent.putExtra("companyName", companyName);
                startActivity(intent);
            }
        });

        registerForContextMenu(companyListView);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if(v.getId() == R.id.companyList) {

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(companiesArrayList.get(info.position));
            menu.add(Menu.NONE, 0, 0, "Delete");


        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int idx = item.getItemId();

        DataStorage data = new DataStorage();

        new HttpAsyncTask4().execute("http://128.61.104.114:18081/api/companies/" + data.getCompanyID(companiesArrayList.get(info.position).toString()));

        return true;
    }

    public String POST(String url) {
        InputStream inputStream = null;
        String result = "";

        DataStorage data = new DataStorage();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            pairs.add(new BasicNameValuePair("name", companyNameToAdd.getText().toString()));

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

    public String DELETE(String url) {
        InputStream inputStream = null;
        String result = "";

        DataStorage data = new DataStorage();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpDelete delete = new HttpDelete(url);
            HttpResponse httpresponse = httpclient.execute(delete);
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

    private class HttpAsyncTask4 extends AsyncTask<String, Void, String> { //for resumes

        @Override
        protected String doInBackground(String... urls) {
            Log.w("GETTING", "URL: " + urls[0]);
            return DELETE(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.w("SERVER SENT: ", result);
            Log.v("RESPONSE", result);

            del.show();
            adapter.notifyDataSetChanged();

        }

    }

    public void newCompany(View view) {

        String url = "http://128.61.104.114:18081/api/companies/";
        new HttpAsyncTask3().execute(url);

        alert.show();

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
