/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    String priceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //for whipped cream
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        //for chocolate topping
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        //for price
        int price = calculatePrice(hasWhippedCream , hasChocolate);

        //for customer name
        EditText customerName = (EditText) findViewById(R.id.customer_name);
        String name_of_customer = customerName.getText().toString();

        //for customer mobile number
        EditText mobileNumber = (EditText) findViewById(R.id.mobile_number);
        String mobile_of_customer = mobileNumber.getText().toString();

        //to display the order summary
        String priceMessage = createOrderSummary(name_of_customer, price, hasWhippedCream, hasChocolate);

        if(name_of_customer.length() == 0 || mobile_of_customer.length() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_SHORT).show();
        }
        else if (mobile_of_customer.length() != 12 )
        {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }

        else
        {
            openWhatsApp(priceMessage , mobile_of_customer);
        }
    }

    public void openWhatsApp(String priceMessage , String mobile_of_customer)
    {
        PackageManager pm=getPackageManager();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mobile_of_customer + "&text="+priceMessage));
            startActivity(intent);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"it may be you don't have whatsapp",Toast.LENGTH_LONG).show();

        }
    }


    public int calculatePrice(boolean addWhippedCream , boolean addChocolate)
    {
        int basePrice = 5;
        if(addWhippedCream)
        {
            basePrice += 1;
        }
        if(addChocolate)
        {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    public void increment(View view) {
        if(quantity<=99)
        {
            quantity += 1;
            display(quantity);
        }
    }

    public void decrement(View view) {
        if(quantity>=2) {
            quantity -= 1;
            display(quantity);
        }
    }

    /**
     *This method is for displaying customer order summary
     */
    private String createOrderSummary(String name , int price , boolean addWhippedCream , boolean addChocolate){
        String whippedCream = "No";
        String chocolate = "No";

        if (addWhippedCream)
        {
            whippedCream = "Yes";
        }
        if (addChocolate)
        {
            chocolate = "Yes";
        }

        String priceMessage="COFFEE TIME ☕\n\nName: " + name;
        priceMessage += "\nAdd Whipped Cream: " + whippedCream;
        priceMessage += "\nAdd Chocolate: " + chocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price ;
        priceMessage += "\nThank You !!! \n\nHAVE A GOOD DAY ☺";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}