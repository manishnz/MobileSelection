package com.example.vihu.mobileselection.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vihu.mobileselection.Models.UserModel;
import com.example.vihu.mobileselection.Other.CommonLogic;
import com.example.vihu.mobileselection.Other.RoleConstant;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

public class LoginActivity extends AppCompatActivity {

    SqlHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqlHelper = new SqlHelper(this);
        this.setTitle("Login");
    }

    public void btnLogin_OnClick(View view) {
        try {
            EditText txtUserId = (EditText) findViewById(R.id.txtUserId);
            EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
            String userId = txtUserId.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            if (userId.equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill valid User ID!", Toast.LENGTH_LONG).show();
            } else if (password.equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill valid Password!", Toast.LENGTH_LONG).show();
            } else {
                sqlHelper.open();
                UserModel user = sqlHelper.getUserByUserNameAndPassword(userId, CommonLogic.hashPassword(password));
                sqlHelper.close();
                if (user != null) {
                    if (user.isActive()) {
                        CommonLogic.setUserId(getApplicationContext(), user.getUserId());
                        if (user.getRole().getRoleName().trim().toLowerCase().equals(RoleConstant.Customer.toLowerCase())) {
                            CommonLogic.setLoginStatus(getApplicationContext(), true);
                            boolean orderStatus = CommonLogic.getOrderStatus(getApplicationContext());
                            if (orderStatus) {
                                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), PhoneSearchActivity.class);
                                startActivity(intent);
                            }
                        } else if (user.getRole().getRoleName().trim().toLowerCase().equals(RoleConstant.BM.toLowerCase()) ||
                                user.getRole().getRoleName().trim().toLowerCase().equals(RoleConstant.DM.toLowerCase()) ||
                                user.getRole().getRoleName().trim().toLowerCase().equals(RoleConstant.EM.toLowerCase())) {
                            Intent intent = new Intent(getApplicationContext(), ManageProductActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Invalid Role Provided!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Deactivated User!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Invalid User Id or Password!", Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
