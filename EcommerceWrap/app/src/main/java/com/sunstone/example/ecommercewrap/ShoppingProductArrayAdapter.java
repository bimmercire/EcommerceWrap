package com.sunstone.example.ecommercewrap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sunstone2 on 8/18/2015.
 */
public class ShoppingProductArrayAdapter extends ArrayAdapter<ShoppingProduct> {
    private final Context context;
    private final ShoppingProduct[] values;
    private final ActivityMain activity;

    public ShoppingProductArrayAdapter(ActivityMain act, ShoppingProduct[] vals) {
        super(act.getApplicationContext(), -1, vals);
        activity = act;
        context = activity.getApplicationContext();
        values = vals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_view, parent, false);
        TextView tvRowTitle = (TextView)rowView.findViewById(R.id.tv_list_item_title);
        TextView tvRowDesc = (TextView)rowView.findViewById(R.id.tv_list_item_desc);
        final Button btnAddItem = (Button)rowView.findViewById(R.id.btn_add_item);
        final Button btnRemoveItem = (Button)rowView.findViewById(R.id.btn_remove_item);

        /**
         * THIS is where textViews get filled in for each ListView row
         */
        tvRowTitle.setText(values[position].getName());
        tvRowDesc.setText(values[position].getDescription());

        // TODO: cache img url for iv_list_item_thumb with values[position].getImgSrc()

        // safe to assume the position of the ListView element (row index) is same as the inex
        // in the controller's item list, since the ListView's adapter was created in that order
        final int itemIdx = position;

        btnAddItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ShoppingProduct product = ((ShoppingController)context).getProduct(itemIdx);

                /**
                 * Sunstone_wrap
                 */
                //Original Code:
                //  activity.onClickAddItem(product);
                //
                try {
                    SnSt.onClickAddItem(activity, product, btnAddItem);
                }
                catch(Exception e) {
                    // probably noSuchMethod, just do what original code called
                    activity.onClickAddItem(product);
                }
            }
        });

        btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShoppingProduct product = ((ShoppingController)context).getProduct(itemIdx);

                /**
                 * SunStone_wrap
                 */
                // original code:
                //  activity.onClickRemoveItem(product);
                //
                try {
                    SnSt.onClickRemoveItem(activity, product, btnRemoveItem);
                }
                catch(Exception e) {
                    activity.onClickRemoveItem(product);
                }
            }
        });

        return rowView;
    }
}
