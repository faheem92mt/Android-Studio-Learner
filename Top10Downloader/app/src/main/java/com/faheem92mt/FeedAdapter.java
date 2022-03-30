package com.faheem92mt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {

    private static final String TAG = "FeedAdapter";

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<FeedEntry> applications;

    public FeedAdapter(Context context, int resource, List<FeedEntry> applications) {

        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.applications = applications;

    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvArtist = (TextView) convertView.findViewById(R.id.tvArtist);
        TextView tvSummary = (TextView) convertView.findViewById(R.id.tvSummary);

        FeedEntry currentApp = applications.get(position);

        tvName.setText(currentApp.getName());
        tvArtist.setText(currentApp.getArtist());
        tvSummary.setText(currentApp.getSummary());

        return convertView;
    }

}
