package io.github.nearchos.assistant8998;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static io.github.nearchos.assistant8998.LanguageActivity.KEY_SELECTED_GREEK;

public class TermsOfUseActivity extends AppCompatActivity {

    public static final String KEY_AGREED_TO_TERMS_OF_USE = "KEY_AGREED_TO_TERMS_OF_USE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        final boolean selectedGreek = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SELECTED_GREEK, false);
        setTitle(selectedGreek ? R.string.View_terms_of_use_el : R.string.View_terms_of_use);

        final Button buttonAgree = findViewById(R.id.buttonAgree);
        buttonAgree.setText(selectedGreek ? R.string.Agree_el : R.string.Agree);
        final Button buttonDisagree = findViewById(R.id.buttonDisagree);
        buttonDisagree.setText(selectedGreek ? R.string.Disagree_el : R.string.Disagree);

        final boolean agreedToTermsOfUse = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_AGREED_TO_TERMS_OF_USE, false);
        if(agreedToTermsOfUse) {
            buttonDisagree.setVisibility(View.GONE);
        }

        final TextView textViewTermsOfUse = findViewById(R.id.textViewTermsOfUse);
        textViewTermsOfUse.setText(selectedGreek ? R.string.Terms_of_use_el : R.string.Terms_of_use);
    }

    public void agree(View view) {
        final boolean agreedToTermsOfUse = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_AGREED_TO_TERMS_OF_USE, false);
        // if the user already agreed then skip to the main activity
        if(agreedToTermsOfUse) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(KEY_AGREED_TO_TERMS_OF_USE, true).apply();
            startActivity(new Intent(this, IdNumberActivity.class));
        }
    }

    public void disagree(View view) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(KEY_AGREED_TO_TERMS_OF_USE, false).apply();
        finish();
    }
}