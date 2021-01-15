package io.github.nearchos.assistant8998;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static io.github.nearchos.assistant8998.LanguageActivity.KEY_SELECTED_GREEK;
import static io.github.nearchos.assistant8998.MainActivity.KEY_SETUP_COMPLETED;

public class PostalCodeActivity extends AppCompatActivity {

    public static final String KEY_POSTAL_CODE = "KEY_POSTAL_CODE";

    private EditText editTextPostalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postal_code);

        editTextPostalCode = findViewById(R.id.editTextPostalCode);
        editTextPostalCode.setFocusableInTouchMode(true);
        final boolean selectedGreek = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SELECTED_GREEK, false);

        final TextView textViewPostalCodeHeader = findViewById(R.id.textViewPostalCodeHeader);
        final TextView textViewPostalCodeDetails = findViewById(R.id.textViewPostalCodeDetails);
        final Button buttonNext = findViewById(R.id.buttonNext);
        if(selectedGreek) {
            textViewPostalCodeHeader.setText(R.string.Enter_your_postal_code_el);
            textViewPostalCodeDetails.setText(R.string.Postal_code_details_el);
            buttonNext.setText(R.string.Next_el);
        } else {
            textViewPostalCodeHeader.setText(R.string.Enter_your_postal_code);
            textViewPostalCodeDetails.setText(R.string.Postal_code_details);
            buttonNext.setText(R.string.Next);
        }

        buttonNext.setOnClickListener(v -> save(selectedGreek));
    }

    @Override
    protected void onResume() {
        super.onResume();
        final int defaultPostalCode = PreferenceManager.getDefaultSharedPreferences(this).getInt(KEY_POSTAL_CODE, 0);
        if(defaultPostalCode != 0) editTextPostalCode.setText(String.format(Locale.ENGLISH, "%d", defaultPostalCode));
        editTextPostalCode.selectAll();
        editTextPostalCode.requestFocus();
    }

    private void save(final boolean selectedGreek) {
        final String postalCodeS = editTextPostalCode.getText().toString();
        if(postalCodeS.length() != 4) {
            Toast.makeText(this, selectedGreek ? R.string.Postal_code_must_be_4_digits_el : R.string.Postal_code_must_be_4_digits, Toast.LENGTH_SHORT).show();
        } else {
            final int postalCode = Integer.parseInt(postalCodeS);
            PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(KEY_POSTAL_CODE, postalCode).apply();
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(KEY_SETUP_COMPLETED, true).apply();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}