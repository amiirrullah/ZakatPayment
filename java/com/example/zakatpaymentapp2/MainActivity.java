package com.example.zakatpaymentapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI components
        EditText inputWeight = findViewById(R.id.inputWeight);
        EditText inputGoldValue = findViewById(R.id.inputGoldValue);
        RadioGroup goldTypeGroup = findViewById(R.id.radioGroupGoldType);
        Button calculateButton = findViewById(R.id.btnCalculate);
        TextView outputResults = findViewById(R.id.outputResults);

        // Add Zakat calculation logic
        calculateButton.setOnClickListener(v -> {
            try {
                double weight = Double.parseDouble(inputWeight.getText().toString());
                double valuePerGram = Double.parseDouble(inputGoldValue.getText().toString());
                int selectedTypeId = goldTypeGroup.getCheckedRadioButtonId();
                double uruf = selectedTypeId == R.id.radioKeep ? 85 : 200;

                double totalValue = weight * valuePerGram;
                double zakatPayableValue = Math.max(0, (weight - uruf) * valuePerGram);
                double totalZakat = zakatPayableValue * 0.025;

                String resultText = "Total Value of Gold: RM" + totalValue +
                        "\nZakat Payable: RM" + zakatPayableValue +
                        "\nTotal Zakat: RM" + totalZakat;
                outputResults.setText(resultText);
            } catch (NumberFormatException e) {
                outputResults.setText("Please enter valid inputs!");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the ActionBar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            // Navigate to AboutActivity2
            Intent intent = new Intent(MainActivity.this, AboutActivity2.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_zakat_instructions) {
            // Show Zakat payment instructions
            showZakatInstructions();
            return true;
        } else if (id == R.id.menu_share) {
            // Share app information
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareText = "Check out this Zakat Payment Calculator App!";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showZakatInstructions() {
        new AlertDialog.Builder(this)
                .setTitle("Zakat Payment Instructions")
                .setMessage("1. Determine your total gold weight.\n" +
                        "2. Identify the current gold value per gram.\n" +
                        "3. Select the type of gold (Kept or Worn).\n" +
                        "4. Use the Zakat calculator to determine the payable amount.\n" +
                        "5. Pay the calculated amount to your preferred Zakat collection agency.")
                .setPositiveButton("OK", null)
                .show();
    }
}
