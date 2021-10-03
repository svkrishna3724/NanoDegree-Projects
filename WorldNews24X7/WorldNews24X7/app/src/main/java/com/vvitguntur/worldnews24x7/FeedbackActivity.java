package com.vvitguntur.worldnews24x7;

import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vvitguntur.worldnews24x7.utilities.NetworkUtils;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feedbackText;
    private DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedbackText = findViewById(R.id.editText12321);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void sendFeedback(View view)
    {
        if(NetworkUtils.isInternetAvailable(this))
        {
            String text = feedbackText.getText().toString();
            String android_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            FeedbackFormat ff = new FeedbackFormat();
            ff.setFeedback(text);
            firebaseDatabase.child(""+android_id).setValue(ff);
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Snackbar.make(view,"NO INTERNET! NOT POSSIBLE TO SUBMIT FEEDBACK!",Snackbar.LENGTH_LONG).show();
        }

    }
}
