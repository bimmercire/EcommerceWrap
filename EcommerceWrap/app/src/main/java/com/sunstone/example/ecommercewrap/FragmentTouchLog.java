package com.sunstone.example.ecommercewrap;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunstone2 on 8/18/2015.
 */
public class FragmentTouchLog  extends DialogFragment {

    private static final String TAG = FragmentTouchLog.class.getSimpleName();
    private List<String> logs;

    static FragmentTouchLog newInstance(ArrayList<String> logs) {
        FragmentTouchLog f = new FragmentTouchLog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putStringArrayList("logs", logs);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logs = getArguments().getStringArrayList("logs");
        Log.d(TAG, ">>>onCreate() logs.size() " + logs.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Touch Log");
        View v = inflater.inflate(R.layout.fragment_touch_log, container, false);

        ViewGroup content = (ViewGroup)v.findViewById(R.id.v_logs);

        for(String curLog : logs) {
            Log.d(TAG, "___ adding log:" + curLog);
            TextView tvLog = new TextView(getActivity().getApplicationContext());
            tvLog.setText(curLog);
            tvLog.setTypeface(Typeface.MONOSPACE);
            tvLog.setTextColor(Color.GREEN);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 20);
            content.addView(tvLog, params);
        }

        return v;

    }
}
