package com.example.vihu.mobileselection.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.vihu.mobileselection.Models.RoleModel;
import com.example.vihu.mobileselection.Other.CommonLogic;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SqlHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Clean memory for shared values
        ResetSharedPreferences();

        // Initialize pre-defined data
        sqlHelper = new SqlHelper(this);
        initializeData();
    }

    private void initializeData() {
        sqlHelper.open();
        ArrayList<RoleModel> roleModels = sqlHelper.getAllRoles();
        if(roleModels == null) {
            sqlHelper.insertInitialData();
        }
        sqlHelper.close();
    }

    private void ResetSharedPreferences() {
        // Clean cart value
        CommonLogic.setCartItem(getApplicationContext(), 0);
        // Clean login status
        CommonLogic.setLoginStatus(getApplicationContext(), false);
        // Clean order status
        CommonLogic.setOrderStatus(getApplicationContext(), false);
        // Clean cart id
        CommonLogic.setCartId(getApplicationContext(), "");
        // Clean user id
        CommonLogic.setUserId(getApplicationContext(), 0);
    }

    public void btnLoginMain_OnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void btnRegisterMain_OnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterNowActivity.class);
        startActivity(intent);
    }

    public void btnSearchMain_OnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), PhoneSearchActivity.class);
        startActivity(intent);
    }
}
