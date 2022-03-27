package com.faheem92mt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listApps;
    private TextView tvName;
    private TextView tvArtist;
    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.list_record);
        listApps = (ListView) findViewById(R.id.xmlListView);

        tvName = (TextView) findViewById(R.id.tvName);
        tvArtist = (TextView) findViewById(R.id.tvArtist);
        tvSummary = (TextView) findViewById(R.id.tvSummary);

        tvSummary.setMovementMethod(new ScrollingMovementMethod());

        //
        Log.d(TAG, "onCreate: starting Async task");
        //
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=25/xml");

        //
        Log.d(TAG, "onCreate: done");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadData";
        
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //
            Log.d(TAG, "onPostExecute: parameter is " + s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);

//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);

            ArrayList<FeedEntry> apps;
            apps = parseApplications.getApplications();
            String ss1 = apps.get(5).getName();
            String ss2 = apps.get(5).getArtist();
            String ss3 = apps.get(5).getSummary();

            tvName.setText(ss1);
            tvArtist.setText(ss2);
            tvSummary.setText(ss3);


        }

        @Override
        protected String doInBackground(String... strings) {
            //
            Log.d(TAG, "doInBackground: starts with " + strings[0]);

            String rssFeed = downloadXML(strings[0]);
            if(rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }

            //
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // "response" stores the response code retrieved from the server when opening the http connection
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response was " + response);

//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charRead;
                char[] inputBuffer = new char[500];

                while (true) {
                    charRead = reader.read(inputBuffer);
//                    Log.d(TAG, "charRead is " + charRead);
//                    System.out.println(charRead);
                    if (charRead < 0) {
                        break;
                    }
                    if (charRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charRead));
                    }
                }
                reader.close();

                return xmlResult.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception. Needs permission? " + e.getMessage());
//                e.printStackTrace();
            }

            return null;
        }

    }

}