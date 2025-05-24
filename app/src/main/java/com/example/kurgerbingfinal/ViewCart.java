package com.example.kurgerbingfinal;

/*
 * The Splash Screen, ViewCart, order, and ItemChoice classes and corresponding
 * layouts and such were made by Will Flowers.
 * Will Flowers implemented: DatePicker, GridView, animation, sound, and radio buttons
 *
 * Tyler Grellner thankfully volunteered to do all documentation
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ViewCart extends AppCompatActivity {

    private GridView gridView;
    private TextView foodLine;
    private TextView taxLine;
    private TextView priceLine;

    private RadioGroup radGroup;

    private double foodPrice = 0.0;
    private double taxPrice = 0.0;
    private double totPrice = 0.0;
    private int totCnt = 0;
    private double shipPrice = 3.25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        gridView = findViewById(R.id.cartContent);

        foodLine = findViewById(R.id.foodCost);
        taxLine = findViewById(R.id.taxCost);
        priceLine = findViewById(R.id.totCost);

        radGroup = findViewById(R.id.deliveryFee);

        Cart cart = Cart.getInstance(); // Cart object is a convenient way of passing information
                                        // from MainActivity on through
        List<String> cartItems = new ArrayList<>(); // List object makes it easier to fill in the
                                                    // GridView

        Iterator<Item> iterator = cart.iterator();

        while(iterator.hasNext()) {
            Item item = iterator.next();
            // Concatenates property values for GridView use
            String cartLine = String.format(Locale.US, "%s   %d   $%.2f", item.getName(), item.getItemCnt(), item.getTotalPrice());
            totCnt += item.getItemCnt(); // Counts total count of items
            foodPrice += item.getTotalPrice(); // Calcs food cost
            cartItems.add(cartLine); // adds item to list
        }

        // This is all methods and functions provided by Java: nifty
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, cartItems);
        gridView.setAdapter(adapter);

        //Calculate Costs
        taxPrice = foodPrice * 0.01225;
        updatePrice();

        // Changes value of shipping accordingly
        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rad_Reg) {
                    shipPrice = 3.25;
                } else if (checkedId == R.id.rad_Xpd) {
                    shipPrice = 9.50;
                }

                updatePrice();
            }
        });
    }

    public void kurgerBongd(View view) {
        finalizeOrder();
    }

    private void finalizeOrder() {
        // This uses an AlertDialog, similar to last assignment given
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewCart.this);
        builder.setTitle("Confirm Order");

        final TextView order = new TextView(this);
        order.setText(String.format(Locale.US,"Order of %d items for $%.2f", totCnt, totPrice));
        builder.setView(order);
        // Set up buttons for the Dialog box
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ViewCart.this, order.class));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ViewCart.this, MainActivity.class));
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    // Just a method to update the UI's text values
    private void updatePrice() {
        totPrice = taxPrice + foodPrice + shipPrice;
        foodLine.setText(String.format(Locale.US,"Food Cost: $%.2f", foodPrice));
        taxLine.setText(String.format(Locale.US,"Tax: $%.2f", taxPrice));
        priceLine.setText(String.format(Locale.US,"Total Cost: $%.2f", totPrice));
    }
}
