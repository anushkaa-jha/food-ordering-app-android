package com.example.kurgerbingfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class Home extends AppCompatActivity {
    private static final int SPACING = 15;
    private final List<ItemChoice> choices = new LinkedList<>();
    private Button btnViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choices.add(new ItemChoice("Burger", 3.99, "https://www.nutritionix.com/food/burger", R.drawable.burger));
        choices.add(new ItemChoice("Fries", 1.99, "https://www.nutritionix.com/food/fries", R.drawable.fries));
        choices.add(new ItemChoice("Chicken Nuggies", 99.99, "https://www.nutritionix.com/food/chicken-nuggets", R.drawable.nugget));

        final ViewGroup choicesContainer = findViewById(R.id.choicesContainer);

        for (ItemChoice choice : choices)
            choicesContainer.addView(createChoiceView(choice));

        btnViewCart = findViewById(R.id.btnViewCart);
        btnViewCart.setOnClickListener(view -> startActivity(new Intent(Home.this, ViewCart.class)));
        updateCartButtonText();
    }

    private View createChoiceView(ItemChoice choice) {
        LinearLayout container = new LinearLayout(this);

        LinearLayout.LayoutParams containerDimensions = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        containerDimensions.setMargins(0, 0, 0, SPACING);
        container.setLayoutParams(containerDimensions);

        ImageView itemImg = new ImageView(this);
        itemImg.setImageResource(choice.getImageId());

        LinearLayout.LayoutParams imgDimensions = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        itemImg.setLayoutParams(imgDimensions);
        itemImg.setScaleType(ImageView.ScaleType.FIT_START);

        Button nutritionBtn = new Button(this);
        nutritionBtn.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(choice.getNutritionLink()));
            startActivity(browserIntent);
        });
        nutritionBtn.setText(R.string.nutritionBtn);

        Button addToChartBtn = new Button(this);
        addToChartBtn.setOnClickListener(view -> createItemQuantityDialog(choice).show());
        addToChartBtn.setText(R.string.addToCartBtn);

        TextView priceTxt = new TextView(this);
        LinearLayout.LayoutParams priceTxtDimensions = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        priceTxtDimensions.setMargins(0, 0, SPACING * 2, 0);
        priceTxt.setLayoutParams(priceTxtDimensions);
        priceTxt.setText("$" + choice.getPrice());

        container.addView(itemImg);
        container.addView(priceTxt);
        container.addView(nutritionBtn);
        container.addView(addToChartBtn);

        return container;
    }

    private AlertDialog.Builder createItemQuantityDialog(ItemChoice choice) {

        EditText quantityInput = new EditText(this);
        quantityInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        return new AlertDialog.Builder(Home.this).
                setTitle(R.string.quantityDialogTitle)
                .setPositiveButton(R.string.addToCartBtn, (dialog, which) -> {
                    int quantity = Integer.parseInt(quantityInput.getText().toString());
                    Cart.getInstance().addItem(choice.createItem(quantity));
                    updateCartButtonText();

                    dialog.dismiss();

                    Toast.makeText(this,
                            String.format("Added %s %s to your cart", quantity, choice.getName()),
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.cancelDialogBtn, (dialog, which) -> dialog.dismiss())
                .setView(quantityInput);
    }

    private void updateCartButtonText() {
        String btnViewCartTxtValue = String.format("%s (%s)", "View Cart", Cart.getInstance().size());
        btnViewCart.setText(btnViewCartTxtValue);
    }
}