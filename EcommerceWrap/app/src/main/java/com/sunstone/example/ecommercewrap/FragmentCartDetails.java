package com.sunstone.example.ecommercewrap;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunstone2 on 8/18/2015.
 */
public class FragmentCartDetails extends DialogFragment {

    private static final String TAG = FragmentCartDetails.class.getSimpleName();

    private boolean bShowConfirm;

    static FragmentCartDetails newInstance(boolean finalSummary) {
        FragmentCartDetails f = new FragmentCartDetails();

        Bundle args = new Bundle();
        args.putBoolean("finalSummary", finalSummary);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bShowConfirm = getArguments().getBoolean("finalSummary");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Cart Details");
        View v = inflater.inflate(R.layout.fragment_cart_details, container, false);

        ViewGroup content = (ViewGroup)v.findViewById(R.id.v_cart_details);
        content.removeAllViews();

        ShoppingCart cart = ((ShoppingController)(getActivity().getApplicationContext())).getCart();
        Map<String, Integer> productCounts = new HashMap<String, Integer>();
        for(int i = 0; i < cart.getSize(); ++i) {
            String productName = cart.getProduct(i).getName();
            if(!productCounts.containsKey(productName)) {
                productCounts.put(productName, 1);
            }
            else {
                int count = productCounts.get(productName);
                ++count;
                productCounts.put(productName, count);
            }
        }

        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            TextView tvCurPoduct = new TextView(getActivity().getApplicationContext());
            tvCurPoduct.setText(entry.getValue() + "x\t" + entry.getKey());
            content.addView(tvCurPoduct);
        }

        TextView tvSize = new TextView(getActivity().getApplicationContext());
        tvSize.setText("Item Count: " + cart.getSize());
        tvSize.setBackgroundColor(Color.CYAN);
        content.addView(tvSize);

        Button btnDismiss = (Button) v.findViewById(R.id.btn_dismiss_cart_details);
        Button btnConfirm = (Button)v.findViewById(R.id.btn_confirm_purchase);
        if(bShowConfirm){
            btnDismiss.setVisibility(View.GONE);
            final ActivityMain activity = (ActivityMain)getActivity();
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onClickConfirmPurchase(v);
                }
            });
        }
        else {
            btnConfirm.setVisibility(View.GONE);
            btnDismiss.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        /**
         * SunStone_inject
         * WARNING: this assumes the view doesn't already have a touchListener set in the
         * original source code.
         */
        content.setOnTouchListener(new SnSt.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // this does the logging
                boolean result = super.onTouch(v, event);

                // this is extra for displaying logging
                String curLog = SnSt.getLatestTouchLog();
                TextView tvCurLog = (TextView)getActivity().findViewById(R.id.tv_recent_touch_log);
                tvCurLog.setText(curLog);

                return result;
            }
        });

        return v;

    }


}
