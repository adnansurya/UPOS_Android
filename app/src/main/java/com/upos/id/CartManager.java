package com.upos.id;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.cardview.widget.CardView;

import com.upos.id.Models.Produk;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;

public class CartManager {

    JSONObject cart;

    public CartManager(String jsonString){
        JSONObject myCart;
        try {
            myCart = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            myCart = new JSONObject();
        }
        this.cart = myCart;
    }

    public String getTotalHarga(){
        Iterator keys = cart.keys();
        int totalCart = 0;
        while (keys.hasNext()) {
            Object key = keys.next();
            JSONObject value = null;
            String hargaJual = "",qty = "";
            int totalPerItem = 0;
            try {
                value = cart.getJSONObject((String) key);
                hargaJual = value.getString("hargaJual");
                qty = value.getString("qty");
                totalPerItem = Integer.parseInt(hargaJual) * Integer.parseInt(qty);
                totalCart += totalPerItem;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("HARGA JUAL / QTY", hargaJual + " / " +qty);
        }
        Log.e("Total", String.valueOf(totalCart));

        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        return currencyFormatter.format(totalCart);
    }

    public String getTotalItem(){
        int totalItem = 0;
        if(cart.length() != 0){
            totalItem = cart.length();
        }
        return String.valueOf(totalItem);
    }

    public void addItem (Produk produk){
        boolean existed = false;
        JSONObject item;
        try {
            item = cart.getJSONObject(produk.kode);
            existed = true;
            int qty = Integer.parseInt(item.getString("qty"));
            item.put("qty", String.valueOf(qty+1));
            cart.put(produk.kode, item);

        }catch (JSONException e){
            e.printStackTrace();
        }

        if(!existed){
            item = new JSONObject();
            try{

                item.put("nama", produk.nama);
                item.put("hargaJual", produk.hargaJual);
                item.put("qty", "1");
                cart.put(produk.kode, item);

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject getCart(){
        return cart;
    }

    public void setCount(Context context, Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.ic_cart_list);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(getTotalItem());
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

}
