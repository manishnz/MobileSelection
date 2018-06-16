package com.example.vihu.mobileselection.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vihu.mobileselection.Models.BranchMobileModel;
import com.example.vihu.mobileselection.Models.BranchModel;
import com.example.vihu.mobileselection.Models.CustomerModel;
import com.example.vihu.mobileselection.Models.OrderItemModel;
import com.example.vihu.mobileselection.Models.OrderModel;
import com.example.vihu.mobileselection.Models.TempCartModel;
import com.example.vihu.mobileselection.MySql.Configuration;
import com.example.vihu.mobileselection.MySql.Controller;
import com.example.vihu.mobileselection.Other.CommonLogic;
import com.example.vihu.mobileselection.Other.Constant;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {

    private SqlHelper sqlHelper;
    Spinner ddlOrderBranch;
    private CustomerModel customer;
    private ArrayList<String> branches;
    ArrayList<BranchModel> branchModelList;
    private HashMap<String, Integer> branchList = new HashMap<String, Integer>();

    private String TAG = "Branches";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sqlHelper = new SqlHelper(this);
        fillCardExpiryMonthYear();
        showCustomerDetail();
    }

    private void fillCardExpiryMonthYear() {
        // Fill Month
        Spinner ddlCardMonth = (Spinner)findViewById(R.id.ddlCardMonth);
        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.card_months, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlCardMonth.setAdapter(adapterMonth);

        // Fill Year
        Spinner ddlCardYear = (Spinner)findViewById(R.id.ddlCardYear);
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.card_years, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlCardYear.setAdapter(adapterYear);

        // Fill Branch
        ddlOrderBranch = (Spinner)findViewById(R.id.ddlOrderBranch);
        getAllBranches();
        ArrayAdapter<String> adapterBranch = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, branches);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlOrderBranch.setAdapter(adapterBranch);
    }

    private void getAllBranches() {
        fillBranchList();
        branches = new ArrayList<String>();
        if(branchModelList != null && branchModelList.size() > 0) {
            branches.add("Select");
            branchList.put("Select", 0);
            for(BranchModel brand : branchModelList) {
                branches.add(brand.getBranchName());
                branchList.put(brand.getBranchName(), brand.getBranchId());
            }
        }
    }

    private void fillBranchList() {
        StringRequest request = new StringRequest(Request.Method.POST, Configuration.BRANCH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if(!error) {
                        JSONArray branches = (JSONArray)jsonObject.get("branches");
                        JSONObject branch = null;
                        branchModelList = new ArrayList<BranchModel>();
                        for(int i=0; i < branches.length(); i++) {
                            branch = (JSONObject)branches.get(i);
                            branchModelList.add(new BranchModel(branch.getInt("BranchId"), branch.getString("BranchName")));
                        }
                    }
                    else {
                        fillBranchBySqlHelper();
                    }
                }
                catch (JSONException e) {
                    fillBranchBySqlHelper();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fillBranchBySqlHelper();
            }
        });
        Controller.getInstance().addToRequestQueue(request, TAG);
    }

    private void fillBranchBySqlHelper() {
        sqlHelper.open();
        branchModelList = sqlHelper.getAllBranches();
        sqlHelper.close();
    }

    private void showCustomerDetail() {
        sqlHelper.open();
        customer = sqlHelper.getCustomerByUserId(CommonLogic.getUserId(this));
        sqlHelper.close();
        TextView tvwPaymentName = (TextView)findViewById(R.id.tvwPaymentName);
        TextView tvwPaymentAddress = (TextView)findViewById(R.id.tvwPaymentAddress);
        TextView tvwPaymentZip = (TextView)findViewById(R.id.tvwPaymentZip);
        TextView tvwPaymentPhone = (TextView)findViewById(R.id.tvwPaymentPhone);
        TextView tvwPaymentEmail = (TextView)findViewById(R.id.tvwPaymentEmail);
        if(customer != null) {
            tvwPaymentName.setText(customer.getName());
            tvwPaymentAddress.setText(customer.getAddress());
            tvwPaymentZip.setText(customer.getZip());
            tvwPaymentPhone.setText(customer.getPhone());
            tvwPaymentEmail.setText(customer.getEmail());
        }
    }

    public void btnPay_OnClick(View view) {
        try {
            EditText txtCreditCard = (EditText) findViewById(R.id.txtCreditCard);
            EditText txtCardName = (EditText) findViewById(R.id.txtCardName);
            Spinner ddlCardMonth = (Spinner) findViewById(R.id.ddlCardMonth);
            Spinner ddlCardYear = (Spinner) findViewById(R.id.ddlCardYear);
            EditText txtCardCode = (EditText) findViewById(R.id.txtCardCode);
            if (ddlOrderBranch.getSelectedItem().toString().trim().toLowerCase().equals(Constant.dropdownDefaultValue.toLowerCase())) {
                Toast.makeText(getApplicationContext(), "Please select a branch!", Toast.LENGTH_LONG).show();
            } else if (txtCreditCard.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill a valid Credit Card No!", Toast.LENGTH_LONG).show();
            } else if (txtCardName.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill Card Holder Name!", Toast.LENGTH_LONG).show();
            } else if (ddlCardMonth.getSelectedItem().toString().trim().equals("Month")) {
                Toast.makeText(getApplicationContext(), "Please fill Expiraion Month!", Toast.LENGTH_LONG).show();
            } else if (ddlCardYear.getSelectedItem().toString().trim().equals("Year")) {
                Toast.makeText(getApplicationContext(), "Please fill Expiraion Year!", Toast.LENGTH_LONG).show();
            } else if (txtCardCode.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill valid Code!", Toast.LENGTH_LONG).show();
            } else {
                // Fill temp cart
                sqlHelper.open();
                ArrayList<TempCartModel> cartItems = sqlHelper.getTempCartItems(CommonLogic.getCartId(this));
                StringBuilder builder = new StringBuilder("");
                boolean orderConfirmed = false;

                if (cartItems != null) {
                    double orderTotalAmount = 0;

                    // Calculate total amount of order
                    for (TempCartModel item : cartItems) {
                        orderTotalAmount += (item.getQuantity() * item.getModel().getPrice());
                    }

                    // Calculate loyalty points
                    int loyaltyPoints = (int) ((orderTotalAmount / Constant.loyaltyPointsForDollar) * Constant.loyaltyPointsRate);

                    // Insert record in order table
                    Date today = Calendar.getInstance().getTime();
                    int position = ddlOrderBranch.getSelectedItemPosition();
                    BranchModel branch = position == 0 ? null : branchModelList.get(position - 1);
                    sqlHelper.insertOrder(new OrderModel(0, customer, orderTotalAmount, today, branch, loyaltyPoints));

                    // Get the latest order record to capture order id for order item table
                    OrderModel currentOrder = sqlHelper.getLatestOrder();
                    if (currentOrder != null) {

                        BranchMobileModel branchMobile;

                        // Now insert order items
                        for (TempCartModel item : cartItems) {
                            // Check if branch has this model with enough quanity and set order confirmation message accordingly
                            branchMobile = sqlHelper.getBranchProductQuantity(branchList.get(ddlOrderBranch.getSelectedItem().toString()), item.getModel().getModelId());
                            if (branchMobile != null) {
                                if (branchMobile.getQuantity() >= item.getQuantity()) {
                                    builder.append("Order is place for " + item.getModel().getModelName() + ", you can pick it anytime.");
                                    builder.append("\n");
                                } else if (branchMobile.getQuantity() <= 0) {
                                    builder.append("Branch does not have " + item.getModel().getModelName() + " model, you can pick it after " + Constant.laterPickDays + " days!");
                                    builder.append("\n");
                                } else {
                                    builder.append("Order is place for " + item.getModel().getModelName() + ", you can pick " + branchMobile.getQuantity() + " mobile anytime.");
                                    builder.append("\n");
                                    builder.append("Please pick " + branchMobile.getQuantity() + " " + item.getModel().getModelName() + " after " + Constant.laterPickDays + " days!");
                                    builder.append("\n");
                                }
                            } else {
                                builder.append("Branch does not have " + item.getModel().getModelName() + " model, you can pick it after " + Constant.laterPickDays + " days!");
                                builder.append("\n");
                            }
                            sqlHelper.insertOrderItem(new OrderItemModel(0, currentOrder, item.getModel(), item.getQuantity()));

                            // Update branch mobile quantity
                            branchMobile.setQuantity(branchMobile.getQuantity() - item.getQuantity());
                            sqlHelper.updateBranchModelQuantity(branchMobile);
                            orderConfirmed = true;
                        }
                        sqlHelper.deleteCartItems(CommonLogic.getCartId(this));
                    }
                }
                sqlHelper.close();

                // Go for order confirmation
                if (orderConfirmed) {
                    Intent intent = new Intent(getApplicationContext(), OrderConfirmationActivity.class);
                    intent.putExtra("orderStatus", builder.toString());
                    startActivity(intent);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
