package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Gets the users name from text input
        EditText editTextField = findViewById(R.id.name_input);
        String userName = editTextField.getText().toString();

        //Checks whether whipped cream checkbox is selected
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //checks whether chocolate checkbox is selected
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Gets total price and display order summary
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, userName, hasWhippedCream, hasChocolate);

        displayMessage(priceMessage);
    }

    /**
     * This method increases the quantity of coffee
     */
    public void increment(View view) {
        //prevents user from ordering over 100 coffees
        if (quantity == 100) {
            //Creates error message as a toast
            Toast.makeText(MainActivity.this,
                    "You cannot have more than 100 cups of coffee",
                    Toast.LENGTH_SHORT).show();
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method decreases the quantity of coffee
     */
    public void decrement(View view) {
        //Prevents user from ordering negative coffees
        if (quantity == 1) {
            //shows error message as a toast
            Toast.makeText(this,
                    "You cannot have less than 1 cup of coffee",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * creates a summary of the users order
     *
     * @param price           of order
     * @param name            of user
     * @param addChocolate    check whether the user wants chocolate
     * @param addWhippedCream check whether the user wants whipped cream
     * @return text summary
     */
    private String createOrderSummary(int price, String name, boolean addWhippedCream, boolean addChocolate) {
        String orderSummary = getString(R.string.order_summary_name, name);
        orderSummary += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        orderSummary += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        orderSummary += "\n" + getString(R.string.order_summary_quantity, quantity);
        orderSummary += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given text on the screen.
     *
     * @param addWhippedCream check whether the user want whipped cream
     * @param addChocolate    check whether the user wants chocolate
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //Add $1 if user wants whipped cream
        if (addWhippedCream) {
            basePrice += 1;
        }

        //Add $2 if user wants chocolate
        if (addChocolate) {
            basePrice += 2;
        }

        //Calc total price by multiplying by quantity of coffee
        return quantity * basePrice;
    }
}
