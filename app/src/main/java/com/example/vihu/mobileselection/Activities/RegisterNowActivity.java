package com.example.vihu.mobileselection.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vihu.mobileselection.Models.CustomerModel;
import com.example.vihu.mobileselection.Models.RoleModel;
import com.example.vihu.mobileselection.Models.UserModel;
import com.example.vihu.mobileselection.Other.CommonLogic;
import com.example.vihu.mobileselection.Other.RoleConstant;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

import java.util.regex.Pattern;

public class RegisterNowActivity extends AppCompatActivity {

    private SqlHelper sqlHelper;
    CustomerModel customer;
    EditText txtName, txtEmail, txtPhone, txtAddress, txtZip, txtUserName, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_now);
        initializeControls();
        sqlHelper = new SqlHelper(this);
        ImageView btnRegister = (ImageView)findViewById(R.id.btnRegister);
        ImageView btnDeactivate = (ImageView)findViewById(R.id.btnDeactivate);

        // If user exist then, display data and deactivate button else allow to register
        if(CommonLogic.getLoginStatus(getApplicationContext())) {
            btnRegister.setVisibility(View.GONE);
            btnDeactivate.setVisibility(View.VISIBLE);
            fillUserData();
        }
        else {
            btnRegister.setVisibility(View.VISIBLE);
            btnDeactivate.setVisibility(View.GONE);
        }
    }

    private void initializeControls() {
        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPhone = (EditText)findViewById(R.id.txtPhone);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtZip = (EditText)findViewById(R.id.txtZip);
        txtUserName = (EditText)findViewById(R.id.txtUserName);
        txtPass = (EditText)findViewById(R.id.txtPass);
    }

    private void fillUserData() {
        sqlHelper.open();
        customer = sqlHelper.getCustomerByUserId(CommonLogic.getUserId(this));
        sqlHelper.close();
        if(customer != null) {
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtZip.setText(customer.getZip());
            txtPhone.setText(customer.getPhone());
            txtEmail.setText(customer.getEmail());
            txtUserName.setText(customer.getUser().getUserName());
            txtPass.setText(customer.getUser().getPassword());
            txtUserName.setEnabled(false);
        }
    }

    public void btnRegister_OnClick(View view) {
        try {
            boolean fieldValidation = true;
            Pattern numberPattern = Pattern.compile("^[0-9]");
            CharSequence seqPhone = txtPhone.getText().toString();
            if (txtName.getText().toString().trim().equals("")) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill your Name!", Toast.LENGTH_LONG).show();
            } else if (txtPhone.getText().toString().trim().equals("")) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill your Phone No!", Toast.LENGTH_LONG).show();
            } else if (numberPattern.matcher(seqPhone).matches()) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill only numeric value in Phone No!", Toast.LENGTH_LONG).show();
            } else if (txtZip.getText().toString().trim().equals("")) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill your Zip Code!", Toast.LENGTH_LONG).show();
            } else if (txtUserName.getText().toString().trim().equals("")) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill valid User Name!", Toast.LENGTH_LONG).show();
            } else if (txtPass.getText().toString().trim().equals("")) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill valid Password!", Toast.LENGTH_LONG).show();
            } else if(userNameExist(txtUserName.getText().toString())){
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "User Name already exist, please choose different User Name!", Toast.LENGTH_LONG).show();
            }
            if(fieldValidation) {
                // Get Customer Role
                sqlHelper.open();
                RoleModel role = sqlHelper.getRoleByRoleName(RoleConstant.Customer);

                // Insert customer records in Database
                // First create user
                sqlHelper.insertUser(new UserModel(0, txtUserName.getText().toString().trim(), CommonLogic.hashPassword(txtPass.getText().toString().trim()), role, true));

                // Get the newly created User Id
                UserModel user = sqlHelper.getLatestUser();

                if(user != null) {
                    // Now enter record in customer table with user Id created in above section
                    sqlHelper.insertCustomer(new CustomerModel(0, txtName.getText().toString(), txtPass.getText().toString(), txtPhone.getText().toString(), txtAddress.getText().toString(), txtZip.getText().toString(), user));
                    sqlHelper.close();

                    // Set login status of newly created user
                    CommonLogic.setLoginStatus(getApplicationContext(), true);
                    CommonLogic.setUserId(getApplicationContext(), user.getUserId());

                    boolean orderStatus = CommonLogic.getOrderStatus(getApplicationContext());
                    if (orderStatus) {
                        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), PhoneSearchActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean userNameExist(String userName) {
        sqlHelper.open();
        UserModel user = sqlHelper.getUserByUserName(userName);
        sqlHelper.close();
        if(user == null) {
            return false;
        }
        return true;
    }

    public void btnDeactivate_OnClick(View view) {
        // Update user and set isActive = false
        sqlHelper.open();
        UserModel user = sqlHelper.getUserById(CommonLogic.getUserId(this));
        if(user != null) {
            user.setActive(false);
            sqlHelper.updateUserAcitveStatus(user);
            sqlHelper.close();
        }
        // Redirect user to home screen
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
