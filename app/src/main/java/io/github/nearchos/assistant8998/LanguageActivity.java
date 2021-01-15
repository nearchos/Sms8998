package io.github.nearchos.assistant8998;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static io.github.nearchos.assistant8998.IdNumberActivity.KEY_ID;
import static io.github.nearchos.assistant8998.PostalCodeActivity.KEY_POSTAL_CODE;
import static io.github.nearchos.assistant8998.TermsOfUseActivity.KEY_AGREED_TO_TERMS_OF_USE;

public class LanguageActivity extends AppCompatActivity {

    public static final String KEY_SELECTED_GREEK = "KEY_SELECTED_GREEK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
    }

    public void selectGreek(final View view) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(KEY_SELECTED_GREEK, true).apply();
        startIdNumberActivity();
    }

    public void selectEnglish(final View view) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(KEY_SELECTED_GREEK, false).apply();
        startIdNumberActivity();
    }

    private void startIdNumberActivity() {
        final boolean agreedToTermsOfUse = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_AGREED_TO_TERMS_OF_USE, false);
        final String id = PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_ID, "");
        final int postalCode = PreferenceManager.getDefaultSharedPreferences(this).getInt(KEY_POSTAL_CODE, 0);
        if (agreedToTermsOfUse) {
            if(!id.isEmpty()) {
                if(postalCode != 0) {
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    startActivity(new Intent(this, PostalCodeActivity.class));
                }
            } else {
                startActivity(new Intent(this, IdNumberActivity.class));
            }
        } else {
            startActivity(new Intent(this, TermsOfUseActivity.class));
        }
    }
}