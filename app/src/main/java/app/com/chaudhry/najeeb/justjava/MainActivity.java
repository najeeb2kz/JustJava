package app.com.chaudhry.najeeb.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int pricePerCup = 5; //5 dolla
    String customerName;
    boolean hasWhipCream;
    boolean hasChoclate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This method is called when order button is clicked
    public void submitOrder(View view) {
        displayQuantity(quantity);
        int priceOfOrder = quantity * pricePerCup;
        if (hasWhipCream) {
            priceOfOrder += 1;  //$1 per whip cream
        }
        if (hasChoclate) {
            priceOfOrder += 1;  //$1 per choclate
        }
        String finalOrder = createOrderSummary(priceOfOrder);
        displayMessage(finalOrder);
    }

    //This method is called when increment button is clicked
    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    //This method is called when decrement button is clicked
    public void decrement(View view) {
        if (quantity > 0) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    //This method displays the given quantity value on the screen
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String priceMessage) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(priceMessage);
    }

    private String createOrderSummary(int priceOfOrder) {

        EditText editText = (EditText) findViewById(R.id.editTextName);
        customerName = editText.getText().toString();

        String priceMessage = "Name: " + customerName;
        priceMessage += "\nAdd Whip cream? " + hasWhipCream;
        priceMessage += "\nAdd Choclate? " + hasChoclate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + priceOfOrder;
        //priceMessage += "\nThank you!";
        //To get a string from strings.xml user getstring method
        priceMessage += "\n" + getString(R.string.thank_you);  //Done this way for spanish

        //call composeEmail method
        composeEmail(priceMessage);

        return priceMessage;
    }

    public void whipCreamCheckBox(View view) {
        CheckBox myWhipCreamCheckBox = (CheckBox) findViewById(R.id.whipCream);
        hasWhipCream = myWhipCreamCheckBox.isChecked();
    }

    public void choclateCheckBox(View view) {
        CheckBox myChoclateCheckBox = (CheckBox) findViewById(R.id.choclate);
        hasChoclate = myChoclateCheckBox.isChecked();

    }

    //Populate email with order summary
    public void composeEmail(String priceMessage) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));  //only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }






    //We don't need this method in this app
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //we don't need this method in this app
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
