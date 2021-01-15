package io.github.nearchos.assistant8998;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static io.github.nearchos.assistant8998.LanguageActivity.KEY_SELECTED_GREEK;

public class IdNumberActivity extends AppCompatActivity {

    public static final String KEY_ID = "KEY_ID";

    private EditText editTextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_number);

        editTextId = findViewById(R.id.editTextId);
        editTextId.setFocusableInTouchMode(true);
        final boolean selectedGreek = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SELECTED_GREEK, false);

        final TextView textViewIdHeader = findViewById(R.id.textViewIdHeader);
        final TextView textViewIdDetails = findViewById(R.id.textViewIdDetails);
        final Button buttonNext = findViewById(R.id.buttonNext);
        if(selectedGreek) {
            textViewIdHeader.setText(R.string.Enter_your_identification_el);
            textViewIdDetails.setText(R.string.Identification_details_el);
            buttonNext.setText(R.string.Next_el);
        } else {
            textViewIdHeader.setText(R.string.Enter_your_identification);
            textViewIdDetails.setText(R.string.Identification_details);
            buttonNext.setText(R.string.Next);
        }

        buttonNext.setOnClickListener(v -> save(selectedGreek));
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String defaultId = PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_ID, "");
        if(!defaultId.isEmpty()) editTextId.setText(defaultId);
        editTextId.selectAll();
        editTextId.requestFocus();
    }

    private void save(final boolean selectedGreek) {
        final String id = editTextId.getText().toString();
        if(id.length() < 6) {
            Toast.makeText(this, selectedGreek ? R.string.Id_must_be_6_letters_or_more_el : R.string.Id_must_be_6_letters_or_more, Toast.LENGTH_SHORT).show();
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(KEY_ID, id).apply();
            startActivity(new Intent(this, PostalCodeActivity.class));
        }
    }
}