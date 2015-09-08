package com.sunstone.example.ecommercewrap;

import java.util.ArrayList;

/**
 * Created by sunstone2 on 8/18/2015.
 */
public class ShoppingCart {
    private ArrayList<ShoppingProduct> mProducts;

    public ShoppingCart() {
        mProducts = new ArrayList<ShoppingProduct>();
    }

    public int getSize() {
        return mProducts.size();
    }

    public ShoppingProduct getProduct(int idx) {
        if(idx < 0 || idx >= mProducts.size()) {
            throw new IllegalArgumentException(
                "given index of " + idx + " is out of cart bounds " + mProducts.size());
        }
        return mProducts.get(idx);
    }

    public void addProduct(ShoppingProduct product) {
        mProducts.add(product);
    }

    public boolean removeProduct(ShoppingProduct product) {
        for(ShoppingProduct p: mProducts) {
            if(p.getName().equals(product.getName())) {
                mProducts.remove(p);
                return true;
            }
        }

        return false;
    }

    public void clear() {
        mProducts.clear();
    }
}
