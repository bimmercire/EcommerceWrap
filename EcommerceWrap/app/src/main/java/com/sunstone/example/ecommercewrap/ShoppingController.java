package com.sunstone.example.ecommercewrap;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by sunstone2 on 8/18/2015.
 */
public class ShoppingController extends Application {
    private ArrayList<ShoppingProduct> mProductList = new ArrayList<ShoppingProduct>();
    private ShoppingCart mCart = new ShoppingCart();

    public ShoppingProduct getProduct(int idx) {
        if(idx < 0 || idx >= mProductList.size()) {
            throw new IllegalArgumentException(
                    "given index of " + idx + " is out of shopping list bounds " + mProductList.size());
        }
        return mProductList.get(idx);
    }

    public void addProduct(ShoppingProduct product) {
        mProductList.add(product);

    }

    public int getProductListSize() {
        return mProductList.size();
    }

    public ArrayList<ShoppingProduct> getProductList() {
        return mProductList;
    }

    public ShoppingCart getCart() {
        return mCart;
    }

    public void clearAll() {
        mProductList.clear();
        mCart.clear();
    }
}
