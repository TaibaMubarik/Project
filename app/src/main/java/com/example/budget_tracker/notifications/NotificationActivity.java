package com.example.budget_tracker.notifications;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.annotations.Nullable;
import com.example.budget_tracker.R;

public class NotificationActivity extends AppCompatActivity {
    TextView tvMessage;
    TextView tvFrom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        tvFrom = (TextView)findViewById(R.id.tv_notification_from);
        tvMessage = (TextView)findViewById(R.id.tv_notification_message);

        String message = getIntent().getStringExtra("message");
        String from  = getIntent().getStringExtra("sentBy");

        tvFrom.setText(from);
        tvMessage.setText(message);
    }
}
