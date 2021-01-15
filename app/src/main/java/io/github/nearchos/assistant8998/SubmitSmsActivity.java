package io.github.nearchos.assistant8998;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static io.github.nearchos.assistant8998.IdNumberActivity.KEY_ID;
import static io.github.nearchos.assistant8998.LanguageActivity.KEY_SELECTED_GREEK;
import static io.github.nearchos.assistant8998.PostalCodeActivity.KEY_POSTAL_CODE;

public class SubmitSmsActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_OPTION = "EXTRA_KEY_OPTION";

    private TextView textViewOption;
    private TextView textViewOptionDescription;
    private TextView textViewId;
    private TextView textViewPostalCode;

    private Button buttonSend;
    private ImageView imageViewOK;
    private TextView textViewPostSmsMessage;
    private Button buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_sms);

        textViewOption = findViewById(R.id.textViewOption);
        textViewOptionDescription = findViewById(R.id.textViewOptionDescription);
        textViewId = findViewById(R.id.textViewId);
        textViewPostalCode = findViewById(R.id.textViewPostalCode);

        buttonSend = findViewById(R.id.buttonSend);

        imageViewOK = findViewById(R.id.imageViewOK);
        textViewPostSmsMessage = findViewById(R.id.textViewPostSmsMessage);
        buttonOK = findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Intent intent = getIntent();
        final int option = intent.getIntExtra(EXTRA_KEY_OPTION, 0);
        final String id = PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_ID, "");
        final int postalCode = PreferenceManager.getDefaultSharedPreferences(this).getInt(KEY_POSTAL_CODE, 0);

        checkSmsPermissions(); // first make sure we have permission to send SMS

        final boolean selectedGreek = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SELECTED_GREEK, false);
        final String details = getResources().getStringArray(selectedGreek ? R.array.optionHeaders_el : R.array.optionHeaders)[option - 1];

        textViewOption.setText(String.format(Locale.ENGLISH, "%d", option));
        textViewOptionDescription.setText(details);
        textViewId.setText(id);
        textViewPostalCode.setText(String.format(Locale.ENGLISH, "%d", postalCode));

        buttonSend.setText(selectedGreek ? R.string.Send_el : R.string.Send);

        buttonSend.setEnabled(true);
        buttonSend.setOnClickListener(v -> send(option, id, postalCode));

        imageViewOK.setVisibility(View.GONE);
        textViewPostSmsMessage.setText(selectedGreek ? R.string.The_SMS_has_been_sent_el : R.string.The_SMS_has_been_sent);
        textViewPostSmsMessage.setVisibility(View.GONE);
        buttonOK.setVisibility(View.GONE);
    }

    private static final int PERMISSIONS_REQUEST_RECEIVE_SMS = 42;

    private void checkSmsPermissions() {
        if(checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_RECEIVE_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSIONS_REQUEST_RECEIVE_SMS && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            final boolean selectedGreek = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_SELECTED_GREEK, false);
            Toast.makeText(this, selectedGreek ? R.string.Must_allow_SMS_to_use_this_app_el : R.string.Must_allow_SMS_to_use_this_app, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void send(final int option, final String id, final int postalCode) {
        final String message = String.format(Locale.ENGLISH, "%d %s %d", option, id, postalCode);
        Log.d("SMS8998", "Send SMS to 8998: " + message);
        final android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        smsManager.sendTextMessage(getString(R.string.targetSmsNumber), "ME", message, null, null);

        buttonSend.setEnabled(false);

        imageViewOK.setVisibility(View.VISIBLE);
        textViewPostSmsMessage.setVisibility(View.VISIBLE);
        buttonOK.setVisibility(View.VISIBLE);
    }
}