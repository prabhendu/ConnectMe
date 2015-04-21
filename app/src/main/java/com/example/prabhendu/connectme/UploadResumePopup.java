package com.example.prabhendu.connectme;

import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.UUID;
import android.widget.TextView;
import android.view.View;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.devpaul.filepickerlibrary.FilePickerActivity;
import com.devpaul.filepickerlibrary.enums.FileScopeType;
import com.devpaul.filepickerlibrary.enums.FileType;
import com.devpaul.filepickerlibrary.enums.ThemeType;
import com.dropbox.chooser.android.DbxChooser;
import com.alexbbb.uploadservice.UploadService;
import com.alexbbb.uploadservice.AbstractUploadServiceReceiver;
import com.alexbbb.uploadservice.ContentType;
import com.alexbbb.uploadservice.UploadRequest;

/**
 * Created by pushpa on 4/5/15.
 */
public class UploadResumePopup extends ActionBarActivity {

    final int DBX_CHOOSER_REQUEST = 0;  // You can change this if needed
    final String serverUrlString = "http://128.61.104.114:18081/api/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_resume_popup);

        final DbxChooser mChooser;

        mChooser = new DbxChooser("o9iw0atsrfkk6u9");

        String[] newResumeArray = {
                "Upload from SD Card",
                "Upload using DropBox",
        };
        UploadService.NAMESPACE = "com.example.prabhnedu.connectme";

        ListView lv = (ListView) findViewById(R.id.newResumeList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newResumeArray);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                    Intent filePickerIntent = new Intent(UploadResumePopup.this, FilePickerActivity.class);
                    filePickerIntent.putExtra(FilePickerActivity.REQUEST_CODE, FilePickerActivity.REQUEST_FILE);
                    startActivityForResult(filePickerIntent, FilePickerActivity.REQUEST_FILE);
                } else if (id == 1) {
                    mChooser.forResultType(DbxChooser.ResultType.DIRECT_LINK)
                            .launch(UploadResumePopup.this, DBX_CHOOSER_REQUEST);
                }
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

      if(requestCode == FilePickerActivity.REQUEST_FILE)
        {
            Log.i("File activity returned", "Activity Returned");
            if (requestCode == FilePickerActivity.REQUEST_FILE
                    && resultCode == RESULT_OK) {

                String filePath = data.
                        getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH);
                if (filePath != null) {
                    Log.i("File Selected", filePath);
                    final UploadRequest request = new UploadRequest(this, UUID.randomUUID().toString(), serverUrlString);
                    request.addFileToUpload(filePath,
                            "fieldname",
                            "resume.pdf",
                            "APPLICATION_PDF");

                    request.setNotificationConfig(android.R.drawable.ic_menu_upload,
                            "notification title",
                            "upload in progress text",
                            "upload completed successfully text",
                            "upload error text",
                            false);

                    try {
                        //Start upload service and display the notification
                        UploadService.startUpload(request);

                    } catch (Exception exc) {
                        //You will end up here only if you pass an incomplete UploadRequest
                        Log.e("AndroidUploadService", exc.getLocalizedMessage(), exc);
                    }



                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
       else if(requestCode == DBX_CHOOSER_REQUEST)
      {
          if (resultCode == RESULT_OK) {
              DbxChooser.Result result = new DbxChooser.Result(data);
              Log.d("main", "Link to selected file: " + result.getLink());

              // Handle the result ( This will be the actual file )
          } else {
              // Failed or was cancelled by the user.
          }
      } else {
          super.onActivityResult(requestCode, resultCode, data);
      }
      }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_reume, menu);
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
