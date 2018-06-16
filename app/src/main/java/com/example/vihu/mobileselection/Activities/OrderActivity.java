package com.example.vihu.mobileselection.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.vihu.mobileselection.Adapters.OrderListAdapter;
import com.example.vihu.mobileselection.Models.TempCartModel;
import com.example.vihu.mobileselection.Other.CommonLogic;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private SqlHelper sqlHelper;
    private ArrayList<TempCartModel> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        sqlHelper = new SqlHelper(this);
        fillOrderListAdapter();
    }

    private void fillOrderListAdapter() {
        ListView lstOrder = (ListView)findViewById(R.id.lstOrder);
        sqlHelper.open();
        cartItems = sqlHelper.getTempCartItems(CommonLogic.getCartId(this));
        sqlHelper.close();
        if(cartItems != null) {
            OrderListAdapter adapter = new OrderListAdapter(this, cartItems);
            lstOrder.setAdapter(adapter);
        }
    }

    public void btnPlaceOrder_OnClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.register_menu_layout, null);
        ImageView btnLoginOrder = (ImageView) mView.findViewById(R.id.btnLoginOrder);
        ImageView btnRegisterOrder = (ImageView) mView.findViewById(R.id.btnRegisterOrder);

        boolean loginStatus = CommonLogic.getLoginStatus(getApplicationContext());
        if(!loginStatus) {
            CommonLogic.setOrderStatus(getApplicationContext(), true);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            btnLoginOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                }
            });
            btnRegisterOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterNowActivity.class);
                startActivity(intent);
                }
            });
        }
        else {
            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
            startActivity(intent);
        }
    }
}
