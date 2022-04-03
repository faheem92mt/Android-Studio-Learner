package com.faheem92mt;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseApplications {

    private static final String TAG = "ParseApplication";
    private ArrayList<FeedEntry> applications;

//    public ParseApplications(ArrayList<FeedEntry> applications) {
//        this.applications = applications;
//    }


    public ParseApplications() {
        // initializes the array list
        this.applications = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApplications() {
        // to return the arraylist
        return applications;
    }

    public boolean parse(String xmlData) {
        // if method parse will return false if it fails to parse
        boolean status =true;
        // the latest app while parsing the xml gets stored in this variable first
        FeedEntry currentRecord = null;
        // this boolean value will tell if we're inside the entry tag of the app in question while we're parsing the xml (the entry tag here refers to the ones inside xml)
        boolean inEntry = false;
        // this will store the value of the current tag
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            // "xmlData" is a String which holds the xml stuff (downloaded from iTunes website) and its read to the pull parser object (xpp) using a StringReader
            xpp.setInput(new StringReader(xmlData));

            // this will help us check if we have reached the end of xml and also to iterate while within the xml
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // a string that tells what is the current tag which we're at
                String tagName = xpp.getName();

                switch (eventType) {
                    // only 3 cases -> start or end of a tag and also another case to store text values within a tag

                    // case 1
                    case XmlPullParser.START_TAG:
//                        Log.d(TAG, "parse: Starting tag for " + tagName);

                        // this if condition makes sure that only if we're inside an entry tag (which represents one app) we're going
                        // to be creating a new stuff to be added to the arraylist, which will represent one particular app
                        if ("entry".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currentRecord = new FeedEntry();
                        }

                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;

                    // case 2
                    case XmlPullParser.END_TAG:
//                        Log.d(TAG, "parse: Ending tag for " + tagName);
                        if(inEntry) {
                            if("entry".equalsIgnoreCase(tagName)) {
                                applications.add(currentRecord);
                                inEntry = false;
                            } else if("name".equalsIgnoreCase(tagName)) {
                                currentRecord.setName(textValue);
                            } else if("artist".equalsIgnoreCase(tagName)) {
                                currentRecord.setArtist(textValue);
                            } else if("releaseDate".equalsIgnoreCase(tagName)) {
                                currentRecord.setReleaseDate(textValue);
                            } else if ("summary".equalsIgnoreCase(tagName)) {
                                currentRecord.setSummary(textValue);
                            } else if("image".equalsIgnoreCase(tagName)) {
                                currentRecord.setImageURL(textValue);
                            }
                        }
                        break;

                    default:
                        // do nothing
                }
                // jumps to the next tag
                eventType = xpp.next();

            }
            for (FeedEntry app: applications) {
                Log.d(TAG, "***********************");
                Log.d(TAG, app.toString());
            }

        }
        catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }


}
