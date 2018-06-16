package com.example.vihu.mobileselection.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vihu.mobileselection.R;

public class OrderConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        TextView tvwConfirmMsg = (TextView)findViewById(R.id.tvwConfirmMsg);
        tvwConfirmMsg.setText(getIntent().getExtras().getString("orderStatus"));
    }

    public void btnHome_OnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), PhoneSearchActivity.class);
        startActivity(intent);
    }
}
