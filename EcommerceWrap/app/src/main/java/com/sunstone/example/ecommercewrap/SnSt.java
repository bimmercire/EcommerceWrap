package com.sunstone.example.ecommercewrap;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sunstone2 on 8/19/2015.
 *
 * static class containing SunStone added stuff (injected functions, wrappers, callbacks)
 */
public class SnSt {

    private static final String TAG = SnSt.class.getSimpleName();

    private static boolean touchLogActive = true;
    private static ArrayList<String> touchLog = new ArrayList<String>();

    private SnSt(){}

    public static void resetLogging() {
        touchLog.clear();
        touchLogActive = true;
    }

    public static void disableTouchLogging() {
        touchLogActive = false;
    }

    public static void enableTouchLogging() {
        touchLogActive = true;
    }

    public static ArrayList<String> getTouchLog() {
        return touchLog;
    }

    public static String getLatestTouchLog() {
        return touchLog.get(touchLog.size()-1);
    }

    private static String getLogForMotionEvent(MotionEvent event) {
        // relative to view that dispatched this event
        float relX = event.getX();
        float relY = event.getY();

        // relative to the screen
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        // time this event chain was started (usually due to ACTION_DOWN)
        long downTime = event.getDownTime();
        // time of current event
        long eventTime = event.getEventTime();
        int pointerCount = event.getPointerCount();

        String eventAction;

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventAction = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_UP:
                eventAction = "ACTION_UP";
                break;
            case MotionEvent.ACTION_MOVE:
                eventAction = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_CANCEL:
                eventAction = "ACTION_CANCEL";
                break;
            case MotionEvent.ACTION_OUTSIDE:
                eventAction = "ACTION_OUTSIDE";
                break;
            case MotionEvent.ACTION_SCROLL:
                eventAction = "ACTION_SCROLL";
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                eventAction = "ACTION_POINTER_DOWN";
                eventAction += "(" + event.getActionIndex() + ")";
                break;
            case MotionEvent.ACTION_POINTER_UP:
                eventAction = "ACTION_POINTER_UP";
                eventAction += "(" + event.getActionIndex() + ")";
                break;
            default:
                eventAction = "UNDEFINED(" + event.getAction() + ")";
        }

        String curLog = eventTime + ":" + eventAction + "\n" +
                "rel:(" + relX + "," + relY + "), abs(" + rawX + "," + rawY + ")\n" +
                "dwnTm:" + downTime + ", elapsedTime:" + (eventTime - downTime) + "\n" +
                "ptrCt:" + pointerCount;

        return curLog;
    }

    private static String getLogInfoForView(View view) {
        int[] vTopLeft = new int[2];
        int[] xyWindow = new int[2];
        view.getLocationOnScreen(vTopLeft);
        view.getLocationInWindow(xyWindow);

        int[] vBotRight = {vTopLeft[0] + view.getWidth(), vTopLeft[1] + view.getHeight()};

        String idName = view.getResources().getResourceEntryName(view.getId());
        String curLog = "viewID:\"" + idName + "\"\n" +
                "time:" + android.os.SystemClock.uptimeMillis() + "\n" +
                "topLeft:(" + vTopLeft[0] + "," + vTopLeft[1] + ")\n" +
                "botRight:(" + vBotRight[0] + "," + vBotRight[1] +")";

        return curLog;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //
    //  Wrappers
    //      These methods use reflection to call the function they are wrapping.
    //      One should write a custom wrapper for each method they are wrapping.
    //      parameters look like:
    //          Object of type Class of wrapped method
    //          Object[...] whetever parameters the wrapped method expects
    //          View for logging info about what was clicked on
    //
    ///////////////////////////////////////////////////////////////////////////////////
    public static void onClickAddItem(Object obj, ShoppingProduct product, View view)
        throws Exception {

        if(touchLogActive) {
            String curLog = "onClickAddItem() item:\"" + product.getName() + "\"\n" + getLogInfoForView(view);
            touchLog.add(curLog);

            // this is extra for displaying logging
            TextView tvCurLog = (TextView)view.getRootView().findViewById(R.id.tv_recent_touch_log);
            tvCurLog.setText(curLog);
        }

        // call method we are wrapping
        Method method = ActivityMain.class.getMethod("onClickAddItem", ShoppingProduct.class);
        method.invoke(obj, product); // ActivityMain.onClickAddItem() returns void
    }

    public static void onClickRemoveItem(Object obj, ShoppingProduct product, View view)
        throws Exception {

        if(touchLogActive) {
            String curLog = "onClickRemoveItem() item:\"" + product.getName() + "\"\n" + getLogInfoForView(view);
            touchLog.add(curLog);

            // this is extra for displaying logging
            TextView tvCurLog = (TextView)view.getRootView().findViewById(R.id.tv_recent_touch_log);
            tvCurLog.setText(curLog);
        }

        // call method we are wrapping
        Method method = ActivityMain.class.getMethod("onClickRemoveItem", ShoppingProduct.class);
        method.invoke(obj, product); // ActivityMain.onClickRemoveItem() returns void
    }


    ///////////////////////////////////////////////////////////////////////////////////
    //
    //  Injected Methods
    //
    ///////////////////////////////////////////////////////////////////////////////////
    public static String dispatchTouchEvent(MotionEvent event) {
        if(!touchLogActive) {
            return null;
        }

        String curLog = getLogForMotionEvent(event);
        touchLog.add(curLog);
        return curLog;
    }

    /**
     * since the setOnClickListener() for the purchase button is using a locally defined function,
     * I couldn't think of a way to wrap it. So instead, I chose to insert this function at the
     * start of the original source code's function
      */
    public static void onClickPurchase(View view) {
        if(touchLogActive) {
            String curLog = "onClickPurchase() " + getLogInfoForView(view);
            touchLog.add(curLog);

            // this is extra for displaying logging
            TextView tvCurLog = (TextView)view.getRootView().findViewById(R.id.tv_recent_touch_log);
            tvCurLog.setText(curLog);
        }
    }

    // need to pass in the logging TextView since this is called from a fragment within the activity
    public static void onClickConfirmPurchase(View view, TextView tvRecentTouchLog) {
        if(touchLogActive) {
            String curLog = "onClickConfirmPurchase() " + getLogInfoForView(view);
            touchLog.add(curLog);

            // this is extra for displaying logging
            tvRecentTouchLog.setText(curLog);
        }
    }

    public static void onClickCartDetails(View view) {
        if(touchLogActive) {
            String curLog = "onClickCartDetails() " + getLogInfoForView(view);
            touchLog.add(curLog);

            // this is extra for displaying logging
            TextView tvCurLog = (TextView)view.getRootView().findViewById(R.id.tv_recent_touch_log);
            tvCurLog.setText(curLog);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //
    //  Android Callback Interfaces
    //
    ///////////////////////////////////////////////////////////////////////////////////
    public static class OnTouchListener implements View.OnTouchListener {

        /**
         * This is convenient, because the view that was touched is already passed in by the
         * Android System. However, calling some view.setOnTouchListener() has the potential
         * to override whatever view.onTouchListener was already set by the original source
         * code
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(touchLogActive) {
                String idName = v.getResources().getResourceEntryName(v.getId());

                String motionEventLog = getLogForMotionEvent(event);
                String curLog = "onTouch(\""+ idName + "\") " + motionEventLog;

                touchLog.add(curLog);
            }

            // don't want to consume the event, just logging details
            return false;
        }
    }
}
