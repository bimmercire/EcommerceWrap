package com.sunstone.example.ecommercewrap;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityMain extends ActionBarActivity {

    private static final String TAG = ActivityMain.class.getSimpleName();

    final String[] itemNames = new String[] {
        "corn", "apple", "lettuce", "banana", "beet", "potato", "carrot", "celery", "sugar", "eggs"
    };

    TextView tvItemCount;

    // Behind the scenes stuff
    TextView tvRecentTouchLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvItemCount = (TextView)findViewById(R.id.tv_item_count);
        tvItemCount.setText("0");

        tvRecentTouchLog = (TextView)findViewById(R.id.tv_recent_touch_log);

        Button btnCartDetails = (Button)findViewById(R.id.btn_cart_details);
        btnCartDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * SunStone_inject
                 */
                SnSt.onClickCartDetails(v);

                showFragmentCartDetails(false);
            }
        });

        Button btnPurchase = (Button)findViewById(R.id.btn_purchase);
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * SunStone_inject
                 */
                SnSt.onClickPurchase(v);

                showFragmentCartDetails(true);
            }
        });

        Button btnExit = (Button)findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnResetTouchLog = (Button)findViewById(R.id.btn_clear_touch_log);
        btnResetTouchLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnSt.resetLogging();
                tvRecentTouchLog.setText("...");
            }
        });

        loadItems();

    }

    private void showFragmentCartDetails(boolean finalSummary) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("tag_fragCartdetails");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = FragmentCartDetails.newInstance(finalSummary);
        newFragment.show(ft, "tag_fragCartdetails");
    }

    private void showFragmentTouchLog() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("tag_fragTouchLog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = FragmentTouchLog.newInstance(SnSt.getTouchLog());
        newFragment.show(ft, "tag_fragTouchLog");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShoppingController controller = (ShoppingController)getApplicationContext();
        controller.clearAll();

        SnSt.resetLogging();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        // so we don't add to the touch log when reading it
        SnSt.disableTouchLogging();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        SnSt.enableTouchLogging();
        super.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_show_touches) {
            showFragmentTouchLog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * http://developer.android.com/reference/android/app/Activity.html#dispatchTouchEvent%28android.view.MotionEvent%29
     *
     * "Called to process touch screen events. You can override this to intercept all
     * touch screen events before they are dispatched to the window. Be sure to call
     * this implementation for touch screen events that should be handled normally."
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        /**
         * SunStone_inject
         */
        String curLog = SnSt.dispatchTouchEvent(event);

        // this is extra, just for logging
        tvRecentTouchLog.setText(curLog);

        return super.dispatchTouchEvent(event);
    }

    public void onClickAddItem(ShoppingProduct product) {
        ShoppingCart cart = ((ShoppingController)getApplicationContext()).getCart();
        cart.addProduct(product);
        tvItemCount.setText(Integer.toString(cart.getSize(), 10));
    }

    public void onClickRemoveItem(ShoppingProduct product) {
        ShoppingCart cart = ((ShoppingController)getApplicationContext()).getCart();
        cart.removeProduct(product);
        tvItemCount.setText(Integer.toString(cart.getSize(), 10));
    }

    public void onClickConfirmPurchase(View v) {
        /**
         * SunStone_inject
         */
        SnSt.onClickConfirmPurchase(v, tvRecentTouchLog);

        findViewById(R.id.lv_items).setVisibility(View.GONE);
        findViewById(R.id.ll_end_msg).setVisibility(View.VISIBLE);

        // hide cartDetails fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("tag_fragCartdetails");
        if (prev != null) {
            ft.remove(prev);
            ((FragmentCartDetails)prev).dismiss();
        }
        ft.addToBackStack(null);
        // empty cart
        ShoppingController controller = (ShoppingController)getApplicationContext();
        controller.getCart().clear();
    }

    /**
     * stub method. In reality, this would probably spawn a thread (AsyncTask) to do some HTTP(S)
     * request for a list of items from the server
     */
    public void loadItems(){
        final ShoppingController controller = (ShoppingController)getApplicationContext();

        for(int i = 0; i < itemNames.length; ++i) {
            controller.addProduct(new ShoppingProduct(
                itemNames[i],
                "description of " + itemNames[i],
                "NULL"));
        }

        ShoppingProduct[] productArray = new ShoppingProduct[controller.getProductListSize()];
        productArray =  controller.getProductList().toArray(productArray);

        final ShoppingProductArrayAdapter  adapter =
            new ShoppingProductArrayAdapter(this, productArray);

        final ListView listView = (ListView)findViewById(R.id.lv_items);
        listView.setAdapter(adapter);
    }


}
