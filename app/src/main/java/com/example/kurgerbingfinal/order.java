package com.example.kurgerbingfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class order extends AppCompatActivity {

    private TextView ordSum;
    private int totCnt = 0;
    private DatePicker datePicker;

    MediaPlayer finishOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ordSum = findViewById(R.id.ordSum); // Allows changing text at top of Activity

        datePicker = findViewById(R.id.ordDate); // Allows the ability to animate this element
        // This also fulfills our DatePicker portion of the project

        finishOrder = new MediaPlayer();
        finishOrder = MediaPlayer.create(this, R.raw.yodadeath);

        Cart cart = Cart.getInstance(); // Cart object is a convenient way of passing information
                                        // from MainActivity on through

        Iterator<Item> iterator = cart.iterator();

        while(iterator.hasNext()) { // This goes through all items in the cart to get the overall quanitity
            Item item = iterator.next();
            totCnt += item.getItemCnt();
        }

        ordSum.setText(String.format(Locale.US,"What date would you like your %d items to be delivered?", totCnt));
    }

    /*
     * Fulfills the animation requirement
     */
    public void subBtn(View view) {
        Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        datePicker.startAnimation(shakeAnimation);

        playFinishOrderSound();
    }

    public void newOrd(View view) { // Completely start app over
        finishAffinity();

        // Start a new instance of the main Activity
        Intent intent = new Intent(order.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /*
     * This method handles the sound requirement for the project
     */
    private void playFinishOrderSound() {
        if (finishOrder == null) {
            finishOrder = MediaPlayer.create(this, R.raw.yodadeath);
        }

        if (finishOrder != null) {
            finishOrder.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (finishOrder != null) {
            finishOrder.release();
            finishOrder = null;
        }
    }
}