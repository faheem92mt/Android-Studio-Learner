package com.faheem92mt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private List list;
    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit = 10;
    private boolean start = true;
    private String feedUrl2 = "";

    private static final String STATE_FEED_LIMIT = "FeedLimit";
    private static final String STATE_FEED_URL = "Url";


//    private TextView tvName;
//    private TextView tvArtist;
//    private TextView tvSummary;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        listApps = (ListView) findViewById(R.id.xmlListView);

        downloadUrl(String.format(feedUrl,feedLimit));

//        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};

//        list = new ArrayList<String>();
//        list.add("Hello, welcome to the Top 10 Downloader App.");
//        list.add("");
//        list.add("To start, click the 3 dots and choose your");
//        list.add("options.");

//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);

//            ArrayList<FeedEntry> apps;
//            apps = parseApplications.getApplications();
//            String ss1 = apps.get(5).getName();
//            String ss2 = apps.get(5).getArtist();
//            String ss3 = apps.get(5).getSummary();
//
//
//            tvName.setText(ss1);
//            tvArtist.setText(ss2);
//            tvSummary.setText(ss3);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, cars);
//        listApps.setAdapter(arrayAdapter);

//        tvName = (TextView) findViewById(R.id.tvName);
//        tvArtist = (TextView) findViewById(R.id.tvArtist);
//        tvSummary = (TextView) findViewById(R.id.tvSummary);
//
//        tvSummary.setMovementMethod(new ScrollingMovementMethod());



//        if (start) {
//            downloadUrl(String.format(feedUrl,feedLimit));
//            start = false;
//        }


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        feedLimit = savedInstanceState.getInt(STATE_FEED_LIMIT);
        feedUrl = savedInstanceState.getString(STATE_FEED_URL);
        downloadUrl(String.format(feedUrl,feedLimit));
        feedUrl2 = feedUrl;

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_FEED_LIMIT,feedLimit);
        outState.putString(STATE_FEED_URL,feedUrl2);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        if(feedLimit == 10) {
            menu.findItem(R.id.mnu10).setChecked(true);
        }
        else {
            menu.findItem(R.id.mnu25).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        boolean need = true;


        switch (id) {
            case R.id.mnuRefresh:
                feedUrl = feedUrl;
                need = true;
                break;
            case R.id.mnuFree:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                if (feedUrl.equals(feedUrl2)) {
                    need = false;
                }
                feedUrl2 = feedUrl;
                break;
            case R.id.mnuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                if (feedUrl.equals(feedUrl2)) {
                    need = false;
                }
                feedUrl2 = feedUrl;
                break;
            case R.id.mnuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                if (feedUrl.equals(feedUrl2)) {
                    need = false;
                }
                feedUrl2 = feedUrl;
                Log.d(TAG, "feed2 is " + feedUrl2 );
                break;
            case R.id.mnu10:
            case R.id.mnu25:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    feedLimit = 35 - feedLimit;
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + " setting feedLimit ti " + feedLimit);
                }
                else {
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + " feedLimit unchanged");
                    need = false;
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

//        downloadUrl(String.format(feedUrl,feedLimit));

        if (need) {
            downloadUrl(String.format(feedUrl,feedLimit));
        }
        else {
            need = true;
        }

        return true;

    }

    private void downloadUrl(String feedUrl) {
        //
        Log.d(TAG, "downloadUrl: starting Async task");
        //
        DownloadData downloadData = new DownloadData();
        downloadData.execute(feedUrl);

        //
        Log.d(TAG, "downloadUrl: done");
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

            FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, R.layout.list_record, parseApplications.getApplications());
            listApps.setAdapter(feedAdapter);

//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);

//            ArrayList<FeedEntry> apps;
//            apps = parseApplications.getApplications();
//            String ss1 = apps.get(5).getName();
//            String ss2 = apps.get(5).getArtist();
//            String ss3 = apps.get(5).getSummary();
//
//
//            tvName.setText(ss1);
//            tvArtist.setText(ss2);
//            tvSummary.setText(ss3);


        }

        @Override
        protected String doInBackground(String... strings) {
            //
            Log.d(TAG, "doInBackground: starts with " + strings[0]);

            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
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