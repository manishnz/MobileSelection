package com.example.vihu.mobileselection.Activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vihu.mobileselection.Models.ProductModel;
import com.example.vihu.mobileselection.Models.TempCartModel;
import com.example.vihu.mobileselection.Other.CommonLogic;
import com.example.vihu.mobileselection.Other.Constant;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

import java.util.ArrayList;
import java.util.UUID;

public class ProductActivity extends AppCompatActivity {

    private SqlHelper sqlHelper;
    private int cartItem = 0;
    private TextView tvwCartItem;
    private ProductModel product;
    private ArrayList<TempCartModel> cartItems;
    private String cartId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        sqlHelper = new SqlHelper(this);
        cartId = CommonLogic.getCartId(this);
        if(cartId.equals("")) {
            cartId = UUID.randomUUID().toString();
            CommonLogic.setCartId(this, cartId);
        }
        showProductDetail();
    }

    private void showProductDetail() {
        try {
            int modelId = getIntent().getExtras().getInt("modelId");
            if(modelId > 0) {
                sqlHelper.open();
                this.product = sqlHelper.getProductByModelId(modelId);
                sqlHelper.close();
            }
            TextView tvwProductBrandName = (TextView)findViewById(R.id.tvwProductBrandName);
            TextView tvwProductModelName = (TextView)findViewById(R.id.tvwProductModelName);
            TextView tvwProductMakeYear = (TextView)findViewById(R.id.tvwProductMakeYear);
            TextView tvwProductPriceAmount = (TextView)findViewById(R.id.tvwProductPriceAmount);
            TextView tvwProductProcessorGhz = (TextView)findViewById(R.id.tvwProductProcessorGhz);
            TextView tvwProductCameraMp = (TextView)findViewById(R.id.tvwProductCameraMp);
            TextView tvwProductRamGb = (TextView)findViewById(R.id.tvwProductRamGb);
            TextView tvwProductStorageGb = (TextView)findViewById(R.id.tvwProductStorageGb);
            if(this.product != null) {
                tvwProductBrandName.setText(product.getBrand().getBrandName());
                tvwProductModelName.setText(product.getModelName());
                tvwProductMakeYear.setText(String.valueOf(product.getMake()));
                tvwProductPriceAmount.setText("$" + String.valueOf(product.getPrice()));
                tvwProductProcessorGhz.setText(product.getProcessor());
                tvwProductCameraMp.setText(product.getCamera());
                tvwProductRamGb.setText(product.getRam());
                tvwProductStorageGb.setText(product.getStorage());
            }
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.top_menu, menu);
            boolean loginStatus = CommonLogic.getLoginStatus(getApplicationContext());
            MenuItem mnuProfile = menu.findItem(R.id.mnuProfile);
            mnuProfile.setVisible(loginStatus);
            MenuItem item = menu.findItem(R.id.mnuBadge);
            MenuItemCompat.setActionView(item, R.layout.badge);
            RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
            item.setVisible(true);
            this.tvwCartItem = (TextView) notifCount.findViewById(R.id.tvwCartItem);
            ImageView ivwCartCount = (ImageView) notifCount.findViewById(R.id.ivwCartCount);
            ivwCartCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartItem > 0) {
                        btnBuyNow_OnClick(v);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please add at least one item in Cart!!", Toast.LENGTH_LONG).show();
                    }
                }
            });
            cartItem = CommonLogic.getCartItem(getApplicationContext());
            tvwCartItem.setText(String.valueOf(cartItem));
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void btnAddCart_OnClick(View view) {
        addToCart();
    }

    public void btnBuyNow_OnClick(View view) {
        addToCart();

        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
        startActivity(intent);
    }

    private void addToCart() {
        cartItem++;
        setCartCount();

        // Load all other items
        if(cartItems == null) {
            loadCartItems();
        }

        // Add product into cart
        addTempCartItem();
    }

    private void setCartCount() {
        CommonLogic.setCartItem(getApplicationContext(), cartItem);
        tvwCartItem.setText(String.valueOf(cartItem));
    }

    private void loadCartItems() {
        sqlHelper.open();
        cartItems = sqlHelper.getTempCartItems(cartId);
        sqlHelper.close();
    }

    private void addTempCartItem() {
        boolean hasProduct = false;
        boolean quantityExceed = false;
        if(cartItems != null) {
            int qty = 0;
            for (TempCartModel cartProduct : cartItems) {
                if(cartProduct.getModel().getModelId() == this.product.getModelId()) {
                    qty = cartProduct.getQuantity();
                    qty++;
                    if(qty > Constant.maxItemQuantity) {
                        quantityExceed = true;
                        cartItem--;
                        setCartCount();
                        break;
                    }
                    else {
                        cartProduct.setQuantity(qty);
                        hasProduct = true;
                        break;
                    }
                }
            }
            if(!quantityExceed) {
                if (hasProduct) {
                    updateCartItem(qty);
                } else {
                    insertCartItem();
                }
            }
            else {
                Toast.makeText(this, "Cannot buy a product more than " + String.valueOf(Constant.maxItemQuantity) + " quantity!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            insertCartItem();
        }
    }

    private void insertCartItem() {
        TempCartModel cartModel = new TempCartModel(cartId, 1, this.product);
        sqlHelper.open();
        if(!sqlHelper.insertTempCart(cartModel)) {
            Toast.makeText(this, "DB is not configured properly!", Toast.LENGTH_LONG).show();
        }
        sqlHelper.close();
    }

    private void updateCartItem(int qty) {
        TempCartModel cartModel = new TempCartModel(cartId, qty, this.product);
        sqlHelper.open();
        if(!sqlHelper.updateTempCartQuantity(cartModel)) {
            Toast.makeText(this, "DB is not configured properly!", Toast.LENGTH_LONG).show();
        }
        sqlHelper.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mnuViewProfile:
                Intent profileIntent = new Intent(getApplicationContext(), RegisterNowActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.mnuSignOut:
                Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void btnShare_OnClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"My New Phone!!!");
        startActivity(Intent.createChooser(intent,"Share via.."));
    }
}
