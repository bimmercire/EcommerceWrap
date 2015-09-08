package com.sunstone.example.ecommercewrap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Alan on 8/17/2015.
 */
public class SimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public SimpleArrayAdapter(Context ctx, String[] vals) {
        super(ctx, -1, vals);
        context = ctx;
        values = vals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_view, parent, false);
        TextView tvRowTitle = (TextView)rowView.findViewById(R.id.tv_list_item_title);
        TextView tvRowDesc = (TextView)rowView.findViewById(R.id.tv_list_item_desc);

        /**
         * THIS is where textViews get filled in for each ListView row
         */
        String s = values[position];
        tvRowTitle.setText(s);
        tvRowDesc.setText("some info about " + s);

        return rowView;
    }
}

