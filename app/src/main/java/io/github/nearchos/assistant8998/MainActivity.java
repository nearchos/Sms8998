package io.github.nearchos.assistant8998;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static io.github.nearchos.assistant8998.LanguageActivity.KEY_SELECTED_GREEK;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SETUP_COMPLETED = "KEY_SETUP_COMPLETED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean selectedGreek = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SELECTED_GREEK, false);

        setTitle(selectedGreek ? R.string.Choose_el : R.string.Choose);

        final OptionsAdapter optionsAdapter = new OptionsAdapter(this, selectedGreek);

        final TextView textViewMoreInfo = findViewById(R.id.textViewMoreInfo);
        textViewMoreInfo.setText(selectedGreek ? R.string.More_info_el : R.string.More_info);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(optionsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();

        final boolean setupCompleted = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SETUP_COMPLETED, false);
        if(!setupCompleted) startSetup();
    }

    private void startSetup() {
        startActivity(new Intent(this, LanguageActivity.class));
        finish();
    }

    public void visitPio(View view) {
        final boolean selectedGreek = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SELECTED_GREEK, false);
        final Uri pioWebpage = Uri.parse(selectedGreek ? "https://www.pio.gov.cy/coronavirus" : "https://www.pio.gov.cy/coronavirus/eng");
        final Intent intent = new Intent(Intent.ACTION_VIEW, pioWebpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final boolean selectedGreek = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SELECTED_GREEK, false);
        menu.findItem(R.id.menuSettings).setTitle(selectedGreek ? R.string.Settings_el : R.string.Settings);
        menu.findItem(R.id.menuAbout).setTitle(selectedGreek ? R.string.About_el : R.string.About);
        menu.findItem(R.id.menuLanguage).setTitle(selectedGreek ? R.string.Language_el : R.string.Language);
        menu.findItem(R.id.menuTermsOfUse).setTitle(selectedGreek ? R.string.View_terms_of_use_el : R.string.View_terms_of_use);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.menuSettings) {
            startActivity(new Intent(this, IdNumberActivity.class));
            return true;
        } else if(id == R.id.menuAbout) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage(getString(R.string.Developed_by, BuildConfig.VERSION_NAME))
                    .setPositiveButton(R.string.OK, (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
            return true;
        } else if(id == R.id.menuLanguage) {
            startActivity(new Intent(this, LanguageActivity.class));
            return true;
        } else if(id == R.id.menuTermsOfUse) {
            startActivity(new Intent(this, TermsOfUseActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}