package app.com.example.android.coffeeorder;

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
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void decrement(View view) {
        if (quantity == 0) {
            Toast.makeText(this, "0 cup of coffees!!", Toast.LENGTH_SHORT).show();
            return;
        } else if (quantity > 0) {
            quantity = quantity - 1;
            display(quantity);
        }
    }

    public void increment(View view) {
        if (quantity >=100) {
            Toast.makeText(this, "100 cup of coffees!!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity + 1;
            display(quantity);
        }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(" " + number);
    }

    public  void submitOrder(View view)
    {
        CheckBox whippedCreamBox=(CheckBox) findViewById(R.id.whipped_checkbox);
        CheckBox chocolateBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean isWhippedCream=whippedCreamBox.isChecked();
        boolean isChocolate=chocolateBox.isChecked();
        EditText editText=(EditText) findViewById(R.id.name_edit_text);
        String name= editText.getText().toString();
        int price=calculatePrice(isWhippedCream, isChocolate);
        String message=createOrderSummary(name, isWhippedCream, isChocolate, price);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    public int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int basePrice=5;
        if(addWhippedCream && addChocolate )
        {
            basePrice=(basePrice+3)*quantity;
        }else if(addWhippedCream)
        {
            basePrice=(basePrice+2)*quantity;
        }else if(addChocolate)
        {
            basePrice=(basePrice+1)*quantity;
        }
        return basePrice;
    }
    String createOrderSummary(String name,boolean addWhippedCream,boolean addChocolate,int price)
    {
        String priceMessage="Name:"+name;
        priceMessage+="\nWhippedcCream?"+addWhippedCream;
        priceMessage+="\nChocolate?"+addChocolate;
        priceMessage+="\nTotal:"+price;
        priceMessage+="\nThank You!!";
        return  priceMessage;
    }

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
